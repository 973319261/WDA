package com.gx.po;

import java.io.Serializable;

/**
 * re_arithmetic_vehicle
 * @author 
 */
public class ReArithmeticVehicle implements Serializable {
    /**
     * 关联ID
     */
    private Integer relevanceId;

    /**
     * 车型ID
     */
    private Integer vehicleId;

    /**
     * 算法ID
     */
    private Integer arithmeticId;

    private static final long serialVersionUID = 1L;

    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getArithmeticId() {
        return arithmeticId;
    }

    public void setArithmeticId(Integer arithmeticId) {
        this.arithmeticId = arithmeticId;
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
        ReArithmeticVehicle other = (ReArithmeticVehicle) that;
        return (this.getRelevanceId() == null ? other.getRelevanceId() == null : this.getRelevanceId().equals(other.getRelevanceId()))
            && (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
            && (this.getArithmeticId() == null ? other.getArithmeticId() == null : this.getArithmeticId().equals(other.getArithmeticId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRelevanceId() == null) ? 0 : getRelevanceId().hashCode());
        result = prime * result + ((getVehicleId() == null) ? 0 : getVehicleId().hashCode());
        result = prime * result + ((getArithmeticId() == null) ? 0 : getArithmeticId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relevanceId=").append(relevanceId);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", arithmeticId=").append(arithmeticId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}