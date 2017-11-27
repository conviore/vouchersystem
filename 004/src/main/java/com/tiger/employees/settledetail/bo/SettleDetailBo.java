package com.tiger.employees.settledetail.bo;

import java.util.List;
import java.util.Map;

import com.tiger.employees.settledetail.po.SettleDetail;

public interface SettleDetailBo {

	void saveDetail(SettleDetail po);
	
	void deleteDetail(SettleDetail po);
	
	void updateDetail(SettleDetail po);
	
	List<SettleDetail> getDetailList(Map param);
	
	void insertDetailList(List<SettleDetail> SettleDetails);
	
	void updateDetailList(List<SettleDetail> SettleDetails);
	
	void deleteDetailList(List<SettleDetail> SettleDetails);
	
	
	
}
