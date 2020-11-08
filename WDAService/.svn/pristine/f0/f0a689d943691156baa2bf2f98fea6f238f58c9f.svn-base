package com.gx.mapper;

import com.gx.po.Notice;
import com.gx.vo.NoticeVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * NoticeDAO继承基类
 */
@Repository
public interface NoticeDAO extends MyBatisBaseDao<Notice, Integer> {

	/**
	 * 查询公告信息
	 * @param noticeTheme 公告主题
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<NoticeVo> selectNoticeInfo(@Param("noticeTheme") String noticeTheme, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 根据公告标题模糊查询公告
	 * @param name
	 * @return
	 */
	public List<NoticeVo> selectListNoticeByName(@Param("name")String name,@Param("startIndex")int startIndex,@Param("limit")int limit);

	/**
	 * 查询公告(数据总条数)
	 * @param noticeTheme
	 * @return
	 */
	public int selectNoticeInfoRows(@Param("noticeTheme") String noticeTheme);

	/**
	 * 查询公告总数
	 * @param name
	 * @return
	 */
	public Integer selectNoticeCount(@Param("name")String name);
	
	/**
	 * 根据公告ID查询公告信息
	 * @param id
	 * @return
	 */
	public NoticeVo selectNoticeById(@Param("id")Integer id);
	
	/**
	 * 通过公告ID删除公告、附件和评论信息
	 * @param noticeId
	 * @return
	 */
	public Integer deleteNoticeInfoById(@Param("noticeId")Integer noticeId);
}