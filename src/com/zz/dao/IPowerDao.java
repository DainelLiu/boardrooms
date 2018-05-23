package com.zz.dao;

import java.util.List;

import com.zz.model.Power;
import com.zz.util.PageBean;

public interface IPowerDao {
	/**
	 * 新增权限数据
	 * @param Power
	 * @return
	 */
	public boolean save(Power power);
	
	/**
	 * 删除权限数据
	 * @param Power
	 * @return
	 */
	public boolean delete(Power power);
	
	/**
	 * 更新权限数据
	 * @param Power
	 * @return
	 */
	public boolean update(Power power);
	
	/**
	 * 查询所有权限数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有权限数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询权限数据
	 * @param id
	 * @return
	 */
	public Power getById(String id);
	
	/**
	 * 根据其他条件查询权限数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询权限数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
