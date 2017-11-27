/**
 * 
 */
package com.tiger.employees.employees.dao;

import java.util.List;
import java.util.Map;

import com.tiger.employees.employees.po.Employees;

/**
 * @author zx
 *
 */
public interface EmployeesDao {

	int delete(String id);
	
	Employees getEmpById(String id);
	
	List<Employees> getEmps(Map params);
	
	void addEmp(Employees emp);
	
}
