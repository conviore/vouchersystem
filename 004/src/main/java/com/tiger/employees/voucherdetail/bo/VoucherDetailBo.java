package com.tiger.employees.voucherdetail.bo;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.employees.voucherdetail.vo.VoucherDetailVo;

public interface VoucherDetailBo {
List<VoucherDetail> getDetailList(Map param);
	
	List<VoucherDetailVo> getDetailListVo(Map param);
	
	VoucherDetail getDetailById(String id);
	
	void insertVoucherDetail(VoucherDetail vd);
	
	void modifyVoucherDetail(VoucherDetail vd);
	
	void deleteVoucherDetail(VoucherDetail vd);
	
	void insertDetailList(List<VoucherDetail> vdl);
	
	void modifyDetailList(List<VoucherDetail> vdl);
	
	void deleteDetailList(List<VoucherDetail> vdl);
	
	InputStream getVoucherSummaryExcel(Map param);
	
	List<VoucherDetailVo> getVoucherSummary(Map param);
}
