package com.yc.core.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;

public class GroupBy {
    
    private List<Projection> projections = new ArrayList<Projection>();
    
    public GroupBy add(Projection projection) {
    	projections.add(projection);
        return this;
    }
    
    public void build(Criteria criteria) {
        for(Projection projection : projections) {
            criteria.setProjection(projection);
        }
    }
}
