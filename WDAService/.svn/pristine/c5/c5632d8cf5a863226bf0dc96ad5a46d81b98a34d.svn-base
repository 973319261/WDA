package com.gx.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.po.Version;
import com.gx.service.IVersionService;
import com.gx.util.ApkUtil;
import com.gx.util.DateUtil;
import com.gx.vo.LayuiJSON;
import com.gx.vo.Page;
import com.gx.vo.UploadFile;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONSerializer;

/**
 * apk版本
 * 
 * @author LJ
 *
 */
@Controller
@RequestMapping("/version")
@Api(value = "版本更新接口", description = "版本更新api")
public class VersionController {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;
	
	@Autowired
	private IVersionService versionService;
	
	/**
	 * 查询版本信息
	 * 
	 * @param versionNumber
	 *            版本号
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            排序类型（升序、降序）
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findVersionInfo")
	@ApiOperation(value = "查询版本信息", httpMethod = "GET", response = JSONSerializer.class)
	public Object findVersionInfo(String versionNumber, String strName, String sortType, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Version> version = versionService.selectVersionInfo(versionNumber, strName, sortType, page.getStartIndex(),
				page.getLimit());
		int totalRows = versionService.selectVersionInfoRows(versionNumber, strName, sortType);
		LayuiJSON<Version> versionInfo = new LayuiJSON<Version>(totalRows, version);
		return JSONSerializer.toJSON(versionInfo);
	}

	/**
	 * 查询版本标题是否存在
	 * 
	 * @param versionTitle
	 *            版本标题
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findVersionByNum")
	@ApiOperation(value = "查询版本标题是否存在", httpMethod = "GET", response = Boolean.class)
	public boolean findVersionByNum(String versionTitle) {
		Version ver = versionService.selectVersionByNum(versionTitle);
		if (ver != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上传apk文件
	 * 
	 * @param request
	 * @param uploadFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadTheApk", method = RequestMethod.POST)
	@ApiOperation(value = "上传apk文件", httpMethod = "GET", response = UploadFile.class)
	public UploadFile uploadTheApk(HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
		int code = -1;
		String src = "";
		String name = "";
		List<String> data = new ArrayList<String>();
		try {
			String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir"
					+ File.separator + "tempApk" + File.separator + "apk";
			File tempPath = new File(path);
			if (!tempPath.exists()) {
				tempPath.getParentFile().mkdir();
			}
			name = uploadFile.getOriginalFilename();
			String fileName = DateUtil.getDateTimeAbb() + name;// 拼接时间前缀
			File tempFile = new File(path, fileName);
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdir();
			}
			tempFile.createNewFile();
			uploadFile.transferTo(tempFile);
			code = 0;
			src = fileName;
			String[] apkInfo = ApkUtil.getApkInfo(path + File.separator + fileName);
			data.add(apkInfo[1]);// 获取版本号
			data.add(apkInfo[2]);// 获取版本标题
		} catch (IOException e) {
			// TODO Auto-generated catch block
			code = -1;
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UploadFile file = UploadFile.getUploadFile(src, code, data);
		return file;
	}

	/**
	 * 清空上传apk的临时文件夹内容
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cleanTempApk")
	@ApiOperation(value = "清空上传apk的临时文件夹内容",notes="清空临时文件夹的内容", httpMethod = "DELETE", response = Boolean.class)
	public static boolean cleanTempApk(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\tempApk";
		FileUtil.clean(path);
		return true;
	}

	/**
	 * 新增apk版本
	 * 
	 * @param request
	 * @param version
	 *            版本实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addVersion", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增apk版本", httpMethod = "POST", response = Object.class)
	public Object addVersion(HttpServletRequest request, Version version) {
		File tempfile = new File(request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir"
				+ File.separator + "tempApk" + File.separator + "apk" + File.separator + version.getApkUrl());
		File savefile = new File(request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir"
				+ File.separator + "apk" + File.separator + version.getApkUrl());
		FileUtil.move(tempfile, savefile, true);
		return versionService.insertVersion(version);
	}

	/**
	 * 删除版本信息
	 * 
	 * @param request
	 * @param versionId
	 *            版本id
	 * @param apkUrl
	 *            apk路径
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteVersion")
	@ApiOperation(value = "删除版本信息", httpMethod = "DELETE", response = Object.class)
	public Object deleteVersion(HttpServletRequest request, int versionId, String apkUrl) {
		Version version = versionService.findVersionById(versionId);
		if (version != null) {
			File apkpath = new File(
					request.getSession().getServletContext().getRealPath("") + File.separator + version.getApkUrl());
			if (apkpath.exists()) {
				FileUtil.del(apkpath);// 删除保存文件夹的内容
			}
		}
		return versionService.deleteVersion(versionId, apkUrl);
	}

	/**
	 * apk文件下载
	 * 
	 * @param request
	 * @param response
	 * @param apkName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadApk", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "apk文件下载", httpMethod = "GET", response = ResponseEntity.class)
	public ResponseEntity<byte[]> downloadApk(HttpServletRequest request, HttpServletResponse response, String apkName)
			throws Exception {
		// 文件下载路径
		String path = request.getSession().getServletContext().getRealPath("") + File.separator;
		// 获取文件
		File file = new File(path, apkName);
		// 如果文件不存在跳转到异常页面
		if (!file.exists()) {
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			return null;
		}
		// 设置header
		HttpHeaders headers = new HttpHeaders();
		// application/octet-stream二进制流数据（最常见的文件下载）
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 通知浏览器以attachment（下载方式） 下载文件，文件名称为指定名称
		String name = apkName.substring(apkName.lastIndexOf("/") + 15);// 截取时间前缀，不显示
		headers.setContentDispositionFormData("attachment", new String(name.getBytes("utf-8"), "ISO8859-1"));
		// 返回文件下载相关参数
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}	

}
