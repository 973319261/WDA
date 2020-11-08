package com.gx.service;

import java.util.List;

import com.gx.po.Version;
import com.gx.vo.JsonReturn;

public interface IVersionService {
	/**
	 * 获取最新spk版本信息
	 * 
	 * @return
	 */
	public JsonReturn getVersionInfo();

	/**
	 * 获取所有版本信息
	 * 
	 * @return
	 */
	public JsonReturn getAllVersionInfo();

	/**
	 * 查询版本信息
	 * 
	 * @param versionNumber
	 *            版本号
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            排序类型（升序、降序）
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Version> selectVersionInfo(String versionNumber, String strName, String sortType, int startIndex,
			int pageSize);

	/**
	 * 查询版本信息（数据总条数）
	 * 
	 * @param versionNumber
	 *            版本号
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            排序类型（升序、降序）
	 * @return
	 */
	public int selectVersionInfoRows(String versionNumber, String strName, String sortType);

	/**
	 * 查询版本标题是否存在
	 * 
	 * @param versionTitle
	 *            版本标题
	 * @return
	 */
	public Version selectVersionByNum(String versionTitle);

	/**
	 * 新增App版本信息
	 * 
	 * @param version
	 *            版本实体类
	 * @return
	 */
	public JsonReturn insertVersion(Version version);

	/**
	 * 通过版本id查询App版本
	 * 
	 * @param versionId
	 *            版本id
	 * @return
	 */
	public Version findVersionById(int versionId);

	/**
	 * 删除App版本信息
	 * 
	 * @param versionId
	 *            版本id
	 * @param apkUrl
	 *            apk路径
	 * @return
	 */
	public JsonReturn deleteVersion(int versionId, String apkUrl);
}
