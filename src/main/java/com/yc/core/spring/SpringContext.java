// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpringContext.java

package com.yc.core.spring;

import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContext {
	public SpringContext(ServletContext appc) {
		ac = WebApplicationContextUtils.getWebApplicationContext(appc);
	}
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T)ac.getBean(beanName);
	}
	public static WebApplicationContext ac;
	
}
