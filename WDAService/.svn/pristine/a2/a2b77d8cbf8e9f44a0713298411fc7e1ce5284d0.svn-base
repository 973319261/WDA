package com.gx.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gx.po.Module;
import com.gx.po.Supplier;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.JsonReturn;

public interface IAppCommonService {
	/**
	 * 查询所有车型
	 */
	public JsonReturn selectCarTypeAll();
	/**
	 * 通过车型ID车型模块
	 * @return
	 */
	public JsonReturn selectModuleByCarTypeId(Integer carTypeId);
	
	/**
	 * 通过模块名称查询模块ID
	 * @return
	 */
	public int selectModuleByModuleName(String moduleName);
	
	/**
	 * 通过模块ID查询供应商
	 * @param moudleId
	 * @return
	 */
	public JsonReturn selectSupplierByModuleId(Integer moudleId);
	
	/**
	 * 查询DID内容下拉框（通过模块名称和供应商名称）
	 * @param moudleName
	 * @param supplierName
	 * @return
	 */
	public JsonReturn selectDidContent(String moudleName,String supplierName);
	
	
	
	
	
	/**
	 * 查询模块信息 by 模块名称、全称、canID
	 * @param mod
	 * @return
	 */
	public JsonReturn selectModuleInfo(Module mod);
	
	
	/**
	 * 查询供应商信息by 名称、编号
	 * @param sup
	 * @return
	 */
	public JsonReturn selectSupInfo(Supplier sup);
	
	/**
	 * EOL生产线查询
	 * @param input
	 * @return
	 */
	public JsonReturn selectEOL(String input);
	
	
	/**
	 * 根据 映射类型 查询 CAN值下拉框
	 * @return
	 */
	public JsonReturn selectCanByType(Integer mapTypeId);
	
}
