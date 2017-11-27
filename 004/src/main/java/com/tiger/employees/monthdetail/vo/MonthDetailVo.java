package com.tiger.employees.monthdetail.vo;

import java.math.BigDecimal;

public class MonthDetailVo {

	private BigDecimal debit;
	private BigDecimal credit;
	private BigDecimal difference;
	private String settleTime;
	private String voucherNumber;
	private String comment;
	private String subjectId;
	private String subjectCode;
	private String subjectName;
	private String settleWay;
	
	public MonthDetailVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MonthDetailVo(BigDecimal debit, BigDecimal credit,
			BigDecimal difference, String settleTime, String voucherNumber,
			String comment, String subjectId, String subjectCode,
			String subjectName,String settleWay) {
		super();
		this.debit = debit;
		this.credit = credit;
		this.difference = difference;
		this.settleTime = settleTime;
		this.voucherNumber = voucherNumber;
		this.comment = comment;
		this.subjectId = subjectId;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.settleWay=settleWay;
	}

	/**
	 * @return the debit
	 */
	public BigDecimal getDebit() {
		return debit;
	}

	/**
	 * @return the credit
	 */
	public BigDecimal getCredit() {
		return credit;
	}

	/**
	 * @return the difference
	 */
	public BigDecimal getDifference() {
		return difference;
	}

	/**
	 * @return the settleTime
	 */
	public String getSettleTime() {
		return settleTime;
	}

	/**
	 * @return the voucherNumber
	 */
	public String getVoucherNumber() {
		return voucherNumber;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param debit the debit to set
	 */
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	/**
	 * @param difference the difference to set
	 */
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	/**
	 * @param settleTime the settleTime to set
	 */
	public void setSettleTime(String settleTime) {
		this.settleTime = settleTime;
	}

	/**
	 * @param voucherNumber the voucherNumber to set
	 */
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @param subjectCode the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
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
