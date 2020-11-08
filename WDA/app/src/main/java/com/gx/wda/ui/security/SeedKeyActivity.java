package com.gx.wda.ui.security;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.bean.SecurityRecordVo;
import com.gx.wda.bean.UserVo;
import com.gx.wda.dialog.BottomOptionDialog;
import com.gx.wda.dialog.KeyBoardDialog;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.SecurityRecordUtil;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.CustomEditText;
import com.gx.wda.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * Seed转Key页面
 */
public class SeedKeyActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private TextView tvVehicle;//车辆
    private TextView tvConfiguration;//配置等级
    private TextView tvEcu;//Ecu
    private TextView tvSupplier;//供应商
    private TextView tvLevel;//等级
    private LinearLayout llVehicle;//车辆
    private LinearLayout llConfiguration;//配置等级
    private LinearLayout llEcu;//Ecu
    private LinearLayout llSupplier;//供应商
    private LinearLayout llLevel;//等级
    private LinearLayout llOption;//选项列表
    private CustomEditText etSeed;//输入Seed
    private TextView tvKey;//输入Key
    private int vehicleId;//车型ID
    private int configurationLevelId;//配置等级ID
    private int moduleId;//模块ID
    private int supplierId = 0;//供应商ID
    private int arithmeticLevelId = 0;//算法等级ID
    private Button btnCalculate;//计算
    private BottomOptionDialog vehicleDialog;//车型弹出框
    private BottomOptionDialog configurationDialog;//配置等级弹出框
    private BottomOptionDialog ecuDialog;//模块弹出框
    private BottomOptionDialog supplierDialog;//供应商弹出框
    private BottomOptionDialog levelDialog;//等级弹出框
    private List<AppendOptionVo> vehicleList=null;//车型列表
    private PromptDialog promptDialog;//加载框
    private Integer userId;//用户ID
    private UserVo user;
    private Boolean isHistory;//判断是否为历史记录进来的

    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;
        setContentView(R.layout.activity_seed_key);
        tvVehicle=findViewById(R.id.tv_security_vehicle);
        tvConfiguration=findViewById(R.id.tv_security_configuration);
        tvEcu=findViewById(R.id.tv_security_ecu);
        tvSupplier=findViewById(R.id.tv_security_supplier);
        tvLevel=findViewById(R.id.tv_security_level);
        llVehicle=findViewById(R.id.ll_security_vehicle);
        llConfiguration=findViewById(R.id.ll_security_configuration);
        llEcu=findViewById(R.id.ll_security_ecu);
        llSupplier=findViewById(R.id.ll_security_supplier);
        llLevel=findViewById(R.id.ll_security_level);
        llOption=findViewById(R.id.ll_security_option);
        etSeed=findViewById(R.id.et_security_seed);
        tvKey=findViewById(R.id.tv_security_key);
        btnCalculate=findViewById(R.id.btn_calculate);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_security_seed_key_title), R.drawable.ic_back,0,
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
        isHistory = getIntent().getBooleanExtra("isHistory",false);//判断是否为历史记录进来的
        if (isHistory){//历史记录进来的
            String securityRecordStr = getIntent().getStringExtra("securityRecordVo");//获取历史记录
            Type type=new TypeToken<SecurityRecordVo>(){}.getType();
            SecurityRecordVo securityRecordVo=gson.fromJson(securityRecordStr,type);
            vehicleId=securityRecordVo.getVehicleId();//设置车型ID
            configurationLevelId=securityRecordVo.getConfigurationLevelId();//配置等级ID
            moduleId=securityRecordVo.getModuleId();//模块等级ID
            supplierId=securityRecordVo.getSupplierId();//设置供应商ID
            arithmeticLevelId=securityRecordVo.getArithmeticLevelId();//设置等级ID
            llOption.setVisibility(View.GONE);//隐藏
        }
        etSeed.initKeyBoard(myActivity);//初始化编辑框键盘
        vehicleDialog =new BottomOptionDialog(myActivity,getResources().getString(R.string.text_detection_vehicle));
        configurationDialog=new BottomOptionDialog(myActivity, getString(R.string.text_security_configuration));
        ecuDialog=new BottomOptionDialog(myActivity, getResources().getString(R.string.text_security_ecu));
        supplierDialog=new BottomOptionDialog(myActivity, getResources().getString(R.string.text_security_supplier));
        levelDialog=new BottomOptionDialog(myActivity, getResources().getString(R.string.text_security_level));
        promptDialog=new PromptDialog(myActivity);//加载框实例化
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取本地用户ID
        String userStr= (String) SPUtils.get(myActivity,SPUtils.SP_USER,"");
        Type type=new TypeToken<UserVo>(){}.getType();
        user=gson.fromJson(userStr,type);
    }



    /**
     * 监听事件
     */
    private void setViewListener(){
        //车辆
        llVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                closeKeyBoard();//关闭虚拟键盘
            if (vehicleList==null){
                //获取车型信息
                String url= ServiceUrls.getSecurityMethodUrl("selectVehicleRelateArithmetic");
                Map<String,Object> map=new HashMap<>();
                map.put("roleId",user.getRoleId());//职务类型
                map.put("algorithmType",1);//算法类型 1
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
                                            vehicleDialog.setData(vehicleList, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    tvVehicle.setText(appendOptionVo.getName());//设置车辆文本
                                                    vehicleId=appendOptionVo.getId();//车型ID
                                                   // selectConfigurationRelateArithmetic();//查询配置
                                                    selectModuleRelateArithmetic();//查询模块
                                                    popupWindow.dismiss();//关闭弹窗
                                                }
                                            });
                                            vehicleDialog.show();//显示
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

            }else {
                vehicleDialog.setData(vehicleList, new BottomOptionDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                        tvVehicle.setText(appendOptionVo.getName());//设置车辆文本
                        vehicleId=appendOptionVo.getId();//车型ID
                       // selectConfigurationRelateArithmetic();//查询配置
                        selectModuleRelateArithmetic();//查询模块
                        popupWindow.dismiss();//关闭弹窗
                    }
                });
                vehicleDialog.show();//显示
            }
            }
        });
        //配置等级
        llConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();//关闭虚拟键盘
                configurationDialog.show();
            }
        });
        //ECU
        llEcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();//关闭虚拟键盘
                ecuDialog.show();
            }
        });
        //供应商
        llSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();//关闭虚拟键盘
                supplierDialog.show();
            }
        });
        //等级
        llLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();//关闭虚拟键盘
                levelDialog.show();
            }
        });
        //计算
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();//关闭虚拟键盘
                String inputValue = etSeed.getText().toString();//获取Seed值
                if (Tools.isHexadecimal(inputValue)){
                    String url=ServiceUrls.getSecurityMethodUrl("calculateByDll");
                    Map<String,Object> map=new HashMap<>();
                    map.put("inputValue",inputValue);//输入值
                    map.put("vehicleId",vehicleId);//车型ID
                    map.put("configurationLevelId",0);//配置等级ID
                    map.put("moduleId",moduleId);//模块ID
                    map.put("supplierId",supplierId);//供应商ID
                    map.put("arithmeticLevelId",arithmeticLevelId);//等级ID
                    map.put("userId",userId);//用户ID
                    map.put("typeId",1);//类型
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
                                            if (code==200){
                                                String data=jsonObject.getString("data");
                                                JSONObject jsonData=new JSONObject(data);//返回信息
                                                String result=jsonData.getString("result");//返回计算值
                                                if (!isHistory){//不是历史记录进来的
                                                    String securityRecordVoStr=jsonData.getString("securityRecordVo");//安全算法记录信息
                                                    Type type=new TypeToken<SecurityRecordVo>(){}.getType();//转换类型
                                                    SecurityRecordVo securityRecordVo=gson.fromJson(securityRecordVoStr,type);
                                                    securityRecordVo.setTypeId(1);//设置类型ID
                                                    securityRecordVo.setTypeName(getResources().getString(R.string.text_security_seed_key_title));//设置类型
                                                    SecurityRecordUtil.setSecurityRecord(myActivity,securityRecordVo);//保存记录记录到本地
                                                }
                                                tvKey.setText(result);//设置返回值
                                                btnCalculate.setText(R.string.text_security_calculate_success);
                                                btnCalculate.setEnabled(false);//禁止计算
                                            }else if (code==500){//暂无算法
                                                Toast.makeText(myActivity, R.string.text_no_algorithm,Toast.LENGTH_SHORT).show();
                                            }else if (code==501){//计算失败
                                                Toast.makeText(myActivity, R.string.text_calculate_failure,Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    promptDialog.dismiss();
                                }
                            });
                        }
                    });
                }else {
                    Toast.makeText(myActivity,R.string.text_input_error,Toast.LENGTH_SHORT).show();
                }
            }
        });

        //输入框点击事件
        etSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSeed.setKeyBoard(myActivity);
            }
        });
        //输入框改变
        etSeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (arithmeticLevelId>0 && s.length()==8){//8位
                    btnCalculate.setEnabled(true);//启用按钮
                }else {
                    btnCalculate.setEnabled(false);//禁用按钮
                }
                tvKey.setText("");//清空输出key
                btnCalculate.setText(R.string.text_security_calculate);//计算
            }
        });
        //输出框长按事件
        tvKey.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String key=tvKey.getText().toString();
                if (Tools.isNotNull(key)){
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Label",key );
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(myActivity,R.string.text_valuecopy,Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }
   /* *//**
     * 通过车型ID查询配置
     *//*
    private void selectConfigurationRelateArithmetic(){
        resetData(true,true,true,true);//重置数据
        String url= ServiceUrls.getSecurityMethodUrl("selectConfigurationRelateArithmetic");
        Map<String,Object> map=new HashMap<>();
        map.put("vehicleId",vehicleId);//车型ID
        map.put("algorithmType",1);//算法类型 1
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    List<AppendOptionVo> list = gson.fromJson(data,type);
                                    configurationDialog.setData(list, new BottomOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            tvConfiguration.setText(appendOptionVo.getName());
                                            configurationLevelId=appendOptionVo.getId();//配置等级ID
                                            selectModuleRelateArithmetic();//查询配置等级
                                            popupWindow.dismiss();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismiss();
                    }
                });
            }
        });
    }*/
    /**
     * 通过车型ID和配置等级ID查询模块
     */
    private void selectModuleRelateArithmetic(){
        resetData(false,true,true,true);//重置数据
        String url= ServiceUrls.getSecurityMethodUrl("selectModuleRelateArithmetic");
        Map<String,Object> map=new HashMap<>();
        map.put("vehicleId",vehicleId);//车型ID
        map.put("configurationLevelId",0);//配置等级ID
        map.put("roleId",user.getRoleId());//配置等级ID
        map.put("algorithmType",1);//算法类型 1
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    List<AppendOptionVo> list = gson.fromJson(data,type);
                                    ecuDialog.setData(list, new BottomOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            tvEcu.setText(appendOptionVo.getName());
                                            moduleId=appendOptionVo.getId();//模块ID
                                            selectSupplierRelateArithmetic();//查询供应商
                                            popupWindow.dismiss();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     *  通过车型ID、配置等级ID、模块ID查询算法关联的供应商
     */
    private void selectSupplierRelateArithmetic(){
        resetData(false,false,true,true);//重置数据
        String url= ServiceUrls.getSecurityMethodUrl("selectSupplierRelateArithmetic");
        Map<String,Object> map=new HashMap<>();
        map.put("vehicleId",vehicleId);//车型ID
        map.put("configurationLevelId",0);//配置等级ID
        map.put("moduleId",moduleId);//模块ID
        map.put("algorithmType",1);//算法类型 1
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    List<AppendOptionVo> list = gson.fromJson(data,type);
                                   supplierDialog.setData(list, new BottomOptionDialog.OnItemClickListener() {
                                       @Override
                                       public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                           tvSupplier.setText(appendOptionVo.getName());
                                           supplierId=appendOptionVo.getId();//设置供应商ID
                                           selectLevelRelateSeedToKey();//查询算法等级
                                           popupWindow.dismiss();
                                       }
                                   });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 获取算法等级
     */
    private void selectLevelRelateSeedToKey(){
        resetData(false,false,false,true);//重置数据
        String url= ServiceUrls.getSecurityMethodUrl("selectLevelRelateSeedToKey");
        Map<String,Object> map=new HashMap<>();
        map.put("vehicleId",vehicleId);//车型ID
        map.put("configurationLevelId",configurationLevelId);//配置等级ID
        map.put("moduleId",moduleId);//模块ID
        map.put("supplierId",supplierId);//供应商ID
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    List<AppendOptionVo> list = gson.fromJson(data,type);
                                    levelDialog.setData(list, new BottomOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            tvLevel.setText(appendOptionVo.getName());
                                            arithmeticLevelId=appendOptionVo.getId();//设置等级ID
                                            isCalculateEnabled();//判断是否启用计算按钮
                                            popupWindow.dismiss();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 判断是否启用计算按钮
     */
    private void isCalculateEnabled(){
        if (arithmeticLevelId >0 && etSeed.getText().length()==8){
            btnCalculate.setEnabled(true);//启用
        }else {
            btnCalculate.setEnabled(false);//禁用
        }
        tvKey.setText("");//清空输出key
        btnCalculate.setText(R.string.text_security_calculate);//计算
    }

    /**
     * 重置数据
     * @param isConfiguration 配置等级
     * @param isEcu 模块
     * @param isSupplier 供应商
     * @param isLevel 算法等级
     */
    private void resetData(boolean isConfiguration, boolean isEcu,boolean isSupplier,boolean isLevel){
        if (isConfiguration){
            tvConfiguration.setText(R.string.text_unchosen);//重新选择
            configurationDialog.setData(null,null);//清空模块数据
            configurationLevelId=0;//重置配置等级ID
            tvKey.setText("");//清空输出key
        }
        if (isEcu){
            tvEcu.setText(R.string.text_unchosen);//重新选择
            ecuDialog.setData(null,null);//清空模块数据
            moduleId=0;//重置模块ID
            tvKey.setText("");//清空输出key
        }
        if (isSupplier){
            tvSupplier.setText(R.string.text_unchosen);//重新选择
            supplierDialog.setData(null,null);//清空供应商数据
            supplierId=0;//重置供应商ID
            tvKey.setText("");//清空输出key
        }
        if (isLevel){
            tvLevel.setText(R.string.text_unchosen);//重新选择
            levelDialog.setData(null,null);//清空等级数据
            arithmeticLevelId=0;//重置等级ID
            tvKey.setText("");//清空输出key
        }
        isCalculateEnabled();//判断是否启用计算按钮
    }
    /**
     * 关闭输入框的虚拟键盘
     */
    private void closeKeyBoard(){
        //关闭虚拟键盘
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSeed.getWindowToken(), 0);
    }
}
