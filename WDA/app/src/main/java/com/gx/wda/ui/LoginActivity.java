package com.gx.wda.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.Jurisdiction;
import com.gx.wda.bean.UserVo;
import com.gx.wda.dialog.LanguageDialog;
import com.gx.wda.dialog.PermissionsDialog;
import com.gx.wda.util.LanguageUtil;
import com.gx.wda.util.MPermissionUtils;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.suke.widget.SwitchButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PHONE_STATE =1 ;
    private Activity myActivity;//上下文
    private MyApplication myApplication;
    private EditText etAccount;//账号
    private EditText etPassword;//密码
    private SwitchButton switchButton;//switch 记住
    private Button btnLogin;//登录
    private boolean remember;//记住密码
    private PromptDialog promptDialog;//加载框
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication= (MyApplication) myActivity.getApplication();
        promptDialog = new PromptDialog(myActivity);
        if (LanguageUtil.isSetting(myActivity)){//未设置
            LanguageDialog languageDialog = new LanguageDialog(myActivity);
            languageDialog.show(getSupportFragmentManager(),"");//显示弹出框
        }
        Locale locale = LanguageUtil.getAppLocale(this);//获取本地的语言
        LanguageUtil.changeAppLanguage(this,locale,true);//设置语言
        setContentView(R.layout.activity_login);
        etAccount=findViewById(R.id.et_login_account);
        etPassword=findViewById(R.id.et_login_password);
        switchButton=findViewById(R.id.switch_button);
        btnLogin=findViewById(R.id.btn_login);
        initView();//初始化页面
        setViewListener();//监听事件
    }
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        remember= (boolean) SPUtils.get(myActivity,SPUtils.SP_REMEMBER,false);//获取本地的是否记住密码
        if (remember){//记住
            switchButton.setChecked(true);//开启switch
            String account = (String) SPUtils.get(myActivity,SPUtils.SP_ACCOUNT,"");//获取本地账号
            String password = (String) SPUtils.get(myActivity,SPUtils.SP_PASSWORD,"");//获取本地密码
            etAccount.setText(account);//回填账号
            etPassword.setText(password);//回填解密后的密码
            if (etAccount.getText().length() > 0 && etPassword.getText().length() > 0){//账号密码不为空
                btnLogin.setEnabled(true);//开启登录按钮
            }
        }else {//不记住
            switchButton.setChecked(false);//关闭switch
        }
        permissionHint();
    }

    /**
     *  //权限温馨提示
     */
    private void permissionHint(){
        String content="";
        int count=0;
        if (!MPermissionUtils.hasAlwaysDeniedPermission(myActivity, Manifest.permission.READ_PHONE_STATE)) {//缺少读取设备识别码状态权限
            count++;
            content=content+count+"、"+getString(R.string.text_permission_device);
        }
        if (!MPermissionUtils.hasAlwaysDeniedPermission(myActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//缺少手机存储权限
            count++;
            content=content+count+"、"+getString(R.string.text_permissions_storage);
        }
        if (!MPermissionUtils.hasAlwaysDeniedPermission(myActivity, Manifest.permission.RECORD_AUDIO)) {//缺少手机存储权限
            count++;
            content=content+count+"、"+getString(R.string.text_permissions_microphone);
        }
        if (!MPermissionUtils.hasAlwaysDeniedPermission(myActivity, Manifest.permission.ACCESS_COARSE_LOCATION)) {//缺少定位限
            count++;
            content=content+count+"、"+getString(R.string.text_permissions_location);
        }
        if (!MPermissionUtils.checkPermissions(myActivity,PERMISSIONS_STORAGE)&& !MPermissionUtils.hasAlwaysDeniedPermission(myActivity,PERMISSIONS_STORAGE)){
            final PermissionsDialog permissionsDialog = new PermissionsDialog(myActivity,content);
            permissionsDialog.setOnOpenListener(new PermissionsDialog.OnOpenListener() {
                @Override
                public void onOpen(Dialog dialog) {
                    MPermissionUtils.startAppSettings(myActivity);
                    dialog.dismiss();
                }
            });
            permissionsDialog.setOnCloseListener(new PermissionsDialog.OnCloseListener() {
                @Override
                public void onClose(Dialog dialog) {
                    dialog.dismiss();
                }
            });
            permissionsDialog.show(getSupportFragmentManager(),"");
        }

    }
    /**
     * 监听事件
     */
    private void setViewListener(){
        //手机号
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0 && etPassword.getText().length()>0){//账号和密码不为空
                    btnLogin.setEnabled(true);//开启按钮
                }else {
                    btnLogin.setEnabled(false);//禁止按钮
                }
            }
        });
        //密码
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //账号和密码不为空
                if (s.length()>0 && etAccount.getText().length()>0){
                    btnLogin.setEnabled(true);//开启按钮
                }else {
                    btnLogin.setEnabled(false);//禁止按钮
                }
            }
        });
        //记住密码
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){//开启
                    remember=true;
                }else {
                    remember=false;
                }
            }
        });
        //登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //网络请求
                final String account = etAccount.getText().toString();//账号
                final String password = etPassword.getText().toString();//密码
                String deviceId = Settings.Secure.getString(myActivity.getContentResolver(), Settings.Secure.ANDROID_ID);//获取androidID
                String url= ServiceUrls.getUserMethodUrl("login");
                Map<String,Object> map=new HashMap<>();
                map.put("account",account);
                map.put("password",password);
                map.put("imei",deviceId);
                promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode==200){
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        String text=jsonObject.getString("text");//提示
                                        if (code==200){
                                            JSONObject jsonData=new JSONObject(jsonObject.getString("data"));//用户信息
                                            String user=jsonData.getString("user");//用户信息
                                            String jurisdictionStr=jsonData.getString("jurisdictionList");//权限列表信息
                                            Type jurisdictionType=new TypeToken<List<Jurisdiction>>(){}.getType();
                                            List<Jurisdiction> jurisdictionList = gson.fromJson(jurisdictionStr,jurisdictionType);
                                            myApplication.setJurisdictionList(jurisdictionList);//设置到内存中
                                            Type type=new TypeToken<UserVo>(){}.getType();
                                            UserVo userVo  = gson.fromJson(user,type);
                                            SPUtils.put(myActivity,SPUtils.SP_USER,user);//保存用户信息到本地
                                            SPUtils.put(myActivity,SPUtils.SP_USER_ID,userVo.getUserId());//保存用户ID到本地
                                            SPUtils.put(myActivity,SPUtils.SP_ACCOUNT,account);//保存账号到本地
                                            SPUtils.put(myActivity,SPUtils.SP_PASSWORD,password);//保存密码到本地
                                            SPUtils.put(myActivity,SPUtils.SP_REMEMBER,remember);//保存是否记住密码到本地
                                            Intent intent = new Intent(myActivity,MainActivity.class);
                                            startActivity(intent);
                                            finish();//关闭当前activity
                                        }else if (code==500){
                                            Toast.makeText(myActivity, R.string.text_parameter_error,Toast.LENGTH_SHORT).show();
                                        }else if (code==501){
                                            Toast.makeText(myActivity, R.string.text_account_not_exist,Toast.LENGTH_SHORT).show();
                                        }else if (code==502){
                                            Toast.makeText(myActivity, R.string.text_password_error,Toast.LENGTH_SHORT).show();
                                        }else if (code==503){
                                            Toast.makeText(myActivity, R.string.text_devices_bind,Toast.LENGTH_SHORT).show();
                                        }else if (code==504){
                                            Toast.makeText(myActivity, R.string.text_account_expired,Toast.LENGTH_SHORT).show();
                                        }else if (code==505){
                                            Toast.makeText(myActivity, R.string.text_account_device,Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    Toast.makeText(myActivity, R.string.text_server_error,Toast.LENGTH_SHORT).show();
                                }
                                promptDialog.dismiss();//关闭加载框
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * 申请权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
