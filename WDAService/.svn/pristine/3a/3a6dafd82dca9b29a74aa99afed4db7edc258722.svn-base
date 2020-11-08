package com.gx.mapper;

import com.gx.po.File;
import com.gx.vo.FilesVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * FileDAO继承基类
 */
@Repository
public interface FileDAO extends MyBatisBaseDao<File, Integer> {
	/**
	 * 通过公告ID查询文件
	 * @param noticeId 公告ID
	 * @return
	 */
	public List<FilesVo> selectFileByNoticeId(@Param("noticeId") int noticeId);
}