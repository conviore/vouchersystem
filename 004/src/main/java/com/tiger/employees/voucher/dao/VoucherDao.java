package com.tiger.employees.voucher.dao;

import java.util.List;
import java.util.Map;

import com.tiger.employees.voucher.po.Voucher;

public interface VoucherDao {
	List<Voucher> getVouchers(Map param);
	
	Voucher getVoucherById(String id);
	
	void addVoucher(Voucher msp);
	
	void updateVoucher(Voucher msp);
	
	void deleteVoucher(Voucher msp);
	
	List<Object> execSql(String sql);

}
