package com.gx.mapper;

import com.gx.po.Comment;
import com.gx.vo.CommentsVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * CommentDAO继承基类
 */
@Repository
public interface CommentDAO extends MyBatisBaseDao<Comment, Integer> {
	/**
	 * 查询评论数据 根据公告ID和上级评论ID
	 * @param noticeId
	 * @param replyId
	 * @return
	 */
	public List<CommentsVo> selectComments(CommentsVo vo);

	/**
	 * 通过ID查询评论信息
	 * @param commmentsId
	 * @return
	 */
	public CommentsVo selectdbCom(int commentId);

	/**
	 * 新增评论内容
	 * @param comment
	 * @return
	 */
	public int addComment(CommentsVo comment);

	/**
	 * 根据评论ID删除下级所有评论
	 * @param commentId
	 * @return
	 */
	public int delCommentByReplyID(int commentId);

	/**
	 * 通过公告ID查询评论信息
	 * @param noticeId 公告ID
	 * @param userName 用户名
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<CommentsVo> selectCommentInfo(@Param("noticeId") Integer noticeId, @Param("userName") String userName,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 通过公告ID查询评论信息(数据总条数)
	 * @param noticeId 公告ID
	 * @param userName 用户名
	 * @return
	 */
	public int selectCommentInfoRows(@Param("noticeId") Integer noticeId, @Param("userName") String userName);

	/**
	 * 通过评论ID查询回复信息
	 * @param replyId 评论ID
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<CommentsVo> selectCommentReplyInfo(@Param("noticeId") Integer noticeId,@Param("replyId") Integer replyId,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	
	/**
	 * 通过评论ID查询回复信息(数据总条数)
	 * @param replyId 评论ID
	 * @return
	 */
	public int selectCommentReplyInfoRows(@Param("noticeId") Integer noticeId,@Param("replyId") Integer replyId);
	
	/**
	 * 通过评论ID查询下级评论信息
	 * @param replyId 评论ID
	 * @return
	 */
	public int selectLevelInfoById(@Param("replyId") Integer replyId);
}