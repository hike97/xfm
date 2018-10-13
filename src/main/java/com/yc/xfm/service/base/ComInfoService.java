package com.yc.xfm.service.base;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.core.util.ConditionQuery;
import com.yc.core.util.Page;
import com.yc.xfm.dao.base.ComInfoDao;
import com.yc.xfm.entity.base.ComInfo;

@Service
@Transactional(rollbackFor = Exception.class)
public class ComInfoService {
	@Resource
	private ComInfoDao dao;
	
	/**常规基础方法，增删改查**/
	public void save(ComInfo object){
		dao.save(object);
	}
	public void update(ComInfo object){
		dao.update(object);
	}
	public void saveOrUpdate(ComInfo object){
		dao.saveOrUpdate(object);
	}
	public void delete(String pk){
		dao.delete(pk);
	}
	public ComInfo get(String pk){
		return dao.get(pk);
	}
	public Page listPage(ConditionQuery query, int pageSize, int currPage) {
		return dao.listPage(query, pageSize, currPage);
	}
	public List<ComInfo> list(ConditionQuery query) {
		return dao.list(query);
	}
	public List<ComInfo> list(ConditionQuery query,int position,int pageSize) {
		return dao.list(query, position, pageSize);
	}
	
	/**自定义方法在下面添加**/
}
