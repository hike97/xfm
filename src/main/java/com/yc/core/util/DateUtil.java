package com.yc.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * @author:顾泽	
	 * @date：2013-2-26上午10:33:30
	 * @Title: main
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param args void    返回类型
	 * @throws
	 */
	public static void main(String[] args)  throws Exception{
		System.out.println(endDayOfMonth(new Date()));
	}
	public static Date firstDayOfMonth(Date date) throws Exception {
		date= parseDate(dateToString(date, "yyyyMM"), "yyyyMM");
        return date;
	}
	public static Date endDayOfMonth(Date date) throws Exception {
		date= parseDate(dateToString(date, "yyyyMM"), "yyyyMM");
		date=dateAdd(date, Calendar.MONTH, 1);
		date=dateAdd(date, Calendar.DATE, -1);
        return date;
	}
	  public static int getMonth(Date start, Date end) {
	        if (start.after(end)) {
	            Date t = start;
	            start = end;
	            end = t;
	        }
	        Calendar startCalendar = Calendar.getInstance();
	        startCalendar.setTime(start);
	        Calendar endCalendar = Calendar.getInstance();
	        endCalendar.setTime(end);
	        Calendar temp = Calendar.getInstance();
	        temp.setTime(end);
	        temp.add(Calendar.DATE, 1);

	        int year = endCalendar.get(Calendar.YEAR)
	                - startCalendar.get(Calendar.YEAR);
	        int month = endCalendar.get(Calendar.MONTH)
	                - startCalendar.get(Calendar.MONTH);

	        if ((startCalendar.get(Calendar.DATE) == 1)
	                && (temp.get(Calendar.DATE) == 1)) {
	            return year * 12 + month + 1;
	        } else if ((startCalendar.get(Calendar.DATE) != 1)
	                && (temp.get(Calendar.DATE) == 1)) {
	            return year * 12 + month;
	        } else if ((startCalendar.get(Calendar.DATE) == 1)
	                && (temp.get(Calendar.DATE) != 1)) {
	            return year * 12 + month;
	        } else {
	            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
	        }
	    }



	public static Date parseDate(String source) throws Exception{
		String format;
		if(source.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
			format="yyyy-MM-dd";
		}else if(source.matches("[0-9]{4}")){
			format="yyyy";
		}else if(source.matches("[0-9]{4}-[0-9]{1,2}")){
			format="yyyy-MM";
		}else{
			format="yyyy-MM-dd HH:mm:ss";
		}
		return parseDate(source,format);
	}
	public static Date parseDate(String source,String format) throws Exception{
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		return dateFormat.parse(source);
	}
	public static String onlyNumber(String source){
		String result="";
		if(source.matches("[1-9]{1}[0-9]+")){
			result=source;
		}else{
			String regex = "[^0-9]+";
			String[] ss=source.split(regex);
			for(String s:ss){
				if(s.length()<2){
					s="0"+s;
				}
				result+=s;
			}
		}
		return result;
	}
	public static String dateToString(Date date,String format){
		  SimpleDateFormat x=new SimpleDateFormat(format);
		  String s=x.format(date);
		  return s;
	}
	
	/**
	 * 日期进行加减
	 * @param type Calendar.DATE
	 * @param offset 把日期往后增加.整数往后推,负数往前移动 
	 * @return
	 */
	public static Date dateAdd(Date date,int type,int offset){
	      Calendar   calendar   =   Calendar.getInstance();
	      calendar.setTime(date); 
	      calendar.add(type,offset);//把日期往后增加.整数往后推,负数往前移动 
		  return calendar.getTime();
	}
	
	/**
	 * 得到两个日期之间相差多少天
	 * @param firstDate
	 * @param secondDate
	 * @return 相差天数
	 * @author 冯立民
	 */
	public static int dateMinus(Date firstDate, Date secondDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String fir = sdf.format(firstDate);
		String sec = sdf.format(secondDate);
		int minus = DateUtil.dateMinus(fir, sec);
		return minus;
	}
	/**
	 * 得到两个日期之间相差多少天
	 * @param frontdate 相对比较小的日期 mm-dd
	 * @param afterdate  相对比较大的日期 mm-dd
	 * @return dateMinus
	 * @author 冯立民
	 */
	public static int dateMinus(String frontdate, String afterdate) {
		String[] front = frontdate.split("-");
		String[] after = afterdate.split("-");
		String frontMon = null;
		String frontDay = null;
		String aftertMon = null;
		String afterDay = null;
		frontMon = front[0].replaceAll("0", "");
		frontDay = front[1];
		if (Integer.parseInt(frontDay) < 10) {
			afterDay = after[1].replaceAll("0", "");
		} else {
			afterDay = after[1];
		}

		aftertMon = after[0];
		if (Integer.parseInt(aftertMon) < 10) {
			aftertMon = after[0].replaceAll("0", "");
		}

		afterDay = after[1];

		// 注释：30=3 的功能
		if (Integer.parseInt(afterDay) < 10) {
			afterDay = after[1].replaceAll("0", "");
		}
		Calendar cal = Calendar.getInstance();

		cal.set(Integer.parseInt(frontMon) - 1,Integer.parseInt(frontDay));
		Date tempd1 = cal.getTime();
		cal.set(Integer.parseInt(aftertMon) - 1,Integer.parseInt(afterDay));
		Date tempd2 = cal.getTime();
		long minus = tempd2.getTime() - tempd1.getTime();
		return (int) (minus / 60 / 60 / 24 / 1000);
	}
	
	/**
	 * 得到日期加减月份的结果
	 * @author:唐秀楠	
	 * @date:2016-2-16 
	 * @param date
	 * @param count
	 * @return 计算后的日期格式化结果
	 */
	public static String dateModified(String date, int count) {
	       	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
	        Date date1 = null;  
	        Date date2 = null;
	        try {  
	            date1 = sdf.parse(date);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        Calendar cl = Calendar.getInstance();  
	        cl.setTime(date1);  
	        cl.add(Calendar.MONTH, count);  
	        date2 = cl.getTime();
			return sdf.format(date2); 
	}
}
