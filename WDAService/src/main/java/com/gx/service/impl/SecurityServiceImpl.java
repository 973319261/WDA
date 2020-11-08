package com.gx.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;

import com.gx.mapper.ArithmeticDAO;
import com.gx.mapper.ArithmeticLevelDAO;
import com.gx.mapper.ReArithmeticSupplierDAO;
import com.gx.mapper.ReArithmeticVehicleDAO;
import com.gx.mapper.ReVehicleSupplierDAO;
import com.gx.po.Arithmetic;
import com.gx.po.ReArithmeticSupplier;
import com.gx.po.ReArithmeticVehicle;
import com.gx.po.ReVehicleSupplier;
import com.gx.service.ISecurityService;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.ArithmeticVo;
import com.gx.vo.JsonReturn;

import cn.hutool.core.io.FileUtil;

@Transactional
@Service("iSecurityService")
public class SecurityServiceImpl implements ISecurityService {

	// 注入DAO层
	@Autowired
	ArithmeticDAO arithmeticDAO;
	@Autowired
	ArithmeticLevelDAO arithmeticLevelDAO;
	@Autowired
	ReVehicleSupplierDAO reVehicleSupplierDAO;
	@Autowired
	ReArithmeticSupplierDAO reArithmeticSupplierDAO;
	@Autowired
	ReArithmeticVehicleDAO reArithmeticVehicleDAO;

