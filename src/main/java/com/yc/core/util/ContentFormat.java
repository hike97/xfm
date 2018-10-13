package com.yc.core.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @description
 * @version 1.0
 * @author 顾泽
 * @update 2012-3-17 下午5:20:38 
 */

public class ContentFormat {
	
	public static String trim(String s){
		if(s!=null) s=s.trim();
		if(s==null||s.equals("")) s=null;
		return s;
	}
	//处理font标记
	/**
	 * @param content
	 * @return
	 * 
	 * @description
	 * @version 1.0
	 * @author 顾泽
	 * @update 2012-3-17 下午5:20:24
	 */
	public static String fontSizeFormat(String content) {
		String regex = "<[^>]*(</a>)";
		content = content.replaceAll(regex, "<font>");
		Matcher m = Pattern.compile(regex).matcher(content);
		String str = "";
		while (m.find()) {
			str = m.group();
			String str2 = str
					.replaceAll(
							"[Ss][Ii][Zz][Ee][\\s]*=[\\s]*[\"']?[\\s]*[0-9]*[\\s]*[\"']?",
							"");
			content = content.replace(str, str2);
		}
		return content;
	}
	public static boolean isRealPath(String path){
		String regex="^[A-Z]{1}:{1}/{1}.+";
		return path.matches(regex);
	}
	/**
	 *匹配范围参数
	 *如：s="+ 题目编号：001001";field="题目编号：" 返回 "001001"
	 * */
	public static String parseRange(String s,String field){
		return parseRange(s, field, false);
	}
	/**
	 *匹配范围参数
	 *如：s="> 题目编号：001001";field="> 题目编号：" 返回 "001001"
	 * */
	public static String parseRange(String s,String field,boolean withBreak){
		String regex;
		if(withBreak){//(?<=title=\"淘金币).+?(?=<del>)
			regex = "("+field+")[^#####]+";
		}else {
			regex = "("+field+").+";
		}
		Matcher m =Pattern.compile(regex).matcher(s);
		if (m.find()) {
			return m.group().replaceAll("^"+field, "").trim();
		}
		return null;
	}
	public static Integer getStep(Integer max,int maxRow,int minRow){
		Integer step=0;
		if(max<=maxRow){
			return 1;
		}
		step=max/maxRow;
		Double log10=Math.log10(step.doubleValue());
		if(log10>1){
			Integer stepArg5=(Integer)log10.intValue()-1;
			stepArg5=5*((Double)Math.pow(10, stepArg5.doubleValue())).intValue();
			Integer stepArg10=(Integer)log10.intValue()-1;
			stepArg10=10*((Double)Math.pow(10, stepArg10.doubleValue())).intValue();
			if(step%stepArg5>step%stepArg10){
				step=step/stepArg10*stepArg10;
			}else{
				step=step/stepArg5*stepArg5;
			}
		}
		return step;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {

		System.out.println(isRealPath("/develop/tomcat6/webapps/dscms2.1/"));
		
	}
	/**
	 *img标记后加br
	 * */
	public static String addBrAfterImg(String content) {
		String regex = "<[\\s]*([iI][mM][gG])[^>]*>";
		Matcher m =Pattern.compile(regex).matcher(content);
		//String regex = "<[^>]*>";
		while (m.find()) {
			content =content.replaceAll(m.group(),"<br>"+m.group()+"<br>");
		}
		
		return content;
	}
	/**
	 * 只有br img 标记
	 * */
	public static String fontFormat(String content) {
		content =content.replaceAll("<[/]?[\\s]*[^(brBRimgIMG)][^>]+>", "");
		content =content.replaceAll("<[\\s]*([iI][mM][gG])", "<br><img");
		content=content.replaceAll("\\r\\n", "<br>");
		//content=content.replaceAll(" ", "&nbsp;");
		return content;
	}
	/**
	 * lucene转义
	 * */
	public static String filterWord(String content){
		String[] t={"\\","+","-","&","|","!","(",")","*","{","}","[","]","^","~","?",":"};
		content=content==null?"":content;
		for(int i=0;i<t.length;i++){
			if(t[i].indexOf("\\")>=0 && content.indexOf(t[i])>=0){
				content=content.replaceAll("\\"+t[i], "\\\\\\"+t[i]);
			}else if(content.indexOf(t[i])>=0){
				content=content.replaceAll("\\"+t[i], "\\\\"+t[i]);
			}
		}
		return content;
	}
	/**
	 * 去除html标记
	 * */
	public static String withOutTag(String s){
		s=s.replaceAll("</?[^>]+>","");
		s=s.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
		return s;
	}
	/**
	 *将非字母数字转换为0
	 * */
	public static String forUrl(String content){
		return content.replaceAll("[^a-zA-Z0-9]+", "0");
	}
	/**
	 * 中文英文数字标点
	 * */
	public static String legalWord(String content){
		return content.replaceAll("[^a-zA-Z0-9\u4e00-\u9fa5\\s\\pP]*", "");
	}
	/**
	 * 中文英文数字
	 * */
	public static String onlyWord(String content){
		return content.replaceAll("[^a-zA-Z0-9\u4e00-\u9fa5\\s]*", "");
	}
	/**
	 *中文英文
	 * */
	public static String onlyWordNoNumber(String content){
		return content.replaceAll("[^a-zA-Z\u4e00-\u9fa5\\s]*", "");
	}
	public static String contentFormat(String content) {
		return content.replaceAll("\\r\\n", "<br>");
	}
	/**
	 *中文
	 * */
	public static String trunSpeace(String content){
		content =content.replaceAll("^[^\u4E00-\u9FA5]*", "");
		return content;
	}
	/**
	 * str:需要截取的字符串
	 * byteLength:要截取的长度
	 * isFillNeeded:是否需要填充
	 * * */
	public static String truncateString(String str, int byteLength,
			boolean isFillNeeded) {
		try {
			if (str.getBytes().length < byteLength) {
				if (isFillNeeded) {
					int spaceNeeded = byteLength - str.getBytes().length;
					StringBuffer sb = new StringBuffer(byteLength);
					sb.append(str);
					for (int i = 0; i < spaceNeeded; i++) {
						sb.append(" ");
					}
					return sb.toString();
				} else {
					return str;
				}
			} else {
				while (str.getBytes().length > byteLength) {
					str = str.substring(0, str.length() - 1);
				}
				StringBuffer sb = new StringBuffer(byteLength);
				sb.append(str);
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 字符串比较
	 * @param s要比较的字符串
	 * @param pattern匹配模型
	 * */
	public boolean compare(String s,String pattern){
		if(pattern.endsWith("*")){
			pattern=pattern.substring(0, pattern.length()-1);
			if(s.startsWith(pattern))return true;
			else return false;
		}else{
			return s.equals(pattern);
		}
	}
	/**
	 * 文本转时间
	 * */
//	public static String StringToDate(String ){
//		  SimpleDateFormat x=new SimpleDateFormat("yyyyMMddHHmm");
//		  String s=x.p
//		  return s;
//}
	/**
	 * 转换时间格式
	 * */
	public static String DateToString(Date date){
		  SimpleDateFormat x=new SimpleDateFormat("yyyyMMddHHmm");
		  String s=x.format(date);
		  return s;
	}
	
	/**
	 * @param date
	 * @param format
	 * @return
	 * 
	 * @description 用一句话说明这个方法做什么
	 * @version 1.0
	 * @author guze
	 * @param 
	 * @update 2012-3-17 下午5:06:13
	 */
	public static String DateToString(Date date,String format){
		  SimpleDateFormat x=new SimpleDateFormat(format);
		  String s=x.format(date);
		  return s;
	}
	/**
	 * @param source
	 * @param format
	 * @return
	 * @throws Exception
	 * 
	 * @description
	 * @version 1.0
	 * @author 顾泽
	 * @update 2012-3-17 下午5:19:19
	 */
	public static Date StringToDate(String source,String format) throws Exception{
		  SimpleDateFormat x=new SimpleDateFormat(format);
		  return  x.parse(source);
	}
}
