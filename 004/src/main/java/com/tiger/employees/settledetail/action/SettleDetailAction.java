package com.tiger.employees.settledetail.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.io.PrintStream;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.settledetail.bo.SettleDetailBo;
import com.tiger.employees.settledetail.po.SettleDetail;
import com.tiger.employees.subject.bo.SubjectBo;
import com.tiger.employees.subject.po.Subject;
import com.tiger.utilities.StringUtil;

public class SettleDetailAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2855981075175415680L;

	@Autowired
	SettleDetailBo settleDetailBo;
	
	@Autowired
	SubjectBo subjectBo;
	
	
	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;
	private List<SettleDetail> detailList=null;
	private Map<String, Object> detailMap=null;
	private List<Subject> subjects=null;
	
	
	public String queryDetails(){
		objectMapper=new ObjectMapper();
		detailMap=new HashMap<String, Object>();
		try{
            PrintStream out=System.out;
			this.jsonGenerator=this.objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
			List<SettleDetail> resultList=this.settleDetailBo.getDetailList(new HashMap<Object, Object>());
			detailList=resultList;
			this.jsonGenerator.writeObject(resultList);
			JSONArray json = JSONArray.fromObject(resultList);
			detailMap.put("rows", resultList);
			detailMap.put("total", resultList.size());

            System.out.println();

		}catch (Exception e){
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String querySubject4Details(){
		Map param=new HashMap();
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("orgId", companyId);
		List<Subject> subjectVos=this.subjectBo.getSubjects(param);
		subjects=new ArrayList();		
		for(Subject po:subjectVos){
			po.setCode(po.getCode()+'/'+po.getComment());
			subjects.add(po);
		}
		
		return Action.SUCCESS;
		
	}
	
	public String saveSubjectDetails(){
		
		try {
			this.request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取编辑数据 这里获取到的是json字符串
		String deleted = this.request.getParameter("deleted");
		String inserted = this.request.getParameter("inserted");
		String updated = this.request.getParameter("updated");
		if(!StringUtil.isEmpty(updated)){
		 List<SettleDetail> listUpdated = JSON.parseArray(updated, SettleDetail.class);
		 this.settleDetailBo.updateDetailList(listUpdated);
		 System.out.println("update list size is :"+listUpdated.size());
		}
		if(!StringUtil.isEmpty(inserted)){
			 List<SettleDetail> listInserted = JSON.parseArray(inserted, SettleDetail.class);
			 System.out.println("insert list size is :"+listInserted.size());
			 try{
				 this.settleDetailBo.insertDetailList(listInserted);
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
	    }
		if(!StringUtil.isEmpty(deleted)){
			 List<SettleDetail> listDeleted = JSON.parseArray(deleted, SettleDetail.class);
			 System.out.println("delete list size is :"+listDeleted.size());
		}
		 detailMap=new HashMap();
		 detailMap.put("status", "operate successful");
		
		return Action.SUCCESS;
	}


	/**
	 * @return the detailList
	 */
	public List<SettleDetail> getDetailList() {
		return detailList;
	}


	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(List<SettleDetail> detailList) {
		this.detailList = detailList;
	}


	/**
	 * @return the detailMap
	 */
	public Map<String, Object> getDetailMap() {
		return detailMap;
	}


	/**
	 * @param detailMap the detailMap to set
	 */
	public void setDetailMap(Map<String, Object> detailMap) {
		this.detailMap = detailMap;
	}

	/**
	 * @return the subjects
	 */
	public List<Subject> getSubjects() {
		return subjects;
	}

	/**
	 * @param subjects the subjects to set
	 */
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}


	
	
}
