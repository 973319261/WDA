package com.gx.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.mapper.CommentDAO;
import com.gx.mapper.DidConversionDAO;
import com.gx.mapper.InformDAO;
import com.gx.mapper.InformDetailDAO;
import com.gx.mapper.ModuleDAO;
import com.gx.mapper.NoticeDAO;
import com.gx.mapper.NoticeInformDAO;
import com.gx.mapper.SupplierDAO;
import com.gx.mapper.UserDAO;
import com.gx.po.Inform;
import com.gx.po.Notice;
import com.gx.po.Supplier;
import com.gx.service.IAppShareService;
import com.gx.util.Tools;
import com.gx.util.WebSocketMapUtil;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.CommentsVo;
import com.gx.vo.InformVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.NoticeVo;
import com.gx.vo.Pagination;
import com.gx.vo.UserVo;
import com.gx.web.MyWebSocketServer;

@Service
@Transactional
public class AppShareServiceImpl implements IAppShareService {
	@Autowired
	ModuleDAO moduleDAO;//模块
	@Autowired
	SupplierDAO supplierDAO;//模块
	@Autowired
	DidConversionDAO didConversionDAO;//Did
	@Autowired
	NoticeDAO noticeDAO;//公告
	@Autowired
	InformDAO informDAO;//通知
	@Autowired
	CommentDAO commentDAO;//公告评论
	@Autowired
	private InformDetailDAO informDetailDAO;
	@Autowired
	private NoticeInformDAO noticeInformDAO;
	@Autowired
	UserDAO userDAO;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Override
	public Pagination selectListNoticeByName(String name,int pageSize,int currentPage) {
		JsonReturn jsonReturn=new JsonReturn();
		int startIndex = 0;
		startIndex = (currentPage-1)*pageSize;
		if(startIndex<0){
			startIndex=0;
		}
		if(!Tools.isNotNull(name)){
			name = "";
		}
		Integer count = noticeDAO.selectNoticeCount(name);
		List<NoticeVo> listNotice = noticeDAO.selectListNoticeByName(name,startIndex,pageSize);
		
		// 分页数据
		Pagination<NoticeVo> pagination = new Pagination<NoticeVo>();
		pagination.setCurrentPage(currentPage);// 当前页
		pagination.setPageSize(pageSize);// 每页大小
		pagination.setTotalRows(count);// 总条数
		pagination.setData(listNotice);// 本页数据
		pagination.setSuccess(true);// 成功
		return pagination;
	}

