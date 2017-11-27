package com.tiger.employees.voucherdetail.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tiger.employees.voucher.po.Voucher;
import com.tiger.employees.voucherdetail.po.VoucherDetail;

public interface VoucherDetailDao {

	List<VoucherDetail> getVoucherDetails(Map param);
	
	VoucherDetail getVoucherDetailById(String id);
	
	void addVoucherDetail(VoucherDetail vd);
	
	void updateVoucherDetail(VoucherDetail vd);
	
	void addVoucherDetailList(List<VoucherDetail> vdl);
	
	void updateVoucherDetailList(List<VoucherDetail> vdl);
	
	List<Object> execSql(String sql);
	
	void deleteVoucherDetail(VoucherDetail vd);
	
	void deleteVoucherDetailList(List<VoucherDetail> vdl);
	
}
