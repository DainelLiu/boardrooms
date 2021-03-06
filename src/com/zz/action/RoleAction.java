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

import com.zz.dao.IRoleDao;
import com.zz.model.Department;
import com.zz.model.Role;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/role")
public class RoleAction {
	
	private IRoleDao roleDao;
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	@Resource(name="RoleDao")
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	

	/**
	 * 保存角色信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="save")
	public String save() throws IOException{
		
		String rName = ServletActionContext.getRequest().getParameter("rName");
		
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date day=new Date();
		String hql ="from Role ORDER BY rId DESC";
		List<Object> roleTypelist = roleDao.getAllByConds(hql);
		
		
		
		Role role = new Role();
		if(roleTypelist.size()!=0){
			String rId = ((Role) roleTypelist.get(0)).getrId();
			boolean sign=(rId.substring(0,8)).equals(df.format(day));
			int num = ((Integer.parseInt(rId.substring(8)))+1);
			if(sign){
				if(num<10){
					role.setrId(df.format(day)+"00"+(Integer.toString(num)));
				}else if(Integer.parseInt(rId.substring(8))<=10 && Integer.parseInt(rId.substring(8))<100){
					role.setrId(df.format(day)+"0"+(Integer.toString(num)));
				}else{
					role.setrId(df.format(day)+(Integer.toString(num)));
				}
			}else{
				role.setrId(df.format(day)+"001");
			}
		}else{
			role.setrId(df.format(day)+"001");
		}
		role.setrName(rName);
		
		
		JSONObject jobj = new JSONObject();
		
		if(roleDao.save(role)) {
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
	 * 删除角色信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="delete")
	public String delete() throws IOException{
		
		String rId = ServletActionContext.getRequest().getParameter("rId");
		Role role = roleDao.getById(rId);
		JSONObject jobj = new JSONObject();
		if(roleDao.delete(role)){
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
	 * 修改角色信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="update")
	public String update() throws IOException{
		
		String rId = ServletActionContext.getRequest().getParameter("rId");
		String rName = ServletActionContext.getRequest().getParameter("rName");
		Role role = roleDao.getById(rId);
		if (rName != null && !"".equals(rName)) {
			role.setrName(rName);
		} 
		JSONObject jobj = new JSONObject();
		
		if(roleDao.update(role)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", role);
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
		String rId = ServletActionContext.getRequest().getParameter("rId");
		Role role = roleDao.getById(rId);
		JSONObject jobj = new JSONObject();
		if(role != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data",role);
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
		List<Object> roleTypelist = roleDao.list();//获取所有类型数据，不带分页
		PageBean page=null;
		if(roleTypelist.size()>0){
			page = new PageBean(roleTypelist.size(),pageNum,5);
			list = roleDao.listAll(page);//带分页
		}
		JSONObject jobj = new JSONObject();
		if(roleTypelist.size() > 0){
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

		List<Object> roleTypelist = roleDao.list();//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(roleTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(roleTypelist));
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
