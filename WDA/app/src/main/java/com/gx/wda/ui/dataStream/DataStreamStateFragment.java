package com.gx.wda.ui.dataStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.adapter.InformAdapter;
import com.gx.wda.adapter.MessageListAdapter;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.bean.MessageVo;
import com.gx.wda.dialog.TopOptionDialog;
import com.gx.wda.ui.MainActivity;
import com.gx.wda.ui.main.TFCardActivity;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.ImageUtil;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.Tools;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import me.leefeng.promptlibrary.PromptDialog;

public class DataStreamStateFragment extends Fragment {



    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private int stateId;
    private EditText etAsciiCode;
    private EditText etHexCode;
    private LinearLayout llChangeAscii;
    private LinearLayout llCopyHex;
    private boolean checkTigger=false;



    private boolean isDialogShow = false;
    private final int GET_CODE = 1;//定义页面常量
    private final int FLOW_CODE = 2;
    private int mesCount = 0;//报文计数

    @SuppressLint("StaticFieldLeak")
    public static Context context ;
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    public SwitchButton sbOpenLis;
    ExecutorService exec = Executors.newCachedThreadPool();
    private ScrollView scrollView;
    private PromptDialog promptDialog;//加载框
    private PromptDialog promptDialog1;//加载框
    private LinearLayout llMapConfig;
    private LinearLayout llMessage;
    private ImageView ivFlowControl;
    private EditText etSend;
    private LinearLayout layoutContent;
    private LinearLayout llContent;
    private LinearLayout llScrollContent;
    private TextView tvChangeTime;
    private LinearLayout llBtnSend;

    private TopOptionDialog addressDialog;//寻址方式dialog
    private LinearLayout llAddressMode;
    private TextView tvAddressMode;
    private ImageView ivAddreddMode;
    private EditText etPhysicsStart;
    private EditText etPhysicsEnd;
    private LinearLayout llPhysics;
    private LinearLayout llFunction;
    private EditText etFunSite;

    private boolean TimerState=false;
    private LinearLayout llSelectCanId;
    private LinearLayout llSelectAisle;
    private TextView tvSelectCanId;
    private TextView tvSelectAisle;
    private ImageView ivSelectCanId;
    private ImageView ivSelectAisle;
    private TopOptionDialog canidDialog;
    private TopOptionDialog aisleDialog;
    private List<AppendOptionVo> listCanId = new ArrayList<>();
    private List<String> canStr = new ArrayList<>();
    private List<AppendOptionVo> listAisle = new ArrayList<>();
    private List<String> aisleStr = new ArrayList<>();
    private String CAN_ID="ALL";
    private String CAN_AISLE="ALL";
    private LinearLayout llRecordSetting;
    private int recordCount = 200;
    private int recordTime = 3;
    //注册手机热点监听广播
    IntentFilter wifiFilter = new IntentFilter();
    public static LinearLayout llStop;
    public static LinearLayout llStart;
    public static LinearLayout llTriggerReocrd;
    public static LinearLayout llTriggerOver;


    private RecyclerView rvMsgList;//列表
    private MessageListAdapter msgAdapter;//适配器
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity=(Activity) context;//设置上下文

