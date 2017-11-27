package com.tiger.employees.balanceitem.vo;

import java.math.BigDecimal;

import com.tiger.employees.balanceitem.po.BalanceItem;

public class BalanceItemVo {
	private String id;
	private String balanceType;
	private String itemName;
	private String relatedCode;
	private String ifFomula;
	private String fomula;
	private String comment;
	private String itemType;
	private String itemNo;
	
	private BigDecimal initYearBalance;
	private BigDecimal endBalance;

	public BalanceItemVo() {
	}

	public BalanceItemVo(String id) {
		this.id = id;
	}

	public BalanceItemVo(String id, String balanceType, String itemName,
			String relatedCode, String ifFomula, String fomula, String comment,
			String itemType, String itemNo) {
		this.id = id;
		this.balanceType = balanceType;
		this.itemName = itemName;
		this.relatedCode = relatedCode;
		this.ifFomula = ifFomula;
		this.fomula = fomula;
		this.comment = comment;
		this.itemType = itemType;
		this.itemNo = itemNo;
	}
	
	public BalanceItemVo(BalanceItem po) {
		this.id = po.getId();
		this.balanceType = po.getBalanceType();
		this.itemName = po.getItemName();
		this.relatedCode = po.getRelatedCode();
		this.ifFomula = po.getIfFomula();
		this.fomula = po.getFomula();
		this.comment = po.getComment();
		this.itemType = po.getItemType();
		this.itemNo = po.getItemNo();
	}
	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBalanceType() {
		return this.balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getRelatedCode() {
		return this.relatedCode;
	}

	public void setRelatedCode(String relatedCode) {
		this.relatedCode = relatedCode;
	}

	public String getIfFomula() {
		return this.ifFomula;
	}

	public void setIfFomula(String ifFomula) {
		this.ifFomula = ifFomula;
	}

	public String getFomula() {
		return this.fomula;
	}

	public void setFomula(String fomula) {
		this.fomula = fomula;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * @return the initYearBalance
	 */
	public BigDecimal getInitYearBalance() {
		return initYearBalance;
	}

	/**
	 * @param initYearBalance the initYearBalance to set
	 */
	public void setInitYearBalance(BigDecimal initYearBalance) {
		this.initYearBalance = initYearBalance;
	}

	/**
	 * @return the endBalance
	 */
	public BigDecimal getEndBalance() {
		return endBalance;
	}

	/**
	 * @param endBalance the endBalance to set
	 */
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

}
