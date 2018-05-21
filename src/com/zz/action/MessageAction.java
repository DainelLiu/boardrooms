package com.zz.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;

import com.zz.dao.IBoardRoomDao;
import com.zz.dao.IMessageDao;
import com.zz.dao.IUsersDao;
import com.zz.model.Department;
import com.zz.model.Message;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/message")
public class MessageAction {

	private IMessageDao messageDao;

	public IMessageDao getMessageDao() {
		return messageDao;
	}

	@Resource(name = "MessageDao")
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	private IBoardRoomDao boardroomDao;

	public IBoardRoomDao getBoardRoomDao() {
		return boardroomDao;
	}

	@Resource(name = "BoardRoomDao")
	public void setBoardRoomDao(IBoardRoomDao boardroomDao) {
		this.boardroomDao = boardroomDao;
	}

	private IUsersDao usersDao;

	public IUsersDao getUsersDao() {
		return usersDao;
	}

	@Resource(name = "UsersDao")
	public void setUsersDao(IUsersDao usersDao) {
		this.usersDao = usersDao;
	}

	/**
	 * 保存留言信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "save")
	public String save() throws IOException {

		String mBId = ServletActionContext.getRequest().getParameter("mBId");
		String mUId = ServletActionContext.getRequest().getParameter("mUId");
		String mDescribe = ServletActionContext.getRequest().getParameter("mDescribe");

		
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date day=new Date();
		String hql ="from Message ORDER BY mId DESC";
		List<Object> messageTypelist = messageDao.getAllByConds(hql);
		String mId = ((Message) messageTypelist.get(0)).getmId();
		Message message = new Message();
		message.setmBId(boardroomDao.getById(mBId));
		message.setmUId(usersDao.getById(mUId));
		message.setmDescribe(mDescribe);
		boolean sign=(mId.substring(0,8)).equals(df.format(day));
		int num = ((Integer.parseInt(mId.substring(8)))+1);
		if(sign){
			if(num<10){
				message.setmId(df.format(day)+"00"+(Integer.toString(num)));
			}else if(Integer.parseInt(mId.substring(8))<=10 && Integer.parseInt(mId.substring(8))<100){
				message.setmId(df.format(day)+"0"+(Integer.toString(num)));
			}else{
				message.setmId(df.format(day)+(Integer.toString(num)));
			}
		}else{
			message.setmId(df.format(day)+"001");
		}
		JSONObject jobj = new JSONObject();
		if (messageDao.save(message)) {
			jobj.put("mes", "保存成功!");
			jobj.put("status", "success");
		} else {
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;

	}

	/**
	 * 删除留言信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "delete")
	public String delete() throws IOException {

		String mId = ServletActionContext.getRequest().getParameter("mId");
		Message message = messageDao.getById(mId);
		JSONObject jobj = new JSONObject();
		if (messageDao.delete(message)) {
			// save success
			jobj.put("mes", "删除成功!");
			jobj.put("status", "success");
		} else {
			// save failed
			jobj.put("mes", "删除失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	/**
	 * 修改留言信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "update")
	public String update() throws IOException {

		String mId = ServletActionContext.getRequest().getParameter("mId");
		String mBId = ServletActionContext.getRequest().getParameter("mBId");
		String mUId = ServletActionContext.getRequest().getParameter("mUId");
		String mDescribe = ServletActionContext.getRequest().getParameter("mDescribe");
		Message message = messageDao.getById(mId);
		if (mBId != null && !"".equals(mBId)) {
			message.setmBId(boardroomDao.getById(mBId));
		} 
		if (mUId != null && !"".equals(mUId)) {
			message.setmUId(usersDao.getById(mUId));
		} 
		if (mDescribe != null && !"".equals(mDescribe)) {
			message.setmDescribe(mDescribe);
		} 
		JSONObject jobj = new JSONObject();

		if (messageDao.update(message)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", message);
		} else {
			// save failed
			jobj.put("mes", "更新失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	/**
	 * 根据id信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getById")
	public String getById() throws IOException {
		String mId = ServletActionContext.getRequest().getParameter("mId");
		Message message = messageDao.getById(mId);
		JSONObject jobj = new JSONObject();
		if (message != null) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", message);
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	/**
	 * 获取品牌(类型)列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "list")
	public String list() throws IOException {
		// 分页
		String pageNumStr = ServletActionContext.getRequest().getParameter("pageNum");
		int pageNum = 1;
		if (pageNumStr != null && !"".equals(pageNumStr)) {
			pageNum = Integer.parseInt(pageNumStr);
		}
		List<Object> list = new ArrayList<Object>();
		List<Object> messageTypelist = messageDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (messageTypelist.size() > 0) {
			page = new PageBean(messageTypelist.size(), pageNum, 5);
			list = messageDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (messageTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(list));
			jobj.put("pageTotal", page.getPageCount());
			jobj.put("pageNum", page.getPageNum());
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	@Action(value = "listAll")
	public String listAll() throws IOException {

		List<Object> messageTypelist = messageDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (messageTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(messageTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	
	@Action(value = "listAllByUsers")
	public String listAllByUsers() throws IOException {
		
		String uId = ServletActionContext.getRequest().getParameter("uId");
		String hql="message where 1=1 and mUId ='"+uId+"'";
		List<Object> messageTypelist = messageDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if (messageTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(messageTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
}
