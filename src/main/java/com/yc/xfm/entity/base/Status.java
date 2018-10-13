package com.yc.xfm.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum Status {
	enable("启用"),
	disable("停用"),
	delete("删除");
 	private	String name;
 	private String code;
	Status(String name){
		this.name = name;
		this.code = this.name();
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	
}
