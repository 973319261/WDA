package com.gx.mapper;

import com.gx.po.CanConfiguration;
import com.gx.vo.AppendOptionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * CanConfigurationDAO继承基类
 */
@Repository
public interface CanConfigurationDAO extends MyBatisBaseDao<CanConfiguration, Integer> {
	/**
	 * 查询映射CAN配置管理信息
	 * 
	 * @param canPassage
	 *            CAN通道
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<CanConfiguration> selectCanInfo(@Param("mapTypeId") Integer mapTypeId,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询映射CAN配置管理信息(数据总条数)
	 * 
	 * @param canPassage
	 *            CAN通道
	 * @return
	 */
	public int selectCanInfoRows(@Param("mapTypeId") Integer mapTypeId);

	/**
	 * 查询映射CAN配置管理信息是否已存在
	 * 
	 * @param canPassage
	 *            CAN通道
	 * @param canConfigurationId
	 *            主键ID
	 * @return
	 */
	public int selectCanInfoWhetherExist(@Param("canPassage") String canPassage,
			@Param("canConfigurationId") Integer canConfigurationId,@Param("mapTypeId") Integer mapTypeId);

	/**
	 * 查询CAN信息（绑定下拉框）
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectCanBoxInfo();
	
	
	
	/**
	 * 根据 映射类型 查询 CAN值下拉框
	 * @return
	 */
	public List<AppendOptionVo> selectCanByType(@Param("mapTypeId") Integer mapTypeId);
	
	/**
	 * 修改映射CAN配置管理的DID名称
	 * @param mapTypeId 模块名称
	 * @param didName DID名称
	 * @return
	 */
	public int updateDidIInfoOnCan(CanConfiguration canInfo);
}