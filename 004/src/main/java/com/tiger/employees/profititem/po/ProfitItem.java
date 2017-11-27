package com.tiger.employees.profititem.po;

import java.io.Serializable;

/**
 * Created by zx on 6/24/17.
 */
public class ProfitItem implements Serializable {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfitItem that = (ProfitItem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (profitTemplate != null ? !profitTemplate.equals(that.profitTemplate) : that.profitTemplate != null)
            return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;
        if (relatedCode != null ? !relatedCode.equals(that.relatedCode) : that.relatedCode != null) return false;
        if (ifFomula != null ? !ifFomula.equals(that.ifFomula) : that.ifFomula != null) return false;
        if (formula != null ? !formula.equals(that.formula) : that.formula != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (itemType != null ? !itemType.equals(that.itemType) : that.itemType != null) return false;
        return itemNo != null ? itemNo.equals(that.itemNo) : that.itemNo == null;
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
}
