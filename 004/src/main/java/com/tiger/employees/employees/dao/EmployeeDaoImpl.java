package com.tiger.employees.employees.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.tiger.employees.employees.po.Employees;

@Repository 
public class EmployeeDaoImpl extends HibernateDaoSupport implements
		EmployeesDao {

	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Employees getEmpById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Employees> getEmps(Map params) {
		// TODO Auto-generated method stub
		String sql="select * from employees where emp_no='10001'";
		Session sess=this.getSession();
		SQLQuery query=sess.createSQLQuery(sql);
		List<Object[]> objectList = query.list(); 
		Employees emp=new Employees();
		Object[] oa= objectList.get(0);
		Number n=(Number) oa[0];
		emp.setEmpNo(n.longValue());
		emp.setBirthDate(new Date());
		emp.setFirstName((String) oa[2]);
		emp.setLastName((String) oa[3]);
		Character c=(Character) oa[4];
		String cs=c.toString();
		emp.setGender(cs);
		emp.setHireDate(new Date());

		List<Employees> resultList = new ArrayList(); 
		resultList.add(emp);
		return resultList;
	}

	public void addEmp(Employees emp) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(emp);
	}

}
