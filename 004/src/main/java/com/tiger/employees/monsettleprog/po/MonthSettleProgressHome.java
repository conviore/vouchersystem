package com.tiger.employees.monsettleprog.po;

// Generated Oct 25, 2014 7:37:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class MonthSettleProgress.
 * @see com.tiger.employees.monsettleprog.po.MonthSettleProgress
 * @author Hibernate Tools
 */
public class MonthSettleProgressHome {

	private static final Log log = LogFactory
			.getLog(MonthSettleProgressHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(MonthSettleProgress transientInstance) {
		log.debug("persisting MonthSettleProgress instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(MonthSettleProgress instance) {
		log.debug("attaching dirty MonthSettleProgress instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MonthSettleProgress instance) {
		log.debug("attaching clean MonthSettleProgress instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(MonthSettleProgress persistentInstance) {
		log.debug("deleting MonthSettleProgress instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MonthSettleProgress merge(MonthSettleProgress detachedInstance) {
		log.debug("merging MonthSettleProgress instance");
		try {
			MonthSettleProgress result = (MonthSettleProgress) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public MonthSettleProgress findById(java.lang.String id) {
		log.debug("getting MonthSettleProgress instance with id: " + id);
		try {
			MonthSettleProgress instance = (MonthSettleProgress) sessionFactory
					.getCurrentSession()
					.get("com.tiger.employees.monsettleprog.po.MonthSettleProgress",
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

	public List findByExample(MonthSettleProgress instance) {
		log.debug("finding MonthSettleProgress instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.tiger.employees.monsettleprog.po.MonthSettleProgress")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
