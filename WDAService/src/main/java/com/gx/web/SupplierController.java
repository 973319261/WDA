package com.gx.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.po.ConfigurationLevel;
import com.gx.po.Module;
import com.gx.po.ReVehicleSupplier;
import com.gx.po.Supplier;
import com.gx.po.Vehicle;
import com.gx.service.ISupplierService;
import com.gx.vo.LayuiJSON;
import com.gx.vo.Page;
import com.gx.vo.SupplierVo;
import com.gx.vo.UploadFile;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/supplier")
@Api(value = "模块供应商管理接口", description = "模块供应商管理相关api")
public class SupplierController {

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;

	@Autowired
	private ISupplierService supplierService;

	/**
	 * 查询模块供应商信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupplierInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询模块供应商信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSupplierInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<SupplierVo> supplier = supplierService.selectSupplierInfo(vehicleId, configurationLevelId, moduleId,
				supplierId, page.getStartIndex(), page.getLimit());
		int totalRows = supplierService.selectSupplierInfoRows(vehicleId, configurationLevelId, moduleId, supplierId);
		LayuiJSON<SupplierVo> supplierInfo = new LayuiJSON<SupplierVo>(totalRows, supplier);
		return JSONSerializer.toJSON(supplierInfo);
	}

	/**
	 * 通过配置等级ID查询模块下拉框
	 * 
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findModuleByConfigurationLevelId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过配置等级ID查询模块信息", httpMethod = "POST", response = Gson.class)
	public Object findModuleByConfigurationLevelId(Integer configurationLevelId, Integer vehicleId) {
		result = supplierService.selectModuleByConfigurationLevelId(configurationLevelId, vehicleId);
		return gson.toJson(result);
	}

	/**
	 * 通过模块ID查询供应商下拉框
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupplierInfoByModuleId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过模块ID查询供应商信息", httpMethod = "POST", response = Gson.class)
	public Object findSupplierInfoByModuleId(Integer moduleId, Integer configurationLevelId, Integer vehicleId) {
		result = supplierService.selectSupplierInfoByModuleId(moduleId, configurationLevelId, vehicleId);
		return gson.toJson(result);
	}

	/**
	 * 新增供应商模块信息
	 * 
	 * @param reVehicleSupplier
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertSupplierInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增供应商模块信息", httpMethod = "POST", response = Gson.class)
	public Object insertSupplierInfo(ReVehicleSupplier reVehicleSupplier) {
		result = supplierService.insertSupplierInfo(reVehicleSupplier);
		return gson.toJson(result);
	}

	/**
	 * 修改供应商模块信息
	 * 
	 * @param reVehicleSupplier
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSupplierInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改供应商模块信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateSupplierInfo(ReVehicleSupplier reVehicleSupplier) {
		result = supplierService.updateSupplierInfo(reVehicleSupplier);
		return gson.toJson(result);
	}

	/**
	 * 删除模块供应商信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSupplierInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除模块供应商信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteSupplierInfo(int relevanceId) {
		result = supplierService.deleteSupplierInfo(relevanceId);
		return gson.toJson(result);
	}

	/**
	 * 上传车型图片
	 * 
	 * @param request
	 * @param uploadFile
	 *            上传的文件
	 * @return
	 */
	@RequestMapping(value = "/uploadCarTypeImage", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "上传车型图片", httpMethod = "GET", response = UploadFile.class)
	public UploadFile uploadCarTypeImage(HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
		int code = -1;
		String src = "";
		// E:\MavenWorkSpace\TestFive\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\WDAService\fileDir\temp
		try {
			String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\temp";
			String fileName = uploadFile(uploadFile, path);
			code = 0;
			src = fileName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			code = -1;
			e.printStackTrace();
		}

		List<String> data = new ArrayList<String>();
		data.add(src);
		UploadFile file = UploadFile.getUploadFile(src, code, data);
		return file;
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 *            文件
	 * @param path
	 *            文件上传路径
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(MultipartFile file, String path) throws IOException {
		String name = file.getOriginalFilename();
		String suffixName = name.substring(name.lastIndexOf("."));// 获取后缀名
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		UUID uuId = UUID.randomUUID();

		String fileName = dateFormat.format(new Date()) + "-" + uuId.toString() + suffixName;
		File tempFile = new File(path, fileName);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdir();
		}
		tempFile.createNewFile();
		file.transferTo(tempFile);
		return tempFile.getName();
	}

