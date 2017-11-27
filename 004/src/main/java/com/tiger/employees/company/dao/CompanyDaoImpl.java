package com.tiger.employees.company.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.tiger.employees.company.po.Company;

public class CompanyDaoImpl extends HibernateDaoSupport implements CompanyDao {

	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Company getCompanyById(String id) {
		// TODO Auto-generated method stub
		Company comp = this.getHibernateTemplate().get(Company.class, id);
		return comp;
	}

	public List<Company> getCompanies(Map params) {
		// TODO Auto-generated method stub
		List<Company> resultList=this.getHibernateTemplate().find("from Company");
		return resultList;
		
	}

	public void addCompany(Company comp) {
		// TODO Auto-generated method stub
		try{
		if(comp!=null){
			this.getHibernateTemplate().save(comp);
		}}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public void updateCompany(Company comp) {
		// TODO Auto-generated method stub
		if(comp!=null){
			this.getHibernateTemplate().update(comp);
		}
		

	}

}
