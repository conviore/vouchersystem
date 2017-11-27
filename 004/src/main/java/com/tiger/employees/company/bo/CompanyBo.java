package com.tiger.employees.company.bo;

import java.util.List;

import com.tiger.employees.company.po.Company;

public interface CompanyBo {

	List<Company> getListCompany();
	
	Company getCompanyById(String id);
	
	void saveCompany(Company comp);

	void updateCompany(Company comp);
		
}
