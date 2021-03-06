package com.tiger.employees.company.po;

// Generated Sep 28, 2014 5:16:38 PM by Hibernate Tools 3.4.0.CR1

/**
 * Company generated by hbm2java
 */
public class Company implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5748895752329307271L;
	private String id;
	private String companyCode;
	private String companyName;
	private String createTime;
	private String updateTime;
	private String comment;
	private String settleBeginDate;
	private String status;

	public Company() {
	}

	public Company(String id) {
		this.id = id;
	}

	public Company(String id, String companyCode, String companyName,
			String createTime, String updateTime, String comment) {
		this.id = id;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.comment = comment;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	 * @return the settleBeginDate
	 */
	public String getSettleBeginDate() {
		return settleBeginDate;
	}

	/**
	 * @param settleBeginDate the settleBeginDate to set
	 */
	public void setSettleBeginDate(String settleBeginDate) {
		this.settleBeginDate = settleBeginDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
