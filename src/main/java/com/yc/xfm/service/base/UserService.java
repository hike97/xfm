package com.yc.xfm.service.base;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.core.util.ConditionQuery;
import com.yc.core.util.Page;
import com.yc.xfm.dao.base.UserDao;
import com.yc.xfm.entity.base.User;

@Service
@Transactional(rollbackFor=Exception.class) 
public class UserService {
	@Resource
	private UserDao userDao;
	
	
	/******************查询****************/
   public User get(Long id) {
		return userDao.get(id);
    }
   	public User getByName(String loginName) {
		String hql="from User where name=?";
		List<User> users =userDao.list(hql, 0, 0, loginName);
		User user=null;
		if(users.size()>0)
			user=users.get(0);
		return user;
	}
   	public User getByRealName(String realName) {
   		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("status", 1));
		query.add(Restrictions.eq("realName", realName));
		List<User> users =userDao.list(query);
		User user=null;
		if(users.size()>0)
			user=users.get(0);
		return user;
	}
	public Page listPage(ConditionQuery query,int pageSize,int currPage) {
		return userDao.listPage(query,  pageSize, currPage);
	}
	public List<User> list(ConditionQuery query) {
		return userDao.list(query);
	}
	
	/***************更新*******************/
    public void update(User user) {
    	userDao.update(user);
	}
	public void delete(User user) {
		userDao.deleteObject(user);
	}
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}
	public List<User> list(ConditionQuery query, int position,int pageSize) {
		return userDao.list(query,  position, pageSize);
	}
}
