package com.gx.po;

import java.io.Serializable;

/**
 * flow_detail
 * @author 
 */
public class FlowDetail implements Serializable {
    /**
     * 流程明细ID
     */
    private Integer flowDetailId;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 发送指令
     */
    private String sendOrder;

    /**
     * 响应指令
     */
    private String responseOrder;

    private static final long serialVersionUID = 1L;

    public Integer getFlowDetailId() {
        return flowDetailId;
    }

    public void setFlowDetailId(Integer flowDetailId) {
        this.flowDetailId = flowDetailId;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getSendOrder() {
        return sendOrder;
    }

    public void setSendOrder(String sendOrder) {
        this.sendOrder = sendOrder;
    }

    public String getResponseOrder() {
        return responseOrder;
    }

    public void setResponseOrder(String responseOrder) {
        this.responseOrder = responseOrder;
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
        FlowDetail other = (FlowDetail) that;
        return (this.getFlowDetailId() == null ? other.getFlowDetailId() == null : this.getFlowDetailId().equals(other.getFlowDetailId()))
            && (this.getFlowId() == null ? other.getFlowId() == null : this.getFlowId().equals(other.getFlowId()))
            && (this.getOperationName() == null ? other.getOperationName() == null : this.getOperationName().equals(other.getOperationName()))
            && (this.getSendOrder() == null ? other.getSendOrder() == null : this.getSendOrder().equals(other.getSendOrder()))
            && (this.getResponseOrder() == null ? other.getResponseOrder() == null : this.getResponseOrder().equals(other.getResponseOrder()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFlowDetailId() == null) ? 0 : getFlowDetailId().hashCode());
        result = prime * result + ((getFlowId() == null) ? 0 : getFlowId().hashCode());
        result = prime * result + ((getOperationName() == null) ? 0 : getOperationName().hashCode());
        result = prime * result + ((getSendOrder() == null) ? 0 : getSendOrder().hashCode());
        result = prime * result + ((getResponseOrder() == null) ? 0 : getResponseOrder().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flowDetailId=").append(flowDetailId);
        sb.append(", flowId=").append(flowId);
        sb.append(", operationName=").append(operationName);
        sb.append(", sendOrder=").append(sendOrder);
        sb.append(", responseOrder=").append(responseOrder);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}