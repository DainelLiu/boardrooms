package com.zz.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Department implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3945031047700675724L;
	/**
	 * 部门编号
	 */
	
	//部门编号
	private String dId;
	//部门名称
	private String dName;
	//部门描述胡
	private String dDescribe;
	//部门人数
	private int dNumber;
	
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getdId() {
		return dId;
	}
	public void setdId(String dId) {
		this.dId = dId;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getdDescribe() {
		return dDescribe;
	}
	public void setdDescribe(String dDescribe) {
		this.dDescribe = dDescribe;
	}
	public int getdNumber() {
		return dNumber;
	}
	public void setdNumber(int dNumber) {
		this.dNumber = dNumber;
	}
	@Override
	public String toString() {
		return "Department [dId=" + dId + ", dName=" + dName + ", dDescribe=" + dDescribe + ", dNumber=" + dNumber
				+ "]";
	}
	public Department(String dId, String dName, String dDescribe, int dNumber) {
		super();
		this.dId = dId;
		this.dName = dName;
		this.dDescribe = dDescribe;
		this.dNumber = dNumber;
	}
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
