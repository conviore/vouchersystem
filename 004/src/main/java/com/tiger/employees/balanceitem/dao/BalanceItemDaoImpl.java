package com.tiger.employees.balanceitem.dao;

// Generated Jan 27, 2015 11:20:52 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tiger.employees.balanceitem.po.BalanceItem;
import com.tiger.employees.voucher.po.Voucher;

/**
 * Home object for domain model class BalanceItem.
 * @see com.tiger.employees.balanceitem.po.BalanceItem
 * @author Hibernate Tools
 */
public class BalanceItemDaoImpl extends HibernateDaoSupport implements BalanceItemDao{

	private static final Log log = LogFactory.getLog(BalanceItemDaoImpl.class);

	private final SessionFactory sessionFactory = getSessionFactory();



	public void persist(BalanceItem transientInstance) {
		log.debug("persisting BalanceItem instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(BalanceItem instance) {
		log.debug("attaching dirty BalanceItem instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BalanceItem instance) {
		log.debug("attaching clean BalanceItem instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BalanceItem persistentInstance) {
		log.debug("deleting BalanceItem instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BalanceItem merge(BalanceItem detachedInstance) {
		log.debug("merging BalanceItem instance");
		try {
			BalanceItem result = (BalanceItem) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BalanceItem findById(java.lang.String id) {
		log.debug("getting BalanceItem instance with id: " + id);
		try {
			BalanceItem instance = (BalanceItem) sessionFactory
					.getCurrentSession().get(
							"com.tiger.employees.balanceitem.po.BalanceItem",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BalanceItem instance) {
		log.debug("finding BalanceItem instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.tiger.employees.balanceitem.po.BalanceItem")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List<BalanceItem> getBalanceItems(Map param) {
		// TODO Auto-generated method stub
		List<BalanceItem> resultList=null;
		String hql="from BalanceItem ";
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
