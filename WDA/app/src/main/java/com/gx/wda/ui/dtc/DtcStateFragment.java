package com.gx.wda.ui.dtc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
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
import com.gx.wda.adapter.DtcQueryAdapter;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.bean.FaultCodeVo;
import com.gx.wda.dialog.FaultCodeInfoDialog;
import com.gx.wda.dialog.TopOptionDialog;
import com.gx.wda.ui.main.InformActivity;
import com.gx.wda.util.ImageUtil;
import com.gx.wda.util.KeyBoardUtil;
import com.gx.wda.util.MyWebSocketClient;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.CustomEditText;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

public class DtcStateFragment extends Fragment {
    private Activity myActivity;//上下文
    private int stateId;
    private LinearLayout llVehicle;//车辆
    private LinearLayout llConfigure;//配置
    private LinearLayout llModule;//模块
    private TextView tvVehicle;//车辆
    private TextView tvConfigure;//配置
    private TextView tvModule;//模块
    private ImageView ivVehicle;
    private ImageView ivConfigure;
    private ImageView ivModule;
    private CustomEditText etDtc;
    private Integer vehicleId=0;//车型ID
    private Integer configureId=0;//配置ID
    private Integer moduleId=0;//模块ID
    private ImageView ivSearch;//DTC搜索
    private TopOptionDialog vehicleDialog;//车型弹窗
    private TopOptionDialog configureDialog;//配置弹窗
    private TopOptionDialog moduleDialog;//模块弹窗
    private DtcQueryAdapter dtcQueryAdapter;//适配器
    private RecyclerView rvDtcQuery;//读取列表
    private LinearLayout llEmpty;//空数据
    private PromptDialog promptDialog;//加载框
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Integer userId;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity=(Activity) context;//设置上下文
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        //获取传递的参数 orderStateId
        Bundle bundle=getArguments();
        stateId = bundle.getInt("stateId");
        if (stateId==0){
            view=inflater.inflate(R.layout.fragment_dtc_query,container,false);
            etDtc=view.findViewById(R.id.et_dtc_query);
            ivSearch=view.findViewById(R.id.iv_dtc_query_search);
            rvDtcQuery=view.findViewById(R.id.rv_dtc_query_list);
            llEmpty=view.findViewById(R.id.ll_empty);
            etDtc.initKeyBoard(myActivity);//初始化编辑框键盘
            //============RecyclerView 初始化=========
            //===1、设置布局控制器
            //=1.1、创建布局管理器
            LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
            //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //=1.3、设置recyclerView的布局管理器
            rvDtcQuery.setLayoutManager(layoutManager);
            //==2、实例化适配器
            //=2.1、初始化适配器
            dtcQueryAdapter=new DtcQueryAdapter(myActivity);
            //=2.3、设置recyclerView的适配器
            rvDtcQuery.setAdapter(dtcQueryAdapter);
            //item点击事件
            dtcQueryAdapter.setOnItemClickListener(new DtcQueryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(FaultCodeVo data, Integer position) {
                    FaultCodeInfoDialog faultCodeInfoDialog=new FaultCodeInfoDialog(myActivity,data);
                    faultCodeInfoDialog.show(getFragmentManager(),"");
                }
            });
            setDtcQueryListListener();
        } else if (stateId==1){
            view=inflater.inflate(R.layout.fragment_dtc_read,container,false);
            setDtcReadListListener();
        }
        llVehicle=view.findViewById(R.id.ll_dtc_vehicle);
        tvVehicle=view.findViewById(R.id.tv_dtc_vehicle);
        ivVehicle=view.findViewById(R.id.iv_dtc_vehicle);
        llConfigure=view.findViewById(R.id.ll_dtc_configure);
        tvConfigure=view.findViewById(R.id.tv_dtc_configure);
        ivConfigure=view.findViewById(R.id.iv_dtc_configure);
        llModule=view.findViewById(R.id.ll_dtc_module);
        tvModule=view.findViewById(R.id.tv_dtc_module);
        ivModule=view.findViewById(R.id.iv_dtc_module);
        promptDialog=new PromptDialog(myActivity);//加载框实例化
        initView();
        setViewLineListener();
        return view;
    }
    /**
     * 初始化
     */
    private void initView(){
        vehicleDialog =new TopOptionDialog(myActivity,getResources().getString(R.string.text_dtc_vehicle),llVehicle,ivVehicle);
        configureDialog =new TopOptionDialog(myActivity,getResources().getString(R.string.text_dtc_configure),llConfigure,ivConfigure);
        moduleDialog =new TopOptionDialog(myActivity,getResources().getString(R.string.text_dtc_module),llModule,ivModule);
        userId= (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
    }
    /**
     * 公共事件
     */
    private void  setViewLineListener(){
        //车型
        llVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleDialog.show();//显示
                ivVehicle.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
        //配置
        llConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureDialog.show();
                ivConfigure.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
        //模块
        llModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moduleDialog.show();
                ivModule.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
    }
    /**
     * Dtc查询故障码监听事件
     */
    private void setDtcQueryListListener() {
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.hideKeyboard(v);
                loadQueryListData(true);//加载数据
            }
        });
        //点击软键盘中的搜索
        etDtc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyBoardUtil.hideKeyboard(v);
                    loadQueryListData(true);//加载数据
                    return true;
                }
                return false;
            }
        });
        //输入框点击事件
        etDtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDtc.setKeyBoard(myActivity);
            }
        });
    }
    /**
     * Dtc读取故障码监听事件
     */
    private void setDtcReadListListener() {

    }

    /**
     * 查询故障码页面列表数据
     * @param isSearch 是否点击按钮搜索,而不是通过下拉框改变搜索
     */
    private void loadQueryListData(boolean isSearch){
        if (isSearch){
            vehicleDialog.setData(null,null);//清空车辆信息
            configureDialog.setData(null,null);//清空模块信息
            moduleDialog.setData(null,null);//清空模块信息
            dtcQueryAdapter.addItem(null);//清空数据列表
            llEmpty.setVisibility(View.VISIBLE);//显示空数据
            rvDtcQuery.setVisibility(View.GONE);//隐藏数据列表
            resetData(true,true,true);
        }
        etDtc.clearFocus();//清除焦点
        String dtc=etDtc.getText().toString();
        if (!Tools.isNotNull(dtc)){
            Toast.makeText(myActivity, R.string.text_dtc_content,Toast.LENGTH_SHORT).show();
            return;
        }
        String url=ServiceUrls.getDtcMethodUrl("selectFaultCode");
        Map<String,Object> map=new ArrayMap<>();
        map.put("dtc",dtc);
        map.put("vehicleId",vehicleId);
        map.put("configureId",configureId);
        map.put("moduleId",moduleId);
        map.put("userId",userId);
        map.put("isVisit",isSearch);
        promptDialog.showLoading(getString(R.string.text_loading));//加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                if (code==200){
                                    String data=jsonObject.getString("data");
                                    JSONObject jsonData=new JSONObject(data);
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();//列表信息
                                    Type faultCodeType=new TypeToken<List<FaultCodeVo>>(){}.getType();//故障码信息
                                    List<AppendOptionVo> vehicles = gson.fromJson(jsonData.getString("vehicles"),type);
                                    List<AppendOptionVo> configurationLevels = gson.fromJson(jsonData.getString("configurationLevels"),type);
                                    List<AppendOptionVo> modules = gson.fromJson(jsonData.getString("modules"),type);
                                    List<FaultCodeVo> faultCodes = gson.fromJson(jsonData.getString("faultCodes"),faultCodeType);
                                    if (isSearch){
                                        vehicleDialog.setData(vehicles, new TopOptionDialog.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                tvVehicle.setText(appendOptionVo.getName());
                                                vehicleId=appendOptionVo.getId();
                                                //selectConfigureByDtc();//查询配置
                                                selectMoudleByDtc(0);//查询模块
                                                resetData(false,true,true);
                                                loadQueryListData(false);
                                                popupWindow.dismiss();//关闭
                                            }
                                        });
                                        configureDialog.setData(configurationLevels, new TopOptionDialog.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                tvConfigure.setText(appendOptionVo.getName());
                                                configureId=appendOptionVo.getId();
                                                resetData(false,false,true);
                                                loadQueryListData(false);
                                                popupWindow.dismiss();//关闭
                                            }
                                        });
                                        moduleDialog.setData(modules, new TopOptionDialog.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                tvModule.setText(appendOptionVo.getName());
                                                moduleId=appendOptionVo.getId();
                                                loadQueryListData(false);
                                                popupWindow.dismiss();
                                            }
                                        });
                                    }
                                    if (faultCodes.size()>0){
                                        llEmpty.setVisibility(View.GONE);//隐藏空数据
                                        rvDtcQuery.setVisibility(View.VISIBLE);//显示数据列表
                                        dtcQueryAdapter.addItem(faultCodes);//添加数据在列表
                                        Toast.makeText(myActivity, R.string.text_update_success,Toast.LENGTH_SHORT).show();
                                    }else {
                                        llEmpty.setVisibility(View.VISIBLE);//显示空数据
                                        rvDtcQuery.setVisibility(View.GONE);//隐藏数据列表
                                        Toast.makeText(myActivity, R.string.text_no_data,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismissImmediately();//关闭
                    }
                });
            }
        });
    }

    /**
     * 通过车型ID查询配置
     */
   /* private void selectConfigureByDtc(){
        String dtc=etDtc.getText().toString();
        String url=ServiceUrls.getDtcMethodUrl("selectConfigureByDtc");
        Map<String,Object> map=new HashMap<>();
        map.put("dtc",dtc);
        map.put("vehicleId",vehicleId);
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
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();//列表信息
                                    List<AppendOptionVo> list=gson.fromJson(data,type);
                                    configureDialog.setData(list, new TopOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            tvConfigure.setText(appendOptionVo.getName());
                                            configureId=appendOptionVo.getId();
                                            selectMoudleByDtc(configureId);//查询模块
                                            resetData(false,false,true);
                                            loadQueryListData(false);
                                            popupWindow.dismiss();//关闭
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }*/

    /**
     * 通过车型ID和配置ID查询模块
     */
    private void selectMoudleByDtc(int configureId){
        String dtc=etDtc.getText().toString();
        String url=ServiceUrls.getDtcMethodUrl("selectMoudleByDtc");
        Map<String,Object> map=new HashMap<>();
        map.put("dtc",dtc);
        map.put("vehicleId",vehicleId);
        map.put("configureId",configureId);
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
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();//列表信息
                                    List<AppendOptionVo> list=gson.fromJson(data,type);
                                    moduleDialog.setData(list, new TopOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            tvModule.setText(appendOptionVo.getName());
                                            moduleId=appendOptionVo.getId();
                                            loadQueryListData(false);
                                            popupWindow.dismiss();
                                        }
                                    });
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
    /**
     * 重置数据
     * @param isVehicle 车型
     * @param isConfiguration 配置等级
     * @param isModule 模块
     */
    private void resetData(boolean isVehicle,boolean isConfiguration, boolean isModule){
        if (isVehicle){
            tvVehicle.setText(R.string.text_dtc_vehicle);//重置
            vehicleId=0;//重置配置等级ID
            vehicleDialog.cleanOption();//清除选项
        }
        if (isConfiguration){
            tvConfigure.setText(R.string.text_dtc_configure);//重置
            configureId=0;//重置模块ID
            configureDialog.cleanOption();//清除选项
        }
        if (isModule){
            tvModule.setText(R.string.text_dtc_module);//重新
            moduleId=0;//重置供应商ID
            moduleDialog.cleanOption();//清除选项
        }
    }
}
