package com.tiger.employees.subject.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.subject.bo.SubjectBo;
import com.tiger.employees.subject.po.Subject;
import com.tiger.employees.subject.po.SubjectHome;
import com.tiger.utilities.StringUtil;
import com.tiger.utilities.TimeUtil;

public class SubjectAction extends BaseAction {

	@Autowired
	private SubjectBo subjectBo;
	private static final Log log = LogFactory.getLog(SubjectHome.class);
	
	private JSON msg;
	
	private JSONArray msgArray;
	
	private Map dataMap;
	
	private List dataList;
	
	private String subjectName;
	
	private String parentCode;
	
	private String id;
	
	private String parentId;
	
	private String subjectCode;
	
	private String subjectId;
	
	private String isLeaf;
	
	private String code;
	
	public String generateTree(){
		Map param=new HashMap();
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("orgId", companyId);
//		param.put("All", true);
		List<Subject> subjcets=this.subjectBo.getSubjects(param);
		dataList=this.createTree(subjcets, "1");
		return Action.SUCCESS;
		
	}
	
	
	
	private List createTree(List<Subject> subjects,String parentId){
		 List<Map<String, Object>> lmjson =new ArrayList<Map<String,Object>>();// 集合对象保存多个构造的map   
		 Map<String, Object> jsonMap = null; //map对象模拟json键值对    
  		 for(Subject subject:subjects){
		    if(parentId.equals(subject.getParentId())){//判断父节点是否相同，把相同的，载入一个组            
		     jsonMap = new HashMap<String, Object>();     
		     jsonMap.put("id", subject.getId());     
		     jsonMap.put("text", subject.getComment());            
		       if(subject.getIsLeaf().equals("0")){//=1表示有子节点       
		          jsonMap.put("state", "open");        
 		          jsonMap.put("children", createTree(subjects,subject.getId()));//载 入子节点          
		       } 
		     lmjson.add(jsonMap);     
		     }     
		 }
		 
		 return lmjson;//map转为json  
	}
	
/**
 * prepare for the page of insert a new subject
 * 	
 * @return
 */
	public String  forInsert(){
		parentId=request.getParameter("nodeId");
		if(StringUtil.isEmpty(parentId)){
			parentId="root";
			
		}
		System.out.println("parentId is "+parentId);
		
		//generate the primary key
		subjectId=UUID.randomUUID().toString();
		
		String postfix=this.subjectBo.getTotalDirectChildsNumber(parentId);
		postfix=String.valueOf((Integer.valueOf(postfix)+1));
		//generate the subject code
		Subject subj=subjectBo.getSubjectById(parentId);
		parentId=subj.getId();
		parentCode=subj.getCode();
		if(postfix.length()<2){
			postfix="0"+""+postfix;
		}
		String newCode=parentCode+"0"+postfix;
		subjectCode=newCode;
		//
		return Action.SUCCESS;
		
	}
	
	public String saveSubject(){
		this.dataMap=new HashMap();
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
        parentCode= request.getParameter("parent");
		String code= request.getParameter("code");
		String comment= request.getParameter("comment");
		if(StringUtil.isEmpty(parentId)){
			this.dataMap.put("status", false);
			this.dataMap.put("info", "parameter is null");
		}
		if(StringUtil.isEmpty(subjectId)){
			this.dataMap.put("status", false);
			this.dataMap.put("info", "parameter is null");
		}
		if(StringUtil.isEmpty(subjectCode)){
			this.dataMap.put("status", false);
			this.dataMap.put("info", "parameter is null");
		}
		if(StringUtil.isEmpty(subjectName)){
			this.dataMap.put("status", false);
			this.dataMap.put("info", "parameter is null");
		}
		Subject subject=new Subject();
		subject.setCode(subjectCode);
		subject.setParentId(parentId);
		subject.setComment(subjectName);
		subject.setId(subjectId);
		subject.setOrgId(companyId);
		
		if(StringUtil.isEmpty(isLeaf)){
			isLeaf="1";
		}
		
		subject.setIsLeaf(isLeaf);
		
		subject.setCreateTime(new Date());
		try{
			this.subjectBo.addSubject(subject);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("comment is "+comment+" code is "+code);
		this.dataMap.put("status", true);
		this.dataMap.put("info", "test");
		return Action.SUCCESS;
		
	}
	
	public String updateSubject(){
		dataMap=new HashMap();
		System.out.println("parent code is "+parentCode);
		System.out.println("subject name is "+subjectName);
		
		Subject subject=this.subjectBo.getSubjectById(subjectId);
		subject.setComment(subjectName);
		this.subjectBo.updateSubject(subject);
		
		this.dataMap.put("status", "1");
		
		this.dataMap.put("info", "test");
		return Action.SUCCESS;
	}

    public String showSubjectDetail(){
    	String nodeId= this.request.getParameter("nodeId");
    	if(StringUtil.isEmpty(nodeId)){
    		return Action.ERROR;
    	}
    	Subject subj=subjectBo.getSubjectById(nodeId);
    	this.subjectName=subj.getComment();
    	this.parentCode=subj.getParentCode();
    	this.subjectId=subj.getId();
    	this.code=subj.getCode();
    	System.out.println("nodeId is "+nodeId);
    	return Action.SUCCESS;
    }
	
	/**
	 * @return the msg 
	 */
	public JSON getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(JSON msg) {
		this.msg = msg;
	}

	/**
	 * @return the msgArray
	 */
	public JSONArray getMsgArray() {
		return msgArray;
	}

	/**
	 * @param msgArray the msgArray to set
	 */
	public void setMsgArray(JSONArray msgArray) {
		this.msgArray = msgArray;
	}

	/**
	 * @return the dataMap
	 */
	public Map getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return the dataList
	 */
	public List getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}



	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}



	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}



	/**
	 * @return the parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}



	/**
	 * @param parentCode the parentCode to set
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}



	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	/**
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}



	/**
	 * @param subjectCode the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}



	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}



	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}



	/**
	 * @return the isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}



	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}



	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}



	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
