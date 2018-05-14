package com.zz.dao;

import java.util.List;

import com.zz.model.Role;
import com.zz.util.PageBean;

public interface IRoleDao {
	/**
	 * 新增角色数据
	 * @param Role
	 * @return
	 */
	public boolean save(Role role);
	
	/**
	 * 删除角色数据
	 * @param Role
	 * @return
	 */
	public boolean delete(Role role);
	
	/**
	 * 更新角色数据
	 * @param Role
	 * @return
	 */
	public boolean update(Role role);
	
	/**
	 * 查询所有角色数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有角色数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询角色数据
	 * @param id
	 * @return
	 */
	public Role getById(String id);
	
	/**
	 * 根据其他条件查询角色数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询角色数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
