package com.gx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gx.mapper.CanConfigurationDAO;
import com.gx.mapper.EolDAO;
import com.gx.mapper.ModuleDAO;
import com.gx.mapper.SupplierDAO;
import com.gx.mapper.VehicleDAO;
import com.gx.po.Module;
import com.gx.po.Supplier;
import com.gx.service.IAppCommonService;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.JsonReturn;

@Service
@Transactional
public class AppCommonServiceImpl implements IAppCommonService{
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private ModuleDAO moduleDAO;//模块
	@Autowired
	private SupplierDAO supplierDAO;//供应商
	@Autowired
	private EolDAO eolDAO;//EOL生产线
	@Autowired
	private CanConfigurationDAO canConfigurationDAO;//can配置

	@Override
	public JsonReturn selectCarTypeAll() {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> listvehicle = vehicleDAO.selectVehicleAll();
		if(listvehicle.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(listvehicle);
		}else {
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectModuleByCarTypeId(Integer carTypeId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int selectModuleByModuleName(String moduleName) {
		Module module = moduleDAO.selectModuleByName(moduleName);
		if(module!=null) {
			return module.getModuleId();
		}else {
			return 0;
		}
	}
	
	@Override
	public JsonReturn selectSupplierByModuleId(Integer moduleId) {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> listSupplier = supplierDAO.selectSupplierByModuleId(moduleId);
		if(listSupplier.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(listSupplier);
		}else {
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectDidContent(String moudleName, String supplierName) {
		return null;
	}

	@Override
	public JsonReturn selectModuleInfo(Module mod) {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> listMod = moduleDAO.selectModuleInfo(mod);
		if(listMod.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(listMod);
		}else {
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}
	

	@Override
	public JsonReturn selectSupInfo(Supplier sup) {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> listSup = supplierDAO.selectSupInfo(sup);
		if(listSup.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(listSup);
		}else {
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectEOL(String input) {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> listEol = eolDAO.selectEOL(input);
		if(listEol.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(listEol);
		}else {
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}

	
	
	@Override
	public JsonReturn selectCanByType(Integer mapTypeId) {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> listSup = canConfigurationDAO.selectCanByType(mapTypeId);
		if(listSup.size()>0) {
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(listSup);
		}else {
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}

	
	
	
}
