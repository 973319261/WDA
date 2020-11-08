package com.gx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.service.IVersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * apk版本
 * 
 * @author LJ
 *
 */
@Controller
@RequestMapping("/app/version")
@Api(value = "APP版本信息接口", description = "APP版本信息相关api")
public class AppVersionController {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;
	@Autowired
	private IVersionService versionService;

	/**
	 * 获取最新apk版本信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getVersionInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "获取最新apk版本信息", httpMethod = "POST", response = Gson.class)
	public Object getVersionInfo() {
		result = versionService.getVersionInfo();
		return gson.toJson(result);
	}

	/**
	 * 获取所有版本信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllVersionInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "获取所有版本信息", httpMethod = "POST", response = Gson.class)
	public Object getAllVersionInfo() {
		result = versionService.getAllVersionInfo();
		return gson.toJson(result);
	}

}
