package com.yc.xfm.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.xfm.entity.CodeEntity;

/**
 * 公共信息代码表
 * 唐秀楠 20170518
 */
@Entity  
@Table(name = "base_com_info") 
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class ComInfo extends CodeEntity{
	private static final long serialVersionUID = 1L;
	private String name;//名称
	private int type;	/*类型
						 * 1民族、
						 * 2文化程度、
						 * 3政治面貌、
						 */
	@Column(name="info_order")
	private int order;	//排序号
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;                              
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
}
