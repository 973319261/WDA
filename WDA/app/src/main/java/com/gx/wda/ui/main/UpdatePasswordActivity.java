package com.gx.wda.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.UserVo;
import com.gx.wda.util.KeyBoardUtil;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 修改密码页面
 */
public class UpdatePasswordActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private EditText etOldPassword;//旧密码
    private EditText etNewPassword;//新密码
    private EditText etConfirmPassword;//确认密码
    private Button btnSave;//保存
    private PromptDialog promptDialog;//加载框
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private UserVo user;//本地用户信息

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;
        setContentView(R.layout.activity_update_password);
        myApplication= (MyApplication) myActivity.getApplication();
        etOldPassword=findViewById(R.id.et_update_password_old);
        etNewPassword=findViewById(R.id.et_update_password_new);
        etConfirmPassword=findViewById(R.id.et_update_password_confirm);
        btnSave=findViewById(R.id.btn_save);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_update_password_title), R.drawable.ic_back, 0,
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
        String userStr = (String) SPUtils.get(myActivity,SPUtils.SP_USER,"");//获取本地用户数据
        Type type=new TypeToken<UserVo>(){}.getType();
        user = gson.fromJson(userStr,type);
        promptDialog=new PromptDialog(myActivity);//加载框实例化
    }

    /**
     * 监听事件
     */
    private void setViewListener(){
        //旧密码
        etOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //都不为空
                if (s.length()>0 && etNewPassword.getText().length()>0 && etConfirmPassword.getText().length()>0){//账号和密码不为空
                    btnSave.setEnabled(true);//开启按钮
                }else {
                    btnSave.setEnabled(false);//禁止按钮
                }
            }
        });
        //新密码
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //都不为空
                if (s.length()>0 && s.length()>0 && etConfirmPassword.getText().length()>0){//账号和密码不为空
                    btnSave.setEnabled(true);//开启按钮
                }else {
                    btnSave.setEnabled(false);//禁止按钮
                }
            }
        });
        //确认密码
        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //都不为空
                if (s.length()>0 && etNewPassword.getText().length()>0 && s.length()>0){//账号和密码不为空
                    btnSave.setEnabled(true);//开启按钮
                }else {
                    btnSave.setEnabled(false);//禁止按钮
                }
            }
        });
        //保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.hideKeyboard(v);//关闭虚拟键盘
                final String oldPassword=etOldPassword.getText().toString();//原来密码
                final String newPassword=etNewPassword.getText().toString();//新密码
                final String confirmPassword=etConfirmPassword.getText().toString();//确认密码
                if (!newPassword.equals(confirmPassword)){//新密码与确认密码不一致
                    Toast.makeText(myActivity, R.string.text_password_confirm,Toast.LENGTH_SHORT).show();
                    return;
                }
                String url= ServiceUrls.getUserMethodUrl("updatePassword");
                Map<String,Object> map=new HashMap<>();
                map.put("userId",user.getUserId());//用户ID
                map.put("oldPassword",oldPassword);//原来密码
                map.put("newPassword",newPassword);//新密码
                promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
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
                                            Toast.makeText(myActivity, R.string.text_password_success,Toast.LENGTH_SHORT).show();
                                            SPUtils.put(myActivity,SPUtils.SP_PASSWORD,newPassword);//重新保存本地密码
                                            etOldPassword.setText("");//清空旧密码
                                            etNewPassword.setText("");//清空新密码
                                            etConfirmPassword.setText("");//清空确认密码
                                        }else if (code==502){
                                            Toast.makeText(myActivity, R.string.text_password_coincide,Toast.LENGTH_SHORT).show();
                                        }else if (code==501){
                                            Toast.makeText(myActivity, R.string.text_oldPassword_error,Toast.LENGTH_SHORT).show();
                                        }else if (code==500){
                                            Toast.makeText(myActivity,R.string.text_parameter_error,Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                promptDialog.dismissImmediately();//关闭弹窗
                            }
                        });
                    }
                });
            }
        });
    }



}
