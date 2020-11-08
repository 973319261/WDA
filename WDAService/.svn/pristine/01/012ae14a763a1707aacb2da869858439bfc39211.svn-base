package com.gx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gx.mapper.FaultCodeDAO;
import com.gx.mapper.ReSupplierFaultcodeDAO;
import com.gx.mapper.ReVehicleSupplierDAO;
import com.gx.mapper.VehicleDAO;
import com.gx.po.FaultCode;
import com.gx.po.ReSupplierFaultcode;
import com.gx.po.ReVehicleSupplier;
import com.gx.service.IFaultCodeService;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.FaultCodeVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.SupplierVo;

@Transactional
@Service("faultCodeService")
public class FaultCodeServiceImpl implements IFaultCodeService {

	// 注入DAO层
	@Autowired
	VehicleDAO vehicleDAO;
	@Autowired
	FaultCodeDAO faultCodeDAO;
	@Autowired
	ReSupplierFaultcodeDAO reSupplierFaultcodeDAO;
	@Autowired
	ReVehicleSupplierDAO reVehicleSupplierDAO;

	@Override
	public List<AppendOptionVo> selectCarTypeInfo() {
		// TODO Auto-generated method stub
		return vehicleDAO.selectVehicleAll();
	}

	@Override
	public List<SupplierVo> selectFaultCodeInfo(int carTypeId, int moudleId, int supplierId, int startIndex,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectFaultCodeInfoRows(int carTypeId, int moudleId, int supplierId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FaultCodeVo> selectSupFault(FaultCodeVo fault) {
		// TODO Auto-generated method stub
		return faultCodeDAO.selectSupFault(fault);
	}

	@Override
	public int selectSupFaultCount(FaultCodeVo fault) {
		// TODO Auto-generated method stub
		return faultCodeDAO.selectSupFaultCount(fault).size();
	}

	@Override
	public JsonReturn insertFaultCodeInfo(List<FaultCodeVo> listFault, ReVehicleSupplier supplier) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setCode(500);
		jsonReturn.setText("保存失败");
		if (listFault != null && supplier != null) {
			FaultCode fault = null;
			int count = 0;
			for (int i = 0; i < listFault.size(); i++) {
				// 查询模块供应商管理ID
				ReVehicleSupplier suppliers = reVehicleSupplierDAO.selectPrimaryKeyById(supplier.getVehicleId(),
						supplier.getConfigurationLevelId(), supplier.getModuleId(), supplier.getSupplierId());
				int row = faultCodeDAO.selectFaultCodeWhetherExist(suppliers.getRelevanceId(),
						listFault.get(i).getDtc());
				if (row == 0) {
					fault = new FaultCode();
					fault.setDtc(listFault.get(i).getDtc());
					fault.setHexDtc(listFault.get(i).getHexDtc());
					fault.setChineseDescription(listFault.get(i).getChineseDescription());
					fault.setEnglishDescription(listFault.get(i).getEnglishDescription());
					fault.setOperatingConditions(listFault.get(i).getOperatingConditions());
					fault.setSettingConditions(listFault.get(i).getSettingConditions());
					fault.setSettingAfterConditions(listFault.get(i).getSettingAfterConditions());
					fault.setRestoreConditions(listFault.get(i).getRestoreConditions());
					fault.setActivateMilRegulations(listFault.get(i).getActivateMilRegulations());
					fault.setMilOffRegulations(listFault.get(i).getMilOffRegulations());
					fault.setClearConditions(listFault.get(i).getClearConditions());
					int intR = faultCodeDAO.insertSelective(fault);
					if (intR > 0) {
						ReSupplierFaultcode supplierFault = new ReSupplierFaultcode();
						supplierFault.setFaultCodeId(fault.getFaultCodeId());
						supplierFault.setRelevanceId(suppliers.getRelevanceId());
						int num = reSupplierFaultcodeDAO.insertSelective(supplierFault);
						if (num > 0) {
							count++;
						}
					}
				}
			}
			if (count == listFault.size()) {
				jsonReturn.setCode(200);
				jsonReturn.setText("保存成功");
			} else if (count > 0 && count < listFault.size()) {
				jsonReturn.setCode(200);
				int nums = listFault.size() - count;
				jsonReturn.setText(count + "条数据保存成功，" + nums + "条数数据保存失败，失败原因：可能存在有相同的数据!");
			} else if (count == 0) {
				jsonReturn.setCode(201);
				jsonReturn.setText("保存失败,可能存在有相同的数据");
			}
		} else {
			jsonReturn.setText("暂无需要保存的数据，或者数据出现异常");
		}
		return jsonReturn;
	}

	@Override
	public List<FaultCodeVo> selectfaultCodeDetailInfo(Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, String dtc, String strName, String sortType, Integer startIndex,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return faultCodeDAO.selectfaultCodeDetailInfo(vehicleId, configurationLevelId, moduleId, supplierId, dtc,
				strName, sortType, startIndex, pageSize);
	}

	@Override
	public int selectfaultCodeDetailInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String dtc) {
		// TODO Auto-generated method stub
		return faultCodeDAO.selectfaultCodeDetailInfoRows(vehicleId, configurationLevelId, moduleId, supplierId, dtc);
	}

