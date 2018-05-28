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
import com.zz.dao.IDepartmentDao;
import com.zz.dao.IReserveDao;
import com.zz.dao.IUsersDao;
import com.zz.model.Department;
import com.zz.model.Message;
import com.zz.model.Reserve;
import com.zz.util.JsonUtil;
import com.zz.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@ParentPackage("struts-default")
// 表示继承的父包
@Namespace(value = "/reserve")
public class ReserveAction {

	private IReserveDao reserveDao;

	public IReserveDao getReserveDao() {
		return reserveDao;
	}

	@Resource(name = "ReserveDao")
	public void setReserveDao(IReserveDao reserveDao) {
		this.reserveDao = reserveDao;
	}

	private IBoardRoomDao boardroomDao;

	public IBoardRoomDao getBoardRoomDao() {
		return boardroomDao;
	}

	@Resource(name = "BoardRoomDao")
	public void setBoardRoomDao(IBoardRoomDao boardroomDao) {
		this.boardroomDao = boardroomDao;
	}

	private IDepartmentDao departmentDao;

	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	@Resource(name = "DepartmentDao")
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
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
	 * 保存预约信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "save")
	public String save() throws IOException {

		String resBId = ServletActionContext.getRequest().getParameter("resBId");
		String resDId = ServletActionContext.getRequest().getParameter("resDId");
		String resUId = ServletActionContext.getRequest().getParameter("resUId");
		String resStarttime = ServletActionContext.getRequest().getParameter("resStarttime");
		String resEndtime = ServletActionContext.getRequest().getParameter("resEndtime");
		Reserve reserve = new Reserve();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		
		Date day=new Date();
		String hql ="from Reserve ORDER BY resId DESC";
		List<Object> reserveTypelist = reserveDao.getAllByConds(hql);
		
		
		
		
		reserve.setResBId(boardroomDao.getById(resBId));
		reserve.setResDId(departmentDao.getById(resDId));
		reserve.setResUId(usersDao.getById(resUId));
		reserve.setResStarttime(resStarttime);
		reserve.setResSign(1);
		reserve.setResEndtime(resEndtime);
		if(reserveTypelist.size()!=0){
			String resId = ((Reserve) reserveTypelist.get(0)).getResId();
			boolean sign=(resId.substring(0,8)).equals(df.format(day));
			int num = ((Integer.parseInt(resId.substring(8)))+1);
			if(sign){
				if(num<10){
					reserve.setResId(df.format(day)+"00"+(Integer.toString(num)));
				}else if(Integer.parseInt(resId.substring(8))<=10 && Integer.parseInt(resId.substring(8))<100){
					reserve.setResId(df.format(day)+"0"+(Integer.toString(num)));
				}else{
					reserve.setResId(df.format(day)+(Integer.toString(num)));
				}
			}else{
				reserve.setResId(df.format(day)+"001");
			}
		}else{
			reserve.setResId(df.format(day)+"001");
		}
		
		JSONObject jobj = new JSONObject();
		if (reserveDao.save(reserve)) {
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
	 * 删除预约信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "delete")
	public String delete() throws IOException {

		String resId = ServletActionContext.getRequest().getParameter("resId");
		Reserve reserve = reserveDao.getById(resId);
		JSONObject jobj = new JSONObject();
		if (reserveDao.delete(reserve)) {
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
	 * 修改预约信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "update")
	public String update() throws IOException {

		String resId = ServletActionContext.getRequest().getParameter("resId");
		Reserve reserve = reserveDao.getById(resId);
		JSONObject jobj = new JSONObject();

		if (reserveDao.update(reserve)) {
			jobj.put("mes", "更新成功!");
			jobj.put("status", "success");
			jobj.put("loginUser", reserve);
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
		String resId = ServletActionContext.getRequest().getParameter("resId");
		Reserve reserve = reserveDao.getById(resId);
		JSONObject jobj = new JSONObject();
		if (reserve != null) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", reserve);
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
		List<Object> reserveTypelist = reserveDao.list();// 获取所有类型数据，不带分页
		PageBean page = null;
		if (reserveTypelist.size() > 0) {
			page = new PageBean(reserveTypelist.size(), pageNum, 5);
			list = reserveDao.listAll(page);// 带分页
		}
		JSONObject jobj = new JSONObject();
		if (reserveTypelist.size() > 0) {
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

		List<Object> reserveTypelist = reserveDao.list();// 获取所有类型数据，不带分页
		JSONObject jobj = new JSONObject();
		if (reserveTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(reserveTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	@Action(value = "searchByCount")
	public String searchByCount() throws IOException {
		String resStarttime = ServletActionContext.getRequest().getParameter("resStarttime");
		String resEndtime = ServletActionContext.getRequest().getParameter("resEndtime");
		String resBId = ServletActionContext.getRequest().getParameter("resBId");

		Reserve reserve = new Reserve();
		Reserve reservetwo = new Reserve();
		Department department = new Department();
		String dId;
		int num = 0;
		String hqlOne = "from Reserve  where 1=1 and resBId ='" + resBId + "' and (resStarttime <= '" + resStarttime
				+ "' and resStarttime >= '" + resEndtime + "'or resEndtime >= '" + resStarttime
				+ "' and resEndtime <= '" + resEndtime + "') group by resDId";

		String hql = "from Reserve dName where 1=1 and resBId ='" + resBId + "' and (resStarttime <= '" + resStarttime
				+ "' and resStarttime >= '" + resEndtime + "'or resEndtime >= '" + resStarttime
				+ "' and resEndtime <= '" + resEndtime + "')";
		JSONArray jsonArr = new JSONArray();
		JSONObject tempJson = new JSONObject();
		List<Object> reserveTypelist = reserveDao.getAllByConds(hqlOne);
		for (int i = 0; i < reserveTypelist.size(); i++) {
			reserve = (Reserve) reserveTypelist.get(i);
			dId = reserve.getResDId().getdId();
			String hqlTwo = hql + "and resDId = '" + dId + "'";
			//部门的次数
			num = reserveDao.getAllByConds(hqlTwo).size();//这个list是

			// 部门
			department = reserve.getResDId();
			tempJson.put("departId", reserve.getResDId().getdId());
			tempJson.put("departName", reserve.getResDId().getdName());
			tempJson.put("useNum", num);
			jsonArr.add(tempJson);
		}

		JSONObject jobj = new JSONObject();
		if (reserveTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", jsonArr);
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value = "listAllByBId")
	public String listAllByBId() throws IOException {

		String resStarttime = ServletActionContext.getRequest().getParameter("resStarttime");
		String resEndtime = ServletActionContext.getRequest().getParameter("resEndtime");
		String resBId = ServletActionContext.getRequest().getParameter("resBId");
		String resDId = ServletActionContext.getRequest().getParameter("resDId");
		String hql = "from Reserve dName where 1=1  and resDId ='"+resDId+"' and resBId ='" + resBId + "' and (resStarttime <= '" + resStarttime
				+ "' and resStarttime >= '" + resEndtime + "'or resEndtime >= '" + resStarttime
				+ "' and resEndtime <= '" + resEndtime + "')";
		System.out.println(hql);
		List<Object> reserveTypelist = reserveDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if (reserveTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(reserveTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}
	
	@Action(value = "listAllByDId")
	public String listAllByDId() throws IOException {

		String resDId = ServletActionContext.getRequest().getParameter("resDId");
		String hql = "from Reserve dName where 1=1  and resDId ='"+resDId + "' ORDER BY resStarttime DESC";
		System.out.println(hql);
		List<Object> reserveTypelist = reserveDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if (reserveTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(reserveTypelist));
		} else {
			// save failed
			jobj.put("mes", "获取失败!");
			jobj.put("status", "error");
		}
		ServletActionContext.getResponse().setHeader("content-type", "text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(jobj.toString());
		return null;
	}

	
	@Action(value = "listAllByUId")
	public String listAllByUId() throws IOException {

		String resUId = ServletActionContext.getRequest().getParameter("resUId");
		String hql = "from Reserve dName where 1=1  and resUId ='"+resUId + "' ORDER BY resStarttime DESC";
		System.out.println(hql);
		List<Object> reserveTypelist = reserveDao.getAllByConds(hql);
		JSONObject jobj = new JSONObject();
		if (reserveTypelist.size() > 0) {
			// save success
			jobj.put("mes", "获取成功!");
			jobj.put("status", "success");
			jobj.put("data", JsonUtil.toJsonByListObj(reserveTypelist));
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
