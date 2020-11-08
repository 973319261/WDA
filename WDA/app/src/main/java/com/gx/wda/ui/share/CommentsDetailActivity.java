package com.gx.wda.ui.share;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.adapter.ShareCommentsAdapter;
import com.gx.wda.bean.CommentsVo;
import com.gx.wda.bean.Pagination;
import com.gx.wda.dialog.InputDialog;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.MyActionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论详情
 */
public class CommentsDetailActivity extends AppCompatActivity {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//记录当前分页
    private AppCompatActivity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private SmartRefreshLayout srlShareCommect;//刷新
    private ShareCommentsAdapter shareCommectAdapter;//适配器
    private RecyclerView rvCommectRecord;//列表
    private int noticeId=0;
    private int replyId = 0;
    private TextView tvUserName;
    private TextView tvDateTime;
    private TextView tvComm;
    private LinearLayout llCom;
    private LinearLayout llEmpty;
    private InputDialog inputDialog1;
    private Integer userid;
    private Integer criticId;
    private Integer tierType=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        myActivity=this;//设置上下文
        userid = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取本地用户数据
        //接收参数
        noticeId = getIntent().getIntExtra("noticeId",0);
        replyId = getIntent().getIntExtra("replyId",0);
        myApplication= (MyApplication) myActivity.getApplication();
        //获取控件
        srlShareCommect=findViewById(R.id.srl_share_comment_list);
        rvCommectRecord=findViewById(R.id.rv_share_comment_list);
        llCom = findViewById(R.id.ll_comments_con);
        llEmpty = findViewById(R.id.ll_empty);
        tvUserName = findViewById(R.id.tv_comment_name);
        tvDateTime = findViewById(R.id.tv_comment_date);
        tvComm = findViewById(R.id.tv_comment_con);

