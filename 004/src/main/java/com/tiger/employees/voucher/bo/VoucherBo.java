package com.tiger.employees.voucher.bo;

import java.util.List;
import java.util.Map;

import com.tiger.employees.voucher.po.Voucher;

public interface VoucherBo {

	List<Voucher> getVoucherList(Map param);
	
	Voucher getVoucherById(String id);
	
	void insertVoucher(Voucher msp);
	
	void modifyVoucher(Voucher msp);
	
	void deleteVoucher(Voucher v);
}
