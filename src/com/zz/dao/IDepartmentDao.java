package com.zz.dao;

import java.util.List;

import com.zz.model.Department;
import com.zz.util.PageBean;

public interface IDepartmentDao {
	/**
	 * 新增班级数据
	 * @param Department
	 * @return
	 */
	public boolean save(Department department);
	
	/**
	 * 删除班级数据
	 * @param Department
	 * @return
	 */
	public boolean delete(Department department);
	
	/**
	 * 更新班级数据
	 * @param Department
	 * @return
	 */
	public boolean update(Department department);
	
	/**
	 * 查询所有班级数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有班级数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询班级数据
	 * @param id
	 * @return
	 */
	public Department getById(String id);
	
	/**
	 * 根据其他条件查询班级数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询班级数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
