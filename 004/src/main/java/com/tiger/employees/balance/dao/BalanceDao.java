package com.tiger.employees.balance.dao;

import java.util.List;
import java.util.Map;

import com.tiger.employees.balance.po.Balance;

public interface BalanceDao {
	
	void addBalance(Balance balance);
	
	void addListBalance(List<Balance> balance);
	
	void updateBalance(Balance balance);
	
	void updateListBalance(List<Balance> balance);
	
	Balance getBalance(String balanceId);
	
	List<Balance> getListBalance(Map params);
	
	List<Object> execSql(String sql);
	
	
}
