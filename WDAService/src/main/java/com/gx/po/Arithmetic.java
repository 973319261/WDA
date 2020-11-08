package com.gx.po;

import java.io.Serializable;

/**
 * arithmetic
 * @author 
 */
public class Arithmetic implements Serializable {
    /**
     * 算法ID
     */
    private Integer arithmeticId;

    /**
     * 算法等级ID
     */
    private Integer algorithmLevelId;

    /**
     * 算法名称
     */
    private String arithmeticName;

    /**
     * 算法接入口
     */
    private String entryPoint;

    /**
     * 算法名称前缀
     */
    private String arithmeticPrefix;

    /**
     * 算法变量
     */
    private String arithmeticDelta;

    /**
     * 访问量
     */
    private Integer visitorVolume;

    /**
     * 算法类型
     */
    private Integer algorithmType;

    private static final long serialVersionUID = 1L;

    public Integer getArithmeticId() {
        return arithmeticId;
    }

    public void setArithmeticId(Integer arithmeticId) {
        this.arithmeticId = arithmeticId;
    }

    public Integer getAlgorithmLevelId() {
        return algorithmLevelId;
    }

    public void setAlgorithmLevelId(Integer algorithmLevelId) {
        this.algorithmLevelId = algorithmLevelId;
    }

    public String getArithmeticName() {
        return arithmeticName;
    }

    public void setArithmeticName(String arithmeticName) {
        this.arithmeticName = arithmeticName;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getArithmeticPrefix() {
        return arithmeticPrefix;
    }

    public void setArithmeticPrefix(String arithmeticPrefix) {
        this.arithmeticPrefix = arithmeticPrefix;
    }

    public String getArithmeticDelta() {
        return arithmeticDelta;
    }

    public void setArithmeticDelta(String arithmeticDelta) {
        this.arithmeticDelta = arithmeticDelta;
    }

    public Integer getVisitorVolume() {
        return visitorVolume;
    }

    public void setVisitorVolume(Integer visitorVolume) {
        this.visitorVolume = visitorVolume;
    }

    public Integer getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(Integer algorithmType) {
        this.algorithmType = algorithmType;
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
        Arithmetic other = (Arithmetic) that;
        return (this.getArithmeticId() == null ? other.getArithmeticId() == null : this.getArithmeticId().equals(other.getArithmeticId()))
            && (this.getAlgorithmLevelId() == null ? other.getAlgorithmLevelId() == null : this.getAlgorithmLevelId().equals(other.getAlgorithmLevelId()))
            && (this.getArithmeticName() == null ? other.getArithmeticName() == null : this.getArithmeticName().equals(other.getArithmeticName()))
            && (this.getEntryPoint() == null ? other.getEntryPoint() == null : this.getEntryPoint().equals(other.getEntryPoint()))
            && (this.getArithmeticPrefix() == null ? other.getArithmeticPrefix() == null : this.getArithmeticPrefix().equals(other.getArithmeticPrefix()))
            && (this.getArithmeticDelta() == null ? other.getArithmeticDelta() == null : this.getArithmeticDelta().equals(other.getArithmeticDelta()))
            && (this.getVisitorVolume() == null ? other.getVisitorVolume() == null : this.getVisitorVolume().equals(other.getVisitorVolume()))
            && (this.getAlgorithmType() == null ? other.getAlgorithmType() == null : this.getAlgorithmType().equals(other.getAlgorithmType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getArithmeticId() == null) ? 0 : getArithmeticId().hashCode());
        result = prime * result + ((getAlgorithmLevelId() == null) ? 0 : getAlgorithmLevelId().hashCode());
        result = prime * result + ((getArithmeticName() == null) ? 0 : getArithmeticName().hashCode());
        result = prime * result + ((getEntryPoint() == null) ? 0 : getEntryPoint().hashCode());
        result = prime * result + ((getArithmeticPrefix() == null) ? 0 : getArithmeticPrefix().hashCode());
        result = prime * result + ((getArithmeticDelta() == null) ? 0 : getArithmeticDelta().hashCode());
        result = prime * result + ((getVisitorVolume() == null) ? 0 : getVisitorVolume().hashCode());
        result = prime * result + ((getAlgorithmType() == null) ? 0 : getAlgorithmType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", arithmeticId=").append(arithmeticId);
        sb.append(", algorithmLevelId=").append(algorithmLevelId);
        sb.append(", arithmeticName=").append(arithmeticName);
        sb.append(", entryPoint=").append(entryPoint);
        sb.append(", arithmeticPrefix=").append(arithmeticPrefix);
        sb.append(", arithmeticDelta=").append(arithmeticDelta);
        sb.append(", visitorVolume=").append(visitorVolume);
        sb.append(", algorithmType=").append(algorithmType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}