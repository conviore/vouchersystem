package com.tiger.employees.voucher.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.voucher.bo.VoucherBo;
import com.tiger.employees.voucher.po.Voucher;
import com.tiger.utilities.StringUtil;
import com.tiger.utilities.TimeUtil;

public class VoucherAction extends BaseAction {
	
	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;
	private List<Voucher> voucherList;
	private Map<String, Object> msgMap=null;
	private List<Voucher> subjects=null;
	private List<Voucher> dataList;
	private Map dataMap;
	/**
	 * @return the msgMap
	 */
	public Map<String, Object> getMsgMap() {
		return msgMap;
	}

	/**
	 * @return the dataMap
	 */
	public Map getDataMap() {
		return dataMap;
	}

	/**
	 * @param msgMap the msgMap to set
	 */
	public void setMsgMap(Map<String, Object> msgMap) {
		this.msgMap = msgMap;
	}

	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	
	@Autowired
	private VoucherBo voucherBo;
	
	public String getVoucherList(){
		
		String queryTime=request.getParameter("time");
		
		this.dataList=new ArrayList();
		Map param=new HashMap();
		if(queryTime!=null){
			param.put("settleTime", queryTime+"%");
		}
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("orgId", companyId);
		this.dataList=this.voucherBo.getVoucherList(param);
		//System.out.println("dataList's size is "+dataList.size());
		
		return Action.SUCCESS;
	}
	
	public String saveVoucher(){
		
		Enumeration names=request.getParameterNames();
		while(names.hasMoreElements()){
			String name=(String)names.nextElement();
			String  value=request.getParameter(name);
			System.out.print(name+"="+value);
		}
		Voucher newVoucher=new Voucher();
		String voucherPK=UUID.randomUUID().toString();
		newVoucher.setId(voucherPK);
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		newVoucher.setOrgId(companyId);
		newVoucher.setCreditSum(new BigDecimal("0"));
		newVoucher.setDebitSum(new BigDecimal("0"));
		newVoucher.setDiff(new BigDecimal("0"));
		
		String voucherDate=request.getParameter("voucherDate");
		String voucherNumber=request.getParameter("voucherNumber");
		if(!StringUtil.isEmpty(voucherDate)){
			String[] va=voucherDate.split("-");
			voucherDate=va[0]+va[1]+va[2];
			newVoucher.setSettleTime(voucherDate);
		}
		
		if(!StringUtil.isEmpty(voucherNumber)){
			newVoucher.setVoucherNo(voucherNumber);
        }
		
		newVoucher.setUpdateTime(TimeUtil.getCurrentTime17ByMillis());
		this.voucherBo.insertVoucher(newVoucher);
		this.dataMap=new HashMap();
		this.dataMap.put("status", true);
		this.dataMap.put("info", "保存成功");
		return Action.SUCCESS;
	}
	
	public String updateVoucher(){
		
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		String t2=request.getParameter("t2");
		t2=(String) request.getAttribute("t2");
		Enumeration names=request.getParameterNames();
		while(names.hasMoreElements()){
			String name=(String)names.nextElement();
			String  value=request.getParameter(name);
			System.out.print(name+"="+value);
		}
		String voucherId=request.getParameter("voucherId2");
		String voucherDate=request.getParameter("voucherDate2");
		String voucherNumber=request.getParameter("voucherNo2");
		String comment=request.getParameter("commentn");
		Voucher v=this.voucherBo.getVoucherById(voucherId);
		if(v==null){
			this.dataMap=new HashMap();
			this.dataMap.put("status", true);
			this.dataMap.put("info", "no record");
			return Action.SUCCESS;
		}
		
		if(!StringUtil.isEmpty(voucherDate)){
			v.setSettleTime(voucherDate);
		}
		
		if(!StringUtil.isEmpty(voucherNumber)){
			v.setVoucherNo(voucherNumber);
        }
		
		if(!StringUtil.isEmpty(comment)){
			v.setComment(comment);
		}
		
		v.setUpdateTime(TimeUtil.getCurrentTime17ByMillis());
		this.voucherBo.modifyVoucher(v);
		this.dataMap=new HashMap();
		this.dataMap.put("status", true);
		this.dataMap.put("info", "保存成功");
		return Action.SUCCESS;
	}
	
	public String deleteVoucher(){
		this.dataMap=new HashMap();
		String voucherId=request.getParameter("voucherId");
		if(StringUtil.isEmpty(voucherId)){
			this.dataMap.put("status", false);
			this.dataMap.put("info", "voucherId is null");
			return Action.SUCCESS;
		}
		
		Voucher v=this.voucherBo.getVoucherById(voucherId);
		if(v==null){
			this.dataMap.put("status", false);
			this.dataMap.put("info", "there is no record about this voucherId");
			return Action.SUCCESS;
		}
		
		this.voucherBo.deleteVoucher(v);
		this.dataMap.put("status", true);
		this.dataMap.put("info", "删除成功");
		
		return Action.SUCCESS;
	}
	
	public String testVoucher(){
		dataMap=new HashMap();
//		this.companyBo.saveCompany(comp);
		this.dataMap.put("status", true);
		this.dataMap.put("info", "test");
		System.out.println("in TestVoucher action");
		return Action.SUCCESS;
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
	 * @param voucherList the voucherList to set
	 */
	public void setVoucherList(List<Voucher> voucherList) {
		this.voucherList = voucherList;
	}
}
