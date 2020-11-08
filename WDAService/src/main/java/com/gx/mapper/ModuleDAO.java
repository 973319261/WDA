package com.gx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gx.po.Module;
import com.gx.vo.AppendOptionVo;

/**
 * ModuleDAO继承基类
 */
@Repository
public interface ModuleDAO extends MyBatisBaseDao<Module, Integer> {

	public List<AppendOptionVo> selectModuleInfo(Module mod);

	/**
	 * 通过车型ID和配置等级ID查询算法与SeedToKey（1）或者SeedPinToKey（4）关联的模块
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param algorithmType
	 *            算法类型（ 1或者4）
	 * @return
	 */
	public List<AppendOptionVo> selectModuleRelateArithmetic(@Param("vehicleId") Integer vehicleId,
			@Param("configurationLevelId") Integer configurationLevelId, @Param("roleId") Integer roleId,
			@Param("algorithmType") Integer algorithmType);

	/**
	 * 通过dtc和车型ID、配置ID查询模块
	 * 
	 * @param dtc
	 * @param vehicleId
	 * @return
	 */
	public List<AppendOptionVo> selectMoudleByDtc(@Param("dtc") String dtc, @Param("vehicleId") Integer vehicleId,
			@Param("configureId") Integer configureId);

	/**
	 * 通过hexdtc和车型ID、配置ID查询模块
	 * 
	 * @param dtcParam
	 * @param vehicleId
	 * @return
	 */
	public List<AppendOptionVo> selectMoudleByHexDtc(@Param("hexHtc") String hexHtc,
			@Param("vehicleId") Integer vehicleId, @Param("configureId") Integer configureId);

	/**
	 * 查询模块信息
	 * 
	 * @param moduleName
	 *            模块名称
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Module> selectModuleMessage(@Param("moduleName") String moduleName, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询模块信息(数据总条数)
	 * 
	 * @param moduleName
	 *            模块名称
	 * @return
	 */
	public int selectModuleMessageRows(@Param("moduleName") String moduleName);

	/**
	 * 查询模块信息是否已存在
	 * 
	 * @param moduleName
	 *            模块名称
	 * @param moduleId
	 *            模块id
	 * @return
	 */
	public Module selectModuleWhetherExist(@Param("moduleName") String moduleName, @Param("moduleId") int moduleId);

	/**
	 * 通过模块名称查询模块信息
	 * 
	 * @param moduleName
	 * @return
	 */
	public Module selectModuleByName(@Param("moduleName") String moduleName);

	/**
	 * 
	 * 查询与DID转换有关的模块 -
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectModuleAsDid();

	/**
	 * 查询与DID关联的模块信息
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Module> selectModuleDataList(@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);
	
	/**
	 * 查询与DID关联的模块信息(数据总条数)
	 * @return
	 */
	public int selectModuleDataListRows();

}