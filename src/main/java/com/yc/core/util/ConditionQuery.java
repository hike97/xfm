package com.yc.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

public class ConditionQuery {

    
    private List<Criterion> criterions = new ArrayList<Criterion>();
    private List<Order> orders = new ArrayList<Order>();
    
    private Projection projection;
    private Map<String,String> aliasMap = new HashMap<>();
    public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public ConditionQuery add(Criterion criterion) {
        criterions.add(criterion);
        return this;
    }
   public ConditionQuery order(Order order) {
        orders.add(order);
        return this;
    }
	public ConditionQuery alias(String entity,String alias) {
		aliasMap.put(entity, alias);
        return this;
    }
    public void build(Criteria criteria,String pkName) {
    	System.out.println("pkName:"+pkName);
    	for(String key:aliasMap.keySet()){
    		criteria.createAlias(key, aliasMap.get(key));
    	}
    	
        for(Criterion criterion : criterions) {
            criteria.add(criterion);
        }
        if(orders == null){
        	if(pkName.equals("id")){
        		criteria.addOrder(Order.desc("id"));
        	}else{
        		criteria.addOrder(Order.asc(pkName));
        	}
        }
        for(Order order : orders) {
            criteria.addOrder(order);
        }
        if(projection!=null){
        	criteria.setProjection(projection);
        }
    }
        
}
