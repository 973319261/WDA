package com.gx.wda.ui.dataStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.MyActionBar;

/**
 * 流控贞管理
 */
public class FlowControlActvity extends AppCompatActivity {
    private Activity myActivity;
    private MyActionBar myActionBar;//标题栏
    private MyApplication myApplication;//全局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_flow_control_frame);
        myApplication= (MyApplication) myActivity.getApplication();
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_setting), R.drawable.ic_back, 0, 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                setResult(2,(new Intent()));
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
    }


   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myApplication.isDataStream()){
            myApplication.setVciRun(true);
        }
    }*/
}
