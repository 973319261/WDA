package com.gx.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.service.IAppSecurityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * APP安全算法模块
 * 
 * @author LJ
 *
 */
@Controller
@RequestMapping("/app/security")
@Api(value = "APP安全算法模块接口", description = "APP安全算法模块相关api")
public class AppSecurityController {
	@Autowired
	IAppSecurityService appSecurityService;
	// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * 通过Dll计算（算法）
	 * 
	 * @param inputValue
	 *            输入值
	 * @param pin
	 *            seed&pin-->key中pin值(不是必须)
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级（不是必须）
	 * @param moduleId
	 *            模块ID（不是必须）
	 * @param supplierId
	 *            供应商ID（不是必须）
	 * @param arithmeticLevelId
	 *            安全算法等级ID（不是必须）
	 * @param userId
	 *            用户ID
	 * @param typeId
	 *            算法类型ID（seed--key ...）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/calculateByDll", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过Dll计算（算法）", httpMethod = "POST", response = Gson.class)
	public Object calculateByDll(String inputValue, @RequestParam(value = "pin", required = false) String pin,
			@RequestParam(value = "vehicleId", required = false) Integer vehicleId,
			@RequestParam(value = "configurationLevelId", required = false) Integer configurationLevelId,
			@RequestParam(value = "moduleId", required = false) Integer moduleId,
			@RequestParam(value = "supplierId", required = false) Integer supplierId,
			@RequestParam(value = "arithmeticLevelId", required = false) Integer arithmeticLevelId, Integer userId,
			Integer typeId, HttpServletRequest request) {
		result = appSecurityService.calculateByDll(inputValue, pin, vehicleId, configurationLevelId, moduleId,
				supplierId, arithmeticLevelId, userId, typeId, request);
		return gson.toJson(result);
	}

	/**
	 * 查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的车型
	 * 
	 * @param algorithmType
	 *            算法类型 1或者4
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectVehicleRelateArithmetic", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询算法与车型(1)", notes = "与SeedToKey（1）或者SeedPinToKey（4）关联的车型", httpMethod = "POST", response = Gson.class)
	public Object selectVehicleRelateArithmetic(@RequestParam(value = "roleId", required = false) Integer roleId,
			Integer algorithmType, @RequestParam(value = "moduleName", required = false) String moduleName) {
		result = appSecurityService.selectVehicleRelateArithmetic(roleId, algorithmType, moduleName);
		return gson.toJson(result);
	}

	/**
	 * 查询算法与VINToPin（2）或者VINToEsk（3）关联的车型
	 * 
	 * @param algorithmType
	 *            算法类型2或者3
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectVehicleRelateArithmeticVehicle", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询算法与车型(2)", notes = "与VINToPin（2）或者VINToEsk（3）关联的车型", httpMethod = "POST", response = Gson.class)
	public Object selectVehicleRelateArithmeticVehicle(Integer algorithmType) {
		result = appSecurityService.selectVehicleRelateArithmeticVehicle(algorithmType);
		return gson.toJson(result);
	}

	/**
	 * 通过车型ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的配置
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param algorithmType
	 *            算法类型（ 1或者4）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectConfigurationRelateArithmetic", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过车型ID查询算法与配置", notes = "与SeedToKey（1）或者SeedPinToKey（4）关联的配置", httpMethod = "POST", response = Gson.class)
	public Object selectConfigurationRelateArithmetic(Integer vehicleId, Integer algorithmType) {
		result = appSecurityService.selectConfigurationRelateArithmetic(vehicleId, algorithmType);
		return gson.toJson(result);
	}

	/**
	 * 通过车型ID和配置等级ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的模块
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param algorithmType
	 *            算法类型（ 1或者4）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectModuleRelateArithmetic", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过车型ID和配置等级ID查询算法与模块", notes = "与SeedToKey（1）或者SeedPinToKey（4）关联的模块", httpMethod = "POST", response = Gson.class)
	public Object selectModuleRelateArithmetic(Integer vehicleId, Integer configurationLevelId,
			@RequestParam(value = "roleId", required = false) Integer roleId, Integer algorithmType) {
		result = appSecurityService.selectModuleRelateArithmetic(vehicleId, configurationLevelId, roleId,
				algorithmType);
		return gson.toJson(result);
	}

	/**
	 * 通过车型ID、配置等级ID、模块ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的供应商
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moudleId
	 *            模块ID
	 * @param algorithmType
	 *            算法类型（ 1或者4）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSupplierRelateArithmetic", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过车型ID、配置等级ID、模块ID查询算法与供应商", notes = "与SeedToKey（1）或者SeedPinToKey（4）关联的供应商", httpMethod = "POST", response = Gson.class)
	public Object selectSupplierRelateArithmetic(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer algorithmType) {
		result = appSecurityService.selectSupplierRelateArithmetic(vehicleId, configurationLevelId, moduleId,
				algorithmType);
		return gson.toJson(result);
	}

	/**
	 * 查询算法与SeedToKey关联的算法等级
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moudleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectLevelRelateSeedToKey", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询算法与SeedToKey关联的算法等级", httpMethod = "POST", response = Gson.class)
	public Object selectLevelRelateSeedToKey(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId) {
		result = appSecurityService.selectLevelRelateSeedToKey(vehicleId, configurationLevelId, moduleId, supplierId);
		return gson.toJson(result);
	}

}
