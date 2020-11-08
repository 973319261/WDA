package com.gx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.service.IAppUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * App用户模块
 * @author LJ
 *
 */
@Controller
@RequestMapping("/app/user")
@Api(value = "APP用户模块接口", description = "APP用户模块相关api")
public class AppUserController {
	@Autowired
	IAppUserService appUserService;
	// 返回参数
	private Object result;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	/**
	 * app登录
	 * @param account 账号
	 * @param password 密码
	 * @param imet 移动设备识别码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "app登录", httpMethod = "POST", response = Gson.class)
	public Object login(String account,String password,String imei){
		result = appUserService.login(account,password,imei);
		return gson.toJson(result);
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePassword", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改密码", httpMethod = "POST", response = Gson.class)
	public Object updatePassword(Integer userId,String oldPassword,String newPassword){
		result = appUserService.updatePassword(userId,oldPassword,newPassword);
		return gson.toJson(result);
	}
}
