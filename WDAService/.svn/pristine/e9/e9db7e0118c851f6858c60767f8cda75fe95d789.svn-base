package com.gx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.po.Module;
import com.gx.po.Supplier;
/**
 * 公共控制器
 * @author LJ
 *
 */
import com.gx.service.IAppCommonService;
import com.gx.vo.JsonReturn;
@Controller
@RequestMapping("/app/common")
public class AppCommonController {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;
	@Autowired
	private IAppCommonService commonService;
	/**
	 * 查询所有车型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCarTypeAll", produces = "application/json;charset=UTF-8")
	public Object selectCarTypeAll() {
		result = commonService.selectCarTypeAll();
		return gson.toJson(result);
	}
	/**
	 * 通过车型ID查询模块
	 * @param carTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectModuleByCarTypeId", produces = "application/json;charset=UTF-8")
	public Object selectModuleByCarTypeId(Integer carTypeId) {
		result = commonService.selectModuleByCarTypeId(carTypeId);
		return gson.toJson(result);
	}

	
	/**
	 * 查询GW的供应商
	 * @param moudleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSupplierByModuleId", produces = "application/json;charset=UTF-8")
	public Object selectSupplierByModuleId(Integer moduleId) {
		result = commonService.selectSupplierByModuleId(commonService.selectModuleByModuleName("GW"));
		return gson.toJson(result);
	}
	/**
	 * 查询DID内容下拉框（通过模块名称和供应商名称）
	 * @param moudleName
	 * @param supplierName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectDidContent", produces = "application/json;charset=UTF-8")
	public Object selectDidContent(String moudleName, String supplierName){
		result = commonService.selectDidContent(moudleName, supplierName);
		return gson.toJson(result);
	}
	
	
	
	
	
	
	/**
	 * 查询模块信息 by 模块名称、全称、canID
	 * @param mod
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectModuleInfo", produces = "application/json;charset=UTF-8")
	public Object selectModuleInfo(Module mod){
		result = commonService.selectModuleInfo(mod);
		return gson.toJson(result);
	}
	
	
	/**
	 * 查询供应商信息by 名称、编号
	 * @param sup
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSupInfo", produces = "application/json;charset=UTF-8")
	public Object selectSupInfo(Supplier sup){
		result = commonService.selectSupInfo(sup);
		return gson.toJson(result);
	}
	
	
	/**
	 * 查询EOL生产线
	 * @param sup
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectEOL", produces = "application/json;charset=UTF-8")
	public Object selectEOL(String input){
		result = commonService.selectEOL(input);
		return gson.toJson(result);
	}
	
	
	
	/**
	 * 查询can值 by 映射方式
	 * @param mapTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCanByType", produces = "application/json;charset=UTF-8")
	public Object selectCanByType(Integer mapTypeId){
		result = commonService.selectCanByType(mapTypeId);
		return gson.toJson(result);
	}
	
	
}
