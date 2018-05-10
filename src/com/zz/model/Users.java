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
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2624372142284147450L;
	/**
	 * 用户实体类
	 */
	// 编号
	private String uId;
	// 用户名
	private String uName;
	// 密码
	private String uPassword;
	// 真实姓名
	private String uRealName;
	// 出生日期
	private String uBirth;
	// 联系方式
	private String uInformation;
	// 部门编号
	private Department uDId;
	// 角色编号
	private Role uRId;
	
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	public String getuRealName() {
		return uRealName;
	}
	public void setuRealName(String uRealName) {
		this.uRealName = uRealName;
	}
	public String getuBirth() {
		return uBirth;
	}
	public void setuBirth(String uBirth) {
		this.uBirth = uBirth;
	}
	public String getuInformation() {
		return uInformation;
	}
	public void setuInformation(String uInformation) {
		this.uInformation = uInformation;
	}
	@ManyToOne
	@JoinColumn(name="uDId")
	public Department getuDId() {
		return uDId;
	}
	public void setuDId(Department uDId) {
		this.uDId = uDId;
	}
	@ManyToOne
	@JoinColumn(name="uRId")
	public Role getuRId() {
		return uRId;
	}
	public void setuRId(Role uRId) {
		this.uRId = uRId;
	}
	@Override
	public String toString() {
		return "Users [uId=" + uId + ", uName=" + uName + ", uPassword=" + uPassword + ", uRealName=" + uRealName
				+ ", uBirth=" + uBirth + ", uInformation=" + uInformation + ", uDId=" + uDId + ", uRId=" + uRId + "]";
	}
	public Users(String uId, String uName, String uPassword, String uRealName, String uBirth, String uInformation,
			Department uDId, Role uRId) {
		super();
		this.uId = uId;
		this.uName = uName;
		this.uPassword = uPassword;
		this.uRealName = uRealName;
		this.uBirth = uBirth;
		this.uInformation = uInformation;
		this.uDId = uDId;
		this.uRId = uRId;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}


	
}
