package com.tiger.employees.control.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.company.bo.CompanyBo;
import com.tiger.employees.company.po.Company;
import com.tiger.utilities.StringUtil;

public class LoginAction extends BaseAction {
	
	@Autowired
	CompanyBo companyBo;
	
	private Map<String, Object> dataMap=null;

	/**
	 * register the company we want to account
	 * @return
	 */
	public String registerCompany(){
		
		dataMap=new HashMap();
		String companyId=this.request.getParameter("companyId");
		if(StringUtil.isEmpty(companyId)){
			dataMap.put("status", false);
			dataMap.put("info", "parameter is null");
		}
		
		//if old exists,remove it first
		String oldCompanyId=(String) this.servletContext.getAttribute("companyId");
		if(!StringUtil.isEmpty(oldCompanyId)){
			this.servletContext.removeAttribute("companyId");
		}
		System.out.println("choose success");
		Company comp=this.companyBo.getCompanyById(companyId);
		this.servletContext.setAttribute("companyId", companyId);
		this.servletContext.setAttribute("companyName", comp.getCompanyName());
		dataMap.put("status", true);
		dataMap.put("info", "save success");
		return Action.SUCCESS;
	
	}

	/**
	 * @return the dataMap
	 */
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
}
