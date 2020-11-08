package com.gx.wda.ui.dataStream;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.bean.MapParamVo;
import com.gx.wda.bean.SecurityRecordVo;
import com.gx.wda.bean.UserVo;
import com.gx.wda.dialog.BottomOptionDialog;
import com.gx.wda.dialog.TopOptionDialog;
import com.gx.wda.ui.MainActivity;
import com.gx.wda.ui.main.InformActivity;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.ImageUtil;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.SecurityRecordUtil;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.leefeng.promptlibrary.PromptDialog;

public class MapConfigActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局

    private MyActionBar myActionBar;//标题栏
    private BottomOptionDialog selectAisleDialog;
    private BottomOptionDialog selectWayDialog;
    private BottomOptionDialog selectCarTypeDialog;
    private BottomOptionDialog selectSupplierDialog;
    private LinearLayout llMapAisle;
    private TextView tvMapAisle;
    private ImageView ivMapAisle;
    private LinearLayout llCarType;
    private TextView tvCarType;
    private LinearLayout llSupplier;
    private TextView tvSupplier;
    private Button btnSave;
    private PromptDialog promptDialog;//加载框
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private int vehicleId = 0;//车型ID
    private int moduleId = 0;//模块ID
    private int supplierId = 0;//供应商ID
    private int mapAisleId = 0;//映射通道ID
    private int mapWayId = 0;//映射方式ID
    private Integer userId;//用户ID
    private String vehicleName;
    private String supplierName;
    private String mapAisle;
    private String mapAisleValue;
    private String mapWay;
    private UserVo user;
    private LinearLayout llMapWay;
    private TextView tvMapWay;
    private MapParamVo mapParam;
    ExecutorService exec = Executors.newCachedThreadPool();
    //VCI连接子线程
    private Handler handlerVCI;
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver;
    //注册手机热点监听广播
    private TextView tvMapAisleHint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;//设置上下文
        myApplication= (MyApplication) myActivity.getApplication();
        setContentView(R.layout.activity_message_map);
        promptDialog=new PromptDialog(myActivity);//加载框实例化
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取本地用户ID

        //获取控件
        //映射通道
        llMapAisle = findViewById(R.id.ll_map_aisle);
        tvMapAisle = findViewById(R.id.tv_map_aisle);
        tvMapAisleHint = findViewById(R.id.tv_select_aisle_hint);

        //映射方式wById(R.id.tv_map_aisle)
        llMapWay = findViewById(R.id.ll_map_way);
        tvMapWay = findViewById(R.id.tv_map_way);

        //车型
        llCarType = findViewById(R.id.ll_map_select_cartype);
        tvCarType = findViewById(R.id.tv_map_select_cartype);
        //供应商
        llSupplier = findViewById(R.id.ll_map_select_supplier);
        tvSupplier = findViewById(R.id.tv_map_select_supplier);
        //保存
        btnSave=findViewById(R.id.btn_save);


        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_map_map), R.drawable.ic_back,0,
                1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
        setViewListener();//设置监听事件
    }

    private void setViewListener() {
        //选择车型 所有车型
        llCarType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取车型信息
                String url= ServiceUrls.getSecurityMethodUrl("selectVehicleRelateArithmetic");
                Map<String,Object> map=new HashMap<>();
                map.put("roleId",user.getRoleId());//职务类型
                map.put("algorithmType",1);//算法类型 1
                map.put("moduleName","GW");//GW模块
                promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
                OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode==200){
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        int code=jsonObject.getInt("code");
                                        String data=jsonObject.getString("data");
                                        if (code==200){
                                            Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                            List<AppendOptionVo> vehicleList = gson.fromJson(data,type);
                                            selectCarTypeDialog.setData(vehicleList, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    tvCarType.setText(appendOptionVo.getName());//设置车辆文本
                                                    tvSupplier.setText(getString(R.string.text_unchosen));//重置供应商选项
                                                    vehicleId=appendOptionVo.getId();//车型ID
                                                    vehicleName=appendOptionVo.getName();
                                                    moduleId=Integer.parseInt(appendOptionVo.getValue());
                                                    selectSuppliceDialog();
                                                    popupWindow.dismiss();//关闭弹窗
                                                    resetData(false,false,false,true);//重置数据
                                                }
                                            });
                                            selectCarTypeDialog.show();//显示
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                promptDialog.dismissImmediately();//关闭
                            }
                        });
                    }
                });
            }
        });

        //选择供应商，GW模块下的
        llSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSupplierDialog.show();//显示
            }
        });

        //选择映射通道
        llMapAisle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAisleDialog.show();
            }
        });

        //选择映射方式
        llMapWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AppendOptionVo> list=new ArrayList<>();

                list.add(new AppendOptionVo(1,getString(R.string.text_map_temporary),"",""));
                list.add(new AppendOptionVo(2,getString(R.string.text_map_perpetual),"",""));
                list.add(new AppendOptionVo(3,getString(R.string.text_map_temporary_cancel),"",""));
                list.add(new AppendOptionVo(4,getString(R.string.text_map_perpetual_cancel),"",""));

                /*if (mapParam.getMapWayId()==1){//取消临时映射
                    list.add(new AppendOptionVo(3,getString(R.string.text_map_temporary_cancel),"",""));
                }else if (mapParam.getMapWayId()==2){//取消永久映射
                    list.add(new AppendOptionVo(4,getString(R.string.text_map_perpetual_cancel),"",""));
                }else {
                    list.add(new AppendOptionVo(1,getString(R.string.text_map_temporary),"",""));
                    list.add(new AppendOptionVo(2,getString(R.string.text_map_perpetual),"",""));
                }*/
                selectWayDialog.setData(list, new BottomOptionDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                        popupWindow.dismiss();
                        tvMapWay.setText(appendOptionVo.getName());
                        mapWayId = appendOptionVo.getId();
                        mapWay=appendOptionVo.getName();
                        selectCanByType();
                        resetData(false,true,true,true);//重置数据

                        if(mapWayId==3||mapWayId==4){
                            llMapAisle.setEnabled(false);
                            tvMapAisleHint.setTextColor(getResources().getColor(R.color.colorGray));
                        }else {
                            llMapAisle.setEnabled(true);
                            tvMapAisleHint.setTextColor(getResources().getColor(R.color.colorBlack));
                        }

                        /*if (mapWayId ==1 || mapWayId==2){
                            resetData(false,true,true,true);//重置数据
                        }else {
                            llMapAisle.setEnabled(false);
                            llCarType.setEnabled(false);
                            llSupplier.setEnabled(false);
                            btnSave.setEnabled(true);
                        }*/


                    }
                });
                //显示选择列表
                selectWayDialog.show();
            }
        });
        //保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    if(myApplication.isLinkState()){
                        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
                        //开启临时/永久映射//取消永久
                    /*    if(mapWayId==2||mapWayId==4){
                            //如果是永久映射或者取消永久，要先发这一条
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String FIRST_MSG = "AA01100000000800100700000210010000000000".replace(" ","");
                                    String FIRST_XOR = EncodingUtil.getBCC(EncodingUtil.hexToByte(FIRST_MSG));
                                    FIRST_MSG += FIRST_XOR;
                                    String sendMsg = FIRST_MSG;
                                    MainActivity.okSocketSend(sendMsg);
                                }
                            }).start();
                        }*/


                        /*if (exec.isShutdown()){
                            exec= Executors.newSingleThreadExecutor();
                            //Toast.makeText(myActivity, "exec被关闭",Toast.LENGTH_SHORT).show();
                        }*/

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                //开始都是发送这一条报文
                                String FIRST_MSG = "AA01100000000800100700000210030000000000".replace(" ","");
                                String FIRST_XOR = EncodingUtil.getBCC(EncodingUtil.hexToByte(FIRST_MSG));
                                FIRST_MSG += FIRST_XOR;
                                String sendMsg = FIRST_MSG;
                                MainActivity.okSocketSend(sendMsg);


                            }
                        }).start();

                        send3EService();

                    }else{
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    /**
     * 发送3E服务
     */
    private void send3EService(){
        Timer timer = myApplication.getServiceTimer();
        if(timer==null){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // circulationHandler.postDelayed(this,3000);//设置循环时间，此处是1秒
                    //需要执行的代码
                    //如果是临时映射
                    String circulation = "AA0110000000080010070000023E800000000000".replace(" ","");
                    String circulationXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(circulation));
                    circulation += circulationXor;
                    String sendMsg = circulation;

                    MainActivity.okSocketSend(sendMsg);
                }
            },100,3000);
            myApplication.setServiceTimer(timer);
        }
    }

    /**
     * 关闭3E服务
     */
    private void stop3EService(){

        if(myApplication.getTempMap()<=0){
            Timer timer = myApplication.getServiceTimer();
            if(timer!=null){
                timer.cancel();
                timer.purge();
            }
            myApplication.setServiceTimer(null);
        }
    }


    private void initView() {
        if(myBroadcastReceiver==null){
            myBroadcastReceiver = new MyBroadcastReceiver();
        }
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        IntentFilter intentFilter = new IntentFilter("tcpMapReceiver");
        myActivity.getApplicationContext().registerReceiver(myBroadcastReceiver,intentFilter);
        String userStr= (String) SPUtils.get(myActivity,SPUtils.SP_USER,"");
        Type type=new TypeToken<UserVo>(){}.getType();
        user=gson.fromJson(userStr,type);
        selectWayDialog = new BottomOptionDialog(myActivity,getString(R.string.text_select_map_way));
        selectAisleDialog = new BottomOptionDialog(myActivity,getString(R.string.text_select_map_aisle));
        selectCarTypeDialog = new BottomOptionDialog(myActivity,getString(R.string.text_map_select_cartype));
        selectSupplierDialog = new BottomOptionDialog(myActivity,getString(R.string.text_map_select_supplier));



        /*if (myApplication.getMapParamVo()==null){
            mapParam=new MapParamVo();
        }else {
            mapParam=myApplication.getMapParamVo();
            tvMapWay.setText(mapParam.getMapWayName());
            tvMapAisle.setText(mapParam.getMapAisleName());
            tvCarType.setText(mapParam.getCarTypeName());
            tvSupplier.setText(mapParam.getSupplierName());
        }*/
    }

    /**
     * 映射循环发送3E服务线程
     */




  /*  private Handler circulationHandler = new Handler();

    private Runnable circulationTask = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub

            circulationHandler.postDelayed(this,3000);//设置循环时间，此处是1秒
            //需要执行的代码
            //如果是临时映射
            String circulation = "AA0110000000080010070000023E800000000000".replace(" ","");
            String circulationXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(circulation));
            circulation += circulationXor;
            String sendMsg = circulation;

            MainActivity.okSocketSend(sendMsg);


          *//*  try {
                exec.wait(3000);
                exec.execute(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*//*
        }
    };*/

