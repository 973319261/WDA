package com.gx.wda;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import com.gx.wda.adapter.FriendListAdapter;
import com.gx.wda.bean.CallVo;
import com.gx.wda.bean.Jurisdiction;
import com.gx.wda.bean.MapParamVo;
import com.gx.wda.util.KeyBoardUtil;
import com.gx.wda.util.MyWebSocketClient;
import com.gx.wda.widget.MyActionBar;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class MyApplication extends Application {
    public static Context context;
    public static MyApplication INSTANCE;
    public static NotificationManager  notificationManager;//通知栏
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new MaterialHeader(context).setColorSchemeResources(R.color.colorPrimary);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    //在app启动时执行
    //在app启动时执行
    @Override
    public void onCreate() {
        super.onCreate();

       /* CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(), this);

        INSTANCE=this;*/

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        context=getApplicationContext();
        // 在调用TBS初始化、创建WebView之前进行如下配置，以开启优化方案
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " x5内核是否加载成功： " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

    }
    private MyActionBar myActionBar;
    public void setActionBar(MyActionBar myActionBar){
        this.myActionBar=myActionBar;
    }
    public MyActionBar getMyActionBar(){
        return this.myActionBar;
    }
    private List<Jurisdiction> jurisdictionList;//权限列表
    private int jurisdictionMaxLength=13;//数据库权限模块数
    /**
     * VCI IP
     */
    private String vciIp;
    public String getVciIp() {
        return vciIp;
    }

    public void setVciIp(String vciIp) {
        this.vciIp = vciIp;
    }


    /**
     * VCI连接状态
     * @return
     */
    public boolean isVciState() {
        return vciState;
    }

    public void setVciState(boolean vciState) {
        this.vciState = vciState;
    }
    private boolean vciState;

    /**
     * VCI总线监听是否启动
     * @return
     */
    private boolean isVciRun = false;
    public boolean isVciRun() {
        return isVciRun;
    }

    public void setVciRun(boolean vciRun) {
        isVciRun = vciRun;
        //Log.e("刷写报文",String.valueOf(isVciRun));
    }



    /**
     * 判断当前页是否是报文页
     * @return
     */
    public boolean isDataStream() {
        return isDataStream;
    }

    public void setDataStream(boolean dataStream) {
        isDataStream = dataStream;
    }

    private boolean isDataStream;


    /**
     * 保存TCP连接
     * @return
     */
 /*   public TcpClient getTcpClient() {
        return tcpClient;
    }

    public void setTcpClient(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    private TcpClient tcpClient;*/


    /**
     * 是否蜂鸣
     * @return
     */
    public boolean isBuzzing() {
        return isBuzzing;
    }

    public void setBuzzing(boolean buzzing) {
        isBuzzing = buzzing;
    }

    private boolean isBuzzing;


    /**
     * 连接状态，是处于连接状态还是断开
     * @return
     */
    public boolean isLinkState() {
        return linkState;
    }

    public void setLinkState(boolean linkState) {
        this.linkState = linkState;
    }

    private boolean linkState;


    /**
     *
     * @return
     */
    public MapParamVo getMapParamVo() {
        return mapParamVo;
    }

    public void setMapParamVo(MapParamVo mapParamVo) {
        this.mapParamVo = mapParamVo;
    }

    private MapParamVo mapParamVo;


    /**
     * OkSocket 管理
     * @return
     */
    public IConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(IConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private IConnectionManager connectionManager;


    /**
     * 记录临时映射总数
     * @return
     */
    public Integer getTempMap() {
        return tempMap;
    }

    public void setTempMap(Integer tempMap) {
        this.tempMap = tempMap;
    }

    private Integer tempMap=0;


    /**
     * 3E服务计时器
     * @return
     */
    public Timer getServiceTimer() {
      //  Log.e("临时映射记录数",String.valueOf(serviceTimer));
        return serviceTimer;
    }

    public void setServiceTimer(Timer serviceTimer) {
        this.serviceTimer = serviceTimer;
    }

    private Timer serviceTimer;





    /**
     * 设置权限列表数据
     * @param jurisdictionList
     */
    public void setJurisdictionList(List<Jurisdiction> jurisdictionList){
        int length=jurisdictionList.size();//获取权限列表数据
        this.jurisdictionList=jurisdictionList;
        Jurisdiction smSimJurisdiction=new Jurisdiction();
        smSimJurisdiction.setIsEnable(false);//默认没有权限
        for (int i=0;i<jurisdictionMaxLength-length;i++){//不过条数补够，防止超出列表索引异常
            this.jurisdictionList.add(smSimJurisdiction);
        }
    }

    public List<Jurisdiction> getJurisdictionList(){
        return jurisdictionList;
    }
    private Activity activity;//页面
    /**
     * 设置页面
     * @param activity
     */
    public void setActivity(Activity activity){
        this.activity=activity;
    }
    public Activity getActivity(){
       return this.activity;
    }
    /**
     * 销毁页面
     */
    public void closeActivity(){
        if (activity!=null){
            activity.finish();
        }
    }
    //呼叫页面
    public CallVo callVo;

    public CallVo getCallVo() {
        return callVo;
    }
    public void setCallVo(CallVo callVo) {
        this.callVo = callVo;
    }
    //推流对象
    private TXLivePusher txLivePusher;
    public void setTxLivePusher(TXLivePusher txLivePusher){
        this.txLivePusher=txLivePusher;
    }
    public TXLivePusher getTxLivePusher(){
        return txLivePusher;
    }
    //拉流对象
    private TXLivePlayer txLivePlayer;
    public void setTxLivePlayer(TXLivePlayer txLivePlayer){
        this.txLivePlayer=txLivePlayer;
    }
    public TXLivePlayer getTxLivePlayer(){
        return txLivePlayer;
    }
    //判断是否正在通话
    public boolean isCall(){
        if (txLivePusher!=null || txLivePlayer!=null){//正在通话时
            return true;
        }
        return false;
    }

    /**
     * 推流拉流房间号
     */
    private String roomNo;
    public void setRoomNo(String roomNo){
        this.roomNo=roomNo;
    }
    public String getRoomNo(){
        return roomNo;
    }

    /**
     * 拉流账号
     */
    private String playerAccount;
    public void setPlayerAccount(String playerAccount){
        this.playerAccount=playerAccount;
    }
    public String getPlayerAccount(){
        return playerAccount;
    }
    /**
     * 直播（拉流）页面
     */
    private Activity liveActivity;//
    public void setLiveActivity(Activity activity){
        this.liveActivity=activity;
    }
    public void closeLiveActivity(){
        if (liveActivity!=null){
            liveActivity.finish();
        }
    }



    //音频
    private AudioManager audioManager;
    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
    //手机原来的音量大小
    private int oldVolume=5;//默认为5

    public int getOldVolume() {
        return oldVolume;
    }

    public void setOldVolume(int oldVolume) {
        this.oldVolume = oldVolume;
    }

    /**
     * socket消息推送
     */
    private MyWebSocketClient myWebSocketClient;
    public void setMyWebSocketClient(MyWebSocketClient myWebSocketClient){
        this.myWebSocketClient=myWebSocketClient;
    }
    public MyWebSocketClient getMyWebSocketClient(){
        return myWebSocketClient;
    }
    public void destroyWebSocketClient(){
        if (myWebSocketClient!=null){
            try {
                myWebSocketClient.closeBlocking();
                myWebSocketClient=null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //定时器（通话）
    private CountDownTimer timer;

    public CountDownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }
    //通话提示音
    private MediaPlayer mediaPlayer;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    /**
     * 好友列表适配器
     */
    private FriendListAdapter friendListAdapter;

    public FriendListAdapter getFriendListAdapter() {
        return friendListAdapter;
    }

    public void setFriendListAdapter(FriendListAdapter friendListAdapter) {
        this.friendListAdapter = friendListAdapter;
    }

    /**
     * 释放资源
     */
    public void destroy (){
        if (txLivePusher!=null){
            txLivePusher.stopScreenCapture();
            txLivePusher.setPushListener(null);
            txLivePusher.stopPusher();
            txLivePusher=null;
        }
        if (txLivePlayer!=null){
            txLivePlayer.setPlayListener(null);//取消监听
            txLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
            txLivePlayer=null;
        }
        if (audioManager!=null){
            audioManager.setMicrophoneMute(false);//取消禁麦
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,oldVolume,0);//音量调到原来大小
        }
        if (timer!=null){
            timer.cancel();//取消
        }
        if (notificationManager!=null){
            notificationManager.cancel(1);//关闭通话中通知栏
            notificationManager.cancel(520);//关闭软触发通知栏
            notificationManager.cancel(521);//关闭永久触发通知栏
            notificationManager.cancel(522);//关闭永久映射通知栏
        }
        if (mediaPlayer!=null){
            mediaPlayer.stop();//关闭通话中通知栏
        }
    }
}
