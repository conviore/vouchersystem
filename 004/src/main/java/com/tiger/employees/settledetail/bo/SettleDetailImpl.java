package com.tiger.employees.settledetail.bo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiger.employees.settledetail.dao.SettleDetailDao;
import com.tiger.employees.settledetail.po.SettleDetail;

import java.util.UUID;

public class SettleDetailImpl implements SettleDetailBo {

	@Autowired
	private SettleDetailDao settleDetailDao;
	
	public void saveDetail(SettleDetail po) {
		// TODO Auto-generated method stub
		this.settleDetailDao.save(po);
	}

	public void deleteDetail(SettleDetail po) {
		// TODO Auto-generated method stub

		this.settleDetailDao.delete(po);
	}

	public void updateDetail(SettleDetail po) {
		// TODO Auto-generated method stub

		this.settleDetailDao.update(po);
	}

	public List<SettleDetail> getDetailList(Map param) {
		// TODO Auto-generated method stub
		
		return this.settleDetailDao.queryList(param);

	}

	public void insertDetailList(List<SettleDetail> SettleDetails) {
		// TODO Auto-generated method stub
		//this.settleDetailDao.beginTrans();
		if(SettleDetails.isEmpty()){
			return;
		}
		
		for(SettleDetail po :SettleDetails){
			UUID uuid=UUID.randomUUID();
			String DetailId=uuid.toString();
			po.setSettleId("1002001");
			po.setId(DetailId);
			po.setUpdateTime((new Date()).toString());
			po.setSettleTime((new Date()).toString());
			this.saveDetail(po);
			
		}
		//this.settleDetailDao.commitTrans();
	}

	public void updateDetailList(List<SettleDetail> SettleDetails) {
		// TODO Auto-generated method stub
		try{
		if(SettleDetails.isEmpty()){
			return;
		}
		for(SettleDetail po :SettleDetails){
			po.setUpdateTime((new Date()).toString());
			//this.settleDetailDao.attachDirty(po);;
			this.settleDetailDao.update(po);
			
		}
		}
		catch(Exception e){
		   e.printStackTrace();
		}
		
	}

	public void deleteDetailList(List<SettleDetail> SettleDetails) {
		// TODO Auto-generated method stub
		if(SettleDetails.isEmpty()){
			return;
		}
		
	}
	


}
