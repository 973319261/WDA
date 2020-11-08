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
import com.gx.wda.R;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.bean.SecurityRecordVo;
import com.gx.wda.dialog.BottomOptionDialog;
import com.gx.wda.util.KeyBoardUtil;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.SecurityRecordUtil;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.Tools;
import com.gx.wda.util.VinUtil;
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
 * Vin转Esk页面
 */
public class VinPinActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private TextView tvVehicle;//车辆
    private LinearLayout llVehicle;//车辆
    private LinearLayout llOption;//选项列表
    private CustomEditText etVin;//输入Vin
    private TextView tvPin;//输入Pin
    private int vehicleId = 0;//车型ID
    private Button btnCalculate;//计算
    private BottomOptionDialog vehicleDialog;//车型弹出框
    private List<AppendOptionVo> vehicleList=null;//车型列表
    private PromptDialog promptDialog;//加载框
    private Integer userId;//用户ID
    private Boolean isHistory;//判断是否为历史记录进来的
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;
        setContentView(R.layout.activity_vin_pin);
        tvVehicle=findViewById(R.id.tv_security_vehicle);
        llVehicle=findViewById(R.id.ll_security_vehicle);
        llOption=findViewById(R.id.ll_security_option);
        etVin=findViewById(R.id.et_security_vin);
        tvPin=findViewById(R.id.tv_security_pin);
        btnCalculate=findViewById(R.id.btn_calculate);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_security_vin_pin_title), R.drawable.ic_back,0,
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
        etVin.initKeyBoard(myActivity);//初始化编辑框键盘
        if (isHistory){//历史记录进来的
            String securityRecordStr = getIntent().getStringExtra("securityRecordVo");//获取历史记录
            Type type=new TypeToken<SecurityRecordVo>(){}.getType();
            SecurityRecordVo securityRecordVo=gson.fromJson(securityRecordStr,type);
            vehicleId=securityRecordVo.getVehicleId();//设置车型ID
            llOption.setVisibility(View.GONE);//隐藏
        }
        vehicleDialog =new BottomOptionDialog(myActivity,getResources().getString(R.string.text_detection_vehicle));
        promptDialog=new PromptDialog(myActivity);//加载框实例化
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取本地用户ID
    }

    /**
     * 监听事件
     */
    private void setViewListener(){
        //车辆
        llVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();//关闭虚拟键盘
                if (vehicleList==null){
                    //获取车型信息
                    String url= ServiceUrls.getSecurityMethodUrl("selectVehicleRelateArithmeticVehicle");
                    Map<String,Object> map=new HashMap<>();
                    map.put("algorithmType",2);//算法类型（类型3）
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
                                                        isCalculateEnabled();//判断是否启用计算按钮
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
                            isCalculateEnabled();//判断是否启用计算按钮
                            popupWindow.dismiss();//关闭弹窗
                        }
                    });
                    vehicleDialog.show();//显示
                }
            }
        });
        //计算
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();//关闭虚拟键盘
                String inputValue = etVin.getText().toString();//获取Seed值
                if (VinUtil.isLegal(inputValue)){//判断VIN是否合法
                    String url=ServiceUrls.getSecurityMethodUrl("calculateByDll");
                    Map<String,Object> map=new HashMap<>();
                    map.put("inputValue",inputValue);//输入值
                    map.put("vehicleId",vehicleId);//车型ID
                    map.put("userId",userId);//用户ID
                    map.put("typeId",2);//类型
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
                                                if (!isHistory) {//不是历史记录进来的
                                                    String securityRecordVoStr=jsonData.getString("securityRecordVo");//安全算法记录信息
                                                    Type type=new TypeToken<SecurityRecordVo>(){}.getType();//转换类型
                                                    SecurityRecordVo securityRecordVo=gson.fromJson(securityRecordVoStr,type);
                                                    securityRecordVo.setTypeId(2);//设置类型ID
                                                    securityRecordVo.setModuleName("None");
                                                    securityRecordVo.setSupplierName("None");
                                                    securityRecordVo.setTypeName(getResources().getString(R.string.text_security_vin_pin_title));//设置类型
                                                    SecurityRecordUtil.setSecurityRecord(myActivity,securityRecordVo);//保存记录记录到本地
                                                }
                                                tvPin.setText(result);//设置返回值
                                                btnCalculate.setText(R.string.text_security_calculate_success);
                                                btnCalculate.setEnabled(false);//禁止计算
                                            }else if (code==500){//暂无算法
                                                Toast.makeText(myActivity,R.string.text_no_algorithm,Toast.LENGTH_SHORT).show();
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
        etVin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etVin.setKeyBoard(myActivity);
            }
        });
        //输入框改变
        etVin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (vehicleId>0 && s.length()==17){//VIN码（17位）
                    btnCalculate.setEnabled(true);//启用按钮
                }else {
                    btnCalculate.setEnabled(false);//禁用按钮
                }
                tvPin.setText("");//清空输出key
                btnCalculate.setText(R.string.text_security_calculate);//计算
            }
        });
        //输出框长按事件
        tvPin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String key=tvPin.getText().toString();
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
    /**
     * 判断是否启用计算按钮
     */
    private void isCalculateEnabled(){
        if (vehicleId >0 && etVin.getText().length()==17){//VIN码（17位）
            btnCalculate.setEnabled(true);//启用
        }else {
            btnCalculate.setEnabled(false);//禁用
        }
        tvPin.setText("");//清空输出key
        btnCalculate.setText(R.string.text_security_calculate);//计算
    }

    /**
     * 关闭输入框的虚拟键盘
     */
    private void closeKeyBoard(){
        //关闭虚拟键盘
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etVin.getWindowToken(), 0);
    }
}
