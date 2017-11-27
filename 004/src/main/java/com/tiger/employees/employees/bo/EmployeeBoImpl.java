package com.tiger.employees.employees.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiger.employees.employees.dao.EmployeesDao;
import com.tiger.employees.employees.po.Employees;

@Service
public class EmployeeBoImpl implements EmployeeBo {

	@Autowired
	private EmployeesDao employeeDao;
	
	public void addEmployee(Employees emp) {
		// TODO Auto-generated method stub
		this.employeeDao.addEmp(emp);
	}

	public List<Employees> findAllEmployee() {
		// TODO Auto-generated method stub
		List<Employees> resultList=this.employeeDao.getEmps(new HashMap());
		return resultList;
	}

}
