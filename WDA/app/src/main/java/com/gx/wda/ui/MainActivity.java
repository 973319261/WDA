package com.gx.wda.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.adapter.FriendListAdapter;
import com.gx.wda.bean.CallVo;
import com.gx.wda.bean.InformVo;
import com.gx.wda.bean.Jurisdiction;
import com.gx.wda.bean.UserVo;
import com.gx.wda.bean.Version;
import com.gx.wda.dialog.LogOutDialog;
import com.gx.wda.dialog.PermissionsDialog;
import com.gx.wda.dialog.VersionUpdateDialog;
import com.gx.wda.ui.dataStream.DataStreamFragment;
import com.gx.wda.ui.dataStream.DataStreamStateFragment;
import com.gx.wda.ui.detection.DetectionFragment;
import com.gx.wda.ui.dtc.DtcFragment;
import com.gx.wda.ui.main.AboutActivity;
import com.gx.wda.ui.main.InformActivity;
import com.gx.wda.ui.main.InformDetailActivity;
import com.gx.wda.ui.main.LanguageActivity;
import com.gx.wda.ui.main.LinkVciActivity;
import com.gx.wda.ui.main.TFCardActivity;
import com.gx.wda.ui.main.UpdatePasswordActivity;
import com.gx.wda.ui.security.SecurityFragment;
import com.gx.wda.ui.share.NoticeDetailActivity;
import com.gx.wda.ui.share.ShareFragment;
import com.gx.wda.util.EncodingUtil;
import com.gx.wda.util.LanguageUtil;
import com.gx.wda.util.MPermissionUtils;
import com.gx.wda.util.MyWebSocketClient;
import com.gx.wda.util.NetworkConnectChangedReceiver;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.RedirectException;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.TeleoperationUtil;
import com.gx.wda.util.Tools;
import com.gx.wda.widget.BadgeHelper;
import com.gx.wda.widget.MyActionBar;
import com.suke.widget.SwitchButton;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePusher;
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.leefeng.promptlibrary.PromptDialog;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

/**
 * 主页页面
 */
public class MainActivity extends AppCompatActivity {
    private static Activity myActivity;//上下文
    private static MyApplication myApplication;//全局
    //控件
    private LinearLayout llContent; //内容容器
    private RadioButton rbSecurity;//安全算法
    private RadioButton rbDtc;//DTC
    private RadioButton rbData;//数据流
    private RadioButton rbDetection;//检测
    private RadioButton rbShare;//分享
    private DrawerLayout drawerLayout;//抽屉页面
    private LinearLayout llTF;//TF录制与配置
    private LinearLayout llLinkVCI;
    private LinearLayout llUpdatePassword;//更改密码
    private LinearLayout llUpdateLanguage;//更改语言
    private LinearLayout llAbout;//关于
    private LinearLayout llLogout;//退出
    public static TextView tvLinkVci;//连接VCI
    private TextView tvLanguage;//语言
    private TextView tvVersion;//版本号
    private TextView tvAccount;//账号
    private TextView tvUserName;//用户名称
    private TextView tvRoleType;//角色类型
    private TextView tvExpirationDate;//到期时间
    private TextView redPoint;//版本提示
    private MyActionBar myActionBar;//标题栏
    private List<Jurisdiction> jurisdictionList ;//权限列表
    private UserVo user;
    private VersionUpdateDialog versionUpdateDialog;//更新弹窗
    private final String TAG=MainActivity.class.getSimpleName();
    private Fragment[] fragments = new Fragment[]{null, null, null,null,null};//存放Fragment
    private long startTime = 0;
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    //VCI相关
    public static Context context;
    public static String VCI_IP = "";//IP
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    //注册手机热点监听广播
    IntentFilter wifiFilter = new IntentFilter();
    //VCI连接子线程
    private Handler handlerVCI;
    private PromptDialog promptDialog;//加载框
    /* 用于 udpReceiveAndTcpSend 的3个变量 */
    Socket socket = null;
    MulticastSocket ms = null;
    DatagramPacket dp;
    private static DatagramSocket reSocket;
    private  IConnectionManager mManager=null;

    /**
     * App前后台状态
     */
    public boolean isForeground = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 如果当前不是竖向就切换成竖向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;//设置上下文
        context=this;
        myApplication= (MyApplication) myActivity.getApplication();
        //关闭TCP连接
        mManager=myApplication.getConnectionManager();
        if(mManager!=null){
            mManager.disconnect();
        }
        //重置蜂鸣状态
        myApplication.setBuzzing(false);
        Locale locale = LanguageUtil.getAppLocale(this);//获取本地的语言
        LanguageUtil.changeAppLanguage(this,locale,true);//设置语言
        setContentView(R.layout.activity_main);
        //获取控件
        llContent = findViewById(R.id.ll_main_content);
        rbSecurity = findViewById(R.id.rb_main_security);
        rbDtc = findViewById(R.id.rb_main_dtc);
        rbData = findViewById(R.id.rb_main_data);
        rbDetection = findViewById(R.id.rb_main_detection);
        rbShare = findViewById(R.id.rb_main_share);
        myActionBar = findViewById(R.id.myActionBar);
        drawerLayout =  findViewById(R.id.drawerLayout);
        llTF=findViewById(R.id.ll_TF);
        llLinkVCI = findViewById(R.id.ll_link_vci);
        llUpdatePassword=findViewById(R.id.ll_update_password);
        llUpdateLanguage=findViewById(R.id.ll_update_language);
        llAbout=findViewById(R.id.ll_about);
        llLogout=findViewById(R.id.ll_logout);
        tvLinkVci=findViewById(R.id.tv_main_link_vci);
        tvAccount=findViewById(R.id.tv_main_account);
        tvUserName=findViewById(R.id.tv_main_user_name);
        tvRoleType=findViewById(R.id.tv_main_role_type);
        tvExpirationDate=findViewById(R.id.tv_main_expiration_date);
        tvLanguage=findViewById(R.id.tv_main_language);
        tvVersion=findViewById(R.id.tv_main_versions);
        redPoint=findViewById(R.id.tv_red_point);
        String language= LanguageUtil.getAppLanguage(myActivity);//获取保存到本地的语言
        if ("zh".equals(language)){
            tvLanguage.setText("简体中文");//设置语言文本
        }else if ("en".equals(language)){
            tvLanguage.setText("English");//设置语言文本
        }
        //侧滑菜单
        myActionBar.setData(myActivity,getResources().getString(R.string.text_main_security), R.drawable.ic_user, R.drawable.ic_inform, 0, getResources().getColor(R.color.colorPrimary), new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                drawerLayout.openDrawer(GravityCompat.START);//openDrawer方法用于将抽屉打开
            }

