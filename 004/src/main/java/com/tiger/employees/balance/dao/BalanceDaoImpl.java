package com.tiger.employees.balance.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tiger.employees.balance.po.Balance;
import com.tiger.employees.subject.po.Subject;

public class BalanceDaoImpl extends HibernateDaoSupport implements BalanceDao {

	public void addBalance(Balance balance) {
		// TODO Auto-generated method stub
		if(balance!=null){
			this.getHibernateTemplate().save(balance);
		}
		
	}

	public void addListBalance(List<Balance> balanceList) {
		// TODO Auto-generated method stub
		if(balanceList!=null){
			for(Balance bal:balanceList){
				String balanceId=UUID.randomUUID().toString();
				bal.setId(balanceId);
//				this.getHibernateTemplate().saveOrUpdate(bal);
				this.getHibernateTemplate().save(bal);
			}
		}
	}

	public void updateBalance(Balance balance) {
		// TODO Auto-generated method stub
		
	}

	public void updateListBalance(Balance balance) {
		// TODO Auto-generated method stub
		
		
	}

	public Balance getBalance(String balanceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Balance> getListBalance(Map params) {
		// TODO Auto-generated method stub
        List<Balance> resultList=null;
		List<Object> valueList=new ArrayList();
		String hql="from Balance ";
		Set ks=params.keySet();
		String queryParm="";
		for(Object key:ks){
			queryParm=queryParm+" and "+ key +" = ? ";
			Object v=params.get(key);
			valueList.add(v);
		}
		if(params.isEmpty()){
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

	public void updateListBalance(List<Balance> balance) {
		// TODO Auto-generated method stub
		
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

}
