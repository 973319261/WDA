package com.gx.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.po.Admin;
import com.gx.po.CanConfiguration;
import com.gx.po.HardwareManagement;
import com.gx.po.Jurisdiction;
import com.gx.po.Role;
import com.gx.po.User;
import com.gx.service.IUserService;
import com.gx.util.WebSocketMapUtil;
import com.gx.vo.HardwareVo;
import com.gx.vo.JurisDictionVo;
import com.gx.vo.LayuiJSON;
import com.gx.vo.ModuleJurisdictionDetailVo;
import com.gx.vo.Page;
import com.gx.vo.UserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/user")
@Api(value = "用户接口", description = "用户相关api")
public class UserController {

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;

	@Autowired
	private IUserService userService;

	// 登录页面http://localhost:8080/WDAService/user/loginsPage.do
	@RequestMapping("/loginsPage")
	@ApiOperation(value = "登录页面", notes = "后台登录页面",httpMethod = "GET", response = ModelAndView.class)
	public ModelAndView loginsPage() {
		ModelAndView mv = new ModelAndView("/login");
		return mv;
	}

	/**
	 * 页面跳转
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("/toUrl")
	@ApiOperation(value = "页面跳转", notes = "用于后台页面跳转",httpMethod = "GET", response = String.class)
	public String toUrl(@ApiParam(value="跳转的页面",required=true) String page) {
		return page;
	}

	/**
	 * 登录
	 * 
	 * @param user
	 *            用户信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "用户登录", notes = "用于后台用户登录",httpMethod = "GET", response = Gson.class)
	public Object login(Admin admin, HttpServletRequest request) {
		result = userService.login(admin.getAdminAccount(), admin.getAdminPassword());
		if (result != null) {
			request.getSession().setAttribute("admin", result);
		}
		return gson.toJson(result);
	}

	/**
	 * 查询用户详情信息
	 * 
	 * @param name
	 *            用户名/区域
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            排序类型
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findUserDetail", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询用户详情信息", notes = "查询用户详情信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findUserDetail(String name, String strName, String sortType, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<UserVo> list = userService.selectUserDetail(name, strName, sortType, page.getStartIndex(),
				page.getLimit());

		for (UserVo user : list) {
			if (WebSocketMapUtil.get(user.getUserId()) != null) {
				user.setIsOnline(true);// 在线
			} else {
				user.setIsOnline(false);// 不在线
			}
		}
		int totalRows = userService.selectUserDetailRows(name);
		LayuiJSON<UserVo> userInfo = new LayuiJSON<UserVo>(totalRows, list);
		return JSONSerializer.toJSON(userInfo);
	}

	/**
	 * 职务信息(绑定下拉框)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectRoleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询职务信息", notes = "用于绑定下拉框",httpMethod = "POST", response = Gson.class)
	public Object selectRoleInfo() {
		result = userService.selectRoleInfo();
		return gson.toJson(result);
	}

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUserInfo")
	@ApiOperation(value = "新增用户", notes = "用于新增用户信息",httpMethod = "POST", response = Gson.class)
	public Object saveUserInfo(User user) {
		result = userService.saveUserInfo(user);
		return gson.toJson(result);
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteUserInfo")
	@ApiOperation(value = "删除用户", notes = "用于删除用户信息",httpMethod = "POST", response = Gson.class)
	public Object deleteUserInfo(int userId) {
		result = userService.deleteUserInfo(userId);
		return gson.toJson(result);
	}

	/**
	 * 查询角色类型
	 * 
	 * @param roleName
	 *            角色类型
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findRoleInfo")
	@ApiOperation(value = "查询角色类型", notes = "用于查询角色类型信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findRoleInfo(String roleName, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Role> role = userService.findRoleInfo(roleName, page.getStartIndex(), page.getLimit());
		int totalRows = userService.findRoleInfoRows(roleName);
		LayuiJSON<Role> roleInfo = new LayuiJSON<Role>(totalRows, role);
		return JSONSerializer.toJSON(roleInfo);
	}

	/**
	 * 查询模块信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAreaInfo")
	@ApiOperation(value = "查询模块信息", notes = "用于查询模块信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findAreaInfo() {
		List<Jurisdiction> modal = userService.selectJurisdictionInfo();
		int totalRows = modal.size();
		LayuiJSON<Jurisdiction> modalInfo = new LayuiJSON<Jurisdiction>(totalRows, modal);
		return JSONSerializer.toJSON(modalInfo);
	}

	/**
	 * 新增角色类型
	 * 
	 * @param roleName
	 * @param describe
	 * @param arrJurisdiction
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertRoleType", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增角色类型", notes = "用于新增角色类型",httpMethod = "POST", response = Gson.class)
	public Object insertRoleType(String roleName, String describe, String arrJurisdiction) {
		result = userService.insertRoleType(roleName, describe, arrJurisdiction);
		return gson.toJson(result);
	}

	/**
	 * 通过用户类型ID查询权限
	 * 
	 * @param roleId
	 *            用户类型ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findJurisDictionById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过用户类型ID查询权限", notes = "通过用户类型ID查询权限",httpMethod = "POST", response = JSONSerializer.class)
	public Object findJurisDictionById(int roleId) {
		List<JurisDictionVo> juris = userService.selectJurisDictionById(roleId);
		int totalRows = juris.size();
		LayuiJSON<JurisDictionVo> jurisInfo = new LayuiJSON<JurisDictionVo>(totalRows, juris);
		return JSONSerializer.toJSON(jurisInfo);
	}

	/**
	 * 修改权限类型
	 * 
	 * @param role
	 *            用户类型
	 * @param arrJurisdiction
	 *            权限信息集合
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateJurisDiction", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改权限类型", notes = "用于修改权限类型",httpMethod = "PATCH", response = Gson.class)
	public Object updateJurisDiction(Role role, String arrJurisdiction) {
		result = userService.updateJurisDiction(role, arrJurisdiction);
		return gson.toJson(result);
	}

	/**
	 * 修改权限类型状态
	 * 
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRoleTypeState", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改权限类型状态", notes = "用于修改权限类型状态",httpMethod = "PATCH", response = Gson.class)
	public Object updateRoleTypeState(Role role) {
		result = userService.updateRoleTypeState(role);
		return gson.toJson(result);
	}

	/**
	 * 删除权限信息
	 * 
	 * @param roleId
	 *            职务ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteJurisInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除权限信息",httpMethod = "DELETE", response = Gson.class)
	public Object deleteJurisInfo(int roleId) {
		result = userService.deleteJurisInfo(roleId);
		return gson.toJson(result);
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOut")
	@ApiOperation(value = "退出登录",httpMethod = "POST", response = ModelAndView.class)
	public ModelAndView loginOut(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/login");
		request.getSession().removeAttribute("admin");// 移除session
		return mv;
	}

	/**
	 * 修改管理员密码
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserPassword", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改管理员密码",httpMethod = "PATCH", response = Gson.class)
	public Object updateUserPassword(Admin admin, String oldPassword) {
		result = userService.updateUserPassword(admin, oldPassword);
		return gson.toJson(result);
	}

	/**
	 * 通过角色id查询权限模块明细
	 * 
	 * @param roleId
	 *            用户类型ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findModuleDetailById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过角色id查询权限模块明细",httpMethod = "POST", response = JSONSerializer.class)
	public Object findModuleDetailById(int roleId) {
		List<ModuleJurisdictionDetailVo> juris = userService.selectModuleDetailById(roleId);
		int totalRows = juris.size();
		LayuiJSON<ModuleJurisdictionDetailVo> jurisInfo = new LayuiJSON<ModuleJurisdictionDetailVo>(totalRows, juris);
		return JSONSerializer.toJSON(jurisInfo);
	}

	/**
	 * 修改权限类型
	 * 
	 * @param role
	 *            用户类型
	 * @param arrJurisdiction
	 *            权限信息集合
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateModuleDetail", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改权限类型",httpMethod = "PATCH", response = Gson.class)
	public Object updateModuleDetail(String arrModuleDetail) {
		result = userService.updateModuleDetail(arrModuleDetail);
		return gson.toJson(result);
	}

	/**
	 * 清除AndroidID
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cleanAndroidId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "清除AndroidID",notes="用于清除用户的AndroidID",httpMethod = "POST", response = Gson.class)
	public Object cleanAndroidId(Integer userId) {
		result = userService.cleanAndroidId(userId);
		return gson.toJson(result);
	}

	/**
	 * 查询映射CAN配置管理信息
	 * 
	 * @param canPassage
	 *            CAN通道
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCanInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询映射CAN配置管理信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findCanInfo(Integer mapTypeId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<CanConfiguration> can = userService.selectCanInfo(mapTypeId, page.getStartIndex(), page.getLimit());
		int totalRows = userService.selectCanInfoRows(mapTypeId);
		LayuiJSON<CanConfiguration> canInfo = new LayuiJSON<CanConfiguration>(totalRows, can);
		return JSONSerializer.toJSON(canInfo);
	}

	/**
	 * 新增映射CAN配置管理信息
	 * 
	 * @param canInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertCanInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增映射CAN配置管理信息",httpMethod = "POST", response = Gson.class)
	public Object insertCanInfo(CanConfiguration canInfo) {
		result = userService.insertCanInfo(canInfo);
		return gson.toJson(result);
	}

	/**
	 * 修改映射CAN配置管理信息
	 * 
	 * @param canInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCanInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改映射CAN配置管理信息",httpMethod = "PATCH", response = Gson.class)
	public Object updateCanInfo(CanConfiguration canInfo) {
		result = userService.updateCanInfo(canInfo);
		return gson.toJson(result);
	}

	/**
	 * 查询硬件管理信息
	 * 
	 * @param hardware
	 *            硬件ID、名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findHardwareManageInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询硬件管理信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findHardwareManageInfo(String hardware, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<HardwareVo> hardWare = userService.selectHardwareManageInfo(hardware, page.getStartIndex(),
				page.getLimit());
		int totalRows = userService.selectHardwareManageInfoRows(hardware);
		LayuiJSON<HardwareVo> hardWareInfo = new LayuiJSON<HardwareVo>(totalRows, hardWare);
		return JSONSerializer.toJSON(hardWareInfo);
	}

	/**
	 * 新增硬件管理信息
	 * 
	 * @param hardware
	 *            硬件管理实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertHardwareManageInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增硬件管理信息",httpMethod = "POST", response = Gson.class)
	public Object insertHardwareManageInfo(HardwareManagement hardware) {
		result = userService.insertHardwareManageInfo(hardware);
		return gson.toJson(result);
	}

	/**
	 * 修改硬件管理信息
	 * 
	 * @param hardware
	 *            硬件管理实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateHardwareManageInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改硬件管理信息",httpMethod = "PATCH", response = Gson.class)
	public Object updateHardwareManageInfo(HardwareManagement hardware) {
		result = userService.updateHardwareManageInfo(hardware);
		return gson.toJson(result);
	}

	/**
	 * 删除硬件管理信息
	 * 
	 * @param hardwareManagementId
	 *            硬件管理id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteHardwareManageInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除硬件管理信息",httpMethod = "POST", response = Gson.class)
	public Object deleteHardwareManageInfo(int hardwareManagementId) {
		result = userService.deleteHardwareManageInfo(hardwareManagementId);
		return gson.toJson(result);
	}

	/**
	 * 更改是否启用状态
	 * 
	 * @param hardware
	 *            硬件管理实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsDisabledState", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "更改是否启用状态",notes="用于硬件管理",httpMethod = "PATCH", response = Gson.class)
	public Object updateIsDisabledState(HardwareManagement hardware) {
		result = userService.updateIsDisabledState(hardware);
		return gson.toJson(result);
	}

	/**
	 * 修改映射CAN配置管理的DID名称
	 * 
	 * @param mapTypeId
	 *            模块名称
	 * @param didName
	 *            DID名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDidIInfoOnCan", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改映射CAN配置管理的DID名称",httpMethod = "PATCH", response = Gson.class)
	public Object updateDidIInfoOnCan(int mapTypeId, String didName) {
		result = userService.updateDidIInfoOnCan(mapTypeId, didName);
		return gson.toJson(result);
	}

	/**
	 * 删除映射CAN配置管理信息
	 * 
	 * @param canConfigurationId
	 *            CAN配置ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCanInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除映射CAN配置管理信息",httpMethod = "POST", response = Gson.class)
	public Object deleteCanInfo(int canConfigurationId) {
		result = userService.deleteCanInfo(canConfigurationId);
		return gson.toJson(result);
	}

	/**
	 * 查询管理员信息
	 * 
	 * @param adminAccount
	 *            账号
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAccountInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询管理员信息",httpMethod = "POST", response = Gson.class)
	public Object findAccountInfo(String adminAccount, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Admin> admin = userService.selectAccountInfo(adminAccount, page.getStartIndex(), page.getLimit());
		int totalRows = userService.selectAccountInfoRows(adminAccount);
		LayuiJSON<Admin> adminInfo = new LayuiJSON<Admin>(totalRows, admin);
		return JSONSerializer.toJSON(adminInfo);
	}

	/**
	 * 新增管理员信息
	 * 
	 * @param admin
	 *            账户实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAdminInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增管理员信息",httpMethod = "POST", response = Gson.class)
	public Object insertAdminInfo(Admin admin) {
		result = userService.insertAdminInfo(admin);
		return gson.toJson(result);
	}

	/**
	 * 修改管理员信息
	 * 
	 * @param admin
	 *            账户实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAdminInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改管理员信息",httpMethod = "PATCH", response = Gson.class)
	public Object updateAdminInfo(Admin admin) {
		result = userService.updateAdminInfo(admin);
		return gson.toJson(result);
	}

	/**
	 * 删除管理员信息
	 * 
	 * @param adminId
	 *            账户实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAdminInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除管理员信息",httpMethod = "DELETE", response = Gson.class)
	public Object deleteAdminInfo(Integer adminId) {
		result = userService.deleteAdminInfo(adminId);
		return gson.toJson(result);
	}

	/**
	 * 修改硬件的使用信息
	 * @param hardwareMmanagementId 硬件ID
	 * @param userId 用户ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateHardwareUseInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改硬件的使用信息",notes="当APP对硬件进行操作时使用该接口", httpMethod = "PATCH", response = Gson.class)
	public Object updateHardwareUseInfo(Integer hardwareMmanagementId,Integer userId) {
		result = userService.updateHardwareUseInfo(hardwareMmanagementId, userId);
		return gson.toJson(result);
	}
}
