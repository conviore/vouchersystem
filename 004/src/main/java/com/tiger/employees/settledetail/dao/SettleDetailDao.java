package com.tiger.employees.settledetail.dao;

import java.util.List;
import java.util.Map;

import com.tiger.employees.settledetail.po.SettleDetail;

public interface SettleDetailDao {
	
	void beginTrans();

	void attachDirty(SettleDetail instance);
	
	void save(SettleDetail settleDetail);
	
	void update(SettleDetail settleDetail);
	
	void delete(SettleDetail settleDetail);
	
	List<SettleDetail> queryList(Map param);
	
	void commitTrans();
		
	
}