	@Override
	public List<ArithmeticVo> selectSeedToKeyInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName, String strName, String sortType, Integer startIndex,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return arithmeticDAO.selectSeedToKeyInfo(vehicleId, configurationLevelId, moduleId, supplierId, arithmeticName,
				strName, sortType, startIndex, pageSize);
	}

	@Override
	public int selectSeedToKeyInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName) {
		// TODO Auto-generated method stub
		return arithmeticDAO.selectSeedToKeyInfoRows(vehicleId, configurationLevelId, moduleId, supplierId,
				arithmeticName);
	}

	@Override
	public List<AppendOptionVo> selectArithmeticInfo(Integer algorithmType) {
		// TODO Auto-generated method stub
		return arithmeticDAO.selectArithmeticInfo(algorithmType);
	}

	@Override
	public List<AppendOptionVo> selectArithmeticLevelInfo() {
		// TODO Auto-generated method stub
		return arithmeticLevelDAO.selectArithmeticLevelInfo();
	}

	@Override
	public JsonReturn insertSeedToKeyInfo(ArithmeticVo arithmetic, String path) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("新增失败");
		int num = arithmeticDAO.selectSeedToKeyWhetherExist(arithmetic.getVehicleId(),
				arithmetic.getConfigurationLevelId(), arithmetic.getModuleId(), arithmetic.getSupplierId(),
				arithmetic.getArithmeticLevelId());
		try {
			if (num == 0) {
				JNative jNative = new JNative(path + "temp\\" + arithmetic.getArithmeticName(),
						arithmetic.getEntryPoint());
				// 获取模块供应商的关联ID
				ReVehicleSupplier relevance = reVehicleSupplierDAO.selectPrimaryKeyById(arithmetic.getVehicleId(),
						arithmetic.getConfigurationLevelId(), arithmetic.getModuleId(), arithmetic.getSupplierId());
				// 新增算法信息
				Arithmetic arithmetics = new Arithmetic();
				arithmetics.setAlgorithmLevelId(arithmetic.getArithmeticLevelId());
				arithmetics.setAlgorithmType(1);
				// 算法前缀
				String prefix = arithmetic.getArithmeticName().substring(0,
						arithmetic.getArithmeticName().indexOf("_") + 1);
				// 算法名称
				String name = arithmetic.getArithmeticName().substring(arithmetic.getArithmeticName().indexOf("_") + 1);
				arithmetics.setArithmeticName(name);
				arithmetics.setArithmeticPrefix(prefix);
				arithmetics.setEntryPoint(arithmetic.getEntryPoint());

				int intR = arithmeticDAO.insertSelective(arithmetics);
				if (intR > 0) {
					ReArithmeticSupplier arithmeticSupplier = new ReArithmeticSupplier();
					arithmeticSupplier.setArithmeticId(arithmetics.getArithmeticId());
					arithmeticSupplier.setRelevanceId(relevance.getRelevanceId());
					int count = reArithmeticSupplierDAO.insertSelective(arithmeticSupplier);
					if (count > 0) {
						jsonReturn.setText("success");
					}
				}
				// 处理上传的附件
				File tempFile = new File(path + "temp" + File.separator + arithmetic.getArithmeticName());
				File saveFile = new File(path + "dll\\SeedToKey\\Level" + arithmetic.getArithmeticLevelId()
						+ File.separator + arithmetic.getArithmeticName());
				FileUtil.move(tempFile, saveFile, false);
				try {
					jNative.dispose();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 释放资源
			} else {
				jsonReturn.setText("存在有重复的数据,请重新选择");
			}
		} catch (NativeException e) {
			jsonReturn.setText("DLL接口不存在该函数");
			// e.printStackTrace();
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteSeedToKeyInfo(Integer arithmeticId, Integer arithmeticLevelId, String path) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		// 通过算法ID查询算法信息
		Arithmetic arithmetic = arithmeticDAO.selectByPrimaryKey(arithmeticId);
		int intR = arithmeticDAO.deleteArithmeticSupplierInfo(arithmeticId);
		if (intR > 0) {
			String savePath = path + "Level" + arithmetic.getAlgorithmLevelId() + File.separator
					+ arithmetic.getArithmeticPrefix() + arithmetic.getArithmeticName();
			FileUtil.del(savePath);
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public List<ArithmeticVo> selectVinToPinInfo(Integer vehicleId, String arithmeticName, String strName,
			String sortType, Integer startIndex, Integer pageSize) {
		Integer algorithmType = 2;
		return arithmeticDAO.selectArithmeticVehicle(vehicleId, arithmeticName, algorithmType, strName, sortType,
				startIndex, pageSize);
	}

	@Override
	public int selectVinToPinInfoRows(Integer vehicleId, String arithmeticName) {
		Integer algorithmType = 2;
		return arithmeticDAO.selectArithmeticVehicleRows(vehicleId, arithmeticName, algorithmType);
	}

	@Override
	public JsonReturn insertArithmeticVehicleInfo(ArithmeticVo arithmetic, String path, Integer algorithmType) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("新增失败");
		if (arithmetic != null) {
			int num = reArithmeticVehicleDAO.selectArithmeticWhetherExist(arithmetic.getVehicleId(), algorithmType);
			if (num == 0) {
				try {
					JNative jNative = new JNative(path + "temp\\" + arithmetic.getArithmeticName(),
							arithmetic.getEntryPoint());
					// 新增算法信息
					Arithmetic arithmetics = new Arithmetic();
					arithmetics.setAlgorithmType(algorithmType);
					// 算法前缀
					String prefix = arithmetic.getArithmeticName().substring(0,
							arithmetic.getArithmeticName().indexOf("_") + 1);
					// 算法名称
					String name = arithmetic.getArithmeticName()
							.substring(arithmetic.getArithmeticName().indexOf("_") + 1);
					arithmetics.setArithmeticName(name);
					arithmetics.setArithmeticPrefix(prefix);
					arithmetics.setEntryPoint(arithmetic.getEntryPoint());
					int intR = arithmeticDAO.insertSelective(arithmetics);
					if (intR > 0) {
						ReArithmeticVehicle vehicle = new ReArithmeticVehicle();
						vehicle.setArithmeticId(arithmetics.getArithmeticId());
						vehicle.setVehicleId(arithmetic.getVehicleId());
						int count = reArithmeticVehicleDAO.insertSelective(vehicle);
						if (count > 0) {
							jsonReturn.setText("success");
						}
					}
					// 处理上传的附件
					File tempFile = new File(path + "temp" + File.separator + arithmetic.getArithmeticName());
					String strPath = "";
					if (algorithmType == 2) {
						strPath = path + "dll\\VinToPin" + File.separator + arithmetic.getArithmeticName();
					} else {
						strPath = path + "dll\\VinToEsk" + File.separator + arithmetic.getArithmeticName();
					}
					File saveFile = new File(strPath);
					FileUtil.move(tempFile, saveFile, false);
					try {
						jNative.dispose();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // 释放资源
				} catch (NativeException e) {
					jsonReturn.setText("DLL接口不存在该函数");
					//e.printStackTrace();
				}
			} else {
				jsonReturn.setText("存在有重复的数据,请重新选择");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteArithmeticVehicleInfo(Integer arithmeticId, String path, Integer algorithmType) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		// 通过算法ID查询算法信息
		Arithmetic arithmetic = arithmeticDAO.selectByPrimaryKey(arithmeticId);
		int intR = arithmeticDAO.deleteVinTurnPinInfo(arithmeticId);
		if (intR > 0) {
			String savePath = "";
			if (algorithmType == 2) {
				savePath = path + "VinToPin" + File.separator + arithmetic.getArithmeticPrefix()
						+ arithmetic.getArithmeticName();
			} else {
				savePath = path + "VinToEsk" + File.separator + arithmetic.getArithmeticPrefix()
						+ arithmetic.getArithmeticName();
			}
			FileUtil.del(savePath);
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public List<ArithmeticVo> selectVinToEskInfo(Integer vehicleId, String arithmeticName, String strName,
			String sortType, Integer startIndex, Integer pageSize) {
		Integer algorithmType = 3;
		return arithmeticDAO.selectArithmeticVehicle(vehicleId, arithmeticName, algorithmType, strName, sortType,
				startIndex, pageSize);
	}

	@Override
	public int selectVinToEskInfoRows(Integer vehicleId, String arithmeticName) {
		Integer algorithmType = 3;
		return arithmeticDAO.selectArithmeticVehicleRows(vehicleId, arithmeticName, algorithmType);
	}

	@Override
	public List<ArithmeticVo> selectSeedAndPinToKeyInfo(Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, String arithmeticName, String strName, String sortType,
			Integer startIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		return arithmeticDAO.selectSeedAndPinToKeyInfo(vehicleId, configurationLevelId, moduleId, supplierId,
				arithmeticName, strName, sortType, startIndex, pageSize);
	}

	@Override
	public int selectSeedAndPinToKeyInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName) {
		// TODO Auto-generated method stub
		return arithmeticDAO.selectSeedAndPinToKeyInfoRows(vehicleId, configurationLevelId, moduleId, supplierId,
				arithmeticName);
	}

	@Override
	public JsonReturn insertSeedAndPinToKeyInfo(ArithmeticVo arithmetic, String path) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("新增失败");
		if (arithmetic != null) {
			int num = arithmeticDAO.selectSeedAndPinToKeyWhetherExist(arithmetic.getVehicleId(),
					arithmetic.getConfigurationLevelId(), arithmetic.getModuleId(), arithmetic.getSupplierId(),
					arithmetic.getArithmeticDelta());
			if (num == 0) {
				try {
					JNative jNative = new JNative(path + "temp\\" + arithmetic.getArithmeticName(),
							arithmetic.getEntryPoint());
					// 获取模块供应商的关联ID
					ReVehicleSupplier relevance = reVehicleSupplierDAO.selectPrimaryKeyById(arithmetic.getVehicleId(),
							arithmetic.getConfigurationLevelId(), arithmetic.getModuleId(), arithmetic.getSupplierId());
					// 新增算法信息
					Arithmetic arithmetics = new Arithmetic();
					arithmetics.setAlgorithmType(4);
					// 算法前缀
					String prefix = arithmetic.getArithmeticName().substring(0,
							arithmetic.getArithmeticName().indexOf("_") + 1);
					// 算法名称
					String name = arithmetic.getArithmeticName()
							.substring(arithmetic.getArithmeticName().indexOf("_") + 1);
					arithmetics.setArithmeticName(name);
					arithmetics.setArithmeticPrefix(prefix);
					arithmetics.setEntryPoint(arithmetic.getEntryPoint());
					arithmetics.setArithmeticDelta(arithmetic.getArithmeticDelta());

					int intR = arithmeticDAO.insertSelective(arithmetics);
					if (intR > 0) {
						ReArithmeticSupplier arithmeticSupplier = new ReArithmeticSupplier();
						arithmeticSupplier.setArithmeticId(arithmetics.getArithmeticId());
						arithmeticSupplier.setRelevanceId(relevance.getRelevanceId());
						int count = reArithmeticSupplierDAO.insertSelective(arithmeticSupplier);
						if (count > 0) {
							jsonReturn.setText("success");
						}
					}
					// 处理上传的附件
					File tempFile = new File(path + "temp" + File.separator + arithmetic.getArithmeticName());
					File saveFile = new File(path + "dll\\Seed&PinToKey\\" + arithmetic.getArithmeticName());
					FileUtil.move(tempFile, saveFile, false);
					try {
						jNative.dispose();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // 释放资源
				} catch (NativeException e) {
					jsonReturn.setText("DLL接口不存在该函数");
					// e.printStackTrace();
				}
			} else {
				jsonReturn.setText("存在有重复的数据,请重新选择");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteSeedAndPinTurnKeyInfo(Integer arithmeticId, String path) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		// 通过算法ID查询算法信息
		Arithmetic arithmetic = arithmeticDAO.selectByPrimaryKey(arithmeticId);
		int intR = arithmeticDAO.deleteArithmeticSupplierInfo(arithmeticId);
		if (intR > 0) {
			String savePath = path + arithmetic.getArithmeticPrefix() + arithmetic.getArithmeticName();
			FileUtil.del(savePath);
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

}
