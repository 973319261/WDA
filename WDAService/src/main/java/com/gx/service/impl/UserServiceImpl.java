package com.gx.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gx.mapper.AdminDAO;
import com.gx.mapper.CanConfigurationDAO;
import com.gx.mapper.HardwareManagementDAO;
import com.gx.mapper.InformDAO;
import com.gx.mapper.InformDetailDAO;
import com.gx.mapper.JurisdictionDAO;
import com.gx.mapper.JurisdictionDetailDAO;
import com.gx.mapper.ModuleJurisdictionDetailDAO;
import com.gx.mapper.RoleDAO;
import com.gx.mapper.UserDAO;
import com.gx.po.Admin;
import com.gx.po.CanConfiguration;
import com.gx.po.HardwareManagement;
import com.gx.po.InformDetail;
import com.gx.po.Jurisdiction;
import com.gx.po.JurisdictionDetail;
import com.gx.po.ModuleJurisdictionDetail;
import com.gx.po.Role;
import com.gx.po.User;
import com.gx.service.IUserService;
import com.gx.util.AESEncryptHelper;
import com.gx.util.Tools;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.HardwareVo;
import com.gx.vo.InformVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.JurisDictionVo;
import com.gx.vo.ModuleJurisdictionDetailVo;
import com.gx.vo.UserVo;

@Transactional
@Service("iUserService")
public class UserServiceImpl implements IUserService {

	// 注入DAO层
	@Autowired
	UserDAO userDAO;
	@Autowired
	RoleDAO roleDAO;
	@Autowired
	JurisdictionDAO jurisdictionDAO;
	@Autowired
	JurisdictionDetailDAO jurisdictionDetailDAO;
	@Autowired
	ModuleJurisdictionDetailDAO moduleJurisdictionDetailDAO;
	@Autowired
	InformDAO informDAO;
	@Autowired
	InformDetailDAO informDetailDAO;
	@Autowired
	CanConfigurationDAO canConfigurationDAO;
	@Autowired
	HardwareManagementDAO hardwareManagementDAO;
	@Autowired
	AdminDAO adminDAO;

