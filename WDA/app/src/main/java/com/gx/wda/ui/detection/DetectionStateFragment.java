package com.gx.wda.ui.detection;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.R;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.dialog.TopOptionDialog;
import com.gx.wda.util.ImageUtil;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.ServiceUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 状态切换页面
 */
public class DetectionStateFragment extends Fragment {
    private Activity myActivity;//上下文
    private int stateId;//状态id
    private LinearLayout llVehicle;//车辆
    private LinearLayout llConfigure;//配置
    private LinearLayout llModule;//模块
    private LinearLayout llDid;//did列表
    private LinearLayout llExport;//导出
    private LinearLayout llSearch;//查询
    private LinearLayout llDetection;//检测
    private TextView tvVehicle;//车辆
    private TextView tvConfigure;//配置
    private TextView tvModule;//模块
    private ImageView ivVehicle;
    private ImageView ivConfigure;
    private ImageView ivModule;

    private TopOptionDialog optionDialog1;
    private TopOptionDialog optionDialog2;
    private TopOptionDialog optionDialog3;
    private TopOptionDialog optionDialog4;
    private LinearLayout llSelectCarType;
    private LinearLayout llSelectConfigure;
    private LinearLayout llSelectDidScreen;
    private LinearLayout llSelectModule;
    private TextView tvSelectCarType;
    private TextView tvSelectConfigure;
    private TextView tvSelectDidScreen;
    private TextView tvSelectModule;
    private ImageView ivSelectCarType;
    private ImageView ivSelectConfigure;
    private ImageView ivSelectDidScreen;
    private ImageView ivSelectModule;


