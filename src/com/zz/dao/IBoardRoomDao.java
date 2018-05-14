package com.zz.dao;

import java.util.List;

import com.zz.model.BoardRoom;
import com.zz.util.PageBean;

public interface IBoardRoomDao {
	/**
	 * 新增会议室数据
	 * @param BoardRoom
	 * @return
	 */
	public boolean save(BoardRoom boardroom);
	
	/**
	 * 删除会议室数据
	 * @param BoardRoom
	 * @return
	 */
	public boolean delete(BoardRoom boardroom);
	
	/**
	 * 更新会议室数据
	 * @param BoardRoom
	 * @return
	 */
	public boolean update(BoardRoom boardroom);
	
	/**
	 * 查询所有会议室数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有会议室数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询会议室数据
	 * @param id
	 * @return
	 */
	public BoardRoom getById(String id);
	
	/**
	 * 根据其他条件查询会议室数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询会议室数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
