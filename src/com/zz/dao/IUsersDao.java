package com.zz.dao;

import java.util.List;

import com.zz.model.Users;
import com.zz.util.PageBean;

public interface IUsersDao {
	/**
	 * 新增用户数据
	 * @param Users
	 * @return
	 */
	public boolean save(Users users);
	
	/**
	 * 删除用户数据
	 * @param Users
	 * @return
	 */
	public boolean delete(Users users);
	
	/**
	 * 更新用户数据
	 * @param Users
	 * @return
	 */
	public boolean update(Users users);
	
	/**
	 * 查询所有用户数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有用户数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询用户数据
	 * @param id
	 * @return
	 */
	public Users getById(String id);
	
	/**
	 * 根据其他条件查询用户数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询用户数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
