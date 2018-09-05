package com.yanger.mybatis.paginator.domain;

public interface FastCopierConverter {
	Object convert(Object source, Object target, Class<?> sourceClass,
			Class<?> targetClass, Object context);
}
