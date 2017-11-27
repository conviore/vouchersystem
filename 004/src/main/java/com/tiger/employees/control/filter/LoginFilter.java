package com.tiger.employees.control.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionContext;
import com.tiger.utilities.StringUtil;

public class LoginFilter implements Filter, ServletContextAware, ServletRequestAware, ServletResponseAware{

	 protected static final Logger log = Logger.getLogger(LoginFilter.class); 
	 private static final long serialVersionUID = 1L; 
	 private javax.servlet.http.HttpServletRequest request; 
	 private javax.servlet.http.HttpServletResponse response; 
	 private javax.servlet.ServletContext servletContext;
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;  
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;  
        String requestUri = httpServletRequest.getRequestURI();
        if(requestUri.endsWith("loginCompany.html")){
        	chain.doFilter(request, response);
        	return;
        }
        System.out.println("requestUril is  "+requestUri);
        String companyId=(String) this.servletContext.getAttribute("companyId");
        System.out.println("filter find the companyId is "+companyId);
        if(StringUtil.isEmpty(companyId)){
        	httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/loginCompany.html");  
            return;  
        }
        
        chain.doFilter(request, response);  
        

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.servletContext=arg0.getServletContext();

	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
		
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
		
	}

	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		this.servletContext=arg0;
	}

}
