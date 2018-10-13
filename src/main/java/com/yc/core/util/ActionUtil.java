package com.yc.core.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yc.xfm.action.BaseAction;

public class ActionUtil {
	public static String getRandomCode(){
		Double d=Math.random();
		String code= String.valueOf(d).substring(2,8);
		code = StringUtils.leftPad(code, 6, "0");
		return code;
	}
	
	public static void main(String[] args) throws Exception {
		Calendar c = Calendar.getInstance();
		int i = c.get(Calendar.DAY_OF_MONTH);
		int i2= c.getActualMinimum(Calendar.DAY_OF_MONTH);
		System.out.println(i+" "+i2);
	}
	public enum ContentType {
		HTML("text/html;charset=UTF-8"),
		TEXT("text/plain;charset=UTF-8"),
		IMAGE("image/jpeg;charset=UTF-8"),
		XML("text/xml;charset=UTF-8"),
		JSON("application/json;charset=UTF-8");
		String typeStr;
		ContentType(String typeStr){
			this.typeStr=typeStr;
		}
		@Override
		public String toString(){
			return typeStr;
		}
	}
	
	public static void createThumb(String sourcePath,int maxWidth,
			int maxHeight,String result) throws Exception {
		// fileExtNmae是图片的格式 gif jpg 或png
		String fix=sourcePath.substring(sourcePath.lastIndexOf(".")+1);
		double Ratio = 0.0;
		File F = new File(sourcePath);
		if (!F.isFile())
			throw new Exception(F
					+ " is not image file error in CreateThumbnail!");
		File ThF = new File(result);
		BufferedImage Bi = ImageIO.read(F);
		// 假设图片宽 高 最大为300 3000
		Image Itemp = Bi.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
		if ((Bi.getHeight() > maxHeight) || (Bi.getWidth() > maxWidth)) {
			if (Bi.getHeight()*maxWidth>maxHeight*Bi.getWidth())
				Ratio =(double)maxHeight/ Bi.getHeight();
			else
				Ratio = (double)maxWidth / Bi.getWidth();
		}
		if(Ratio==0)return;
		AffineTransformOp op = new AffineTransformOp(AffineTransform
				.getScaleInstance(Ratio, Ratio), null);
		Itemp = op.filter(Bi, null);
		try {
			ImageIO.write((BufferedImage) Itemp, fix, ThF);
		} catch (Exception ex) {
			throw new Exception(" ImageIo.write error in CreatThum.: "
					+ ex.getMessage());
		}
	}

