package com.gx.po;

import java.io.Serializable;

/**
 * arithmetic_level
 * @author 
 */
public class ArithmeticLevel implements Serializable {
    /**
     * 算法等级ID
     */
    private Integer arithmeticLevelId;

    /**
     * 算法等级名称
     */
    private String arithmeticLevelName;

    private static final long serialVersionUID = 1L;

    public Integer getArithmeticLevelId() {
        return arithmeticLevelId;
    }

    public void setArithmeticLevelId(Integer arithmeticLevelId) {
        this.arithmeticLevelId = arithmeticLevelId;
    }

    public String getArithmeticLevelName() {
        return arithmeticLevelName;
    }

    public void setArithmeticLevelName(String arithmeticLevelName) {
        this.arithmeticLevelName = arithmeticLevelName;
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
        ArithmeticLevel other = (ArithmeticLevel) that;
        return (this.getArithmeticLevelId() == null ? other.getArithmeticLevelId() == null : this.getArithmeticLevelId().equals(other.getArithmeticLevelId()))
            && (this.getArithmeticLevelName() == null ? other.getArithmeticLevelName() == null : this.getArithmeticLevelName().equals(other.getArithmeticLevelName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getArithmeticLevelId() == null) ? 0 : getArithmeticLevelId().hashCode());
        result = prime * result + ((getArithmeticLevelName() == null) ? 0 : getArithmeticLevelName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", arithmeticLevelId=").append(arithmeticLevelId);
        sb.append(", arithmeticLevelName=").append(arithmeticLevelName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}