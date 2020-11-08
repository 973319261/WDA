package com.gx.wda.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.adapter.ExportMessageAdapter;
import com.gx.wda.adapter.ShareNoticeAdapter;
import com.gx.wda.bean.NoticeVo;
import com.gx.wda.bean.Pagination;
import com.gx.wda.bean.VciFileVo;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.KeyBoardUtil;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.ServiceUrls;
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
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

public class ExportMessageActivity extends AppCompatActivity {

    private int pageSize = 10;//分页大小
    private int currentPage = 1;//记录当前分页
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private LinearLayout llFileList;//刷新
    private ExportMessageAdapter shareFileAdapter;//适配器
    private RecyclerView rvFileRecord;//列表
    private LinearLayout llEmpty;
    List<VciFileVo> listAllFile = null;
    private EditText etFileName;
    private ImageView ivFileSearch;
    private RadioButton rbExport;
    private LinearLayout llBottomMenu;
    private BigDecimal time = null;

    private PromptDialog promptDialog;//加载框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_message);
        myActivity = this;
        myApplication = (MyApplication) myActivity.getApplication();
        promptDialog = new PromptDialog(myActivity);
        setContentView(R.layout.activity_export_message);
        etFileName = findViewById(R.id.et_file_query);
        ivFileSearch = findViewById(R.id.iv_file_query_search);
        llFileList=findViewById(R.id.ll_file_msg_list);
        rvFileRecord=findViewById(R.id.rv_vci_list_list); //空数据提示
        llEmpty = findViewById(R.id.ll_empty);
        rbExport = findViewById(R.id.rb_asc_export);
        llBottomMenu = findViewById(R.id.ll_bottom_menu);


        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("报文信息列表", R.drawable.ic_back,"", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                if(shareFileAdapter!=null){
                    if(shareFileAdapter.isCheck()){
                        shareFileAdapter.setCheck(false);
                        shareFileAdapter.notifyDataSetChanged();
                        shareFileAdapter.exportList.clear();//清空需要导出数据
                        //还原标题栏
                        myActionBar.setRightBtn("");
                        myActionBar.setRightText("选择");
                        myActionBar.setTitle("报文信息列表");
                        myActionBar.setLeftText("");
                        myActionBar.setLeftIco(R.drawable.ic_back);
                        //隐藏导出功能栏
                        llBottomMenu.setVisibility(View.GONE);
                    } else {
                        shareFileAdapter.setCheck(true);
                        shareFileAdapter.notifyDataSetChanged();
                        myActionBar.setLeftText("全选");
                        myActionBar.setRightText("取消");
                        myActionBar.checkAll(shareFileAdapter);
                        myActionBar.setLeftIco(0);
                        //显示底部功能栏
                        llBottomMenu.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        myActionBar.setRightText("选择");
        initView();//初始化页面
        setViewListener();//控件监听
    }

    /**
     * 控件监听
     */
    private void setViewListener() {
        //搜索筛选
        ivFileSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareFileAdapter.isCheckAll(false);
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
        //点击软键盘中的搜索
        etFileName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    shareFileAdapter.isCheckAll(false);
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
                    return true;
                }
                return false;
            }
        });

        //导出
        rbExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!Tools.isFastClick()){
                   int count = shareFileAdapter.exportList.size();
                   final int[] i = {0};
                   if(count<=0){
                       promptDialog.showError("没有可导出的报文数据...");
                   }else{
                       MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, myActivity.getResources().getString(R.string.text_export_tf_card_info), new MessageDialog.OnCloseListener() {
                           @Override
                           public void onClick(Dialog dialog, boolean confirm) {
                               if (confirm == true) {//确定按钮
                                   promptDialog.showLoading("正在导出报文信息...");
                                   new Thread(new Runnable() {
                                       @Override
                                       public void run() {

                                           for (VciFileVo file : shareFileAdapter.exportList) {
                                               if (file.getFileNum() > 0) {
                                                   i[0]++;
                                                   export(file);
                                               }
                                           }
                                           Log.e("成功导出",String.valueOf(i[0]));
                                           myActivity.runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   promptDialog.showSuccess((count-i[0])+"条无数据，成功导出"+i[0]+"条数据！");
                                                   shareFileAdapter.isCheckAll(false);
                                               }
                                           });
                                       }
                                   }).start();

                                  // Toast.makeText(myActivity,(count-exportCount)+"条无数据，成功导出"+exportCount+"条数据！",Toast.LENGTH_LONG).show();
                               }
                               dialog.dismiss();//关闭弹出框
                           }
                       });
                       messageDialog.setTitle(myActivity.getResources().getString(R.string.text_warm_prompt));
                       messageDialog.show();//显示弹出框
                   }
               }
            }
        });

    }



    /**
     * 执行导出
     * @param fileVo
     */
    public void export(VciFileVo fileVo){
        try {
            String fileName = fileVo.getFileName().replace("/record/","");
            boolean getFileList = TFTPExample.TftpUtils(myApplication.getVciIp(),fileName,fileVo.getFileName());
            if(getFileList){
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        Looper.prepare();//增加部分
                        String binPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/bin/"+fileVo.getFileName().replace("/record/","");
                        String ascPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/asc/"+fileVo.getFileName().replace("/record/","").replace(".bin",".asc");
                        String hintPath ="内部存储目录/com.gx.wda/export_msg_info/asc/"+fileVo.getFileName().replace("/record/","").replace(".bin",".asc");
                        try {
                            File f = new File(binPath);
                            int length = (int) f.length();
                            byte[] buff = new byte[length];
                            FileInputStream fin = new FileInputStream(f);
                            fin.read(buff);
                            fin.close();

                            String[] MONTH_EN = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
                            //"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"
                            String UTC_DATE = "";
                            //建立链接
                            FileInputStream fileInputStream = new FileInputStream(f);
                            int len = (int) f.length();
                            boolean state = true;
                            boolean isFirst = true;
                            int n = 0;//读出的字节数
                            int i = 0;
                            byte[] bigh = new byte[12];
                            byte [] b_start = new byte[12];
                            File ascPath1 = new File(Environment.getExternalStorageDirectory().getPath() + "/com.gx.wda");
                            File ascPath2 = new File(Environment.getExternalStorageDirectory().getPath() + "/export_msg_info/");
                            File ascFile = new File(ascPath);
                            if (!ascPath1.exists()) {
                                ascPath1.mkdirs();
                            }
                            if (!ascPath2.exists()) {
                                ascPath2.mkdirs();
                            }
                            if (!ascFile.exists()) {
                                ascFile.createNewFile();
                            }
                            FileOutputStream fos = new FileOutputStream(ascFile);

                            while (n != -1) {
                                //创建acs文件

                                //读取前12个字节的报文
                                if (state) {
                                    state = false;
                                    fileInputStream.read(b_start);//返回实际读取到字节数组中的字节数
                                    String HEADER_INFO = EncodingUtil.toHexString(b_start);
                                    //解析数据
                                    //1.起始4个字节，表示CAN_COUNT，全文只出现一次
                                    String CAN_COUNT = HEADER_INFO.substring(0, 8);
                                    //2.接着是UTC，8字节，全文只出现一次
                                    String FILE_UTC = HEADER_INFO.substring(8, 24);
                                    //转小端，然后转byte数组，再转10进制字符串
                                    //UTC_DATE =String.valueOf((int)Long.parseLong(EncodingUtil.sToB(FILE_UTC.split("")),16));
                                    UTC_DATE = EncodingUtil.binaryToDecimal(EncodingUtil.hexString2binaryString(EncodingUtil.sToB(FILE_UTC.split(""))));
                                }

                                //依次读取报文

                                n =  fileInputStream.read(bigh);//读取到数据场前的报文

                                String MSG_INFO = EncodingUtil.toHexString(bigh);//MSG_INFO 存储数据场以前的数据


                                //获取到DLC  （数据场长度）
                                int MSG_DLC = Integer.parseInt(MSG_INFO.substring(13,14));

                                int dataCount = 0;

                                if(MSG_DLC<1)
                                {
                                    dataCount=0;
                                }
                                else if(MSG_DLC<5&&MSG_DLC>0){
                                    dataCount=4;
                                }else if(4<MSG_DLC&&MSG_DLC<9){
                                    dataCount=8;
                                }else if (MSG_DLC > 8)
                                {
                                    dataCount=8;
                                }

                                byte[] bData = new byte[dataCount];
                                n =  fileInputStream.read(bData);//读取数据场数据

                                String dataField = EncodingUtil.toHexString(bData);

                                dataField = dataField.substring(0,MSG_DLC*2);



                                //1.时间戳 （6字节） 小端结构
                                Long timer = Long.parseLong(EncodingUtil.sToB(MSG_INFO.substring(0, 12).split("")), 16);//时间戳
                                BigDecimal bgTimer = new BigDecimal(timer);

                                if(isFirst){
                                    BigDecimal bgUTC = new BigDecimal(UTC_DATE);
                                    bgUTC = bgUTC.add(bgTimer);
                                    UTC_DATE = bgUTC.toString();
                                    // UTC_DATE = Float.toString();
                                    String DATA = EncodingUtil.timeStamp2Date(UTC_DATE, "yyyy-MM-dd");//获取日期 年月日
                                    //获取年份
                                    String YEAR = DATA.split("-")[0];
                                    //获取月份
                                    String MONTH = DATA.split("-")[1];
                                    MONTH = MONTH_EN[Integer.parseInt(MONTH)-1];
                                    //获取日期
                                    String DAY = DATA.split("-")[2];
                                    //获取星期
                                    String WEEK = EncodingUtil.dateToWeek(DATA);
                                    //获取时间
                                    String TIME = EncodingUtil.timeStamp2Date(UTC_DATE, "HH:mm:ss:SSS");//MM dd HH:mm:ss.SSS yyyy
                                    //获取上下午
                                    String HOUR = TIME.split(":")[0];
                                    String IsMORNING = "am";
                                    if (Integer.parseInt(HOUR) > 12) {
                                        IsMORNING = "pm";
                                    } else {
                                        IsMORNING = "am";
                                    }
                                    //写入的内容
                                    //时间格式
                                    String WRITE_DATE = WEEK + " " + MONTH + " " + DAY + " " + TIME + " " + IsMORNING + " " + YEAR;
                                    String WRITE_HEADER = "date " + WRITE_DATE + "\nbase hex timestamps absolute\ninternal events logged\n//version 7.0.0\n" +
                                            "Begin TriggerBlock "+ WRITE_DATE + "\n0.000000 Start of measurement\n";

                                    //String WRITE_DATE = WEEK + " " + MONTH + " " + DAY + " " + TIME + " " + YEAR;
                                    //String WRITE_HEADER = "date " + WRITE_DATE + "\nbase hex timestamps absolute\ninternal events logged\n";

                                    byte[] bytes = WRITE_HEADER.getBytes();
                                    fos.write(bytes);
                                    time = bgTimer;
                                }

                                BigDecimal flo_timer = bgTimer.subtract(time);//减去第一条报文的时间，得到相对时间
                                flo_timer = flo_timer.divide(new BigDecimal(1000*1000));
                                String MSG_TIMER = "";
                                if(isFirst){
                                    isFirst=false;
                                    MSG_TIMER = String.format(Locale.getDefault(), "%.3f", flo_timer);
                                }else {
                                    MSG_TIMER = String.format(Locale.getDefault(), "%.6f", flo_timer);
                                }


                                //2.报文类型 （1字节）
                                String MSG_TYPE = MSG_INFO.substring(14, 16);

                                //3.CAN ID （4字节） 小端结构
                                //CANID根据单帧和多帧不同类型使用不同的解析方式
                                String untreated = EncodingUtil.sToB(MSG_INFO.substring(16, 24).split(""));
                                String first =untreated.substring(0,1);//取得首位 转二进制
                                if(first.equals("0")){
                                    first = "0000";
                                }else {
                                    first = EncodingUtil.hexString2binaryString("0"+first);
                                }
                                String MSG_CANID = first.substring(first.length()-1,first.length()) + untreated.substring(1,untreated.length());
                                MSG_CANID = MSG_CANID.replaceAll("^(0+)", "");
                                //.replaceAll("(0)+$", "")\\




                                //4.CAN通道
                                String CAN_AISLE = "1";
                                if (MSG_TYPE.equals("00")) {
                                    CAN_AISLE = "1";
                                } else if (MSG_TYPE.equals("01")) {
                                    CAN_AISLE = "2";
                                }

                                //5.发送 or 接收
                                String b_MSG_TYPE = EncodingUtil.hexString2binaryString(MSG_TYPE);
                                String b_type = b_MSG_TYPE.substring(b_MSG_TYPE.length()-1,b_MSG_TYPE.length());
                                if(b_type.contains("0")){
                                    b_type = "Rx";
                                }else if(b_type.contains("1")){
                                    b_type = "Tx";
                                }

                                //6.数据场 （）
                                String MSG_DATA = EncodingUtil.addSpace(dataField);
                                /*String regex = "(.{2})";
                                MSG_DATA = MSG_DATA.replaceAll(regex, "$1 ");*/

                                //7.ID
                                int MSG_CANID_ID = (int) Long.parseLong(MSG_CANID, 16);


                                //写入格式
                                String WRITE_MSG = " " + MSG_TIMER + " " + CAN_AISLE + " " + MSG_CANID + " "+ b_type + " d " + MSG_DLC + MSG_DATA
                                        + "Length = 768000 BitCount = 67 ID = " + MSG_CANID_ID+"\n";

                                // String WRITE_MSG = "  " + MSG_TIMER + " " + CAN_AISLE + " " + MSG_CANID + " "+ b_type + " d " + MSG_DLC + " " + MSG_DATA+"\n";
                                if (n != -1) {
                                    i++;
                                    byte[] bytes = WRITE_MSG.getBytes();
                                    fos.write(bytes);
                                }
                            }

                            //写入尾部数据
                            String WRITE_FOOTER = "\nEnd TriggerBlock";
                            Log.e("WRITE_FOOTER", WRITE_FOOTER);
                            byte[] bytes = WRITE_FOOTER.getBytes();
                            fos.write(bytes);


                            //关闭流
                            fos.close();
                            //Toast.makeText(myActivity,"文件已保存于"+hintPath,Toast.LENGTH_LONG).show();
                            //打开文件
                            /*Intent intent = new Intent(myActivity,TbsActivity.class);
                            intent.putExtra("filePath",ascPath);
                            myActivity.startActivity(intent);*/
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            /*myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    promptDialog.dismiss();
                                    *//*File fileBin = new File(binPath);
                                    if(fileBin.exists()){
                                        fileBin.delete();//删除Bin文件
                                    }*//*
                                }
                            });*/
                        }
                        Looper.loop();//增加部分
                    }
                }).start();
            }
            /*else{
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        promptDialog.dismiss();
                        //连接失败
                        Toast.makeText(myActivity,"导出文件失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }*/
        } catch (IOException e) {
            e.printStackTrace();
            /*//连接失败
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    *//*promptDialog.dismiss();
                    Toast.makeText(myActivity,"导出文件失败！",Toast.LENGTH_SHORT).show();*//*
                }
            });*/
        }
    }




    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity, true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity, true);//状态栏文字颜色
        //设置导航栏图标样式
        Drawable iconSecurity=getResources().getDrawable(R.drawable.ic_asc_export_primary);//设置主页图标样式
        iconSecurity.setBounds(0,0,80,80);//设置图标边距 大小
        rbExport.setCompoundDrawables(null,iconSecurity,null,null);//设置图标位置
        rbExport.setCompoundDrawablePadding(1);//设置文字与图片的间距
        listAllFile = getFileList(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/bin/filelist.txt"));
        if(listAllFile.size()>0){
            llEmpty.setVisibility(View.GONE);
            llFileList.setVisibility(View.VISIBLE);
        }else{
            llEmpty.setVisibility(View.VISIBLE);
            llFileList.setVisibility(View.GONE);
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
            //将分页数据添加到recyclerView的适配器
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
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                String[] str = s.split(",");
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
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        Collections.reverse(list);
        return list;
    }



    /**
     * 返回键监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(shareFileAdapter.isCheck()){
                shareFileAdapter.setCheck(false);
                shareFileAdapter.notifyDataSetChanged();
                shareFileAdapter.exportList.clear();//清空需要导出数据
                //还原标题栏
                myActionBar.setRightBtn("");
                myActionBar.setRightText("选择");
                myActionBar.setTitle("报文信息列表");
                myActionBar.setLeftText("");
                myActionBar.setLeftIco(R.drawable.ic_back);
                //隐藏导出功能栏
                llBottomMenu.setVisibility(View.GONE);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        File listFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gx.wda/export_msg_info/bin/filelist.txt");
        if(listFile.exists()){
            listFile.delete();
        }
    }
}
