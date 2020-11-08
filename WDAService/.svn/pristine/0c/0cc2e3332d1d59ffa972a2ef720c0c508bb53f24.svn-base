package com.gx.mapper;

import com.gx.po.Version;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * VersionDAO继承基类
 */
@Repository
public interface VersionDAO extends MyBatisBaseDao<Version, Integer> {
	/**
	 * 获取最新apk版本信息
	 * 
	 * @return
	 */
	public Version getVersionInfo();

	/**
	 * 获取所有版本信息
	 * 
	 * @return
	 */
	public List<Version> getAllVersionInfo();

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
	public List<Version> selectVersionInfo(@Param("versionNumber") String versionNumber,
			@Param("strName") String strName, @Param("sortType") String sortType, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

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
	public int selectVersionInfoRows(@Param("versionNumber") String versionNumber, @Param("strName") String strName,
			@Param("sortType") String sortType);

	/**
	 * 查询版本标题是否存在
	 * 
	 * @param versionTitle
	 *            版本标题
	 * @return
	 */
	public Version selectVersionByNum(@Param("versionTitle") String versionTitle);

}