	@Override
	public JsonReturn selectNoticeById(Integer id,Integer userId) {
		JsonReturn jsonReturn=new JsonReturn();
		NoticeVo dbNotice = noticeDAO.selectNoticeById(id);
		if(dbNotice!=null){
			userDAO.updateShareVisit(userId);//修改访问量
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(dbNotice);
		}else{
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectComments(CommentsVo vo) {
		JsonReturn jsonReturn = new JsonReturn();
		List<CommentsVo> listCom = commentDAO.selectComments(vo);
		for(int i=0;i<listCom.size();i++){
			vo.setReplyId(listCom.get(i).getCommentId());
			List<CommentsVo> listChild = commentDAO.selectComments(vo);
			listCom.get(i).setReplyCount(listChild.size());
		}
		if(listCom.size()>0){
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(listCom);
		}else{
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}
	
	@Override
	public Pagination selectListComment(CommentsVo vo) {
		int startIndex = 0;
		startIndex = (vo.getCurrentPage()-1)*vo.getPageSize();
		if(startIndex<0){
			startIndex=0;
		}
		vo.setStartIndex(startIndex);

		Integer count = commentDAO.selectComments(vo).size();
		List<CommentsVo> listCom = commentDAO.selectComments(vo);
		
		// 分页数据
		Pagination<CommentsVo> pagination = new Pagination<CommentsVo>();
		pagination.setCurrentPage(vo.getCurrentPage());// 当前页
		pagination.setPageSize(vo.getPageSize());// 每页大小
		pagination.setTotalRows(count);// 总条数
		pagination.setData(listCom);// 本页数据
		pagination.setSuccess(true);// 成功
		return pagination;
	}

	
	@Override
	public JsonReturn addComment(CommentsVo comment) {
		JsonReturn jsonReturn = new JsonReturn();
		comment.setCreationTime(new Date());
		int count = commentDAO.addComment(comment);
		if(count>0){
			jsonReturn.setCode(200);
			jsonReturn.setText("新增成功");
		}else{
			jsonReturn.setCode(404);
			jsonReturn.setText("新增失败");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn delComment(int commentId) {
		JsonReturn jsonReturn = new JsonReturn();
		commentDAO.delCommentByReplyID(commentId);
		int count = commentDAO.deleteByPrimaryKey(commentId);
		if(count>0){
			jsonReturn.setCode(200);
			jsonReturn.setText("已删除");
		}else{
			jsonReturn.setCode(404);
			jsonReturn.setText("操作失败");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectdbCom(int commentId) {
		JsonReturn jsonReturn = new JsonReturn();
		CommentsVo dbCom = commentDAO.selectdbCom(commentId);
		if(dbCom!=null){
			jsonReturn.setCode(200);
			jsonReturn.setText("查询成功");
			jsonReturn.setData(dbCom);
		}else{
			jsonReturn.setCode(404);
			jsonReturn.setText("无数据");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectInformByUserId(int userId) {
		JsonReturn jsonReturn=new JsonReturn();
		List<InformVo> list= informDetailDAO.selectInformByUserId(userId);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}

	@Override
	public JsonReturn selectFileInformByUserId(int userId) {
		JsonReturn jsonReturn=new JsonReturn();
		List<InformVo> list= noticeInformDAO.selectNoticeInformByUserId(userId);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}

	@Override
	public JsonReturn updateInformState(int type, int userId, int id) {
		JsonReturn jsonReturn=new JsonReturn();
		boolean flag=false;
		if (type==0) {//通知
			flag=informDetailDAO.updateInformState(userId, id);
		}else {//公告
			flag=noticeInformDAO.updateInformState(userId, id);
		}
		if(flag) {
			jsonReturn.setCode(200);
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectInformById(Integer informId) {
		JsonReturn jsonReturn=new JsonReturn();
		Inform inform = informDAO.selectByPrimaryKey(informId);
		jsonReturn.setCode(200);
		jsonReturn.setData(inform);
		return jsonReturn;
	}
	@Override
	public int selectInformIsReadState(Integer userId) {
		// TODO Auto-generated method stub
		return informDetailDAO.selectIsReadState(userId);
	}

	@Override
	public int selectFileInformIsReadState(Integer userId) {
		// TODO Auto-generated method stub
		return noticeInformDAO.selectIsReadState(userId);
	}

	@Override
	public JsonReturn selectUserInfoAll(Boolean isPush) {
		JsonReturn jsonReturn=new JsonReturn();
		jsonReturn.setCode(200);
		List<UserVo> list=userDAO.selectUserInfoAll();//查询所有工程师
		
		for (UserVo user : list) {
			if(WebSocketMapUtil.get(user.getUserId())!=null) {
				user.setIsOnline(true);//在线
			}else {
				user.setIsOnline(false);//不在线
			}
		}
		Collections.sort(list);
		jsonReturn.setData(list);
		if(isPush) {
			//群发消息
	        for(MyWebSocketServer item: WebSocketMapUtil.getValues()){
	        	try {
					item.sendMessage(2001, "消息推送成功", gson.toJson(list));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }		
		}
    	return jsonReturn;
	}
	
	
	@Override
	public JsonReturn selectModuleAsDid() {
		// TODO Auto-generated method stub
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> list=moduleDAO.selectModuleAsDid();
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}

	@Override
	public JsonReturn selectSupplierByModuleId(Integer moduleId) {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> list=supplierDAO.selectSupplierByModuleId(moduleId);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}

	@Override
	public JsonReturn findDidByModSup(Integer moudleId, Integer supplierId) {
		JsonReturn jsonReturn = new JsonReturn();
		List<AppendOptionVo> list=didConversionDAO.findDidByModSup(moudleId,supplierId);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}
}
