package com.yc.xfm.dao.base;

import org.springframework.stereotype.Repository;

import com.yc.xfm.entity.base.User;
import com.yc.xfm.dao.BaseHibernateDao;
	
@Repository
public class UserDao extends BaseHibernateDao<User,Long> {

}
