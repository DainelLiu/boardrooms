package com.zz.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;

import com.zz.dao.IBoardRoomDao;
import com.zz.model.BoardRoom;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/boardroom")
public class BoardRoomAction {
	
	private IBoardRoomDao boardroomDao;
	
	public IBoardRoomDao getBoardRoomDao() {
		return boardroomDao;
	}
	@Resource(name="BoardRoomDao")
	public void setBoardRoomDao(IBoardRoomDao boardroomDao) {
		this.boardroomDao = boardroomDao;
	}
	

	/**
	 * 保存会议室信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="save")
	public String save() throws IOException{
		
		String bName = ServletActionContext.getRequest().getParameter("bName");
		String bNum = ServletActionContext.getRequest().getParameter("bNum");
		String bAdd = ServletActionContext.getRequest().getParameter("bAdd");
		String bEquipment = ServletActionContext.getRequest().getParameter("bEquipment");
		
		
		BoardRoom boardroom = new BoardRoom();
		
		boardroom.setbName(bName);
		boardroom.setbNum(Integer.parseInt(bNum));
		boardroom.setbAdd(bAdd);
		boardroom.setbEquipment(Integer.parseInt(bEquipment));
		boardroom.setbSign(1);
		JSONObject jobj = new JSONObject();
		if(boardroomDao.save(boardroom)) {
			jobj.put("mes", "保存成功!");
			jobj.put("status", "success");
		}else {
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
		
	}
	/**
	 * 删除会议室信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="delete")
	public String delete() throws IOException{
		
		String bId = ServletActionContext.getRequest().getParameter("bId");
		BoardRoom boardroom = boardroomDao.getById(bId);
		JSONObject jobj = new JSONObject();
		if(boardroomDao.delete(boardroom)){
			//save success
			jobj.put("mes", "删除成功!");
			jobj.put("status", "success");
		}else{
			//save failed
			jobj.put("mes", "删除失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	/**
	 * 修改会议室信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="update")
	public String update() throws IOException{
		
		String bId = ServletActionContext.getRequest().getParameter("bId");
		String bName = ServletActionContext.getRequest().getParameter("bName");
		String bNum = ServletActionContext.getRequest().getParameter("bNum");
		String bAdd = ServletActionContext.getRequest().getParameter("bAdd");
		String bEquipment = ServletActionContext.getRequest().getParameter("bEquipment");
		BoardRoom boardroom = boardroomDao.getById(bId);
		
		if (bName != null && !"".equals(bName)) {
			boardroom.setbName(bName);
		}
		if (bNum != null && !"".equals(bNum)) {
			boardroom.setbNum(Integer.parseInt(bNum));
		}
		if (bAdd != null && !"".equals(bAdd)) {
			boardroom.setbAdd(bAdd);
		}
		if (bEquipment != null && !"".equals(bEquipment)) {
			boardroom.setbEquipment(Integer.parseInt(bEquipment));
		}
		
		
		JSONObject jobj = new JSONObject();
		
		if(boardroomDao.update(boardroom)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", boardroom);
		}else{
			//save failed
			jobj.put("mes", "更新失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	/**
	 * 根据id信息
	 * @return
	 * @throws IOException
	 */
	@Action(value="getById")
	public String getById() throws IOException{
		String bId = ServletActionContext.getRequest().getParameter("bId");
		BoardRoom boardroom = boardroomDao.getById(bId);
		JSONObject jobj = new JSONObject();
		if(boardroom != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data",boardroom);
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	/**
	 * 获取品牌(类型)列表
	 * @return
	 * @throws IOException
	 */
	@Action(value="list")
	public String list() throws IOException{
		//分页
		String pageNumStr = ServletActionContext.getRequest().getParameter("pageNum");
		int pageNum = 1;
		if(pageNumStr!=null && !"".equals(pageNumStr)){
			pageNum = Integer.parseInt(pageNumStr);
		}
		List<Object> list = new ArrayList<Object>();
		List<Object> boardroomTypelist = boardroomDao.list();//获取所有类型数据，不带分页
		PageBean page=null;
		if(boardroomTypelist.size()>0){
			page = new PageBean(boardroomTypelist.size(),pageNum,5);
			list = boardroomDao.listAll(page);//带分页
		}
		JSONObject jobj = new JSONObject();
		if(boardroomTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(list));
			jobj.put("pageTotal", page.getPageCount());
			jobj.put("pageNum", page.getPageNum());
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value="listAll")
	public String listAll() throws IOException{

		List<Object> boardroomTypelist = boardroomDao.list();//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(boardroomTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(boardroomTypelist));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value="updateBySign")
	public String updateBySign() throws IOException{
		
		String bId = ServletActionContext.getRequest().getParameter("bId");
		BoardRoom boardroom = boardroomDao.getById(bId);
		if(boardroom.getbSign() == 1){
			boardroom.setbSign(0);
		}
		if(boardroom.getbSign() == 0){
			boardroom.setbSign(1);
		}
		JSONObject jobj = new JSONObject();
		
		if(boardroomDao.update(boardroom)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", boardroom);
		}else{
			//save failed
			jobj.put("mes", "更新失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	
	@Action(value="searchAll")
	public String searchAll() throws IOException{
		String bName = URLDecoder.decode(ServletActionContext.getRequest().getParameter("bName"), "utf-8");
		String hql ="from BoardRoom where 1=1 and bName LIKE '%"+bName+"%' or bAdd LIKE '%"+bName+"%'";
		List<Object> boardroomTypelist = boardroomDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if(boardroomTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(boardroomTypelist));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value="searchByTime")
	public String searchByTime() throws IOException{
		String resStarttime = ServletActionContext.getRequest().getParameter("resStarttime");
		String resEndtime = ServletActionContext.getRequest().getParameter("resEndtime");
		//String hql ="from BoardRoom where 1=1 and bName LIKE '%"+bName+"%' or bAdd LIKE '%"+bName+"%'";
		/*
			select * from boardroom where 1=1 and bSign = 1 
	and bId not in (select resBId from reserve where 1=1 and resStarttime >= '2013-01-01 11:30:00' and resStarttime <= '2013-01-01 12:30:00' or 
resEndtime >= '2013-01-01 11:30:00' and resEndtime <= '2013-01-01 12:30:00'
)

		 */
		String hql ="from BoardRoom where 1=1 and bSign = 1 and bId not in (select resBId from Reserve where 1=1 and resStarttime <= '"
				+resStarttime+"' and resStarttime >= '"+resEndtime+"'or resEndtime >= '"+resStarttime+"' and resEndtime <= '"+resEndtime+"')";
		System.out.println(hql);
		List<Object> boardroomTypelist = boardroomDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if(boardroomTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(boardroomTypelist));
		}else{
			//save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	

}
