package com.gx.po;

import java.io.Serializable;

/**
 * eol
 * @author 
 */
public class Eol implements Serializable {
    /**
     * eou_id
     */
    private Integer eouId;

    /**
     * 输入值
     */
    private String inputValue;

    /**
     * 输出值
     */
    private String outputValue;

    private static final long serialVersionUID = 1L;

    public Integer getEouId() {
        return eouId;
    }

    public void setEouId(Integer eouId) {
        this.eouId = eouId;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(String outputValue) {
        this.outputValue = outputValue;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Eol other = (Eol) that;
        return (this.getEouId() == null ? other.getEouId() == null : this.getEouId().equals(other.getEouId()))
            && (this.getInputValue() == null ? other.getInputValue() == null : this.getInputValue().equals(other.getInputValue()))
            && (this.getOutputValue() == null ? other.getOutputValue() == null : this.getOutputValue().equals(other.getOutputValue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEouId() == null) ? 0 : getEouId().hashCode());
        result = prime * result + ((getInputValue() == null) ? 0 : getInputValue().hashCode());
        result = prime * result + ((getOutputValue() == null) ? 0 : getOutputValue().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eouId=").append(eouId);
        sb.append(", inputValue=").append(inputValue);
        sb.append(", outputValue=").append(outputValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}