package com.gx.wda.ui.dataStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.dialog.BottomOptionDialog;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.TFTPExample;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.MyActionBar;

import java.io.IOException;

import me.leefeng.promptlibrary.PromptDialog;

public class SettingRecordActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局

    private MyActionBar myActionBar;//标题栏
    private BottomOptionDialog selectDialog;
    private EditText etCount;
    private EditText etTime;
    private Button btnSave;
    private PromptDialog promptDialog;//加载框


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;//设置上下文
        myApplication= (MyApplication) myActivity.getApplication();
        setContentView(R.layout.activity_message_setting);

        promptDialog = new PromptDialog(myActivity);
        etCount = findViewById(R.id.et_tfcard_setting_count);
        etTime = findViewById(R.id.et_tfcard_setting_time);
        btnSave = findViewById(R.id.btn_save_setting);


        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_record_setting), R.drawable.ic_back,getResources().getString(R.string.text_datastream_message_export),
                1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                if(!Tools.isFastClick()) {

                    if(!myApplication.isLinkState()){
                        Toast.makeText(myActivity,"还未与VCI建立连接哦",Toast.LENGTH_SHORT).show();
                    }else {
                        String vciIP = myApplication.getVciIp();
                        String triggerStart =  (String) SPUtils.get(myActivity,SPUtils.SP_TRIGGER_START,"");//获取上次软触发时间+
                        if(Tools.isNotNull(triggerStart)){
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
                                                    //进入导出列表
                                                    Intent intent = new Intent(myActivity,ExportTriggerActicity.class);
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
                        }else{
                            Toast.makeText(myActivity,"还未使用过软触发哦",Toast.LENGTH_SHORT).show();
                            promptDialog.dismiss();
                        }
                    }
                }

            }
        });
        initView();//初始化页面
        setViewListener();//设置监听事件
    }

    private void setViewListener() {
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



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strCount = etCount.getText().toString();
                String strTime = etTime.getText().toString();
                if(Tools.isNotNull(strCount)&&Tools.isNotNull(strTime)){
                    if (Integer.parseInt(strCount) > 2000){
                        Toast.makeText(myActivity, getString(R.string.text_record_setting_maximum) + "2000", Toast.LENGTH_SHORT).show();
                    }else if(Integer.parseInt(strTime) > 30){
                        Toast.makeText(myActivity, getString(R.string.text_record_setting_maximum) + "30", Toast.LENGTH_SHORT).show();
                    }else{
                        SPUtils.put(myActivity,SPUtils.SP_TEMP_TRIGGER_COUNT,strCount);
                        SPUtils.put(myActivity,SPUtils.SP_TEMP_TRIGGER_TIMER,strTime);
                        finish();
                    }

                }else{
                    Toast.makeText(myActivity, getString(R.string.text_record_setting_full), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        //selectDialog = new BottomOptionDialog(myActivity,getString(R.string.text_select_map_aisle));
        //回填保存的参数
        String SAVE_COUNT = (String) SPUtils.get(myActivity,SPUtils.SP_TEMP_TRIGGER_COUNT,"");//获取软触发保留条数
        String SAVE_TIMER = (String) SPUtils.get(myActivity,SPUtils.SP_TEMP_TRIGGER_TIMER,"");//获取软触发录制时间
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
    }


}
