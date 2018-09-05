/******************************************************************************
* CREATETIME : 2015年12月8日 下午2:20:48
******************************************************************************/
package com.yanger.mybatis.paginator.domain;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class  DozerCopier {

	private static Mapper mapper = new DozerBeanMapper();
	/**
	 * 数据来源的bean
	 */
	private Object fromObj;
	private List<?> fromList;

	/**
	 * 单例模式引用 <br>
	 * Typically a system will only have one DozerBeanMapper instance per VM. If you are not using an IOC framework where you can define the Mapper as singleton="true", a
	 * DozerBeanMapperSingletonWrapper convenience class has been provided in the Dozer jar.
	 * @return
	 */
	private Mapper getMapperInstance() { 
		return mapper;
	}

	public DozerCopier from(Object fromObj) {
		this.fromObj = fromObj;
		return this;
	}

	public DozerCopier from(List<?> fromList) {
		this.fromList = fromList;
		return this;
	}
	
	public <T> T to(Class<T> toClass) {
		if(fromObj==null)return null;
		Mapper mapper = getMapperInstance();
		T toObj = mapper.map(fromObj,toClass);
		return toObj;
	}
	
	/**
	 * 复制到一个list，list里面对象的属性也会深度复制
	 * @param toClass
	 * @return
	 * @modified:
	 */
	public <T> List<T> toList(Class<T> toClass) {
		if(fromList==null)return null;
		
		Mapper mapper = getMapperInstance();
		List<T> listTo=new  ArrayList<T>();
		for(Object fromObj:fromList){
			T toObj = mapper.map(fromObj,toClass);
			listTo.add(toObj);
		}
		return listTo;
	}
	
}
