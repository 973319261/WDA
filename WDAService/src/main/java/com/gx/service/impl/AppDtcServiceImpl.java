package com.gx.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gx.mapper.ConfigurationLevelDAO;
import com.gx.mapper.FaultCodeDAO;
import com.gx.mapper.ModuleDAO;
import com.gx.mapper.UserDAO;
import com.gx.po.ConfigurationLevel;
import com.gx.po.Module;
import com.gx.po.Vehicle;
import com.gx.service.IAppDtcService;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.FaultCodeResultVo;
import com.gx.vo.FaultCodeVo;
import com.gx.vo.JsonReturn;

import cn.hutool.core.collection.CollUtil;
@Service
@Transactional
public class AppDtcServiceImpl implements IAppDtcService{
	@Autowired
	private FaultCodeDAO faultCodeDAO;//故障码
	@Autowired
	private ConfigurationLevelDAO configurationLevelDAO;//配置
	@Autowired
	private ModuleDAO moduleDAO;//模块
	@Autowired
	private UserDAO userDAO;//用户ID
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Override
	public JsonReturn selectFaultCode(String dtc, Integer vehicleId, Integer configureId, Integer moduleId,
			Integer userId,Boolean isVisit) {
		List<AppendOptionVo> vehicles=new ArrayList<>();//车型
		List<AppendOptionVo> configurationLevels=new ArrayList<>();//配置等级
		List<AppendOptionVo> modules=new ArrayList<>();//模块
		List<FaultCodeVo> faultCodeVos=new ArrayList<>();//故障码
		List<String> dtcList = new ArrayList<>();//实例化dtc列表
		AppendOptionVo appendOptionVo = null;
		FaultCodeResultVo resultVo =null;
		JsonReturn jsonReturn=new JsonReturn();
		if(dtc.contains(";")) {
			String[] dtcs=dtc.split(";");//分割字符串
			dtcList=CollUtil.toList(dtcs);//把数组转为list
		}else {
			dtcList.add(dtc);//单条数据时直接添加数据
		}
		for (String dtcStr : dtcList) {
			 resultVo = faultCodeDAO.selectFaultCode(dtcStr, vehicleId, configureId, moduleId);//通过dtc查询故障码信息
			 disposeData(appendOptionVo, resultVo, vehicles, configurationLevels, modules, faultCodeVos, vehicleId);
			 resultVo = faultCodeDAO.selectFaultCodeByHexDtc(dtcStr, vehicleId, configureId, moduleId);//通过hexdtc查询故障码信息
			 disposeData(appendOptionVo, resultVo, vehicles, configurationLevels, modules, faultCodeVos, vehicleId);
			 
		}
		if (isVisit && faultCodeVos!=null && faultCodeVos.size()>0) {//数据不为空
			userDAO.updateFaultCodeVisit(userId);//修改故障码访问量
		}
		JsonObject jsonObject=new JsonObject();
		jsonObject.add("vehicles", gson.toJsonTree(vehicles));//车型
		jsonObject.add("configurationLevels", gson.toJsonTree(configurationLevels));//配置
		jsonObject.add("modules", gson.toJsonTree(modules));//模块
		jsonObject.add("faultCodes", gson.toJsonTree(faultCodeVos));//故障码
		jsonReturn.setCode(200);
		jsonReturn.setData(jsonObject);
		return jsonReturn;
	}

	@Override
	public JsonReturn selectConfigureByDtc(String dtc, Integer vehicleId) {
		JsonReturn jsonReturn=new JsonReturn();
		List<String> dtcList = new ArrayList<>();//实例化dtc列表
		List<AppendOptionVo> configurationLevels=new ArrayList<>();//配置
		List<AppendOptionVo> appendOptionVos;
		if(dtc.contains(";")) {
			String[] dtcs=dtc.split(";");//分割字符串
			dtcList=CollUtil.toList(dtcs);//把数组转为list
		}else {
			dtcList.add(dtc);//单条数据时直接添加数据
		}
		for (String dtcStr : dtcList) {
			 appendOptionVos = configurationLevelDAO.selectConfigureByDtc(dtc, vehicleId);
			 for (AppendOptionVo appendOptionVo : appendOptionVos) {
				if (!configurationLevels.contains(appendOptionVo)) {
					configurationLevels.add(appendOptionVo);
				}
			};
			 appendOptionVos = configurationLevelDAO.selectConfigureByHexDtc(dtc, vehicleId);
			 for (AppendOptionVo appendOptionVo : appendOptionVos) {
				if (!configurationLevels.contains(appendOptionVo)) {
					configurationLevels.add(appendOptionVo);
				}
			};
			 
		}
		jsonReturn.setCode(200);
		jsonReturn.setData(configurationLevels);
		return jsonReturn;
	}

