package com.yc.xfm.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yc.core.util.ConditionQuery;
import com.yc.core.util.Page;

/**
 * 
 * @author guze
 *
 * @version 2.0, 2016-7-7
 */
@SuppressWarnings({"unchecked","rawtypes"})
public abstract class BaseHibernateDao<M extends java.io.Serializable, PK extends java.io.Serializable>{

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseHibernateDao.class);

    private final Class<M> entityClass;
    private final String HQL_LIST_ALL;
    private final String HQL_COUNT_ALL;
    private final String HQL_OPTIMIZE_PRE_LIST_ALL;
    private final String HQL_OPTIMIZE_NEXT_LIST_ALL;
    private String pkName = null;

    public BaseHibernateDao() {
        this.entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] fields = this.entityClass.getDeclaredFields();
        for(Field f : fields) {
            if(f.isAnnotationPresent(Id.class)) {
                this.pkName = f.getName();
            }
        }
        if(pkName==null)
        	pkName="id";
        HQL_LIST_ALL = "from " + this.entityClass.getSimpleName() + " order by " + pkName + " desc";
        HQL_OPTIMIZE_PRE_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " > ? order by " + pkName + " asc";
        HQL_OPTIMIZE_NEXT_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " < ? order by " + pkName + " desc";
        HQL_COUNT_ALL = " select count(*) from " + this.entityClass.getSimpleName();
    }
        
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        //事务必须是开启的(Required)，否则获取不到
        return sessionFactory.getCurrentSession();
    }
    private Criteria getCriteria(){
    	return DetachedCriteria.forClass(this.entityClass).getExecutableCriteria(getSession());
    }
    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }
    
    protected static <R> void  setParameters(Query<R> query, Object[] paramlist) {
        if (paramlist != null) {
            for (int i = 0; i < paramlist.length; i++) {
            	 query.setParameter(i, paramlist[i]);
            }
        }
    }

    /**************更新方法********************/
    public PK save(M model) {
        return (PK) getSession().save(model);
    }

    public void saveOrUpdate(M model) {
        getSession().saveOrUpdate(model);
    }

    public void update(M model) {
        getSession().update(model);

    }

    public void merge(M model) {
        getSession().merge(model);
    }

    public void delete(PK id) {
        getSession().delete(this.get(id));

    }

    public void deleteObject(M model) {
        getSession().delete(model);

    }
    /**
     * 执行批处理语句.如 之间insert, update, delete 等.
     */
    public int execteBulk(final String hql, final Object... paramlist) {
        Query query = getSession().createQuery(hql);
        setParameters(query, paramlist);
        Object result = query.executeUpdate();
        return result == null ? 0 : ((Integer) result).intValue();
    }
    /**
     * @param nativeSQL执行语句 paramlist 语句中参数 
     * @return 执行数据行数
     */
    public int execteNativeBulk(final String nativeSQL, final Object... paramlist) {
        Query query = getSession().createNativeQuery(nativeSQL);
        setParameters(query, paramlist);
        Object result = query.executeUpdate();
        return result == null ? 0 : ((Integer) result).intValue();
    }

    /**************查询all list方法********************/

    public List<M> listAll() {
        return list(HQL_LIST_ALL,0,0);
    }

    public List<M> listAll(int position, int pageSize) {
        return list(HQL_LIST_ALL, position, pageSize);
    }
    
    public List<M> pre(PK pk, int position, int pageSize) {
        if(pk == null) {
            return list(HQL_LIST_ALL, position, pageSize);
        }
        //倒序，重排
        List<M> result = list(HQL_OPTIMIZE_PRE_LIST_ALL, 1, pageSize, pk);
        Collections.reverse(result);
        return result;
    }
    public List<M> next(PK pk, int position, int pageSize) {
        if(pk == null) {
            return list(HQL_LIST_ALL, position, pageSize);
        }
        return list(HQL_OPTIMIZE_NEXT_LIST_ALL, 1, pageSize, pk);
    }
    /**************查询list方法********************/
    public List<M> list(final String hql){
    	return list(hql, 0, 0);
    }
    /**
     * @param hql
     * @param pn
     * @param pageSize
     * @param paramlist
     * @return
     * 
     * @description
     * @version 1.0
     * @author 顾泽
     * @update 2012-5-7 上午10:56:26
     */
    public List<M> list(final String hql, final int position, final int pageSize, final Object... paramlist) {
        Query<M> query = getSession().createQuery(hql);
        setParameters(query, paramlist);
        if(pageSize>0){
        	query.setFirstResult(position);
        	query.setMaxResults(pageSize);
        }
        List<M> results = query.getResultList();
        return results;
    }
   
    /** 
    * @date 2012-6-20 上午10:54:04
    * @author gu
    * @Description: 查询列表
    * @param query 查询条件
    * @param orderBy 排序字段
    * @param position 当前位置
    * @param pageSize 每页条数
    * @return List<T> 
    * @throws 
    */ 
    public <T> List<T> list(ConditionQuery query, final int position, final int pageSize) {
        Criteria criteria =  getCriteria();
        query.build(criteria,pkName);
       
        if(pageSize>0){
        	criteria.setFirstResult(position);
        	criteria.setMaxResults(pageSize);
        }
        System.out.println(criteria.list().size());
        return criteria.list();
    }
    public <T> List<T> list(ConditionQuery query) {
        return list( query,0, 0);
    }
    public <T> List<T> list(Criteria criteria) {
        return criteria.list();
    }
