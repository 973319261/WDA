package com.gx.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gx.mapper.ConfigurationLevelDAO;
import com.gx.mapper.ModuleDAO;
import com.gx.mapper.ModuleJurisdictionDetailDAO;
import com.gx.mapper.ReArithmeticSupplierDAO;
import com.gx.mapper.ReArithmeticVehicleDAO;
import com.gx.mapper.ReDidRelevanceDAO;
import com.gx.mapper.ReSupplierFaultcodeDAO;
import com.gx.mapper.ReVehicleSupplierDAO;
import com.gx.mapper.SupplierDAO;
import com.gx.mapper.VehicleDAO;
import com.gx.po.ConfigurationLevel;
import com.gx.po.Module;
import com.gx.po.ModuleJurisdictionDetail;
import com.gx.po.ReVehicleSupplier;
import com.gx.po.Supplier;
import com.gx.po.Vehicle;
import com.gx.service.ISupplierService;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.SupplierVo;

import cn.hutool.core.io.FileUtil;

@Transactional
@Service("iSupplierService")
public class SupplierServiceImpl implements ISupplierService {

	@Autowired
	VehicleDAO vehicleDAO;
	@Autowired
	ModuleDAO moduleDAO;
	@Autowired
	SupplierDAO supplierDAO;
	@Autowired
	ReVehicleSupplierDAO reVehicleSupplierDAO;
	@Autowired
	ReArithmeticVehicleDAO reArithmeticVehicleDAO;
	@Autowired
	ReSupplierFaultcodeDAO reSupplierFaultcodeDAO;
	@Autowired
	ReArithmeticSupplierDAO reArithmeticSupplierDAO;
	@Autowired
	ReDidRelevanceDAO reDidRelevanceDAO;
	@Autowired
	ConfigurationLevelDAO configurationLevelDAO;
	@Autowired
	ModuleJurisdictionDetailDAO moduleJurisdictionDetailDAO;

