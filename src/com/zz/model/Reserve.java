package com.zz.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Reserve implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 288297544990957781L;
	/**
	 *预约表实体类 
	 */
	//预约表编号
	private String resId;
	//会议室编号
	private BoardRoom resBId;
	//部门编号
	private Department resDId;
	//用户编号
	private Users resUId;
	//开始时间
	private String resStarttime;
	//结束时间
	private String resEndtime;
	//预约状态
	private String resSign;
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	@ManyToOne
	@JoinColumn(name="resBId")
	public BoardRoom getResBId() {
		return resBId;
	}
	public void setResBId(BoardRoom resBId) {
		this.resBId = resBId;
	}
	@ManyToOne
	@JoinColumn(name="resDId")
	public Department getResDId() {
		return resDId;
	}
	public void setResDId(Department resDId) {
		this.resDId = resDId;
	}
	@ManyToOne
	@JoinColumn(name="resUId")
	public Users getResUId() {
		return resUId;
	}
	public void setResUId(Users resUId) {
		this.resUId = resUId;
	}
	public String getResStarttime() {
		return resStarttime;
	}
	public void setResStarttime(String resStarttime) {
		this.resStarttime = resStarttime;
	}
	public String getResEndtime() {
		return resEndtime;
	}
	public void setResEndtime(String resEndtime) {
		this.resEndtime = resEndtime;
	}
	public String getResSign() {
		return resSign;
	}
	public void setResSign(String resSign) {
		this.resSign = resSign;
	}
	@Override
	public String toString() {
		return "Reserve [resId=" + resId + ", resBId=" + resBId + ", resDId=" + resDId + ", resUId=" + resUId
				+ ", resStarttime=" + resStarttime + ", resEndtime=" + resEndtime + ", resSign=" + resSign + "]";
	}
	public Reserve(String resId, BoardRoom resBId, Department resDId, Users resUId, String resStarttime,
			String resEndtime, String resSign) {
		super();
		this.resId = resId;
		this.resBId = resBId;
		this.resDId = resDId;
		this.resUId = resUId;
		this.resStarttime = resStarttime;
		this.resEndtime = resEndtime;
		this.resSign = resSign;
	}
	public Reserve() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
