package com.gx.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.mapper.UserDAO;
import com.gx.service.IAppShareService;
import com.gx.service.IFaultCodeService;
import com.gx.util.Tools;
import com.gx.util.TransformCoding;
import com.gx.util.WebSocketMapUtil;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.CommentsVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.UserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/app/share")
@Api(value = "APP分享模块接口", description = "APP分享模块相关api")
public class AppShareController {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;

	@Autowired
	private IAppShareService appShareService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	IFaultCodeService faultCodeService;

	
	/**
	 * 查询模块下拉框
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectModuleAsDid", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询模块下拉框",httpMethod = "POST", response = Gson.class)
	public Object selectModuleAsDid() {
		result = appShareService.selectModuleAsDid();
		return gson.toJson(result);
	}
	
	
	/**
	 * 通过模块ID查询供应商下拉框
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSupplierByModuleId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过模块ID查询供应商下拉框",httpMethod = "POST", response = Gson.class)
	public Object findSupplierInfoByModuleId(Integer moduleId) {
		result = appShareService.selectSupplierByModuleId(moduleId);
		return gson.toJson(result);
	}
	
	
	/**
	 * 通过模块 供应商查询DID
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDidByModSup", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过模块、供应商查询DID",httpMethod = "POST", response = Gson.class)
	public Object findDidByModSup(Integer moduleId, Integer supplierId) {
		result = appShareService.findDidByModSup(moduleId,supplierId);
		return gson.toJson(result);
	}
	
	 /**
	  * 转换DID
	  * @param listDidVlaue
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping(value = "/transformDID", produces = "application/json;charset=UTF-8")
	 @ApiOperation(value = "转换DID",httpMethod = "POST", response = Gson.class)
	 public Object transformDID(String listDidVlaue){
	  JsonReturn jsonReturn=new JsonReturn();
	  List<AppendOptionVo> listDID = new ArrayList<AppendOptionVo>();
	  listDID = Tools.jsonToList(listDidVlaue, AppendOptionVo.class);
	  for (int i=0;i<listDID.size();i++){
	            String type = listDID.get(i).getValue();
	            String val = listDID.get(i).getFullName();
	            if (type.contains("HEX"))
	            {
	             listDID.get(i).setValue(TransformCoding.strTo16(val));
	            }
	            else if (type.contains("ASCII"))
	            {
	             listDID.get(i).setValue(TransformCoding.stringToAscii(val));
	            }
	            else if (type.contains("BCD"))
	            {
	             listDID.get(i).setValue(val);
	            }else{
	             listDID.get(i).setValue("");
	            }
	        };
	  jsonReturn.setCode(200);
	  jsonReturn.setData(listDID);
	  return gson.toJson(jsonReturn);
	 }
	
	/**
	 * 查询公告列表 通过公告标题筛选
	 * @param name
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectListNoticeByName", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询公告列表 通过公告标题筛选",httpMethod = "POST", response = Gson.class)
	public Object selectListNoticeByName(String name,int pageSize,int currentPage){
		result = appShareService.selectListNoticeByName(name,pageSize,currentPage);
		return gson.toJson(result);
	}
	
	/**
	 * 通过公告ID查询公告信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectNoticeById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过公告ID查询公告信息",httpMethod = "POST", response = Gson.class)
	public Object selectNoticeById(Integer id,Integer userId){
		result = appShareService.selectNoticeById(id,userId);
		return gson.toJson(result);
	}
	
	/**
	 * 通过通知ID查询通知信息
	 * @param informId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectInformById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过通知ID查询通知信息",httpMethod = "POST", response = Gson.class)
	public Object selectInformById(Integer informId){
		result = appShareService.selectInformById(informId);
		return gson.toJson(result);
	}
	
	
	/**
	 * 查询评论数据 根据公告ID和上级评论ID
	 * @param noticeId
	 * @param replyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectComments", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询评论数据 根据公告ID和上级评论ID",httpMethod = "POST", response = Gson.class)
	public Object selectComments(String vo){
		CommentsVo vos = gson.fromJson(vo, CommentsVo.class);
		result = appShareService.selectComments(vos);
		return gson.toJson(result);
	}
	
	
	/**
	 * 查询评论列表
	 * @param vo
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectListComment", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询评论列表信息",httpMethod = "POST", response = Gson.class)
	public Object selectListComment(String vo){
		CommentsVo vos = gson.fromJson(vo, CommentsVo.class);
		result = appShareService.selectListComment(vos);
		return gson.toJson(result);
	}
	
	
	/**
	 * 通过ID查询评论信息
	 * @param commmentsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectdbCom", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过ID查询评论信息",httpMethod = "POST", response = Gson.class)
	public Object selectdbCom(int commmentsId){
		result = appShareService.selectdbCom(commmentsId);
		return gson.toJson(result);
	}
	
	/**
	 * 新增评论内容
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addComment", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过ID查询评论信息",httpMethod = "POST", response = Gson.class)
	public Object addComment(String comment){
		CommentsVo comments = gson.fromJson(comment, CommentsVo.class);
		comments.setCreationTime(new Date());
		result = appShareService.addComment(comments);
		return gson.toJson(result);
	}
	
	
	/**
	 * 删除评论 根据主键ID
	 * @param commentsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delComment", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除评论 根据主键ID",httpMethod = "DELETE", response = Gson.class)
	public Object delComment(int commentId){
		result = appShareService.delComment(commentId);
		return gson.toJson(result);
	}
	
	/**
	 * 通过用户ID查询公告通知
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectInformByUserId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过用户ID查询公告通知",httpMethod = "POST", response = Gson.class)
	public Object selectInformByUserId(int userId){
		result = appShareService.selectInformByUserId(userId);
		return gson.toJson(result);
	}
	
	/**
	 * 通过用户ID查询文件通知
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectFileInformByUserId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过用户ID查询文件通知",httpMethod = "POST", response = Gson.class)
	public Object selectFileInformByUserId(int userId){
		result = appShareService.selectFileInformByUserId(userId);
		return gson.toJson(result);
	}
	
	/**
	 * 修改公告通知为已读状态
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateInformState", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改公告通知为已读状态",httpMethod = "PATCH", response = Gson.class)
	public Object updateInformState(int type,int userId,int id){
		result = appShareService.updateInformState(type,userId,id);
		return gson.toJson(result);
	}
	
	/**
	 * 查询未读通知
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectInformIsReadState", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询未读通知",httpMethod = "POST", response = Gson.class)
	public Object selectInformIsReadState(int userId){
		 JsonReturn jsonReturn=new JsonReturn();
		 int informTotal = appShareService.selectInformIsReadState(userId);//公告通知条数
         int fileInformTotal = appShareService.selectFileInformIsReadState(userId);//文件通知条数
         if(informTotal > 0 || fileInformTotal > 0) {//存在未读消息
        	 jsonReturn.setCode(200);
         }else {
        	 jsonReturn.setCode(500);
         }
		return gson.toJson(jsonReturn);
	}
	
	/**
	 * 发送远程连接请求
	 * @param roomNo 房间号
	 * @param fromAccount 推送用户 
	 * @param toAccount 被推送用户
	 * @param stateCode 状态码
	 * @param screenWidth 屏幕宽度
	 * @param screenHeight 屏幕高度
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMstsc", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "发送远程连接请求",httpMethod = "POST", response = JsonReturn.class)
	public Object sendMstsc(String fromAccount,String toAccount,int stateCode, 
			@RequestParam(value = "roomNo",required = false)String roomNo,
			@RequestParam(value = "screenWidth",required = false)Integer screenWidth,
			@RequestParam(value = "screenHeight",required = false)Integer screenHeight){
		JsonReturn jsonReturn=new JsonReturn();
		UserVo fromUser=userDAO.selectUserInfoByAccount(fromAccount);//通过账号查询用户信息
		UserVo toUser=userDAO.selectUserInfoByAccount(toAccount);//通过账号查询用户信息
		if(toUser!=null) {
			if(!toUser.getUserId().equals(fromUser.getUserId())) {//
				MyWebSocketServer myWebSocket = WebSocketMapUtil.get(toUser.getUserId());//通过用户id查询socket连接用户
				if(myWebSocket!=null) {//对方在线
					try {
						jsonReturn.setCode(200);
						if(stateCode==1001) {//请求对方通话
							JSONObject jsonObject=new JSONObject();
							jsonObject.put("roomNo",roomNo);
							jsonObject.put("account", fromAccount);
							jsonObject.put("userName", fromUser.getUserName());
							jsonObject.put("screenWidth", screenWidth);
							jsonObject.put("screenHeight", screenHeight);
							if(WebSocketMapUtil.isCallUser(fromUser.getUserId())) {
								if(WebSocketMapUtil.isCallUser(toUser.getUserId())) {
									WebSocketMapUtil.addCallUser(fromUser.getUserId());
									WebSocketMapUtil.addCallUser(toUser.getUserId());
									WebSocketMapUtil.putCallMap(fromUser.getUserId(),toUser.getUserId());
									myWebSocket.sendMessage(1001, "消息推送成功",jsonObject);
								}else {
									jsonReturn.setCode(504);
									jsonReturn.setText("对方处于通话中");
								}
							}else {
								jsonReturn.setCode(503);
								jsonReturn.setText("你正处于通话中");
							}
						} else if(stateCode==1002) {//响应推流端（接受）
							myWebSocket.sendMessage(1002, "消息推送成功",true);
						} else if(stateCode==1003) {//响应推流端（拒绝）
							WebSocketMapUtil.removeCallUser(fromUser.getUserId());
							WebSocketMapUtil.removeCallUser(toUser.getUserId());
							WebSocketMapUtil.removeCallMap(toUser.getUserId());//移除toUserId
							WebSocketMapUtil.removeCallMap(fromUser.getUserId());
							myWebSocket.sendMessage(1003, "消息推送成功",false);
						} else if(stateCode==1004) {//响应推流端（挂断）
							WebSocketMapUtil.removeCallUser(fromUser.getUserId());
							WebSocketMapUtil.removeCallUser(toUser.getUserId());
							WebSocketMapUtil.removeCallMap(toUser.getUserId());//移除toUserId
							WebSocketMapUtil.removeCallMap(fromUser.getUserId());
							myWebSocket.sendMessage(1004, "消息推送成功",true);
						} else if(stateCode==1005) {//通话连接成功
							myWebSocket.sendMessage(1005, "消息推送成功",true);
						} 
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					jsonReturn.setCode(502);
					jsonReturn.setText("对方不在线");
				}
			}else {
				jsonReturn.setCode(501);
				jsonReturn.setText("不能和自己通话哦");
			}
			
		}else {
			jsonReturn.setCode(500);
			jsonReturn.setText("账号不存在");
		}
		
		return jsonReturn;
	}
	
	/**
	 * 远程操作
	 * @param toAccount
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 */
	@ResponseBody
	@RequestMapping(value = "/callControl", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "远程操作",httpMethod = "POST")
	public void callControl(String toAccount,Float x,Float y,Float x1,Float y1){
		UserVo toUser=userDAO.selectUserInfoByAccount(toAccount);//通过账号查询用户信息
		MyWebSocketServer myWebSocket = WebSocketMapUtil.get(toUser.getUserId());//通过用户id查询socket连接用户
		try {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("x", x);
			jsonObject.put("y", y);
			jsonObject.put("x1", x1);
			jsonObject.put("y1", y1);
			myWebSocket.sendMessage(1006, "消息推送成功",jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询工程师信息（在线、离线）
	 */
	@ResponseBody
	@RequestMapping(value = "/selectEngineerInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询工程师信息（在线、离线）",httpMethod = "POST", response = Gson.class)
	public Object selectEngineerInfo(){
		result=appShareService.selectUserInfoAll(false);//查询所有工程师
		return gson.toJson(result);
	}
}
