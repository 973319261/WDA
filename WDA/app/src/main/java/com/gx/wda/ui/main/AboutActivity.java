package com.gx.wda.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.Version;
import com.gx.wda.dialog.VersionUpdateDialog;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.BadgeHelper;
import com.gx.wda.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 关于页面
 */
public class AboutActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private TextView tvVersion;//版本信息
    private TextView redPoint;//版本提示
    private LinearLayout llIntroduction;//功能介绍
    private LinearLayout llCheckver;//检查新版本
    private PromptDialog promptDialog;//加载框
    private VersionUpdateDialog versionUpdateDialog;//更新弹窗
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;
        setContentView(R.layout.activity_about);
        myApplication= (MyApplication) myActivity.getApplication();
        tvVersion=findViewById(R.id.tv_version);
        llIntroduction=findViewById(R.id.ll_introduction);
        llCheckver=findViewById(R.id.ll_checkver);
        redPoint=findViewById(R.id.tv_red_point);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_about_title), R.drawable.ic_back,0,
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
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        tvVersion.setText(String.format(Locale.getDefault(),"Version %s",Tools.getVersionName(myActivity)));//设置版本信息
        promptDialog = new PromptDialog(myActivity);
        //版本红点提示
        boolean flag= (boolean) SPUtils.get(myActivity,SPUtils.SP_VERSION_NEW, false);
        BadgeHelper badgeHelper = new BadgeHelper(myActivity);
        badgeHelper.setBadgeType(BadgeHelper.Type.TYPE_POINT)
                .setBadgeOverlap(false)
                .bindToTargetView(redPoint);
        badgeHelper.setBadgeEnable(flag);
    }

    /**
     * 监听事件
     */
    private void setViewListener(){
        //功能介绍
        llIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,FunctionIntroductionActivity.class);
                startActivity(intent);
            }
        });
        //检查新版本
        llCheckver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
                String url= ServiceUrls.getVersionMethodUrl("getVersionInfo");
                Map<String,Object> map=new HashMap<>();
                OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode==200){
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        int code=jsonObject.getInt("code");
                                        if (code==200){
                                            String data=jsonObject.getString("data");
                                            Type type=new TypeToken<Version>(){}.getType();
                                            Version version=gson.fromJson(data,type);
                                            //判断是否需要更新
                                            if (Integer.valueOf(version.getVersionNumber())> Tools.getVersionCode(myActivity)){
                                                versionUpdateDialog=new VersionUpdateDialog(myActivity,version);
                                                versionUpdateDialog.show(getSupportFragmentManager(),"");
                                            }else {
                                                Toast.makeText(myActivity, R.string.text_newest_version,Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                promptDialog.dismiss();//关闭加载框
                            }
                        });
                    }
                });
            }
        });
    }

}
