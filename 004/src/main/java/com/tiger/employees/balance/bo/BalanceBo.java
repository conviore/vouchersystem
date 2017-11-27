package com.tiger.employees.balance.bo;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tiger.employees.balance.po.Balance;

public interface BalanceBo {

	void saveBalance(Balance balance);
	
	Balance getBalanceById(String id);
	
	List<Balance> getBalances(Map param);
	
	InputStream getBalanceExcel(Map param);
	
	void saveListBalances(List<Balance> balance);
	
	/**
	 * 月结
	 * @param param
	 */
    void endMonSettle(Map param);
	
	/**
	 * 生成科目汇总
	 * @param param
	 * @return
	 */
    List<Balance> generateSubjectSummary(Map param);
	
	
	
	
}
