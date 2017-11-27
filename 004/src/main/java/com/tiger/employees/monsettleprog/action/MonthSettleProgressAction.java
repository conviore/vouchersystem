package com.tiger.employees.monsettleprog.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.balance.bo.BalanceBo;
import com.tiger.employees.monsettleprog.bo.MonthSettleProgressBo;
import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.voucher.po.Voucher;
import com.tiger.utilities.SettleConfig;
import com.tiger.utilities.TimeUtil;
import org.springframework.stereotype.Controller;

public class MonthSettleProgressAction extends BaseAction {


	private List<MonthSettleProgress> dataList;
	private Map<String, Object> msgMap;
	private Map dataMap;
	
	private String filename;
	
	private InputStream downloadStream;

	private InputStream downloadProfitStream;
	
	@Autowired
	private MonthSettleProgressBo monSettleProgBo;

	@Autowired
	private BalanceBo balanceBo;
	
	public String getMonSettleProgList(){
		dataList=new ArrayList();
		Map param=new HashMap();
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("companyId", companyId);
		dataList=this.monSettleProgBo.getMSPs(param);
		return Action.SUCCESS;
	}
	
	public String endMonSettle(){
		Map param=new HashMap();
		this.dataMap=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("settleTime", settleTime);
		param.put("companyId", orgId);
		this.balanceBo.endMonSettle(param);
		this.dataMap.put("status",true);
		this.dataMap.put("msg", "月结成功");
		return Action.SUCCESS;
	}
	
	//开始新的月记账
	public String addNewMonSettle(){
		Map param=new HashMap();
		this.dataMap=new HashMap();
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		//查看是否有没月结
		param.put("status", SettleConfig.MON_SETTLE_INCOMPLETE);
		param.put("companyId", orgId);
		List<MonthSettleProgress> mspList=this.monSettleProgBo.getMSPs(param);
		if(mspList.size()>0){
			this.dataMap.put("status",true);
	        this.dataMap.put("msg", "还有没月结的月份，请先月结");
			return Action.SUCCESS;
		}
		else{
			param.clear();
			param.put("companyId", orgId);
			this.monSettleProgBo.addMonSettleProg(param);
			this.dataMap.put("msg", "添加成功");
		}
		return Action.SUCCESS;
	}

	public String getBalanceSheetExcel(){
		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		param.put("settleTime", settleTime);
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		String companyName = (String) ServletActionContext.getServletContext().getAttribute("companyName");
		param.put("orgId", orgId);
		
		this.setDownloadStream(monSettleProgBo.getBalanceSheetExcel(param));
		String newFilename=companyName+"资产负债表"+settleTime+"_"+TimeUtil.getCurrentTime17ByMillis().substring(0, 12)+".xls";
		try {
			newFilename=new String(newFilename.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setFilename(newFilename);
		return Action.SUCCESS;
	}

	public String getProfitSheetExcel(){
		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		param.put("settleTime", settleTime);
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		String companyName = (String) ServletActionContext.getServletContext().getAttribute("companyName");
		param.put("orgId", orgId);

		this.setDownloadProfitStream(monSettleProgBo.getProfitSheetExcel(param));
		String newFilename=companyName+"利润表"+settleTime+"_"+TimeUtil.getCurrentTime17ByMillis().substring(0, 12)+".xls";
		try {
			newFilename=new String(newFilename.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setFilename(newFilename);
		return Action.SUCCESS;
	}

	public String getVSRExcel(){
		Map param=new HashMap();
		String settleTime=this.request.getParameter("settleTime");
		param.put("settleTime", settleTime);
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		String companyName = (String) ServletActionContext.getServletContext().getAttribute("companyName");
		param.put("orgId", orgId);

		this.setDownloadProfitStream(monSettleProgBo.getVoucherSummaryExcel(param));
		String newFilename=companyName+"记账凭证汇总表"+settleTime+"_"+TimeUtil.getCurrentTime17ByMillis().substring(0, 12)+".xls";
		try {
			newFilename=new String(newFilename.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setFilename(newFilename);
		return Action.SUCCESS;
	}
	
	/**
	 * @return the msgMap
	 */
	public Map<String, Object> getMsgMap() {
		return msgMap;
	}

	/**
	 * @param msgMap the msgMap to set
	 */
	public void setMsgMap(Map<String, Object> msgMap) {
		this.msgMap = msgMap;
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
	public List<MonthSettleProgress> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<MonthSettleProgress> dataList) {
		this.dataList = dataList;
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

	public InputStream getDownloadProfitStream() {
		return downloadProfitStream;
	}

	public void setDownloadProfitStream(InputStream downloadProfitStream) {
		this.downloadProfitStream = downloadProfitStream;
	}
}
