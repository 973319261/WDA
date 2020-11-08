package com.gx.wda.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.R;
import com.gx.wda.bean.InformVo;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.BadgeHelper;
import com.gx.wda.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 通知明细信息
 */
public class InformDetailActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private TextView tvTitle;//标题
    private TextView tvContent;//内容
    private TextView tvDate;//时间
    private int informId;//通知ID
    private Integer userId;//用户ID
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        setContentView(R.layout.activity_inform_detail);
        tvTitle=findViewById(R.id.tv_inform_title);
        tvContent=findViewById(R.id.tv_inform_content);
        tvDate=findViewById(R.id.tv_inform_date);
        myActivity=this;
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_inform_detail_title), R.drawable.ic_back, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
    }
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userId= (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        //得到传递过来的参数
        informId = getIntent().getIntExtra("informId",0);
        if (informId>0){
            String url= ServiceUrls.getShareMethodUrl("selectInformById");
            Map<String,Object> map=new HashMap<>();
            map.put("informId",informId);
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
                                        Type type=new TypeToken<InformVo>(){}.getType();
                                        InformVo informVo = gson.fromJson(data,type);
                                        if (informVo!=null){
                                            tvTitle.setText(informVo.getInformTitle());
                                            tvContent.setText(informVo.getInformContent());
                                            tvDate.setText(sf.format(informVo.getCreationTime()));
                                        }
                                        //修改公告通知是否已读状态
                                        updateInformState();
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
    /**
     * 修改公告通知是否已读状态
     */
    private void updateInformState(){
        String url= ServiceUrls.getShareMethodUrl("updateInformState");
        Map<String,Object> map=new HashMap<>();
        map.put("type",0);
        map.put("userId",userId);
        map.put("id",informId);
        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
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
