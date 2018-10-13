package com.yc.xfm.entity.base;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.xfm.entity.IdEntity;

@Entity  
@Table(name = "base_user") 
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)   
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User extends IdEntity{
	private static final long serialVersionUID = 803035328793378831L;
	private String name;//登录账户
	private String realName;//真实姓名  t
	@JsonIgnore
	private String password;  //登陆密码
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
