package com.tiger.employees.voucher.po;

// Generated Oct 29, 2014 4:58:45 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Voucher.
 * @see com.tiger.employees.voucher.po.Voucher
 * @author Hibernate Tools
 */
public class VoucherHome {

	private static final Log log = LogFactory.getLog(VoucherHome.class);

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

	public void persist(Voucher transientInstance) {
		log.debug("persisting Voucher instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Voucher instance) {
		log.debug("attaching dirty Voucher instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Voucher instance) {
		log.debug("attaching clean Voucher instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Voucher persistentInstance) {
		log.debug("deleting Voucher instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Voucher merge(Voucher detachedInstance) {
		log.debug("merging Voucher instance");
		try {
			Voucher result = (Voucher) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Voucher findById(java.lang.String id) {
		log.debug("getting Voucher instance with id: " + id);
		try {
			Voucher instance = (Voucher) sessionFactory.getCurrentSession()
					.get("com.tiger.employees.voucher.po.Voucher", id);
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

	public List findByExample(Voucher instance) {
		log.debug("finding Voucher instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.tiger.employees.voucher.po.Voucher")
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
