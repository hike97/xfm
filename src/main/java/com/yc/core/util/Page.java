package com.yc.core.util;

import java.util.List;

/**
 * 顾泽 2008.12.26
 * */
public class Page{
	private int maxRow;
	private int offSet;
	private int curPage;
	private int prePage;
	private int nextPage;
	private int	totPage;
	private int position;
	private String pageString;
	private List<?> list;
	/**
	 * maxRow行数
	 * offSet偏移
	 * curPage当前页
	 * */
	public Page(){
		
	}
	public Page(int maxRow,int offSet,int curPage){
		this.maxRow=maxRow;
		this.offSet=maxRow>0?offSet:0;
		this.curPage=curPage<=0?1:curPage;
		if(maxRow%offSet==0)
			this.totPage=maxRow/offSet;
		else
			this.totPage=maxRow/offSet+1;
		if(this.totPage<=0)
			this.totPage=1;
		if(curPage>=this.totPage)
			this.curPage=this.totPage;
//		if(curPage<=0)
//			this.curPage=1;
		this.position=(this.curPage-1)*offSet;
		prePage=this.curPage-1>0?this.curPage-1:1;
		nextPage=this.curPage+1>totPage?totPage:this.curPage+1;
	}
	public String getSimplePageStr(String tagA){
		tagA="<a href=\""+tagA+"&page=";
		String pagestr=curPage==1?"":tagA+(curPage-1)+"\">[上一页]</a>&nbsp;&nbsp;&nbsp;";
		pagestr+="共"+totPage +"页&nbsp;&nbsp;&nbsp;";
		pagestr+="当前第 "+curPage +" 页&nbsp;&nbsp;&nbsp;";
		pagestr+="记录共 "+maxRow+" 条&nbsp;&nbsp;&nbsp;";
		pagestr+=curPage==totPage?"":tagA+(curPage+1)+"\">[下一页]</a> ";
		return pagestr;
	}
	/**
	 * 分页下拉框
	 * @param tagA：要转向的链接
	 * #page替换标志
	 * */
	public String getPageSelect(String tagA){
		String selectpage="";
		if(this.getTotPage()>1){
			selectpage="<select onchange=\"javascript:window.location='"
				+tagA+";\">";//'+this.value
			selectpage=selectpage.replace("#page", "'+this.value");
			for(int i=1;i<=this.getTotPage();i++){
				selectpage+="<option value=\""+i+"\"";
				if(i==this.getCurPage()){
					selectpage+=" selected=\"selected\"";
				}
				selectpage+=">第"+i+"页"+"</option>";
			}
			selectpage+="</select>";
		}
		return selectpage;
	}
	
