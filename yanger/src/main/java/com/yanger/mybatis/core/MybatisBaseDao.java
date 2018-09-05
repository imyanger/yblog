package com.yanger.mybatis.core;

import java.util.List;

/**
 * <p>
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * </p>
 * <p>
 * 这个 Mapper 支持 id 泛型
 * </p>
 */
public interface MybatisBaseDao<T, I> {

	/**
	 * <p>
	 * 插入一条记录
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return int
	 */
	int insert(T entity);

	/**
	 * <p>
	 * 插入一条记录（选择字段， null 字段不插入）
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return int 
	 */
	int insertSelective(T entity);

	/**
	 * <p>
	 * 根据 主键 删除
	 * </p>
	 * 
	 * @param id
	 *            主键ID
	 * @return int 
	 */
	int deleteByPrimaryKey(I id);

	/**
	 * <p>
	 * 删除（根据ID 批量删除）
	 * </p>
	 * 
	 * @param idList
	 *            主键ID列表
	 * @return int 
	 */
	int deleteByPrimaryKeys(List<I> idList);

	/**
	 * <p>
	 * 根据 ID 修改
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return int 
	 */
	int updateByPrimaryKey(T entity);

	/**
	 * <p>
	 * 根据 ID 选择修改
	 * </p>
	 * 
	 * @param entity
	 *            实体对象
	 * @return 影响的记录数
	 */
	int updateSelectiveByPrimaryKey(T entity);

	/**
	 * <p>
	 * 根据 ID 查询
	 * </p>
	 * 
	 * @param id
	 *            主键ID
	 * @return T 返回ID对应的对象
	 */
	T selectByPrimaryKey(I id);

	/**
	 * <p>
	 * 查询（根据ID 批量查询）
	 * </p>
	 * 
	 * @param idList
	 *            主键ID列表
	 * @return 记录List
	 */
	List<T> selectByPrimaryKeys(List<I> idList);

	/**
	 * <p>
	 * 根据 分页参数和entity对象，查询一页记录
	 * </p>
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param entity
	 *            实体对象
	 * @return 一页记录
	 */
	Page<T> selectPage(PageParam pageParam, T entity);
}
