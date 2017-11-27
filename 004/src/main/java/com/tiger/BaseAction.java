package com.tiger;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	protected static final String GLOBAL_EXCEPTION="exception";
	private static final long serialVersionUID = 127831821455812235L;
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected Map<String, Object> session = ActionContext.getContext().getSession();
	protected ServletContext servletContext=ServletActionContext.getServletContext();
	public String msg;//页面消息
}
