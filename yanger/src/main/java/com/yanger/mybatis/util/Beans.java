package com.yanger.mybatis.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 用于操作Bean的工具类
 */
@Slf4j
public class Beans {

	/**
	 * @description 禁止实例化
	 */
	private Beans() {

	}

	/**
	 * @description 从数据来源对象创建一个Copier,不再使用，简单复制请使用 Beans.copy().from(obj)
	 * @see Beans#.copy().{@link #from(Object)}
	 * @param from
	 * @return
	 */
	@Deprecated
	public static SimpleCopier from(Object from) {
		return new SimpleCopier().from(from);
	}

	/**
	 * @description 简单复制
	 * @return
	 */
	public static SimpleCopier copy() {
		return new SimpleCopier();
	}

	/**
	 * @description 深度全属性复制，包括list，一般用于PO转VO
	 * @return
	 */
	public static DozerCopier copyDepth() {
		return new DozerCopier();
	}

	/**************** 反射相关方法 ***********************/
	public static Object invokeValue(Object o, String field) {
		Object result = null;
		Method[] methods = o.getClass().getDeclaredMethods();
		for (Method method : methods) {
			String m = method.getName();
			if (m.startsWith("get")) {
				String s = m.substring(3, m.length());
				if (s.equalsIgnoreCase(field)) {
					try {
						result = method.invoke(o);
					} catch (IllegalAccessException e) {
						log.warn("{}", e);
					} catch (IllegalArgumentException e) {
						log.warn("{}", e);
					} catch (InvocationTargetException e) {
						log.warn("{}", e);
					}
				}
			}
		}
		return result;
	}

	public static Object invoke(Object o, String field, Object value) {
		Method[] methods = o.getClass().getDeclaredMethods();
		for (Method method : methods) {
			String m = method.getName();
			if (m.startsWith("set")) {
				String s = m.substring(3, m.length());
				if (s.equalsIgnoreCase(field)) {
					try {
						method.invoke(o, value);
					} catch (IllegalAccessException e) {
						log.warn("{}", e);
					} catch (IllegalArgumentException e) {
						log.warn("{}", e);
					} catch (InvocationTargetException e) {
						log.warn("{}", e);
					}
				}
			}
		}
		return o;
	}

	public static void copy(Object from, Object to) {
		Method[] methods = from.getClass().getDeclaredMethods();
		Map<String, Object> fromObj = new HashMap<>();
		for (Method method : methods) {
			String m = method.getName();
			if (m.startsWith("get")) {
				try {
					Object result = method.invoke(from);
					if (result != null) {
						String field = m.substring(3, m.length()).toLowerCase();
						fromObj.put(field, result);
					}

				} catch (IllegalAccessException e) {
					log.warn("{}", e);
				} catch (IllegalArgumentException e) {
					log.warn("{}", e);
				} catch (InvocationTargetException e) {
					log.warn("{}", e);
				}
			}
		}
		Set<String> fields = fromObj.keySet();
		for (String str : fields) {
			to = invoke(to, str, fromObj.get(str));
		}
	}

}
