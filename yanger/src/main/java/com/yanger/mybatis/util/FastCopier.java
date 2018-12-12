package com.yanger.mybatis.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.asm.ClassVisitor;
import net.sf.cglib.asm.Type;
import net.sf.cglib.core.AbstractClassGenerator;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.EmitUtils;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.core.Local;
import net.sf.cglib.core.MethodInfo;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.core.Signature;
import net.sf.cglib.core.TypeUtils;

public abstract class FastCopier {
	
	private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey) KeyFactory.create(BeanCopierKey.class);
	
	private static final Type CONVERTER = TypeUtils.parseType(FastCopierConverter.class.getCanonicalName());
	
	private static final Type BEAN_COPIER = TypeUtils.parseType(FastCopier.class.getCanonicalName());
	
	private static final Signature COPY = new Signature("copy", Type.VOID_TYPE,
			new Type[] { Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER });
	
	private static final Signature CONVERT = TypeUtils
			.parseSignature("Object convert(Object, Object, Class, Class, Object)");

	interface BeanCopierKey {
		public Object newInstance(String source, String target, boolean useConverter, boolean caseSensitive);
	}

	abstract public void copy(Object from, Object to, FastCopierConverter converter);

	public static FastCopier create(Class<?> source, Class<?> target, boolean useConverter, boolean caseSensitive) {
		Generator gen = new Generator();
		gen.setSource(source);
		gen.setTarget(target);
		gen.setUseConverter(useConverter);
		gen.setCaseSensitive(caseSensitive);
		return gen.create();
	}

	public static class Generator extends AbstractClassGenerator {
		
		private static final Source SOURCE = new Source(FastCopier.class.getName());
		
		@SuppressWarnings("rawtypes")
		private Class source;
		
		@SuppressWarnings("rawtypes")
		private Class target;
		/**
		 * 是否使用转换器
		 */
		private boolean useConverter;

		private boolean caseSensitive;

		public Generator() {
			super(SOURCE);
		}

		@SuppressWarnings("rawtypes")
		public void setSource(Class source) {
			if (!Modifier.isPublic(source.getModifiers())) {
				setNamePrefix(source.getName());
			}
			this.source = source;
		}

		@SuppressWarnings("rawtypes")
		public void setTarget(Class target) {
			if (!Modifier.isPublic(target.getModifiers())) {
				setNamePrefix(target.getName());
			}
			this.target = target;
		}

		public void setUseConverter(boolean useConverter) {
			this.useConverter = useConverter;
		}

		public void setCaseSensitive(boolean caseSensitive) {
			this.caseSensitive = caseSensitive;
		}

		protected ClassLoader getDefaultClassLoader() {
			return source.getClassLoader();
		}

		public FastCopier create() {
			Object key = KEY_FACTORY.newInstance(source.getName(), target.getName(), useConverter, caseSensitive);
			return (FastCopier) super.create(key);
		}

		public void generateClass(ClassVisitor v) {
			Type sourceType = Type.getType(source);
			Type targetType = Type.getType(target);
			ClassEmitter ce = new ClassEmitter(v);
			ce.begin_class(Constants.V1_2, Constants.ACC_PUBLIC, getClassName(), BEAN_COPIER, null,
					Constants.SOURCE_FILE);

			EmitUtils.null_constructor(ce);
			CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, COPY, null);
			PropertyDescriptor[] sourcePropertyDescriptors = getBeanProperties(source);
			PropertyDescriptor[] targetPropertyDescriptors = getBeanProperties(target);

			Map<String, PropertyDescriptor> targetPropertyDescriptorMap = new HashMap<>();
			for (int i = 0; i < targetPropertyDescriptors.length; i++) {
				PropertyDescriptor setter = targetPropertyDescriptors[i];
				String name = setter.getName();
				if ("class".equals(name)) {
					continue;
				}
				if (setter.getPropertyType() == null) {
					continue;
				}
				if (setter.getWriteMethod() == null || setter.getReadMethod() == null) {
					continue;
				}
				if (!caseSensitive) {
					name = name.toLowerCase();
				}
				targetPropertyDescriptorMap.put(name, targetPropertyDescriptors[i]);
			}
			Local targetLocal = e.make_local();
			Local sourceLocal = e.make_local();
			if (useConverter) {
				e.load_arg(1);
				e.checkcast(targetType);
				e.store_local(targetLocal);
				e.load_arg(0);
				e.checkcast(sourceType);
				e.store_local(sourceLocal);
			} else {
				e.load_arg(1);
				e.checkcast(targetType);
				e.load_arg(0);
				e.checkcast(sourceType);
			}
			for (int i = 0; i < sourcePropertyDescriptors.length; i++) {
				PropertyDescriptor sourcePropertyDescriptor = sourcePropertyDescriptors[i];
				String name = sourcePropertyDescriptor.getName();
				if ("class".equals(name)) {
					continue;
				}
				if (sourcePropertyDescriptor.getPropertyType() == null) {
					continue;
				}
				if (sourcePropertyDescriptor.getReadMethod() == null) {
					continue;
				}
				if (!caseSensitive) {
					name = name.toLowerCase();
				}
				PropertyDescriptor targetPropertyDescriptor = targetPropertyDescriptorMap.get(name);
				if (targetPropertyDescriptor != null) {
					MethodInfo sourceRead = ReflectUtils.getMethodInfo(sourcePropertyDescriptor.getReadMethod());
					MethodInfo targetRead = ReflectUtils.getMethodInfo(targetPropertyDescriptor.getReadMethod());
					MethodInfo targetWrite = ReflectUtils.getMethodInfo(targetPropertyDescriptor.getWriteMethod());
					if (useConverter) {
						// 编写如下代码
						// write.invoke(target,converter.convert(read.invoke(source),targetRead.invoke(target),read.type,write.type,write.name));
						e.load_local(targetLocal);
						e.load_arg(2);
						e.load_local(sourceLocal);
						e.invoke(sourceRead);
						e.box(sourceRead.getSignature().getReturnType());
						e.load_local(targetLocal);
						e.invoke(targetRead);
						e.box(targetRead.getSignature().getReturnType());
						Type getterType = sourceRead.getSignature().getReturnType();
						EmitUtils.load_class(e, getterType);
						Type setterType = targetWrite.getSignature().getArgumentTypes()[0];
						EmitUtils.load_class(e, setterType);
						e.push(name);
						e.invoke_interface(CONVERTER, CONVERT);
						e.unbox_or_zero(setterType);
						e.invoke(targetWrite);
					} else if (compatible(sourcePropertyDescriptor, targetPropertyDescriptor)) {
						e.dup2();
						e.invoke(sourceRead);
						e.invoke(targetWrite);
					}
				}
			}
			e.return_value();
			e.end_method();
			ce.end_class();
		}

		private PropertyDescriptor[] getBeanProperties(Class<?> type) {
			try {
				return Introspector.getBeanInfo(type).getPropertyDescriptors();
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}
		}

		private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
			return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
		}

		@SuppressWarnings("rawtypes")
		protected Object firstInstance(Class type) {
			return ReflectUtils.newInstance(type);
		}

		protected Object nextInstance(Object instance) {
			return instance;
		}
	}
	
}
