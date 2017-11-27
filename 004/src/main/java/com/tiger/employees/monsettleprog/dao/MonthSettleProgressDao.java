/**
 * 
 */
package com.tiger.employees.monsettleprog.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.utilities.StringUtil;

/**
 * @author zx
 *
 */
public interface MonthSettleProgressDao {
	
	List<MonthSettleProgress> getMSPs(Map param) ;


	MonthSettleProgress getMSPById(String id) ;


	void addMSP(MonthSettleProgress msp) ;


	void updateMSP(MonthSettleProgress msp) ;
	
	List<Object> execSql(String sql);
	
	String execDmlSql(String sql);


}
