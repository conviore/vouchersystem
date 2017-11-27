package com.tiger.employees.vouchersummary.vo;

import java.math.BigDecimal;

public class VSReportVo {

	private String subjectCode;
	private String subjectName;
	private BigDecimal currentDebit;
	private BigDecimal currentCredit;
	private String settleDate;
	private String companyId;
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
	 * @return the currentDebit
	 */
	public BigDecimal getCurrentDebit() {
		return currentDebit;
	}
	/**
	 * @param currentDebit the currentDebit to set
	 */
	public void setCurrentDebit(BigDecimal currentDebit) {
		this.currentDebit = currentDebit;
	}
	
	public VSReportVo(){
		this.setCurrentCredit(new BigDecimal('0'));
		this.currentDebit =new BigDecimal('0');
		
		
	}
	/**
	 * @return the settleDate
	 */
	public String getSettleDate() {
		return settleDate;
	}
	/**
	 * @param settleDate the settleDate to set
	 */
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	 * @return the currentCredit
	 */
	public BigDecimal getCurrentCredit() {
		return currentCredit;
	}
	/**
	 * @param currentCredit the currentCredit to set
	 */
	public void setCurrentCredit(BigDecimal currentCredit) {
		this.currentCredit = currentCredit;
	}
	
}
