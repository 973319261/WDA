package com.gx.wda.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.ui.MainActivity;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.MPermissionUtils;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.MyActionBar;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import me.leefeng.promptlibrary.PromptDialog;

public class LinkVciActivity extends AppCompatActivity {
    private static MyApplication myApplication;//全局
    private static PromptDialog promptDialog;//加载框
    private static Activity myActivity;
    public static Context context;
    private MyActionBar myActionBar;//标题栏
    private SwitchButton switchButton;//switch 记住
    private static Button btnLink;
    private EditText etHotSpotName;
    private EditText etHotSpotPass;
    public static CountDownTimer timerLink;
    private static CountDownTimer timer;
    private static boolean remember;//记住密码

    public static String SET_NAME = "";//NAME
    public static String SET_PASS = "";//PASS

    public static String VCI_Mac = "";//MAC地址
    public static String VCI_IP = "";//IP
    public static String VCI_SUBNET_MASK = "";//子网掩码
    public static String VCI_GATEWAY = "";//网关IP
    public static String VCI_PORT = "";//端口
    public static String USE_ACCOUNT = "";//上次使用者
    public static String BREAK_TIME = "";//上次使用断开时间


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkvci);
        myActivity=this;
        context=this;
        myApplication= (MyApplication) myActivity.getApplication();

        promptDialog = new PromptDialog(myActivity);
        //获取控件
        btnLink = findViewById(R.id.btn_link);
        etHotSpotName = findViewById(R.id.et_hopspot_name);
        etHotSpotPass = findViewById(R.id.et_hopspot_pass);
        switchButton=findViewById(R.id.switch_button);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_link_vci_title), R.drawable.ic_back,0,
                1, 0, new MyActionBar.ActionBarClickListener() {
                    @Override
                    public void onLeftClick() {
                        finish();
                    }

                    @Override
                    public void onRightClick() {

                    }
                });
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色

        initView();
        setViewListener();//监听事件




    }

    private void setViewListener() {

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Tools.isFastClick()){
                    String[] permissions=new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION};//判断定位权限
                    if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                        if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_DISABLED){
                            //wifi关闭状态
                            Toast.makeText(myActivity,getString(R.string.text_permissions_wifi),Toast.LENGTH_LONG).show();
                        }else if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED){
                            //wifi打开状态
                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                            String SSID = wifiInfo.getSSID();//获取Wifi名称
                            if(SSID.contains("unknown")){
                                //未开启定位，无法获取SSID
                                Toast.makeText(myActivity,getString(R.string.text_permissions_location_open),Toast.LENGTH_LONG).show();
                            }else if(!SSID.contains("VCI")){
                                //WIFI名不包含VCI，不是vci的热点
                                Toast.makeText(myActivity,getString(R.string.text_permissions_not_vciwifi),Toast.LENGTH_LONG).show();
                            }else{
                                promptDialog.showLoading(getString(R.string.text_connect_vci));
                                timer=new CountDownTimer(1000*60,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        promptDialog.showError("配置失败,是否连接VCI的WIFI？",true);
                                        btnLink.setEnabled(true);
                                        cancel();

                                    }
                                };
                                timer.start();//启动
                                String hotSpotName = etHotSpotName.getText().toString();
                                String hotSpotPass = etHotSpotPass.getText().toString();
                                if(hotSpotName!=""){
                                    if(hotSpotPass!=""&&hotSpotPass.length()>=8){
                                        SET_NAME = hotSpotName;
                                        SET_PASS = hotSpotPass;
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    sendBroadcast("AA 01 AA BB CC DD 94",1,hotSpotName,hotSpotPass);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    }else{
                                        //密码不能少于8位
                                        Toast.makeText(myActivity,getString(R.string.text_link_check_ssid),Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    //SSID不能为空
                                    Toast.makeText(myActivity,getString(R.string.text_link_check_pass),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }else {//没有权限
                        MPermissionUtils.showTipsDialog(myActivity,getString(R.string.text_permissions_location));//提示
                    }
                }
            }
        });


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){//开启
                    remember=true;
                }else {
                    remember=false;
                }
            }
        });
        etHotSpotName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0 && etHotSpotPass.length()>=8){
                    btnLink.setEnabled(true);//开启连接按钮
                }else {
                    btnLink.setEnabled(false);//关闭连接按钮
                }
            }
        });
        etHotSpotPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>=8 && etHotSpotName.length()>0){
                    btnLink.setEnabled(true);//开启连接按钮
                }else {
                    btnLink.setEnabled(false);//关闭连接按钮
                }
            }
        });
    }

    private void initView() {
        remember= (boolean) SPUtils.get(myActivity,SPUtils.SP_HOT_REMEMBER,false);//获取本地的是否记住密码
        if (remember){//记住
            switchButton.setChecked(true);//开启switch
            btnLink.setEnabled(true);//开启连接按钮
            String account = (String) SPUtils.get(myActivity,SPUtils.SP_HOT_NAME,"");//获取本地账号
            String password = (String) SPUtils.get(myActivity,SPUtils.SP_HOT_PASSWORD,"");//获取本地密码
            etHotSpotName.setText(account);//回填账号
            etHotSpotPass.setText(password);//回填解密后的密码
        }else {//不记住
            switchButton.setChecked(false);//关闭switch
        }
    }


    /**
     * 发送报文
     * @param msg
     * @param index
     * @param ssid
     * @param pass
     * @throws IOException
     */
    private static void sendBroadcast(String msg, int index, final String ssid, final String pass) throws IOException {
        DatagramSocket socket = new DatagramSocket(13047);
        String msgs = msg.replace(" ","");
        String CRC8_ITU = EncodingUtil.CRC8(EncodingUtil.hexToByte(msgs));
        msgs = msgs+CRC8_ITU.toUpperCase();

        Log.e("广播报文：", msgs);
        Log.e("广播报文长度：", String.valueOf(msgs.length()));
        byte[] data = msg.getBytes();
        byte[] data2 = EncodingUtil.hexToByte(msgs);
        //创建DatagramPacket对象，用来存放发送的数据，端口和ip
        DatagramPacket packet = new DatagramPacket(data2,data2.length);
        packet.setPort(58121);
        packet.setAddress(InetAddress.getByName("255.255.255.255"));

        //使用DatagramSocket的send方法，发送数据包
        socket.send(packet);
        socket.close();
        // new Thread(new SearchListener()).start();
        //latch.countDown();

        if(index!=3){
            myApplication.setVciState(false);
            new Thread(new SearchListener(myActivity,index,ssid,pass)).start();
        }else{
            myApplication.setVciState(true);
            getVciState();
            socket.close();
        }

    }


    /**
     * 开启监听
     */
    private static class SearchListener implements Runnable{

        MyApplication myApplication;
        Activity activity;
        DatagramSocket socket;
        boolean isClosed = false;
        Integer index=0;
        String ssid;
        String pass;

        SearchListener(Activity activity,Integer index,String ssid,String pass){
            this.index = index;
            this.ssid = ssid;
            this.pass = pass;
            this.myApplication= (MyApplication) activity.getApplication();
        };


        @Override
        public void run() {
            Log.e("监听提示：","监听已启动...");


            try {
                socket = new DatagramSocket(13047);
                byte[] receiverData = new byte[1024];
                DatagramPacket receiverPacket = new DatagramPacket(receiverData,receiverData.length);

                //使用DatagramSocket对象接收数据包
                //该方法会阻塞，直到接收到数据报文
                try {
                    socket.receive(receiverPacket);
                }catch(SocketTimeoutException e){
                    exit();
                    sendBroadcast("AA 01 AA BB CC DD 94",1,SET_NAME,SET_PASS);
                }

                //拆解接收到的数据包
                //获得接收到的IP地址

                //数据缓冲区
                byte[] buffer = receiverPacket.getData();
                String datas = EncodingUtil.toHexString(buffer).substring(0,334);
               // System.out.println("Search-> Data:"+datas);
                if(index==1){
                    Log.e("第一次监听到的报文",datas);
                    VCI_Mac = datas.substring(4,16);
                    String VCI_NAME = datas.substring(16,56);//设备名称
                    String hardware = datas.substring(56,60);//硬件版本
                    String firmware = datas.substring(60,64);//固件版本
                    String serialNumber = datas.substring(64,72);//VCI序列号
                    String sta = datas.substring(72,74);
                    String mod = datas.substring(74,76);
                    //0-sta 和 加密模式固定 00 34
                    String SSID = datas.substring(76,120);//SSID
                    String Pass = datas.substring(120,256);//Pass
                    VCI_IP = datas.substring(256,264);//VCI IP
                    VCI_SUBNET_MASK = datas.substring(264,272);//子网掩码
                    VCI_GATEWAY = datas.substring(272,280);//网关IP
                    VCI_PORT = datas.substring(280,284);//端口
                    USE_ACCOUNT = datas.substring(284,328);//上次记录使用者
                    BREAK_TIME = datas.substring(328,332);//上次使用断开时间

                    Log.e("MAC地址----",VCI_Mac);
                    Log.e("VCI名称----",VCI_NAME);
                    Log.e("硬件版本----",hardware);
                    Log.e("固件版本----",firmware);
                    Log.e("VCI序列号----",serialNumber);
                    Log.e("sta----",sta);
                    Log.e("加密模式----",mod);
                    Log.e("SSID----",SSID);
                    Log.e("Pass----",Pass);
                    Log.e("VCI_IP----",VCI_IP);
                    Log.e("子网掩码----",VCI_SUBNET_MASK);
                    Log.e("网关IP----",VCI_GATEWAY);
                    Log.e("端口----",VCI_PORT);
                    Log.e("上次记录使用者----",USE_ACCOUNT);
                    Log.e("上次使用断开时间----",BREAK_TIME);


                    int break_time = (int) Long.parseLong(BREAK_TIME,16);
                    String FullSSID = EncodingUtil.addZeroForNum(EncodingUtil.stringToHexString(ssid),44,false);
                    String FullPass = EncodingUtil.addZeroForNum(EncodingUtil.stringToHexString(pass),136,false);
                    String account = (String) SPUtils.get(myActivity,SPUtils.SP_ACCOUNT,"");
                    String UserNumber = EncodingUtil.addZeroForNum(EncodingUtil.stringToHexString(account),44,false);

                    if(break_time!=0){
                        //无人使用中
                        final String Msg = "AA 02 "+VCI_Mac+"00 24"+FullSSID+FullPass+"00000000"+VCI_SUBNET_MASK+VCI_GATEWAY+VCI_PORT+UserNumber;
                        exit();
                        sendBroadcast(Msg,2,ssid,pass);
                    }else {
                        //设备正在使用中
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //配置过，提示  当前VCI已被使用，是否确认进行配置
                                MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, myActivity.getResources().getString(R.string.text_link_check_using), new MessageDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        if (confirm==true){//确定按钮

                                            final String Msg = "AA 02 "+VCI_Mac+"00 24"+FullSSID+FullPass+"00000000"+VCI_SUBNET_MASK+VCI_GATEWAY+VCI_PORT+UserNumber;

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        exit();
                                                        sendBroadcast(Msg,2,ssid,pass);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();

                                        }else {
                                            close();
                                            myActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    promptDialog.showSuccess("已取消配置");
                                                    timer.cancel();
                                                }
                                            });
                                        }
                                        dialog.dismiss();//关闭弹出框
                                    }
                                });
                                messageDialog.setTitle(myActivity.getResources().getString(R.string.text_warning));
                                messageDialog.show();//显示弹出框

                            }
                        });
                    }
                }
                else if(index==2){
                    Log.e("第二次监听到的报文",datas);
                    exit();
                    sendBroadcast("AA 03 "+VCI_Mac+"0A",3,ssid,pass);
                }

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private void close(){
            if(socket !=null){
                socket.close();
            }
            socket = null;
            Log.e("监听提示：","监听Socket已关闭...");
        }

        private void exit(){
            isClosed=true;
            close();
        }
    }





    private static void getVciState() throws IOException {
        if(myApplication.isVciState()){
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    promptDialog.showSuccess("配置成功！请手动设置好热点");
                    timer.cancel();//取消定时器
                    MainActivity.timerLink().start();////开启VCI连接定时
                    SPUtils.put(myActivity,SPUtils.SP_HOT_NAME,SET_NAME);//保存账号到本地
                    SPUtils.put(myActivity,SPUtils.SP_HOT_PASSWORD,SET_PASS);//保存密码到本地
                    SPUtils.put(myActivity,SPUtils.SP_HOT_REMEMBER,remember);//保存是否记住密码到本地
                    btnLink.setEnabled(false);//禁止按钮
                }
            });

        }else{
            new Thread(new Runnable() {
                public void run() {
                    //sleep设置的是时长
                    try {
                        Thread.sleep(1000);
                        getVciState();
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }







}
