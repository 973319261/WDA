package com.gx.po;

import java.io.Serializable;

/**
 * node
 * @author 
 */
public class Node implements Serializable {
    /**
     * 节点ID
     */
    private Integer nodeId;

    /**
     * 关联ID
     */
    private Integer relevanceId;

    /**
     * 线束段配置ID
     */
    private Integer harnessConfigurationId;

    /**
     * 发送ID
     */
    private String txId;

    /**
     * 接收ID
     */
    private String rxId;

    private static final long serialVersionUID = 1L;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Integer getHarnessConfigurationId() {
        return harnessConfigurationId;
    }

    public void setHarnessConfigurationId(Integer harnessConfigurationId) {
        this.harnessConfigurationId = harnessConfigurationId;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getRxId() {
        return rxId;
    }

    public void setRxId(String rxId) {
        this.rxId = rxId;
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
        Node other = (Node) that;
        return (this.getNodeId() == null ? other.getNodeId() == null : this.getNodeId().equals(other.getNodeId()))
            && (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()))
            && (this.getHarnessConfigurationId() == null ? other.getHarnessConfigurationId() == null : this.getHarnessConfigurationId().equals(other.getHarnessConfigurationId()))
            && (this.getTxId() == null ? other.getTxId() == null : this.getTxId().equals(other.getTxId()))
            && (this.getRxId() == null ? other.getRxId() == null : this.getRxId().equals(other.getRxId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNodeId() == null) ? 0 : getNodeId().hashCode());
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        result = prime * result + ((getHarnessConfigurationId() == null) ? 0 : getHarnessConfigurationId().hashCode());
        result = prime * result + ((getTxId() == null) ? 0 : getTxId().hashCode());
        result = prime * result + ((getRxId() == null) ? 0 : getRxId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nodeId=").append(nodeId);
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", harnessConfigurationId=").append(harnessConfigurationId);
        sb.append(", txId=").append(txId);
        sb.append(", rxId=").append(rxId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}