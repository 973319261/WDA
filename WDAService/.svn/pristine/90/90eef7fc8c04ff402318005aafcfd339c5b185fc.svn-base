package com.gx.web;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.gx.service.IAppShareService;
import com.gx.util.WebSocketMapUtil;

import net.sf.json.JSONObject;
/**
 * 消息推送服务
 * @author LJ
 *
 */
@Component
@ServerEndpoint(value = "/websocket/{userId}",configurator = SpringConfigurator.class)
public class MyWebSocketServer {
     private Logger logger = Logger.getLogger(MyWebSocketServer.class);
     private Session session;
     
     @Autowired
     private IAppShareService iShareService;
     
     /**
      * 连接建立后触发的方法
      */
     @OnOpen
     public void onOpen(@PathParam("userId")Integer userId,Session session){
          this.session = session;
          WebSocketMapUtil.put(userId,this);
          iShareService.selectUserInfoAll(true);//刷新工程师在线列表
          logger.info("======用户"+userId+ "打开连接 ======");
     }

     /**
      * 连接关闭后触发的方法
      */
     @OnClose
     public void onClose(@PathParam("userId")Integer userId){
          //从map中删除
    	 if(session!=null) {
    		 WebSocketMapUtil.remove(userId);
    		 WebSocketMapUtil.removeCallUser(userId);//移除fromUserId
			 WebSocketMapUtil.removeCallUser(WebSocketMapUtil.getToUserId(userId));//移除toUserId
			 iShareService.selectUserInfoAll(true);//刷新工程师在线列表
			    //获取服务端到客户端的通道
			 if(WebSocketMapUtil.getToUserId(userId)!=null) {
				  MyWebSocketServer myWebSocket = WebSocketMapUtil.get(WebSocketMapUtil.getToUserId(userId));
			     	try {
			     		 logger.info("======用户"+userId+ "关闭连接 ======");
			     		if(myWebSocket!=null) {
			     			 WebSocketMapUtil.removeCallMap(userId);//移除toUserId
			 				myWebSocket.sendMessage(1004, "消息推送成功",true);//响应对方（挂断）
			     		}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 }
	       
    	 }
         
     }

     /**
      * 接收到客户端消息时触发的方法
      */
     @OnMessage
     public void onMessage(String params,@PathParam("userId")Integer userId,Session session) throws Exception{
          //获取服务端到客户端的通道
         MyWebSocketServer myWebSocket = WebSocketMapUtil.get(userId);
         if(WebSocketMapUtil.get(userId)==null) {
        	 WebSocketMapUtil.put(userId,this);
         }
         logger.info("用户ID为："+params);
         String result = "收到来自用户ID为:"+userId+"的消息";
         int informTotal = iShareService.selectInformIsReadState(userId);//公告通知条数
         int fileInformTotal = iShareService.selectFileInformIsReadState(userId);//文件通知条数
         if(informTotal > 0 || fileInformTotal > 0) {//存在未读消息
        	  myWebSocket.sendMessage(0,"成功！",result);//返回给客户端
         }
     }
     
     /**
      * 发生错误时触发的方法
      */
     @OnError
     public void onError(Session session,@PathParam("userId")Integer userId,Throwable error){
          logger.info("======用户"+userId+ "连接发生错误 ======"+error.getMessage());
          error.printStackTrace();	
     }

     public void sendMessage(int status,String message,Object datas) throws IOException{
		 JSONObject result = new JSONObject();
         result.put("status", status);
         result.put("message", message);
         result.put("data", datas);
         if(this.session.isOpen()) {
        	 this.session.getBasicRemote().sendText(result.toString());
         }
    }

}