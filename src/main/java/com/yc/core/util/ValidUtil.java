package com.yc.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {
	public static boolean isMobile(String mobiles){  
		Pattern p = Pattern.compile("^[1][3,5,7,8][0-9]{9}$");  
		Matcher m = p.matcher(mobiles);  
		return m.matches();
	} 
	//验证注册密码
	public static boolean validPassword(String password){  
		if(password==null||password.length()>16||password.length()<6){
			return false;
		}
		return true;
	}  
}

