package com.gx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.service.IAppDtcService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * APP DTC模块
 * @author LJ
 *
 */
@Controller
@RequestMapping("/app/dtc")
@Api(value = "APP DTC模块接口", description = "APP DTC模块相关api")
public class AppDtcController {
	@Autowired
	private IAppDtcService appDtcService;
	// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	/**
	 * 查询故障码
	 * @param dtc
	 * @param vehicleId
	 * @param configureId
	 * @param moduleId
	 * @param isVisit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectFaultCode", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询故障码信息",httpMethod = "POST", response = Gson.class)
	public Object selectFaultCode(String dtc, Integer vehicleId,Integer configureId,Integer moduleId,Integer userId,Boolean isVisit){
		result=appDtcService.selectFaultCode(dtc, vehicleId, configureId, moduleId, userId,isVisit);
		return gson.toJson(result);
	}
	
	/**
	 * 通过dtc和车型查询配置
	 * @param dtc
	 * @param vehicleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectConfigureByDtc", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过dtc和车型查询配置",httpMethod = "POST", response = Gson.class)
	public Object selectConfigureByDtc(String dtc,Integer vehicleId){
		result=appDtcService.selectConfigureByDtc(dtc, vehicleId);
		return gson.toJson(result);
	}
	
	/**
	 * 通过dtc、车型、配置查询模块
	 * @param dtc
	 * @param vehicleId
	 * @param configureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectMoudleByDtc", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过dtc、车型、配置查询模块",httpMethod = "POST", response = Gson.class)
	public Object selectMoudleByDtc(String dtc,Integer vehicleId,Integer configureId){
		result=appDtcService.selectMoudleByDtc(dtc, vehicleId, configureId);
		return gson.toJson(result);
	}
}
