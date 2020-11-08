package com.gx.wda.ui.share;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.gx.wda.bean.CommentsVo;
import com.gx.wda.bean.NoticeVo;
import com.gx.wda.dialog.InputDialog;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.MyActionBar;
import com.tencent.smtt.sdk.TbsVideo;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 公告详情
 */
public class NoticeDetailActivity extends AppCompatActivity {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private int noticeId=0;
    private PromptDialog promptDialog;//加载框
    private LinearLayout llContent;
    private ImageView ivCover;
    private TextView tvTheme;
    private TextView tvAuthor;
    private TextView tvPostDate;
    private TextView tvDescribe;
    private LinearLayout llEmpty;
    private TextView tvNoComment;
    private LinearLayout llCommentCon;
    private LinearLayout layoutContent;
    private TextView tvReply;
    private InputDialog inputDialog1;
    private InputDialog inputDialog2;
    private Integer userid;
    private String title = "";
    private String filePath = "";
    private int fileType;
    private final int GET_CODE = 1;//定义页面常量
    private Integer tierType=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        myActivity = this;
        userid = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取本地用户数据


        //初始化加载层
        promptDialog = new PromptDialog(myActivity);


        //得到传递过来的参数
        noticeId = getIntent().getIntExtra("noticeId",0);
        //获取控件
        //最外层LinearLayout
        llContent = findViewById(R.id.ll_notice_content);
        //附件图标
        ivCover = findViewById(R.id.iv_file_type_cover);
        //标题
        tvTheme = findViewById(R.id.tv_notice_theme);
        //作者
        tvAuthor = findViewById(R.id.tv_notice_author);
        //发布时间
        tvPostDate = findViewById(R.id.tv_notice_postdate);
        //说明
        tvDescribe = findViewById(R.id.tv_notice_describe);
        //无数据显示
        llEmpty = findViewById(R.id.ll_empty);
        //评论外围LinearLayout
        llCommentCon = findViewById(R.id.ll_comment_con);
        //无评论显示
        tvNoComment = findViewById(R.id.tv_no_comment_data);
        title = getString(R.string.text_notice_looking_comment);
        //评论输入框
        inputDialog1 = new InputDialog(title, "", InputType.TYPE_CLASS_TEXT, 140, new InputDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(Dialog dialog, String content) {
                String url = ServiceUrls.getShareMethodUrl("addComment");
                CommentsVo comment = new CommentsVo();
                comment.setContent(content);
                //comment.setCommentdate(new Date());
                comment.setNoticeId(noticeId);
                comment.setUserId(userid);
                comment.setReplyId(0);
                comment.setCriticId(0);
                comment.setTierType(0);
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
                                            llCommentCon.removeAllViews();//清空评论列表
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
        //初始化页面
        initView();
        setViewListener();
    }

    private void setViewListener() {
        //观看文件
        ivCover.setOnClickListener(new View.OnClickListener() {
            /*if(TbsVideo.canUseTbsPlayer(getApplicationContext())){
                TbsVideo.openVideo(getApplicationContext(), docUrl);
            }*/
            @Override
            public void onClick(View v) {
                String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (!EasyPermissions.hasPermissions(NoticeDetailActivity.this, perms)) {
                    EasyPermissions.requestPermissions(NoticeDetailActivity.this, getString(R.string.text_jurisdiction_store_none), 10086, perms);
                } else {
                    if(filePath!=""){
                        if(fileType!=1){
                            //非视频文件
                            Intent intent = new Intent(myActivity,TbsActivity.class);
                            intent.putExtra("filePath",filePath);
                            startActivity(intent);
                        }else{
                            //视频
                            if(TbsVideo.canUseTbsPlayer(getApplicationContext())){
                                TbsVideo.openVideo(getApplicationContext(),filePath);
                            }
                        }
                    }else{
                        promptDialog.showError(getString(R.string.text_file_lose));
                    }
                }
            }
        });

        //打开评论输入窗口
        //评论公告
        tvNoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog1.show(getSupportFragmentManager(),"");
            }
        });
        //回复评论
        tvDescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog1.show(getSupportFragmentManager(),"");
            }
        });
    }

    private void initView() {
        //全屏
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        //标题栏
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_notice_detail), R.drawable.ic_back,0,
                1, 0, new MyActionBar.ActionBarClickListener() {
                    @Override
                    public void onLeftClick() {
                        finish();
                    }

                    @Override
                    public void onRightClick() {

                    }
                });

        //清空评论
        llCommentCon.removeAllViews();
        if(noticeId!=0){
            //回填数据
            String url = ServiceUrls.getShareMethodUrl("selectNoticeById");
            Map<String,Object> map = new HashMap<>();
            map.put("id",noticeId);
            map.put("userId",userid);
            OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isSuccess==true && responseCode==200){
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    int code=jsonObject.getInt("code");

                                    if (code==200){
                                        String strData=jsonObject.getString("data");
                                        NoticeVo dbNotice = gson.fromJson(strData, NoticeVo.class);
                                        tvTheme.setText(dbNotice.getNoticeName());
                                        tvAuthor.setText(dbNotice.getUserName());
                                        tvDescribe.setText(dbNotice.getNoticeDescribe());
                                        //时间处理
                                        SimpleDateFormat SHOT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String date =SHOT_DATE.format(dbNotice.getReleaseTime());
                                        tvPostDate.setText(date);
                                        title = "@"+dbNotice.getUserName();
                                        fileType = dbNotice.getFileTypeId();
                                        filePath = ServiceUrls.serviceUrl + dbNotice.getFileName();
                                        int pictureUrl = R.drawable.ic_file;
                                        switch (fileType){
                                            case 1:
                                                pictureUrl = R.drawable.ic_video;
                                                break;
                                            case 2:
                                                pictureUrl = R.drawable.ic_pdf;
                                                break;
                                            case 3:
                                                pictureUrl = R.drawable.ic_word;
                                                break;
                                            case 4:
                                                pictureUrl = R.drawable.ic_excel;
                                                break;
                                            case 5:
                                                pictureUrl = R.drawable.ic_ppt;
                                                break;
                                        }
                                        ivCover.setImageDrawable(getDrawable(pictureUrl));
                                        updateInformState();//修改已读状态

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });
            //查询评论
            findComments(noticeId,0);



        }else{
            //显示无数据控件
            llEmpty.setVisibility(View.VISIBLE);
            //隐藏原控件
            llContent.setVisibility(View.GONE);
        }


    }


    /**
     * 查询评论
     * @param noticeId
     * @param replyId
     */
    private void findComments(final int noticeId, final int replyId){
        // 查询评论
        final String url = ServiceUrls.getShareMethodUrl("selectComments");
        CommentsVo com = new CommentsVo();
        com.setNoticeId(noticeId);
        com.setReplyId(replyId);
        Map<String,Object> map = new HashMap<>();
        map.put("vo",gson.toJson(com));
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isSuccess==true && responseCode==200){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if(code==200){

                                    tvNoComment.setVisibility(View.GONE);
                                    llCommentCon.setVisibility(View.VISIBLE);
                                    String data=jsonObject.getString("data");
                                    Type type=new TypeToken<List<CommentsVo>>(){}.getType();
                                    List<CommentsVo> listCom = gson.fromJson(data,type);
                                    if(replyId==0){
                                        for (int i=0;i<listCom.size();i++){
                                            createComView(listCom.get(i));//生成评论视图
                                        }
                                    }

                                }else{
                                    //无数据
                                    tvNoComment.setVisibility(View.VISIBLE);
                                    llCommentCon.setVisibility(View.GONE);
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
     * 生成评论视图
     * @param com
     */
    private void createComView(final CommentsVo com){
        tierType = com.getCommentId();
        int totalComm = com.getReplyCount();
        //时间处理
        SimpleDateFormat SHOT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date =SHOT_DATE.format(com.getCreationTime());
        //最外层LinearLayout

        layoutContent = new LinearLayout(myActivity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutContent.setLayoutParams(layoutParams);
        layoutContent.setPadding(0,0,0,10);
        layoutContent.setOrientation(LinearLayout.VERTICAL);
        layoutContent.setBackground(getDrawable(R.drawable.bg_border_bottom));

        //用户信息LinearLayout
        final LinearLayout layoutUser = new LinearLayout(myActivity);
        layoutUser.setLayoutParams(layoutParams);
        layoutUser.setOrientation(LinearLayout.HORIZONTAL);

        //用户名 TextView
        final TextView tvUserName = new TextView(myActivity);
        LinearLayout.LayoutParams layoutView = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
        tvUserName.setLayoutParams(layoutView);
        tvUserName.setPadding(20,20,20,20);
        tvUserName.setTextSize(16);
        tvUserName.setGravity(Gravity.CENTER_VERTICAL);
        tvUserName.setText(com.getUserName());

        //时间 TextView
        final TextView tvDate = new TextView(myActivity);
        tvDate.setLayoutParams(layoutView);
        tvDate.setPadding(20,20,20,20);
        tvDate.setTextSize(16);
        tvDate.setGravity(Gravity.RIGHT);
        tvDate.setText(date);

        //将TextView添加进用户信息LinearLayout中
        layoutUser.addView(tvUserName);
        layoutUser.addView(tvDate);

        //评论内容TextView
        final TextView tvCom = new TextView(myActivity);
        tvCom.setLayoutParams(layoutParams);
        tvCom.setPadding(20,20,20,20);
        tvUserName.setTextColor(getResources().getColor(R.color.colorBlack));
        tvCom.setTextSize(16);
        tvCom.setText(com.getContent());

        tvCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = "@ "+ com.getUserName();
                //评论输入框
                inputDialog2 = new InputDialog(title, "", InputType.TYPE_CLASS_TEXT, 140, new InputDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(Dialog dialog, String content) {
                        String url = ServiceUrls.getShareMethodUrl("addComment");
                        CommentsVo comment = new CommentsVo();
                        comment.setContent(content);
                        //comment.setCommentdate(new Date());
                        comment.setNoticeId(noticeId);
                        comment.setUserId(userid);
                        comment.setReplyId(com.getCommentId());
                        comment.setCriticId(com.getUserId());
                        comment.setTierType(tierType);
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
                                                    inputDialog2.hide();
                                                    llCommentCon.removeAllViews();//清空评论列表
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
                inputDialog2.showNow(getSupportFragmentManager(),"");
            }
        });

        tvCom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(com.getUserId()==userid&&userid!=0){
                    MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, myActivity.getResources().getString(R.string.text_comment_delete), new MessageDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm==true){//确定按钮
                                String url = ServiceUrls.getShareMethodUrl("delComment");
                                Map<String,Object> map = new HashMap<>();
                                map.put("commentId",com.getCommentId());
                                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                                    @Override
                                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                                        myActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (isSuccess&&responseCode==200){
                                                    String strMsg = getString(R.string.text_network_anomaly);
                                                    if(isSuccess == true && responseCode == 200){
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response);
                                                            int code = jsonObject.getInt("code");
                                                            if(code==200){
                                                                Toast.makeText(myActivity,getString(R.string.text_delete_success),Toast.LENGTH_SHORT).show();
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
                return true;
            }
        });

        //更多相关评论TextView
        tvReply = new TextView(myActivity);
        layoutParams.setMargins(0,0,0,20);
        tvReply.setLayoutParams(layoutParams);
        tvReply.setBackground(getDrawable(R.color.colorGrayTints));//背景色
        tvReply.setTextColor(getResources().getColor(R.color.colorPrimary));//文字颜色
        tvReply.setPadding(20,20,20,20);
        tvReply.setText(getResources().getString(R.string.text_reply_in_all) +" "+totalComm+" "+getResources().getString(R.string.text_reply_count));
        //点击事件
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myActivity,CommentsDetailActivity.class);
                intent.putExtra("noticeId",noticeId);
                intent.putExtra("replyId",com.getCommentId());
                startActivityForResult(intent,GET_CODE);
            }
        });

        //添加进最外层LinearLayout中
        layoutContent.addView(layoutUser);
        layoutContent.addView(tvCom);
        if (totalComm>0){
            layoutContent.addView(tvReply);
        }

        llCommentCon.addView(layoutContent);

    }
    /**
     * 修改公告通知是否已读状态
     */
    private void updateInformState(){
        String url= ServiceUrls.getShareMethodUrl("updateInformState");
        Map<String,Object> map=new HashMap<>();
        map.put("type",1);
        map.put("userId",userid);
        map.put("id",noticeId);
        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
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
     * 页面跳转回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == GET_CODE) {
            initView();
        }
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
