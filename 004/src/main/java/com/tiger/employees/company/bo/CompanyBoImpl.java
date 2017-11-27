package com.tiger.employees.company.bo;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiger.employees.company.dao.CompanyDao;
import com.tiger.employees.company.po.Company;
import com.tiger.employees.subject.dao.SubjectDao;
import com.tiger.utilities.StringUtil;
import com.tiger.utilities.TimeUtil;

public class CompanyBoImpl implements CompanyBo {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private SubjectDao subjectDao;

	public List<Company> getListCompany() {
		// TODO Auto-generated method stub
		List<Company> resultList=this.companyDao.getCompanies(new HashMap());
		return resultList;
	}

	public Company getCompanyById(String id) {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(id)){
			return null;
		}
		Company comp=this.companyDao.getCompanyById(id);
		return comp;
	}

	public void saveCompany(Company comp) {
		// TODO Auto-generated method stub
		String companyId=UUID.randomUUID().toString();
		comp.setId(companyId);
		comp.setCreateTime(TimeUtil.getCurrentTime17ByMillis());
		if(comp!=null){
			this.companyDao.addCompany(comp);
			//initialized the subject data
			this.subjectDao.insertInitSubjectData(companyId);
		}
	}

	public void updateCompany(Company comp) {
		// TODO Auto-generated method stub

	}

}
