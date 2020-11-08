package com.gx.service;

import com.gx.vo.JsonReturn;

public interface IAppDtcService {
	/**
	 * 查询故障码
	 * @param dtc 
	 * @param vehicleId 车型ID
	 * @param configureId 配置ID
	 * @param moduleId 模块ID
	 * @param isVisit 是否修改访问量
	 * @return
	 */
	public JsonReturn selectFaultCode(String dtc, Integer vehicleId,Integer configureId,Integer moduleId,Integer userId,Boolean isVisit);
	/**
	 * 查询配置
	 * @param dtc
	 * @param vehicleId
	 * @return
	 */
	public JsonReturn selectConfigureByDtc(String dtc,Integer vehicleId);
	/**
	 * 查询模块
	 * @param dtc
	 * @param vehicleId
	 * @param configureId
	 * @return
	 */
	public JsonReturn selectMoudleByDtc(String dtc,Integer vehicleId,Integer configureId);
}
