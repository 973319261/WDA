package com.gx.wda.bean;

public class MapParamVo {
    private int mapAisleId;//映射方式ID
    private int mapWayId;//映射通道ID
    private String mapAisleName;
    private String mapWayName;
    private String carTypeName;//车型
    private String supplierName;//供应商

    public int getMapWayId() {
        return mapWayId;
    }

    public void setMapWayId(int mapWayId) {
        this.mapWayId = mapWayId;
    }

    public int getMapAisleId() {
        return mapAisleId;
    }

    public void setMapAisleId(int mapAisleId) {
        this.mapAisleId = mapAisleId;
    }

    public String getMapAisleName() {
        return mapAisleName;
    }

    public void setMapAisleName(String mapAisleName) {
        this.mapAisleName = mapAisleName;
    }

    public String getMapWayName() {
        return mapWayName;
    }

    public void setMapWayName(String mapWayName) {
        this.mapWayName = mapWayName;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }



}
