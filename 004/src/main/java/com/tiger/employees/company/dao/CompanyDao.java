package com.tiger.employees.company.dao;

import java.util.List;
import java.util.Map;

import com.tiger.employees.company.po.Company;
import com.tiger.employees.employees.po.Employees;

public interface CompanyDao {
	int delete(String id);
	
	Company getCompanyById(String id);
	
	List<Company> getCompanies(Map params);
	
	void addCompany(Company comp);
	
	void updateCompany(Company comp);
}
