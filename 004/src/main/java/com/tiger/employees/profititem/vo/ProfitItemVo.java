package com.tiger.employees.profititem.vo;

import com.tiger.employees.profititem.po.ProfitItem;
import java.math.BigDecimal;

/**
 * Created by zx on 7/1/17.
 */
public class ProfitItemVo {
    /**
     *  本月金额
     */
    private BigDecimal amountIn;
    /**
     *  本年累计
     */
    private BigDecimal amountTotal;

    private String id;
    private String profitTemplate;
    private String itemName;
    private String relatedCode;
    private String ifFomula;
    private String formula;
    private String comment;
    private String itemType;
    private String itemNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfitTemplate() {
        return profitTemplate;
    }

    public void setProfitTemplate(String profitTemplate) {
        this.profitTemplate = profitTemplate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRelatedCode() {
        return relatedCode;
    }

    public void setRelatedCode(String relatedCode) {
        this.relatedCode = relatedCode;
    }

    public String getIfFomula() {
        return ifFomula;
    }

    public void setIfFomula(String ifFomula) {
        this.ifFomula = ifFomula;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }



    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (profitTemplate != null ? profitTemplate.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (relatedCode != null ? relatedCode.hashCode() : 0);
        result = 31 * result + (ifFomula != null ? ifFomula.hashCode() : 0);
        result = 31 * result + (formula != null ? formula.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        return result;
    }

    public BigDecimal getAmountIn() {
        return amountIn;
    }

    public void setAmountIn(BigDecimal amountIn) {
        this.amountIn = amountIn;
    }


    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public ProfitItemVo(ProfitItem po) {
        this.id= po.getId();
        this.itemType = po.getItemType();
        this.itemName = po.getItemName();
        this.relatedCode = po.getRelatedCode();
        this.ifFomula = po.getIfFomula();
        this.formula = po.getFormula();
        this.comment = po.getComment();
        this.itemType = po.getItemType();
        this.itemNo = po.getItemNo();
        this.amountIn=new BigDecimal("0");
        this.amountTotal=new BigDecimal("0");
    }
}
