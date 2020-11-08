package com.gx.mapper;

import com.gx.po.ReArithmeticVehicle;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ReArithmeticVehicleDAO继承基类
 */
@Repository
public interface ReArithmeticVehicleDAO extends MyBatisBaseDao<ReArithmeticVehicle, Integer> {
	/**
	 * 查询与车型关联的数据(数据总条数)
	 * @param vehicleId
	 * @return
	 */
	public int selectConnectVehicleRows(@Param("vehicleId")Integer vehicleId);
	
	/**
	 * 查询车型算法信息是否存在
	 * @param vehicleId 车型ID
	 * @param algorithmType 算法类型
	 * @return
	 */
	public int selectArithmeticWhetherExist(@Param("vehicleId")Integer vehicleId,@Param("algorithmType")Integer algorithmType);
	
}