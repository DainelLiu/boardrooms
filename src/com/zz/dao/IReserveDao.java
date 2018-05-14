package com.zz.dao;

import java.util.List;

import com.zz.model.Reserve;
import com.zz.util.PageBean;

public interface IReserveDao {
	/**
	 * 新增预约数据
	 * @param Reserve
	 * @return
	 */
	public boolean save(Reserve reserve);
	
	/**
	 * 删除预约数据
	 * @param Reserve
	 * @return
	 */
	public boolean delete(Reserve reserve);
	
	/**
	 * 更新预约数据
	 * @param Reserve
	 * @return
	 */
	public boolean update(Reserve reserve);
	
	/**
	 * 查询所有预约数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有预约数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询预约数据
	 * @param id
	 * @return
	 */
	public Reserve getById(String id);
	
	/**
	 * 根据其他条件查询预约数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询预约数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
