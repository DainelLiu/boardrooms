package com.zz.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7661203144178409828L;
	/**
	 * 留言实体类
	 */
	
	//留言编号
	private String mId;
	//会议室编号
	private BoardRoom mBId;
	//留言用户编号
	private Users mUId;
	//留言用户编号
	private String mDescribe;
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")  
	@GeneratedValue(generator="systemUUID")
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	@ManyToOne
	@JoinColumn(name="mBId")
	public BoardRoom getmBId() {
		return mBId;
	}
	public void setmBId(BoardRoom mBId) {
		this.mBId = mBId;
	}
	@ManyToOne
	@JoinColumn(name="mUId")
	public Users getmUId() {
		return mUId;
	}
	public void setmUId(Users mUId) {
		this.mUId = mUId;
	}
	public String getmDescribe() {
		return mDescribe;
	}
	public void setmDescribe(String mDescribe) {
		this.mDescribe = mDescribe;
	}
	@Override
	public String toString() {
		return "Message [mId=" + mId + ", mBId=" + mBId + ", mUId=" + mUId + ", mDescribe=" + mDescribe + "]";
	}
	public Message(String mId, BoardRoom mBId, Users mUId, String mDescribe) {
		super();
		this.mId = mId;
		this.mBId = mBId;
		this.mUId = mUId;
		this.mDescribe = mDescribe;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
