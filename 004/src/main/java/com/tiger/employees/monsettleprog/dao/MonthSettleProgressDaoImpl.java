package com.tiger.employees.monsettleprog.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.xwork2.Action;
import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.utilities.StringUtil;

public class MonthSettleProgressDaoImpl  extends HibernateDaoSupport implements MonthSettleProgressDao{

	public List<MonthSettleProgress> getMSPs(Map param) {
		// TODO Auto-generated method stub
		List<MonthSettleProgress> resultList=null;
		List<Object> valueList=new ArrayList();
		String hql="from MonthSettleProgress ";
		Set ks=param.keySet();
		
		
		String queryParm="";
		for(Object key:ks){
			queryParm=queryParm+" and "+ key +" = ? ";
			Object v=param.get(key);
			valueList.add(v);
		}
		if(param.isEmpty()){
			resultList=this.getHibernateTemplate().find(hql);
		}
		else{
			hql=hql+" where 1=1 ";
			hql=hql+queryParm;
			Object[] vsa=valueList.toArray();
			hql=hql+" order by settleYear desc,settleMonth desc";
			try{
				resultList=this.getHibernateTemplate().find(hql, vsa);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return resultList;
	}

	public MonthSettleProgress getMSPById(String id) {
		// TODO Auto-generated method stub
		MonthSettleProgress po=null;
		if(!StringUtil.isEmpty(id)){
			po=this.getHibernateTemplate().get(MonthSettleProgress.class, id);
		}
		
		return po;
	}

	public void addMSP(MonthSettleProgress msp) {
		// TODO Auto-generated method stub
		if(msp!=null){
			this.getHibernateTemplate().save(msp);
		}
		
	}

	public void updateMSP(MonthSettleProgress msp) {
		// TODO Auto-generated method stub
		if(msp!=null){
			this.getHibernateTemplate().saveOrUpdate(msp);
//			this.getHibernateTemplate().update(msp);
		}
	}

	public List<Object> execSql(String sql) {
		// TODO Auto-generated method stub
		try{
			Session sess=this.getHibernateTemplate().getSessionFactory().openSession();
				Transaction tx=sess.beginTransaction();
				 SQLQuery query = this.getSession().createSQLQuery(sql); 
				 List resultist=query.list();
				 sess.clear();
				 sess.close();
				 return resultist;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String execDmlSql(String sql) {
		// TODO Auto-generated method stub
		try{
			Session sess=this.getHibernateTemplate().getSessionFactory().openSession();
				Transaction tx=sess.beginTransaction();
				Query q=sess.createSQLQuery(sql);
				q.executeUpdate();
				tx.commit();
				sess.flush();
				sess.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
}
