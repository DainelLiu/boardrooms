package com.zz.dao;

import java.util.List;

import com.zz.model.Message;
import com.zz.util.PageBean;

public interface IMessageDao {
	/**
	 * 新增留言数据
	 * @param Message
	 * @return
	 */
	public boolean save(Message message);
	
	/**
	 * 删除留言数据
	 * @param Message
	 * @return
	 */
	public boolean delete(Message message);
	
	/**
	 * 更新留言数据
	 * @param Message
	 * @return
	 */
	public boolean update(Message message);
	
	/**
	 * 查询所有留言数据
	 * @return
	 */
	public List<Object> list();
	
	/**
	 * 查询所有留言数据带分页
	 * @return
	 */
	public List<Object> listAll(PageBean page);
	
	/**
	 * 根据主键id查询留言数据
	 * @param id
	 * @return
	 */
	public Message getById(String id);
	
	/**
	 * 根据其他条件查询留言数据带分页
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getByConds(String hql,PageBean page);
	
	/**
	 * 根据其他条件查询留言数据
	 * @param hql 查询语句
	 * @return
	 */
	public List<Object> getAllByConds(String hql);

}
