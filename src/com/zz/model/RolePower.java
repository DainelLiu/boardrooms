package com.zz.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class RolePower implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6580613296277367568L;
	/**
	 * 角色权限实体类
	 */
	
	//角色权限编号
	private String rpId;
	//角色权限角色编号
	private Role rpRId;
	//角色权限权限编号
	private Power rpPId;
	@Id
	public String getrpId() {
		return rpId;
	}
	public void setrpId(String rpId) {
		this.rpId = rpId;
	}
	@ManyToOne
	@JoinColumn(name="rpRId")
	public Role getrpRId() {
		return rpRId;
	}
	public void setrpRId(Role rpRId) {
		this.rpRId = rpRId;
	}
	@ManyToOne
	@JoinColumn(name="rpPId")
	public Power getrpPId() {
		return rpPId;
	}
	public void setrpPId(Power rpPId) {
		this.rpPId = rpPId;
	}
	@Override
	public String toString() {
		return "RolePower [rpId=" + rpId + ", rpRId=" + rpRId + ", rpPId=" + rpPId + "]";
	}
	public RolePower(String rpId, Role rpRId, Power rpPId) {
		super();
		this.rpId = rpId;
		this.rpRId = rpRId;
		this.rpPId = rpPId;
	}
	public RolePower() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
