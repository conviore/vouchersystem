package com.tiger.employees.voucher.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiger.employees.voucher.dao.VoucherDao;
import com.tiger.employees.voucher.po.Voucher;
import com.tiger.employees.voucherdetail.dao.VoucherDetailDao;
import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.utilities.StringUtil;

public class VoucherBoImpl implements VoucherBo {

	@Autowired
	private VoucherDao voucherDao;
	
	@Autowired
	private VoucherDetailDao voucherDetailDao;

	public List<Voucher> getVoucherList(Map param) {
		// TODO Auto-generated method stub
		List<Voucher> resultList=null;
		resultList=this.voucherDao.getVouchers(param);
		return resultList;
	}

	public Voucher getVoucherById(String id) {
		// TODO Auto-generated method stub
		Voucher po=null;
		if(!StringUtil.isEmpty(id)){
			po=this.voucherDao.getVoucherById(id);
            String sql="select ifnull(sum(debit),0),ifnull(sum(credit),0) from voucher_detail where voucher_id='"+id+"'";
            List sqlResultL=this.voucherDao.execSql(sql);
            try{
            	BigDecimal debitSum=(BigDecimal)((Object[])sqlResultL.get(0))[0];
            	BigDecimal creditSum=(BigDecimal)((Object[])sqlResultL.get(0))[1];
            po.setDebitSum(debitSum);
            po.setCreditSum(creditSum);
            this.voucherDao.updateVoucher(po);
            }catch(Exception e){
            	e.printStackTrace();
            }
		}
		
		
		return po;
	}

	public void insertVoucher(Voucher po) {
		// TODO Auto-generated method stub
		if(po!=null){
			this.voucherDao.addVoucher(po);
		}
		
	}

	public void modifyVoucher(Voucher po) {
		// TODO Auto-generated method stub
		if(po!=null){
			this.voucherDao.updateVoucher(po);
		}
	}

	public void deleteVoucher(Voucher v) {
		// TODO Auto-generated method stub
		Map param=new HashMap();
		if(v!=null){
			param.put("voucherId", v.getId());
			List<VoucherDetail> vdl= this.voucherDetailDao.getVoucherDetails(param);
			if(vdl!=null&&vdl.size()>0){
				this.voucherDetailDao.deleteVoucherDetailList(vdl);
			}
		}
		if(v!=null){
			this.voucherDao.deleteVoucher(v);
		}
		
	}
}
