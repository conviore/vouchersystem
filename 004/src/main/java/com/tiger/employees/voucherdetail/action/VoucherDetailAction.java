package com.tiger.employees.voucherdetail.action;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.settledetail.po.SettleDetail;
import com.tiger.employees.subject.po.Subject;
import com.tiger.employees.voucher.bo.VoucherBo;
import com.tiger.employees.voucher.po.Voucher;
import com.tiger.employees.voucherdetail.bo.VoucherDetailBo;
import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.employees.voucherdetail.vo.VoucherDetailVo;
import com.tiger.utilities.StringUtil;
import com.tiger.utilities.TimeUtil;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;

public class VoucherDetailAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5808580111149074297L;

	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;
	private List<VoucherDetail> detailList=null;
	private Map<String, Object> detailMap=null;
	private List<VoucherDetail> voucherDetails=null;
	private List<VoucherDetailVo> dataList;
	private Map<String, Object> dataMap;
	
	private String filename;
	
	private InputStream downloadStream;
	
	
	
	@Autowired
	private VoucherDetailBo voucherDetailBo;
	
	@Autowired
	private VoucherBo voucherBo;
	
	
	
	public String queryVoucherDetails(){
		Map param=new HashMap();
		this.dataList=new ArrayList();
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		param.put("orgId", companyId);
		String voucherId = request.getParameter("voucherId");
		param.put("voucherId", voucherId);
		this.dataList=this.voucherDetailBo.getDetailListVo(param);
		
		return Action.SUCCESS;
		
	}
	
	public String saveVoucherDetails(){
		String deleted = this.request.getParameter("deleted");
		String inserted = this.request.getParameter("inserted");
		String updated = this.request.getParameter("updated");
		String voucherId=this.request.getParameter("voucherId");
		String settleTime=this.request.getParameter("settleTime");
		
		String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		
		if(!StringUtil.isEmpty(updated)){
			List<VoucherDetail> listUpdated = JSON.parseArray(updated, VoucherDetail.class);
			
			this.voucherDetailBo.modifyDetailList(listUpdated);
            System.out.println("update list size is :"+listUpdated.size());
		}
		
		if(!StringUtil.isEmpty(inserted)){
			List<VoucherDetail> listInserted = JSON.parseArray(inserted, VoucherDetail.class);
			System.out.println("insert list size is :"+listInserted.size());
			try{
				for(VoucherDetail vd:listInserted){
					vd.setVoucherId(voucherId);
					vd.setOrgId(companyId);
					vd.setSettleTime(settleTime);
					vd.setUpdateTime(TimeUtil.getCurrentTime17ByMillis());
				}
				this.voucherDetailBo.insertDetailList(listInserted);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(!StringUtil.isEmpty(deleted)){
			List<VoucherDetail> listDeleted = JSON.parseArray(deleted, VoucherDetail.class);
			this.voucherDetailBo.deleteDetailList(listDeleted);
			System.out.println("delete list size is :"+listDeleted.size());
		}
		
		dataMap=new HashMap();
		dataMap.put("status", true);
			
			return Action.SUCCESS;
	}
	
	public String getVoucherInfo(){
		this.dataMap=new HashMap();
		String voucherId=request.getParameter("voucherId");
		if(voucherId!=null){
			Voucher v=this.voucherBo.getVoucherById(voucherId);
			if(v!=null){
				dataMap.put("voucherNo", v.getVoucherNo().toString());
				dataMap.put("settleTime", v.getSettleTime().toString());
				dataMap.put("debitSum", v.getDebitSum().toString());
				dataMap.put("creditSum", v.getCreditSum().toString());
			}
		}
		
		return Action.SUCCESS;
	}
	
	public String getVoucherSummaryExcel(){
		Map param=new HashMap();
		String voucherId=this.request.getParameter("voucherId");
		String settleTime=null;
		Voucher v=this.voucherBo.getVoucherById(voucherId);
		String voucherNo=null;
		if(v!=null){
			voucherNo=v.getVoucherNo();
			settleTime=v.getSettleTime();
		}
		String orgId = (String) ServletActionContext.getServletContext().getAttribute("companyId");
		String companyName = (String) ServletActionContext.getServletContext().getAttribute("companyName");
		param.put("companyId", orgId);
		param.put("companyName", companyName);
		param.put("voucherId", voucherId);
		param.put("companyId", orgId);
		param.put("settleTime", settleTime);
		this.setDownloadStream(voucherDetailBo.getVoucherSummaryExcel(param));
		String newFilename=companyName+"凭证"+settleTime+"编号"+voucherNo+"_"+TimeUtil.getCurrentTime17ByMillis().substring(0, 12)+".xls";
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
	 * @return the dataList
	 */
	public List<VoucherDetailVo> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<VoucherDetailVo> dataList) {
		this.dataList = dataList;
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
