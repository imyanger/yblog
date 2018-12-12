package com.yanger.mybatis.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.commons.beanutils.MethodUtils;

public class SimpleCopier {

	@SuppressWarnings("rawtypes")
	private static final Map<Class, Map<Class, FastCopier>> COPIERS_CASESENSITIVE_MAP = new WeakHashMap<>();

	@SuppressWarnings("rawtypes")
	private static final Map<Class, Map<Class, FastCopier>> COPIERS_CASEINSENSITIVE_MAP = new WeakHashMap<>();

	/**
	 * 包含字段
	 */
	private Set<String> includes;

	/**
	 * 排除字段
	 */
	private Set<String> excludes;

	/**
	 * @description 是否大小写敏感，默认否
	 * @return
	 */
	private boolean caseSensitive = true;

	/**
	 * @description 是否包含null值属性
	 * @return
	 */
	private boolean includeNull = true;

	/**
	 * @description 是否包含null值属性
	 * @return
	 */
	private boolean includeEmpty = true;

	/**
	 * @description 是否包含基类型的0
	 * @return
	 */
	private boolean includePrimitiveZero = true;

	/**
	 * @description 是否包含基类型的false
	 * @return
	 */
	private boolean includePrimitiveFalse = true;

	/**
	 * @description 数据来源的bean
	 */
	private Object from;

	/**
	 * @description 数据目标bean
	 */
	private List<Object> toList;

	/**
	 * @description 包含null字段，copier.includeNull();
	 * @return 复印机对象本身
	 */
	public SimpleCopier includeNull() {
		this.includeNull = true;
		return this;
	}

	/**
	 * @description 排除null字段，copier.excludeNull();
	 * @return 复印机对象本身
	 */
	public SimpleCopier excludeNull() {
		this.includeNull = false;
		return this;
	}

	/**
	 * @description 排除空对象字段，copier.includeEmpty();
	 * @return 复印机对象本身
	 */
	public SimpleCopier includeEmpty() {
		this.includeEmpty = true;
		return this;
	}

	/**
	 * @description 排除空对象字段，copier.excludeEmpty();
	 * @return 复印机对象本身
	 */
	public SimpleCopier excludeEmpty() {
		this.includeEmpty = false;
		return this;
	}

	/**
	 * @description 包含基类型的false
	 * @return
	 */
	public SimpleCopier includePrimitiveFalse() {
		this.includePrimitiveFalse = true;
		return this;
	}

	/**
	 * @description 排除基类型的false
	 * @return
	 */
	public SimpleCopier excludePrimitiveFalse() {
		this.includePrimitiveFalse = false;
		return this;
	}

	/**
	 * @description 包含基类型的0
	 * @return
	 */
	public SimpleCopier includePrimitiveZero() {
		this.includePrimitiveZero = true;
		return this;
	}

	/**
	 * @description 排除基类型的0
	 * @return
	 */
	public SimpleCopier excludePrimitiveZero() {
		this.includePrimitiveZero = false;
		return this;
	}

	/**
	 * @description 字段名大小写敏感
	 * @return
	 */
	public SimpleCopier caseSensitive() {
		this.caseSensitive = true;
		return this;
	}

	/**
	 * @description 字段名忽略大小写
	 * @return
	 */
	public SimpleCopier caseInsensitive() {
		this.caseSensitive = false;
		return this;
	}

	/**
	 * @description 指定包含的字段名称，SimpleCopier copier =
	 *              Lang.newCopier();copier.includes(&quot;name&quot;);
	 * @param names-字段名
	 * @return 复印机对象本身
	 */
	public SimpleCopier includes(String... names) {
		for (String name : names) {
			if (name == null) {
				continue;
			}
			name = name.trim();
			if (this.includes == null) {
				includes = new HashSet<>();
			}
			this.includes.add(name);
		}
		return this;
	}

	/**
	 * @description 指定排除的字段名称，SimpleCopier copier =
	 *              Lang.newCopier();copier.excludes(&quot;name&quot;);
	 * @param names-字段名
	 * @return 复印机对象本身
	 */
	public SimpleCopier excludes(String... names) {
		for (String name : names) {
			if (name == null) {
				continue;
			}
			name = name.trim();
			if (this.excludes == null) {
				excludes = new HashSet<>();
			}
			this.excludes.add(name);
		}
		return this;
	}

	public SimpleCopier from(Object from) {
		this.from = from;
		done();
		return this;
	}

	public SimpleCopier to(Object... tos) {
		toList = new ArrayList<>(tos.length);
		for (Object to : tos) {
			if (to != null) {
				toList.add(to);
			}
		}
		done();
		return this;
	}

	/**
	 * @description 清除内含对象，必须重新调用from和to方法才能再次拷贝数据
	 * @return
	 */
	public SimpleCopier clear() {
		this.from = null;
		this.toList = null;
		return this;
	}

	/**
	 * @description 完成拷贝
	 */
	private void done() {
		if (from == null || toList == null || toList.isEmpty()) {
			return;
		}
		fastCopy();
	}

	@SuppressWarnings("rawtypes")
	private boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private void fastCopy() {
		for (Object to : toList) {
			if (to == null) {
				continue;
			}
			Class fromClass = from.getClass();
			Class toClass = to.getClass();

			Map<Class, FastCopier> copierMap = caseSensitive ? COPIERS_CASESENSITIVE_MAP.get(fromClass)
					: COPIERS_CASEINSENSITIVE_MAP.get(fromClass);
			if (copierMap == null) {
				copierMap = new HashMap<>();
				if (caseSensitive) {
					COPIERS_CASESENSITIVE_MAP.put(fromClass, copierMap);
				} else {
					COPIERS_CASEINSENSITIVE_MAP.put(fromClass, copierMap);
				}
			}

			FastCopier cglibCopier = copierMap.get(toClass);
			if (cglibCopier == null) {
				cglibCopier = FastCopier.create(fromClass, toClass, true, caseSensitive);
				copierMap.put(toClass, cglibCopier);
			}

			cglibCopier.copy(from, to, new FastCopierConverter() {

				@SuppressWarnings({})
				@Override
				public Object convert(Object fromValue, Object target, Class sourceClass, Class targetClass,
						Object context) {
					if (!getWrapper(targetClass).isAssignableFrom(getWrapper(sourceClass))) {
						return target;
					}
					if (sourceClass.equals(List.class)) {// 刘平 2015-12-9
															// list对象不复制
						return target;
					}
					if (includes != null && !includes.contains(context)) {
						return target;
					}
					if (excludes != null && excludes.contains(context)) {
						return target;
					}
					if (!includeNull && fromValue == null) {
						return target;
					}
					if (!includeEmpty && fromValue != null && isEmpty(fromValue)) {
						return target;
					}
					if (fromValue != null) {
						if (!includePrimitiveFalse && sourceClass.equals(boolean.class)
								&& SimpleCopier.valueEquals(fromValue, false)) {
							return target;
						}
						if (!includePrimitiveZero && sourceClass.isPrimitive() && fromValue instanceof Number
								&& SimpleCopier.valueEquals(fromValue, 0)) {
							return target;
						}
					}
					return fromValue;
				}

				private Class<?> getWrapper(Class<?> type) {

					if (type.isPrimitive()) {
						return MethodUtils.getPrimitiveWrapper(type);
					}
					return type;
				}
			});
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean valueEquals(Object a, Object b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		if (a.equals(b)) {
			return true;
		}
		// 比较
		if (a instanceof Comparable && b instanceof Comparable) {
			return ((Comparable) a).compareTo((Comparable) b) != 0;
		}
		return false;
	}

}
