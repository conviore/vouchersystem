package com.tiger.employees.voucherdetail.vo;
import java.math.BigDecimal;


import com.tiger.employees.voucherdetail.po.VoucherDetail;

public class VoucherDetailVo {
	private String id;
	private String orgId;
	private String voucherId;
	private String subjectId;
	private BigDecimal debit;
	private BigDecimal credit;
	private String settleTime;
	private String updateTime;
	private String comment;
	private String subjectCode;
	private String subjectName;
	private String settleWay;

	public VoucherDetailVo() {
	}

	public VoucherDetailVo(String id) {
		this.id = id;
	}

	public VoucherDetailVo(String id, String orgId, String voucherId,
			String subjectId, BigDecimal debit, BigDecimal credit,
			String settleTime, String updateTime, String comment,String settleWay) {
		this.id = id;
		this.orgId = orgId;
		this.voucherId = voucherId;
		this.subjectId = subjectId;
		this.debit = debit;
		this.credit = credit;
		this.settleTime = settleTime;
		this.updateTime = updateTime;
		this.comment = comment;
		this.settleWay= settleWay;
		
	}

	public String getId() {
		return this.id;
	}
	
	public VoucherDetailVo(VoucherDetail po) {
		this.id = po.getId();
		this.orgId = po.getOrgId();
		this.voucherId = po.getVoucherId();
		this.subjectId = po.getSubjectId();
		this.debit = po.getDebit();
		this.credit = po.getCredit();
		this.settleTime = po.getSettleTime();
		this.updateTime = po.getUpdateTime();
		this.comment = po.getComment();
		this.settleWay=po.getSettleWay();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getVoucherId() {
		return this.voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public BigDecimal getDebit() {
		return this.debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return this.credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public String getSettleTime() {
		return this.settleTime;
	}

	public void setSettleTime(String settleTime) {
		this.settleTime = settleTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * @param subjectCode the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the settleWay
	 */
	public String getSettleWay() {
		return settleWay;
	}

	/**
	 * @param settleWay the settleWay to set
	 */
	public void setSettleWay(String settleWay) {
		this.settleWay = settleWay;
	}
}
