package com.yc.xfm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yc.xfm.entity.base.Status;
import com.yc.xfm.entity.base.User;

//@Embeddable
@MappedSuperclass
public class CodeEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String code;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User creater;	//创建者[user_id]
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+08:00")
	private Date createTime;//创建时间
	@Enumerated(EnumType.STRING)
	private Status status;	//状态[-1删除1启用，0停用，默认为1]
	
	@Transient
	private Map<String, Object> ext= new LinkedHashMap<>();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Map<String, Object> getExt() {
		return ext;
	}

	/**
	 * @param key 
	 * @param value
	 */
	public void put(String key,Object value) {
		ext.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T)ext.get(key);
	}
	
}
