package com.yc.xfm.entity.base;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.xfm.entity.CodeEntity;

/**
 * 部门表
 * 唐秀楠 20161128
 */
@Entity  
@Table(name = "base_department") 
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Department extends CodeEntity{
	private static final long serialVersionUID = 803035328793378831L;
	private String name;		//部门名（比如环保局、工商局等）
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"creater"})
	private Department parent;	//上级部门
	private int level;			//级别（本系统目前只考虑1级，为可扩展性保留此字段）
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