	@Override
	public JsonReturn addFaultCodeInfo(FaultCodeVo faultCode) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("保存失败");
		if (faultCode != null) {
			int row = faultCodeDAO.selectFaultCodeWhetherExist(faultCode.getRelevanceId(), faultCode.getDtc());
			if (row == 0) {
				FaultCode fault = new FaultCode();
				fault.setDtc(faultCode.getDtc());
				fault.setHexDtc(faultCode.getHexDtc());
				fault.setChineseDescription(faultCode.getChineseDescription());
				fault.setEnglishDescription(faultCode.getEnglishDescription());
				fault.setOperatingConditions(faultCode.getOperatingConditions());
				fault.setSettingConditions(faultCode.getSettingConditions());
				fault.setSettingAfterConditions(faultCode.getSettingAfterConditions());
				fault.setRestoreConditions(faultCode.getRestoreConditions());
				fault.setActivateMilRegulations(faultCode.getActivateMilRegulations());
				fault.setMilOffRegulations(faultCode.getMilOffRegulations());
				fault.setClearConditions(faultCode.getClearConditions());
				int intR = faultCodeDAO.insertSelective(fault);
				if (intR > 0) {
					ReSupplierFaultcode supplierFault = new ReSupplierFaultcode();
					supplierFault.setFaultCodeId(fault.getFaultCodeId());
					supplierFault.setRelevanceId(faultCode.getRelevanceId());
					int num = reSupplierFaultcodeDAO.insertSelective(supplierFault);
					if (num > 0) {
						jsonReturn.setText("success");
					}
				}
			} else {
				jsonReturn.setText("保存失败,可能存在有相同的数据");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateFaultCodeInfo(FaultCodeVo faultCode) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("保存失败");
		if (faultCode != null) {
			int row = faultCodeDAO.selectRepetitionFaultCodeRows(faultCode.getRelevanceId(), faultCode.getDtc(),
					faultCode.getFaultCodeId());
			if (row == 0) {
				FaultCode fault = new FaultCode();
				fault.setFaultCodeId(faultCode.getFaultCodeId());
				fault.setDtc(faultCode.getDtc());
				fault.setHexDtc(faultCode.getHexDtc());
				fault.setChineseDescription(faultCode.getChineseDescription());
				fault.setEnglishDescription(faultCode.getEnglishDescription());
				fault.setOperatingConditions(faultCode.getOperatingConditions());
				fault.setSettingConditions(faultCode.getSettingConditions());
				fault.setSettingAfterConditions(faultCode.getSettingAfterConditions());
				fault.setRestoreConditions(faultCode.getRestoreConditions());
				fault.setActivateMilRegulations(faultCode.getActivateMilRegulations());
				fault.setMilOffRegulations(faultCode.getMilOffRegulations());
				fault.setClearConditions(faultCode.getClearConditions());
				int intR = faultCodeDAO.updateByPrimaryKeySelective(fault);
				if (intR > 0) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("保存失败,可能存在有相同的数据");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteFaultCodeInfo(Integer reId, Integer faultCodeId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		if (reId > 0 && faultCodeId > 0) {
			int intR = faultCodeDAO.deleteByPrimaryKey(faultCodeId);
			if (intR > 0) {
				int num = reSupplierFaultcodeDAO.deleteByPrimaryKey(reId);
				if (num > 0) {
					jsonReturn.setText("success");
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteFaultCodeInfoTwo(Integer relevanceId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		if (relevanceId > 0) {
			int intR = reSupplierFaultcodeDAO.deleteFaultCodeInfoTwo(relevanceId);
			if (intR > 0) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<FaultCodeVo> selectFaultCodeById(Integer relevanceId) {
		// TODO Auto-generated method stub
		return faultCodeDAO.selectFaultCodeById(relevanceId);
	}

}
