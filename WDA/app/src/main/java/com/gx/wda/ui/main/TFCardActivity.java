package com.gx.wda.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.dialog.BottomOptionDialog;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.ui.MainActivity;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.TFTPExample;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.MyActionBar;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.leefeng.promptlibrary.PromptDialog;

/***
 * TF卡录制与配置
 */
public class TFCardActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    public static Context context;
    ExecutorService exec = Executors.newCachedThreadPool();
    public SwitchButton switchButton;//切换按钮
    private LinearLayout llExport;//导出
    private MyActionBar myActionBar;//标题栏
    private EditText etCount;
    private EditText etTime;
    private LinearLayout llCanWay;
    private TextView tvCanWay;
    private BottomOptionDialog wayDialog;//can通道选择
    private PromptDialog promptDialog;//加载框
    private int wayId=3;
    private int recordWay = 3;//监听默认can通道
    private int recordTime = 3;//监听默认记录时间
    private int recordCount = 200;//监听默认保留条数
    //注册手机热点监听广播
    IntentFilter wifiFilter = new IntentFilter();
    private LinearLayout llContinuousReocrd;
    private LinearLayout llContinuousOver;
    private LinearLayout llClean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication= (MyApplication) myActivity.getApplication();
        context=myActivity;//设置上下文
        setContentView(R.layout.activity_tf_card);


        promptDialog = new PromptDialog(myActivity);
        /*switchButton=findViewById(R.id.switch_button);
        if(myApplication.isLinkState()){
            switchButton.setEnabled(true);
        }else{
            switchButton.setEnabled(false);
        }*/
        llExport=findViewById(R.id.ll_tf_card_export);
        llClean = findViewById(R.id.ll_tf_card_clean);
        etCount = findViewById(R.id.et_tfcard_record_count);
        etTime = findViewById(R.id.et_tfcard_record_time);
        llCanWay = findViewById(R.id.ll_tfcard_record_way);
        tvCanWay = findViewById(R.id.tv_tfcard_record_way);
        //永久触发
        llContinuousReocrd = findViewById(R.id.ll_continuous_reocrd);
        //结束
        llContinuousOver = findViewById(R.id.ll_continuous_over);
        myActionBar = findViewById(R.id.myActionBar);

        myActionBar.setData(getResources().getString(R.string.text_tf_card_title), R.drawable.ic_back,  0, 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
        setViewListener();//监听事件
    }
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        wayDialog =new BottomOptionDialog(myActivity,getResources().getString(R.string.text_detection_vehicle));
        promptDialog=new PromptDialog(myActivity);//加载框实例化
        List<AppendOptionVo> listWay = new ArrayList<>();
        listWay.add(new AppendOptionVo(1,"CAN1(6/14)","",""));
        listWay.add(new AppendOptionVo(2,"CAN2(3/11)","",""));
        listWay.add(new AppendOptionVo(3,"All","",""));
        wayDialog.setData(listWay, new BottomOptionDialog.OnItemClickListener() {
            @Override
            public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                tvCanWay.setText(appendOptionVo.getName());
                wayId=appendOptionVo.getId();
                popupWindow.dismiss();//关闭弹窗
            }
        });

        //回填录制状态
        boolean flag= (boolean) SPUtils.get(myActivity,SPUtils.SP_PERMANENT_TRIGGER,false);
        if (flag){
            llContinuousReocrd.setVisibility(View.GONE);
            llContinuousOver.setVisibility(View.VISIBLE);
        }

        //回填保存的参数
        String SAVE_COUNT = (String) SPUtils.get(myActivity,SPUtils.SP_PERMANENT_TRIGGER_COUNT,"");//获取永久触发保留条数
        String SAVE_TIMER = (String) SPUtils.get(myActivity,SPUtils.SP_PERMANENT_TRIGGER_TIMER,"");//获取永久触发录制时间
        String SAVE_ROW = (String) SPUtils.get(myActivity,SPUtils.SP_PERMANENT_TRIGGER_ROW,"");//获取永久触发录制通道
        if(Tools.isNotNull(SAVE_COUNT)){
            etCount.setText(SAVE_COUNT);
        }else{
            etCount.setText("200");
        }
        if(Tools.isNotNull(SAVE_TIMER)){
            etTime.setText(SAVE_TIMER);
        }else{
            etTime.setText("3");
        }
        if(Tools.isNotNull(SAVE_ROW)){
            wayId = Integer.parseInt(SAVE_ROW);
            if(wayId==1){
                tvCanWay.setText("CAN1(6/14)");
            }else if(wayId==2){
                tvCanWay.setText("CAN2(3/11)");
            }else{
                tvCanWay.setText("All");
            }
        }else{
            wayId=3;
            tvCanWay.setText("All");
        }
    }

    /**
     * 监听事件
     */
    private void setViewListener(){
        //永久触发
        llContinuousReocrd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(!Tools.isFastClick()){
                    recordWay = wayId;
                    SPUtils.put(myActivity,SPUtils.SP_PERMANENT_TRIGGER_ROW, String.valueOf(recordWay));
                    String setCount = etCount.getText().toString();
                    if(Tools.isNotNull(setCount)){
                        recordCount = Integer.parseInt(setCount);
                        SPUtils.put(myActivity,SPUtils.SP_PERMANENT_TRIGGER_COUNT,setCount);
                    }
                    String setTime = etTime.getText().toString();
                    if(Tools.isNotNull(setTime)){
                        recordTime = Integer.parseInt(setTime);
                        SPUtils.put(myActivity,SPUtils.SP_PERMANENT_TRIGGER_TIMER,setTime);
                    }



                    if(myApplication.isLinkState()){
                        //永久触发
                        String way= EncodingUtil.addZeroForNum(Integer.toHexString(recordWay),2,true) ;//
                        String count= EncodingUtil.sToB(EncodingUtil.addZeroForNum(Integer.toHexString(recordCount),4,true).split("")) ;//
                        String time= EncodingUtil.addZeroForNum(Integer.toHexString(recordTime),2,true);
                        //String message ="AA FE 0A 00 02 00 06 00 01 03 01 05 07 d0".replace(" ","");
                        String message = ("AA FE 0A 00 02 00 06 00 01 " + way + "00" + time + count).replace(" ","");
                        String xor = EncodingUtil.getBCC(EncodingUtil.hexToByte(message));
                        message += xor;
                        String sendMsg = message;

                        exec.execute(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.okSocketSend(sendMsg);
                            }
                        });

                        //硬件触发状态查询
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String softRecord = "AA FE 05 00 02 80 01 00 08".replace(" ","");
                                String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                                softRecord += softXor;
                                String sendHardware = softRecord;
                                exec.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.okSocketSend(sendHardware);
                                    }
                                });
                            }
                        }).start();
                        //显示结束按钮
                        llContinuousOver.setVisibility(View.VISIBLE);
                        //隐藏触发按钮
                        llContinuousReocrd.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //结束触发
        llContinuousOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Tools.isFastClick()){
                    if(myApplication.isLinkState()){
                        //结束
                        String message = "AA FE 0A 00 02 00 06 00 01 00 00 00 00 00".replace(" ","");
                        String xor = EncodingUtil.getBCC(EncodingUtil.hexToByte(message));
                        message += xor;
                        String sendMsg = message;
                        exec.execute(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.okSocketSend(sendMsg);
                            }
                        });
                        //隐藏软结束按钮
                        llContinuousOver.setVisibility(View.GONE);
                        //显示触发按钮
                        llContinuousReocrd.setVisibility(View.VISIBLE);

                        //硬件触发状态查询
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String softRecord = "AA FE 05 00 02 80 01 00 08".replace(" ","");
                                String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                                softRecord += softXor;
                                String sendHardware = softRecord;
                                exec.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.okSocketSend(sendHardware);
                                    }
                                });
                            }
                        }).start();
                    }else {
                        Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        //导出
        llExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    String vciIP = myApplication.getVciIp();
                    if(!Tools.isNotNull(vciIP)){
                        Toast.makeText(myActivity,"还未与VCI建立连接哦",Toast.LENGTH_SHORT).show();
                    }else{
                        /**
                         * 获取VCI文件列表
                         */
                        promptDialog.showLoading(getString(R.string.text_loading));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    boolean getFileList = TFTPExample.TftpUtils(vciIP,"filelist.txt","\\record\\filelist.txt");
                                    if(getFileList){
                                        myActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                myActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        promptDialog.dismiss();
                                                    }
                                                });
                                                //前往VCI保存目录界面
                                                Intent intent = new Intent(myActivity, ExportMessageActivity.class);
                                                startActivity(intent);
                                            }
                                        });

                                    }else{
                                        //连接失败
                                        myActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                promptDialog.showError("文件不存在！");
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
            }
        });

        //限制文本最大值
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if(Integer.parseInt(s.toString())>2000){
                        etCount.setText("2000");
                        Toast.makeText(myActivity,getString(R.string.text_record_setting_maximum)+"2000",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        etTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >0 ) {//账号和密码不为空
                    int i = Integer.parseInt(s.toString());
                    if (Integer.parseInt(s.toString()) > 30) {
                        Toast.makeText(myActivity, getString(R.string.text_record_setting_maximum) + "30", Toast.LENGTH_SHORT).show();
                        etTime.setText("30");
                    }else if (Integer.parseInt(s.toString()) == 0) {
                        Toast.makeText(myActivity, getString(R.string.text_record_time_nozero), Toast.LENGTH_SHORT).show();
                        etTime.setText("1");
                    }
                }
            }
        });

        //选择通道
        llCanWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wayDialog.show();//显示
            }
        });

        //清除TF卡内的数据
        llClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Tools.isFastClick()){
                    MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, myActivity.getResources().getString(R.string.text_tf_card_info_delete), new MessageDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm==true){//确定按钮
                                if(myApplication.isLinkState()){
                                    //永久触发
                                    String message = "AA FE 05 00 04 00 01 00 FF".replace(" ","");
                                    String xor = EncodingUtil.getBCC(EncodingUtil.hexToByte(message));
                                    message += xor;
                                    String sendMsg = message;
                                    exec.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.okSocketSend(sendMsg);
                                        }
                                    });
                                    Toast.makeText(myActivity,getString(R.string.text_tf_card_delete_sure),Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                                }
                            }
                            dialog.dismiss();//关闭弹出框
                        }
                    });
                    messageDialog.setTitle(myActivity.getResources().getString(R.string.text_warning));
                    messageDialog.show();//显示弹出框
                }
            }
        });
    }



/*
    *//**
     * 监听热点状态广播
     *//*
    class HotsspotReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.net.wifi.WIFI_AP_STATE_CHANGED")) {//便携式热点的状态为：10---正在关闭；11---已关闭；12---正在开启；13---已开启
                int state = intent.getIntExtra("wifi_state", 0);
                if (state == 11) {
                    Log.e("监听热点信息：","热点状态：已关闭");
                    myApplication.setVciIp(null);//清除VCI IP
                    myApplication.setBuzzing(false);//更改蜂鸣状态
                    Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                    finish();//关闭页面
                }
                if (state == 10) {
                    //正在关闭热点
                    *//*switchButton.setEnabled(false);//禁用永久触发按钮
                    llExport.setEnabled(false);//禁用导出按钮*//*
                }
            }
        }
    }*/
}
