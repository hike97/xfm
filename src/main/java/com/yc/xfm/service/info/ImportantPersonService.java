package com.yc.xfm.service.info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.core.util.ConditionQuery;
import com.yc.core.util.Page;
import com.yc.xfm.dao.info.ImportantPersonDao;
import com.yc.xfm.entity.info.ImportantPerson;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImportantPersonService {
	@Resource
	private ImportantPersonDao dao;
	
	/**常规基础方法，增删改查**/
	public void save(ImportantPerson object){
		dao.save(object);
	}
	public void update(ImportantPerson object){
		dao.update(object);
	}
	public void saveOrUpdate(ImportantPerson object){
		dao.saveOrUpdate(object);
	}
	public void delete(Long pk){
		dao.delete(pk);
	}
	public ImportantPerson get(Long pk){
		return dao.get(pk);
	}
	public Page listPage(ConditionQuery query, int pageSize, int currPage) {
		return dao.listPage(query, pageSize, currPage);
	}
	public List<ImportantPerson> list(ConditionQuery query) {
		return dao.list(query);
	}
	public List<ImportantPerson> list(ConditionQuery query,int position,int pageSize) {
		return dao.list(query, position, pageSize);
	}
	
	/**自定义方法在下面添加**/
}