	/**
	 * 通过车型ID查询车型图片
	 * 
	 * @param carTypeId
	 *            车型ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCarTypeImageById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过车型ID查询车型图片", httpMethod = "POST", response = Gson.class)
	public Object findCarTypeImageById(Integer vehicleId) {
		result = supplierService.selectCarTypeImageById(vehicleId);
		return gson.toJson(result);
	}

	/**
	 * 保存车型图片
	 * 
	 * @param carType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCarTypeImage", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "保存车型图片", httpMethod = "POST", response = Gson.class)
	public Object saveCarTypeImage(HttpServletRequest request, Vehicle vehicle) {
		String savePath = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\";
		result = supplierService.saveCarTypeImage(vehicle, savePath);
		cleanTemps(request);
		return gson.toJson(result);
	}

	/**
	 * 查询车型信息
	 * 
	 * @param vehicleName
	 *            车型名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findVehicleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询车型信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findVehicleInfo(String vehicleName, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Vehicle> vehicle = supplierService.selectVehicleInfo(vehicleName, page.getStartIndex(), page.getLimit());
		int totalRows = supplierService.selectVehicleInfoRows(vehicleName);
		LayuiJSON<Vehicle> vehicleInfo = new LayuiJSON<Vehicle>(totalRows, vehicle);
		return JSONSerializer.toJSON(vehicleInfo);
	}

	/**
	 * 新增车型信息
	 * 
	 * @param request
	 * @param vehicle
	 *            车型实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertVehicleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增车型信息", httpMethod = "POST", response = Gson.class)
	public Object insertVehicleInfo(HttpServletRequest request, Vehicle vehicle) {
		result = supplierService.insertVehicleInfo(vehicle);
		if (vehicle.getVehiclePicture() != null) {
			String savePath = request.getSession().getServletContext().getRealPath("") + File.separator
					+ "fileDir\\vehicleImage";
			// 如果目录不存在则创建
			File uploadDir = new File(savePath);
			if (!uploadDir.exists()) {
				FileUtil.mkdir(uploadDir);
			}
			File temp = new File(request.getSession().getServletContext().getRealPath("") + File.separator
					+ "fileDir\\temp\\" + vehicle.getVehiclePicture());
			File save = new File(savePath + File.separator + vehicle.getVehiclePicture());
			if (temp.exists()) {
				FileUtil.move(temp, save, true);
			}
		}
		return gson.toJson(result);
	}

	/**
	 * 查询模块信息
	 * 
	 * @param moduleName
	 *            模块名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findModuleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询模块信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findModuleInfo(String moduleName, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Module> module = supplierService.selectModuleMessage(moduleName, page.getStartIndex(), page.getLimit());
		int totalRows = supplierService.selectModuleMessageRows(moduleName);
		LayuiJSON<Module> moduleInfo = new LayuiJSON<Module>(totalRows, module);
		return JSONSerializer.toJSON(moduleInfo);
	}

	/**
	 * 查询供应商信息
	 * 
	 * @param moduleName
	 *            供应商名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupplierMessage", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询供应商信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSupplierMessage(String supplierName, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Supplier> supplier = supplierService.selectSupplierInfo(supplierName, page.getStartIndex(),
				page.getLimit());
		int totalRows = supplierService.selectSupplierInfoRows(supplierName);
		LayuiJSON<Supplier> supplierInfo = new LayuiJSON<Supplier>(totalRows, supplier);
		return JSONSerializer.toJSON(supplierInfo);
	}

	/**
	 * 修改车型信息
	 * 
	 * @param vehicle
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateVehicleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改车型信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateVehicleInfo(HttpServletRequest request, Vehicle vehicle) {
		String savePath = request.getSession().getServletContext().getRealPath("") + File.separator
				+ "fileDir\\vehicleImage";
		result = supplierService.updateVehicleInfo(vehicle, savePath);
		if (vehicle.getVehiclePicture() != null) {
			// 如果目录不存在则创建
			File uploadDir = new File(savePath);
			if (!uploadDir.exists()) {
				FileUtil.mkdir(uploadDir);
			}
			File temp = new File(request.getSession().getServletContext().getRealPath("") + File.separator
					+ "fileDir\\temp\\" + vehicle.getVehiclePicture());
			File save = new File(savePath + File.separator + vehicle.getVehiclePicture());
			if (temp.exists()) {
				FileUtil.move(temp, save, true);
			}
		}
		return gson.toJson(result);
	}

	/**
	 * 删除车型信息
	 * 
	 * @param vehicleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteVehicleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除车型信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteVehicleInfo(HttpServletRequest request, Integer vehicleId) {
		String savePath = request.getSession().getServletContext().getRealPath("") + File.separator
				+ "fileDir\\vehicleImage\\";
		result = supplierService.deleteVehicleInfo(vehicleId, savePath);
		return gson.toJson(result);
	}

	/**
	 * 新增模块信息
	 * 
	 * @param module
	 *            模块实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertModuleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增模块信息", httpMethod = "POST", response = Gson.class)
	public Object insertModuleInfo(Module module) {
		result = supplierService.insertModuleInfo(module);
		return gson.toJson(result);
	}

	/**
	 * 修改模块信息
	 * 
	 * @param module
	 *            模块实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateModuleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改模块信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateModuleInfo(Module module) {
		result = supplierService.updateModuleInfo(module);
		return gson.toJson(result);
	}

	/**
	 * 删除模块信息
	 * 
	 * @param moduleId
	 *            模块id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteModuleInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除模块信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteModuleInfo(Integer moduleId) {
		result = supplierService.deleteModuleInfo(moduleId);
		return gson.toJson(result);
	}

	/**
	 * 新增供应商信息
	 * 
	 * @param supplier
	 *            供应商实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertSupplierMessage", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增供应商信息", httpMethod = "POST", response = Gson.class)
	public Object insertSupplierMessage(Supplier supplier) {
		result = supplierService.insertSupplierMessage(supplier);
		return gson.toJson(result);
	}

	/**
	 * 修改供应商信息
	 * 
	 * @param supplier
	 *            供应商实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSupplierMessage", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改供应商信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateSupplierMessage(Supplier supplier) {
		result = supplierService.updateSupplierMessage(supplier);
		return gson.toJson(result);
	}

	/**
	 * 删除供应商信息
	 * 
	 * @param supplierId
	 *            供应商id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSupplierMessage", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除供应商信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteSupplierMessage(Integer supplierId) {
		result = supplierService.deleteSupplierMessage(supplierId);
		return gson.toJson(result);
	}

	/**
	 * 通过车型ID查询配置等级信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findConfigurationLevelInfoById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过车型ID查询配置等级信息", httpMethod = "POST", response = Gson.class)
	public Object findConfigurationLevelInfoById(Integer vehicleId) {
		result = supplierService.selectConfigurationLevelInfoById(vehicleId);
		return gson.toJson(result);
	}

	/**
	 * 清空临时文件夹 和 session
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/cleanTemps")
	@ApiOperation(value = "清空临时文件夹和session", httpMethod = "POST", response = Boolean.class)
	public @ResponseBody static boolean cleanTemps(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\temp";
		FileUtil.clean(path);
		return true;
	}

	/**
	 * 查询配置信息
	 * 
	 * @param configurationLevelName
	 *            配置名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findConfigurationInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询配置信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findConfigurationInfo(String configurationLevelName, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<ConfigurationLevel> module = supplierService.selectConfigurationInfo(configurationLevelName,
				page.getStartIndex(), page.getLimit());
		int totalRows = supplierService.selectConfigurationInfoRows(configurationLevelName);
		LayuiJSON<ConfigurationLevel> moduleInfo = new LayuiJSON<ConfigurationLevel>(totalRows, module);
		return JSONSerializer.toJSON(moduleInfo);
	}

	/**
	 * 新增配置信息
	 * 
	 * @param configurationLevel
	 *            配置实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertConfigurationInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增配置信息", httpMethod = "POST", response = Gson.class)
	public Object insertConfigurationInfo(ConfigurationLevel configurationLevel) {
		result = supplierService.insertConfigurationInfo(configurationLevel);
		return gson.toJson(result);
	}

	/**
	 * 修改配置信息
	 * 
	 * @param configurationLevel
	 *            配置实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateConfigurationInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改配置信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateConfigurationInfo(ConfigurationLevel configurationLevel) {
		result = supplierService.updateConfigurationInfo(configurationLevel);
		return gson.toJson(result);
	}

	/**
	 * 删除配置信息
	 * 
	 * @param configurationLevelId
	 *            配置id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteConfigurationInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除配置信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteConfigurationInfo(Integer configurationLevelId) {
		result = supplierService.deleteConfigurationInfo(configurationLevelId);
		return gson.toJson(result);
	}

}
