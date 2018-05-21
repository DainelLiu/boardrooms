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
public class BoardRoom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -187075694557220127L;
	/**
	 * 会议室实体类
	 */
	
	//会议室编号
	private String bId;
	//会议室名称
	private String bName;
	//容纳人数
	private int bNum;
	//会议室地址
	private String bAdd;
	//是否有投影仪
	private int bEquipment;
	//会议室状态
	private int bSign;
	
	@Id
	/*@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")*/
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public int getbNum() {
		return bNum;
	}
	public void setbNum(int bNum) {
		this.bNum = bNum;
	}
	public String getbAdd() {
		return bAdd;
	}
	public void setbAdd(String bAdd) {
		this.bAdd = bAdd;
	}
	public int getbEquipment() {
		return bEquipment;
	}
	public void setbEquipment(int bEquipment) {
		this.bEquipment = bEquipment;
	}
	public int getbSign() {
		return bSign;
	}
	public void setbSign(int bSign) {
		this.bSign = bSign;
	}
	@Override
	public String toString() {
		return "BoardRoom [bId=" + bId + ", bName=" + bName + ", bNum=" + bNum + ", bAdd=" + bAdd + ", bEquipment="
				+ bEquipment + ", bSign=" + bSign + "]";
	}
	public BoardRoom(String bId, String bName, int bNum, String bAdd, int bEquipment, int bSign) {
		super();
		this.bId = bId;
		this.bName = bName;
		this.bNum = bNum;
		this.bAdd = bAdd;
		this.bEquipment = bEquipment;
		this.bSign = bSign;
	}
	public BoardRoom() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
