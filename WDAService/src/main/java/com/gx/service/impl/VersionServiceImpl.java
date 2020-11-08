package com.gx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gx.mapper.VersionDAO;
import com.gx.po.Version;
import com.gx.service.IVersionService;
import com.gx.vo.JsonReturn;

import cn.hutool.core.io.FileUtil;

@Transactional
@Service
public class VersionServiceImpl implements IVersionService {
	@Autowired
	private VersionDAO versionDAO;

	@Override
	public JsonReturn getVersionInfo() {
		JsonReturn jsonReturn = new JsonReturn();
		Version version = versionDAO.getVersionInfo();
		jsonReturn.setCode(200);
		jsonReturn.setData(version);
		return jsonReturn;
	}

	@Override
	public JsonReturn getAllVersionInfo() {
		JsonReturn jsonReturn = new JsonReturn();
		List<Version> version = versionDAO.getAllVersionInfo();
		jsonReturn.setCode(200);
		jsonReturn.setData(version);
		return jsonReturn;
	}

	@Override
	public List<Version> selectVersionInfo(String versionNumber, String strName, String sortType, int startIndex,
			int pageSize) {
		return versionDAO.selectVersionInfo(versionNumber, strName, sortType, startIndex, pageSize);
	}

	@Override
	public int selectVersionInfoRows(String versionNumber, String strName, String sortType) {
		return versionDAO.selectVersionInfoRows(versionNumber, strName, sortType);
	}

	@Override
	public Version selectVersionByNum(String versionTitle) {
		return versionDAO.selectVersionByNum(versionTitle);
	}

	@Override
	public JsonReturn insertVersion(Version version) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("提交失败");
		if (version != null) {
			version.setApkUrl("fileDir/apk/" + version.getApkUrl());
			version.setCreationDate(new Date());// 获取当前时间
			int intR = versionDAO.insertSelective(version);
			if (intR == 1) {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public Version findVersionById(int versionId) {
		return versionDAO.selectByPrimaryKey(versionId);
	}

	@Override
	public JsonReturn deleteVersion(int versionId, String apkUrl) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		if (versionId > 0) {
			// 查询apk文件信息
			Version version = versionDAO.selectByPrimaryKey(versionId);
			// 删除apk文件信息
			int intR = versionDAO.deleteByPrimaryKey(versionId);
			if (intR > 0) {
				String str = version.getApkUrl();
				String name = str.substring(str.lastIndexOf("/") + 1);// 截取apk文件名称
				FileUtil.del(apkUrl + name);
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

}
