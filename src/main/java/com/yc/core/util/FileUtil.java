package com.yc.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
	public static String read(File file) throws Exception{
		String line,result="";
		BufferedReader br=null;
		try{
			br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			line = null;
//			while ((line = br.readLine()) != null) {
//				result+=line;
//			}
//			br.close();
//			fr=new FileReader(file);
			char[] cbuf = new char[32];  
            //用于保存实际读取的字符数  
            int hasRead = 0;  
            //使用循环读取数据  
            while((hasRead = br.read(cbuf)) > 0){  
                //将字符数组转化为字符串输出  
            	result+=new String(cbuf,0,hasRead);  
            }  
		}catch(Exception e){
			throw e;
		}finally{
			if(br!=null){
				br.close();
			}
		}
		return result;
	}
	public static String stream2String(InputStream is) {   
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
        StringBuilder sb = new StringBuilder();   
        String line = null;   
        try {   
            while ((line = reader.readLine()) != null) {   
                sb.append(line);   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                is.close();   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
        return sb.toString();   
	}   
}
