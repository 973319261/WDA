package com.gx.mapper;

import com.gx.po.ArithmeticLevel;
import com.gx.vo.AppendOptionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ArithmeticLevelDAO继承基类
 */
@Repository
public interface ArithmeticLevelDAO extends MyBatisBaseDao<ArithmeticLevel, Integer> {
	/**
	 * 查询算法与SeedToKey关联的算法等级
	 * @param vehicleId 车型ID
	 * @param configurationLevelId 配置等级ID
	 * @param moudleId 模块ID
	 * @param supplierId 供应商ID
	 * @return
	 */
	public List<AppendOptionVo> selectLevelRelateSeedToKey(@Param("vehicleId")Integer vehicleId,
			@Param("configurationLevelId")Integer configurationLevelId,@Param("moudleId")Integer moudleId,
			@Param("supplierId")Integer supplierId);
	
	/**
	 * 查询算法级别信息(绑定下拉框)
	 * @return
	 */
	public List<AppendOptionVo> selectArithmeticLevelInfo();
	
}