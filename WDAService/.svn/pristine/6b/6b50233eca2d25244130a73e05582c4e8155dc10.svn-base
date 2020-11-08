package com.gx.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gx.mapper.ArithmeticDAO;
import com.gx.mapper.ArithmeticLevelDAO;
import com.gx.mapper.ConfigurationLevelDAO;
import com.gx.mapper.ModuleDAO;
import com.gx.mapper.SupplierDAO;
import com.gx.mapper.UserDAO;
import com.gx.mapper.VehicleDAO;
import com.gx.service.IAppSecurityService;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.DllVo;
import com.gx.vo.JNativeVo;
import com.gx.vo.JsonReturn;
@Service
@Transactional
public class AppSecurityServiceImpl implements IAppSecurityService {
	@Autowired
	private UserDAO userDAO;//用户
	@Autowired
	private ArithmeticDAO arithmeticDAO;//算法
	@Autowired
	private VehicleDAO vehicleDAO;//车型
	@Autowired
	private ConfigurationLevelDAO configurationLevelDAO;//配置等级
	@Autowired
	private ModuleDAO moduleDAO;//模块
	@Autowired
	private SupplierDAO supplierDAO;//供应商
	@Autowired
	private ArithmeticLevelDAO arithmeticLevelDAO;//算法等级
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	@Override
	public JsonReturn calculateByDll(String inputValue, String pin, Integer vehicleId, Integer configurationLevelId,
			Integer moduleId,Integer supplierId,Integer arithmeticLevelId,
			Integer userId, Integer typeId, HttpServletRequest request) {
		JsonReturn jsonReturn=new JsonReturn();
		String result = "";//返回值
		JNativeVo jNativeVo=getDllPath(vehicleId, configurationLevelId, moduleId, supplierId, arithmeticLevelId, userId, typeId, request);
		if(jNativeVo!=null) {
			JNative jNative;
			try {
				//1、加载dll并且调用dll方法
				jNative = new JNative(jNativeVo.getPath(), jNativeVo.getMethodName());
				// 2.设置返回参数的类型 没有它不出值
				jNative.setRetVal(Type.INT);;//返回状态
				// 3.设置参数
				int i=0;
				if(typeId==4) {//Seed&Pin-->Key
					byte[] bseed = Hex.decodeHex(inputValue);//16进制字符串转换为byte数组
					byte[] del=Hex.decodeHex(jNativeVo.getParameter());//16进制字符串转换为byte数组
					byte[] bpin =  Hex.decodeHex(pin);//16进制字符串转换为byte数组
					MemoryBlock m1 = MemoryBlockFactory.createMemoryBlock(4);//创建内存的空间大小
					MemoryBlock m2 = MemoryBlockFactory.createMemoryBlock(4);//创建内存的空间大小
					MemoryBlock m3 = MemoryBlockFactory.createMemoryBlock(4);//创建内存的空间大小
					Pointer pointer1 = new Pointer(m1);//指向内存块的指针
					pointer1.setMemory(bseed);//设置bsees数组
					jNative.setParameter(i++, pointer1); //设置参数1
					jNative.setParameter(i++,4);//位数 设置参数2
					Pointer pointer2 = new Pointer(m2);//指向内存块的指针
					pointer2.setMemory(bpin);//设置bpin数组
					jNative.setParameter(i++, pointer2); //设置参数3
					Pointer pointer3 = new Pointer(m3);//指向内存块的指针
					pointer3.setMemory(del);//del数组
					jNative.setParameter(i++, pointer3); //设置参数4
					jNative.invoke();//执行方法
					bseed= pointer1.getMemory();//输出值
					result=Hex.encodeHexString(bseed,false);//转为16进制字符串  false字母为小写
				}else {//非Seed&Pin-->Key
					jNative.setParameter(i++, Type.STRING,inputValue);//输入参数
					MemoryBlock m = MemoryBlockFactory.createMemoryBlock(1024);//创建内存的空间大小
					Pointer pointer = new Pointer(m);//指向内存块的指针
					jNative.setParameter(i++, pointer); //输出参数
					jNative.invoke();//执行方法
					result = pointer.getAsString();//输出值
				}
				jNative.dispose(); // 释放资源
				updateVisitNum(vehicleId, configurationLevelId, moduleId, supplierId, arithmeticLevelId, userId, typeId);//修改访问量
				JsonObject jsonObject=new JsonObject();
				//将两个需要返回的对象放入一个JSON对象中
				jsonObject.add("result", gson.toJsonTree(result));//返回值
				jsonObject.add("securityRecordVo", gson.toJsonTree(jNativeVo.getSecurityRecordVo()));//算法计算记录
				jsonReturn.setData(jsonObject);
				jsonReturn.setCode(200);
			} catch (NativeException e) {
				jsonReturn.setCode(501);
				//e.printStackTrace();
			} catch (IllegalAccessException e) {
				jsonReturn.setCode(501);
				//e.printStackTrace();
			} catch (DecoderException e) {
				jsonReturn.setCode(501);
				//e.printStackTrace();
			}
		}else {
			jsonReturn.setCode(500);
			jsonReturn.setText("暂无算法");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn selectVehicleRelateArithmetic(Integer roleId,Integer algorithmType,String moduleName) {
		JsonReturn jsonReturn=new JsonReturn();
		List<AppendOptionVo> list=vehicleDAO.selectVehicleRelateArithmetic(roleId,algorithmType,moduleName);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}


	@Override
	public JsonReturn selectVehicleRelateArithmeticVehicle(Integer algorithmType) {
		JsonReturn jsonReturn=new JsonReturn();
		List<AppendOptionVo> list=vehicleDAO.selectVehicleRelateArithmeticVehicle(algorithmType);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}
	
	@Override
	public JsonReturn selectConfigurationRelateArithmetic(Integer vehicleId, Integer algorithmType) {
		JsonReturn jsonReturn=new JsonReturn();
		List<AppendOptionVo> list=configurationLevelDAO.selectConfigurationRelateArithmetic(vehicleId, algorithmType);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}


	@Override
	public JsonReturn selectModuleRelateArithmetic(Integer vehicleId, Integer configurationLevelId,Integer roleId,
			Integer algorithmType) {
		JsonReturn jsonReturn=new JsonReturn();
		List<AppendOptionVo> list=moduleDAO.selectModuleRelateArithmetic(vehicleId, configurationLevelId,roleId, algorithmType);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}


	@Override
	public JsonReturn selectSupplierRelateArithmetic(Integer vehicleId, Integer configurationLevelId,
			Integer moudleId, Integer algorithmType) {
		JsonReturn jsonReturn=new JsonReturn();
		List<AppendOptionVo> list=supplierDAO.selectSupplierRelateArithmetic(vehicleId, configurationLevelId, moudleId, algorithmType);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}


	@Override
	public JsonReturn selectLevelRelateSeedToKey(Integer vehicleId, Integer configurationLevelId,
			Integer moudleId, Integer supplierId) {
		JsonReturn jsonReturn=new JsonReturn();
		List<AppendOptionVo> list=arithmeticLevelDAO.selectLevelRelateSeedToKey(vehicleId, configurationLevelId, moudleId, supplierId);
		jsonReturn.setCode(200);
		jsonReturn.setData(list);
		return jsonReturn;
	}
	
	/**
	 * 获取DLL文件路径
	 * @param conditionId 条件ID（供应商ID或者车辆ID）
	 * @param levelId 等级ID
	 * @param typeId 算法类型ID（seed--key ...）
	 * @return
	 */
	private JNativeVo getDllPath(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId,Integer arithmeticLevelId,Integer userId,Integer typeId,HttpServletRequest request) {
		DllVo dllVo=null;
		String path="";
		JNativeVo jNativeVo=null;
		if (typeId==1) {//Seed-->Key
			dllVo = arithmeticDAO.selectSeedToKeyDll(vehicleId, configurationLevelId, moduleId, supplierId, arithmeticLevelId);
			if(dllVo!=null) {
				path=request.getSession().getServletContext().getRealPath("fileDir/dll/SeedToKey/Level"+
					dllVo.getAlgorithmLevelId()+"/"+dllVo.getArithmeticPrefix()+dllVo.getArithmeticName()); //获取服务器DLL文件存取路径	
			}
		} else if (typeId==2){//Vin-->Pin
			dllVo = arithmeticDAO.selectVinToPinDll(vehicleId);
			if(dllVo!=null) {
				path=request.getSession().getServletContext().getRealPath("fileDir/dll/VinToPin/"
						+dllVo.getArithmeticPrefix()+dllVo.getArithmeticName()); //获取服务器DLL文件存取路径	
			}
			
		} else if (typeId==3){//Vin-->Esk
			dllVo = arithmeticDAO.selectVinToEskDll(vehicleId);
			if(dllVo!=null) {
				path=request.getSession().getServletContext().getRealPath("fileDir/dll/VinToEsk/"+
						dllVo.getArithmeticPrefix()+dllVo.getArithmeticName()); //获取服务器DLL文件存取路径	
			}
		} else if (typeId==4){//S&P-->Key
			dllVo = arithmeticDAO.selectSeedAndPinToKeyDll(vehicleId, configurationLevelId, moduleId, supplierId);
			if(dllVo!=null) {
				path=request.getSession().getServletContext().getRealPath("fileDir/dll/Seed&PinToKey/"+
						dllVo.getArithmeticPrefix()+dllVo.getArithmeticName()); //获取服务器DLL文件存取路径	
			}
		}
		File file=new File(path);//dll文件
		if(dllVo!=null && file.exists()) {//在数据查出有算法
			jNativeVo = new JNativeVo();
			jNativeVo.setMethodName(dllVo.getEntryPoint());//dll方法名称
			jNativeVo.setParameter(dllVo.getArithmeticDelta());//dll方法变量
			jNativeVo.setPath(path);//dll文件路径
			jNativeVo.setSecurityRecordVo(dllVo.getSecurityRecordVo());//算法计算记录
		}
		return jNativeVo;
	}
	
	/**
	 * 修改算法访问量
	 * @param conditionId  条件ID（供应商ID或者车辆ID）
	 * @param levelId 等级ID
	 * @param userId 用户ID
	 * @param typeId 算法类型ID（seed--key ...）
	 * @return
	 */
	private boolean updateVisitNum(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId,Integer arithmeticLevelId,Integer userId,Integer typeId) {
		boolean falg = false;//状态
		falg = userDAO.updateArithmeticVisit(userId);//修改该用户安全算法的访问量
		if (typeId==1) {//Seed-->Key
			falg = arithmeticDAO.updateSeedToKeyVisitNum(vehicleId, configurationLevelId, moduleId, supplierId, arithmeticLevelId);//修改该算法的访问量
		} else if (typeId==2){//Vin-->Pin
			falg = arithmeticDAO.updateVinToPinVisitNum(vehicleId);//修改该算法的访问量
		} else if (typeId==3){//Vin-->Esk
			falg = arithmeticDAO.updateVinToEskVisitNum(vehicleId);//修改该算法的访问量
		} else if (typeId==4){//S&P-->Key
			falg = arithmeticDAO.updateSeedAndPinToKeyVisitNum(vehicleId, configurationLevelId, moduleId, supplierId);//修改该算法的访问量
		}
		return falg;
	}

	

	

	
}
