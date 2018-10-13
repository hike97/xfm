package com.yc.core.util;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {  
	  
    private static final String path = "/ehcache.xml";  
    private static final String NAMECAPTCHA="com.wkt.captcha";//验证码
    private static final String NAMESIGNIN="com.wkt.signin";//登录
    private String CACHENAME;
    private static URL url;  
  
    private static CacheManager manager;  
  
    private static EhcacheUtil ehCacheCaptcha;  
    private static EhcacheUtil ehCacheSignin;  
    private EhcacheUtil(String path) {  
    	if(manager==null){
    		System.setProperty("net.sf.ehcache.enableShutdownHook","true");
    		 url = getClass().getResource(path);  
    		 manager = CacheManager.create(url);  
    	}
    }  
    //验证码缓存
    public static EhcacheUtil getCaptchaInstance() { 
    	if (ehCacheCaptcha== null) {  
        	ehCacheCaptcha= new EhcacheUtil(path);  
        	ehCacheCaptcha.CACHENAME=NAMECAPTCHA;
        }  
        return ehCacheCaptcha;  
    }
    //登陆缓存
    public static EhcacheUtil getSigninInstance() { 
    	if (ehCacheSignin== null) {  
    		ehCacheSignin= new EhcacheUtil(path);  
    		ehCacheSignin.CACHENAME=NAMESIGNIN;
        }  
        return ehCacheSignin;  
    }
//    private static EhcacheUtil getInstance() {  
//        if (ehCache== null) {  
//        	System.setProperty("net.sf.ehcache.enableShutdownHook","true");
//            ehCache= new EhcacheUtil(path);  
//        }  
//        return ehCache;  
//    }  
  
    public void put(String key, Object value) {  
        Cache cache = manager.getCache(CACHENAME);  
        Element element = new Element(key, value);  
        cache.put(element); 
        cache.flush();
    }  
  
    public <T> T get(String key) {  
        Cache cache = manager.getCache(CACHENAME);  
        Element element = cache.get(key);  
        return element == null ? null :(T) element.getObjectValue();  
    }  
  
    public Cache get() {  
        return manager.getCache(CACHENAME);  
    }  
  
    public void remove(String key) {  
        Cache cache = manager.getCache(CACHENAME);  
        cache.remove(key);  
    }  
  
}  