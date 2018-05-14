package com.zz.dao;

import java.util.List;

import com.zz.model.Department;
import com.zz.util.PageBean;

public interface IDepartmentDao {
	/**
	 * 新增部门数据
	 * @param Department
	 * @return
	 */
	public boolean save(Department department);
	
	/**
	 * 删除部门数据
	 * @param Department
	 * @return
	 */
	public boolean delete(Department department);
	
	/**
	 * 更新部门数据
	 * @param Department
	 * @return
	 */
	public boolean update(Department department);
	
	/**
	 * 查询所有部门数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有部门数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询部门数据
	 * @param id
	 * @return
	 */
	public Department getById(String id);
	
	/**
	 * 根据其他条件查询部门数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询部门数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
