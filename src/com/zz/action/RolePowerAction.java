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

import com.zz.dao.IRolePowerDao;
import com.zz.model.RolePower;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/rolepower")
public class RolePowerAction {

	private IRolePowerDao rolepowerDao;

	public IRolePowerDao getRolePowerDao() {
		return rolepowerDao;
	}

	@Resource(name = "RolePowerDao")
	public void setRolePowerDao(IRolePowerDao rolepowerDao) {
		this.rolepowerDao = rolepowerDao;
	}

	/**
	 * 保存部门信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "save")
	public String save() throws IOException {
		String rpRId = ServletActionContext.getRequest().getParameter("rpRId");
		String rpPId = ServletActionContext.getRequest().getParameter("rpPId");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date day=new Date();
		RolePower rolepower = new RolePower();
		String hql ="from RolePower ORDER BY rpId DESC";
		List<Object> rolepowerTypelist = rolepowerDao.getAllByConds(hql);
		String id = ((RolePower) rolepowerTypelist.get(0)).getrpId();
		//String id = ((RolePower) rolepowerTypelist.get(0)).getrpId().substring(8);
		
		boolean sign=(id.substring(0,8)).equals(df.format(day));
		int num = ((Integer.parseInt(id.substring(8)))+1);
		if(sign){
			if(num<10){
				rolepower.setrpId(df.format(day)+"00"+(Integer.toString(num)));
			}else if(Integer.parseInt(id.substring(8))<=10 && Integer.parseInt(id.substring(8))<100){
				rolepower.setrpId(df.format(day)+"0"+(Integer.toString(num)));
			}else{
				rolepower.setrpId(df.format(day)+(Integer.toString(num)));
			}
		}else{
			rolepower.setrpId(df.format(day)+"001");
		}
		
		rolepower.setrpRId(rpRId);
		rolepower.setrpPId(rpPId);
		

		JSONObject jobj = new JSONObject();
		if (rolepowerDao.save(rolepower)) {
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

		String rpId = ServletActionContext.getRequest().getParameter("rpId");
		RolePower rolepower = rolepowerDao.getById(rpId);
		JSONObject jobj = new JSONObject();
		if (rolepowerDao.delete(rolepower)) {
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

		String rpId = ServletActionContext.getRequest().getParameter("rpId");
		String rpRId = ServletActionContext.getRequest().getParameter("rpRId");
		String rpPId = ServletActionContext.getRequest().getParameter("rpPId");
		RolePower rolepower = rolepowerDao.getById(rpId);
		if (rpRId != null && !"".equals(rpRId)) {
			rolepower.setrpRId(rpRId);
		}
		if (rpPId != null && !"".equals(rpPId)) {
			rolepower.setrpPId(rpPId);
		}
		JSONObject jobj = new JSONObject();

		if (rolepowerDao.update(rolepower)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", rolepower);
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
		String rpId = ServletActionContext.getRequest().getParameter("rpId");
		RolePower rolepower = rolepowerDao.getById(rpId);
		JSONObject jobj = new JSONObject();
		if (rolepower != null) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", rolepower);
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
		List<Object> rolepowerTypelist = rolepowerDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (rolepowerTypelist.size() > 0) {
			page = new PageBean(rolepowerTypelist.size(), pageNum, 5);
			list = rolepowerDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (rolepowerTypelist.size() > 0) {
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

		List<Object> rolepowerTypelist = rolepowerDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (rolepowerTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(rolepowerTypelist));
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
		String hql = "from RolePower where 1=1 and dName LIKE '%" + dName + "%'  or dDescribe LIKE '%" + dName + "%'";
		List<Object> rolepowerTypelist = rolepowerDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (rolepowerTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(rolepowerTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value = "searchByrpPId")
	public String searchByrpPId() throws IOException {
		String rpPId = ServletActionContext.getRequest().getParameter("rpPId");
		String hql = "from RolePower where 1=1 and rpPId = '" + rpPId +"'";
		List<Object> rolepowerTypelist = rolepowerDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (rolepowerTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(rolepowerTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value = "searchByrpRId")
	public String searchByrpRId() throws IOException {
		String rpRId = ServletActionContext.getRequest().getParameter("rpRId");
		String hql = "from RolePower where 1=1 and rpRId = '" + rpRId +"'";
		List<Object> rolepowerTypelist = rolepowerDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (rolepowerTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(rolepowerTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value = "searchByuId")
	public String searchByuId() throws IOException {
		String uId = ServletActionContext.getRequest().getParameter("uId");
		//SELECT  * from rolepower where 1=1 and rpRId IN (SELECT uRId FROM users where uId ='60284706635a2fc601635a30e9a31100')
		String hql = "from rolepower where 1=1 and rpRId IN (SELECT uRId FROM users where uId ='" + uId +"')";
		List<Object> rolepowerTypelist = rolepowerDao.getAllByConds(hql);// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (rolepowerTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(rolepowerTypelist));
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
