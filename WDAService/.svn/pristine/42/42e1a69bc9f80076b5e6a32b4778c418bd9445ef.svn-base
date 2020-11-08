package com.gx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gx.mapper.JurisdictionDetailDAO;
import com.gx.mapper.UserDAO;
import com.gx.po.JurisdictionDetail;
import com.gx.po.User;
import com.gx.service.IAppUserService;
import com.gx.util.AESEncryptHelper;
import com.gx.util.Tools;
import com.gx.vo.JsonReturn;
import com.gx.vo.UserVo;

@Service
@Transactional
public class AppUserServiceImpl implements IAppUserService {
	@Autowired
	UserDAO userDAO;
	@Autowired
	JurisdictionDetailDAO jurisdictionDetailDAO;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Override
	public JsonReturn login(String account, String password, String imei) {
		JsonReturn jsonReturn=new JsonReturn();
		List<JurisdictionDetail> jurisdictionList;//权限列表
		JsonObject jsonObject=new JsonObject();
		// TODO Auto-generated method stub
		if(Tools.isNotNull(account) && Tools.isNotNull(password)) {
			UserVo user = userDAO.selectUserInfoByAccount(account);
			if(user!=null) {
				if(user.getUserPassword().equals(AESEncryptHelper.encrypt(password))){//判断密码是否正确
					imei=AESEncryptHelper.encrypt(imei);//加密设备ID
					UserVo userImei = userDAO.selectUserInfoByImei(imei);//判断设备是否已绑定其他用户
				    if(imei.trim().equals(user.getAndroidId())) {//判断是否已绑定其他设备
				    	if(user.getExpirationDate().after(new Date())) {//判断是否已过期
							jurisdictionList = jurisdictionDetailDAO.selectJurisdictionByUserId(user.getUserId());//查询用户权限
							//将两个需要返回的对象放入一个JSON对象中
							jsonObject.add("user", gson.toJsonTree(user));
							jsonObject.add("jurisdictionList", gson.toJsonTree(jurisdictionList));
							jsonReturn.setCode(200);
							jsonReturn.setText("登录成功");
							jsonReturn.setData(jsonObject);
						}else {
							jsonReturn.setCode(504);
							jsonReturn.setText("该账号已过期");
						}
				    	
					}else{
						if(user.getAndroidId()==null || "".equals(user.getAndroidId())) {//第一次登陆
							if(userImei==null) {
								if(user.getExpirationDate().after(new Date())) {//判断是否已过期
									User User=new User();
									User.setUserId(user.getUserId());
									User.setAndroidId(imei.trim());
									userDAO.updateByPrimaryKeySelective(User);//保存设备标识码
									jurisdictionList = jurisdictionDetailDAO.selectJurisdictionByUserId(user.getUserId());//查询用户权限
									//将两个需要返回的对象放入一个JSON对象中
									jsonObject.add("user", gson.toJsonTree(user));
									jsonObject.add("jurisdictionList", gson.toJsonTree(jurisdictionList));
									jsonReturn.setCode(200);
									jsonReturn.setText("登录成功");
									jsonReturn.setData(jsonObject);
								}else {
									jsonReturn.setCode(504);
									jsonReturn.setText("该账号已过期");
								}
					    	}else {
					    		jsonReturn.setCode(505);
								jsonReturn.setText("该设备已绑定其他用户");
					    	}
							
						}else {
							jsonReturn.setCode(503);
							jsonReturn.setText("该用户已在其他设备绑定");
						}
					}
				}else{
					jsonReturn.setCode(502);
					jsonReturn.setText("密码输入错误");
				}
			}else {
				jsonReturn.setCode(501);
				jsonReturn.setText("该账户不存在");
			}
		}else {
			jsonReturn.setCode(500);	
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}
	@Override
	public JsonReturn updatePassword(Integer userId,String oldPassword, String newPassword) {
		JsonReturn jsonReturn=new JsonReturn();
		User User=userDAO.selectByPrimaryKey(userId);
		if (User!=null) {
			if (User.getUserPassword().equals(AESEncryptHelper.encrypt(oldPassword))) {//密码正确
				if(!User.getUserPassword().equals(AESEncryptHelper.encrypt(newPassword))){//新密码和旧密码不一致
					User.setUserPassword(AESEncryptHelper.encrypt(newPassword));//设置新密码
					int total = userDAO.updateByPrimaryKeySelective(User);//修改问题
					if (total>0) {
						jsonReturn.setCode(200);
						jsonReturn.setText("密码修改成功");
					}
				}else {
					jsonReturn.setCode(502);
					jsonReturn.setText("新密码和旧密码一致");
				}
			}else {
				jsonReturn.setCode(501);
				jsonReturn.setText("旧密码输入错误");
			}
		}else {
			jsonReturn.setCode(500);	
			jsonReturn.setText("参数异常");
		}
		return jsonReturn;
	}

}
