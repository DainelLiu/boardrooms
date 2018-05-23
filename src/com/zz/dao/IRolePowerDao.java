package com.zz.dao;

import java.util.List;

import com.zz.model.RolePower;
import com.zz.util.PageBean;

public interface IRolePowerDao {
	/**
	 * 新增角色权限数据
	 * @param RolePower
	 * @return
	 */
	public boolean save(RolePower rolepower);
	
	/**
	 * 删除角色权限数据
	 * @param RolePower
	 * @return
	 */
	public boolean delete(RolePower rolepower);
	
	/**
	 * 更新角色权限数据
	 * @param RolePower
	 * @return
	 */
	public boolean update(RolePower rolepower);
	
	/**
	 * 查询所有角色权限数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有角色权限数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询角色权限数据
	 * @param id
	 * @return
	 */
	public RolePower getById(String id);
	
	/**
	 * 根据其他条件查询角色权限数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询角色权限数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
