package com.gx.wda.ui.share;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformDidActivity extends AppCompatActivity {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private List<Integer> listDid;
    private LinearLayout llDidContent;
    //Map<Integer,String> listDidVlaue = new HashMap<>();//did小方块list
    List<AppendOptionVo> listDidVlaue =  null;
    private Button btnTransformDid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myActivity=this;
        //获取传递过来的参数
        String data = getIntent().getStringExtra("listDidVlaue");
        Type type = new TypeToken<List<AppendOptionVo>>(){}.getType();
        listDidVlaue =  gson.fromJson(data,type);

        setContentView(R.layout.activity_transform_did);

        //获取控件
        llDidContent = findViewById(R.id.ll_count_did_content);
        btnTransformDid = findViewById(R.id.btn_transform_did);



        initView();

        btnTransformDid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ServiceUrls.getShareMethodUrl("transformDID");
                for (int i=0;i<listDidVlaue.size();i++){
                    EditText et = findViewById(i);
                    String val = et.getText().toString();
                    listDidVlaue.get(i).setFullName(val);
                };
                Map<String,Object> map = new HashMap<>();
                map.put("listDidVlaue",gson.toJson(listDidVlaue));
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                        if (isSuccess && responseCode==200){
                            try {
                                List<AppendOptionVo> DidList=null;//did列表
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    DidList = gson.fromJson(data,type);
                                    for (int i=0;i<DidList.size();i++){
                                        EditText etOutput = findViewById(DidList.get(i).getId()+10000);
                                        String val = DidList.get(i).getValue();
                                        if(etOutput!=null){
                                            myActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    etOutput.setText(String.valueOf(val));
                                                }
                                            });
                                        }

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


        super.onCreate(savedInstanceState);
    }

    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色

        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("DID "+getResources().getString(R.string.text_transformation), R.drawable.ic_back,0,
                1, 0, new MyActionBar.ActionBarClickListener() {
                    @Override
                    public void onLeftClick() {
                        finish();
                    }

                    @Override
                    public void onRightClick() {

                    }
                });


        //加载输入输出框
        if(listDidVlaue.size()>0){
            for (int i=0;i<listDidVlaue.size();i++){
                addDidInput(i,listDidVlaue.get(i).getId(),listDidVlaue.get(i).getName());
            }
        }else{

        }
    }


    private void addDidInput(int id,int didID,String didName){
        // 1.创建最外层LinearLayout控件
        final LinearLayout layoutOuterMost = new LinearLayout(myActivity);
        LinearLayout.LayoutParams layoutlayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置margin
        layoutlayoutParams.setMargins(0, 20, 0, 20);
        layoutOuterMost.setLayoutParams(layoutlayoutParams);
        // 设置属性
        layoutOuterMost.setPadding(35,25,35,25);
        layoutOuterMost.setOrientation(LinearLayout.VERTICAL);
        layoutOuterMost.setGravity(Gravity.CENTER);

        //2.创建Input linearLayout
        final LinearLayout layoutInput = new LinearLayout(myActivity);
        layoutlayoutParams.setMargins(0, 0, 0, 20);
        layoutInput.setLayoutParams(layoutlayoutParams);
        //设置属性
        layoutInput.setPadding(45,15,45,15);
        layoutInput.setGravity(View.TEXT_ALIGNMENT_CENTER);
        layoutInput.setBackground(getDrawable(R.drawable.btn_border_radius_fill_gray));
        layoutInput.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout layoutOutput = new LinearLayout(myActivity);
        layoutlayoutParams.setMargins(0, 0, 0, 20);
        layoutOutput.setLayoutParams(layoutlayoutParams);
        //设置属性
        layoutOutput.setPadding(45,15,45,15);
        layoutOutput.setGravity(View.TEXT_ALIGNMENT_CENTER);
        layoutOutput.setBackground(getDrawable(R.drawable.btn_border_radius_fill_gray));
        layoutOutput.setOrientation(LinearLayout.HORIZONTAL);

        //3.设置 input TextView
        final TextView tvInput = new TextView(myActivity);

        LinearLayout.LayoutParams layoutTextView = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutTextView.setMargins(0,0,25,0);
        tvInput.setLayoutParams(layoutTextView);
        tvInput.setTextSize(14);
        tvInput.setGravity(Gravity.CENTER);
        tvInput.setPadding(10,0,10,0);
        tvInput.setTextColor(getResources().getColor(R.color.colorBlack));
        tvInput.setText(didName);

        final TextView tvOutput = new TextView(myActivity);
        tvOutput.setLayoutParams(layoutTextView);
        tvOutput.setTextSize(14);
        tvOutput.setGravity(Gravity.CENTER);
        tvOutput.setTextColor(getResources().getColor(R.color.colorBlack));
        tvOutput.setText("Output");


        //4.设置 input EditText
        final EditText etInput = new EditText(myActivity);
        LinearLayout.LayoutParams layoutEditText = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1);
        etInput.setLayoutParams(layoutEditText);
        etInput.setSingleLine(true);
        etInput.setBackground(null);
        etInput.setMaxLines(1);
        etInput.setGravity(Gravity.CENTER);
        etInput.setTextSize(14);
        etInput.setId(id);
        etInput.setInputType(InputType.TYPE_CLASS_NUMBER );
        etInput.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        etInput.setSingleLine(true);
        //输入框改变
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    btnTransformDid.setEnabled(true);
                }

            }
        });



        final EditText etOutput = new EditText(myActivity);
        etOutput.setLayoutParams(layoutEditText);
        etOutput.setSingleLine(true);
        etOutput.setBackground(null);
        etOutput.setMaxLines(1);
        etOutput.setEnabled(false);
        etOutput.setTextColor(getResources().getColor(R.color.colorBlack));
        etOutput.setGravity(Gravity.CENTER);
        etOutput.setTextSize(14);
        etOutput.setId(didID+10000);


        //5.设置 Input ImageView
        final  ImageView ivInput = new ImageView(myActivity);
        LinearLayout.LayoutParams layoutImageView = new LinearLayout.LayoutParams(60, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutImageView.setMargins(25,0,25,0);
        ivInput.setLayoutParams(layoutImageView);
        ivInput.setImageResource(R.drawable.ic_import);

        final  ImageView ivOutput = new ImageView(myActivity);
        layoutImageView.setMargins(25,0,25,0);
        ivOutput.setLayoutParams(layoutImageView);
        ivOutput.setImageResource(R.drawable.ic_export);
        ivOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = etOutput.getText().toString();
                if(Tools.isNotNull(output)){
                    ClipboardManager cm = (ClipboardManager) myActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(output);//复制的文字
                    Toast.makeText(myActivity,getResources().getString(R.string.text_valuecopy),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //6.将input ImageView 和 input EditText 塞到 input LinearLayout里面去
        layoutInput.addView(tvInput);
        layoutInput.addView(etInput);
        layoutInput.addView(ivInput);
        layoutOutput.addView(tvOutput);
        layoutOutput.addView(etOutput);
        layoutOutput.addView(ivOutput);

        layoutOuterMost.addView(layoutInput);
        layoutOuterMost.addView(layoutOutput);
        //4.将最外层layout添加到指定位置
        llDidContent.addView(layoutOuterMost);
    }
}