private boolean isFlag=false;

    /**
     * 监听报文
     */
    private class MyHandler extends Handler {


        private WeakReference<MapConfigActivity> mActivitys;
        MyHandler(MapConfigActivity activity){
            mActivitys = new WeakReference<MapConfigActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            String msgs = msg.obj.toString().toUpperCase();
            String data = msgs;
            Log.e("接收映射报文",data);
            if(data.contains("080018070000065003003201F4")){
                if(!isFlag) {
                        isFlag = true;
                        Log.e("映射方式ID", String.valueOf(mapWayId));
                        //发送第二条指令
                        if (mapWayId == 1) {
                            //临时映射
                            //exec.execute(circulationTask);
                            //send3EService();
                            // circulationHandler.postDelayed(circulationTask,500);//延迟调用3E服务
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        String tempLast = ("AA0110000000080010070000053101F005" + mapAisleValue + "0000").replace(" ", "");
                                        String tempLastXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(tempLast));
                                        tempLast += tempLastXor;
                                        String sendMsg = tempLast;
                                        MainActivity.okSocketSend(sendMsg);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else if (mapWayId == 3) {
                            //取消临时映射
                            //exec.execute(circulationTask);
                            //send3EService();
                            // circulationHandler.postDelayed(circulationTask,500);//延迟调用3E服务
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        //发送最后一条报文
                                        String tempLast = ("AA0110000000080010070000053101F005000000").replace(" ", "");
                                        String tempLastXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(tempLast));
                                        tempLast += tempLastXor;
                                        String sendMsg = tempLast;
                                        MainActivity.okSocketSend(sendMsg);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else if (mapWayId == 2 || mapWayId == 4) {
                            //如果是永久映射 或者 取消永久映射
                            //exec.execute(circulationTask);
                            //send3EService();
                            //  circulationHandler.postDelayed(circulationTask,500);//延迟调用3E服务
                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }).start();*/

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                        String foLast = ("AA01100000000800100700000227010000000000").replace(" ", "");
                                        String foLastXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(foLast));
                                        foLast += foLastXor;
                                        String sendMsg = foLast;
                                        MainActivity.okSocketSend(sendMsg);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                        }
                    }
            }else if(data.contains("080018070000057101F005")||data.contains("080018070000057101F004")){
                //临时映射/取消临时映射 成功
                //开启临时映射，不关闭3E服务
                //取消临时映射，关闭3E服务
                isFlag=false;

                if(mapWayId==3){
                    myApplication.setTempMap(myApplication.getTempMap()-1);
                    //circulationHandler.removeCallbacks(circulationTask);
                    stop3EService();
                }else if(mapWayId==1){
                    myApplication.setTempMap(myApplication.getTempMap()+1);
                }

                //提示
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        promptDialog.dismiss();
                    }
                });
                Toast.makeText(myActivity, R.string.text_map_successful,Toast.LENGTH_LONG).show();
                //关闭页面
                finish();
            }
            else if(data.contains("036EC110")){
                    //映射配置成功
                    isFlag=false;
                    //查询永久映射状态
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String softRecord = "AA 01 10 00 00 00 08 00 10 07 00 00 03 22 C1 10 00 00 00 00".replace(" ","");
                            String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                            softRecord += softXor;
                            String sendMsg = softRecord;
                            MainActivity.okSocketSend(sendMsg);
                        }
                    }).start();

                    //如果永久映射或者关闭永久映射，关闭3E服务
                    if(mapWayId==2||mapWayId==4){
                        //circulationHandler.removeCallbacks(circulationTask);
                        stop3EService();
                    }
                    //提示
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            promptDialog.dismiss();
                        }
                    });
                    //exec.shutdownNow();
                    Toast.makeText(myActivity, R.string.text_map_successful,Toast.LENGTH_LONG).show();
                    //关闭页面
                    finish();
                }else if(data.contains("080018070000066701")||data.contains("670100000000")){
                    //将ECU回复的67 01后面的4位数，调用Seed转Key的算法，得到值
                    String seedValue = data.substring(30+8,38+8);
                    //如果参数为00 00 00 00，则直接执行最后一步
                    if(seedValue.contains("00000000")){
                        if(mapWayId==2){
                            //如果是开启永久映射
                            //发送CAN值
                            //发送最后一条报文


                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mapAisleValue = EncodingUtil.addZeroForNum(mapAisleValue,8,false);
                                    String tempLast = ("AA0110000000080010070000042EC110"+mapAisleValue).replace(" ","");
                                    String tempLastXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(tempLast));
                                    tempLast += tempLastXor;
                                    String sendMsg = tempLast;
                                    MainActivity.okSocketSend(sendMsg);
                                }
                            }).start();

                        }else if(mapWayId==4){
                            //如果是取消永久映射
                            //发送最后一条报文
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String tempLast = ("AA0110000000080010070000042EC11000000000").replace(" ","");
                                    String tempLastXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(tempLast));
                                    tempLast += tempLastXor;
                                    String sendMsg = tempLast;
                                    MainActivity.okSocketSend(sendMsg);
                                }
                            }).start();

                        }
                    }else{
                        if (Tools.isHexadecimal(seedValue)){
                            String url=ServiceUrls.getSecurityMethodUrl("calculateByDll");
                            Map<String,Object> map=new HashMap<>();
                            map.put("inputValue",seedValue);//输入值
                            map.put("vehicleId",vehicleId);//车型ID
                            map.put("configurationLevelId",0);//配置等级ID
                            map.put("moduleId",moduleId);//模块ID
                            map.put("supplierId",supplierId);//供应商ID
                            map.put("arithmeticLevelId",1);//等级ID
                            map.put("userId",userId);//用户ID
                            map.put("typeId",1);//类型
                            //promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
                            OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                                @Override
                                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                                    myActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (isSuccess && responseCode==200){
                                                try {
                                                    JSONObject jsonObject=new JSONObject(response);
                                                    int code=jsonObject.getInt("code");
                                                    if (code==200){
                                                        String data=jsonObject.getString("data");
                                                        JSONObject jsonData=new JSONObject(data);//返回信息
                                                        String result=jsonData.getString("result");//返回计算值
                                                        result = EncodingUtil.addZeroForNum(result,8,false);

                                                        String sendSeed ="AA0110000000080010070000062702"+result+"00";
                                                        String sendSeedXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(sendSeed));
                                                        String sendMsg = sendSeed + sendSeedXor;
                                                        MainActivity.okSocketSend(sendMsg);

                                                    }else if (code==500){//暂无算法
                                                        stop3EService();
                                                        promptDialog.dismiss();
                                                        Toast.makeText(myActivity, R.string.text_no_algorithm,Toast.LENGTH_SHORT).show();
                                                    }else if (code==501){//计算失败
                                                        stop3EService();
                                                        promptDialog.dismiss();
                                                        Toast.makeText(myActivity, R.string.text_calculate_failure,Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        }else {
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    promptDialog.dismiss();
                                }
                            });
                        }
                    }
                }else if(data.contains("026702")){
                    if(mapWayId==2){
                        //永久映射
                        //发送CAN值
                        //发送最后一条报文

                        mapAisleValue = EncodingUtil.addZeroForNum(mapAisleValue,8,false);
                        String tempLast = ("AA0110000000080010070000042EC110"+mapAisleValue).replace(" ","");
                        String tempLastXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(tempLast));
                        tempLast += tempLastXor;
                        String sendMsg = tempLast;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.okSocketSend(sendMsg);
                            }
                        }).start();

                    }else if(mapWayId==4){
                        //取消永久映射
                        //发送最后一条报文
                        String tempLast = ("AA0110000000080010070000042EC11000000000").replace(" ","");
                        String tempLastXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(tempLast));
                        tempLast += tempLastXor;
                        String sendMsg = tempLast;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.okSocketSend(sendMsg);
                            }
                        }).start();


                    }
                }else if(data.contains("080018070000037F")){
                    //判断是否为 7F + 服务 + 78（等待，不处理）/其他（错误，提示错误）
                    //34,36异常帧
                    //36,38 服务帧
                    String abnormalFrame = data.substring(26+8,28+8);
                    //38,40 判断 若不为【78】,则提示错误
                    String errorFrame = data.substring(30+8,32+8);
                    if(abnormalFrame.contains("7F")){
                        if(errorFrame.contains("78")){
                            //ECU忙碌，等待，APP不作处理
                        }else if(errorFrame.contains("22")){
                            //算成功
                            //映射配置成功
                            isFlag=false;
                            //查询永久映射状态
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String softRecord = "AA 01 10 00 00 00 08 00 10 07 00 00 03 22 C1 10 00 00 00 00".replace(" ","");
                                    String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                                    softRecord += softXor;
                                    String sendMsg = softRecord;
                                    MainActivity.okSocketSend(sendMsg);
                                }
                            }).start();

                            //如果永久映射或者关闭永久映射，关闭3E服务
                            if(mapWayId==2||mapWayId==4){
                                //circulationHandler.removeCallbacks(circulationTask);
                                stop3EService();
                            }
                            //提示
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    promptDialog.dismiss();
                                }
                            });
                            //exec.shutdownNow();
                            Toast.makeText(myActivity, R.string.text_map_successful,Toast.LENGTH_LONG).show();
                            //关闭页面
                            finish();
                        }
                        else{
                            //提示出错
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    promptDialog.dismiss();
                                }
                            });
                            //exec.shutdownNow();
                            //circulationHandler.removeCallbacks(circulationTask);
                            stop3EService();
                            isFlag=false;
                            Toast.makeText(myActivity,R.string.text_map_fail,Toast.LENGTH_SHORT).show();
                            //查询永久映射状态
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String softRecord = "AA 01 10 00 00 00 08 00 10 07 00 00 03 22 C1 10 00 00 00 00".replace(" ","");
                                    String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                                    softRecord += softXor;
                                    String sendMsg = softRecord;
                                    MainActivity.okSocketSend(sendMsg);
                                }
                            }).start();
                        }
                    }
                }
        }
    }

    /**
     * 接收VCI传输的报文信息
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case "tcpMapReceiver":
                    String msg = intent.getStringExtra("tcpMapReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }




    /**
     * 绑定映射通道下拉框
     */
    private void selectCanByType(){
        String url= ServiceUrls.getCommonMethodUrl("selectCanByType");
        Map<String,Object> map=new HashMap<>();
        map.put("mapTypeId",mapWayId);//
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    List<AppendOptionVo> list = gson.fromJson(data,type);
                                    selectAisleDialog.setData(list, new BottomOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            tvMapAisle.setText(appendOptionVo.getName());
                                            mapAisleValue=appendOptionVo.getValue().toLowerCase().replace("0x","");
                                            mapAisleId=appendOptionVo.getId();//设置供应商ID
                                            mapAisle=appendOptionVo.getName();
                                            popupWindow.dismiss();
                                            resetData(false,false,true,true);//重置数据
                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismiss();
                    }
                });
            }
        });
    }
    /**
     * 绑定供应商下拉框
     */
    private void selectSuppliceDialog(){
        String url= ServiceUrls.getSecurityMethodUrl("selectSupplierRelateArithmetic");
        Map<String,Object> map=new HashMap<>();
        map.put("vehicleId",vehicleId);//车型ID
        map.put("configurationLevelId",0);//配置等级ID
        map.put("moduleId",moduleId);//模块ID
        map.put("algorithmType",1);//算法类型 1
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    List<AppendOptionVo> list = gson.fromJson(data,type);
                                    selectSupplierDialog.setData(list, new BottomOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            tvSupplier.setText(appendOptionVo.getName());
                                            supplierId=appendOptionVo.getId();//设置供应商ID
                                            supplierName=appendOptionVo.getName();
                                            popupWindow.dismiss();
                                            resetData(false,false,false,false);//重置数据
                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismiss();
                    }
                });
            }
        });
    }


    /**
     * 重置数据
     */
    private void resetData(boolean isMapWay,boolean isMapAisle, boolean isVehicle,boolean isSupplier){
        if (isMapWay){
            tvMapWay.setText(R.string.text_unchosen);//重新选择
            selectWayDialog.setData(null,null);//清空模块数据
            mapWayId=0;//
            mapWay="";
        }
        if (isMapAisle){
            tvMapAisle.setText(R.string.text_unchosen);//重新选择
            selectAisleDialog.setData(null,null);//清空模块数据
            mapAisleId=0;
            mapAisle="";
        }
        if (isVehicle){
            tvCarType.setText(R.string.text_unchosen);//重新选择
            selectCarTypeDialog.setData(null,null);//清空供应商数据
            vehicleId=0;//
            vehicleName="";
        }
        if (isSupplier){
            tvSupplier.setText(R.string.text_unchosen);//重新选择
            selectSupplierDialog.setData(null,null);//清空等级数据
            supplierId=0;//重置等级ID
            supplierName="";
        }
        isCalculateEnabled();//判断是否启用计算按钮
    }

    /**
     * 判断是否启用计算按钮
     */
    private void isCalculateEnabled(){
        if(mapWayId==3||mapWayId==4){
            if (mapWayId >0 && vehicleId >0  && supplierId >0 ){
                btnSave.setEnabled(true);//启用
            }else {
                btnSave.setEnabled(false);//禁用
            }
        }else {
            if (mapWayId >0 && mapAisleId >0  && vehicleId >0  && supplierId >0 ){
                btnSave.setEnabled(true);//启用
            }else {
                btnSave.setEnabled(false);//禁用
            }
        }
    }
  /*  //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            setResult(1,(new Intent()));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
       // exec.shutdown();
        //注销广播
        myActivity.getApplicationContext().unregisterReceiver(myBroadcastReceiver);
    }
}
