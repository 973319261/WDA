package com.gx.po;

import java.io.Serializable;

/**
 * flow_step
 * @author 
 */
public class FlowStep implements Serializable {
    /**
     * 流程步骤ID
     */
    private Integer flowStepId;

    /**
     * 操作名称
     */
    private String operationNames;

    /**
     * 发送指令
     */
    private String sendOrders;

    /**
     * 响应指令
     */
    private String responseOrders;

    private static final long serialVersionUID = 1L;

    public Integer getFlowStepId() {
        return flowStepId;
    }

    public void setFlowStepId(Integer flowStepId) {
        this.flowStepId = flowStepId;
    }

    public String getOperationNames() {
        return operationNames;
    }

    public void setOperationNames(String operationNames) {
        this.operationNames = operationNames;
    }

    public String getSendOrders() {
        return sendOrders;
    }

    public void setSendOrders(String sendOrders) {
        this.sendOrders = sendOrders;
    }

    public String getResponseOrders() {
        return responseOrders;
    }

    public void setResponseOrders(String responseOrders) {
        this.responseOrders = responseOrders;
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
        FlowStep other = (FlowStep) that;
        return (this.getFlowStepId() == null ? other.getFlowStepId() == null : this.getFlowStepId().equals(other.getFlowStepId()))
            && (this.getOperationNames() == null ? other.getOperationNames() == null : this.getOperationNames().equals(other.getOperationNames()))
            && (this.getSendOrders() == null ? other.getSendOrders() == null : this.getSendOrders().equals(other.getSendOrders()))
            && (this.getResponseOrders() == null ? other.getResponseOrders() == null : this.getResponseOrders().equals(other.getResponseOrders()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFlowStepId() == null) ? 0 : getFlowStepId().hashCode());
        result = prime * result + ((getOperationNames() == null) ? 0 : getOperationNames().hashCode());
        result = prime * result + ((getSendOrders() == null) ? 0 : getSendOrders().hashCode());
        result = prime * result + ((getResponseOrders() == null) ? 0 : getResponseOrders().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flowStepId=").append(flowStepId);
        sb.append(", operationNames=").append(operationNames);
        sb.append(", sendOrders=").append(sendOrders);
        sb.append(", responseOrders=").append(responseOrders);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}