	@Override
	public List<SupplierVo> selectSupplierInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, Integer startIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		return reVehicleSupplierDAO.selectSupplierInfo(vehicleId, configurationLevelId, moduleId, supplierId,
				startIndex, pageSize);
	}

	@Override
	public int selectSupplierInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId) {
		// TODO Auto-generated method stub
		return reVehicleSupplierDAO.selectSupplierInfoRows(vehicleId, configurationLevelId, moduleId, supplierId);
	}

	@Override
	public List<AppendOptionVo> selectModuleByConfigurationLevelId(Integer configurationLevelId, Integer vehicleId) {
		// TODO Auto-generated method stub
		return reVehicleSupplierDAO.selectModuleByConfigurationLevelId(configurationLevelId, vehicleId);
	}

	@Override
	public List<AppendOptionVo> selectSupplierInfoByModuleId(Integer moduleId, Integer configurationLevelId,
			Integer vehicleId) {
		// TODO Auto-generated method stub
		return reVehicleSupplierDAO.selectSupplierInfoByModuleId(moduleId, configurationLevelId, vehicleId);
	}

	@Override
	public JsonReturn insertSupplierInfo(ReVehicleSupplier reVehicleSupplier) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("提交失败");
		int num = reVehicleSupplierDAO.selectSupplierGroup(reVehicleSupplier.getVehicleId(),
				reVehicleSupplier.getConfigurationLevelId(), reVehicleSupplier.getModuleId(),
				reVehicleSupplier.getSupplierId(), 0);
		if (num > 0) {
			jsonReturn.setText("该组合已存在，请重新选择");
		} else {
			int intR = reVehicleSupplierDAO.insertSelective(reVehicleSupplier);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateSupplierInfo(ReVehicleSupplier reVehicleSupplier) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		int num = reVehicleSupplierDAO.selectSupplierGroup(reVehicleSupplier.getVehicleId(),
				reVehicleSupplier.getConfigurationLevelId(), reVehicleSupplier.getModuleId(),
				reVehicleSupplier.getSupplierId(), reVehicleSupplier.getRelevanceId());
		if (num > 0) {
			jsonReturn.setText("该组合已存在，请重新选择");
		} else {
			int intR = reVehicleSupplierDAO.updateByPrimaryKeySelective(reVehicleSupplier);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteSupplierInfo(int relevanceId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int numOne = reArithmeticSupplierDAO.selectConnectRelevanceRows(relevanceId);
		int numTwo = reDidRelevanceDAO.selectConnectRelevanceRows(relevanceId);
		// int numThree =
		// reSupplierFaultcodeDAO.selectConnectRelevanceRows(relevanceId);
		if (numOne == 0 && numTwo == 0) {
			int intR = reVehicleSupplierDAO.deleteByPrimaryKey(relevanceId);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<Vehicle> selectVehicleInfo(String vehicleName, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return vehicleDAO.selectVehicleInfo(vehicleName, startIndex, pageSize);
	}

	@Override
	public int selectVehicleInfoRows(String vehicleName) {
		// TODO Auto-generated method stub
		return vehicleDAO.selectVehicleInfoRows(vehicleName);
	}

	@Override
	public JsonReturn insertVehicleInfo(Vehicle vehicle) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("提交失败");
		Vehicle vehicles = vehicleDAO.selectVehicleWhetherExist(vehicle.getVehicleName(), 0);
		if (vehicles != null) {
			jsonReturn.setText("该车型名称已存在");
		} else {
			int intR = vehicleDAO.insertSelective(vehicle);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<Module> selectModuleMessage(String moduleName, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return moduleDAO.selectModuleMessage(moduleName, startIndex, pageSize);
	}

	@Override
	public int selectModuleMessageRows(String moduleName) {
		// TODO Auto-generated method stub
		return moduleDAO.selectModuleMessageRows(moduleName);
	}

	@Override
	public List<Supplier> selectSupplierInfo(String supplierName, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return supplierDAO.selectSupplierInfo(supplierName, startIndex, pageSize);
	}

	@Override
	public int selectSupplierInfoRows(String supplierName) {
		// TODO Auto-generated method stub
		return supplierDAO.selectSupplierInfoRows(supplierName);
	}

	@Override
	public JsonReturn updateVehicleInfo(Vehicle vehicle, String savePath) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if (vehicle != null) {
			Vehicle vehicles = vehicleDAO.selectByPrimaryKey(vehicle.getVehicleId());
			int intR = vehicleDAO.updateByPrimaryKeySelective(vehicle);
			if (intR == 1) {
				if (vehicle.getVehiclePicture() != null) {
					FileUtil.del(savePath + File.separator + vehicles.getVehiclePicture());
				}
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteVehicleInfo(int vehicleId, String savePath) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int numOne = reVehicleSupplierDAO.selectConnectVehicleRows(vehicleId);
		int numTwo = reArithmeticVehicleDAO.selectConnectVehicleRows(vehicleId);
		if (numOne == 0 && numTwo == 0) {
			Vehicle vehicle = vehicleDAO.selectByPrimaryKey(vehicleId);
			int intR = vehicleDAO.deleteByPrimaryKey(vehicleId);
			if (intR == 1) {
				jsonReturn.setText("success");
				if (vehicle.getVehiclePicture() != null) {
					FileUtil.del(savePath + vehicle.getVehiclePicture());
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn insertModuleInfo(Module module) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("提交失败");
		Module modules = moduleDAO.selectModuleWhetherExist(module.getModuleName(), 0);
		if (modules != null) {
			jsonReturn.setText("该模块名称已存在");
		} else {
			int intR = moduleDAO.insertSelective(module);// 新增模块
			if (intR == 1) {
				// 查询所有角色id
				List<ModuleJurisdictionDetail> detail = moduleJurisdictionDetailDAO.selectAllRole();
				// 新增模块权限明细信息
				int numTwo = moduleJurisdictionDetailDAO.addModuleDetailByBatch(module.getModuleId(), detail);
				if (numTwo > 0) {
					jsonReturn.setText("success");
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateModuleInfo(Module module) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if (module != null) {
			Module modules = moduleDAO.selectModuleWhetherExist(module.getModuleName(), module.getModuleId());
			if (modules != null) {
				jsonReturn.setText("该模块名称已存在");
			} else {
				int intR = moduleDAO.updateByPrimaryKeySelective(module);
				if (intR == 1) {
					jsonReturn.setText("success");
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteModuleInfo(int moduleId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int numOne = reVehicleSupplierDAO.selectConnectModuleRows(moduleId);
		if (numOne == 0) {
			int intR = moduleDAO.deleteByPrimaryKey(moduleId);// 删除模块
			if (intR == 1) {
				// 通过模块id删除模块权限明细信息，删除模块id时删除
				int numTwo = moduleJurisdictionDetailDAO.deleteModuleDetailByModuleId(moduleId);
				if (numTwo > 0) {
					jsonReturn.setText("success");
				}
			}
		} else {
			jsonReturn.setText("删除失败，该模块已有其他关联的数据");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn insertSupplierMessage(Supplier supplier) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("提交失败");
		Supplier suppliers = supplierDAO.selectSupplierWhetherExist(supplier.getSupplierName(), 0);
		int num=0;
		if(supplier.getSupplierCode()!=null && !supplier.getSupplierCode().equals("")) {
			num = supplierDAO.selectSupplierCodeWhetherExist(supplier.getSupplierCode(), 0);
		}
		if (suppliers != null) {
			jsonReturn.setText("该供应商名称已存在");
		} else if (num > 0) {
			jsonReturn.setText("该供应商代码已存在");
		} else {
			int intR = supplierDAO.insertSelective(supplier);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateSupplierMessage(Supplier supplier) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if (supplier != null) {
			Supplier suppliers = supplierDAO.selectSupplierWhetherExist(supplier.getSupplierName(),
					supplier.getSupplierId());
			int num = 0;
			if(supplier.getSupplierCode()!=null && !supplier.getSupplierCode().equals("")) {
				num=supplierDAO.selectSupplierCodeWhetherExist(supplier.getSupplierCode(), supplier.getSupplierId());
			}
			if (suppliers != null) {
				jsonReturn.setText("该供应商名称已存在");
			} else if (num > 0) {
				jsonReturn.setText("该供应商代码已存在");
			} else {
				int intR = supplierDAO.updateByPrimaryKeySelective(supplier);
				if (intR == 1) {
					jsonReturn.setText("success");
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteSupplierMessage(int supplierId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int numOne = reVehicleSupplierDAO.selectConnectSupplierRows(supplierId);
		// int numTwo = reSupplierFaultcodeDAO.selectConnectSupplierRows(supplierId);
		if (numOne == 0) {
			int intR = supplierDAO.deleteByPrimaryKey(supplierId);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		} else {
			jsonReturn.setText("删除失败，该供应商已有其他关联的数据");
		}
		return jsonReturn;
	}

	@Override
	public Vehicle selectCarTypeImageById(Integer vehicleId) {
		// TODO Auto-generated method stub
		return vehicleDAO.selectByPrimaryKey(vehicleId);
	}

	@Override
	public JsonReturn saveCarTypeImage(Vehicle vehicle, String savePath) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("保存失败");
		if (vehicle != null) {
			Vehicle vehicles = vehicleDAO.selectByPrimaryKey(vehicle.getVehicleId());
			int intR = vehicleDAO.updateByPrimaryKeySelective(vehicle);
			if (intR > 0) {
				if (vehicle.getVehiclePicture() != null
						&& !vehicle.getVehiclePicture().equals(vehicles.getVehiclePicture())) {
					String savePaths = savePath + "vehicleImage";
					File uploadDir = new File(savePaths);
					if (!uploadDir.exists()) {
						FileUtil.mkdir(uploadDir);
					}
					File temp = new File(savePath + "temp\\" + vehicle.getVehiclePicture());
					File save = new File(savePath + "vehicleImage" + File.separator + vehicle.getVehiclePicture());
					if (temp.exists()) {
						FileUtil.move(temp, save, true);
					}
					FileUtil.del(savePath + "vehicleImage\\" + vehicles.getVehiclePicture());
				}
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<AppendOptionVo> selectConfigurationLevelInfoById(Integer vehicleId) {
		// TODO Auto-generated method stub
		return reVehicleSupplierDAO.selectConfigurationLevelInfoById(vehicleId);
	}

	@Override
	public List<ConfigurationLevel> selectConfigurationInfo(String configurationLevelName, int startIndex,
			int pageSize) {
		// TODO Auto-generated method stub
		return configurationLevelDAO.selectConfigurationInfo(configurationLevelName, startIndex, pageSize);
	}

	@Override
	public int selectConfigurationInfoRows(String configurationLevelName) {
		// TODO Auto-generated method stub
		return configurationLevelDAO.selectConfigurationInfoRows(configurationLevelName);
	}

	@Override
	public JsonReturn insertConfigurationInfo(ConfigurationLevel configurationLevel) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("提交失败");
		ConfigurationLevel cl = configurationLevelDAO
				.selectConfigurationWhetherExist(configurationLevel.getConfigurationLevelName(), 0);
		if (cl != null) {
			jsonReturn.setText("该配置名称已存在");
		} else {
			int intR = configurationLevelDAO.insertSelective(configurationLevel);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateConfigurationInfo(ConfigurationLevel configurationLevel) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if (configurationLevel != null) {
			ConfigurationLevel cl = configurationLevelDAO.selectConfigurationWhetherExist(
					configurationLevel.getConfigurationLevelName(), configurationLevel.getConfigurationLevelId());
			if (cl != null) {
				jsonReturn.setText("该配置名称已存在");
			} else {
				int intR = configurationLevelDAO.updateByPrimaryKeySelective(configurationLevel);
				if (intR == 1) {
					jsonReturn.setText("success");
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteConfigurationInfo(int configurationLevelId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int numOne = reVehicleSupplierDAO.selectConnectConfigurationRows(configurationLevelId);
		if (numOne == 0) {
			int intR = configurationLevelDAO.deleteByPrimaryKey(configurationLevelId);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		} else {
			jsonReturn.setText("删除失败，该配置已有其他关联的数据");
		}
		return jsonReturn;
	}

}
