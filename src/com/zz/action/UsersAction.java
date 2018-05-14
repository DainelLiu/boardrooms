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

import com.zz.dao.IDepartmentDao;
import com.zz.dao.IRoleDao;
import com.zz.dao.IUsersDao;
import com.zz.model.Department;
import com.zz.model.Users;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/users")
public class UsersAction {

	private IUsersDao usersDao;

	public IUsersDao getUsersDao() {
		return usersDao;
	}

	@Resource(name = "UsersDao")
	public void setUsersDao(IUsersDao usersDao) {
		this.usersDao = usersDao;
	}

	private IDepartmentDao departmentDao;

	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	@Resource(name = "DepartmentDao")
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	private IRoleDao roleDao;

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Resource(name = "RoleDao")
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "save")
	public String save() throws IOException {

		String uName = ServletActionContext.getRequest().getParameter("uName");
		String uPassword = ServletActionContext.getRequest().getParameter("uPassword");
		String uRealName = ServletActionContext.getRequest().getParameter("uRealName");
		String uBirth = ServletActionContext.getRequest().getParameter("uBirth");
		String uInformation = ServletActionContext.getRequest().getParameter("uInformation");
		String uDId = ServletActionContext.getRequest().getParameter("uDId");
		String uRId = ServletActionContext.getRequest().getParameter("uRId");

		Users users = new Users();
		Department department = new Department();
		users.setuName(uName);
		users.setuPassword(uPassword);
		users.setuRealName(uRealName);
		users.setuBirth(uBirth);
		users.setuInformation(uInformation);
		users.setuDId(departmentDao.getById(uDId));
		users.setuRId(roleDao.getById(uRId));

		JSONObject jobj = new JSONObject();
		if (usersDao.save(users)) {
			int dNumber = (departmentDao.getById(uDId)).getdNumber();
			department.setdNumber(dNumber + 1);
			departmentDao.update(department);
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
	 * 删除用户信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "delete")
	public String delete() throws IOException {

		String uId = ServletActionContext.getRequest().getParameter("uId");
		Users users = usersDao.getById(uId);
		JSONObject jobj = new JSONObject();
		if (usersDao.delete(users)) {
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
	 * 修改用户信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "update")
	public String update() throws IOException {

		String uId = ServletActionContext.getRequest().getParameter("uId");
		String uName = ServletActionContext.getRequest().getParameter("uName");
		String uPassword = ServletActionContext.getRequest().getParameter("uPassword");
		String uRealName = ServletActionContext.getRequest().getParameter("uRealName");
		String uBirth = ServletActionContext.getRequest().getParameter("uBirth");
		String uInformation = ServletActionContext.getRequest().getParameter("uInformation");
		String uDId = ServletActionContext.getRequest().getParameter("uDId");
		String uRId = ServletActionContext.getRequest().getParameter("uRId");
		Users users = usersDao.getById(uId);
		if (uName != null && !"".equals(uName)) {
			users.setuName(uName);
		}
		if (uPassword != null && !"".equals(uPassword)) {
			users.setuPassword(uPassword);
		}
		if (uRealName != null && !"".equals(uRealName)) {
			users.setuRealName(uRealName);
		}
		if (uBirth != null && !"".equals(uBirth)) {
			users.setuBirth(uBirth);
		}
		if (uInformation != null && !"".equals(uInformation)) {
			users.setuInformation(uInformation);
		}
		if (uDId != null && !"".equals(uDId)) {
			users.setuDId(departmentDao.getById(uDId));
		}
		if (uRId != null && !"".equals(uRId)) {
			users.setuRId(roleDao.getById(uRId));
		}
		JSONObject jobj = new JSONObject();

		if (usersDao.update(users)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", users);
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
		String uId = ServletActionContext.getRequest().getParameter("uId");
		Users users = usersDao.getById(uId);
		JSONObject jobj = new JSONObject();
		if (users != null) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", users);
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
		List<Object> usersTypelist = usersDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (usersTypelist.size() > 0) {
			page = new PageBean(usersTypelist.size(), pageNum, 5);
			list = usersDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (usersTypelist.size() > 0) {
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

		List<Object> usersTypelist = usersDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (usersTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(usersTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	@Action(value = "login")
	public String login() throws IOException {
		String uName = URLDecoder.decode(ServletActionContext.getRequest().getParameter("uName"), "utf-8");
		String uPassword = ServletActionContext.getRequest().getParameter("uPwd");
		String uRId = ServletActionContext.getRequest().getParameter("uRId");
		String hql = "from Users where uName='" + uName + "' and uPassword='" + uPassword +"' and uRId='"+uRId+"'";
		List<Object> usersTypelist = usersDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (usersTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", usersTypelist.get(0));
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
