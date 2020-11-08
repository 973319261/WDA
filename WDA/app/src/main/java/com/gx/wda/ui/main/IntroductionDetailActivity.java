package com.gx.wda.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gx.wda.R;
import com.gx.wda.adapter.VersionRecordAdapter;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.MyActionBar;

/**
 * 功能明细
 */
public class IntroductionDetailActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private TextView tvContent;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;
        setContentView(R.layout.activity_introduction_detail);
        tvContent=findViewById(R.id.tv_version_content);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_function_detail_title), R.drawable.ic_back,0,
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
        setViewListener();//监听事件
    }
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        String content = getIntent().getStringExtra("content");//更新内容
        tvContent.setText(Html.fromHtml(content));
    }
    private void setViewListener(){

    }
}
