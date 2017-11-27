package com.tiger.employees.monsettleprog.bo;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tiger.employees.monsettleprog.po.MonthSettleProgress;

public interface MonthSettleProgressBo {

	List<MonthSettleProgress> getMSPs(Map param);
	
	MonthSettleProgress getMSPById(String id);
	
	void addMSP(MonthSettleProgress msp);
	
	void updateMSP(MonthSettleProgress msp);
	
	//月结后开始新的月份结帐
    void addMonSettleProg(Map param);
	
	InputStream getBalanceSheetExcel(Map param);
	
	void initAccountingDirection();
	
	InputStream getProfitSheetExcel(Map param);
	
	InputStream getVoucherSummaryExcel(Map param);
	
	
}