	public static boolean parseUrl(String uri,String s){
		if(s.endsWith("*")){
			s=s.substring(0, s.length()-1);
			if(uri.startsWith(s)){
				return true;
			}
		}else{
			if(uri.equals(s)){
				return true;
			}
		}
		return false;
	}
	public static String uploadFile(MultipartHttpServletRequest request,
			String sourcePath, long size) throws Exception {
		ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
		sfu.setHeaderEncoding("UFT-8");
		String sourceName = "";
		String extName = "";
		String sfileName = "";
		if(size==0l)
			size = 500000*1000;
		for (String s:request.getFileMap().keySet()) {
			MultipartFile  fi =request.getFileMap().get(s);
			sourceName = fi.getOriginalFilename();
			if (sourceName == null || "".equals(sourceName.trim())) {
				continue;
			}
			long fileSize=fi.getSize();
			if(fileSize>size){
				throw new Exception("文件大小超过"+size);
			}
			if (sourceName.lastIndexOf(".") >= 0) {
				// 扩展名
				extName = sourceName.substring(sourceName
						.lastIndexOf("."));
			}
			// UUID + 扩展名
			sfileName = java.util.UUID.randomUUID().toString() + extName;
			File saveSourceFile = new File(sourcePath + sfileName);
			FileCopyUtils.copy(fi.getBytes(),saveSourceFile);
			sourcePath=saveSourceFile.getAbsolutePath();
		}
		return sourcePath;
	}
	/** 
	* @date 2012-8-1 下午1:35:15
	* @author guze
	* @Description: 将实体集合按属性转换成数组
	* @param list 要转换的list
	* @param property 要转换的list内的实体属性
	* @param objs	转换的数组
	* @throws Exception void 
	* @throws 
	*/ 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> void listToArray(List  list,String property,T[] objs) throws Exception{
		if(list==null||list.size()==0) 
			return;
		Class classType = list.get(0).getClass();
		String firstWord=property.substring(0, 1).toUpperCase();
		String methodName="get"+firstWord+property.substring(1);
		Method getMethod = classType.getMethod(methodName,new Class[]{});
		try {
			for(int i=0;i<list.size();i++){
				objs[i]=(T)getMethod.invoke(list.get(i));
			}
		} catch (Exception e) {
			throw e;
		}  
	}
	/*
	 * 提交表单名必须与字段名一致
	 * 只支持String、Integer、Long、Float、Date
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  static <T> void setProperty(HttpServletRequest request,T entity) throws Exception{
		Class classType = entity.getClass();
		Field[] fields= classType.getDeclaredFields();
		for(Field field:fields){
			String s=request.getParameter(field.getName());
			if(s==null||s.equals("")){
				continue;
			}
			Class propertyClass = field.getType();
			String firstWord=field.getName().substring(0, 1).toUpperCase();
			String methodName="set"+firstWord+field.getName().substring(1);
			Method getMethod;
			try {
				getMethod = classType.getMethod(methodName,propertyClass);
				if(propertyClass==String.class){
					getMethod.invoke(entity,s);
				}else if(propertyClass==Integer.class){
					getMethod.invoke(entity,Integer.parseInt(s));
				}else if(propertyClass==Long.class){
					getMethod.invoke(entity,Long.parseLong(s));
				}else if(propertyClass==Float.class){
					getMethod.invoke(entity,Float.parseFloat(s));
				}else if(propertyClass==Date.class){
					getMethod.invoke(entity,parseDate(s));
				}else{
					continue;
				}
			}catch (NoSuchMethodException e) {
				System.out.println("NoSuchMethod:"+methodName);
				continue;
			} catch (Exception e) {
				throw e;
			}  
			
		}
	}
	/**
	* @author:顾泽	
	* @date：2012-10-23下午3:33:06
	* @Title: getCookieValue
	* @Description: 获取cookie值
	* @param request
	* @param CookieName
	* @return String    返回类型
	* @throws
	*/
	public static String getCookieValue(HttpServletRequest request,String CookieName){
		String CookieValue=null;
		Cookie[] cookies =request.getCookies();
		if(cookies!=null)
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(CookieName)){
				CookieValue= cookie.getValue();
			}
		}
		return CookieValue;
	}
	public static void setCookieValue(HttpServletResponse response,
			String CookieName,String CookieValue){
		Cookie cookie=new Cookie(CookieName, CookieValue);
		cookie.setMaxAge(Integer.MAX_VALUE);   
		cookie.setPath("/");
		response.addCookie(cookie); 
	}
	public static void removeCookie(HttpServletResponse response,
			String CookieName){
		Cookie cookie=new Cookie(CookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie); 
	}
	/** action 跳转工具
	 **/
	public static void reDirect(HttpServletResponse response,String url){
		reDirect(response,url,null,null);
	}
	public static void reDirect(HttpServletResponse response,String url,String message){
		reDirect(response,url,message,null);
	}
	/**
	 * @param response
	 * @param url
	 * @param message
	 * @param script
	 */
	public static void reDirect(HttpServletResponse response,String url,String message,String script){
		response.setContentType("text/html;charset=UTF-8"); 
		String html="<script type=\"text/javascript\">";
		if(message!=null&&!message.equals("")){
			html+="alert('"+message+"');";
		}
		if(script!=null){
			html+=script;
		}
		if(url!=null&&!url.equals("")){
			html+="window.location='"+url+"';";
		}
		html+="</script>";
		try {
			response.getWriter().print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void backDirect(HttpServletResponse response,String message){
		String script="history.back();";
		reDirect(response,null,message,script);
	}
	/*** @deprecated 用 {@link ResponseBody} 代替*/
	@Deprecated
	public static void print(HttpServletResponse response,
			String message) throws IOException{
		print(response,message,ContentType.HTML);
	}
	/*** @deprecated 用 {@link ResponseBody} 代替*/
	public static void print(HttpServletResponse response,
			String message,ContentType contentType) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType.toString()); 
		response.getWriter().print(message);
	}
	/*** @deprecated  用 {@link BaseAction#parseBoolean(String)} 代替**/
	public static Boolean parseBoolean(String booleanStr){
		return  parseBoolean(booleanStr,false);
	}
	/*** @deprecated  用 {@link BaseAction#parseBoolean(String, Boolean)} 代替**/
	public static Boolean parseBoolean(String booleanStr,Boolean defaut){
		if(booleanStr==null)return defaut;
		if("".equals(booleanStr))return defaut;
		if("null".equals(booleanStr))return defaut;
		if("NULL".equals(booleanStr))return defaut;
		if(booleanStr.equals("true")||booleanStr.equals("1"))return true;
		if(booleanStr.equals("false")||booleanStr.equals("0"))return false;
		return defaut;
	}
	/*** @deprecated  用 {@link BaseAction#parseLong(String)} 代替**/
	public static Long parseLong(String longStr){
		if(longStr==null||longStr.equals(""))return 0l;
		return Long.parseLong(longStr);
	}
	/**
	 * @param longStr
	 * @param defaut
	 * @return
	 * 
	 * @description
	 * @version 1.0
	 * @author 顾泽
	 * @update 2012-3-30 下午2:28:04
	 * @deprecated  用 {@link BaseAction#parseLong(String, Long)} 代替**/
	public static Long parseLong(String longStr,Long defaut){
		if(longStr==null||longStr.equals(""))return defaut;
		return Long.parseLong(longStr);
	}
	/*** @deprecated  用 {@link BaseAction#parseInt(String)} 代替**/
	public static Integer parseInt(String intStr){
		if(intStr==null||intStr.equals(""))return 0;
		return Integer.parseInt(intStr);
	}
	/*** @deprecated  用 {@link BaseAction#parseInt(String, Integer)} 代替**/
	public static Integer parseInt(String intStr,Integer defaut){
		if(intStr==null||intStr.equals(""))return defaut;
		return Integer.parseInt(intStr);
	}
	/**
	* @author:顾泽	
	* @date：2013-12-25下午4:51:55
	* @Title: parseDate
	* @Description: 格式化日期
	* @param source 日期字符串
	* @param format 格式 如 yyyy-MM-dd
	* @param defaut 默认值 如 new Date()
	* @return
	* @throws Exception Date    返回类型
	* @deprecated  用 {@link BaseAction#parseDate(String, String, Date)} 代替**/
	public static Date parseDate(String source,String format,Date defaut) throws Exception{
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
	/*** @deprecated  用 {@link BaseAction#parseDate(String)} 代替**/
	public static Date parseDate(String source) throws Exception{
		return parseDate(source,null,null);
	}
	/*** @deprecated  用 {@link BaseAction#parseDate(String, String)} 代替**/
	public static Date parseDate(String source,String format) throws Exception{
		return parseDate(source,format,null);
	}
	//唐秀楠添加转Float方法
	/*** @deprecated  用 {@link BaseAction#parseFloat(String)} 代替**/
	public static Float parseFloat(String intStr){
		if(intStr==null||intStr.equals(""))return (float) 0.0;
		return Float.parseFloat(intStr);
	}
	/*** @deprecated  用 {@link BaseAction#parseFloat(String, Float)} 代替**/
	public static Float parseFloat(String intStr,Float defaut){
		if(intStr==null||intStr.equals(""))return defaut;
		return Float.parseFloat(intStr);
	}
	/*** @deprecated  用 {@link BaseAction#parseFloat(Double)} 代替**/
	public static Float parseFloat(Double inDouble){
		if(inDouble==null||!(inDouble>0))return (float) 0.0;
		return Float.parseFloat(String.valueOf(inDouble));
	}
	/*** @deprecated  用 {@link BaseAction#parseFloat(Double, Float)} 代替**/
	public static Float parseFloat(Double inDouble,Float defaut){
		if(inDouble==null||!(inDouble>0))return defaut;
		return Float.parseFloat(String.valueOf(inDouble));
	}


	public static Object getCellValue(Cell cell){
		Object cellValue = null;
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		switch (cell.getCellType()) {  
	        case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
	    			//判断是否为日期类型
	    		if(HSSFDateUtil.isCellDateFormatted(cell)){
	        		//用于转化为日期格式
	        		Date d = cell.getDateCellValue();
	        		cellValue = dateFormat2.format(d);
	        		cellValue = String.valueOf(cellValue).trim();
	    		}else{
	    			Double d=cell.getNumericCellValue();
	    			BigDecimal db = new BigDecimal(d);
//	    			DecimalFormat df=new DecimalFormat("#0.00");
//	    			BigDecimal db =df.format(d);
					cellValue=String.valueOf(db).trim();
	    		}  
	        	break;  
	        case HSSFCell.CELL_TYPE_STRING: // 字符串  
	        	cellValue=cell.getStringCellValue().trim();  
	            break;  
	        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean  
	        	cellValue=cell.getBooleanCellValue();  
	            break;  
//	        case HSSFCell.CELL_TYPE_FORMULA: // 公式  
//	        	cellValue=cell.getCellFormula();  
//	            break;  
	        case HSSFCell.CELL_TYPE_BLANK: // 空值  
	        	cellValue=null;
	            break;
	        default: cellValue=null;
	            break;
		}
		return cellValue;
	}
    public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        str = str.replace("-", "");
        return str; 
    }
}
