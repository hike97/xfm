// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpringListener.java

package com.yc.core.spring;


import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
public class SpringListener extends ContextLoaderListener {

	public SpringListener() {
		logger =LogManager.getLogger(getClass());
	}

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		try {
			new SpringContext(event.getServletContext());
		} catch (Exception e) {
			logger.error("Initialized SpringContextError", e);
		}
	}

	private Logger logger;
}
