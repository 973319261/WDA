package com.gx.po;

import java.io.Serializable;

/**
 * vehicle
 * @author 
 */
public class Vehicle implements Serializable {
    /**
     * 车型ID
     */
    private Integer vehicleId;

    /**
     * 车型名称
     */
    private String vehicleName;

    /**
     * 车辆图片
     */
    private String vehiclePicture;

    private static final long serialVersionUID = 1L;

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehiclePicture() {
        return vehiclePicture;
    }

    public void setVehiclePicture(String vehiclePicture) {
        this.vehiclePicture = vehiclePicture;
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
        Vehicle other = (Vehicle) that;
        return (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
            && (this.getVehicleName() == null ? other.getVehicleName() == null : this.getVehicleName().equals(other.getVehicleName()))
            && (this.getVehiclePicture() == null ? other.getVehiclePicture() == null : this.getVehiclePicture().equals(other.getVehiclePicture()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVehicleId() == null) ? 0 : getVehicleId().hashCode());
        result = prime * result + ((getVehicleName() == null) ? 0 : getVehicleName().hashCode());
        result = prime * result + ((getVehiclePicture() == null) ? 0 : getVehiclePicture().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", vehicleName=").append(vehicleName);
        sb.append(", vehiclePicture=").append(vehiclePicture);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}