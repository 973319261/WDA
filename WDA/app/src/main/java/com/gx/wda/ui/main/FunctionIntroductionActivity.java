package com.gx.wda.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.R;
import com.gx.wda.adapter.VersionRecordAdapter;
import com.gx.wda.bean.Version;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能介绍
 */
public class FunctionIntroductionActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private VersionRecordAdapter versionRecordAdapter;//适配器
    private LinearLayout llEmpty;
    private RecyclerView rvVersionList;//列表
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;
        setContentView(R.layout.activity_function_introduction);
        rvVersionList=findViewById(R.id.rv_version_list);
        llEmpty=findViewById(R.id.ll_empty);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_about_function_title), R.drawable.ic_back,0,
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
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvVersionList.setLayoutManager(layoutManager);

        //==2、实例化适配器
        //=2.1、初始化适配器
        versionRecordAdapter=new VersionRecordAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvVersionList.setAdapter(versionRecordAdapter);
        loadListData();
    }
    private void setViewListener(){

    }

    /**
     * 加载版本信息
     */
    private void loadListData() {
        String url = ServiceUrls.getVersionMethodUrl("getAllVersionInfo");
        Map<String, Object> map = new HashMap<>();
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    String data = jsonObject.getString("data");
                                    Type type = new TypeToken<List<Version>>() {
                                    }.getType();
                                    List<Version> list = gson.fromJson(data, type);
                                    versionRecordAdapter.addItem(list);
                                    if (list != null && list.size() > 0) {
                                        llEmpty.setVisibility(View.GONE);
                                        rvVersionList.setVisibility(View.VISIBLE);
                                    } else {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        rvVersionList.setVisibility(View.GONE);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}
