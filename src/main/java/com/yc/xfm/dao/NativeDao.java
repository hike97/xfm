package com.yc.xfm.dao;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yc.core.util.Page;

/**原生sql操作类
 * @author 顾泽 
 * @date 2015-5-18
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class NativeDao{
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    } 
    /** 原生sql保存
    * @author 顾泽 
    * @date 2015-5-19
    * @param tableName
    * @param map
    */
    public void save(String tableName,Map<String,Object> map) {
    	String sql="insert into "+tableName+" (id";
    	String param="";
    	Object[] paramlist=new Object[map.keySet().size()];
    	int k=0;
    	for(String s:map.keySet()){
    		sql+=","+s;
    		param+=",?";
    		paramlist[k++]=map.get(s);
    	}
    	sql+=") values (S"+tableName.substring(2)+".nextval"+param+")";
    	Query query=getSession().createNativeQuery(sql);
    	BaseHibernateDao.setParameters(query, paramlist);
    	query.executeUpdate();
    }
    /**更新**/
    public void update(String tableName,Map<String,Object> map){
    	String sql="update "+tableName+" set ";
    	int k=0;
    	Object[] paramlist=new Object[map.keySet().size()];
    	for(String s:map.keySet()){
    		if(s.equals("id"))
    			continue;
    		paramlist[k]=map.get(s);
    		if(k++!=0)
    			sql+=",";
    		sql+=s+"=?";
    	}
    	paramlist[k]=map.get("id");
    	sql+=" where id=?";
    	Query query=getSession().createNativeQuery(sql);
    	BaseHibernateDao.setParameters(query, paramlist);
    	query.executeUpdate();
    }
    /*****新增或者更新******/
    public void saveOrUpdate(String tableName,Map<String,Object> map) {
       if(map.get("id")!=null){
    	   update(tableName, map);
       }else{
    	   save(tableName, map);
       }
    }
    /*****删除******/
    public void delete(String tableName,Long id) {
       String sql="delete from "+tableName+" where id=?";
       Query query=getSession().createNativeQuery(sql);
       query.setParameter(0, id);
       query.executeUpdate();
    }
    
    public void deleteB(String tableName,Long id,String colum) {
        String sql="delete from "+tableName+" where "+colum+"=?";
        Query query=getSession().createNativeQuery(sql);
        query.setParameter(0, id);
        query.executeUpdate();
     }
    
    
    /*********查询方法**************/
	@SuppressWarnings("deprecation")
	public Map<String, Object> get(String tableName,Long id) {
    	String sql="select * from "+tableName+" where id=?";
    	 Query query=getSession().createNativeQuery(sql);
    	 query.setParameter(0, id);
         query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
         return (Map<String, Object>) query.getSingleResult();
    }
	public <T> T unique(String sql,Object... paramlist){
    	Query query=getSession().createNativeQuery(sql);
    	BaseHibernateDao.setParameters(query, paramlist);
    	Object object=query.getSingleResult();
    	if(object instanceof BigInteger){
    		object=((BigInteger)object).intValue();
    	}else if(object instanceof BigDecimal){
    		object=((BigDecimal)object).intValue();
    	}
    	
    	return (T)object;
    }
    /**
     * @param sql
     * @param position
     * @param pageSize
     * @param paramlist
     * @return
     * @description
     * @version 1.0
     * @author 顾泽
     * @update 2012-5-7 上午10:56:26
     */
    @SuppressWarnings("deprecation")
	public List<Map<String, Object>> list(final String sql, final int position,
    		final int pageSize, final Object... paramlist) {
        Query query = getSession().createNativeQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        BaseHibernateDao.setParameters(query, paramlist);
        if(pageSize>0){
        	query.setFirstResult(position);
        	query.setMaxResults(pageSize);
        }
        return  query.getResultList();
    }
   
    /**
    * @author 顾泽 
    * @date 2015-5-19
    * @param sql
    * @param order
    * @param pageSize
    * @param currPage
    * @param paramlist 
    * @return
    */
    @SuppressWarnings("deprecation")
	public Page pageList(String sql, Order order, final int pageSize, final int currPage,Object... paramlist) {
		Query query =null;
		query=getSession().createNativeQuery("select count(*) from ("+sql+") page_count");
		BaseHibernateDao.setParameters(query, paramlist);
		String maxRowStr=query.getSingleResult().toString();
		int maxRow=Integer.parseInt(maxRowStr);
        Page page=new Page(maxRow,pageSize,currPage);
        sql+=order==null?" ":" order by "+order.toString();
        query=getSession().createNativeQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        BaseHibernateDao.setParameters(query, paramlist);
        if(pageSize>0){
        	query.setFirstResult(page.getPosition());
        	query.setMaxResults(page.getOffSet());
        }
        page.setList(query.getResultList());
        return page;
    }
    @SuppressWarnings("deprecation")
	public Page pageList(String sql, Order order, final int pageSize, final int currPage) {
		Query query =null;
		query=getSession().createNativeQuery("select count(*) from ("+sql+") page_count");
		String maxRowStr=query.getSingleResult().toString();
		int maxRow=Integer.parseInt(maxRowStr);
        Page page=new Page(maxRow,pageSize,currPage);
        if(currPage>page.getTotPage()){
        	return page;
        }
        sql+=order==null?" ":" order by "+order.toString();
        query=getSession().createNativeQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if(pageSize>0){
        	query.setFirstResult(page.getPosition());
        	query.setMaxResults(page.getOffSet());
        }
        page.setList(query.getResultList());
        return page;
    }
    
    /**  
     * @param nativeSQL执行语句 paramlist 语句中参数 
     * @return 执行数据行数
     */
    public int execteNativeBulk(final String nativeSQL, final Object... paramlist) {
    	Query query = getSession().createNativeQuery(nativeSQL);
        BaseHibernateDao.setParameters(query, paramlist);
        Object result = query.executeUpdate();
        return result == null ? 0 : ((Integer) result).intValue();
    }
    
    
}
