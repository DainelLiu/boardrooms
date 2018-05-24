package com.zz.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Power implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4277905841891575439L;
	/**
	 * 权限实体类
	 */
	
	//权限编号
	private String pId;
	//权限名称
	private String pName;
	//权限地址
	private String pUrl;
	//子编号
	private String pPId;
	
	@Id
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpUrl() {
		return pUrl;
	}
	public void setpUrl(String pUrl) {
		this.pUrl = pUrl;
	}
	public String getpPId() {
		return pPId;
	}
	public void setpPId(String pPId) {
		this.pPId = pPId;
	}
	@Override
	public String toString() {
		return "Power [pId=" + pId + ", pName=" + pName + ", pUrl=" + pUrl + ", pPId=" + pPId + "]";
	}
	public Power(String pId, String pName, String pUrl, String pPId) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.pUrl = pUrl;
		this.pPId = pPId;
	}
	public Power() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
