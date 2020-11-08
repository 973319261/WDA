package com.gx.wda.ui.security;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.R;
import com.gx.wda.adapter.SecurityRecordAdapter;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.bean.SecurityRecordVo;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.SecurityRecordUtil;
import com.gx.wda.util.Tools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 安全算法页面
 */
public class SecurityFragment extends Fragment {
    private Activity myActivity;//上下文
    private LinearLayout llSecuritySk;//Seed→Key
    private LinearLayout llSecurityVp;//Vin→Pin
    private LinearLayout llSecurityVe;//Vin→Esk
    private LinearLayout llSecuritySpk;//Seed＆Pin→Key
    private SmartRefreshLayout srlSecurityRecord;//刷新
    private SecurityRecordAdapter securityRecordAdapter;//适配器
    private RecyclerView rvSecurityRecord;//列表
    private List<SecurityRecordVo> securityRecordList;//安全算法历史记录
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_security,container,false);
        llSecuritySk=view.findViewById(R.id.ll_security_sk);
        llSecurityVp=view.findViewById(R.id.ll_security_vp);
        llSecurityVe=view.findViewById(R.id.ll_security_ve);
        llSecuritySpk=view.findViewById(R.id.ll_security_spk);
        srlSecurityRecord=view.findViewById(R.id.srl_security_record_list);
        rvSecurityRecord=view.findViewById(R.id.rv_security_record_list);
        initView();//初始化
        setViewListener();//监听事件
        return view;
    }
    /**
     * 初始化页面
     */
    private void initView() {
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvSecurityRecord.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        securityRecordAdapter=new SecurityRecordAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvSecurityRecord.setAdapter(securityRecordAdapter);
        securityRecordList= SecurityRecordUtil.getSecurityRecord(myActivity);//获取本地安全算法记录
        securityRecordAdapter.addItem(securityRecordList);
        srlSecurityRecord.setEnableLoadMore(false);//关闭上拉刷新


    }


    /*监听事件*/
    private void setViewListener() {
        //Seed→Key
        llSecuritySk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    Intent intent=new Intent(myActivity, SeedKeyActivity.class);
                    startActivity(intent);
                }
            }
        });
        //Vin→Pin
        llSecurityVp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    Intent intent=new Intent(myActivity,VinPinActivity.class);
                    startActivity(intent);
                }
            }
        });
        //Vin→Esk
        llSecurityVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    Intent intent=new Intent(myActivity, VinEskActivity.class);
                    startActivity(intent);
                }
            }
        });
        ////Seed＆Pin→Key
        llSecuritySpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    Intent intent=new Intent(myActivity,SeedPinKeyActivity.class);
                    startActivity(intent);
                }
            }
        });
        //下拉刷新
        srlSecurityRecord.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                securityRecordList=SecurityRecordUtil.getSecurityRecord(myActivity);//获取本地安全算法记录
                securityRecordAdapter.addItem(securityRecordList);
                srlSecurityRecord.finishRefresh();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        securityRecordList= SecurityRecordUtil.getSecurityRecord(myActivity);//获取本地安全算法记录
        securityRecordAdapter.addItem(securityRecordList);
    }
}