	public String getCustomPageR(String tagA){
		return getCustomPageR(tagA, true, true, true, 8);
	}
	public String getCustomPageR(String tagA,int pageNum){
		return getCustomPageR(tagA, true, true, true, pageNum);
	}
	/**
	 * 自定义分页替换模式
	 * @param isWithSE：包含尾页与首页;
	 * @param isWithD：包描述;
	 * @param isWithP：包含页码;
	 * @param pageNum: 页码长度;
	 * #page替换标志
	 * */
	public String getCustomPageR(String tagA,boolean isWithSE,boolean isWithD,boolean isWithP,int pageNum){
		tagA="<a href=\""+tagA+"\">";
		String pagestr="";
		if(isWithD){
			pagestr+="共<b>"+totPage+"</b>页&nbsp;";
			if(!isWithP)pagestr+="当前第 "+curPage +" 页&nbsp;&nbsp;";
			pagestr+="记录共<b>"+maxRow+"</b>条&nbsp;&nbsp;";
		}
		if(isWithSE){
			pagestr += (curPage==1?"":(tagA.replace("#page", "1")+"[首页]</a>&nbsp;"));
		}
		pagestr += (curPage==1?"":tagA.replace("#page", (curPage-1)+"")+"[上一页]</a>&nbsp;");
		int pageNumMid=pageNum/2;
		int k=pageNum%2==0?-1:-2;
		if(totPage-curPage<=pageNumMid)k=-1;
		if(isWithP){
			if(totPage>1){
				if(totPage<=pageNum||curPage<=pageNumMid+1){
					for(int i=1;i<=pageNum;i++){
						if(i>totPage)break;
						if(curPage==i){
							pagestr+="&nbsp;"+i+"&nbsp;";
							continue;
						}
						pagestr+=tagA.replace("#page", i+"")+"["+i+"]</a>&nbsp;";
					}
				}
				//尾页
				else if(totPage-curPage<=pageNumMid) {
					for(int i=totPage-pageNum-k;i<=totPage;i++){
						if(i>totPage)break;
						if(curPage==i){
							pagestr+="&nbsp;"+i+"&nbsp;";
							continue;
						}
						pagestr+=tagA.replace("#page", i+"")+"["+i+"]</a>&nbsp;";
					}
				}
				
				else {
					for(int i=curPage-pageNumMid;i<=curPage+pageNumMid;i++){
						if(curPage==i){
							pagestr+="&nbsp;"+i+"&nbsp;";
							continue;
						}
						pagestr+=tagA.replace("#page", i+"")+"["+i+"]</a>&nbsp;";
					}
				}
			}
			//pagestr+=curPage==totPage?"":tagA+(curPage+1)+"\">&gt;</a>&nbsp;&nbsp;"+tagA+totPage+"\">&gt;&gt;</a> ";
			
		}
		pagestr+=curPage==totPage?"":tagA.replace("#page", (curPage+1)+"")+"[下一页]</a>&nbsp;";
		if(isWithSE){
			pagestr += curPage==totPage?"":tagA.replace("#page", totPage+"")+"[尾页]</a> ";
		}
		return pagestr;
	}
	/**
	 * 自定义分页替换模式
	 * @param isWithSE：包含尾页与首页;
	 * @param isWithD：包描述;
	 * @param isWithP：包含页码;
	 * @param pageNum: 页码长度;
	 * #page替换标志
	 * */
	public String getPageForBootstrap(String tagA,boolean isWithSE,boolean isWithD,boolean isWithP,int pageNum){
		tagA="<a href=\""+tagA+"\">";
		String pagestr="";
		if(isWithD){
			pagestr+="<li><span>共"+totPage+"页"+maxRow+"条记录</span></li>";
//			if(!isWithP)pagestr+="<li><span>当前第 "+curPage +" 页</span></a></li>";
//			pagestr+="<li><span>记录共"+maxRow+"条</span></a></li>";
		}
		if(isWithSE){
			pagestr += (curPage==1?"":"<li>"+(tagA.replace("#page", "1")+"<span>首页</span></a></li>"));
		}
		pagestr += (curPage==1?"":"<li>"+tagA.replace("#page", (curPage-1)+"")+"<span>上一页</span></a></li>");
		int pageNumMid=pageNum/2;
		int k=pageNum%2==0?-1:-2;
		if(totPage-curPage<=pageNumMid)k=-1;
		if(isWithP){
			if(totPage>1){
				if(totPage<=pageNum||curPage<=pageNumMid+1){
					for(int i=1;i<=pageNum;i++){
						if(i>totPage)break;
						if(curPage==i){
							pagestr+="<li class=\"active\">"+tagA.replace("#page", i+"")+i+"</a></li>";
							continue;
						}
						pagestr+="<li>"+tagA.replace("#page", i+"")+i+"</a></li>";
					}
				}
				//尾页
				else if(totPage-curPage<=pageNumMid) {
					for(int i=totPage-pageNum-k;i<=totPage;i++){
						if(i>totPage)break;
						if(curPage==i){
							pagestr+="<li class=\"active\">"+tagA.replace("#page", i+"")+i+"</a></li>";
							continue;
						}
						pagestr+="<li>"+tagA.replace("#page", i+"")+i+"</a></li>";
					}
				}
				
				else {
					for(int i=curPage-pageNumMid;i<=curPage+pageNumMid;i++){
						if(curPage==i){
							pagestr+="<li class=\"active\">"+tagA.replace("#page", i+"")+i+"</a></li>";
							continue;
						}
						pagestr+="<li>"+tagA.replace("#page", i+"")+i+"</a></li>";
					}
				}
			}
		}
		pagestr+=curPage==totPage?"":"<li>"+tagA.replace("#page", (curPage+1)+"")+"<span>下一页</span></a></li>";
		if(isWithSE){
			pagestr += curPage==totPage?"":"<li>"+tagA.replace("#page", totPage+"")+"<span>尾页</span></a></li>";
		}
		return pagestr;
	}
	/**
	 * 自定义分页
	 * @param isWithSE：包含尾页与首页;
	 * @param isWithD：包描述;
	 * @param isWithP：包含页码;
	 * */
	public String getCustomPageStr(String tagA,boolean isWithSE,boolean isWithD,boolean isWithP){
		tagA="<a href=\""+tagA+"&page=";
		String pagestr="";
		if(isWithD){
			pagestr+="共"+totPage +"页&nbsp;&nbsp;";
			if(!isWithP)pagestr+="当前第 "+curPage +" 页&nbsp;&nbsp;";
			pagestr+="记录共 "+maxRow+" 条&nbsp;&nbsp;";
		}
		if(isWithSE){
			pagestr += (curPage==1?"":(tagA+"1\">[首页]</a>&nbsp;"));
		}
		pagestr += (curPage==1?"":tagA+(curPage-1)+"\">[上一页]</a>&nbsp;");
		
		if(isWithP){
			if(totPage>1){
				if(totPage<=8||curPage<=5){
					for(int i=1;i<=8;i++){
						if(i>totPage)break;
						if(curPage==i){
							pagestr+="&nbsp;"+i+"&nbsp;";
							continue;
						}
						pagestr+=tagA+i+"\">["+i+"]</a>&nbsp;";
					}
				}else if(totPage-curPage<=4) {
					for(int i=totPage-7;i<=totPage;i++){
						if(i>totPage)break;
						if(curPage==i){
							pagestr+="&nbsp;"+i+"&nbsp;";
							continue;
						}
						pagestr+=tagA+i+"\">["+i+"]</a>&nbsp;";
					}
				}
				
				else {
					for(int i=curPage-4;i<=curPage+4;i++){
						if(curPage==i){
							pagestr+="&nbsp;"+i+"&nbsp;";
							continue;
						}
						pagestr+=tagA+i+"\">["+i+"]</a>&nbsp;";
					}
				}
			}
			//pagestr+=curPage==totPage?"":tagA+(curPage+1)+"\">&gt;</a>&nbsp;&nbsp;"+tagA+totPage+"\">&gt;&gt;</a> ";
			
		}
		pagestr+=curPage==totPage?"":tagA+(curPage+1)+"\">[下一页]</a>&nbsp;";
		if(isWithSE){
			pagestr += curPage==totPage?"":(tagA+totPage+"\">[尾页]</a> ");
		}
		return pagestr;
	}
	public String getPagestr(String tagA){
		tagA="<a href=\""+tagA+"&page=";
		String pagestr="";
		//pagestr="共"+totPage +"页&nbsp;&nbsp;&nbsp;&nbsp;";
		//pagestr+="当前第 "+curPage +" 页 &nbsp;&nbsp;&nbsp;&nbsp;";
		//pagestr+="记录共 "+maxRow+" 条&nbsp;&nbsp;&nbsp;&nbsp;";
		
		pagestr+=curPage==1?"":tagA+1+"\">&lt&lt;</a>&nbsp;&nbsp;"+tagA+(curPage-1)+"\">&lt;</a>&nbsp;&nbsp;";
		if(totPage>1){
			if(totPage<=8||curPage<=5){
				for(int i=1;i<=8;i++){
					if(i>totPage)break;
					if(curPage==i){
						pagestr+="&nbsp;&nbsp;"+i+"&nbsp;&nbsp;";
						continue;
					}
					pagestr+=tagA+i+"\">["+i+"]</a>&nbsp;&nbsp;";
				}
			}else if(totPage-curPage<=4) {
				for(int i=totPage-7;i<=totPage;i++){
					if(i>totPage)break;
					if(curPage==i){
						pagestr+="&nbsp;&nbsp;"+i+"&nbsp;&nbsp;";
						continue;
					}
					pagestr+=tagA+i+"\">["+i+"]</a>&nbsp;&nbsp;";
				}
			}
			
			else {
				for(int i=curPage-4;i<=curPage+4;i++){
					if(curPage==i){
						pagestr+="&nbsp;&nbsp;"+i+"&nbsp;&nbsp;";
						continue;
					}
					pagestr+=tagA+i+"\">["+i+"]</a>&nbsp;&nbsp;";
				}
			}
		}
		pagestr+=curPage==totPage?"":tagA+(curPage+1)+"\">&gt;</a>&nbsp;&nbsp;"+tagA+totPage+"\">&gt;&gt;</a> ";
		return pagestr;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getMaxRow() {
		return maxRow;
	}
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}
	public int getOffSet() {
		return offSet;
	}
	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}
	public int getTotPage() {
		return totPage;
	}
	public void setTotPage(int totPage) {
		this.totPage = totPage;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public String getPageString() {
		return pageString;
	}
	public void setPageString(String pageString) {
		this.pageString = pageString;
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> getList() {
		return (List<T>) list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	
}
