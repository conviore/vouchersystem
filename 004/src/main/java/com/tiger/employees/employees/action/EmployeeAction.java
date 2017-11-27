package com.tiger.employees.employees.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletOutputStream;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.opensymphony.xwork2.ActionSupport;
import com.tiger.BaseAction;
import com.tiger.employees.employees.bo.EmployeeBo;
import com.tiger.employees.employees.po.Employees;

public class EmployeeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1062499935294498941L;
	@Autowired
	EmployeeBo employeeBo;
	
	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;
	private List<Employees> empList=null;
	private Map empMap=null;
	

	
	public String getList(){
		
	objectMapper=new ObjectMapper();
	try{
		this.jsonGenerator=this.objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
		List<Employees> resultList=this.employeeBo.findAllEmployee();
		request.setAttribute("empList", resultList);
		this.jsonGenerator.writeObject(resultList);
	}catch (IOException e){
		e.printStackTrace();
	}
		
		
		return "success";
		
	}
	
	/**
	 * use List object to return json object
	 */
	public String queryEmp(){
		objectMapper=new ObjectMapper();
		try{
			//ServletOutputStream out=this.response.getOutputStream();
			//this.jsonGenerator=this.objectMapper.getJsonFactory().createJsonGenerator(out, JsonEncoding.UTF8);
			List<Employees> resultList=this.employeeBo.findAllEmployee();
			empList=resultList;
			//request.setAttribute("empList", resultList);
			//this.jsonGenerator.writeObject(resultList);
			//out.flush();
			//out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
			
			
			return SUCCESS;

	}
	
	
	/**
	 * use map object to return json object
	 */
	public String queryEmpStr(){
		objectMapper=new ObjectMapper();
		empMap=new HashMap();
		try{
            PrintStream out=System.out;
			this.jsonGenerator=this.objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
			List<Employees> resultList=this.employeeBo.findAllEmployee();
			empList=resultList;
			//request.setAttribute("empList", resultList);
			this.jsonGenerator.writeObject(resultList);
			JSONArray json = JSONArray.fromObject(resultList);
			empMap.put("rows", resultList);
			empMap.put("total", resultList.size());
            //empStr=out.toString();
            System.out.println();
            //this.response.getWriter().write(empStr);
            //response.flushBuffer();
		}catch (Exception e){
			e.printStackTrace();
		}
		return SUCCESS;

	}
	

	public ArrayList getEmpList() {
		return (ArrayList) empList;
	}

	public void setEmpList(ArrayList empList) {
		this.empList = empList;
	}

	public Map getEmpMap() {
		return empMap;
	}

	public void setEmpMap(Map empMap) {
		this.empMap = empMap;
	}




	
}