            @Override
            public void onRightClick() {
                myApplication.setVciRun(false);//关闭VCI监听
                Intent intent=new Intent(myActivity, InformActivity.class);
                startActivity(intent);
            }
        });
        initView();//初始化页面
        setViewListener();//设置监听事件

        //判断是否开启通知栏权限
        boolean isOpen = NotificationManagerCompat.from(context).areNotificationsEnabled();
        if(!isOpen){
            String content="未开启通知栏权限，建议你立即前往启用";
            final PermissionsDialog permissionsDialog = new PermissionsDialog(myActivity,content);
            permissionsDialog.setOnOpenListener(new PermissionsDialog.OnOpenListener() {
                @Override
                public void onOpen(Dialog dialog) {
                    goToSetting();
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
     * 跳转到通知栏权限开启界面
     */
    private void goToSetting() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {// android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) { // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);
        } else {//其它
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }














    /*页面初始化*/
    private void initView() {
        myApplication.setActionBar(myActionBar);
        //设置导航栏图标样式
        Drawable iconSecurity=getResources().getDrawable(R.drawable.selector_main_rb_security);//设置主页图标样式
        iconSecurity.setBounds(0,0,68,68);//设置图标边距 大小
        rbSecurity.setCompoundDrawables(null,iconSecurity,null,null);//设置图标位置
        rbSecurity.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconDtc=getResources().getDrawable(R.drawable.selector_main_rb_dtc);//设置主页图标样式
        iconDtc.setBounds(0,0,60,60);//设置图标边距 大小
        rbDtc.setCompoundDrawables(null,iconDtc,null,null);//设置图标位置
        rbDtc.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconData=getResources().getDrawable(R.drawable.selector_main_rb_data);//设置主页图标样式
        iconData.setBounds(0,0,60,60);//设置图标边距 大小
        rbData.setCompoundDrawables(null,iconData,null,null);//设置图标位置
        rbData.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconDetection=getResources().getDrawable(R.drawable.selector_main_rb_detection);//设置主页图标样式
        iconDetection.setBounds(0,0,60,60);//设置图标边距 大小
        rbDetection.setCompoundDrawables(null,iconDetection,null,null);//设置图标位置
        rbDetection.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconShare=getResources().getDrawable(R.drawable.selector_main_rb_share);//设置主页图标样式
        iconShare.setBounds(0,0,60,60);//设置图标边距 大小
        rbShare.setCompoundDrawables(null,iconShare,null,null);//设置图标位置
        rbShare.setCompoundDrawablePadding(5);//设置文字与图片的间距
        tvVersion.setText(String.format(Locale.getDefault(),"v%s",Tools.getVersionName(myActivity)));//设置版本信息
        //用户管理权限
        jurisdictionList = myApplication.getJurisdictionList();
        if (!jurisdictionList.get(1).getIsEnable()) {//TF卡应用
            llTF.setVisibility(View.GONE);//GONE
        }
        if (!jurisdictionList.get(9).getIsEnable() && !jurisdictionList.get(10).getIsEnable()
                && !jurisdictionList.get(11).getIsEnable() &&!jurisdictionList.get(12).getIsEnable()){//分享
            rbShare.setVisibility(View.GONE);//GONE
        }else {
            rbShare.setChecked(true);
            myActionBar.setTitle(getResources().getString(R.string.text_main_share));
            switchFragment(4);//默认显示HomeFragment
        }
        if (!jurisdictionList.get(7).getIsEnable() && !jurisdictionList.get(8).getIsEnable()){//检测
            rbDetection.setVisibility(View.GONE);//GONE
        }else {
            rbDetection.setChecked(true);
            myActionBar.setTitle(getResources().getString(R.string.text_main_detection));
            switchFragment(3);//默认显示HomeFragment
        }
        if (!jurisdictionList.get(4).getIsEnable() && !jurisdictionList.get(5).getIsEnable()
                && !jurisdictionList.get(6).getIsEnable()){//数据流
            rbData.setVisibility(View.GONE);//GONE
        }else {
            rbData.setChecked(true);
            myActionBar.setTitle(getResources().getString(R.string.text_main_data));
            switchFragment(2);//默认显示HomeFragment
        }
        if (!jurisdictionList.get(2).getIsEnable() && !jurisdictionList.get(3).getIsEnable()){//DTC
            rbDtc.setVisibility(View.GONE);//GONE
        }else {
            rbDtc.setChecked(true);
            myActionBar.setTitle(getResources().getString(R.string.text_main_dtc));
            switchFragment(1);//默认显示HomeFragment
        }
        if (!jurisdictionList.get(0).getIsEnable()) {//安全算法
            rbSecurity.setVisibility(View.GONE);//GONE
        }else {
            rbSecurity.setChecked(true);
            myActionBar.setTitle(getResources().getString(R.string.text_main_security));
            switchFragment(0);//默认显示HomeFragment
        }
        setDrawer();//设置drawer相关动画和滑动屏幕范围
        String userStr = (String) SPUtils.get(myActivity,SPUtils.SP_USER,"");//获取本地用户数据
        Type type=new TypeToken<UserVo>(){}.getType();
        user = gson.fromJson(userStr,type);
        promptDialog=new PromptDialog(myActivity);
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        if (user!=null){
            tvAccount.setText(user.getUserAccount());//设置账号
            tvUserName.setText(user.getUserName());//设置昵称
            tvRoleType.setText(user.getRoleName().trim());//设置角色类型
            tvExpirationDate.setText(sf.format(user.getExpirationDate()));//设置到期时间
        }
        //注册网络状态监听
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkConnectChangedReceiver networkChang=new NetworkConnectChangedReceiver();
        myActivity.registerReceiver(networkChang,intentFilter);

        //判断是否为8.0以上：Build.VERSION_CODES.O为26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";//创建通知渠道ID
            String channelName = "公告消息";//创建通知渠道名称
            int importance = NotificationManager.IMPORTANCE_HIGH;//创建通知渠道重要性
            createNotificationChannel(channelId, channelName, importance);
        }
        //注册手机热点监听广播
        wifiFilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        myActivity.registerReceiver(new HotsspotReceiver(), wifiFilter);
        checkVersion();//检测版本
        loadSocketClient();//连接消息推送服务
        /* 开一个线程 接收udp多播 并 发送tcp 连接*/
        new udpReceiveAndtcpSend().start();
    }

    /**
     * 检测版本
     */
    private void checkVersion(){
        BadgeHelper badgeHelper = new BadgeHelper(context);
        badgeHelper.setBadgeType(BadgeHelper.Type.TYPE_POINT)
                .setBadgeOverlap(false)
                .bindToTargetView(redPoint);
        String url= ServiceUrls.getVersionMethodUrl("getVersionInfo");
        Map<String,Object> map=new HashMap<>();
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                if (code==200){
                                    String data=jsonObject.getString("data");
                                    Type type=new TypeToken<Version>(){}.getType();
                                    Version version=gson.fromJson(data,type);
                                    //判断是否需要更新
                                    if (Integer.valueOf(version.getVersionNumber())> Tools.getVersionCode(myActivity)){
                                       /* boolean flag = (boolean) SPUtils.get(myActivity,SPUtils.SP_VERSION_FIRST,true);
                                        if (flag || version.getIsForce()){
                                            SPUtils.put(myActivity,SPUtils.SP_VERSION_FIRST,false);
                                        }
                                        SPUtils.put(myActivity,SPUtils.SP_VERSION_NEW,true);
                                        */
                                        myActionBar.setBadgeLeftShow();//小红点提示
                                        badgeHelper.setBadgeEnable(true);//显示提示
                                        versionUpdateDialog=new VersionUpdateDialog(myActivity,version);
                                        versionUpdateDialog.show(getSupportFragmentManager(),"");
                                        SPUtils.put(myActivity,SPUtils.SP_VERSION_NEW,true);
                                    }else {
                                        myActionBar.setBadgeLeftHide();
                                        badgeHelper.setBadgeEnable(false);//关闭
                                        SPUtils.put(myActivity,SPUtils.SP_VERSION_NEW,false);
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
    }
    /**
     * 连接消息推送服务
     */
    MyWebSocketClient  client;
    CountDownTimer timer;
    private void loadSocketClient(){
        // 此处的WebSocket服务端URI，
        URI uri=URI.create(ServiceUrls.serviceUrl+"websocket/"+user.getUserId());
        client = new MyWebSocketClient(uri){
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                Type type=new TypeToken<InformVo>(){}.getType();
                try {
                    JSONObject jsonObject=new JSONObject(message);
                    int status = jsonObject.getInt("status");
                    String data=jsonObject.getString("data");//获取信息
                    if (status==1){//发布（公告通知）新内容
                        InformVo informVo=gson.fromJson(data,type);
                        if (informVo.getTypeId()==0){
                            Intent intent = new Intent(myActivity, InformDetailActivity.class);
                            intent.putExtra("informId",informVo.getInformId());
                            PendingIntent pendingIntent = PendingIntent.getActivity(myActivity, 0, intent, FLAG_CANCEL_CURRENT);
                            //在通知栏通知
                            Notification notification = new NotificationCompat.Builder(myActivity, "chat")
                                    .setAutoCancel(true)
                                    .setContentTitle(informVo.getInformTitle())
                                    .setContentText(informVo.getInformContent())
                                    .setContentIntent(pendingIntent)
                                    .setWhen(System.currentTimeMillis())
                                    .setSmallIcon(R.drawable.ic_logo)
                                    .setColor(Color.parseColor("#F00606"))  //设置红色
                                    .build();
                            myApplication.notificationManager.notify(0, notification);
                        }else {
                            Intent intent = new Intent(myActivity, NoticeDetailActivity.class);
                            intent.putExtra("noticeId",informVo.getInformId());
                            PendingIntent pendingIntent = PendingIntent.getActivity(myActivity, 0, intent, FLAG_CANCEL_CURRENT);
                            //在通知栏通知
                            Notification notification = new NotificationCompat.Builder(myActivity, "chat")
                                    .setAutoCancel(true)
                                    .setContentTitle(informVo.getInformTitle())
                                    .setContentText(informVo.getInformContent())
                                    .setContentIntent(pendingIntent)
                                    .setWhen(System.currentTimeMillis())
                                    .setSmallIcon(R.drawable.ic_logo)
                                    .setColor(Color.parseColor("#F00606"))  //设置红色
                                    .build();
                            myApplication.notificationManager.notify(0, notification);
                        }
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myActionBar.setBadgeRightShow();
                            }
                        });
                    }else if (status==1001){//请求对方通话
                        turnOnScreen();//唤醒锁屏
                        Intent intent=new Intent(myActivity,LiveActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        intent.putExtra("data",data);
                        startActivity(intent);
                    }else if (status==1002) {//对方接受通话
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //在通知栏通知
                                Notification notification = new NotificationCompat.Builder(myActivity, "chat")
                                        .setContentTitle(myApplication.getPlayerAccount())
                                        .setOngoing(true)
                                        .setContentText(getString(R.string.text_live_in_call))
                                        .setWhen(System.currentTimeMillis())
                                        .setSmallIcon(R.drawable.ic_logo)
                                        .setColor(Color.parseColor("#F00606"))  //设置红色
                                        .build();
                                myApplication.notificationManager.notify(1, notification);
                                TXLivePusher mLivePusher=myApplication.getTxLivePusher();
                                TXLivePlayer mLivePlayer=myApplication.getTxLivePlayer();
                                  if (mLivePusher!=null){
                                      //==开始推流
                                      String rtepUrl=ServiceUrls.rtmpUrl+myApplication.getRoomNo();//地址推流
                                      mLivePusher.startPusher(rtepUrl);
                                      mLivePusher.startScreenCapture();
                                }
                                  if (mLivePlayer!=null){
                                      //开始拉流
                                      String voiceUrl=ServiceUrls.rtmpUrl+"v"+myApplication.getRoomNo();//拉流地址
                                      mLivePlayer.startPlay(voiceUrl,TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
                                  }
                                CallVo callVo=myApplication.getCallVo();
                                  if (callVo!=null){
                                      callVo.getLlHands().setVisibility(View.VISIBLE);//显示免提
                                      callVo.getLlMute().setVisibility(View.VISIBLE);//显示禁麦
                                      callVo.getTvHint().setText(R.string.text_live_connecting);
                                      startTime=0;//初始化时间
                                      timer=new CountDownTimer(24*60*60*1000,1000) {
                                          @Override
                                          public void onTick(long millisUntilFinished) {
                                              String content = Tools.showTimeCount(System.currentTimeMillis() - startTime);
                                              callVo.getTvHint().setText(content);
                                          }

                                          @Override
                                          public void onFinish() {

                                          }
                                      };
                                      myApplication.setTimer(timer);
                                  }
                            }
                        });
                    }else if (status==1003) {//对方拒绝通话(响应推流端)
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(myActivity, R.string.text_live_reject_call,Toast.LENGTH_LONG).show();
                                CallVo callVo=myApplication.getCallVo();
                                if (callVo!=null){
                                    callVo.getLlHands().setVisibility(View.GONE);//隐藏免提
                                    callVo.getLlMute().setVisibility(View.GONE);//隐藏禁麦
                                    callVo.getLlConnect().setVisibility(View.VISIBLE);//显示连接按钮
                                    callVo.getLlFriendList().setVisibility(View.VISIBLE);//显示好友列表
                                    callVo.getLlCall().setVisibility(View.GONE);//隐藏通话页
                                    callVo.getTvHint().setText(getString(R.string.text_live_await));
                                }
                            }
                        });
                    }else if (status==1004) {//通话结束
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(myActivity,R.string.text_live_vreceng,Toast.LENGTH_LONG).show();
                                myApplication.notificationManager.cancel(1);
                                CallVo callVo=myApplication.getCallVo();
                                if (callVo!=null){
                                    callVo.getLlHands().setVisibility(View.GONE);//隐藏免提
                                    callVo.getLlMute().setVisibility(View.GONE);//隐藏禁麦
                                    callVo.getLlConnect().setVisibility(View.VISIBLE);//显示连接按钮
                                    callVo.getLlFriendList().setVisibility(View.VISIBLE);//显示好友列表
                                    callVo.getLlCall().setVisibility(View.GONE);//隐藏通话页
                                    callVo.getLlMuteWhite().setVisibility(View.VISIBLE);
                                    callVo.getLlMuteBlack().setVisibility(View.GONE);
                                    callVo.getLlHandsWhite().setVisibility(View.VISIBLE);
                                    callVo.getLlHandsBlack().setVisibility(View.GONE);
                                    callVo.getTvHint().setText(getString(R.string.text_live_await));
                                }
                                myApplication.closeLiveActivity();//关闭拉流（通话）页面
                                myApplication.destroy();//释放拉流推流资源
                            }
                        });
                    }else if (status==1005) {//通话成功
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (startTime==0){
                                    startTime=System.currentTimeMillis();
                                }
                                timer.start();
                                Toast.makeText(myActivity, R.string.text_live_successfu,Toast.LENGTH_LONG).show();
                            }
                        });
                    }else if (status==1006){//远程控制操作
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonData = null;
                                try {
                                    jsonData = new JSONObject(data);
                                    Float x=Float.parseFloat(jsonData.getString("x"));
                                    Float y=Float.parseFloat(jsonData.getString("y"));
                                    Float x1=Float.parseFloat(jsonData.getString("x1"));
                                    Float y1=Float.parseFloat(jsonData.getString("y1"));
                                    TeleoperationUtil.sendControl(x,y,x1,y1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else if (status==2001){//更新好友列表在线（离线）状态
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FriendListAdapter friendListAdapter = myApplication.getFriendListAdapter();
                                if (friendListAdapter!=null){
                                    Type type = new TypeToken<List<UserVo>>() {}.getType();
                                    List<UserVo> list = gson.fromJson(data, type);
                                    friendListAdapter.addItem(list);
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("回调",message);
            }
        };
        try {
            client.connectBlocking();
            // 往websocket服务端发送用户ID
            client.send(String.valueOf(user.getUserId()));
            myApplication.setMyWebSocketClient(client);//保存对象到内存
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
    //唤醒锁屏
    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager;
    @SuppressLint("InvalidWakeLockTag")
    public void turnOnScreen() {
        // turn on screen
        try {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
            mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            mWakeLock.acquire();
            mWakeLock.release();
        } catch (Exception e) {

        }
    }
    final long HEART_BEAT_RATE = 3 * 1000;//每隔3秒进行一次对长连接的心跳检测
    Handler mHandler = new Handler();
    Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (client != null) {
                if (client.isClosed()) {//连接关闭
                    reconnectWs();
                }
            }
            //定时对长连接进行心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * vci连接热点超时
     * @return
     */
    static CountDownTimer timerLink = null;
    public static CountDownTimer timerLink(){
        if(timerLink==null){
            timerLink=new CountDownTimer(1000*60,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.context,"请检查热点是否已打开",Toast.LENGTH_LONG).show();
                    cancel();
                }
            };
        }

        //timerLink.start();
        return timerLink;
    }
    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    if (myApplication.getMyWebSocketClient()!=null){
                        //重连
                        client.reconnectBlocking();
                        if (client.isOpen()){//连接成功
                            // 往websocket服务端发送用户ID
                            client.send(String.valueOf(user.getUserId()));
                        }
                        Log.i("i","重新连接socket");
                    }else {

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 自定义数据包头包体解析
     */
    public static class myReaderProtocol implements IReaderProtocol {
        @Override
        public int getHeaderLength() {
            return 4;
        }

        @Override
        public int getBodyLength(byte[] header, ByteOrder byteOrder) {
            int len = 0;
            String HEADER = EncodingUtil.toHexString(header).substring(0,4);
            // Log.e("头文件",EncodingUtil.toHexString(header));
            if(HEADER.contains("AA00")||HEADER.contains("AAFE")){

                //Log.e("长度帧原数据",EncodingUtil.toHexString(header).substring(4,8));
                //获取长度帧
                int lens = (int) Long.parseLong(EncodingUtil.toHexString(header).substring(4,6),16);
                if(lens<=100){
                    //Log.e("长度帧解析",String.valueOf(lens));
                    return lens+1;//加一个校验位
                }
            }
            return len;
        }
    }


    /**
     * 打印日志
     * @param log
     */
    private void logSend(String log) {
        Log.e("提示",log);
    }

    /**
     * 发送报文
     * @param msg
     */
    public static void okSocketSend(String msg){
        Log.e("发送报文",msg);
        IConnectionManager connectionManager = myApplication.getConnectionManager();
        if(connectionManager!=null&&connectionManager.isConnect()){
            connectionManager.send(new ISendable() {
                @Override
                public byte[] parse() {
                    byte[] b = EncodingUtil.hexToByte(msg);
                    return b;
                }
            });
        }

    }

    /**
     * 定义心跳管理器需要的心跳数据类型
     */
    public class PulseData implements IPulseSendable {

        @Override
        public byte[] parse() {
            String circulation = "AA0110000000080010070000023E800000000000".replace(" ","");
            String circulationXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(circulation));
            circulation += circulationXor;
            String sendMsg = circulation;
            byte[] b = EncodingUtil.hexToByte(sendMsg);
            return b;
        }
    }
    private PulseData mPulseData = new PulseData();




    /**
     * Socket回调
     */
    public SocketActionAdapter socketAdapter = new SocketActionAdapter() {

        //连接成功回调
        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            //开启心跳管理器
            OkSocket.open(info)
                    .getPulseManager()
                    .setPulseSendable(mPulseData)//只需要设置一次,下一次可以直接调用pulse()
                    .pulse();//开始心跳,开始心跳后,心跳管理器会自动进行心跳触发

            logSend("连接成功(Connecting Successful)");
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myApplication.setLinkState(false);
                    MainActivity.timerLink().cancel();//取消定时
                    MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_connected));
                }
            });

            //连接成功，蜂鸣通知
            if(!myApplication.isBuzzing()){
                myApplication.setBuzzing(true);


                //蜂鸣提示
                String buzzing = "AA FE 0E 00 03 00 0A 00 60 09 A0 00 80 05 A0 00 01 00".replace(" ","");
                String xor = EncodingUtil.getBCC(EncodingUtil.hexToByte(buzzing));
                buzzing += xor;
                okSocketSend(buzzing);

                /**
                 * 设置VCI时间
                 */
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            //配置VCI的时间
                            String UTC = String.valueOf(System.currentTimeMillis()+(8*3600*1000)).substring(0,10);//获取当前UTC精确到秒  加时区8H
                            String hexUTC =EncodingUtil.sToB(Integer.toHexString(Integer.parseInt(UTC)).split(""));

                            String setVciTime = ("AA FE 08 00 00 00 04 00"+hexUTC).replace(" ","");
                            String setTimeXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(setVciTime));
                            setVciTime += setTimeXor;
                            okSocketSend(setVciTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();







                //查询VCI触发状态
                /**
                 * 查询永久触发状态
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);//休眠
                            //永久触发
                            String continuousRecord = "AA FE 05 00 02 80 01 00 02".replace(" ","");
                            String continuousXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(continuousRecord));
                            continuousRecord += continuousXor;
                            String sendContinuous = continuousRecord;
                            okSocketSend(sendContinuous);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                //硬件触发
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                            String softRecord = "AA FE 05 00 02 80 01 00 08".replace(" ","");
                            String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                            softRecord += softXor;
                            okSocketSend(softRecord);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                //软触发
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            String softRecord = "AA FE 05 00 02 80 01 00 01".replace(" ","");
                            String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                            softRecord += softXor;
                            okSocketSend(softRecord);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                //永久映射
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3500);
                            String softRecord = "AA 01 10 00 00 00 08 00 10 07 00 00 03 22 C1 10 00 00 00 00".replace(" ","");
                            String softXor = EncodingUtil.getBCC(EncodingUtil.hexToByte(softRecord));
                            softRecord += softXor;
                            okSocketSend(softRecord);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        }


        //连接断开回调
        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            if (e != null) {
                if (e instanceof RedirectException) {
                    logSend("正在重定向连接(Redirect Connecting)...");
                    mManager.switchConnectionInfo(((RedirectException) e).redirectInfo);
                    mManager.connect();
                } else {
                    logSend("异常断开(Disconnected with exception):" + e.getMessage());
                }
            } else {
                logSend("正常断开(Disconnect Manually)");
            }
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myApplication.setLinkState(false);
                    MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_ununited));
                }
            });

        }

        //连接失败回调
        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
            if(Tools.isNotNull(myApplication.getVciIp())){
                logSend("连接失败(Connecting Failed)");
            /*mManager.switchConnectionInfo(((RedirectException) e).redirectInfo);
            mManager.connect();*/
            }
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myApplication.setLinkState(false);
                    MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_ununited));
                }
            });
        }

        //接收数据回调
        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            String msg = EncodingUtil.toHexString(data.getHeadBytes()) + EncodingUtil.toHexString(data.getBodyBytes());
            //变更连接状态
            if(!myApplication.isLinkState()){
                myApplication.setLinkState(true);
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_connected));
                    }
                });
            }
            if(msg.contains("AA00")||msg.contains("AAFE")){
// Log.e("报文",msg);
                String MSG_START = msg.substring(0,4);

                if(MSG_START.contains("AAFE")||msg.toUpperCase().contains("0462C110")){
                    //接收硬件状态查询信息报文
                    //Log.e("查询VCI状态",msg);
                    /*Intent intent =new Intent();
                    intent.setAction("tcpMainReceiver");
                    intent.putExtra("tcpMainReceiver",msg);
                    MainActivity.context.sendBroadcast(intent);//将消息发送给主界面*/
                    if(msg.toUpperCase().contains("0462C110")){

                        //获取映射状态
                        String MAP_STATE = msg.substring(40,42);

                        if(MAP_STATE.contains("00")){
                            //永久映射未开启
                            if (mapNotify!=null){
                                myApplication.notificationManager.cancel(522);//关闭通知栏
                            }
                        }else {
                            //已开启永久映射
                            setNotification("永久映射模式已开启",522);
                        }
                    }else{
                       // Log.e("Main接收的报文",msg);
                        //获取触发方式  0——未触发，1——连续记录模式，2——软触发模式，3——硬件触发模式，4——错误触发模式
                        String TRIGGER_WAY = msg.substring(16,18);
                        // Log.e("TRIGGER_WAY",TRIGGER_WAY);
                        //获取触发状态 0——关闭记录功能，1——记录CAN1(6/14),2——记录CAN2(3/11),3——同时记录两路
                        String TRIGGER_STATE = msg.substring(18,20);

                        //Log.e("TRIGGER_STATE",TRIGGER_STATE);
                        if(TRIGGER_WAY.contains("01")){
                            //连续记录模式
                            if(TRIGGER_STATE.contains("00")){
                                if (permanentNotify!=null){
                                    myApplication.notificationManager.cancel(521);//关闭通知栏
                                }
                                SPUtils.put(myActivity,SPUtils.SP_PERMANENT_TRIGGER,false);
                                Log.e("连续触发模式","已关闭");
                            }else if(TRIGGER_STATE.contains("01")){
                                setNotification("连续触发模式正在记录CAN1(4/16)",521);
                                SPUtils.put(myActivity,SPUtils.SP_PERMANENT_TRIGGER,true);
                                Log.e("连续触发模式","正在记录CAN1(4/16)");
                            }else if(TRIGGER_STATE.contains("02")){
                                setNotification("连续触发模式正在记录CAN2(3/11)",521);
                                SPUtils.put(myActivity,SPUtils.SP_PERMANENT_TRIGGER,true);
                                Log.e("连续触发模式","正在记录CAN2(3/11)");
                            }else if(TRIGGER_STATE.contains("03")){
                                setNotification("连续触发模式正在记录CAN1(4/16)和CAN2(3/11)",521);
                                SPUtils.put(myActivity,SPUtils.SP_PERMANENT_TRIGGER,true);
                                Log.e("连续触发模式","正在记录CAN1(4/16)和CAN2(3/11)");
                            }
                        }else if(TRIGGER_WAY.contains("02")||TRIGGER_WAY.contains("00")){
                            //记录模式
                            if(TRIGGER_STATE.contains("00")){
                                if (softNotify!=null){
                                    myApplication.notificationManager.cancel(520);//关闭通知栏
                                }
                                SPUtils.put(myActivity,SPUtils.SP_SOFT_TRIGGER,false);
                                Log.e("软件触发模式","已关闭");
                            }else if(TRIGGER_STATE.contains("01")){
                                SPUtils.put(myActivity,SPUtils.SP_SOFT_TRIGGER,true);
                                setNotification("软件触发模式正在记录CAN1(4/16)",520);
                                Log.e("软件触发模式","正在记录CAN1(4/16)");
                            }else if(TRIGGER_STATE.contains("02")){
                                SPUtils.put(myActivity,SPUtils.SP_SOFT_TRIGGER,true);
                                setNotification("软件触发模式正在记录CAN2(3/11)",520);
                                Log.e("软件触发模式","正在记录CAN2(3/11)");
                            }else if(TRIGGER_STATE.contains("03")){
                                setNotification("软件触发模式正在记录CAN1(4/16)和CAN2(3/11)",520);
                                SPUtils.put(myActivity,SPUtils.SP_SOFT_TRIGGER,true);
                                if (DataStreamStateFragment.context!=null){
                                    DataStreamStateFragment.llTriggerReocrd.setVisibility(View.GONE);
                                    DataStreamStateFragment.llTriggerOver.setVisibility(View.VISIBLE);
                                }
                                Log.e("软件触发模式","正在记录CAN1(4/16)和CAN2(3/11)");
                            }
                        }

                    }
                }else {
                    if(msg.length()>=50){
                        //接收映射和总线监听报文
                        String msgStr = "";
                        String MSG_HEADER = msg.substring(0,8);//获取报头
                        String MSG_BCC = msg.substring(msg.length()-2,msg.length());//获取校验位
                        int MSG_LEN = Integer.parseInt(msg.substring(4,6),16)/20;//长度帧;
                        for (int i=0;i<MSG_LEN;i++){
                            int math = i*40;
                            String timer = msg.substring(8+math,20+math);
                            String dlc = msg.substring(20+math,24+math);
                            String canid = msg.substring(24+math,32+math);
                            String datas = msg.substring(32+math,48+math);

                            String map = "AA001400"+timer+dlc+canid+datas+MSG_BCC;
                            msgStr+="AA001400"+timer+dlc+canid+datas+MSG_BCC+",";

                            //AA001400 + 时间戳 + 080018070000065003003201F4 + 填充位 + 校验位  映射首次回应的都是这个
                            //临时映射成功 080018070000057101F00500
                            //  080018070000047101F005 取消临时映射
                            //永久映射
                            // 得到的Seed值 080018070000066701
                            // 已收到Key的报文  026702
                            // 完成永久映射  036EC110
                            //错误帧 080018070000037F + 服务 + 78（等待中，不作处理）/其他（错误帧，需要提示）
                            if (map.toUpperCase().contains("080018070000065003003201F4")
                                    || map.toUpperCase().contains("080018070000057101F005")
                                    || map.toUpperCase().contains("080018070000057101F004")
                                    || map.toUpperCase().contains("080018070000066701") || map.toUpperCase().contains("670100000000")
                                    || map.toUpperCase().contains("026702")
                                    || map.toUpperCase().contains("036EC110")
                                    || map.toUpperCase().contains("080018070000047101F005")
                                    || map.toUpperCase().contains("080018070000037F")) {
                                //将报文发送给配置页面
                                Intent intent = new Intent();
                                intent.setAction("tcpMapReceiver");
                                intent.putExtra("tcpMapReceiver", map);
                                MainActivity.context.sendBroadcast(intent);//将报文发送给配置页面
                                map = "";
                            }
                        }

                        if (DataStreamStateFragment.context != null) {
                            Intent intent = new Intent();
                            intent.setAction("tcpClientReceiver");
                            intent.putExtra("tcpClientReceiver", msgStr);
                            DataStreamStateFragment.context.sendBroadcast(intent);//将消息发送给报文监听界面
                            msgStr = "";
                        }
                    }




                }
            }

        }

        //发送数据回调
        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            //logSend(action);
        }

        //喂狗回调
        @Override
        public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
            if(mManager != null){//是否是心跳返回包,需要解析服务器返回的数据才可知道
                //喂狗操作
                mManager.getPulseManager().feed();
            }
        }

    };



    /**
     * 连接VCI
     */
    public void linkVCI() throws IOException {

        String vciIP = myApplication.getVciIp();
        if(Tools.isNotNull(vciIP)){

          /*  IntentFilter intentFilter = new IntentFilter("tcpMainReceiver");
            myActivity.getApplicationContext().registerReceiver(myBroadcastReceiver,intentFilter);
            tcpClient = new TcpClient(myApplication,myApplication.getVciIp(),ServiceUrls.VCI_PORT);
            exec.execute(tcpClient);*/



            ConnectionInfo mInfo = new ConnectionInfo(myApplication.getVciIp(),ServiceUrls.VCI_PORT);

            if(mManager==null){
                mManager = OkSocket.open(mInfo);
                myApplication.setConnectionManager(mManager);

                //设置自定义解析头
                OkSocketOptions.Builder okOptionsBuilder = new OkSocketOptions.Builder(mManager.getOption());
                okOptionsBuilder.setReaderProtocol(new myReaderProtocol());

                //将新的修改后的参配设置给连接管理器
                mManager.option(okOptionsBuilder.build());

                mManager.registerReceiver(socketAdapter);
                mManager.connect();
            }


        }else{
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);//休眠3秒
                        linkVCI();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }

    /**
     *
     * @throws IOException
     */
    public static void sendBroadcast(Activity activity, String msg) throws IOException {
        DatagramSocket socket = new DatagramSocket(13047);
        String msgs = msg.replace(" ","");
        String CRC8_ITU = EncodingUtil.CRC8(EncodingUtil.hexToByte(msgs));
        msgs = msgs+CRC8_ITU.toUpperCase();


        Log.e("广播报文：", msgs);
        Log.e("广播报文长度：", String.valueOf(msgs.length()));
        byte[] data = msg.getBytes();
        byte[] data2 = EncodingUtil.hexToByte(msgs);
        //创建DatagramPacket对象，用来存放发送的数据，端口和ip
        DatagramPacket packet = new DatagramPacket(data2,data2.length);
        packet.setPort(58121);
        packet.setAddress(InetAddress.getByName("192.168.43.255"));

        //使用DatagramSocket的send方法，发送数据包
        socket.send(packet);
        socket.close();
        // new Thread(new SearchListener()).start();
        //latch.countDown();
        new Thread(new SearchListener(activity)).start();

    }




    private static class SearchListener implements Runnable{

        MyApplication myApplication;
        boolean isClosed = false;


        SearchListener(Activity activity){

            this.myApplication= (MyApplication) activity.getApplication();
        };


        @Override
        public void run() {
            Log.e("监听提示：","监听已启动...");

            try {
                reSocket = new DatagramSocket(13047);
                reSocket.setSoTimeout(5000);
                while (!isClosed){
                    byte[] receiverData = new byte[1024];
                    DatagramPacket receiverPacket = new DatagramPacket(receiverData,receiverData.length);
                    //使用DatagramSocket对象接收数据包
                    //该方法会阻塞，直到接收到数据报文
                    try {
                        reSocket.receive(receiverPacket);
                        //拆解接收到的数据包
                        //数据缓冲区
                        byte[] buffer = receiverPacket.getData();
                        String datas = EncodingUtil.toHexString(buffer).substring(0,284);

                        VCI_IP = datas.substring(256,264);//VCI IP
                        String reIP = EncodingUtil.hexStringToIP(VCI_IP);
                        reIP = reIP.substring(0,reIP.length()-1);
                        if(reIP.equals("0.0.0.0")){
                            exit();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        sendBroadcast(myActivity,"AA 01 AA BB CC DD");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 2000);
                        }else{
                            exit();
                            Log.e("监听提示：","VCI的IP已保存..."+reIP);

                            myApplication.setVciIp(reIP);
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity main = new MainActivity();
                                    try {
                                        main.linkVCI();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }catch(SocketTimeoutException e){
                        exit();
                        sendBroadcast(myActivity,"AA 01 AA BB CC DD");
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private void close(){
            if(reSocket !=null){
                reSocket.close();
            }
            reSocket = null;
            Log.e("监听提示：","监听Socket已关闭...");
        }

        private void exit(){
            isClosed=true;
            close();
        }
    }


    /**
     * 接收VCI传输的报文信息
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case "tcpMainReceiver":
                    String msg = intent.getStringExtra("tcpMainReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }

    private class MyHandler extends Handler{
        private WeakReference<MainActivity> mActivitys;
        MyHandler(MainActivity activity){
            mActivitys = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {


        }
    }
    public static Notification softNotify=null;
    private Notification permanentNotify=null;
    private Notification mapNotify=null;
    public void setNotification(String text,int id){
        if (id==520){
            //在通知栏通知
            softNotify = new NotificationCompat.Builder(myActivity, "chat")
                    .setOngoing(true)
                    .setContentTitle("软触发")
                    .setContentText(text)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_logo)
                    .setColor(Color.parseColor("#F00606"))  //设置红色
                    .build();
            myApplication.notificationManager.notify(520, softNotify);
        }else if(id==521){
            //在通知栏通知
            permanentNotify = new NotificationCompat.Builder(myActivity, "chat")
                    .setOngoing(true)
                    .setContentTitle("永久触发")
                    .setContentText(text)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_logo)
                    .setColor(Color.parseColor("#F00606"))  //设置红色
                    .build();
            myApplication.notificationManager.notify(521, permanentNotify);
        }else if(id==522){
            //在通知栏通知
            mapNotify = new NotificationCompat.Builder(myActivity, "chat")
                    .setOngoing(true)
                    .setContentTitle("永久映射")
                    .setContentText(text)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_logo)
                    .setColor(Color.parseColor("#F00606"))  //设置红色
                    .build();
            myApplication.notificationManager.notify(522, mapNotify);
        }

    }


    /**
     * 监听热点状态广播
     */
    class HotsspotReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.net.wifi.WIFI_AP_STATE_CHANGED")) {//便携式热点的状态为：10---正在关闭；11---已关闭；12---正在开启；13---已开启
                int state = intent.getIntExtra("wifi_state", 0);
                if (state == 11) {
                    Log.e("监听热点信息：","热点状态：已关闭");
                    if(handlerVCI!=null){
                        handlerVCI.removeCallbacksAndMessages(null);
                    }
                    if(reSocket !=null){
                        reSocket.close();
                    }
                    reSocket = null;
                    myApplication.setVciIp(null);//清除VCI IP
                    myApplication.setBuzzing(false);//更改蜂鸣状态
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myApplication.setLinkState(false);
                            MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_ununited));
                        }
                    });

                } else if (state == 13) {
                    Log.e("监听热点信息：","热点状态：已开启");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sendBroadcast(myActivity,"AA 01 AA BB CC DD");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }
    }





    /*监听事件*/
    private void setViewListener() {
        //导航栏点击事件
        //安全算法
        rbSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActionBar.setTitle(getResources().getString(R.string.text_main_security));
                myApplication.setDataStream(false);//非数据流页面
                myApplication.setVciRun(false);//关闭VCI监听
                switchFragment(0);
            }
        });
        //DTC
        rbDtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActionBar.setTitle(getResources().getString(R.string.text_main_dtc));
                myApplication.setDataStream(false);//非数据流页面
                myApplication.setVciRun(false);//关闭VCI监听
                if (jurisdictionList.get(2).getIsEnable() && jurisdictionList.get(3).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_dtc_query));//设置状态栏标题
                }else if (jurisdictionList.get(3).getIsEnable() && jurisdictionList.get(2).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_dtc_read));
                }
                switchFragment(1);
            }
        });
        //数据流
        rbData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActionBar.setTitle(getResources().getString(R.string.text_main_data));
                myApplication.setDataStream(true);//数据流页面
                if(DataStreamStateFragment.llStop!=null){
                    if(DataStreamStateFragment.llStop!=null){
                        DataStreamStateFragment.llStop.setVisibility(View.GONE);//隐藏停止按钮
                        DataStreamStateFragment.llStart.setVisibility(View.VISIBLE);//显示启动按钮
                    }
                }



                /*if(DataStreamStateFragment.llStop!=null){
                    if(DataStreamStateFragment.llStop.getVisibility()!=View.GONE){
                        myApplication.setVciRun(true);
                    }
                }*/
                /*try {
                    linkVCI();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                if (jurisdictionList.get(4).getIsEnable() && jurisdictionList.get(5).getIsEnable()==false
                        && jurisdictionList.get(6).getIsEnable()==false && jurisdictionList.get(6).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_datastream_title_message));
                }else if (jurisdictionList.get(5).getIsEnable() && jurisdictionList.get(4).getIsEnable()==false
                        && jurisdictionList.get(6).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_datastream_title_did_analysis));
                }else if (jurisdictionList.get(6).getIsEnable() && jurisdictionList.get(4).getIsEnable()==false
                        && jurisdictionList.get(5).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_datastream_title_diagnostician));
                }
                switchFragment(2);
            }
        });
        //检测
        rbDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActionBar.setTitle(getResources().getString(R.string.text_main_detection));
                myApplication.setDataStream(false);//非数据流页面
                myApplication.setVciRun(false);//关闭VCI监听
                if (jurisdictionList.get(7).getIsEnable() && jurisdictionList.get(8).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_detection_ecu_version));
                }else if (jurisdictionList.get(8).getIsEnable() && jurisdictionList.get(7).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_detection_node_online));
                }
                switchFragment(3);
            }
        });
        //分享
        rbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActionBar.setTitle(getResources().getString(R.string.text_main_share));
                myApplication.setDataStream(false);//非数据流页面
                myApplication.setVciRun(false);//关闭VCI监听
                if (jurisdictionList.get(9).getIsEnable() && jurisdictionList.get(10).getIsEnable()==false
                        && jurisdictionList.get(11).getIsEnable()==false && jurisdictionList.get(12).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_sharemain_query));
                }else if (jurisdictionList.get(10).getIsEnable() && jurisdictionList.get(9).getIsEnable()==false
                        && jurisdictionList.get(11).getIsEnable()==false && jurisdictionList.get(12).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_sharemain_share));
                }else if (jurisdictionList.get(11).getIsEnable() && jurisdictionList.get(9).getIsEnable()==false
                        && jurisdictionList.get(10).getIsEnable()==false && jurisdictionList.get(12).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_sharemain_flow));
                }else if (jurisdictionList.get(12).getIsEnable() && jurisdictionList.get(9).getIsEnable()==false
                        && jurisdictionList.get(10).getIsEnable()==false && jurisdictionList.get(11).getIsEnable()==false){
                    myActionBar.setTitle(getResources().getString(R.string.text_sharemain_assist));
                }
                switchFragment(4);
            }
        });
        //TF录制预配置
        llTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myApplication.isLinkState()){
                    if (!Tools.isFastClick()){
                        myApplication.setVciRun(false);//关闭VCI监听
                        Intent intent=new Intent(myActivity, TFCardActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(myActivity,getString(R.string.text_vci_lose),Toast.LENGTH_SHORT).show();
                }
            }
        });
        //连接VCI
        llLinkVCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Tools.isFastClick()){
                    myApplication.setVciRun(false);//关闭VCI监听
                    Intent intent = new Intent(myActivity, LinkVciActivity.class);
                    startActivity(intent);


                   /* String[] permissions=new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION};//判断定位权限
                    if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                        if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_DISABLED){
                            //wifi关闭状态
                            Toast.makeText(myActivity,getString(R.string.text_permissions_wifi),Toast.LENGTH_LONG).show();
                        }else if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED){
                            //wifi打开状态
                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                            String SSID = wifiInfo.getSSID();//获取Wifi名称
                            if(SSID.contains("unknown")){
                                //未开启定位，无法获取SSID
                                Toast.makeText(myActivity,getString(R.string.text_permissions_location_open),Toast.LENGTH_LONG).show();

                            }else if(!SSID.contains("VCI")){
                                //WIFI名不包含VCI，不是vci的热点
                                Toast.makeText(myActivity,getString(R.string.text_permissions_not_vciwifi),Toast.LENGTH_LONG).show();

                            }else{
                                Intent intent = new Intent(myActivity, LinkVciActivity.class);
                                startActivity(intent);
                            }
                        }
                    }else {//没有权限
                        MPermissionUtils.showTipsDialog(myActivity,getString(R.string.text_permissions_location));//提示
                    }
                    */

                }
            }
        });
        //修改密码
        llUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    myApplication.setVciRun(false);//关闭VCI监听
                    Intent intent=new Intent(myActivity, UpdatePasswordActivity.class);
                    startActivity(intent);
                }
            }
        });
        //更改语言
        llUpdateLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    myApplication.setVciRun(false);//关闭VCI监听
                    myApplication.setActivity(myActivity);//保存主页面activity到内存
                    Intent intent=new Intent(myActivity, LanguageActivity.class);
                    startActivity(intent);
                }
            }
        });
        //关于
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    myApplication.setVciRun(false);//关闭VCI监听
                    Intent intent=new Intent(myActivity, AboutActivity.class);
                    startActivity(intent);
                }

            }
        });
        //退出
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    LogOutDialog logOutDialog=new LogOutDialog(myActivity);
                    logOutDialog.show(getSupportFragmentManager(),"");//显示弹窗
                }
            }
        });
    }
    /**
     * 方法 - 切换Fragment
     *
     * @param fragmentIndex 要显示Fragment的索引
     */
    private void switchFragment(int fragmentIndex) {
        //在Activity中显示Fragment
        //1、获取Fragment管理器 FragmentManager
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        //2、开启fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //懒加载 - 如果需要显示的Fragment为null，就new。并添加到Fragment事务中
        if (fragments[fragmentIndex] == null) {
            switch (fragmentIndex) {
                case 0://SecurityFragment
                   fragments[fragmentIndex] = new SecurityFragment();
                    break;
                case 1://DtcFragment
                    fragments[fragmentIndex] = new DtcFragment();
                    break;
                case 2://DataStreamFragment
                    fragments[fragmentIndex] = new DataStreamFragment();
                    break;
                case 3://DetectionFragment
                    fragments[fragmentIndex] = new DetectionFragment();
                    break;
                case 4://ShareFragment
                    fragments[fragmentIndex] = new ShareFragment();
                    break;
            }
            //==添加Fragment对象到Fragment事务中
            //参数：显示Fragment的容器的ID，Fragment对象
            transaction.add(R.id.ll_main_content, fragments[fragmentIndex]);
        }

        //隐藏其他的Fragment
        for (int i = 0; i < fragments.length; i++) {
            if (fragmentIndex != i && fragments[i] != null) {
                //隐藏指定的Fragment
                transaction.hide(fragments[i]);
            }
        }
        //4、显示Fragment
        transaction.show(fragments[fragmentIndex]);

        //5、提交事务
        transaction.commit();
    }


    /*接收udp多播 并 发送tcp 连接*/
    private class udpReceiveAndtcpSend extends  Thread {
        @Override
        public void run() {
            byte[] data = new byte[1024];
            try {
                InetAddress groupAddress = InetAddress.getByName("255.255.255.255");
                ms = new MulticastSocket(58121);
                ms.joinGroup(groupAddress);
            } catch (Exception e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    dp = new DatagramPacket(data, data.length);
                    if (ms != null)
                        ms.receive(dp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (dp.getAddress() != null) {
                    final String quest_ip = dp.getAddress().toString();

                    /* 若udp包的ip地址 是 本机的ip地址的话，丢掉这个包(不处理)*/

                    //String host_ip = getLocalIPAddress();

                    String host_ip = getLocalHostIp();



                    if( (!host_ip.equals(""))  && host_ip.equals(quest_ip.substring(1)) ) {
                        continue;
                    }

                    final String codeString = new String(data, 0, dp.getLength());

                   /*finally {
                        try {
                            if (socket != null)
                                socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }*/
                }
            }
        }
    }


    public String getLocalHostIp() {
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()
                            && getLocalIPAddress()!=ip.toString()) {
                        return ip.getHostAddress();
                    }
                }
            }
        }
        catch(SocketException e)
        {
            Log.e("feige", "获取本地ip地址失败");
            e.printStackTrace();
        }
        return ipaddress;
    }


    private static String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("getLocalIPAddress", ex.toString());
        }
        return null;
    }



    /**
     * 设置drawer相关动画和滑动屏幕范围
     */
    private void setDrawer(){
        //通过actionbardrawertoggle将toolbar与drawablelayout关联起来  动画
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, 0, 0) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //可以重新侧滑方法,该方法实现侧滑动画,整个布局移动效果
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = drawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));

            }
        };
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        //滑动屏幕范围
        if (myActivity == null || drawerLayout == null) return;
        try {
            //变更vci连接状态显示
            if(myApplication.isBuzzing()){
                MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_connected));//显示已连接
            }else{
                MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_ununited));//显示已连接
            }
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            myActivity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    0f)));//0.6f为滑动屏幕范围的设置
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }
    private boolean isExit;

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                myApplication.destroyWebSocketClient();//关闭webSocket
                this.finish();
            } else {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){///判断滑动菜单是否打开
                    drawerLayout.closeDrawer(GravityCompat.START);//关闭
                }else {
                    Toast.makeText(this, getResources().getString(R.string.text_exit), Toast.LENGTH_SHORT).show();
                    isExit = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mManager!=null){
                                mManager.disconnect();
                            }
                            myApplication.destroy();//释放资源
                            if (myApplication.isCall()){//正在通话时
                                callEnd(1004,getString(R.string.text_live_vreceng));
                            }
                            isExit= false;

                        }
                    }, 2000);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 拉流端通话结束
     * @param tip
     */
    private void callEnd(int stateCode,String tip) {
        String url = ServiceUrls.getShareMethodUrl("sendMstsc");
        Map<String, Object> map = new HashMap<>();
        map.put("fromAccount", SPUtils.get(myActivity, SPUtils.SP_ACCOUNT, ""));
        map.put("toAccount", myApplication.getPlayerAccount());
        map.put("stateCode", stateCode);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
            }
        });
        Toast.makeText(myActivity, tip, Toast.LENGTH_LONG).show();
        myApplication.closeLiveActivity();
        myApplication.destroyWebSocketClient();
        myApplication.destroy();
    }
    //通知栏 兼容安卓8以上 创建通知渠道
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
    @Override
    protected void onResume() {
        super.onResume();

        /*if(myApplication.isDataStream()){
            try {
                linkVCI();
                //myApplication.setVciRun(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        if (isForeground == false) {
            //由后台切换到前台
            isForeground = true;
           /* if(myApplication.isDataStream()){
                myApplication.setVciRun(true);//开启VCI监听
            }*/

        }

        //查询是否有未读消息
        String url=ServiceUrls.getShareMethodUrl("selectInformIsReadState");
        Map<String,Object> map=new HashMap<>();
        map.put("userId",user.getUserId());
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                if (code==200){
                                    myActionBar.setBadgeRightShow();//显示红点提示
                                }else {
                                    myActionBar.setBadgeRightHide();//隐藏红点提示
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
     * 判断app是否处于前台
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (!isAppOnForeground()) {
            //由前台切换到后台
            isForeground = false;
            myApplication.setVciRun(false);//关闭VCI监听
        }
    }
}
