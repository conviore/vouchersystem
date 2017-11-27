package com.tiger.employees.balance.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.tiger.BaseAction;
import com.tiger.employees.balance.bo.BalanceBo;
import com.tiger.employees.balance.po.Balance;
import com.tiger.employees.voucher.po.Voucher;
import com.tiger.utilities.TimeUtil;

public class BalanceAction extends BaseAction {
	
	private List<Balance> dataList;
	private Map dataMap;
	private InputStream downloadStream;
	
	private String filename;
	
	@Autowired 
	private BalanceBo balanceBo;
	
	public String getBalanceSheet(){
		String settleTime=this.request.getParameter("settleTime");
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("l");
		
		
		
		return Action.SUCCESS;
	}
	
	public String getSummaryReport(){
		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("settleTime", settleTime);
		param.put("companyId", orgId);
		dataList=this.balanceBo.generateSubjectSummary(param);
		
		return Action.SUCCESS;
	}
	
	public String getSummaryReportExcel(){
		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		String companyName = (String) ServletActionContext.getServletContext().getAttribute("companyName");
		param.put("companyName", companyName);
		param.put("settleTime", settleTime);
		param.put("companyId", orgId);
		this.downloadStream=balanceBo.getBalanceExcel(param);
		String newFilename=companyName+settleTime+"资产负债表"+TimeUtil.getCurrentTime17ByMillis().substring(0, 12)+".xls";
		try {
			newFilename=new String(newFilename.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setFilename(newFilename);
		return Action.SUCCESS;
	}
	
	public String endMonSettle(){
		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("settleTime", settleTime);
		param.put("companyId", orgId);
		this.balanceBo.endMonSettle(param);		
		return Action.SUCCESS;
	}
	
	/**
	 * @return the dataList
	 */
	public List<Balance> getDataList() {
		return dataList;
	}
	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<Balance> dataList) {
		this.dataList = dataList;
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
