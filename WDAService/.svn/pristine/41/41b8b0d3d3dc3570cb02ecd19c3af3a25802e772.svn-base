package com.gx.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.gx.web.MyWebSocketServer;
/**
 * 消息推送工具类
 * @author LJ
 *
 */
public class WebSocketMapUtil {

    public static ConcurrentMap<Integer, MyWebSocketServer> webSocketMap = new ConcurrentHashMap<>();
	private static ConcurrentMap<Integer, Integer> callMap = new ConcurrentHashMap<>();//通话中的用户
	private static ConcurrentMap<Integer, Integer> callReversalMap = new ConcurrentHashMap<>();//通话中的用户
	private static List<Integer> callList=new ArrayList<Integer>();//通话中的用户
    public static void put(Integer key, MyWebSocketServer myWebSocketServer){
	  webSocketMap.put(key, myWebSocketServer);
    }
    /**
     * 添加用户
     * @param key
     * @return
     */
    public static MyWebSocketServer get(Integer key){
    	return webSocketMap.get(key);
    }
    /**
     * 移除用户
     * @param key
     */
    public static void remove(Integer key){
         webSocketMap.remove(key);
    }
    public static Collection<MyWebSocketServer> getValues(){
        return webSocketMap.values();
    }
    /**
     * 添加用户
     * @param userId
     */
    public static void addCallUser(Integer userId) {
    	callList.add(userId);
    }
    /**
     * 移除用户
     * @param userId
     */
    public static void removeCallUser(Integer userId) {
    	if(userId!=null) {
    		for (int i = 0; i < callList.size(); i++) {
    			if(callList.get(i).equals(userId)) {
    				callList.remove(i);
    			}
    		}
    	}
    }
    /**
     * 判断该用户是否存在通话中
     * @param userId
     * @return
     */
    public static boolean isCallUser(Integer userId) {
    	for (int i = 0; i < callList.size(); i++) {
			if(callList.get(i).equals(userId)) {
				return false;
			}
		}
    	return true;
    }
    /**
     * 添加两方通话用户ID
     * @param fromUserId
     * @param toUserId
     */
    public static void putCallMap(Integer fromUserId,Integer toUserId) {
    	callMap.put(fromUserId, toUserId);
    	callReversalMap.put(toUserId, fromUserId);
    }
    /**
     * 获取对方账号
     * @param fromUserId
     * @return
     */
    public static Integer getToUserId(Integer fromUserId) {
    	if(callMap.containsKey(fromUserId)) {
    		return callMap.get(fromUserId);
    	}else if(callReversalMap.containsKey(fromUserId)) {
    		return callReversalMap.get(fromUserId);
    	}
    	return null;
	}
    /**
     * 移除通话账号
     * @param fromUserId
     */
    public static void removeCallMap(Integer fromUserId) {
    	if(callMap.containsKey(fromUserId)) {
    		callMap.remove(fromUserId);
    	}
    	if(callReversalMap.containsKey(fromUserId)) {
    		callReversalMap.remove(fromUserId);
    	}
    }
}