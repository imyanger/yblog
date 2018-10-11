package com.yanger.generator.dao;

import java.util.List;

import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;

/**
* <p>Title: MybatisBaseDao.java</p>  
* <p>Description: Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能 这个 Mapper 支持 id 泛型</p>  
* @author 杨号  
* @date 2018年9月14日
 */
public interface MybatisBaseDao<T, I> {

	/**
	 * <p>插入一条记录 </p>
	 * @param entity实体对象
	 * @return int
	 */
	int insert(T entity);

	/**
	 * <p>插入一条记录（选择字段， null 字段不插入） </p>
	 * @param entity 实体对象
	 * @return int 
	 */
	int insertVal(T entity);

	/**
	 * <p>根据 主键 删除  </p>
	 * @param id 主键ID
	 * @return int 
	 */
	int deleteById(I id);

	/**
	 * <p>删除（根据ID 批量删除） </p>
	 * @param idList 主键ID列表
	 * @return int 
	 */
	int deleteByIds(List<I> idList);

	/**
	 * <p>根据 ID 修改 </p>
	 * @param entity 实体对象
	 * @return int 
	 */
	int updateById(T entity);

	/**
	 * <p>根据 ID 选择修改</p>
	 * @param entity 实体对象
	 * @return 影响的记录数
	 */
	int updateValById(T entity);

	/**
	 * <p>根据 ID 查询 </p>
	 * @param id 主键ID
	 * @return T 返回ID对应的对象
	 */
	T selectById(I id);

	/**
	 * <p>查询（根据ID 批量查询） </p>
	 * @param idList 主键ID列表
	 * @return 记录List
	 */
	List<T> selectByIds(List<I> idList);

	/**
	 * <p>根据 分页参数和entity对象，查询一页记录 </p>
	 * @param pageParam 分页参数
	 * @param entity 实体对象
	 * @return 一页记录
	 */
	Page<T> selectPage(PageParam pageParam, T entity);
	
}
