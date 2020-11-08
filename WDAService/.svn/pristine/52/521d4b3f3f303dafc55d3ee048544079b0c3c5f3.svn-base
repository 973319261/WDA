package com.gx.po;

import java.io.Serializable;

/**
 * flow
 * @author 
 */
public class Flow implements Serializable {
    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 关联ID(模块供应商)
     */
    private Integer relevanceId;

    private static final long serialVersionUID = 1L;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
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
        Flow other = (Flow) that;
        return (this.getFlowId() == null ? other.getFlowId() == null : this.getFlowId().equals(other.getFlowId()))
            && (this.getFlowName() == null ? other.getFlowName() == null : this.getFlowName().equals(other.getFlowName()))
            && (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFlowId() == null) ? 0 : getFlowId().hashCode());
        result = prime * result + ((getFlowName() == null) ? 0 : getFlowName().hashCode());
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flowId=").append(flowId);
        sb.append(", flowName=").append(flowName);
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}