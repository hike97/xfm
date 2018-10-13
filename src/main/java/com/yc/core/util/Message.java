package com.yc.core.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
	Map<String, Object> map = new HashMap<>();
	public void put(String key,Object value) {
		map.put(key, value);
	}
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String json="";
		try {
			json=mapper.writeValueAsString(map);
			System.out.println("message:"+json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	public void fail() throws Exception {
		fail(null,null);
	}
	public void fail(String msg) throws Exception {
		fail(msg,null);
	}
	public void fail(String msg,HttpServletResponse response) throws Exception {
		success(false,msg, response);
	}
	public void success(String msg,HttpServletResponse response) throws Exception {
		success(true,msg, response);
	}
	private void success(Boolean success, String msg, HttpServletResponse response) throws Exception {
		map.put("success", success);
		if(msg!=null)
			map.put("msg", msg);
		if(response!=null){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8"); 
			response.getWriter().print(this.toString());
		}
	}
}
