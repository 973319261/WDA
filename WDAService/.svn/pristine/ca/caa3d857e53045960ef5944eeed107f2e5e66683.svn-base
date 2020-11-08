package com.gx.mapper;

import com.gx.po.Vehicle;
import com.gx.vo.AppendOptionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * VehicleDAO继承基类
 */
@Repository
public interface VehicleDAO extends MyBatisBaseDao<Vehicle, Integer> {
	/**
	 * app查询所有车型列表
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectVehicleAll();

	/**
	 * 查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的车型
	 * 
	 * @param algorithmType
	 *            算法类型 1或者4
	 * @return
	 */
	public List<AppendOptionVo> selectVehicleRelateArithmetic(@Param("roleId")Integer roleId,@Param("algorithmType")Integer algorithmType,
			@Param("moduleName")String moduleName);

	/**
	 * 查询算法与VINToPin（2）或者VINToEsk（3）关联的车型
	 * 
	 * @param algorithmType
	 *            算法类型2或者3
	 * @return
	 */
	public List<AppendOptionVo> selectVehicleRelateArithmeticVehicle(Integer algorithmType);

	/**
	 * 查询车型信息是否已存在
	 * 
	 * @param vehicleName
	 *            车型名称
	 * @param vehicleId
	 *            车型id
	 * @return
	 */
	public Vehicle selectVehicleWhetherExist(@Param("vehicleName") String vehicleName,
			@Param("vehicleId") int vehicleId);

	/**
	 * 查询车型信息
	 * 
	 * @param vehicleName
	 *            车型名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Vehicle> selectVehicleInfo(@Param("vehicleName") String vehicleName,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询车型信息(数据总条数)
	 * 
	 * @param vehicleName
	 *            车型名称
	 * @return
	 */
	public int selectVehicleInfoRows(@Param("vehicleName") String vehicleName);
}