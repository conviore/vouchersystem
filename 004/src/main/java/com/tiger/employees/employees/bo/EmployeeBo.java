package com.tiger.employees.employees.bo;

import java.util.List;

import com.tiger.employees.employees.po.Employees;

public interface EmployeeBo {

	void addEmployee(Employees emp);
	 
	List<Employees> findAllEmployee();
	
}
