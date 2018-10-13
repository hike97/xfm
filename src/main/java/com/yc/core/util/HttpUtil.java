package com.yc.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpUtil {

	public static void main(String[] args) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String kw="综合";
		kw = URLEncoder.encode(kw,"UTF-8");
		//timeType2一周 3一个月
		String url="http://search.ccgp.gov.cn/bxsearch?searchtype=1&page_index=1&start_time=&end_time=&timeType=3&searchparam=&searchchannel=0&dbselect=bidx&"
				+ "kw="+kw+"&bidSort=0&pinMu=0&bidType=0&buyerName=&projectId=&displayZone=&zoneId=&agentName=";
		
//		System.out.println(url);
		String rs = doGet(url,null);
//		System.out.println(rs);
		Document doc=Jsoup.parse(rs);
		Elements elements = doc.getElementsByAttributeValue("class", "vT-srch-result-list-bid");
		if(elements == null || elements.size() == 0){
			return;
		}
		elements = elements.get(0).getElementsByTag("li");
		for(Element element:elements){
			//标题
			Elements tagAs = element.getElementsByTag("a");
			Element tagP = element.getElementsByTag("p").get(0);
			Element span = element.getElementsByTag("span").get(0);
			Element strong = element.getElementsByTag("strong").get(0);
			String[] spanHtml = span.html().split("<br>")[0].split("\\|");
			
			String title = tagAs.get(0).html().trim();
			String href = tagAs.get(0).attr("href");
			String area = tagAs.get(1).html().trim();
			String content = tagP.html().trim();
			String type = strong.html().trim();
			String dateStr = spanHtml[0].trim();
			String purchaser = spanHtml[1].replace("采购人：", "") .trim();
			String agent = spanHtml[2].replace("代理机构：", "") .trim();
			Date  date = DateUtil.parseDate(dateStr, "yyyy.MM.dd HH:mm:ss");
			System.out.println("date："+ date);
			System.out.println("地区："+ area);
			System.out.println("内容："+ content);
			System.out.println("类型："+ type);
			System.out.println("日期："+ date);
			System.out.println("采购人："+ purchaser);
			System.out.println("代理机构："+ agent);
			
		}
		
		
	}
	  public static String doGet(String url,Map<String,String> params){
			 return doMethod(url,params,1);
		  }
	  public static String doPost(String url,String json){
		  HttpClient client = new HttpClient();
		  StringBuilder responseBody = new StringBuilder();
		  InputStream ins = null;
			try {
				PostMethod method=new PostMethod(url);
//				method.addRequestHeader("Content-Type", "application/json");  //.addHeader("Content-Type", "text/xml"); 
//				 StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
				RequestEntity requestEntity=new StringRequestEntity(json, "application/json", "UTF-8");
				method.setRequestEntity(requestEntity);
				int statusCode = client.executeMethod(method);
				if (statusCode == HttpStatus.SC_OK) {
					ins = method.getResponseBodyAsStream();
					byte[] b = new byte[1024];
					int r_len = 0;
					while ((r_len = ins.read(b)) > 0) {
						responseBody.append(new String(b, 0, r_len, "UTF-8"));
					}
				}
				} catch (HttpException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
			}
			return responseBody.toString();
		  }
		public static String doMethod(String url,Map<String,String> params,int type){
			HttpClient client = new HttpClient();
			StringBuilder sb = new StringBuilder(url);
			StringBuilder responseBody = new StringBuilder();
			InputStream ins = null;
			try {
				HttpMethodBase method ;
				if(type==1){
					if(params!=null && params.size() > 0){
						sb.append("?");
						for (Entry<String, String>map: params.entrySet()) {
							String pramName = map.getKey();
							String values = map.getValue();
							sb.append(pramName+"="+values);
							sb.append("&");
						}
					}
					method=new GetMethod(sb.toString().substring(0, sb.length()));
				}else {
					method=new PostMethod(url);
					for(String s:params.keySet()){
						((PostMethod) method).addParameter(s,params.get(s));
					}
					
				}
				 
				int statusCode = client.executeMethod(method);
				if (statusCode == HttpStatus.SC_OK) {
					ins = method.getResponseBodyAsStream();
					 BufferedReader br = new BufferedReader(new InputStreamReader(ins,"utf-8"));
			            String temp = "";
			            while ((temp = br.readLine()) != null) {
			            	responseBody.append(temp+"\n");
			            }
//					byte[] b = new byte[1024];
//					char[] c = new char[1024];
//					int r_len = 0;
//					while ((r_len = ins.read() ) > 0) {
//						responseBody.append(new String(c, 0, r_len, "UTF-8"));
//					}
				}
				} catch (HttpException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
			}
			return responseBody.toString();
		}
}
