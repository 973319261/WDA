package com.gx.service;

import java.util.List;

import com.gx.po.Admin;
import com.gx.po.CanConfiguration;
import com.gx.po.HardwareManagement;
import com.gx.po.Jurisdiction;
import com.gx.po.Role;
import com.gx.po.User;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.HardwareVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.JurisDictionVo;
import com.gx.vo.ModuleJurisdictionDetailVo;
import com.gx.vo.UserVo;

public interface IUserService {

	/**
	 * 用户登录
	 * 
	 * @param adminAccount
	 *            管理员账号
	 * @param password
	 *            密码
	 * @return
	 */
	public JsonReturn login(String adminAccount, String password);

	/**
	 * 查询用户详情信息
	 * 
	 * @param name
	 *            用户名/区域
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            排序类型
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<UserVo> selectUserDetail(String name, String strName, String sortType, int startIndex, int pageSize);

	/**
	 * 查询用户详情信息(数据条数)
	 * 
	 * @param name
	 *            用户名/区域
	 * @return
	 */
	public int selectUserDetailRows(String name);

	/**
	 * 查询软件使用已到期的用户信息
	 * 
	 * @return
	 */
	public int selectAllUserInfo();

	/**
	 * 查询职位信息
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectRoleInfo();

	/**
	 * 新增/修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	public JsonReturn saveUserInfo(User user);

	/**
	 * 删除用户信息
	 * 
	 * @param userID
	 * @return
	 */
	public JsonReturn deleteUserInfo(int userID);

	/**
	 * 查询职位信息
	 * 
	 * @param roleName
	 *            权限类型
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Role> findRoleInfo(String roleName, int startIndex, int pageSize);

	/**
	 * 查询职位信息(数据总条数)
	 * 
	 * @param roleName
	 *            权限类型
	 * @return
	 */
	public int findRoleInfoRows(String roleName);

	/**
	 * 查询模块信息
	 * 
	 * @return
	 */
	public List<Jurisdiction> selectJurisdictionInfo();

	/**
	 * 新增用户类型
	 * 
	 * @param roleName
	 *            用户类型
	 * @param describe
	 *            描述
	 * @param arrJurisdiction
	 *            权限数组
	 * @return
	 */
	public JsonReturn insertRoleType(String roleName, String describe, String arrJurisdiction);

	/**
	 * 通过用户类型查找权限
	 * 
	 * @param roleId
	 *            用户类型ID
	 * @return
	 */
	public List<JurisDictionVo> selectJurisDictionById(int roleId);

	/**
	 * 修改用户类型权限
	 * 
	 * @param role
	 *            用户类型
	 * @param arrJurisdiction
	 *            权限明细列表
	 * @return
	 */
	public JsonReturn updateJurisDiction(Role role, String arrJurisdiction);

	/**
	 * 修改权限类型状态
	 * 
	 * @param role
	 * @return
	 */
	public JsonReturn updateRoleTypeState(Role role);

	/**
	 * 删除权限信息
	 * 
	 * @param roleId
	 * @return
	 */
	public JsonReturn deleteJurisInfo(int roleId);

	/**
	 * 修改管理员密码
	 * 
	 * @param user
	 * @return
	 */
	public JsonReturn updateUserPassword(Admin admin, String oldPassword);

	/**
	 * 通过角色id查询权限模块明细
	 * 
	 * @param roleId
	 *            用户类型ID
	 * @return
	 */
	public List<ModuleJurisdictionDetailVo> selectModuleDetailById(int roleId);

	/**
	 * 修改模块权限明细
	 * 
	 * @param arrModuleDetail
	 *            模块权限明细列表
	 * @return
	 */
	public JsonReturn updateModuleDetail(String arrModuleDetail);

	/**
	 * 清除AndroidID
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public JsonReturn cleanAndroidId(Integer userId);

	/**
	 * 查询映射CAN配置管理信息
	 * 
	 * @param canPassage
	 *            CAN通道
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<CanConfiguration> selectCanInfo(Integer mapTypeId, int startIndex, int pageSize);

	/**
	 * 查询映射CAN配置管理信息(数据总条数)
	 * 
	 * @param canPassage
	 *            CAN通道
	 * @return
	 */
	public int selectCanInfoRows(Integer mapTypeId);

	/**
	 * 新增映射CAN配置管理信息
	 * 
	 * @param canInfo
	 * @return
	 */
	public JsonReturn insertCanInfo(CanConfiguration canInfo);

	/**
	 * 修改映射CAN配置管理信息
	 * 
	 * @param canInfo
	 * @return
	 */
	public JsonReturn updateCanInfo(CanConfiguration canInfo);

	/**
	 * 查询硬件管理信息
	 * 
	 * @param hardware
	 *            硬件ID、名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<HardwareVo> selectHardwareManageInfo(String hardware, int startIndex, int pageSize);

	/**
	 * 查询硬件管理信息(数据总条数)
	 * 
	 * @param hardware
	 *            硬件ID、名称
	 * @return
	 */
	public int selectHardwareManageInfoRows(String hardware);

	/**
	 * 新增硬件管理信息
	 * 
	 * @param hardware
	 *            硬件管理实体类
	 * @return
	 */
	public JsonReturn insertHardwareManageInfo(HardwareManagement hardware);

	/**
	 * 修改硬件管理信息
	 * 
	 * @param hardware
	 *            硬件管理实体类
	 * @return
	 */
	public JsonReturn updateHardwareManageInfo(HardwareManagement hardware);

	/**
	 * 删除硬件管理信息
	 * 
	 * @param hardwareManagementId
	 *            硬件管理id
	 * @return
	 */
	public JsonReturn deleteHardwareManageInfo(int hardwareManagementId);

	/**
	 * 更改是否启用状态
	 * 
	 * @param hardware
	 *            硬件管理实体类
	 * @return
	 */
	public JsonReturn updateIsDisabledState(HardwareManagement hardware);

	/**
	 * 修改映射CAN配置管理的DID名称
	 * 
	 * @param mapTypeId
	 *            映射类型ID
	 * @param didName
	 *            DID名称
	 * @return
	 */
	public JsonReturn updateDidIInfoOnCan(int mapTypeId, String didName);

	/**
	 * 删除映射CAN配置管理信息
	 * 
	 * @param canConfigurationId
	 *            CAN配置ID
	 * @return
	 */
	public JsonReturn deleteCanInfo(int canConfigurationId);

	/**
	 * 查询管理员信息
	 * 
	 * @param adminAccount
	 *            账号
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Admin> selectAccountInfo(String adminAccount, int startIndex, int pageSize);

	/**
	 * 查询管理员信息(数据总条数)
	 * 
	 * @param adminAccount
	 *            账号
	 * @return
	 */
	public int selectAccountInfoRows(String adminAccount);

	/**
	 * 新增管理员信息
	 * 
	 * @param admin
	 *            账户实体类
	 * @return
	 */
	public JsonReturn insertAdminInfo(Admin admin);

	/**
	 * 修改管理员信息
	 * 
	 * @param admin
	 *            账户实体类
	 * @return
	 */
	public JsonReturn updateAdminInfo(Admin admin);

	/**
	 * 删除管理员信息
	 * 
	 * @param adminId
	 *            管理员id
	 * @return
	 */
	public JsonReturn deleteAdminInfo(Integer adminId);
	
	/**
	 * 修改硬件的使用信息
	 * @param hardwareMmanagementId 硬件ID
	 * @param userId 用户ID
	 * @return
	 */
	public JsonReturn updateHardwareUseInfo(Integer hardwareMmanagementId,Integer userId);
}
