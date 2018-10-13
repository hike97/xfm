package com.yc.xfm.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class IdEntity implements Serializable{
	/**
	 * //	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Transient
	private Map<String, Object> ext= new LinkedHashMap<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
