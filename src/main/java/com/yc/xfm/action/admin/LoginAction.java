package com.yc.xfm.action.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yc.core.util.ConditionQuery;
import com.yc.core.util.GlobalDefine;
import com.yc.core.util.MD5Util;
import com.yc.xfm.action.BaseAction;
import com.yc.xfm.entity.base.User;
import com.yc.xfm.service.base.UserService;

@Controller
@RequestMapping("/admin")
public class LoginAction extends BaseAction {
	@Resource
	private UserService userService;
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginView() {
		return new ModelAndView("/admin/login.jsp");
	}

	// 登录信息校验
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login() throws Exception {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String md5Password = MD5Util.MD5(password);
		ConditionQuery query;
		query = new ConditionQuery();
		query.add(Restrictions.eq("name", name));
		query.add(Restrictions.eq("password", md5Password));
		//后加的2018-1-17
//		query.add(Restrictions.eq("status", 1));
		List<User> users = userService.list(query);
		if (users != null && users.size() > 0) {
			User user=users.get(0);
			request.getSession().setAttribute(GlobalDefine.SESSION_USER, user);
			//获取未读消息条数end
			return success();
		}
		return error("用户名或密码有误！");
	}

	// 进入主界面
	@RequestMapping(value = "/main")
	public ModelAndView main() {
		return new ModelAndView("/admin/main/main.jsp");
	}
	@RequestMapping("/logout")
	public String  logout(){
		//移除session
		@SuppressWarnings("unused")
		User user = (User) request.getSession().getAttribute(GlobalDefine.SESSION_USER);
		request.getSession().invalidate();
		return "redirect:/admin/login";
	}
}
