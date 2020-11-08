package com.gx.mapper;

import com.gx.po.ResponseOrder;
import com.gx.vo.AppendOptionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ResponseOrderDAO继承基类
 */
@Repository
public interface ResponseOrderDAO extends MyBatisBaseDao<ResponseOrder, Integer> {
	/**
	 * 查询响应指令数据(绑定下拉框)
	 * 
	 * @return
	 */
	public List<AppendOptionVo> selectAllResponseOrder();

	/**
	 * 查询响应指令信息
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ResponseOrder> selectResponseOrderInfo(@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 查询响应指令信息(数据总条数)
	 * 
	 * @return
	 */
	public int selectResponseOrderInfoRows();

	/**
	 * 查询响应指令是否存在
	 * 
	 * @param responseInstructions
	 *            响应指令
	 * @param responseOrderId
	 *            响应指令ID
	 * @return
	 */
	public int selectResponseOrderWhetherExist(@Param("responseInstructions") String responseInstructions,
			@Param("responseOrderId") Integer responseOrderId);
	
	/**
	 * 批量删除响应指令信息
	 * @param responseOrdernfoIds
	 * @return
	 */
	public int deleteResponseOrderInfo(List<Integer> responseOrdernfoIds);
}