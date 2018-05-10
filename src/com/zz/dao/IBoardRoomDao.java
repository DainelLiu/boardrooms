package com.zz.dao;

import java.util.List;

import com.zz.model.BoardRoom;
import com.zz.util.PageBean;

public interface IBoardRoomDao {
	/**
	 * 新增班级数据
	 * @param BoardRoom
	 * @return
	 */
	public boolean save(BoardRoom boardroom);
	
	/**
	 * 删除班级数据
	 * @param BoardRoom
	 * @return
	 */
	public boolean delete(BoardRoom boardroom);
	
	/**
	 * 更新班级数据
	 * @param BoardRoom
	 * @return
	 */
	public boolean update(BoardRoom boardroom);
	
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
	public BoardRoom getById(String id);
	
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
