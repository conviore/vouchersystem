package com.tiger.employees.voucher.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.voucher.po.Voucher;
import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.utilities.StringUtil;

public class VoucherDaoImpl extends HibernateDaoSupport implements VoucherDao {

	public List<Voucher> getVouchers(Map param) {
		// TODO Auto-generated method stub
		List<Voucher> resultList=null;
		String hql="from Voucher ";
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
		queryParm=queryParm+" order by voucher_no";
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

	public Voucher getVoucherById(String id) {
		// TODO Auto-generated method stub
		Voucher po=null;
		if(!StringUtil.isEmpty(id)){
			po=this.getHibernateTemplate().get(Voucher.class, id);
		}
		
		return po;
	}

	public void addVoucher(Voucher v) {
		// TODO Auto-generated method stub
		if(v!=null){
			this.getHibernateTemplate().save(v);
		}
		
	}

	public void updateVoucher(Voucher v) {
		// TODO Auto-generated method stub
		if(v!=null){
			this.getHibernateTemplate().saveOrUpdate(v);
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

	public void deleteVoucher(Voucher v) {
		// TODO Auto-generated method stub
		if(v!=null){
			this.getHibernateTemplate().delete(v);
		}
	}

}
