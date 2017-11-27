package com.tiger.employees.subject.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Entity;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.tiger.employees.subject.po.Subject;
import com.tiger.employees.subject.po.SubjectHome;
import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.utilities.StringUtil;

@Transactional
public class SubjectDaoImpl extends HibernateDaoSupport implements SubjectDao {
	
	private static final Log log = LogFactory.getLog(SubjectHome.class);

	public void addSubject(Subject subject) {
		// TODO Auto-generated method stub

		try{
		if(subject!=null){
			Session sess=this.getSessionFactory().openSession();
			sess.beginTransaction();
			this.getHibernateTemplate().save(subject);
			sess.flush();
			sess.close();
		}}
		catch(Throwable t){
			t.printStackTrace();
		}
		
		
	}

	public void updateSubject(Subject subject) {
		// TODO Auto-generated method stub

	}

	public Subject querySubjectById(String id) {
		Subject po=null;
		if(!StringUtil.isEmpty(id)){
			po=this.getHibernateTemplate().get(Subject.class, id);
		}
		return po;
	}
   

	public List<Subject> querySubjects(Map param) {
		// TODO Auto-generated method stub
        log.debug("attaching dirty Subject instance");
        List<Subject> resultList=null;
		List<Object> valueList=new ArrayList();
		String hql="from Subject ";
		Set ks=param.keySet();
		String queryParm="";
		for(Object key:ks){
			queryParm=queryParm+" and "+ key +" = ? ";
			Object v=param.get(key);
			valueList.add(v);
		}
		queryParm= queryParm+" order by code";
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
	
	public void attachDirty(Subject instance) {
		log.debug("attaching dirty Subject instance");
		try {
			Session sess=this.getHibernateTemplate().getSessionFactory().openSession();
			Transaction tx=sess.beginTransaction();
			sess.saveOrUpdate(instance);
			tx.commit();
			sess.close();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Subject instance) {
		log.debug("attaching clean Subject instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Subject persistentInstance) {
		log.debug("deleting Subject instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Subject merge(Subject detachedInstance) {
		log.debug("merging Subject instance");
		try {
			Subject result = (Subject) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Subject findById(java.lang.String id) {
		log.debug("getting Subject instance with id: " + id);
		try {
			Subject instance = (Subject)this.getSession()
					.get("com.tiger.employees.subject.po.Subject", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			re.printStackTrace();
            log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Subject instance) {
		log.debug("finding Subject instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.tiger.employees.subject.po.Subject")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	private final SessionFactory sessionFactory = getSessionFactory();
	
	private Object execSql4singleRetrun(String sql){
		
	    SQLQuery query = this.getSession().createSQLQuery(sql);
		
		return query.uniqueResult();
		
	}

	public String getTotalDirectChildsNumber(String parentId) {
		// TODO Auto-generated method stub
		String sql="select count(*) from subject where parent_id= '"+parentId+"'";
		String result= this.execSql4singleRetrun(sql).toString();
		return result;
	}

	/**
	 * initialized the subject data
	 */
	public String insertInitSubjectData(String companyId) {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(companyId)){
			return Action.ERROR;
		}
		String sql="insert into subject (id,org_id,code,comment,parent_id,is_leaf) select uuid(),'"+companyId+"',code,comment,parent_id,is_leaf from subject where org_id='smallcorp'";
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