	@Override
	public JsonReturn login(String adminAccount, String password) {
		JsonReturn jsonReturn = new JsonReturn();
		if (Tools.isNotNull(adminAccount) && Tools.isNotNull(password)) {
			Admin admin = adminDAO.selectAdminByAccount(adminAccount);
			if (admin != null) {
				if (AESEncryptHelper.encrypt(password).equals(admin.getAdminPassword())) {
					jsonReturn.setCode(200);
					jsonReturn.setText("登录成功");
					jsonReturn.setData(admin);
				} else {
					jsonReturn.setCode(502);
					jsonReturn.setText("密码输入错误");
				}
			} else {
				jsonReturn.setCode(501);
				jsonReturn.setText("该账户不存在");
			}
		} else {
			jsonReturn.setCode(500);
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}

	@Override
	public List<UserVo> selectUserDetail(String name, String strName, String sortType, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return userDAO.selectUserDetail(name, strName, sortType, startIndex, pageSize);
	}

	@Override
	public int selectUserDetailRows(String name) {
		// TODO Auto-generated method stub
		return userDAO.selectUserDetailRows(name);
	}

	@Override
	public int selectAllUserInfo() {
		List<User> user = userDAO.selectAllUserInfo();
		int num = 0;
		for (int i = 0; i < user.size(); i++) {
			User users = new User();
			users.setUserId(user.get(i).getUserId());
			users.setIsExpires(true);
			int intR = userDAO.updateByPrimaryKeySelective(users);
			if (intR == 1) {
				num++;
			}
		}
		if (num == user.size()) {
			num = 1;
		}
		return num;
	}

	@Override
	public List<AppendOptionVo> selectRoleInfo() {
		return roleDAO.selectRoleInfo();
	}

	@Override
	public JsonReturn saveUserInfo(User user) {
		Object obj = user.getUserId();
		if (obj == null) {
			user.setUserId(0);
		}
		JsonReturn jsonReturn = new JsonReturn();
		if (user != null) {
			// 查询员工编号是否重复
			int num = userDAO.selectUserNumberReuse(user.getUserNumber(), user.getUserId());
			// 查询用户账号是否重复
			int count = userDAO.selectAccountResuse(user.getUserAccount(), user.getUserId());
			if (count > 0) {
				jsonReturn.setCode(502);
				jsonReturn.setText("用户名已存在");
			} else {
				if (num == 0) {
					int intR = 0;
					if (user.getUserId() != null && user.getUserId() > 0) {
						// 修改用户信息
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String strOne = dateFormat.format(new Date());
						String strTwo = dateFormat.format(user.getExpirationDate());
						try {
							Date now = dateFormat.parse(strOne);
							Date time = dateFormat.parse(strTwo);
							if (time.after(now) || time.equals(now)) {
								user.setIsExpires(false);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (user.getUserPassword() != null && user.getUserPassword() != "") {
							String password = user.getUserPassword();
							user.setUserPassword(AESEncryptHelper.encrypt(password));
						} else {
							user.setUserPassword(null);
						}

						intR = userDAO.updateByPrimaryKeySelective(user);
					} else {
						// 新增用户信息
						String password = user.getUserPassword();
						user.setUserPassword(AESEncryptHelper.encrypt(password));
						user.setFaultcodeVisit(0);
						user.setArithmeticVisit(0);
						user.setShareVisit(0);
						user.setIsExpires(false);
						intR = userDAO.insertSelective(user);
						if (intR == 1) {
							List<InformVo> inform = informDAO.selectAllInform();
							List<InformDetail> informList = new ArrayList<InformDetail>();
							InformDetail informDetail = null;
							for (InformVo informs : inform) {
								informDetail = new InformDetail();
								informDetail.setIsRead(false);
								informDetail.setInformId(informs.getInformId());
								informDetail.setUserId(user.getUserId());
								informList.add(informDetail);
							}
							intR = informDetailDAO.insertRelevanceInformByBatch(informList);
						}
					}
					if (intR > 0) {
						jsonReturn.setCode(200);
						jsonReturn.setText("保存成功");
					} else {
						jsonReturn.setCode(500);
						jsonReturn.setText("保存失败");
					}

				} else {
					jsonReturn.setCode(501);
					jsonReturn.setText("员工编号已存在");
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteUserInfo(int userId) {
		JsonReturn jsonReturn = new JsonReturn();
		if (userId > 0) {
			int intR = userDAO.deleteByPrimaryKey(userId);
			if (intR == 1) {
				jsonReturn.setText("success");
			} else {
				jsonReturn.setText("删除失败");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<Role> findRoleInfo(String roleName, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return roleDAO.findRoleInfo(roleName, startIndex, pageSize);
	}

	@Override
	public int findRoleInfoRows(String roleName) {
		// TODO Auto-generated method stub
		return roleDAO.findRoleInfoRows(roleName);
	}

	@Override
	public List<Jurisdiction> selectJurisdictionInfo() {
		// TODO Auto-generated method stub
		return jurisdictionDAO.selectJurisdictionInfo();
	}

	@Override
	public JsonReturn insertRoleType(String roleName, String describe, String arrJurisdiction) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("新增失败");
		Role role = new Role();
		role.setRoleName(roleName);
		role.setIsEnable(true);
		role.setRoleDescribe(describe);
		// 查询权限类型名称是否存在
		int count = roleDAO.findRoleNameInfoRows(roleName, 0);
		if (count == 0) {
			int intR = roleDAO.insertSelective(role);
			List<JurisdictionDetail> jurisdiction = Tools.jsonToList(arrJurisdiction, JurisdictionDetail.class);
			if (intR > 0) {
				// 新增权限明细
				int num = jurisdictionDetailDAO.insertJurisDictionByBatch(role.getRoleId(), jurisdiction);
				if (num > 0) {
					// 查询所有模块id
					List<ModuleJurisdictionDetail> detail = moduleJurisdictionDetailDAO.selectAllModule();
					// 新增模块权限明细信息
					int numTwo = moduleJurisdictionDetailDAO.insertModuleDetailByBatch(role.getRoleId(), detail);
					if (numTwo > 0) {
						jsonReturn.setText("success");
					}
				}
			}
		} else {
			jsonReturn.setText("该权限类型名称已存在");
		}
		return jsonReturn;
	}

	@Override
	public List<JurisDictionVo> selectJurisDictionById(int roleId) {
		// TODO Auto-generated method stub
		return jurisdictionDetailDAO.selectJurisDictionById(roleId);
	}

	@Override
	public JsonReturn updateJurisDiction(Role role, String arrJurisdiction) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		int count = roleDAO.findRoleNameInfoRows(role.getRoleName(), role.getRoleId());
		if (count == 0) {
			int intR = roleDAO.updateByPrimaryKeySelective(role);
			List<JurisdictionDetail> jurisdiction = Tools.jsonToList(arrJurisdiction, JurisdictionDetail.class);
			if (intR > 0) {
				int num = jurisdictionDetailDAO.updateJurisDictionByBatch(jurisdiction);
				if (num > 0) {
					jsonReturn.setText("success");
				}
			}
		} else {
			jsonReturn.setText("该权限类型名称已存在");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateRoleTypeState(Role role) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("已有用户使用该权限,操作失败");
		int num = userDAO.selectUserRowByRoleId(role.getRoleId());
		if (num == 0) {
			int intR = roleDAO.updateByPrimaryKeySelective(role);
			if (intR > 0) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteJurisInfo(int roleId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int intR = roleDAO.deleteRoleInfoByRoleId(roleId);// 删除角色
		if (intR > 0) {
			// 通过角色id删除模块权限明细信息，删除角色id时删除
			int numTwo = moduleJurisdictionDetailDAO.deleteModuleDetailByRoleId(roleId);
			if (numTwo > 0) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateUserPassword(Admin admin, String oldPassword) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if (admin != null) {
			// 密码加密
			String password = admin.getAdminPassword();
			admin.setAdminPassword(AESEncryptHelper.encrypt(password));
			// 查询管理员旧的密码
			Admin admins = adminDAO.selectByPrimaryKey(admin.getAdminId());
			if (admins.getAdminPassword().equals(AESEncryptHelper.encrypt(oldPassword))) {
				int intR = adminDAO.updateByPrimaryKeySelective(admin);
				if (intR > 0) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("旧密码输入错误");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<ModuleJurisdictionDetailVo> selectModuleDetailById(int roleId) {
		return moduleJurisdictionDetailDAO.selectModuleDetailById(roleId);
	}

	@Override
	public JsonReturn updateModuleDetail(String arrModuleDetail) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		List<ModuleJurisdictionDetail> moduleDetail = Tools.jsonToList(arrModuleDetail, ModuleJurisdictionDetail.class);
		int num = moduleJurisdictionDetailDAO.updateModuleDetailByBatch(moduleDetail);
		if (num > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn cleanAndroidId(Integer userId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("清除失败");
		if (userId > 0) {
			User user = new User();
			user.setUserId(userId);
			user.setAndroidId("");
			int intR = userDAO.updateByPrimaryKeySelective(user);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<CanConfiguration> selectCanInfo(Integer mapTypeId, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return canConfigurationDAO.selectCanInfo(mapTypeId, startIndex, pageSize);
	}

	@Override
	public int selectCanInfoRows(Integer mapTypeId) {
		// TODO Auto-generated method stub
		return canConfigurationDAO.selectCanInfoRows(mapTypeId);
	}

	@Override
	public JsonReturn insertCanInfo(CanConfiguration canInfo) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("新增失败");
		if (canInfo != null) {
			int num = canConfigurationDAO.selectCanInfoWhetherExist(canInfo.getCanPassage(), 0, canInfo.getMapTypeId());
			if (num == 0) {
				if (canInfo.getDidName() == null || canInfo.getDidName().equals("")) {
					if (canInfo.getMapTypeId() == 1) {
						canInfo.setDidName("C110");
					} else {
						canInfo.setDidName("F005");
					}
				}
				int intR = canConfigurationDAO.insertSelective(canInfo);
				if (intR > 0) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("该CAN通道已存在，请重新填写");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateCanInfo(CanConfiguration canInfo) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if (canInfo != null) {
			int num = canConfigurationDAO.selectCanInfoWhetherExist(canInfo.getCanPassage(),
					canInfo.getCanConfigurationId(), canInfo.getMapTypeId());
			if (num == 0) {
				int intR = canConfigurationDAO.updateByPrimaryKeySelective(canInfo);
				if (intR > 0) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("该CAN通道已存在，请重新填写");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<HardwareVo> selectHardwareManageInfo(String hardware, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return hardwareManagementDAO.selectHardwareManageInfo(hardware, startIndex, pageSize);
	}

	@Override
	public int selectHardwareManageInfoRows(String hardware) {
		// TODO Auto-generated method stub
		return hardwareManagementDAO.selectHardwareManageInfoRows(hardware);
	}

	@Override
	public JsonReturn insertHardwareManageInfo(HardwareManagement hardware) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("新增失败");
		if (hardware != null) {
			int numOne = hardwareManagementDAO.selectHardwareWhetherExist(0, hardware.getHardwareEquipmentId(), 0);
			int numTwo = hardwareManagementDAO.selectHardwareWhetherExist(1, hardware.getHardwareEquipmentName(), 0);
			if (numOne == 0) {
				if (numTwo == 0) {
					int intR = hardwareManagementDAO.insertSelective(hardware);
					if (intR > 0) {
						jsonReturn.setText("success");
					}
				} else {
					jsonReturn.setText("该硬件名称已存在，请重新填写");
				}
			} else {
				jsonReturn.setText("该硬件ID已存在，请重新填写");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateHardwareManageInfo(HardwareManagement hardware) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if (hardware != null) {
			int numOne = hardwareManagementDAO.selectHardwareWhetherExist(0, hardware.getHardwareEquipmentId(),
					hardware.getHardwareManagementId());
			int numTwo = hardwareManagementDAO.selectHardwareWhetherExist(1, hardware.getHardwareEquipmentName(),
					hardware.getHardwareManagementId());
			if (numOne == 0) {
				if (numTwo == 0) {
					int intR = hardwareManagementDAO.updateByPrimaryKeySelective(hardware);
					if (intR > 0) {
						jsonReturn.setText("success");
					}
				} else {
					jsonReturn.setText("该硬件名称已存在，请重新填写");
				}
			} else {
				jsonReturn.setText("该硬件ID已存在，请重新填写");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteHardwareManageInfo(int hardwareManagementId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int intR = hardwareManagementDAO.deleteByPrimaryKey(hardwareManagementId);
		if (intR > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateIsDisabledState(HardwareManagement hardware) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("操作失败");
		int intR = hardwareManagementDAO.updateByPrimaryKeySelective(hardware);
		if (intR > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateDidIInfoOnCan(int mapTypeId, String didName) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		CanConfiguration canInfo = new CanConfiguration();
		canInfo.setMapTypeId(mapTypeId);
		canInfo.setDidName(didName);
		if (canInfo != null) {
			int intR = canConfigurationDAO.updateDidIInfoOnCan(canInfo);
			if (intR > 0) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteCanInfo(int canConfigurationId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int intR = canConfigurationDAO.deleteByPrimaryKey(canConfigurationId);
		if (intR > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public List<Admin> selectAccountInfo(String adminAccount, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return adminDAO.selectAccountInfo(adminAccount, startIndex, pageSize);
	}

	@Override
	public int selectAccountInfoRows(String adminAccount) {
		// TODO Auto-generated method stub
		return adminDAO.selectAccountInfoRows(adminAccount);
	}

	@Override
	public JsonReturn insertAdminInfo(Admin admin) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("新增失败");
		int numOne = adminDAO.selectAccountWhetherExist(admin.getAdminAccount(), 0);
		int numTwo = adminDAO.selectNameWhetherExist(admin.getAdminName(), 0);
		if (admin != null) {
			if (numOne == 0) {
				if (numTwo == 0) {
					admin.setAdminType(1);// 设置默认值
					admin.setAdminPassword(AESEncryptHelper.encrypt(admin.getAdminPassword()));// 加密
					int intR = adminDAO.insertSelective(admin);
					if (intR > 0) {
						jsonReturn.setText("success");
					}
				} else {
					jsonReturn.setText("该用户名已存在，请重新填写");
				}
			} else {
				jsonReturn.setText("该账户已存在，请重新填写");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateAdminInfo(Admin admin) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		int numOne = adminDAO.selectAccountWhetherExist(admin.getAdminAccount(), admin.getAdminId());
		int numTwo = adminDAO.selectNameWhetherExist(admin.getAdminName(), admin.getAdminId());
		if (admin != null) {
			if (numOne == 0) {
				if (numTwo == 0) {
					admin.setAdminType(1);// 设置默认值
					// 判断修改的密码是否为空
					if (admin.getAdminPassword() != null && admin.getAdminPassword() != "") {
						admin.setAdminPassword(AESEncryptHelper.encrypt(admin.getAdminPassword()));// 加密
					} else {
						admin.setAdminPassword(null);
					}

					int intR = adminDAO.updateByPrimaryKeySelective(admin);
					if (intR > 0) {
						jsonReturn.setText("success");
					}
				} else {
					jsonReturn.setText("该用户名已存在，请重新填写");
				}
			} else {
				jsonReturn.setText("该账户已存在，请重新填写");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteAdminInfo(Integer adminId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int intR = adminDAO.deleteByPrimaryKey(adminId);
		if (intR > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateHardwareUseInfo(Integer hardwareMmanagementId, Integer userId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setCode(500);
		jsonReturn.setText("修改失败");
		if(hardwareMmanagementId>0 && userId>0) {
			HardwareManagement harware=new HardwareManagement();
			harware.setHardwareManagementId(hardwareMmanagementId);
			harware.setUserId(userId);
			harware.setUseDate(new Date());
			int intR=hardwareManagementDAO.updateByPrimaryKeySelective(harware);
			if(intR>0) {
				jsonReturn.setCode(200);
				jsonReturn.setText("修改成功");
			}
		}
		return jsonReturn;
	}

}
