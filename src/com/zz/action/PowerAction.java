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

import com.zz.dao.IPowerDao;
import com.zz.model.Department;
import com.zz.model.Power;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
//表示继承的父包
@Namespace(value = "/power")
public class PowerAction {
	
	private IPowerDao powerDao;
	
	public IPowerDao getPowerDao() {
		return powerDao;
	}
	@Resource(name="PowerDao")
	public void setPowerDao(IPowerDao powerDao) {
		this.powerDao = powerDao;
	}
	

	/**
	 * 保存角色信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="save")
	public String save() throws IOException{
		
		String pName = ServletActionContext.getRequest().getParameter("pName");
		String pUrl = ServletActionContext.getRequest().getParameter("pUrl");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date day=new Date();
		String hql ="from Power ORDER BY pId DESC";
		List<Object> powerTypelist = powerDao.getAllByConds(hql);
		String pId = ((Power) powerTypelist.get(0)).getpId();
		
		
		Power power = new Power();
		boolean sign=(pId.substring(0,8)).equals(df.format(day));
		int num = ((Integer.parseInt(pId.substring(8)))+1);
		if(sign){
			if(num<10){
				power.setpId(df.format(day)+"00"+(Integer.toString(num)));
			}else if(Integer.parseInt(pId.substring(8))<=10 && Integer.parseInt(pId.substring(8))<100){
				power.setpId(df.format(day)+"0"+(Integer.toString(num)));
			}else{
				power.setpId(df.format(day)+(Integer.toString(num)));
			}
		}else{
			power.setpId(df.format(day)+"001");
		}
		power.setpName(pName);
		power.setpUrl(pUrl);
		
		JSONObject jobj = new JSONObject();
		
		if(powerDao.save(power)) {
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
		
		String pId = ServletActionContext.getRequest().getParameter("pId");
		Power power = powerDao.getById(pId);
		JSONObject jobj = new JSONObject();
		if(powerDao.delete(power)){
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
		
		String pId = ServletActionContext.getRequest().getParameter("pId");
		String pName = ServletActionContext.getRequest().getParameter("pName");
		String pUrl = ServletActionContext.getRequest().getParameter("pUrl");
		Power power = powerDao.getById(pId);
		if (pName != null && !"".equals(pName)) {
			power.setpName(pName);
		} 
		if (pUrl != null && !"".equals(pUrl)) {
			power.setpUrl(pUrl);
		} 
		JSONObject jobj = new JSONObject();
		
		if(powerDao.update(power)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("data", power);
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
		String pId = ServletActionContext.getRequest().getParameter("pId");
		Power power = powerDao.getById(pId);
		JSONObject jobj = new JSONObject();
		if(power != null){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data",power);
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
		List<Object> powerTypelist = powerDao.list();//获取所有类型数据，不带分页
		PageBean page=null;
		if(powerTypelist.size()>0){
			page = new PageBean(powerTypelist.size(),pageNum,5);
			list = powerDao.listAll(page);//带分页
		}
		JSONObject jobj = new JSONObject();
		if(powerTypelist.size() > 0){
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

		List<Object> powerTypelist = powerDao.list();//获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if(powerTypelist.size() > 0){
			//save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(powerTypelist));
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
