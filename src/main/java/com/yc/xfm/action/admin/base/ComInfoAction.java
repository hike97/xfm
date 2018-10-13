package com.yc.xfm.action.admin.base;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yc.core.util.ConditionQuery;
import com.yc.core.util.Page;
import com.yc.xfm.action.BaseAction;
import com.yc.xfm.entity.base.ComInfo;
import com.yc.xfm.entity.base.Status;
import com.yc.xfm.entity.base.User;
import com.yc.xfm.service.base.ComInfoService;


@Controller
@RequestMapping("/admin/base/comInfo_")
public class ComInfoAction extends BaseAction {
	@Resource
	private ComInfoService comInfoService;

	/**跳转**/
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listView(){
		int type = getInt("type");
		request.setAttribute("type", type);
		return new ModelAndView("/admin/base/comInfo/list.jsp");
	}
	/**数据，列表**/
	@RequestMapping(value="/list")
	@ResponseBody
	public String list(){	
		//初始化分页
		int pageSize = getInt("length",20);
		int start = getInt("start");
		int currPage = start/pageSize+1;
		//页面参数
		int type = getInt("type");
		String nameTJ = getParameter("nameTJ");
		//条件与排序
		ConditionQuery query = new ConditionQuery();
		if(nameTJ!=null&&!"".equals(nameTJ)&&!"null".equals(nameTJ)){
			query.add(Restrictions.like("name", nameTJ,MatchMode.ANYWHERE));
		}
		query.add(Restrictions.eq("type", type));
		query.add(Restrictions.ne("status", Status.valueOf("delete")));
		query.order(Order.asc("order"));
		//数据获取与返回
		Page pp=comInfoService.listPage(query, pageSize, currPage);
 		return toJson(pp);
	}	
	/**交互：新增或修改**/
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add() {
		Map<String, Object> result;
		User user = getSessionUser();
		String oldCode = getParameter("oldCode");
		String code = getParameter("code");
		String name = getParameter("name");
		int order = getInt("order");
		int type = getInt("type");
		ComInfo comInfo = null;
		try{
			if(oldCode!=null&&!"".equals(oldCode)&&!"null".equals(oldCode)){
				comInfo = comInfoService.get(oldCode);
				comInfo.setCreateTime(new Date());
			}else{
				comInfo = new ComInfo();
				comInfo.setStatus(Status.valueOf("enable"));
				comInfo.setCode(code);
				comInfo.setType(type);
				comInfo.setCreater(user);
				comInfo.setCreateTime(new Date());
			}
			comInfo.setName(name);
			comInfo.setOrder(order);
			comInfoService.saveOrUpdate(comInfo);
			result = success("保存成功");
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result = error();
			return result;
		}
	}
	/**更改状态**/
	@RequestMapping(value="/updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus() {
		String code = getParameter("code");
		String status = getParameter("status");
		ComInfo comInfo = comInfoService.get(code);
		comInfo.setStatus(Status.valueOf(status));
		comInfoService.update(comInfo);
		return success();
	}
	
	/**
	 * 判断添加时是否有重复的code
	 * **/
	@RequestMapping("judgeCode")
	@ResponseBody
	public String judgeCode() throws Exception {
		String code=getParameter("code");
		String message="";
		try{
			ComInfo comInfo = comInfoService.get(code);
			if(comInfo==null)
				message="true";
			else
				message="该编码已经被使用!";
		}catch(Exception e){
			e.printStackTrace();
		}
		return  message;
	}
}
