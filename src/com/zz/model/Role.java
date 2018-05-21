package com.zz.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7997678402697563403L;
	/**
	 * 教师实体类
	 */
	//角色编号
	private String rId;
	//角色名称
	private String rName;
	@Id
	/*@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")*/
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	@Override
	public String toString() {
		return "Role [rId=" + rId + ", rName=" + rName + "]";
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String rId, String rName) {
		super();
		this.rId = rId;
		this.rName = rName;
	}

	
	
}