//    public <T> List<T> list(DetachedCriteria criteria) {
//        return list(criteria.getExecutableCriteria(getSession()));
//    }
    /**
	 * @param sql:sql语句
	 * */
	public <T> List<T> listBySQL(final String sql){
		return listBySQL(sql,null,0,0);
	}
    
	/**
	 * @param sql:sql语句
	 * @param firstResult:当前位置
	 * @param maxResults：偏移量
	 * */
	public <T> List<T> listBySQL(final String sql,final int position,final int pageSize){
		return listBySQL(sql,null,position,pageSize);
	}
	 public <T> List<Map<String, Object> > listBySQLAsMap(String sql,Order order,
			 final int position, final int pageSize, final Object... paramlist) {
	  		sql+=order==null?"":" order by "+order.toString();
	  		Query query = getSession().createNativeQuery(sql);
	  		if(pageSize>0){
	  			query.setFirstResult(position);
	  			query.setMaxResults(pageSize);
	  		}
	  		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	  		setParameters(query, paramlist);
	  		return (List<Map<String, Object> >)query.getResultList();
	  	}
	public <T> List<T> listBySQL(String sql,Order order,final int position, final int pageSize, final Object... paramlist) {
  		sql+=order==null?"":" order by "+order.toString();
  		Query<T> query = getSession().createNativeQuery(sql);
  				//createSQLQuery(sql);
  		if(pageSize>0){
  			query.setFirstResult(position);
  			query.setMaxResults(pageSize);
  		}
  		setParameters(query, paramlist);
  		return (List<T>)query.getResultList();
  	}
    /**************查询page方法********************/
     /** 
      * @date 2012-6-20 上午10:54:04
      * @author gu
      * @Description: 查询列表带分页
      * @param query 查询条件
      * @param orderBy 排序字段
      * @param position 当前位置
      * @param pageSize 每页条数
      * @return Page 
      * @throws 
      */ 
     public Page listPage(ConditionQuery query,final int pageSize, final int currPage) {
         Criteria criteria = getCriteria();
         query.build(criteria,pkName);
         criteria.setProjection(Projections.rowCount());
         int maxRow=Integer.parseInt(criteria.uniqueResult().toString());
         Page page=new Page(maxRow,pageSize,currPage);
         criteria.setProjection(null);
         criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
         if(pageSize>0){
         	criteria.setFirstResult(page.getPosition());
         	criteria.setMaxResults(page.getOffSet());
         }
         page.setList(criteria.list());
         return page;
     }
     /**
      * @param hql
      * @param order
      * @param pageSize
      * @param currPage
      * @param alias 需要查的实体别名
      * @param paramlist
      * @return
      */
	public Page listPage(String hql, Order order, final int pageSize, final int currPage,String alias,Object... paramlist) {
		Query query =getSession().createQuery("select count(*) "+hql);
     	BaseHibernateDao.setParameters(query, paramlist);
     	int maxRow=((Long)query.getSingleResult()).intValue();
     	Page page=new Page(maxRow,pageSize,currPage);
     	hql+=order==null?"":" order by "+order.toString();
     	if(alias!=null)
     		hql="select "+alias+" "+hql;
     	query=getSession().createQuery(hql);
     	BaseHibernateDao.setParameters(query, paramlist);
     	if(pageSize>0){
			query.setFirstResult(page.getPosition());
			query.setMaxResults(page.getOffSet());
     	}
     	page.setList(query.getResultList());
     	return page;
       }
     /**
      * @param hql
      * @param order
      * @param pageSize
      * @param currPage
      * @param alias 需要查的实体别名
      * @param paramlist
      * @return
      */
      public Page listPage(String sql,final int pageSize, final int currPage) {   	
      	String countSql = "select count(*) from ("+sql+") tmp";
  		Query query = getSession().createNativeQuery(countSql);
  		int maxRow=(Integer)query.getSingleResult();
  		
  		Page page=new Page(maxRow,pageSize,currPage);
  		if(pageSize>0){
  			query = getSession().createNativeQuery(sql);
  			query.setFirstResult(page.getPosition());
  			query.setMaxResults(page.getOffSet());
  		}
  		page.setList(query.getResultList());
  		
  		return page;
  	}
      public Page listPage(String sql,Order order,final int pageSize, final int currPage, final Object... paramlist) {
       	String countSql = "select count(*) from ("+sql+") tmp";
   		Query query = getSession().createNativeQuery(countSql);
   		setParameters(query, paramlist);
   		int maxRow=Integer.parseInt(query.getSingleResult().toString());
   		Page page=new Page(maxRow,pageSize,currPage);
   		sql+=order==null?"":" order by "+order.toString();
   		query = getSession().createNativeQuery(sql);
   		if(pageSize>0){
   			query.setFirstResult(page.getPosition());
   			query.setMaxResults(page.getOffSet());
   		}
   		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
   		setParameters(query, paramlist);
   		page.setList(query.getResultList());
   		return page;
   	}
    /**************查询unique方法********************/
    
    public boolean exists(PK id) {
        return get(id) != null;
    }

	public M get(PK id) {
        return (M) getSession().get(this.entityClass, id);
    }

    public int countAll() {
        Long total = aggregate(HQL_COUNT_ALL);
        return total.intValue();
    }



    protected long getIdResult(String hql, Object... paramlist) {
        long result = -1;
        List<?> list = list(hql,0,0, paramlist);
        if (list != null && list.size() > 0) {
            return ((Number) list.get(0)).longValue();
        }
        return result;
    }

 
    public int count(M model){
    	 Criteria criteria = getCriteria();
    	 criteria.add(Example.create(model));
    	 criteria.setProjection(Projections.rowCount());
    	 return Integer.parseInt(criteria.uniqueResult().toString());
    }
    /** 
     * @date 2012-6-20 上午10:54:04
     * @author gu
     * @Description: 统计数量
     * @param query 查询条件
     * @return count 
     * @throws 
     */ 
    public int count(ConditionQuery query) {
        Criteria criteria = getCriteria();
        query.build(criteria,pkName);
        criteria.setProjection(Projections.rowCount());
        int maxRow=Integer.parseInt(criteria.uniqueResult().toString());
        return maxRow;
    }
    public int count(final String hql,final Object... paramlist){
    	Object obj=aggregate(hql,paramlist);
    	int count=0;
    	if(obj instanceof Long){
    		count= ((Long)obj).intValue();
    	}
    	else if(obj instanceof Integer){
    		count= (Integer)obj;
    	}
    	return count;
    }
    
    /** 
     * @date 2013-7-17 上午10:54:04
     * @author gu
     * @Description: 根据查询条件返回唯一一条记录
     * @param query 查询条件
     * @return uniqueResult 
     * @throws 
     */ 
    public <T> T unique(ConditionQuery query) {
        Criteria criteria = getCriteria();
        query.build(criteria,pkName);
        //criteria.setProjection(Projections.rowCount());
       // int maxRow=Integer.parseInt(criteria.uniqueResult().toString());
        return (T)criteria.uniqueResult();
    }
    /**
     * 根据查询条件返回唯一一条记录
     */
    public <T> T unique(final String hql, final Object... paramlist) {
        Query<T> query = getSession().createQuery(hql);
        setParameters(query, paramlist);
        return (T) query.setMaxResults(1).getSingleResult();
    }

    protected <T> T aggregate(final String hql, final Object... paramlist) {
        Query <T>  query = getSession().createQuery(hql);
        setParameters(query, paramlist);

        return (T) query.getSingleResult();

    }
    public <T> T unique(Criteria criteria) {
        return (T) criteria.uniqueResult();
    }

    public <T> T unique(DetachedCriteria criteria) {
        return (T) unique(criteria.getExecutableCriteria(getSession()));
    }


        
}
