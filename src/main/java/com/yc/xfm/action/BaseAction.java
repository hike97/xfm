package com.yc.xfm.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.core.util.GlobalDefine;
import com.yc.core.util.Page;
import com.yc.xfm.entity.base.User;

/**
 * 基础的Controller,所有的Controller必须继承此类
 * 
 * 作者：赵夯 时间：2016年7月14日
 */
public abstract class BaseAction{
	private Logger logger;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	public BaseAction(){
		logger =LogManager.getLogger(getClass());
	}
	/**Session Attribute**/
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String name){
		Object object=request.getSession().getAttribute(name);
		return object==null?null:(T) object;
	}
	/**Session Attribute**/
	public User getSessionUser(){
		return getAttribute(GlobalDefine.SESSION_USER);
	}
	/**Ehcache Attribute**/
/*	public Member getCacheMember(){
		String token = request.getParameter("token");
		return EhcacheUtil.getSigninInstance().get(token);
	}*/
	public String getParameter(String name,String defaut){
		String s=request.getParameter(name); 
		return s!=null&&!s.equals("")?s:defaut;
	}
	public String getParameter(String name){
		String s=request.getParameter(name); 
		return "".equals(s)?null:s;
	}
	public String[] getParameterValues(String name){
		return request.getParameterValues(name); 
	}
	/**基本类型转换 **/
	public Boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public Boolean getBoolean(String name, Boolean defaut) {
		String booleanStr=request.getParameter(name);
		if (booleanStr == null)
			return defaut;
		if (booleanStr.equals("true") || booleanStr.equals("1"))
			return true;
		if (booleanStr.equals("false") || booleanStr.equals("0"))
			return false;
		return defaut;
	}

	public Long getLong(String name) {
		return getLong(name,0l);
	}
	public Long getLong(String name, Long defaut) {
		String longStr=request.getParameter(name);
		if (longStr == null || longStr.equals(""))
			return defaut;
		return Long.parseLong(longStr);
	}

	public Integer getInt(String name) {
		return getInt(name,0);
	}

	public Integer getInt(String name, Integer defaut) {
		String intStr=request.getParameter(name);
		if (intStr == null || intStr.equals(""))
			return defaut;
		return Integer.parseInt(intStr);
	}

	public Float getFloat(String name) {
		return getFloat(name, 0f);
	}

	public Float getFloat(String name, Float defaut) {
		String floatStr=request.getParameter(name);
		if (floatStr == null || floatStr.equals(""))
			return defaut;
		return Float.parseFloat(floatStr);
	}

	/**
	* @author:顾泽	
	* @date 2013-12-25下午4:51:55
	* @Title: getDate
	* @Description: 格式化日
	* @param source 日期字符
	* @param format 格式  yyyy-MM-dd
	* @param defaut 默认 new Date()
	* @return
	* @throws Exception Date    返回类型
	* @throws
	*/
	public Date getDate(String name,String format,Date defaut) throws Exception{
		String source=request.getParameter(name);
		if(source==null||source.equals("")){
			if(format==null||defaut==null)
				return defaut;
			SimpleDateFormat dateFormat=new SimpleDateFormat(format);
			return dateFormat.parse(dateFormat.format(defaut));
		}
		if(format==null){
			if(source.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
				format="yyyy-MM-dd";
			}else if(source.matches("[0-9]{4}")){
				format="yyyy";
			}else if(source.matches("[0-9]{4}-[0-9]{1,2}")){
				format="yyyy-MM";
			}else{
				format="yyyy-MM-dd HH:mm:ss";
			}
		}
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		return dateFormat.parse(source);
	}
	public Date getDate(String name) throws Exception{
		return getDate(name,null,null);
	}
	public Date getDate(String name,String format) throws Exception{
		return getDate(name,format,null);
	}
	/*********** 结果处理 **********/
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, Object> error(Exception e) {
//		e.printStackTrace();
		logger.error("SpringMVCError", e);
		Map<String, Object> map = new HashMap<>();
		map.put("s", false);
		map.put("i", "发生异常，操作失败！");
		return map;
	}
	
	public Map<String, Object> error() {
		return error("");
	}

	public Map<String, Object> error(String error) {
		Map<String, Object> map = new HashMap<>();
		map.put("s", false);
		if (error == null||error.equals(""))
			map.put("i", "发生异常，操作失败！");
		else
			map.put("i", error);
		return map;
	}
	public Map<String, Object> success() {
		return success(null);
	}
	public Map<String, Object> success(String success) {
		Map<String, Object> map = new HashMap<>();
		map.put("s", true);
		if (success == null)
			map.put("i", "操作成功！");
		else
			map.put("i", success);
		return map;
	}

	public Map<String, Object> success(Object object) {
		Map<String, Object> result= success();
		if (object instanceof Page) {
			Page page = (Page) object;
			result.put("totalNum", page.getMaxRow());
			result.put("recordsTotal", page.getMaxRow());
			result.put("recordsFiltered", page.getMaxRow());
			result.put("totalPage", page.getTotPage());
			result.put("pageNum", page.getCurPage());
			result.put("pageSize", page.getPrePage());
			result.put("data", page.getList());
		} else if (object instanceof List) {
			result.put("list", object);
		} else if (object instanceof String) {
			result.put("i", object);
		} else {
			result.put("data", object);
		}
		return result;
	}
	
	public String toJson(Object object) {
		String str = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (object instanceof Page) {
				Page page = (Page) object;
				Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
		    	jsonMap.put("recordsTotal", page.getMaxRow());//total键 存放总记录数，必须的  
		    	jsonMap.put("recordsFiltered", page.getMaxRow());//total键 存放总记录数，必须的  
		        jsonMap.put("data", page.getList());//+
		        object=jsonMap;
			}
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("SpringMVCError", e);
		}
		return str;
	}
	
	
	public static Long parseLong(String longStr){
		if(longStr==null||longStr.equals(""))return 0l;
		return Long.parseLong(longStr);
	}
	public static Long parseLong(String longStr,Long defaut){
		if(longStr==null||longStr.equals(""))return defaut;
		return Long.parseLong(longStr);
	}
	public static Integer parseInt(String intStr){
		if(intStr==null||intStr.equals(""))return 0;
		return Integer.parseInt(intStr);
	}
	public static Integer parseInt(String intStr,Integer defaut){
		if(intStr==null||intStr.equals(""))return defaut;
		return Integer.parseInt(intStr);
	}
}