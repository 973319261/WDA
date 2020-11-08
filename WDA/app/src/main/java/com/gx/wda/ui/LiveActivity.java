package com.gx.wda.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.util.MPermissionUtils;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.util.Tools;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LiveActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;
    private MediaPlayer mediaPlayer;
    private String account;//对方账号
    private String userName;//对方账号昵称
    private TextView tvTitle;//标题
    private TextView tvHint;//提示
    private LinearLayout llReceive;//接受
    private LinearLayout llDropped;//拒绝
    private LinearLayout llCancel;//取消
    private LinearLayout llMute;//禁麦
    private LinearLayout llMuteWhite;//禁麦
    private LinearLayout llMuteBlack;//禁麦
    private LinearLayout llHands;//免提
    private LinearLayout llHandsWhite;//免提
    private LinearLayout llHandsBlack;//免提
    private LinearLayout llAwait;//等待中按钮
    private LinearLayout llCall;//通话中按钮
    private TXLivePlayer mLivePlayer;//拉流
    private TXLivePusher mLivePusher;//推流
    private TXCloudVideoView videoView;
    private int oldVolume;//原来的音量大小
    private String playUrl;//获取推流地址
    private String voiceUrl;
    private AudioManager audioManager;
    private boolean isSpeaker = false;
    private double screenWidth;//对方屏幕宽度
    private double screenHeight;//对方屏幕宽度
    private double width;//视频宽度
    private double height;//视频高度
    private double widthProportion;//对方手机屏幕分辨率与视频控件宽度比例
    private double heightProportion;//对方手机屏幕分辨率与视频控件高度比例
    private float moveX;
    private float moveY;
    private float pressX;
    private float pressY;
    private CountDownTimer timer;
    private long startTime = 0;
    private final String TAG=LiveActivity.class.getSimpleName();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication=(MyApplication)myActivity.getApplication();
        myApplication.setLiveActivity(myActivity);//保存到内存
        setContentView(R.layout.activity_live);
        tvTitle=findViewById(R.id.tv_live_title);
        tvHint=findViewById(R.id.tv_live_hint);
        llReceive=findViewById(R.id.ll_live_receive);
        llDropped=findViewById(R.id.ll_live_dropped);
        llCancel=findViewById(R.id.ll_live_cancel);
        llHands=findViewById(R.id.ll_live_hands);
        llHandsWhite=findViewById(R.id.ll_live_hands_white);
        llHandsBlack=findViewById(R.id.ll_live_hands_black);
        llMute=findViewById(R.id.ll_live_mute);
        llMuteWhite=findViewById(R.id.ll_live_mute_white);
        llMuteBlack=findViewById(R.id.ll_live_mute_black);
        llAwait=findViewById(R.id.ll_live_await);
        llCall=findViewById(R.id.ll_live_call);
        videoView=findViewById(R.id.video_view);
        initView();//初始化页面
        setViewListener();//监听事件
        mediaPlayer = MediaPlayer.create(myActivity,
                R.raw.warning_tone);
        mediaPlayer.start();
        // 监听音频播放完的代码，实现音频的自动循环播放
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
        myApplication.setMediaPlayer(mediaPlayer);
    }

    private void setViewListener() {
        //接受
        llReceive.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();//关闭提示音
                String[] permissions=new String[]{
                        Manifest.permission.RECORD_AUDIO};//判断录音权限
                if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
                    String url= ServiceUrls.getShareMethodUrl("sendMstsc");
                    Map<String,Object> map=new HashMap<>();
                    map.put("fromAccount", SPUtils.get(myActivity,SPUtils.SP_ACCOUNT,""));
                    map.put("toAccount",account);
                    map.put("stateCode",1002);
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
                                                //==开始推流
                                                mLivePusher.startPusher(voiceUrl);
                                                mLivePusher.startScreenCapture();
                                                //开始拉流
                                                mLivePlayer.startPlay(playUrl,TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
                                                myApplication.setTxLivePusher(mLivePusher);
                                                myApplication.setTxLivePlayer(mLivePlayer);
                                                myApplication.setAudioManager(audioManager);
                                                llCall.setVisibility(View.VISIBLE);//显示通话中按钮
                                                llAwait.setVisibility(View.GONE);//隐藏通话中按钮
                                                tvHint.setText(R.string.text_live_connecting);

                                                timer = new CountDownTimer(24*60*60*1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        String content = Tools.showTimeCount(System.currentTimeMillis() - startTime);
                                                        tvHint.setText(content);
                                                    }

                                                    @Override
                                                    public void onFinish() {

                                                    }
                                                };
                                                myApplication.setTimer(timer);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    });
                }else {//没有权限
                    MPermissionUtils.showTipsDialog(myActivity,getString(R.string.text_permissions_microphone));//提示
                }
            }
        });
        //拒绝
        llDropped.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                callEnd(1003,getString(R.string.text_live_vreceng));
            }
        });
        //取消通话（挂断）
        llCancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                callEnd(1004,getString(R.string.text_live_vreceng));
            }
        });
        //禁麦
        llMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioManager.isMicrophoneMute()){
                    //rbMute.setChecked(false);
                    llMuteWhite.setVisibility(View.VISIBLE);
                    llMuteBlack.setVisibility(View.GONE);
                   // Toast.makeText(myActivity,"已关闭禁麦",Toast.LENGTH_LONG).show();
                    audioManager.setMicrophoneMute(false);//取消禁麦

                }else{
                    llMuteWhite.setVisibility(View.GONE);
                    llMuteBlack.setVisibility(View.VISIBLE);
                   // Toast.makeText(myActivity,"已开启禁麦",Toast.LENGTH_LONG).show();
                    audioManager.setMicrophoneMute(true);//禁麦

                }
            }
        });
        //免提
        llHands.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                if(!isSpeaker){
                    isSpeaker=true;
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume
                            (AudioManager.STREAM_MUSIC),0);
                    llHandsWhite.setVisibility(View.GONE);
                    llHandsBlack.setVisibility(View.VISIBLE);
                }else{
                    isSpeaker=false;
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,oldVolume,0);//音量调到3
                    llHandsWhite.setVisibility(View.VISIBLE);
                    llHandsBlack.setVisibility(View.GONE);
                }
            }
        });
        //拉流监听
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
                Log.d(TAG,"i="+i+"    bundle"+bundle.toString());
                //网络断连，已启动自动重连（重连超过三次就直接抛送 PLAY_ERR_NET_DISCONNECT）
                if (i == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    callEnd(1004,getString(R.string.text_live_connection_fail));
                }else if (i== TXLiveConstants.PLAY_EVT_PLAY_END){
                    callEnd(1004,getString(R.string.text_live_connection_fail));
                }else if (i== TXLiveConstants.PLAY_WARNING_RECV_DATA_LAG){//网络来包不稳：可能是下行带宽不足，或由于主播端出流不均匀
                    Toast.makeText(myActivity,R.string.text_live_instability,Toast.LENGTH_LONG).show();
                }else if (i== TXLiveConstants.PLAY_EVT_PLAY_BEGIN){//视频播放开始，如果您自己做 loading，会需要它
                    callSucceed();//通话连接成功
                }
            }
            //网络状况和视频信息
            @Override
            public void onNetStatus(Bundle bundle) {
                Log.d(TAG,"bundle"+bundle.toString());
            }
        });
        //推流监听
        mLivePusher.setPushListener(new ITXLivePushListener() {
            @Override
            public void onPushEvent(int i, Bundle bundle) {
                //与服务器握手完毕，一切正常，准备开始推流
                if (i== TXLiveConstants.PUSH_EVT_PUSH_BEGIN){
                   // Toast.makeText(myActivity,"连接成功",Toast.LENGTH_LONG).show();
                }else if (i== TXLiveConstants.PUSH_WARNING_NET_BUSY ){//网络不稳定
                    Toast.makeText(myActivity, R.string.text_live_instability,Toast.LENGTH_LONG).show();
                }
                Log.d(TAG,"i="+i+"    bundle"+bundle.toString());
            }

            @Override
            public void onNetStatus(Bundle bundle) {
                Log.d(TAG,"bundle"+bundle.toString());
            }
        });
        //屏幕操作
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    //按下
                    case MotionEvent.ACTION_DOWN:
                        pressX = event.getX();
                        pressY = event.getY();
                        break;
                    //移动
                    case MotionEvent.ACTION_MOVE:
                        moveX = event.getX();
                        moveY = event.getY();
                        break;
                    //松开
                    case MotionEvent.ACTION_UP:
                        // 处理输入的离开事件
                        callControl(pressX,pressY,moveX,moveY);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity, true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity, true);//状态栏文字颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //锁屏显示
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD //解锁
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕不息屏
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);//点亮屏幕
        String data = getIntent().getStringExtra("data");
        JSONObject jsonData= null;
        try {
            jsonData = new JSONObject(data);
            String roomNo=jsonData.getString("roomNo");//房间号
            account=jsonData.getString("account");//对方账号
            userName=jsonData.getString("userName");//对方账号昵称
            screenWidth=jsonData.getDouble("screenWidth");//对方屏幕宽度
            screenHeight=jsonData.getDouble("screenHeight");//对方屏幕高度
            playUrl=ServiceUrls.rtmpUrl+roomNo;//拉流（视频、语音）地址
            voiceUrl=ServiceUrls.rtmpUrl+"v"+roomNo;//语音推流地址
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvTitle.setText(userName);
        audioManager = (AudioManager)myActivity.getSystemService(Context.AUDIO_SERVICE);
        oldVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//原来的音量
        myApplication.setOldVolume(oldVolume);//保存原来的音量大小
        audioManager.setMicrophoneMute(false);//取消禁麦.
       // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,3,0);//音量调到3
        //创建 pusher 对象
        mLivePusher = new TXLivePusher(myActivity);
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableNearestIP(false);
        mLivePusher.setConfig(mLivePushConfig);
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(myActivity);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(videoView);
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
    }

    /**
     * 拉流端通话结束
     * @param tip
     */
    private void callEnd(int stateCode, String tip){
        String url= ServiceUrls.getShareMethodUrl("sendMstsc");
        Map<String,Object> map=new HashMap<>();
        map.put("fromAccount", SPUtils.get(myActivity,SPUtils.SP_ACCOUNT,""));
        map.put("toAccount",account);
        map.put("stateCode",stateCode);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {

            }
        });
        Toast.makeText(myActivity,tip,Toast.LENGTH_LONG).show();
        finish();
        myApplication.destroy();
        videoView.onDestroy();
    }

    /**
     * 远程控制(滑动)
     */
    private void callControl (float x,float y,float x1,float y1){
        String url= ServiceUrls.getShareMethodUrl("callControl");
        Map<String,Object> map=new HashMap<>();
        x= (float) widthProportion*x;
        y= (float) heightProportion*y;
        x1= (float) heightProportion*x1;
        y1= (float) heightProportion*y1;
        map.put("toAccount",account);
        map.put("x",x);
        map.put("y",y);
        map.put("x1",x1);
        map.put("y1",y1);
        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }
    /**
     * 通话连接成功
     */
    private void callSucceed(){
        width =videoView.getMeasuredWidth();//控件(操作画面)的宽度
        height =videoView.getMeasuredHeight();//控件（操作画面）的高度
        if (screenHeight>=2040){//高度分辨率大于2040时
            heightProportion= (screenHeight /height)+0.05;//高度比例
        }else {
            heightProportion= (screenHeight /height);//高度比例
        }
        int realityWidth= (int) (screenWidth /heightProportion);//实际视频宽度
        widthProportion=screenWidth/realityWidth;//宽度比例
        //获取控件的布局参数，设置控件的宽高
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        params.width = realityWidth;
        params.height = (int) height;
        videoView.setLayoutParams(params);
        String url= ServiceUrls.getShareMethodUrl("sendMstsc");
        Map<String,Object> map=new HashMap<>();
        map.put("fromAccount", SPUtils.get(myActivity,SPUtils.SP_ACCOUNT,""));
        map.put("toAccount",account);
        map.put("stateCode",1005);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在通知栏通知
                       Notification notification = new NotificationCompat.Builder(myActivity, "chat")
                                .setOngoing(true)
                                .setContentTitle(userName)
                                .setContentText(getString(R.string.text_live_in_call))
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.drawable.ic_logo)
                                .setColor(Color.parseColor("#F00606"))  //设置红色
                                .build();
                        myApplication.notificationManager.notify(1, notification);
                        if (startTime==0){
                            startTime=System.currentTimeMillis();
                        }
                        timer.start();
                        Toast.makeText(myActivity,R.string.text_live_successfu,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    //返回键
    @Override
    public void onBackPressed() {
        Toast.makeText(myActivity, R.string.text_live_in_call,Toast.LENGTH_LONG).show();
    }
}
