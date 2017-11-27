package com.tiger.employees.profititem.dao;

import com.tiger.employees.profititem.po.ProfitItem;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zx on 6/25/17.
 */
public class ProfitItemDaoImpl extends HibernateDaoSupport implements ProfitItemDao {
    public List<ProfitItem> getProfitItems(Map param) {
        // TODO Auto-generated method stub
        List<ProfitItem> resultList=null;
        String hql="from ProfitItem";
        Set ks=param.keySet();
        List<Object> valueList=new ArrayList();
        String queryParm="";
        for(Object key:ks){
            Object v=param.get(key);
            valueList.add(v);
            if(v.toString().contains("%")){
                queryParm=queryParm+" and "+ key +" like ? ";
            }
            else{

                queryParm=queryParm+" and "+ key +" = ? ";
            }
        }
        queryParm=queryParm+" order by id";
        if(param.isEmpty()){
            resultList=this.getHibernateTemplate().find(hql);
        }
        else{
            hql=hql+" where 1=1 ";
            hql=hql+queryParm;
            Object[] vsa=valueList.toArray();
            try{
                resultList=this.getHibernateTemplate().find(hql, vsa);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
