package com.tiger.employees.balanceitem.dao;

import java.util.List;
import java.util.Map;

import com.tiger.employees.balanceitem.po.BalanceItem;

public interface BalanceItemDao {

	List<BalanceItem> getBalanceItems(Map param);
	
}
