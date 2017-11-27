package com.tiger.employees.voucherdetail.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.utilities.StringUtil;

public class VoucherDetailDaoImpl extends HibernateDaoSupport implements
		VoucherDetailDao {

	public List<VoucherDetail> getVoucherDetails(Map param) {
		// TODO Auto-generated method stub
		List<VoucherDetail> resultList=null;
		List<Object> valueList=new ArrayList();
		String hql="from VoucherDetail";
		Set ks=param.keySet();
		String queryParm="";
		for(Object key:ks){
			queryParm=queryParm+" and "+ key +" = ? ";
			Object v=param.get(key);
			valueList.add(v);
		}
		queryParm=queryParm+" order by updateTime";
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

	public VoucherDetail getVoucherDetailById(String id) {
		// TODO Auto-generated method stub
		VoucherDetail po=null;
		if(!StringUtil.isEmpty(id)){
			po=this.getHibernateTemplate().get(VoucherDetail.class, id);
		}
		
		return po;
	}

	public void addVoucherDetail(VoucherDetail vd) {
		// TODO Auto-generated method stub
		if(vd!=null){
			if(StringUtil.isEmpty(vd.getId())){
				vd.setId(UUID.randomUUID().toString());
			}
			
			this.getHibernateTemplate().save(vd);
		}
		
	}

	public void updateVoucherDetail(VoucherDetail vd) {
		// TODO Auto-generated method stub
		if(vd!=null){
			this.getHibernateTemplate().saveOrUpdate(vd);
		}
		
	}

	public void addVoucherDetailList(List<VoucherDetail> vdl) {
		// TODO Auto-generated method stub
		for(VoucherDetail vd:vdl){
			if(StringUtil.isEmpty(vd.getId())){
				vd.setId(UUID.randomUUID().toString());
			}
			System.out.println("the insert VoucherDetail ID is "+vd.getId());
			this.addVoucherDetail(vd);
		}
	}

	public void updateVoucherDetailList(List<VoucherDetail> vdl) {
		// TODO Auto-generated method stub
		for(VoucherDetail vd:vdl){
			this.updateVoucherDetail(vd);
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
	
	public void deleteVoucherDetail(VoucherDetail vd) {
		// TODO Auto-generated method stub
		if(vd!=null){
			this.getHibernateTemplate().delete(vd);
		}
	}

	public void deleteVoucherDetailList(List<VoucherDetail> vdl) {
		// TODO Auto-generated method stub
		for(VoucherDetail vd:vdl){
			this.deleteVoucherDetail(vd);
		}
	}

}