	@Override
	public JsonReturn selectMoudleByDtc(String dtc, Integer vehicleId, Integer configureId) {
		JsonReturn jsonReturn=new JsonReturn();
		List<String> dtcList = new ArrayList<>();//实例化dtc列表
		List<AppendOptionVo> modules=new ArrayList<>();//车型
		List<AppendOptionVo> appendOptionVos;
		if(dtc.contains(";")) {
			String[] dtcs=dtc.split(";");//分割字符串
			dtcList=CollUtil.toList(dtcs);//把数组转为list
		}else {
			dtcList.add(dtc);//单条数据时直接添加数据
		}
		for (String dtcStr : dtcList) {
			 appendOptionVos = moduleDAO.selectMoudleByDtc(dtc, vehicleId,configureId);
			 for (AppendOptionVo appendOptionVo : appendOptionVos) {
				if (!modules.contains(appendOptionVo)) {
					modules.add(appendOptionVo);
				}
			};
			 appendOptionVos = moduleDAO.selectMoudleByHexDtc(dtc, vehicleId,configureId);
			 for (AppendOptionVo appendOptionVo : appendOptionVos) {
				if (!modules.contains(appendOptionVo)) {
					modules.add(appendOptionVo);
				}
			};
			 
		}
		jsonReturn.setCode(200);
		jsonReturn.setData(modules);
		return jsonReturn;
	}
	/**
	 * 处理数据
	 * @param appendOptionVo
	 * @param resultVo
	 * @param vehicles
	 * @param configurationLevels
	 * @param modules
	 * @param faultCodeVos
	 * @param vehicleId
	 */
	private void disposeData(AppendOptionVo appendOptionVo,FaultCodeResultVo resultVo,
			List<AppendOptionVo> vehicles,List<AppendOptionVo> configurationLevels,List<AppendOptionVo> modules,
			List<FaultCodeVo> faultCodeVos,Integer vehicleId) {
		if (resultVo!=null) {
			 if(vehicleId==0) {
				 for (Vehicle vehicle : resultVo.getVehicle()) {
					 appendOptionVo=new AppendOptionVo();
					 appendOptionVo.setId(vehicle.getVehicleId());
					 appendOptionVo.setName(vehicle.getVehicleName());
					if (!vehicles.contains(appendOptionVo)) {
						vehicles.add(appendOptionVo);
					}
				};
			 }
			//配置去重
			 for (ConfigurationLevel configurationLevel : resultVo.getConfigurationLevel()) {
				 appendOptionVo=new AppendOptionVo();
				 appendOptionVo.setId(configurationLevel.getConfigurationLevelId());
				 appendOptionVo.setName(configurationLevel.getConfigurationLevelName());
				 if (!configurationLevels.contains(appendOptionVo)) {
					 configurationLevels.add(appendOptionVo);
					}
			};
			
			//模块去重
			 for (Module module : resultVo.getModule()) {
				 appendOptionVo=new AppendOptionVo();
				 appendOptionVo.setId(module.getModuleId());
				 appendOptionVo.setName(module.getModuleName());
				 if (!modules.contains(appendOptionVo)) {
					 modules.add(appendOptionVo);
					}
			};
			//故障码去重
			for (FaultCodeVo faultCodeVo : resultVo.getFaultCodeVo()) {
				if (!faultCodeVos.contains(faultCodeVo)) {
					faultCodeVos.add(faultCodeVo);
				}
			}
		}	
	}
}