    private TopOptionDialog vehicleDialog;//车型弹窗
    private TopOptionDialog configureDialog;//配置弹窗
    private TopOptionDialog moduleDialog;//模块弹窗
    private List<AppendOptionVo> vehicleList;//车型列表
    private List<AppendOptionVo> configureList;//配置列表
    private List<AppendOptionVo> moduleList;//模块列表
    private PromptDialog promptDialog;//加载框
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity)context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        //获取传递的参数 stateId
        Bundle bundle=getArguments();
        stateId = bundle.getInt("stateId");
        if (stateId==0){//DID解析
            //DID解析
            view=inflater.inflate(R.layout.fragment_did_analysis,container,false);
            //4个下拉框
            llSelectCarType = view.findViewById(R.id.ll_select_cartype);
            llSelectConfigure = view.findViewById(R.id.ll_select_configure);
            llSelectDidScreen = view.findViewById(R.id.ll_select_did_screen);
            llSelectModule = view.findViewById(R.id.ll_select_module);
            //下拉框内容
            tvSelectCarType = view.findViewById(R.id.tv_select_cartype);
            tvSelectConfigure = view.findViewById(R.id.tv_select_configure);
            tvSelectDidScreen = view.findViewById(R.id.tv_select_did_screen);
            tvSelectModule = view.findViewById(R.id.tv_select_module);
            //下拉框图标
            ivSelectCarType = view.findViewById(R.id.iv_select_cartype);
            ivSelectConfigure = view.findViewById(R.id.iv_select_configure);
            ivSelectDidScreen = view.findViewById(R.id.iv_select_did_screen);
            ivSelectModule = view.findViewById(R.id.iv_select_module);


            setViewAnalysis();
        }else if (stateId==1){//ECU版本
            view=inflater.inflate(R.layout.fragment_detection_ecu_version,container,false);
            llModule=view.findViewById(R.id.ll_detection_module);
            tvModule=view.findViewById(R.id.tv_detection_module);
            ivModule=view.findViewById(R.id.iv_detection_module);
            llDid=view.findViewById(R.id.ll_detection_ecu_did);
            llExport=view.findViewById(R.id.ll_detection_ecu_export);
            llSearch=view.findViewById(R.id.ll_detection_ecu_search);
            setDidListListener();
        } else if (stateId==2){//节点在线
            view=inflater.inflate(R.layout.fragment_detection_node_online,container,false);
            llDetection=view.findViewById(R.id.ll_detection_node_detection);
            llVehicle=view.findViewById(R.id.ll_detection_vehicle);
            llConfigure=view.findViewById(R.id.ll_detection_configure);
            tvVehicle=view.findViewById(R.id.tv_detection_vehicle);
            tvConfigure=view.findViewById(R.id.tv_detection_configure);
            ivVehicle=view.findViewById(R.id.iv_detection_vehicle);
            ivConfigure=view.findViewById(R.id.iv_detection_configure);
            setNodeOnLineListener();
        }

        promptDialog=new PromptDialog(myActivity);//加载框实例化
        initView();
       // setViewLineListener();
        loadData();
        return view;
    }
    /**
     * 初始化
     */
    private void initView(){
        vehicleDialog =new TopOptionDialog(myActivity,getResources().getString(R.string.text_detection_vehicle),llVehicle,ivVehicle);
        configureDialog =new TopOptionDialog(myActivity,getResources().getString(R.string.text_detection_configure),llConfigure,ivConfigure);
        moduleDialog =new TopOptionDialog(myActivity,getResources().getString(R.string.text_detection_module),llModule,ivModule);

    }





    /**
     * 公共事件
     */
    private void  setViewLineListener(){
        //车型
        llVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicleList==null){//第一次加载请求服务器
                    //获取车型信息
                    String url= ServiceUrls.getCommonMethodUrl("selectCarTypeAll");
                    Map<String,Object> map=new HashMap<>();
                    promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
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
                                            String data=jsonObject.getString("data");
                                            if (code==200){
                                                Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                                vehicleList = gson.fromJson(data,type);
                                                vehicleDialog.setData(vehicleList, new TopOptionDialog.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                        tvVehicle.setText(appendOptionVo.getName());
                                                        popupWindow.dismiss();//关闭
                                                    }
                                                });
                                                vehicleDialog.show();//显示
                                                ivVehicle.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    promptDialog.dismissImmediately();
                                }
                            });
                        }
                    });
                }else {
                    vehicleDialog.setData(vehicleList, new TopOptionDialog.OnItemClickListener() {
                        @Override
                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                            tvVehicle.setText(appendOptionVo.getName());
                            popupWindow.dismiss();//关闭
                        }
                    });
                    vehicleDialog.show();//显示
                    ivVehicle.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
                }
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
    }
    /**
     * DID监听事件
     */
    private void setDidListListener() {
        //模块
        llModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moduleDialog.show();
                ivModule.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
        //DID
        llDid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //导出
        llExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //搜索
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    /**
     * 节点在线监听事件
     */
    private void setNodeOnLineListener() {
        //检测
        llDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    /**
     * 加载数据
     */
    private void loadData(){
        final List<AppendOptionVo> list=new ArrayList<>();
        AppendOptionVo appendOptionVo;
        for (int i=0;i<10;i++){
            appendOptionVo=new AppendOptionVo();
            appendOptionVo.setId(i);
            appendOptionVo.setName("选项"+i);
            list.add(appendOptionVo);
        }
        optionDialog1 =new TopOptionDialog(myActivity,getResources().getString(R.string.text_sharemain_flow_model),llSelectCarType,ivSelectCarType);
        optionDialog2 =new TopOptionDialog(myActivity,getResources().getString(R.string.text_datastream_did_configure),llSelectConfigure,ivSelectConfigure);
        optionDialog3 =new TopOptionDialog(myActivity,getResources().getString(R.string.text_datastream_did_module),llSelectModule,ivSelectModule);
        optionDialog4 =new TopOptionDialog(myActivity,getResources().getString(R.string.text_datastream_did_screen),llSelectDidScreen,ivSelectDidScreen);
    }

    /**
     * DID解析
     */
    private void setViewAnalysis() {
        //车型
        llSelectCarType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionDialog1.show();//显示
                ivSelectCarType.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
        //配置
        llSelectConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionDialog2.show();
                ivSelectConfigure.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
        //模块
        llSelectModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionDialog3.show();
                ivSelectModule.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
        //筛选DID
        llSelectDidScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionDialog4.show();
                ivSelectDidScreen.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_down,180));//设置180度的旋转图片
            }
        });
    }
}
