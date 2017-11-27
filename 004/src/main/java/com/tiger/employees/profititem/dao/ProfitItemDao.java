package com.tiger.employees.profititem.dao;

import com.tiger.employees.balanceitem.po.BalanceItem;
import com.tiger.employees.profititem.po.ProfitItem;

import java.util.List;
import java.util.Map;

/**
 * Created by zx on 6/25/17.
 */
public interface ProfitItemDao {
    List<ProfitItem> getProfitItems(Map param);
}