        initView();
        setViewListener();
    }




    private void initView(){
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        //标题栏
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_comments_detail), R.drawable.ic_back,0,
        1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                setResult(1,(new Intent()));
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });

        //回填数据
        String url = ServiceUrls.getShareMethodUrl("selectdbCom");
        Map<String,Object> map = new HashMap<>();
        map.put("commmentsId",replyId);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if(code==200){
                                    String strData = jsonObject.getString("data");
                                    CommentsVo dbCom = gson.fromJson(strData,CommentsVo.class);
                                    criticId = dbCom.getUserId();
                                    tierType = dbCom.getCommentId();
                                    SimpleDateFormat SHOT_DATE = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat SHOW_TIME = new SimpleDateFormat("HH:mm:ss");
                                    String date =SHOT_DATE.format(dbCom.getCreationTime());
                                    String time =SHOW_TIME.format(dbCom.getCreationTime());
                                    Calendar dateNow=Calendar.getInstance();
                                    String[] datas = date.split("-");
                                    String dateTime = date;
                                    if(Integer.valueOf(datas[0])<=dateNow.get(Calendar.YEAR)){
                                        if(Integer.valueOf(datas[1])<=dateNow.get(Calendar.MONTH+1)){
                                            if(Integer.valueOf(datas[2])==dateNow.get(Calendar.DATE)){
                                                dateTime=time;
                                            }
                                        }
                                    }
                                    tvUserName.setText(dbCom.getUserName());
                                    tvDateTime.setText(dateTime);
                                    tvComm.setText(dbCom.getContent());
                                }else{
                                    llEmpty.setVisibility(View.VISIBLE);
                                    llCom.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });




        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvCommectRecord.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        List<CommentsVo> mListData=new ArrayList<>();
        //新增评论
        shareCommectAdapter = new ShareCommentsAdapter(mListData, myActivity, myApplication, new ShareCommentsAdapter.OnSendClickListener() {
            @Override
            public void onSendClick(final Dialog dialog, CommentsVo commentsVo, String content, final InputDialog inputDialog) {
                String url = ServiceUrls.getShareMethodUrl("addComment");
                CommentsVo comment = new CommentsVo();
                comment.setContent(content);
                //comment.setCommentdate(new Date());
                comment.setNoticeId(commentsVo.getNoticeId());
                comment.setUserId(userid);
                comment.setTierType(tierType);
                comment.setReplyId(commentsVo.getReplyId());
                comment.setCriticId(commentsVo.getReplyId());
                Map<String,Object> map = new HashMap<>();
                map.put("comment", gson.toJson(comment));
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String strMsg =myActivity.getResources().getString(R.string.text_network_anomaly);
                                if(isSuccess == true && responseCode == 200){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        if(code==200){
                                            Toast.makeText(myActivity,getString(R.string.text_comment_success),Toast.LENGTH_SHORT).show();
                                            inputDialog.hide();
                                            initView();
                                            return;
                                        }
                                        Toast.makeText(myActivity,strMsg,Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Toast.makeText(myActivity,strMsg,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }





        });
        //长按事件，删除
        shareCommectAdapter.setLongClickListener(new ShareCommentsAdapter.OnLongClick() {
            @Override
            public void onLongClick(View view, final CommentsVo data, int position) {
                if(data.getUserId()==userid&&userid!=0){
                    MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, myActivity.getResources().getString(R.string.text_comment_delete), new MessageDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm==true){//确定按钮
                                String url = ServiceUrls.getShareMethodUrl("delComment");
                                Map<String,Object> map = new HashMap<>();
                                map.put("commentId",data.getCommentId());
                                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                                    @Override
                                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                                        myActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (isSuccess&&responseCode==200){
                                                    String strMsg = myActivity.getString(R.string.text_network_anomaly);
                                                    if(isSuccess == true && responseCode == 200){
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response);
                                                            int code = jsonObject.getInt("code");
                                                            if(code==200){
                                                                Toast.makeText(myActivity,myActivity.getString(R.string.text_delete_success),Toast.LENGTH_SHORT).show();
                                                                initView();
                                                                return;
                                                            }
                                                            Toast.makeText(myActivity,strMsg,Toast.LENGTH_SHORT).show();
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    Toast.makeText(myActivity,strMsg,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                            dialog.dismiss();//关闭弹出框
                        }
                    });
                    messageDialog.setTitle(myActivity.getResources().getString(R.string.text_delete_msg));
                    messageDialog.show();//显示弹出框
                }
            }
        });
        //=2.3、设置recyclerView的适配器
        rvCommectRecord.setAdapter(shareCommectAdapter);
        //加载RecyclerView数据
        loadListData(true);
    }


    //Share 控件事件
    private void setViewListener(){
        //下拉 刷新整个RecyclerView
        srlShareCommect.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });

        //上拉加载更多数据
        srlShareCommect.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });


        //显示评论窗口
        tvComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//评论输入框
                inputDialog1 = new InputDialog("@"+tvUserName.getText().toString(), "", InputType.TYPE_CLASS_TEXT, 140, new InputDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(Dialog dialog, String content) {
                        String url = ServiceUrls.getShareMethodUrl("addComment");
                        CommentsVo comment = new CommentsVo();
                        comment.setContent(content);
                        //comment.setCommentdate(new Date());
                        comment.setNoticeId(noticeId);
                        comment.setUserId(userid);
                        comment.setReplyId(replyId);
                        comment.setCriticId(criticId);
                        Map<String,Object> map = new HashMap<>();
                        map.put("comment", gson.toJson(comment));
                        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                            @Override
                            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                                myActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String strMsg = getString(R.string.text_network_anomaly);
                                        if(isSuccess == true && responseCode == 200){
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                int code = jsonObject.getInt("code");
                                                if(code==200){
                                                    Toast.makeText(myActivity,getString(R.string.text_comment_success),Toast.LENGTH_SHORT).show();
                                                    inputDialog1.hide();
                                                    initView();
                                                    return;
                                                }
                                                Toast.makeText(myActivity,strMsg,Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        Toast.makeText(myActivity,strMsg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
                inputDialog1.show(getSupportFragmentManager(),"");
            }
        });
    }





    //加载RecyclerView数据

    /**
     * 加载列表数据
     *
     * @param isRefresh 是否是刷新列表
     */
    private void loadListData(final boolean isRefresh) {
        if (isRefresh) {//刷新，
            currentPage = 1;//加载第一页
        } else {
            currentPage++;//加载下一页
        }
        //=====请求服务端数据
        String url = ServiceUrls.getShareMethodUrl("selectListComment");//服务端url
        //准备服务端所需参数
        CommentsVo com = new CommentsVo();
        com.setNoticeId(noticeId);
        com.setReplyId(replyId);
        com.setPageSize(pageSize);
        com.setCurrentPage(currentPage);
        Map<String,Object> map = new HashMap<>();
        map.put("vo",gson.toJson(com));
        //异步请求
        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode == 200) {
                            //请求成功
                            Pagination<CommentsVo> pagination;
                            //使用Gson对响应的数据反序列化
                            Type type = new TypeToken<Pagination<CommentsVo>>() {
                            }.getType();
                            pagination = gson.fromJson(response, type);
                            //处理数据
                            if (pagination != null) {
                                //将分页数据添加到recyclerView的适配器
                                List<CommentsVo> listCom = pagination.getData();
                                shareCommectAdapter.addItem(listCom, currentPage);

                                if (isRefresh) {
                                    srlShareCommect.finishRefresh();//刷新完成
                                } else {
                                    srlShareCommect.finishLoadMore();//加载更多完成
                                }
                                //获取数据总页数
                                int totalPage = pagination.getTotalPage();
                                //如果当前页数等于总页数，设置所有页数加载完成提示
                                if (currentPage == totalPage) {
                                    //告诉SmartRefreshLayout没有更多数据了
                                    srlShareCommect.finishLoadMoreWithNoMoreData();
                                }
                            } else {
                                //加载失败
                                if (isRefresh) {
                                    srlShareCommect.finishRefresh(false);//刷新失败
                                } else {
                                    srlShareCommect.finishLoadMore(false);//加载更多失败
                                }
                            }
                        } else {
                            //加载失败
                            if (isRefresh) {
                                srlShareCommect.finishRefresh(false);//刷新失败
                            } else {
                                srlShareCommect.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }



    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                setResult(1,(new Intent()));
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
