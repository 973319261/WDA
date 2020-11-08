package com.gx.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.gx.service.ISecurityService;
import com.gx.vo.ArithmeticVo;
import com.gx.vo.LayuiJSON;
import com.gx.vo.Page;
import com.gx.vo.UploadFile;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/security")
@Api(value = "安全算法管理接口", description = "安全算法管理相关api")
public class SecurityController {

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;

	@Autowired
	ISecurityService securityService;

	/**
	 * 查询Seed转Key管理信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticName
	 *            安全算法名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSeedToKeyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询Seed转Key管理信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSeedToKeyInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName,String strName, String sortType, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<ArithmeticVo> arithmetic = securityService.selectSeedToKeyInfo(vehicleId, configurationLevelId, moduleId,
				supplierId, arithmeticName, strName, sortType, page.getStartIndex(), page.getLimit());
		int totalRows = securityService.selectSeedToKeyInfoRows(vehicleId, configurationLevelId, moduleId, supplierId,
				arithmeticName);
		LayuiJSON<ArithmeticVo> arithmeticInfo = new LayuiJSON<ArithmeticVo>(totalRows, arithmetic);
		return JSONSerializer.toJSON(arithmeticInfo);
	}

	/**
	 * 查询安全算法(绑定下拉框)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findArithmeticInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询安全算法", notes="用于绑定下拉框",httpMethod = "POST", response = Gson.class)
	public Object findArithmeticInfo(Integer algorithmType) {
		result = securityService.selectArithmeticInfo(algorithmType);
		return gson.toJson(result);
	}

	/**
	 * 查询算法等级(绑定下拉框)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findArithmeticLevelInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询算法等级", notes="用于绑定下拉框",httpMethod = "POST", response = Gson.class)
	public Object findArithmeticLevelInfo() {
		result = securityService.selectArithmeticLevelInfo();
		return gson.toJson(result);
	}

	/**
	 * 上传Dll文件
	 * 
	 * @param request
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping(value = "/uploadDllFile", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "上传Dll文件", httpMethod = "GET", response = UploadFile.class)
	public UploadFile uploadDllFile(HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
		int code = -1;
		String src = "";
		// E:\MavenWorkSpace\TestFive\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\WDAService\fileDir\temp
		try {
			String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\temp";
			String fileName = uploadFile(request, uploadFile, path);
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
	public static String uploadFile(HttpServletRequest request, MultipartFile file, String path) throws IOException {
		String name = file.getOriginalFilename();
		String suffixName = name.substring(name.lastIndexOf("."));// 获取后缀名
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddHHmmssSSS");
		String time = dateFormat.format(new Date());
		System.out.println("文件名称:" + time + "_" + name.substring(0, name.lastIndexOf(".")) + suffixName);

		String fileName = time + "_" + name.substring(0, name.lastIndexOf(".")) + suffixName;
		File tempFile = new File(path, fileName);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdir();
		}
		tempFile.createNewFile();
		file.transferTo(tempFile);
		return tempFile.getName();
	}

	/**
	 * 新增Seed转Key信息
	 * 
	 * @param arithmetic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertSeedToKeyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增Seed转Key信息", httpMethod = "POST", response = Gson.class)
	public Object insertSeedToKeyInfo(HttpServletRequest request, ArithmeticVo arithmetic) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\";
		result = securityService.insertSeedToKeyInfo(arithmetic, path);
		return gson.toJson(result);
	}

	/**
	 * 删除Seed转Key信息
	 * 
	 * @param request
	 * @param arithmeticId
	 *            算法ID
	 * @param arithmeticLevelId
	 *            算法等级ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSeedToKeyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除Seed转Key信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteSeedToKeyInfo(HttpServletRequest request, Integer arithmeticId, Integer arithmeticLevelId) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator
				+ "fileDir\\dll\\SeedToKey\\";
		result = securityService.deleteSeedToKeyInfo(arithmeticId, arithmeticLevelId, path);
		return gson.toJson(result);
	}

	/**
	 * 查询Vin转Pin管理信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param arithmeticName
	 *            安全算法
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findVinToPinInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询Vin转Pin管理信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findVinToPinInfo(Integer vehicleId, String arithmeticName, String strName, String sortType,
			Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<ArithmeticVo> arithmetic = securityService.selectVinToPinInfo(vehicleId, arithmeticName, strName, sortType,
				page.getStartIndex(), page.getLimit());
		int totalRows = securityService.selectVinToPinInfoRows(vehicleId, arithmeticName);
		LayuiJSON<ArithmeticVo> arithmeticInfo = new LayuiJSON<ArithmeticVo>(totalRows, arithmetic);
		return JSONSerializer.toJSON(arithmeticInfo);
	}

	/**
	 * 新增Vin转Pin管理信息
	 * 
	 * @param request
	 * @param arithmetic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertVinTurnPinInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增Vin转Pin管理信息", httpMethod = "POST", response = Gson.class)
	public Object insertVinTurnPinInfo(HttpServletRequest request, ArithmeticVo arithmetic) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\";
		result = securityService.insertArithmeticVehicleInfo(arithmetic, path, 2);
		return gson.toJson(result);
	}

	/**
	 * 删除Vin转Pin管理信息
	 * 
	 * @param request
	 * @param arithmeticId
	 *            算法ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteVinTurnPinInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除Vin转Pin管理信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteVinTurnPinInfo(HttpServletRequest request, Integer arithmeticId) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\dll\\";
		result = securityService.deleteArithmeticVehicleInfo(arithmeticId, path, 2);
		return gson.toJson(result);
	}

	/**
	 * 查询Vin转Esk管理信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param arithmeticName
	 *            算法名称
	 * @param strName
	 * @param sortType
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findVinToEskInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询Vin转Esk管理信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findVinToEskInfo(Integer vehicleId, String arithmeticName, String strName, String sortType,
			Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<ArithmeticVo> arithmetic = securityService.selectVinToEskInfo(vehicleId, arithmeticName, strName, sortType,
				page.getStartIndex(), page.getLimit());
		int totalRows = securityService.selectVinToEskInfoRows(vehicleId, arithmeticName);
		LayuiJSON<ArithmeticVo> arithmeticInfo = new LayuiJSON<ArithmeticVo>(totalRows, arithmetic);
		return JSONSerializer.toJSON(arithmeticInfo);
	}

	/**
	 * 清空临时文件夹
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/cleanTemps")
	@ApiOperation(value = "清空临时文件夹", notes="清空文件上传的临时文件夹",httpMethod = "POST", response = Boolean.class)
	public @ResponseBody static boolean cleanTemps(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\temp";
		FileUtil.clean(path);
		return true;
	}

	/**
	 * 新增Vin转Esk管理信息
	 * 
	 * @param request
	 * @param arithmetic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertVinTurnEskInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增Vin转Esk管理信息", httpMethod = "POST", response = Gson.class)
	public Object insertVinTurnEskInfo(HttpServletRequest request, ArithmeticVo arithmetic) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\";
		result = securityService.insertArithmeticVehicleInfo(arithmetic, path, 3);
		return gson.toJson(result);
	}

	/**
	 * 删除Vin转Esk管理信息
	 * 
	 * @param request
	 * @param arithmeticId
	 *            算法ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteVinTurnEskInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除Vin转Esk管理信息", httpMethod = "POST", response = Gson.class)
	public Object deleteVinTurnEskInfo(HttpServletRequest request, Integer arithmeticId) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\dll\\";
		result = securityService.deleteArithmeticVehicleInfo(arithmeticId, path, 3);
		return gson.toJson(result);
	}

	/**
	 * 查询SeedAndPinToKey信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param arithmeticName
	 *            安全算法
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            倒叙、升序
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSeedAndPinToKeyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询SeedAndPinToKey信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSeedAndPinToKeyInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String arithmeticName, String strName, String sortType, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<ArithmeticVo> arithmetic = securityService.selectSeedAndPinToKeyInfo(vehicleId, configurationLevelId,
				moduleId, supplierId, arithmeticName, strName, sortType, page.getStartIndex(), page.getLimit());
		int totalRows = securityService.selectSeedAndPinToKeyInfoRows(vehicleId, configurationLevelId, moduleId,
				supplierId, arithmeticName);
		LayuiJSON<ArithmeticVo> arithmeticInfo = new LayuiJSON<ArithmeticVo>(totalRows, arithmetic);
		return JSONSerializer.toJSON(arithmeticInfo);
	}

	/**
	 * 新增SeedAndPinToKey信息
	 * 
	 * @param request
	 * @param arithmetic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertSeedAndPinToKeyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增SeedAndPinToKey信息", httpMethod = "POST", response = Gson.class)
	public Object insertSeedAndPinToKeyInfo(HttpServletRequest request, ArithmeticVo arithmetic) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator
				+ "fileDir\\";
		result = securityService.insertSeedAndPinToKeyInfo(arithmetic, path);
		return gson.toJson(result);
	}
	
	/**
	 * 删除SeedAndPinToKey信息
	 * @param request
	 * @param arithmeticId 算法ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSeedAndPinTurnKeyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除SeedAndPinToKey信息", httpMethod = "POST", response = Gson.class)
	public Object deleteSeedAndPinTurnKeyInfo(HttpServletRequest request, Integer arithmeticId) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\dll\\Seed&PinToKey\\";
		result=securityService.deleteSeedAndPinTurnKeyInfo(arithmeticId, path);
		return gson.toJson(result);
	}
}
