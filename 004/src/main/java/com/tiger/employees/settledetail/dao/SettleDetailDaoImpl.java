package com.tiger.employees.settledetail.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.tiger.employees.settledetail.po.SettleDetail;
import com.tiger.employees.subject.po.Subject;
import com.tiger.employees.subject.po.SubjectHome;


public class SettleDetailDaoImpl extends HibernateDaoSupport implements
		SettleDetailDao {

	private static final Log log = LogFactory.getLog(SubjectHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();
	
	public void attachDirty(SettleDetail instance) {
		log.debug("attaching dirty Subject instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}

	public void save(SettleDetail settleDetail) {
		// TODO Auto-generated method stub
//
//		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
//		try{
//		
//		if(session==null){
//			session=this.getSessionFactory().openSession();
//		}
//		//session.beginTransaction();
//		Transaction tx=session.beginTransaction();
		this.getHibernateTemplate().save(settleDetail);
//		session.save(settleDetail);
//		tx.commit();
//		
//		if(tx.wasCommitted()){
//			log.debug("save subject instance");
//		}
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			session.flush();
//			session.close();
//			
//		}
		//session.close();
	}

	public void update(SettleDetail settleDetail) {
		// TODO Auto-generated method stub
//		Session session=this.getSessionFactory().getCurrentSession();
//		
//		if(session==null){
//			session=this.getSessionFactory().openSession();
//		}
		//session.save(settleDetail);
		//this.getHibernateTemplate().getSessionFactory().getCurrentSession().e
		this.getHibernateTemplate().update(settleDetail);
//		String hql = "DELETE FROM settle_detail ";  
//		Transaction tx=session.beginTransaction();
//	    session.createSQLQuery(hql).executeUpdate(); 
//	    tx.commit();
	    
	    
		//session.update(settleDetail);
//		session.flush();
//		session.close();

	}

	public void delete(SettleDetail settleDetail) {
		// TODO Auto-generated method stub

		log.debug("deleting Subject instance");
		try {
			sessionFactory.getCurrentSession().delete(settleDetail);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List<SettleDetail> queryList(Map param) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
        Query query = session.createQuery("from SettleDetail"); 
        List<SettleDetail> resultlist = query.list(); 
        session.close();
        return resultlist;
	}

	public void commitTrans() {
		// TODO Auto-generated method stub
	    this.getSessionFactory().getCurrentSession().getTransaction().commit();
		this.getSession().flush();
		this.getSession().close();
		
	}

	public void beginTrans() {
		// TODO Auto-generated method stub
		Session sess=this.getSessionFactory().getCurrentSession();
		if(sess==null){
			this.getSessionFactory().openSession();
		}
		this.getSessionFactory().getCurrentSession().beginTransaction();
	}
	
	private void update(Session sess,SettleDetail po){
		if(po!=null){
			sess.update(po);
		}
	}

}
