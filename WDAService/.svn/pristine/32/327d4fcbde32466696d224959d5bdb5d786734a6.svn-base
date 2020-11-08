package com.gx.mapper;

import com.gx.po.HardwareManagement;
import com.gx.vo.HardwareVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * HardwareManagementDAO继承基类
 */
@Repository
public interface HardwareManagementDAO extends MyBatisBaseDao<HardwareManagement, Integer> {
	/**
	 * 查询硬件管理信息
	 * 
	 * @param hardware
	 *            硬件ID、名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<HardwareVo> selectHardwareManageInfo(@Param("hardware") String hardware,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询硬件管理信息(数据总条数)
	 * 
	 * @param hardware
	 *            硬件ID、名称
	 * @return
	 */
	public int selectHardwareManageInfoRows(@Param("hardware") String hardware);

	/**
	 * 查询硬件管理信息是否存在
	 * @param typeId 用于区分ID、名称
	 * @param hardware 硬件ID、名称
	 * @param hardwareManagementId
	 * @return
	 */
	public int selectHardwareWhetherExist(@Param("typeId") Integer typeId, @Param("hardware") String hardware,
			@Param("hardwareManagementId") Integer hardwareManagementId);
}