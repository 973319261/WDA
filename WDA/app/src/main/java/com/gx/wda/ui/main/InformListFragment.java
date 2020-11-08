package com.gx.wda.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.R;
import com.gx.wda.adapter.InformAdapter;
import com.gx.wda.bean.InformVo;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformListFragment extends Fragment {
    private Activity myActivity;
    //控件
    private RecyclerView rvInformList;//列表
    private InformAdapter informAdapter;//适配器
    private LinearLayout llEmpty;//数据为空图片
    private Integer state;//状态
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Integer userId;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inform_list,container,false);
        rvInformList = view.findViewById(R.id.rv_inform_list);
        llEmpty=view.findViewById(R.id.ll_inform_empty);
        Bundle bundle=getArguments();
        if (bundle!=null){
            userId= (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
            state=bundle.getInt("state");
            //============RecyclerView 初始化=========
            //===1、设置布局控制器
            //=1.1、创建布局管理器
            LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
            //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //=1.3、设置recyclerView的布局管理器
            rvInformList.setLayoutManager(layoutManager);
            //==2、实例化适配器
            //=2.1、初始化适配器
            informAdapter=new InformAdapter(myActivity);
            //=2.3、设置recyclerView的适配器
            rvInformList.setAdapter(informAdapter);
            if (state==0){
                loadListData("selectInformByUserId");
            }else {
                loadListData("selectFileInformByUserId");
            }
            setViewListener();
        }
        return view;
    }
    /**
     * 监听事件
     */
    private void setViewListener() {

    }
    /**
     * 加载通知信息
     */
    private void loadListData(String urlName) {
        String url= ServiceUrls.getShareMethodUrl(urlName);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
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
                                    Type type=new TypeToken<List<InformVo>>(){}.getType();
                                    List<InformVo> list = gson.fromJson(data,type);
                                    informAdapter.addItem(list);
                                    if (list!=null && list.size()>0){
                                        llEmpty.setVisibility(View.GONE);
                                        rvInformList.setVisibility(View.VISIBLE);
                                    }else {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        rvInformList.setVisibility(View.GONE);
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
