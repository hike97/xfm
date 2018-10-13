package com.yc.xfm.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yc.core.util.ActionUtil;
import com.yc.core.util.GlobalDefine;
import com.yc.xfm.entity.base.User;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	private static final String[] NOINTERCEPTORURL = {"/admin/login*"};
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri=request.getRequestURI();
		if(uri.endsWith(".jsp")){
			return true;
		}
		String contextPath=request.getContextPath();
		if(contextPath!=null&&!contextPath.equals("")){
			uri=uri.substring(contextPath.length());
		}
		String queryString= request.getQueryString();
		uri+="?"+queryString;
		for(String s:NOINTERCEPTORURL){
			if(ActionUtil.parseUrl(uri,s)) return true;
		}
		User user = request.getSession().getAttribute(GlobalDefine.SESSION_USER)==null?
				null:(User) request.getSession().getAttribute(GlobalDefine.SESSION_USER);
		if(user==null){
			String url=request.getContextPath()+"/admin/login";
			ActionUtil.reDirect(response, url,"请先登陆�?");
			return false;
		}
		return true;
	}
}
