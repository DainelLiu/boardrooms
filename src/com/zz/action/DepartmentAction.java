package com.zz.action;

import java.io.IOException;
import java.net.URLDecoder;
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

import com.zz.dao.IDepartmentDao;
import com.zz.model.Department;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/department")
public class DepartmentAction {

	private IDepartmentDao departmentDao;

	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	@Resource(name = "DepartmentDao")
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	/**
	 * 保存部门信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "save")
	public String save() throws IOException {
		String dName = ServletActionContext.getRequest().getParameter("dName");
		String dDescribe = ServletActionContext.getRequest().getParameter("dDescribe");
		String dNumber = ServletActionContext.getRequest().getParameter("dNumber");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date day=new Date();
		Department department = new Department();
		String hql ="from Department ORDER BY dId DESC";
		List<Object> departmentTypelist = departmentDao.getAllByConds(hql);
		if(departmentTypelist.size() != 0){
			String depId = ((Department) departmentTypelist.get(0)).getdId();
			//String depId = ((Department) departmentTypelist.get(0)).getdId().substring(8);
			
			boolean sign=(depId.substring(0,8)).equals(df.format(day));
			int num = ((Integer.parseInt(depId.substring(8)))+1);
			if(sign){
				if(num<10){
					department.setdId(df.format(day)+"00"+(Integer.toString(num)));
				}else if(Integer.parseInt(depId.substring(8))<=10 && Integer.parseInt(depId.substring(8))<100){
					department.setdId(df.format(day)+"0"+(Integer.toString(num)));
				}else{
					department.setdId(df.format(day)+(Integer.toString(num)));
				}
			}else{
				department.setdId(df.format(day)+"001");
			}
			if (dNumber != null && !"".equals(dNumber)) {
				department.setdNumber(Integer.parseInt(dNumber));
			} else {
				department.setdNumber(0);
			}
		}else{
			department.setdId(df.format(day)+"001");
		}
		if (dNumber != null && !"".equals(dNumber)) {
			department.setdNumber(Integer.parseInt(dNumber));
		} else {
			department.setdNumber(0);
		}
		department.setdDescribe(dDescribe);
		department.setdName(dName);
		

		JSONObject jobj = new JSONObject();
		if (departmentDao.save(department)) {
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
	 * 删除部门信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "delete")
	public String delete() throws IOException {

		String dId = ServletActionContext.getRequest().getParameter("dId");
		Department department = departmentDao.getById(dId);
		JSONObject jobj = new JSONObject();
		if (departmentDao.delete(department)) {
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
	 * 修改部门信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "update")
	public String update() throws IOException {

		String dId = ServletActionContext.getRequest().getParameter("dId");
		String dName = ServletActionContext.getRequest().getParameter("dName");
		String dDescribe = ServletActionContext.getRequest().getParameter("dDescribe");
		String dNumber = ServletActionContext.getRequest().getParameter("dNumber");
		Department department = departmentDao.getById(dId);
		if (dName != null && !"".equals(dName)) {
			department.setdName(dName);
		}
		if (dDescribe != null && !"".equals(dDescribe)) {
			department.setdDescribe(dDescribe);
		}
		if (dNumber != null && !"".equals(dNumber)) {
			department.setdNumber(Integer.parseInt(dNumber));
		}
		JSONObject jobj = new JSONObject();

		if (departmentDao.update(department)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", department);
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
		String dId = ServletActionContext.getRequest().getParameter("dId");
		Department department = departmentDao.getById(dId);
		JSONObject jobj = new JSONObject();
		if (department != null) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", department);
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
		List<Object> departmentTypelist = departmentDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (departmentTypelist.size() > 0) {
			page = new PageBean(departmentTypelist.size(), pageNum, 5);
			list = departmentDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (departmentTypelist.size() > 0) {
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

		List<Object> departmentTypelist = departmentDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (departmentTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(departmentTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	@Action(value = "searchBydName")
	public String searchBydName() throws IOException {
		String dName = URLDecoder.decode(ServletActionContext.getRequest().getParameter("dName"), "utf-8");
		String hql = "from Department where 1=1 and dName LIKE '%" + dName + "%'  or dDescribe LIKE '%" + dName + "%'";
		List<Object> departmentTypelist = departmentDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (departmentTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(departmentTypelist));
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
