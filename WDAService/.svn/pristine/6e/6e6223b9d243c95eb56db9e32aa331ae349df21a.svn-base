package com.gx.mapper;

import com.gx.po.Eol;
import com.gx.vo.AppendOptionVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * EolDAO继承基类
 */
@Repository
public interface EolDAO extends MyBatisBaseDao<Eol, Integer> {
	/**
	 * app--EOL生产线查询
	 * 
	 * @param input
	 * @return
	 */
	public List<AppendOptionVo> selectEOL(String input);

	/**
	 * 查询ECU产线EOL操作
	 * 
	 * @param inputValue
	 *            输入值
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Eol> selectEolOperationInfo(@Param("inputValue") String inputValue, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询ECU产线EOL操作(数据总条数)
	 * 
	 * @param inputValue
	 *            输入值
	 * @return
	 */
	public int selectEolOperationInfoRows(@Param("inputValue") String inputValue);
	
	/**
	 * 查询输入值是否重复
	 * @param inputValue
	 * @param eol
	 * @return
	 */
	public int selectEolNameUseRows(@Param("inputValue") String inputValue,@Param("eolId") Integer eolId);
}