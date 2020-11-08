package com.gx.po;

import java.io.Serializable;

/**
 * response_order
 * @author 
 */
public class ResponseOrder implements Serializable {
    /**
     * 响应指令ID
     */
    private Integer responseOrderId;

    /**
     * 响应指令
     */
    private String responseInstructions;

    /**
     * 详情
     */
    private String particulars;

    private static final long serialVersionUID = 1L;

    public Integer getResponseOrderId() {
        return responseOrderId;
    }

    public void setResponseOrderId(Integer responseOrderId) {
        this.responseOrderId = responseOrderId;
    }

    public String getResponseInstructions() {
        return responseInstructions;
    }

    public void setResponseInstructions(String responseInstructions) {
        this.responseInstructions = responseInstructions;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
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
        ResponseOrder other = (ResponseOrder) that;
        return (this.getResponseOrderId() == null ? other.getResponseOrderId() == null : this.getResponseOrderId().equals(other.getResponseOrderId()))
            && (this.getResponseInstructions() == null ? other.getResponseInstructions() == null : this.getResponseInstructions().equals(other.getResponseInstructions()))
            && (this.getParticulars() == null ? other.getParticulars() == null : this.getParticulars().equals(other.getParticulars()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getResponseOrderId() == null) ? 0 : getResponseOrderId().hashCode());
        result = prime * result + ((getResponseInstructions() == null) ? 0 : getResponseInstructions().hashCode());
        result = prime * result + ((getParticulars() == null) ? 0 : getParticulars().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", responseOrderId=").append(responseOrderId);
        sb.append(", responseInstructions=").append(responseInstructions);
        sb.append(", particulars=").append(particulars);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}