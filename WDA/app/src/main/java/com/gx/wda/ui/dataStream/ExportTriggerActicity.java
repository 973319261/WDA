package com.gx.wda.ui.dataStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.adapter.ExportMessageAdapter;
import com.gx.wda.bean.VciFileVo;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.TFTPExample;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.MyActionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.leefeng.promptlibrary.PromptDialog;

public class ExportTriggerActicity extends AppCompatActivity {

    private PromptDialog promptDialog;//加载框
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//记录当前分页
    private static Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private LinearLayout llFileList;//刷新
    private ExportMessageAdapter shareFileAdapter;//适配器
    private RecyclerView rvFileRecord;//列表

    List<VciFileVo> listAllFile = null;
    private EditText etFileName;
    private ImageView ivFileSearch;
    private LinearLayout llEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        myApplication = (MyApplication) myActivity.getApplication();
        promptDialog = new PromptDialog(myActivity);
        setContentView(R.layout.activity_export_message);
        etFileName = findViewById(R.id.et_file_query);
        ivFileSearch = findViewById(R.id.iv_file_query_search);
        llFileList=findViewById(R.id.ll_file_msg_list);
        rvFileRecord=findViewById(R.id.rv_vci_list_list);
        //空数据提示
        llEmpty = findViewById(R.id.ll_empty);

        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("报文信息列表", R.drawable.ic_back, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });

        initView();//初始化页面

        setViewListener();//控件监听
    }

    /**
     * 控件监听
     */
    private void setViewListener() {
        ivFileSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = etFileName.getText().toString();
                if(!Tools.isNotNull(fileName)){
                    loadListData(true,listAllFile);
                }else{
                    List<VciFileVo> listFile = new ArrayList<>();
                    for(int i=0;i<listAllFile.size();i++){
                        if(listAllFile.get(i).getFileName().contains(fileName.toLowerCase())){
                            listFile.add(listAllFile.get(i));
                        }
                    }
                    loadListData(true,listFile);
                }

            }
        });

    }

    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity, true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity, true);//状态栏文字颜色

        listAllFile = getFileList(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/bin/filelist.txt"));

        if(listAllFile.size()>0){
            llEmpty.setVisibility(View.GONE);
        }else{
            llEmpty.setVisibility(View.VISIBLE);
        }

        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvFileRecord.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        List<VciFileVo> mListData=new ArrayList<>();
        shareFileAdapter = new ExportMessageAdapter(mListData, myActivity, myApplication,myActionBar);
        //=2.3、设置recyclerView的适配器
        rvFileRecord.setAdapter(shareFileAdapter);
        //加载RecyclerView数据
        loadListData(true,listAllFile);

    }
    /**
     * 加载列表数据
     *
     * @param isRefresh 是否是刷新列表
     */
    private void loadListData(final boolean isRefresh,List<VciFileVo> list) {
        if (isRefresh) {//刷新，
            currentPage = 1;//加载第一页
        } else {
            currentPage++;//加载下一页
        }
        //=====请求服务端数据

        //异步请求
        if (list!=null) {
            shareFileAdapter.addItem(list);
        }

    }


    /**
     * 获取文件列表
     * @param file
     * @return
     */
    public static List<VciFileVo> getFileList(File file){
        List<VciFileVo> list=new ArrayList<VciFileVo>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            String triggerStart =  (String) SPUtils.get(myActivity,SPUtils.SP_TRIGGER_START,"");//获取上次软触发时间
            String triggerEnd =  (String) SPUtils.get(myActivity,SPUtils.SP_TRIGGER_END,"");//获取上次软结束时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            //如果未记录结束时间，则使用当前时间
            if(!Tools.isNotNull(triggerEnd)){
                Calendar cal = Calendar.getInstance();
                triggerEnd = sdf.format(cal.getTime());
            }

            //通过时间比较筛选，生成文件列表
            Date start = sdf.parse(triggerStart);
            Date end = sdf.parse(triggerEnd);
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                String[] str = s.split(",");
                String saveTime = str[0].replace(".bin","").split("_")[2]+str[0].replace(".bin","").split("_")[3];
                Date fileTime = sdf.parse(saveTime);
                if(fileTime.compareTo(start)>=0&&fileTime.compareTo(end)<=0){//&&fileTime.compareTo(end)<=0
                    VciFileVo fileVo=new VciFileVo();
                    if(str.length>1){
                        fileVo.setFileName(str[0]);
                        fileVo.setFileNum(Integer.valueOf(str[1]));
                        fileVo.setFileSize(Integer.valueOf(str[2]));
                    }else{
                        fileVo.setFileName(str[0]);
                    }
                    list.add(fileVo);
                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //顺序反转
        Collections.reverse(list);
        return list;
    }







    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 退出当前页面时，删除filelist.txt
         */
        File listFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/filelist.txt");
        if(listFile.exists()){
            listFile.delete();
        }
    }



}
