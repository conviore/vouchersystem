package com.tiger.employees.monthdetail.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.monthdetail.bo.MonthDeatailBo;
import com.tiger.employees.monthdetail.vo.MonthDetailVo;
import com.tiger.employees.subject.po.Subject;
import com.tiger.utilities.TimeUtil;

public class MonthDetailAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 161343186564770951L;

	@Autowired
	private MonthDeatailBo monthDetailBo;
	
	private List<MonthDetailVo> dataList;
	
	private List<Subject> subjectList;
	
	private Map<String, Object> dataMap;
	
	private InputStream downloadStream;
	
	private String filename;

	public String getMonthDetailList(){

		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		String subjectId=this.request.getParameter("subjectId");
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("settleTime", settleTime);
		param.put("companyId", orgId);
		param.put("subjectId", subjectId);
		//this.dataList=this.monthDetailBo.getMonthDetailList(param);
		this.dataList=this.monthDetailBo.getMonDetailList(param);

		return Action.SUCCESS;
		
	}
	
	public String getMonthDetailExcel(){
		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		String subjectId=this.request.getParameter("subjectId");
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		String companyName = (String) ServletActionContext.getServletContext().getAttribute("companyName");
		param.put("settleTime", settleTime);
		param.put("companyId", orgId);
		param.put("subjectId", subjectId);
		param.put("companyName", companyName);
		this.downloadStream=this.monthDetailBo.getMonthDetailExcel(param);
		String subjectName=this.monthDetailBo.getSubjectName(subjectId);
		String newFilename=companyName+settleTime+subjectName+"科目明细"+TimeUtil.getCurrentTime17ByMillis().substring(0, 12)+".xls";
		try {
			newFilename=new String(newFilename.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setFilename(newFilename);
		return Action.SUCCESS;
		
	}
	
	public String getSubjectComboxData(){
		Map param=new HashMap();
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("orgId", companyId);
		this.subjectList=this.monthDetailBo.getSubject4Combolbox(param);
		return Action.SUCCESS;
	}
	
	
	/**
	 * @return the dataList
	 */
	public List<MonthDetailVo> getDataList() {
		return dataList;
	}

	/**
	 * @return the dataMap
	 */
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<MonthDetailVo> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return the downloadStream
	 */
	public InputStream getDownloadStream() {
		return downloadStream;
	}

	/**
	 * @param downloadStream the downloadStream to set
	 */
	public void setDownloadStream(InputStream downloadStream) {
		this.downloadStream = downloadStream;
	}

	/**
	 * @return the subjectList
	 */
	public List<Subject> getSubjectList() {
		return subjectList;
	}

	/**
	 * @param subjectList the subjectList to set
	 */
	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
}
