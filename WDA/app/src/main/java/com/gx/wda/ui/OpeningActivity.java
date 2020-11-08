package com.gx.wda.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.request.RequestOptions;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.util.LanguageUtil;
import com.gx.wda.util.MPermissionUtils;
import com.gx.wda.util.StatusBarUtil;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

/**
 * 开屏页面
 */
public class OpeningActivity extends AppCompatActivity {
    private ImageView ivSplash;
    private RequestOptions backgroundRO = new RequestOptions().centerInside();
    private MyApplication myApplication;
    private Activity myActivity;
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = OpeningActivity.this;
        myApplication = (MyApplication) getApplication();
        if (!LanguageUtil.isSetting(myActivity)) {//未设置
            Locale locale = LanguageUtil.getAppLocale(this);//获取本地的语言
            LanguageUtil.changeAppLanguage(this,locale,true);//设置语言
        }
        setContentView(R.layout.activity_opening);
        initView();

    }
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        MPermissionUtils.requestPermissionsResult(myActivity,
                REQUEST_EXTERNAL_STORAGE,
                PERMISSIONS_STORAGE,
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
                                    finish();
                                    return;
                                }
                                Intent intent2 = new Intent();
                                intent2.setClass(OpeningActivity.this, LoginActivity.class);
                                startActivity(intent2);
                                finish();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onPermissionDenied() {
                        Intent intent2 = new Intent();
                        intent2.setClass(OpeningActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        finish();

                    }
                });
    }


    //权限请求的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {

    }
}
