package com.yc.xfm.entity.base;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.xfm.entity.CodeEntity;
/**
 * 区域
 */
@Entity
@Table(name = "base_region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Region extends CodeEntity{
	private static final long serialVersionUID = 1L;
	private String name;	//区域名，比如江源区，比如XX街道
	@ManyToOne
	@JoinColumn(name="parent_code")
	@JsonIgnoreProperties({"creater"})
	private Region parent;	//上级区域
	private int level;		//级别（本系统目前规定1级为江源区，2级为下属镇、街道）
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Region getParent() {
		return parent;
	}
	public void setParent(Region parent) {
		this.parent = parent;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