        //注册手机热点监听广播
        wifiFilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        myActivity.registerReceiver(new HotsspotReceiver(), wifiFilter);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null; //初始化加载层
        promptDialog = new PromptDialog(myActivity);
        promptDialog1 = new PromptDialog(myActivity);
        context = getContext();
        myApplication= (MyApplication) myActivity.getApplication();
        //获取传递的参数 orderStateId
        Bundle bundle=getArguments();
        stateId = bundle.getInt("pageId");
        if (stateId==0){
            view=inflater.inflate(R.layout.fragment_message,container,false);

            scrollView = view.findViewById(R.id.scrollView);
            llMapConfig = view.findViewById(R.id.ll_map_config);
            llMessage = view.findViewById(R.id.ll_message);


            //报文数据加载控件
            llContent = view.findViewById(R.id.ll_message_content);
            llScrollContent = view.findViewById(R.id.ll_scrollview_content);

            rvMsgList = view.findViewById(R.id.rv_message_list);

            //解决刷新时闪烁的问题
            rvMsgList.getItemAnimator().setAddDuration(0);
            rvMsgList.getItemAnimator().setChangeDuration(0);
            rvMsgList.getItemAnimator().setMoveDuration(0);
            rvMsgList.getItemAnimator().setRemoveDuration(0);
            ((SimpleItemAnimator)rvMsgList.getItemAnimator()).setSupportsChangeAnimations(false);



            llRecordSetting = view.findViewById(R.id.ll_record_setting);

            //时间转换按钮
            tvChangeTime = view.findViewById(R.id.ll_msg_change_time);
            //停止读取报文
            llStop = view.findViewById(R.id.ll_message_stop);
            //开始读取报文
            llStart = view.findViewById(R.id.ll_message_start);
            //软触发
            llTriggerReocrd = view.findViewById(R.id.ll_trigger_record);
            //软结束
            llTriggerOver = view.findViewById(R.id.ll_trigger_over);


            //报文筛选下拉框
            llSelectCanId = view.findViewById(R.id.ll_select_canid);
            llSelectAisle = view.findViewById(R.id.ll_select_aisle);
            //下拉框内容
            tvSelectCanId = view.findViewById(R.id.tv_select_canid);
            tvSelectAisle = view.findViewById(R.id.tv_select_aisle);
            //下拉框图标
            ivSelectCanId = view.findViewById(R.id.iv_select_canid);
            ivSelectAisle = view.findViewById(R.id.iv_select_aisle);
            boolean flag= (boolean) SPUtils.get(myActivity,SPUtils.SP_SOFT_TRIGGER,false);
            if (flag){
                llTriggerReocrd.setVisibility(View.GONE);
                llTriggerOver.setVisibility(View.VISIBLE);
            }
            initMsgView();
            setViewMsg();



        } else if (stateId==1) {
            //诊断专家
            view = inflater.inflate(R.layout.fragment_diagnostician, container, false);
            etAsciiCode = view.findViewById(R.id.et_ascii_code);
            etHexCode = view.findViewById(R.id.et_hex_code);
            llChangeAscii = view.findViewById(R.id.ll_change_ascii);
            llCopyHex = view.findViewById(R.id.ll_copy_hex);
            ivFlowControl = view.findViewById(R.id.id_ligusticum_lucidum);
            //寻址方式下拉框
            llAddressMode = view.findViewById(R.id.ll_addressing_mode);
            tvAddressMode = view.findViewById(R.id.tv_addressing_mode);
            ivAddreddMode = view.findViewById(R.id.iv_addressing_mode);
            //物理寻址地址栏
            llPhysics = view.findViewById(R.id.ll_physics_et);
            etPhysicsStart = view.findViewById(R.id.et_physics_site_start);
            etPhysicsEnd = view.findViewById(R.id.et_physics_site_end);
            //功能寻址地址栏
            llFunction = view.findViewById(R.id.ll_function_et);
            etFunSite = view.findViewById(R.id.et_fun_site_val);

            //发送报文
            llBtnSend = view.findViewById(R.id.ll_instruct_send);
            initDiagnostician();
            setViewDiagnostician();


            etSend = view.findViewById(R.id.et_send_message);


        }
        return view;
    }


    /**
     * 总线监听事件
     */
    private void setViewMsg() {
        //CANID
        llSelectCanId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canidDialog.show();//显示
                ivSelectCanId.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });

        //CAN通道
        llSelectAisle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aisleDialog.show();//显示
                ivSelectAisle.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });

        //映射
        llMapConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    if(myApplication.isLinkState()){
                        myApplication.setVciRun(false);
                        Intent intent = new Intent(myActivity,MapConfigActivity.class);
                        startActivityForResult(intent,1);
                    }else{
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**
         * 时间类型转换
         */
        tvChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    if(TimerState){
                        TimerState=false;
                    }else{
                        TimerState=true;
                    }

                    //msgAdapter.isTimeType();
                }
            }
        });

        /**
         * 软触发配置
         */
        llRecordSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    if(myApplication.isLinkState()){
                        Intent intent = new Intent(myActivity,SettingRecordActivity.class);
                        startActivityForResult(intent,1);
                    }else{
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        /**
         * 停止报文
         */
        llStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Tools.isFastClick()){
                    if(myApplication.getVciIp()!=null){
                        llStop.setVisibility(View.GONE);//隐藏停止按钮
                        llStart.setVisibility(View.VISIBLE);//显示启动按钮
                        myApplication.setVciRun(false);//停止报文
                    }else{
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**
         * 启动报文
         */
        llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Tools.isFastClick()){
                    if(myApplication.getVciIp()!=null){
                        llStart.setVisibility(View.GONE);//隐藏启动按钮
                        llStop.setVisibility(View.VISIBLE);//显示停止按钮
                        myApplication.setVciRun(true);//启动报文
                    }else{
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**
         * 软触发
         */
        llTriggerReocrd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (!Tools.isFastClick()) {
                    if (myApplication.isLinkState()) {
                        //软触发
                        String SAVE_COUNT = (String) SPUtils.get(myActivity,SPUtils.SP_TEMP_TRIGGER_COUNT,"");//获取软触发保留条数
                        String SAVE_TIMER = (String) SPUtils.get(myActivity,SPUtils.SP_TEMP_TRIGGER_TIMER,"");//获取软触发录制时间
                        if(Tools.isNotNull(SAVE_COUNT)){
                            recordCount = Integer.parseInt(SAVE_COUNT);
                        }
                        if(Tools.isNotNull(SAVE_TIMER)){
                            recordTime = Integer.parseInt(SAVE_TIMER);
                        }
                        //保存条数
                        String count = EncodingUtil.sToB(EncodingUtil.addZeroForNum(Integer.toHexString(recordCount), 4, true).split(""));//小端结构
                        //录制时间
                        String time = EncodingUtil.addZeroForNum(Integer.toHexString(recordTime), 2, true);

                        String message = ("AA FE 0A 00 02 00 06 00 02 03 00 " + time + count).replace(" ", "");
                        String xor = EncodingUtil.getBCC(EncodingUtil.hexToByte(message));
                        message += xor;
                        String sendMsg = message;

                        exec.execute(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.okSocketSend(sendMsg);
                            }
                        });


                        //记录触发时间
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Calendar cal = Calendar.getInstance();
                        String dataNow = String.valueOf(Long.parseLong(dateFormat.format(cal.getTime()))-5);//-5s
                        SPUtils.put(myActivity, SPUtils.SP_TRIGGER_START, dataNow);
                        //清除上次结束时间
                        SPUtils.put(myActivity, SPUtils.SP_TRIGGER_END, "");
                        //显示软结束按钮
                        llTriggerOver.setVisibility(View.VISIBLE);
                        //隐藏软触发按钮
                        llTriggerReocrd.setVisibility(View.GONE);
                        //查询触发状态
                        selectTiggerState();
                    } else {
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**
         * 软触发结束
         */
        llTriggerOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Tools.isFastClick()) {
                    if (myApplication.isLinkState()) {
                        //软结束
                        String message = "AA FE 0A 00 02 00 06 00 02 00 00 00 00 00".replace(" ","");
                        String xor = EncodingUtil.getBCC(EncodingUtil.hexToByte(message));
                        message = message += xor;
                        String sendMsg = message;
                        exec.execute(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.okSocketSend(sendMsg);
                            }
                        });

                        //记录结束触发时间
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Calendar cal = Calendar.getInstance();
                        String dataNow = dateFormat.format(cal.getTime());
                        SPUtils.put(myActivity,SPUtils.SP_TRIGGER_END,dataNow);
                        //隐藏软结束按钮
                        llTriggerOver.setVisibility(View.GONE);
                        //显示软触发按钮
                        llTriggerReocrd.setVisibility(View.VISIBLE);
                        if (MainActivity.softNotify !=null){
                            myApplication.notificationManager.cancel(520);//关闭通知栏
                        }
                        //查询触发状态
                        selectTiggerState();
                    } else {
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    /**
     * 查询VCI触发状态
     */
    public void selectTiggerState(){
        //查询VCI触发状态
        /**
         * 查询永久触发状态
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//休眠
                    //永久触发
                    String continuousRecord = "AA FE 05 00 02 80 01 00 02".replace(" ","");
                    String continuousXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(continuousRecord));
                    continuousRecord += continuousXor;
                    String sendContinuous = continuousRecord;
                    MainActivity.okSocketSend(sendContinuous);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //硬件触发
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    String softRecord = "AA FE 05 00 02 80 01 00 08".replace(" ","");
                    String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                    softRecord += softXor;
                    MainActivity.okSocketSend(softRecord);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //软触发
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    String softRecord = "AA FE 05 00 02 80 01 00 01".replace(" ","");
                    String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                    softRecord += softXor;
                    MainActivity.okSocketSend(softRecord);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //永久映射
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    String softRecord = "AA 01 10 00 00 00 08 00 10 07 00 00 03 22 C1 10 00 00 00 00".replace(" ","");
                    String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                    softRecord += softXor;
                    MainActivity.okSocketSend(softRecord);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 总线监听初始化
     */
    private void initMsgView() {
        //开启广播，接收报文
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        myActivity.getApplicationContext().registerReceiver(myBroadcastReceiver,intentFilter);
        //下拉框
        canidDialog = new TopOptionDialog(myActivity,"CANID",llSelectCanId,ivSelectCanId);
        listCanId.add(0,new AppendOptionVo(0,"ALL","",""));
        listAisle.add(0,new AppendOptionVo(0,"ALL","",""));
        aisleDialog = new TopOptionDialog(myActivity,getResources().getString(R.string.text_tf_card_can_channel),llSelectAisle,ivSelectAisle);
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvMsgList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        msgAdapter=new MessageListAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvMsgList.setAdapter(msgAdapter);
    }

    /**
     * 接收报文广播
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case "tcpClientReceiver":
                    String msg = intent.getStringExtra("tcpClientReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }

    /**
     * 处理解析报文线程
     */
    List<MessageVo> listAllMsg = new ArrayList<>();

    List<MessageVo> listScreen = new ArrayList<>();

    //保存上一帧的时间戳
    BigDecimal bdTimer = new BigDecimal(0);
    private class MyHandler extends Handler{
        private WeakReference<DataStreamStateFragment> mActivitys;

        MyHandler(DataStreamStateFragment activity){
            mActivitys = new WeakReference<DataStreamStateFragment>(activity);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {
            if(!checkTigger){
                checkTigger=true;
            }
            if(myApplication.isVciRun()){
                String[] message = (msg.obj).toString().split(",");
                for (int i=0;i<message.length;i++){

                    String MSG_HEADER = message[i].substring(0,4);
                    if(MSG_HEADER.contains("AA00")){
                        int length = Integer.parseInt(message[i].substring(4,6),16)*2/40;//长度帧;
                        String data = message[i];
                        for (int j=0;j<length;j++){
                            int math = j*40;
                            //String test = data.substring(8+math,20+math);
                            Long Timer = Long.parseLong(EncodingUtil.sToB(data.substring(8+math,20+math).split("")),16);//时间戳

                            String DLC = data.substring(21+math,22+math);//DLC
                            String Aisle = data.substring(22+math,24+math);//CAN通道
                            String dataField = data.substring(32+math,48+math);//数据场
                            /*Log.e("时间戳",Timer+"\n");
                            Log.e("DLC",DLC+"\n");
                            Log.e("CAN通道",Aisle+"\n");
                            Log.e("帧ID",CANID+"\n");
                            Log.e("数据场",dataField+"\n");*/


                            /*CANID根据单帧和多帧不同类型使用不同的解析方式*/
                            String untreated = EncodingUtil.sToB(data.substring(24+math,32+math).split(""));
                            String first =untreated.substring(0,1);//取得首位 转二进制
                            if(first.equals("0")){
                                first = "0000";
                            }else {
                                first = EncodingUtil.hexString2binaryString(first);
                            }
                            String CANID = first.substring(first.length()-1,first.length()) + untreated.substring(1,untreated.length());
                            CANID = CANID.replaceAll("^(0+)", "").replaceAll("(0)+$", "");


                            /* 绑定CANID下拉框*/
                            if(canStr.indexOf(CANID)==-1){
                                canStr.add(CANID);
                                listCanId.add(listCanId.size(),new AppendOptionVo(listCanId.size(),CANID,"",""));
                                //Canid下拉框点击事件
                                canidDialog.setData(listCanId, new TopOptionDialog.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                        tvSelectCanId.setText(appendOptionVo.getName());
                                        CAN_ID = appendOptionVo.getName();
                                        popupWindow.dismiss();//关闭


                                         if (!CAN_ID.equals("ALL")){
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int len = listAllMsg.size();
                                                    listScreen.clear();

                                                    //查找
                                                    listScreen = listAllMsg.stream()
                                                            .filter(item -> item.getCanId().equals(appendOptionVo.getName()))
                                                            .collect(Collectors.toList());
                                                    //整体刷新
                                                    msgAdapter.addItem(listScreen);
                                                }
                                            }).start();
                                        }else{
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    msgAdapter.addItem(listAllMsg);
                                                }
                                            }).start();
                                        }



                                        if (!CAN_ID.equals("ALL")){
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int len = listAllMsg.size();
                                                    listScreen.clear();

                                                    for (MessageVo screen : listAllMsg) {
                                                        if(screen.getCanId().equals(appendOptionVo.getName())){
                                                            listScreen.add(screen);
                                                        }
                                                    }
                                                    for (int i=0;i<len;i++){
                                                        if(listAllMsg.get(i).getCanId().equals(appendOptionVo.getName())){
                                                            listScreen.add(listAllMsg.get(i));
                                                        }
                                                    }
                                                    msgAdapter.addItem(listScreen);
                                                }
                                            }).start();
                                        }else{
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    msgAdapter.addItem(listAllMsg);
                                                }
                                            }).start();
                                        }
                                    }
                                });
                            }

                            /*绑定CAN通道下拉框*/
                            if(Aisle.equals("00")){
                                Aisle = "CAN1";
                            }else if(Aisle.equals("01")){
                                Aisle = "CAN2";
                            }else{
                                Aisle = "CAN1";
                            }
                            if(aisleStr.indexOf(Aisle)==-1){
                                aisleStr.add(Aisle);
                                listAisle.add(listAisle.size(),new AppendOptionVo(listAisle.size(),Aisle,"",""));
                                //Canid下拉框点击事件
                                aisleDialog.setData(listAisle, new TopOptionDialog.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                        tvSelectAisle.setText(appendOptionVo.getName());
                                        CAN_AISLE = appendOptionVo.getName();
                                        popupWindow.dismiss();//关闭


                                        /*int len = listAllMsg.size();
                                        for (int i=0;i<len;i++){
                                            if(listAllMsg.get(i).getAisle().equals(appendOptionVo.getName())){
                                                listScreen.add(listAllMsg.get(i));
                                            }
                                        }
                                        msgAdapter.addItem(listScreen);*/
                                    }
                                });
                            }


                            //筛选  动态添加控件
                            if(CAN_ID.equals("ALL")){
                                if(CAN_AISLE.equals("ALL")){
                                    addView(CANID,Timer,DLC,dataField,Aisle);
                                    scrollTo();
                                }else{
                                    if(CAN_AISLE.equals(Aisle)){
                                        addView(CANID,Timer,DLC,dataField,Aisle);
                                        scrollTo();
                                    }
                                }
                            }else if(CAN_ID.equals(CANID)){
                                if(CAN_AISLE.equals("ALL")){
                                    addView(CANID,Timer,DLC,dataField,Aisle);
                                    scrollTo();
                                }else{
                                    if(CAN_AISLE.equals(Aisle)){
                                        addView(CANID,Timer,DLC,dataField,Aisle);
                                        scrollTo();
                                    }
                                }
                            }


                           /* MessageVo msgVo = new MessageVo();
                            msgVo.setCanId(CANID);
                            Float f=Float.valueOf(Timer.toString());
                            f = f/(1000*1000*1000);
                            msgVo.setTimer(String.format(Locale.getDefault(),"%.6f",f));
                            BigDecimal dataTime=new BigDecimal(Timer);
                            msgVo.setLocalTime(String.valueOf(dataTime.subtract(bdTimer)));
                            bdTimer = dataTime;
                            msgVo.setDlc(DLC);
                            msgVo.setDataField(dataField);
                            msgVo.setAisle(Aisle);

                            listAllMsg.add(msgVo);
                           // msgAdapter.addNewItem(msgVo);
                           // rvMsgList.smoothScrollToPosition(msgAdapter.getItemCount()-1);

                            //筛选
                            if(CAN_ID.equals("ALL")){
                                if(CAN_AISLE.equals("ALL")){
                                    listScreen.addAll(listAllMsg);
                                    msgAdapter.addNewItem(msgVo);
                                    //rvMsgList.smoothScrollToPosition(msgAdapter.getItemCount()-1);
                                }else{
                                    if(CAN_AISLE.equals(Aisle)){
                                        listScreen.add(msgVo);
                                        msgAdapter.addNewItem(msgVo);
                                        //rvMsgList.smoothScrollToPosition(msgAdapter.getItemCount()-1);
                                    }
                                }
                            }else if(CAN_ID.equals(CANID)){
                                if(CAN_AISLE.equals("ALL")){
                                    listScreen.addAll(listAllMsg);
                                    msgAdapter.addNewItem(msgVo);
                                   // rvMsgList.smoothScrollToPosition(msgAdapter.getItemCount()-1);
                                }else{
                                    if(CAN_AISLE.equals(Aisle)){
                                        listScreen.add(msgVo);
                                        msgAdapter.addNewItem(msgVo);
                                       // rvMsgList.smoothScrollToPosition(msgAdapter.getItemCount()-1);
                                    }
                                }
                            }
                            rvMsgList.smoothScrollToPosition(msgAdapter.getItemCount());
*/

                        }
                    }
                }
            }
        }
    }


    /**
     * 定位滚动条
     */
    public void scrollTo(){
        if (scrollView == null || llContent == null) {
            return;
        }
        int offset = llContent.getMeasuredHeight()
                - scrollView.getMeasuredHeight();
        if (offset < 0) {
            offset = 0;
        }
        scrollView.scrollTo(0, offset-200);
    }


    /**
     * 添加报文
     * @param canid
     * @param time
     * @param len
     * @param data
     * @param aisle,
     */
    public void addView(String canid,Long time,String len,String data,String aisle){


        mesCount++;
        layoutContent = new LinearLayout(myActivity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60);
        layoutContent.setLayoutParams(layoutParams);
        layoutContent.setOrientation(LinearLayout.HORIZONTAL);


        LinearLayout.LayoutParams layoutTextView = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);

        //报文计数
        TextView tvmsgCount = new TextView(myActivity);
        tvmsgCount.setLayoutParams(layoutTextView);
        tvmsgCount.setGravity(Gravity.CENTER);
        tvmsgCount.setText(String.valueOf(mesCount));


        //CAN ID
        TextView tvCanid = new TextView(myActivity);
        tvCanid.setLayoutParams(layoutTextView);
        tvCanid.setGravity(Gravity.CENTER);
        tvCanid.setText(canid);


        //时间戳
        //绝对
        TextView tvTimes = new TextView(myActivity);
        tvTimes.setLayoutParams(layoutTextView);
        tvTimes.setGravity(Gravity.CENTER);
        Float f=Float.valueOf(time.toString());
        f = f/(1000*1000*1000);
        tvTimes.setText(String.format(Locale.getDefault(),"%.6f",f));


        //相对
        BigDecimal dataTime=new BigDecimal(time);

        TextView tvAbsTime = new TextView(myActivity);
        tvAbsTime.setLayoutParams(layoutTextView);
        tvAbsTime.setGravity(Gravity.CENTER);
        tvAbsTime.setText(String.valueOf(dataTime.subtract(bdTimer)));



        bdTimer = dataTime;



        //DLC
        TextView tvDlc = new TextView(myActivity);
        tvDlc.setLayoutParams(layoutTextView);
        tvDlc.setGravity(Gravity.CENTER);
        tvDlc.setText(len);


        LinearLayout.LayoutParams layoutData = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2);
        TextView tvData = new TextView(myActivity);
        tvData.setLayoutParams(layoutData);
        tvData.setGravity(Gravity.CENTER);
        tvData.setText(data.toUpperCase());

        TextView tvAisle = new TextView(myActivity);
        tvAisle.setLayoutParams(layoutTextView);
        tvAisle.setGravity(Gravity.CENTER);
        tvAisle.setText(aisle);



        layoutContent.addView(tvmsgCount);
        layoutContent.addView(tvCanid);
        if(TimerState){
            layoutContent.addView(tvAbsTime);
        }else{
            layoutContent.addView(tvTimes);
        }
        layoutContent.addView(tvDlc);
        layoutContent.addView(tvData);
        layoutContent.addView(tvAisle);


        llContent.addView(layoutContent);


        int count = llContent.getChildCount();
        if(count>2500){
            llContent.removeViewAt(0);
        }
    }

    /**
     * 对字符串进行反转
     * @param s
     * @return
     */
    public static String spiltRtoL(String s) {

        StringBuffer sb = new StringBuffer();
        int length = s.length();
        char[] c = new char[length];
        for (int i = 0; i < length; i++) {
            c[i] = s.charAt(i);
        }
        for (int i = length - 1; i >= 0; i--) {
            sb.append(c[i]);
        }

        return sb.toString();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }












    //诊断专家
    private void setViewDiagnostician() {
        //ASCII转HEX
        llChangeAscii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ASCII = etAsciiCode.getText().toString();
                char[] chars =ASCII.toCharArray();
                StringBuilder hex = new StringBuilder();
                for (char ch : chars) {
                    hex.append(Integer.toHexString((int) ch));
                }
                etHexCode.setText(hex);
            }
        });

        //复制HEX
        llCopyHex.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String HEX = etHexCode.getText().toString();
                if(Tools.isNotNull(HEX)){
                    ClipboardManager cm = (ClipboardManager) myActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(HEX);//复制的文字
                    Toast.makeText(myActivity,getResources().getString(R.string.text_valuecopy),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //前往流控帧设置
        ivFlowControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication.setVciRun(false);//关闭VCI监听
                Intent intent = new Intent(myActivity,FlowControlActvity.class);
                startActivityForResult(intent,FLOW_CODE);
            }
        });

        //寻址类型下拉
        llAddressMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressDialog.show();//显示
                ivAddreddMode.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });


        /* etSend.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length()==6){
                        String str = "";
                        for (int i=0;i<6;i++){
                            str = str+s.charAt(i);
                            if(i!=0){
                                if((i+1)%2==0){
                                    str = str+" ";
                                }
                            }
                        }
                        etSend.setText(str);
                        //光标显示在文本末尾处
                        etSend.setSelection( etSend. getText().length());
                    }else if(s.length()==7){
                        etSend.setText(s.toString().replace(" ",""));
                        //光标显示在文本末尾处
                        etSend.setSelection( etSend. getText().length());
                    }
                }
            });*/

        //发送报文
        llBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                /*if(tcpClient!=null){
                    String message = "AA FE 08 00 00 00 04 00 00 00 00 00";
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tcpClient.send(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Toast.makeText(myActivity,getString(R.string.text_message_sent),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                }*/

                  /*  if(etSend.length()==8){
                        if(tcpClient!=null){
                            String message = "08 00 00 07 00 03 "+etSend.getText()+" 00 00 00 00";
                            exec.execute(new Runnable() {
                                @Override
                                public void run() {
                                    tcpClient.send(message);
                                   //
                                }
                            });
                            Toast.makeText(myActivity,getString(R.string.text_message_sent),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(myActivity,getString(R.string.text_message_missing),Toast.LENGTH_SHORT).show();
                    }*/
            }
        });
    }

    /**
     * 诊断专家页面加载事件
     */
    private void initDiagnostician() {
        //设置寻址方式下拉框数据
        addressDialog =new TopOptionDialog(myActivity,"",llAddressMode,ivAddreddMode);
        List<AppendOptionVo> list = new ArrayList<>();
        list.add(new AppendOptionVo(1,getString(R.string.text_physical_addressing),"",""));
        list.add(new AppendOptionVo(2,getString(R.string.text_functional_addressing),"",""));
        //寻址方式下拉框点击事件
        addressDialog.setData(list, new TopOptionDialog.OnItemClickListener() {
            @Override
            public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                tvAddressMode.setText(appendOptionVo.getName());
                if(appendOptionVo.getId()==1){
                    llFunction.setVisibility(View.GONE);
                    llPhysics.setVisibility(View.VISIBLE);
                }else{
                    llFunction.setVisibility(View.VISIBLE);
                    llPhysics.setVisibility(View.GONE);
                }
                popupWindow.dismiss();//关闭
            }
        });
    }


    /**
     * 监听热点状态广播
     */
    class HotsspotReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.net.wifi.WIFI_AP_STATE_CHANGED")) {//便携式热点的状态为：10---正在关闭；11---已关闭；12---正在开启；13---已开启
                int state = intent.getIntExtra("wifi_state", 0);
                if (state == 11) {
                    //正在关闭热点
                    if(sbOpenLis!=null){
                        sbOpenLis.setEnabled(false);//禁用触发按钮

                        llStop.setVisibility(View.GONE);//隐藏停止按钮
                        llStart.setVisibility(View.VISIBLE);//显示启动按钮
                    }
                }
                if (state == 10) {
                    //正在关闭热点
                    if(sbOpenLis!=null){
                        sbOpenLis.setEnabled(false);//禁用触发按钮

                        llStop.setVisibility(View.GONE);//隐藏停止按钮
                        llStart.setVisibility(View.VISIBLE);//显示启动按钮
                    }
                }
            }
        }
    }





      @Override
    public void onResume() {
        super.onResume();
        /*if(llStop!=null){
            if(llStop.getVisibility()!=View.GONE){
                myApplication.setVciRun(true);
            }
        }*/

        if(llStop!=null){
            llStop.setVisibility(View.GONE);//隐藏停止按钮
            llStart.setVisibility(View.VISIBLE);//显示启动按钮
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        myApplication.setVciRun(false);
    }
}