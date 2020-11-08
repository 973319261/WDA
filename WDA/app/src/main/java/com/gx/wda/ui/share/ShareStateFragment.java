package com.gx.wda.ui.share;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.adapter.FriendListAdapter;
import com.gx.wda.adapter.ShareNoticeAdapter;
import com.gx.wda.adapter.VersionRecordAdapter;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.bean.CallVo;
import com.gx.wda.bean.NoticeVo;
import com.gx.wda.bean.Pagination;
import com.gx.wda.bean.UserVo;
import com.gx.wda.bean.Version;
import com.gx.wda.dialog.BottomOptionDialog;
import com.gx.wda.dialog.ShareQueryDetailDialog;
import com.gx.wda.dialog.TopOptionDialog;
import com.gx.wda.util.KeyBoardUtil;
import com.gx.wda.util.MPermissionUtils;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.Tools;
import com.gx.wda.util.ViewUtils;
import com.gx.wda.widget.CustomEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import me.leefeng.promptlibrary.PromptDialog;

public class ShareStateFragment extends Fragment {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//记录当前分页
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局

    private ShareQueryDetailDialog selectDialogQuery;

    private BottomOptionDialog dialogModule;
    private TopOptionDialog dialogSupplier;
    private TopOptionDialog dialogDID;

    //Query
    private LinearLayout llEcunameToCanid;
    private LinearLayout llCandiToEcuname;
    private ImageView ivChangeEcunameToCanid;
    private ImageView ivChangeCanidToEcuname;
    private LinearLayout llSupplierToCode;
    private LinearLayout llCodeiToSupplier;
    private ImageView ivChangeSupplierToCode;
    private ImageView ivChangeCodeToSupplier;
    private LinearLayout llDidBulk;//存放did小方块


    private BottomOptionDialog selectDialogECU;
    private BottomOptionDialog selectDialogECUToCan;
    private BottomOptionDialog selectDialogCanToECU;
    private BottomOptionDialog selectDialogCodeToName;
    private BottomOptionDialog selectDialogNameToCode;
    private BottomOptionDialog selectDialogEOLContent;

    private int pageId;
    private TextView btnSoftQuery;
    private TextView btnEcuQuery;
    private TextView btnDiagnosisNameToCan;
    private TextView btnDiagnosisCanToName;
    private TextView btnSupplierNameToCode;
    private TextView btnSupplierCodeToName;
    private TextView btnProductionLine;
    private CustomEditText etEcuName;
    private TextView etEcuFullName;
    private CustomEditText etNameToCanIDEcuName;
    private TextView etNameToCanIDCanID;
    private TextView etCanIDToNameEcuName;
    private CustomEditText etCanIDToNameCanID;
    private CustomEditText etNameToCodeName;
    private TextView etNameToCodeCode;
    private TextView etCodeToNameName;
    private CustomEditText etCodeToNameCode;
    private CustomEditText etProductionEcuName;
    private TextView etProductEolContent;
    private LinearLayout llSelectModule;
    private LinearLayout llSelectSupplier;
    private LinearLayout llSelectDID;
    private TextView tvSelectModule;
    private TextView tvSelectSupplier;
    private TextView tvSelectDID;
    private EditText etAccount;//连接账号
    private Button btnConnect;//连接
    private TextView tvTitle;//对方账号
    private TextView tvHint;//提示
    private LinearLayout llContent;//容器
    private LinearLayout llCall;//通话
    private LinearLayout llConnect;//连接
    private LinearLayout llCancel;//取消
    private LinearLayout llMute;//禁麦
    private LinearLayout llMuteWhite;//禁麦
    private LinearLayout llMuteBlack;//禁麦
    private LinearLayout llHands;//免提
    private LinearLayout llHandsWhite;//免提
    private LinearLayout llHandsBlack;//免提
    private TXLivePusher mLivePusher;//推流
    private TXLivePlayer mLivePlayer;//拉流
    private TextView tvNum;//列表数
    private FriendListAdapter friendListAdapter;//适配器
    private SmartRefreshLayout srlFriendlist;
    private LinearLayout llFriendList;//好友列表
    private RecyclerView rvFriendList;//列表
    private String toAccount;//对方账号
    private boolean isSpeaker = false;
    private int oldVolume;//原来的音量大小
    private AudioManager audioManager;
    private final String TAG=ShareStateFragment.class.getSimpleName();
    private Animation rotate;
    private AnimationSet setAnimation;
    private BottomOptionDialog ecuDialog;//模块下拉框
    private BottomOptionDialog supDialog;//供应商下拉框
    private BottomOptionDialog didDialog;//供应商下拉框
    private PromptDialog promptDialog;//加载框
   // Map<Integer,String> listDidVlaue = new HashMap<>();//did小方块list
    List<AppendOptionVo> listDidVlaue = new ArrayList<>();
    private Integer saveModuleId;
    private SmartRefreshLayout srlShareNotice;//刷新
    private ShareNoticeAdapter shareNoticeAdapter;//适配器
    private RecyclerView rvNoticeRecord;//列表

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity=(Activity) context;//设置上下文
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        myApplication= (MyApplication) myActivity.getApplication();
        setAnimation = new AnimationSet(true);
        setAnimation.setRepeatCount(1);// 设置了循环一次,但无效
        // 旋转动画
        rotate = new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(500);
        rotate.setRepeatMode(Animation.RESTART);
        setAnimation.addAnimation(rotate);

        promptDialog=new PromptDialog(myActivity);//加载框实例化
        //获取传递的参数 orderStateId
        Bundle bundle=getArguments();
        pageId = bundle.getInt("pageId");
        if (pageId==0){
            view=inflater.inflate(R.layout.fragment_share_query,container,false);
            //获取控件
            //ECU名称查询CANID
            llEcunameToCanid = view.findViewById(R.id.ll_ecuname_to_canid);
            //CANID查询ECU名称
            llCandiToEcuname = view.findViewById(R.id.ll_canid_to_ecuname);
            //供应商名称查询编码
            llSupplierToCode = view.findViewById(R.id.ll_supplier_to_code);
            //编码查询供应商名称
            llCodeiToSupplier = view.findViewById(R.id.ll_code_to_supplier);
            llDidBulk = view.findViewById(R.id.ll_did_select_bulk);

            /*************************************  下拉框  ************************************************/
            //模块
            llSelectModule = view.findViewById(R.id.ll_query_select_module);
            tvSelectModule = view.findViewById(R.id.tv_query_select_module);
            //供应商
            llSelectSupplier = view.findViewById(R.id.ll_query_select_supplier);
            tvSelectSupplier = view.findViewById(R.id.tv_query_select_supplier);
            //DID
            llSelectDID = view.findViewById(R.id.ll_query_select_did);
            tvSelectDID = view.findViewById(R.id.tv_query_select_did);




            /*************************************  转换图标  ************************************************/
            //ECU名称查询转CANID查询
            ivChangeEcunameToCanid = view.findViewById(R.id.iv_change_name_to_canid);
            ivChangeEcunameToCanid.startAnimation(setAnimation);
            //CANID查询转ECU名称查询
            ivChangeCanidToEcuname = view.findViewById(R.id.iv_change_canid_to_name);
            ivChangeCanidToEcuname.startAnimation(setAnimation);
            //供应商名称查询转编码查询
            ivChangeSupplierToCode = view.findViewById(R.id.iv_change_supplier_to_code);
            ivChangeSupplierToCode.startAnimation(setAnimation);
            //编码查询转供应商名称查询
            ivChangeCodeToSupplier = view.findViewById(R.id.iv_change_code_to_supplier);
            ivChangeCodeToSupplier.startAnimation(setAnimation);


            /*************************************  查询按钮组  ************************************************/
            //程序版本变更查询按钮
            btnSoftQuery = view.findViewById(R.id.tv_btn_soft_query);
            //ECU查询
            btnEcuQuery = view.findViewById(R.id.tv_btn_ecu_query);
            //ECU诊断查询（ECU名称 to canID）
            btnDiagnosisNameToCan = view.findViewById(R.id.tv_btn_diagnosis_nametocan_query);
            //供应商查询(供应商名称 to 供应商编码)
            btnSupplierNameToCode = view.findViewById(R.id.tv_btn_supplier_to_code_query);
            //ECU诊断查询（canID to ECU名称）
            btnDiagnosisCanToName = view.findViewById(R.id.tv_btn_diagnosis_cantoname_query);
            //供应商查询(供应商编码 to 供应商名称)
            btnSupplierCodeToName = view.findViewById(R.id.tv_btn_code_to_supplier_query);
            //ECU生产线查询
            btnProductionLine = view.findViewById(R.id.tv_btn_production_query);

            /*************************************  文本内容组  ************************************************/
            //ECU名称
            etEcuName = view.findViewById(R.id.et_ecu_name);
            //ECU全称
            etEcuFullName = view.findViewById(R.id.et_ecu_full_name);
            //ECU名称（诊断控制--ECU名称 To canID）
            etNameToCanIDEcuName = view.findViewById(R.id.et_diagnosis_nametocan_ecu_name);
            //CANID （诊断控制--ECU名称 To canID）
            etNameToCanIDCanID = view.findViewById(R.id.et_diagnosis_nametocan_canid);
            //ECU名称（诊断控制--canID To ECU名称）
            etCanIDToNameEcuName = view.findViewById(R.id.et_diagnosis_cantoname_ecu_name);
            //CANID （诊断控制--canID To ECU名称）
            etCanIDToNameCanID = view.findViewById(R.id.et_diagnosis_cantoname_canid);
            //供应商名称（供应商名称 to 编号）
            etNameToCodeName = view.findViewById(R.id.et_supplier_to_code_supplier_name);
            //供应商编号（供应商名称 to 编号）
            etNameToCodeCode = view.findViewById(R.id.et_supplier_to_code_supplier_code);
            //供应商名称（供应商编号 to 名称）
            etCodeToNameName = view.findViewById(R.id.et_code_to_supplier_supplier_name);
            //供应商编号（供应商编号 to 名称）
            etCodeToNameCode = view.findViewById(R.id.et_code_to_supplier_supplier_code);
            //ECU名称（ECU生产线）
            etProductionEcuName = view.findViewById(R.id.et_production_ecu_name);
            //EOL内容
            etProductEolContent = view.findViewById(R.id.et_production_eol_content);

            initQueryView();//页面初始化
            setViewQueryListener();//设置监听事件

        } else if (pageId==1){
            view=inflater.inflate(R.layout.fragment_share_sharing,container,false);
            srlShareNotice=view.findViewById(R.id.srl_share_notice_list);
            rvNoticeRecord=view.findViewById(R.id.rv_share_notice_list);
            initShareView();
            setViewShareListener();
        }else if (pageId==2){
            view=inflater.inflate(R.layout.fragment_share_flow,container,false);
        }else if (pageId==3){
            view=inflater.inflate(R.layout.fragment_share_assist,container,false);
            etAccount=view.findViewById(R.id.et_account);
            btnConnect=view.findViewById(R.id.btn_connect);
            tvTitle=view.findViewById(R.id.tv_live_title);
            tvHint=view.findViewById(R.id.tv_live_hint);
            llContent=view.findViewById(R.id.ll_content);
            llCall=view.findViewById(R.id.ll_call);
            llConnect=view.findViewById(R.id.ll_connect);
            llHands=view.findViewById(R.id.ll_live_hands);
            llHandsWhite=view.findViewById(R.id.ll_live_hands_white);
            llHandsBlack=view.findViewById(R.id.ll_live_hands_black);
            llMute=view.findViewById(R.id.ll_live_mute);
            llMuteWhite=view.findViewById(R.id.ll_live_mute_white);
            llMuteBlack=view.findViewById(R.id.ll_live_mute_black);
            llCancel=view.findViewById(R.id.ll_live_cancel);
            rvFriendList=view.findViewById(R.id.rv_friend_list);
            tvNum=view.findViewById(R.id.tv_list_num);
            llFriendList=view.findViewById(R.id.ll_friend_list);
            srlFriendlist=view.findViewById(R.id.srl_friend_list);
            initAssistView();
            setAssistListener();
        }

        return view;
    }

    /**
     * 远程连接监听
     */
    private void setAssistListener() {
        //连接
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.isFastClick()){
                    KeyBoardUtil.hideKeyboard(v);
                    toAccount=etAccount.getText().toString();//获取编辑框值
                    connectCall();//连接通话
                }
            }
        });
        //取消
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEnd(getString(R.string.text_live_vreceng));
            }
        });
        //禁麦
        llMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioManager.isMicrophoneMute()){
                    llMuteWhite.setVisibility(View.VISIBLE);
                    llMuteBlack.setVisibility(View.GONE);
                    audioManager.setMicrophoneMute(false);//取消禁麦
                }else{
                    llMuteWhite.setVisibility(View.GONE);
                    llMuteBlack.setVisibility(View.VISIBLE);
                    audioManager.setMicrophoneMute(true);//禁麦
                }
            }
        });
        //免提
        llHands.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                if(!isSpeaker){//最大音量
                    isSpeaker=true;
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume
                            (AudioManager.STREAM_MUSIC),0);
                    llHandsWhite.setVisibility(View.GONE);
                    llHandsBlack.setVisibility(View.VISIBLE);
                }else{
                    isSpeaker=false;
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,oldVolume,0);//音量调到原来的大小
                    llHandsWhite.setVisibility(View.VISIBLE);
                    llHandsBlack.setVisibility(View.GONE);
                }
            }
        });
        //推流监听
        mLivePusher.setPushListener(new ITXLivePushListener() {
            @Override
            public void onPushEvent(int i, Bundle bundle) {
                //与服务器握手完毕，一切正常，准备开始推流
                if (i== TXLiveConstants.PUSH_EVT_PUSH_BEGIN){
                   // Toast.makeText(myActivity,"连接成功",Toast.LENGTH_LONG).show();
                }else if (i== TXLiveConstants.PUSH_WARNING_NET_BUSY ){
                    Toast.makeText(myActivity,R.string.text_live_instability,Toast.LENGTH_LONG).show();
                }
                Log.d(TAG,"i="+i+"    bundle"+bundle.toString());
            }

            @Override
            public void onNetStatus(Bundle bundle) {
                Log.d(TAG,"bundle"+bundle.toString());
            }
        } );
        //拉流监听
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
                Log.d(TAG,"i="+i+"    bundle"+bundle.toString());
                //网络断连，已启动自动重连（重连超过三次就直接抛送 PLAY_ERR_NET_DISCONNECT）
                if (i == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    callEnd(getString(R.string.text_live_connection_fail));
                }else if (i== TXLiveConstants.PLAY_EVT_PLAY_END){
                    callEnd(getString(R.string.text_live_connection_fail));
                }else if (i== TXLiveConstants.PLAY_WARNING_RECV_DATA_LAG){//网络来包不稳：可能是下行带宽不足，或由于主播端出流不均匀
                    Toast.makeText(myActivity,R.string.text_live_instability,Toast.LENGTH_LONG).show();
                }
            }
            //网络状况和视频信息
            @Override
            public void onNetStatus(Bundle bundle) {
                Log.d(TAG,"bundle"+bundle.toString());
            }
        });
        //下拉刷新
        srlFriendlist.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadEngineerListData();
            }
        });
        //账号
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 ){//账号和密码不为空
                    btnConnect.setEnabled(true);//开启按钮
                }else {
                    btnConnect.setEnabled(false);//禁止按钮
                }
            }
        });
    }

    /**
     * 远程连接初始化
     */
    private void initAssistView() {
        //创建 pusher 对象
        mLivePusher = new TXLivePusher(myActivity);
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableNearestIP(false);
        mLivePusher.setConfig(mLivePushConfig);
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(myActivity);
        audioManager = (AudioManager)myActivity.getSystemService(Context.AUDIO_SERVICE);
        oldVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//原来的音量
        myApplication.setOldVolume(oldVolume);//保存原来的音量大小
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvFriendList.setLayoutManager(layoutManager);
        //=2.1、初始化适配器
        friendListAdapter=new FriendListAdapter(myActivity,tvNum);
        //=2.3、设置recyclerView的适配器
        rvFriendList.setAdapter(friendListAdapter);
        myApplication.setFriendListAdapter(friendListAdapter);
        srlFriendlist.setEnableLoadMore(false);//禁止上拉加载
        srlFriendlist.autoRefresh();//自动刷新动画
        loadEngineerListData();

    }
    /**
     * 加载好友列表信息
     */
    public void loadEngineerListData() {
        String url = ServiceUrls.getShareMethodUrl("selectEngineerInfo");
        Map<String, Object> map = new HashMap<>();
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    String data = jsonObject.getString("data");
                                    Type type = new TypeToken<List<UserVo>>() {}.getType();
                                    List<UserVo> list = gson.fromJson(data, type);
                                    friendListAdapter.addItem(list);
                                    //设置点击事件
                                    friendListAdapter.setOnItemClickListener(new FriendListAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(UserVo data, Integer position) {
                                            if (!Tools.isFastClick()){
                                                if (data.getOnline()){//对方在线
                                                    toAccount=data.getUserAccount();
                                                    connectCall();
                                                }else {//对方不在线
                                                    Toast.makeText(myActivity, R.string.text_live_offline,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                                    srlFriendlist.finishRefresh();
                                }else {
                                    srlFriendlist.finishRefresh(false);//刷新失败
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        srlFriendlist.finishRefresh(false);//刷新失败
                    }
                });
            }
        });
    }



    /**
     * 连接通话
     */
    private void connectCall(){
        String[] permissions=new String[]{
                Manifest.permission.RECORD_AUDIO};//判断录音权限
        if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
            String fromAccount= (String) SPUtils.get(myActivity,SPUtils.SP_ACCOUNT,"");
            String roomNo= String.valueOf(System.currentTimeMillis());//唯一房间号
            String url=ServiceUrls.getShareMethodUrl("sendMstsc");
            Map<String,Object> map=new HashMap<>();
            map.put("fromAccount",fromAccount );
            map.put("toAccount",toAccount);
            map.put("stateCode",1001);
            map.put("roomNo",roomNo);
            map.put("screenWidth",ViewUtils.getScreenWidth(myActivity));//屏幕的宽度
            map.put("screenHeight",ViewUtils.getScreenHeight(myActivity));//屏幕的高度
            OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseCode==200 && isSuccess){
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    int code=jsonObject.getInt("code");
                                    if (code==200){
                                        CallVo callVo=new CallVo();
                                        callVo.setTvTitle(tvTitle);
                                        callVo.setTvHint(tvHint);
                                        callVo.setLlConnect(llConnect);
                                        callVo.setLlCall(llCall);
                                        callVo.setLlMute(llMute);
                                        callVo.setLlCancel(llCancel);
                                        callVo.setLlHands(llHands);
                                        callVo.setLlMuteWhite(llMuteWhite);
                                        callVo.setLlMuteBlack(llMuteBlack);
                                        callVo.setLlHandsWhite(llHandsWhite);
                                        callVo.setLlHandsBlack(llHandsBlack);
                                        callVo.setLlFriendList(llFriendList);
                                        myApplication.setCallVo(callVo);
                                        myApplication.setPlayerAccount(toAccount);
                                        myApplication.setTxLivePusher(mLivePusher);
                                        myApplication.setTxLivePlayer(mLivePlayer);
                                        myApplication.setAudioManager(audioManager);
                                        myApplication.setRoomNo(roomNo);
                                    }else if (code==500){
                                        Toast.makeText(myActivity, R.string.text_live_nonentity,Toast.LENGTH_SHORT).show();
                                    }else if (code==501){
                                        Toast.makeText(myActivity, R.string.text_live_yourself,Toast.LENGTH_SHORT).show();
                                    }else if (code==502){
                                        Toast.makeText(myActivity, R.string.text_live_offline,Toast.LENGTH_SHORT).show();
                                    }else if (code==503){
                                        Toast.makeText(myActivity, R.string.text_live_you_call,Toast.LENGTH_SHORT).show();
                                    }else if (code==504){
                                        Toast.makeText(myActivity, R.string.text_live_opposite_call,Toast.LENGTH_SHORT).show();
                                    }
                                    if (code==200){
                                        tvTitle.setText(toAccount);
                                        llConnect.setVisibility(View.GONE);//隐藏
                                        llFriendList.setVisibility(View.GONE);
                                        llCall.setVisibility(View.VISIBLE);//显示
                                    }else {
                                        llConnect.setVisibility(View.VISIBLE);//显示
                                        llFriendList.setVisibility(View.VISIBLE);
                                        llCall.setVisibility(View.GONE);//隐藏
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                Toast.makeText(myActivity,R.string.text_network_anomaly,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }else {//没有权限
            MPermissionUtils.showTipsDialog(myActivity,getString(R.string.text_permissions_microphone));//提示
        }

    }
    /**
     * 推流端通话结束
     * @param tip
     */
    private void callEnd(String tip) {
        String url = ServiceUrls.getShareMethodUrl("sendMstsc");
        Map<String, Object> map = new HashMap<>();
        map.put("fromAccount", SPUtils.get(myActivity, SPUtils.SP_ACCOUNT, ""));
        map.put("toAccount", toAccount);
        map.put("stateCode", 1004);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {

            }
        });
        Toast.makeText(myActivity, tip, Toast.LENGTH_LONG).show();
        myApplication.destroy();
        llConnect.setVisibility(View.VISIBLE);//显示连接按钮
        llFriendList.setVisibility(View.VISIBLE);//显示好友列表
        llCall.setVisibility(View.GONE);//隐藏通话页
        llHands.setVisibility(View.GONE);//隐藏
        llMute.setVisibility(View.GONE);//隐藏
        llMuteWhite.setVisibility(View.VISIBLE);
        llMuteBlack.setVisibility(View.GONE);
        llHandsWhite.setVisibility(View.VISIBLE);
        llHandsBlack.setVisibility(View.GONE);
        tvHint.setText(getString(R.string.text_live_await));
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
        String url = ServiceUrls.getShareMethodUrl("selectListNoticeByName");//服务端url
        //准备服务端所需参数
        Map<String, Object> map = new HashMap<>();
        map.put("name","");
        map.put("pageSize", pageSize); //分页大小
        map.put("currentPage", currentPage);//当前页数
        //异步请求
        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         if (isSuccess && responseCode == 200) {
                           //请求成功
                            Pagination<NoticeVo> pagination;
                            //使用Gson对响应的数据反序列化
                            Type type = new TypeToken<Pagination<NoticeVo>>() {
                            }.getType();
                            pagination = gson.fromJson(response, type);
                            //处理数据
                            if (pagination != null) {
                                //将分页数据添加到recyclerView的适配器
                                List<NoticeVo> notices = pagination.getData();
                                shareNoticeAdapter.addItem(notices, currentPage);

                                if (isRefresh) {
                                    srlShareNotice.finishRefresh();//刷新完成
                                } else {
                                    srlShareNotice.finishLoadMore();//加载更多完成
                                }
                                //获取数据总页数
                                int totalPage = pagination.getTotalPage();
                                //如果当前页数等于总页数，设置所有页数加载完成提示
                                if (currentPage == totalPage) {
                                    //告诉SmartRefreshLayout没有更多数据了
                                    srlShareNotice.finishLoadMoreWithNoMoreData();
                                }
                            } else {
                                //加载失败
                                if (isRefresh) {
                                    srlShareNotice.finishRefresh(false);//刷新失败
                                } else {
                                    srlShareNotice.finishLoadMore(false);//加载更多失败
                                }
                            }
                        } else {
                            //加载失败
                            if (isRefresh) {
                                srlShareNotice.finishRefresh(false);//刷新失败
                            } else {
                                srlShareNotice.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }
    //Share 页面初始化
    private void initShareView(){
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvNoticeRecord.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        List<NoticeVo> mListData=new ArrayList<>();
        shareNoticeAdapter = new ShareNoticeAdapter(mListData, myActivity, myApplication);
        //=2.3、设置recyclerView的适配器
        rvNoticeRecord.setAdapter(shareNoticeAdapter);
        //加载RecyclerView数据
        loadListData(true);
    }


    //Share 控件事件
    private void setViewShareListener(){
        //下拉 刷新整个RecyclerView
        srlShareNotice.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });

        //上拉加载更多数据
        srlShareNotice.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });
    }
    //Query控件事件
    private void setViewQueryListener() {

        /*****************************************************  下拉框事件  ************************************************/
        llSelectModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取模块信息
                String url= ServiceUrls.getShareMethodUrl("selectModuleAsDid");
                Map<String,Object> map=new HashMap<>();
                promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
                OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode==200){
                                    try {
                                        List<AppendOptionVo> ECUList=null;//模块列表
                                        JSONObject jsonObject=new JSONObject(response);
                                        int code=jsonObject.getInt("code");

                                        if (code==200){
                                            String data=jsonObject.getString("data");
                                            Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                            ECUList = gson.fromJson(data,type);

                                            ecuDialog.setData(ECUList, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    supDialog.removeSaveId();//清除上次保存的小勾勾
                                                    tvSelectSupplier.setText(getString(R.string.text_please_select));//清除供应商
                                                    tvSelectDID.setText(getString(R.string.text_please_select));//清除did
                                                    listDidVlaue.clear();//清空小方块list
                                                    llDidBulk.removeAllViews();//移除所有DID小块
                                                    tvSelectModule.setText(appendOptionVo.getName());//设置模块文本
                                                    selectSupplierByModuleId(appendOptionVo.getId());//查询供应商
                                                    saveModuleId = appendOptionVo.getId();
                                                    popupWindow.dismiss();//关闭弹窗
                                                }
                                            });
                                            ecuDialog.show();//显示
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                promptDialog.dismissImmediately();//关闭
                            }
                        });
                    }
                });
            }
        });
        //供应商
        llSelectSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(supDialog.isHasData()){
                    supDialog.show();
                }else{
                    Toast.makeText(myActivity,getString(R.string.text_please_select)+" "+getString(R.string.text_detection_module),Toast.LENGTH_SHORT).show();
                }
            }
        });
        //did
        llSelectDID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(didDialog.isHasData()){
                    didDialog.show();
                }else{
                    Toast.makeText(myActivity,getString(R.string.text_please_select)+" "+getString(R.string.text_security_supplier),Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*****************************************  转换图标点击事件  *************************************************/
        //CANID查询转ECU名称查询
        ivChangeCanidToEcuname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivChangeCanidToEcuname.startAnimation(setAnimation);
                resetDiagnosis();
                llEcunameToCanid.setVisibility(View.VISIBLE);
                llCandiToEcuname.setVisibility(View.GONE);
            }
        });
        //ECU名称查询转CANID查询
        ivChangeEcunameToCanid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivChangeEcunameToCanid.startAnimation(setAnimation);
                resetDiagnosis();
                llEcunameToCanid.setVisibility(View.GONE);
                llCandiToEcuname.setVisibility(View.VISIBLE);
            }
        });
        //编码查询转供应商名称查询
        ivChangeSupplierToCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivChangeSupplierToCode.startAnimation(setAnimation);
                resetSupplier();
                llCodeiToSupplier.setVisibility(View.VISIBLE);
                llSupplierToCode.setVisibility(View.GONE);
            }
        });
        //供应商名称查询转编码查询
        ivChangeCodeToSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivChangeCodeToSupplier.startAnimation(setAnimation);
                resetSupplier();
                llCodeiToSupplier.setVisibility(View.GONE);
                llSupplierToCode.setVisibility(View.VISIBLE);
            }
        });

        /*****************************************  查询按钮点击事件  *************************************************/

        btnSoftQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listDidVlaue.size()>0){
                    Intent intent = new Intent(myActivity, TransformDidActivity.class);
                    intent.putExtra("listDidVlaue",gson.toJson(listDidVlaue));
                    startActivity(intent);
                   /* Intent intent = new Intent(myActivity,TransformDidActivity.class);
                    final SerializableMap myMap=new SerializableMap();
                    myMap.setMap(listDidVlaue);//将map数据添加到封装的myMap中
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("listDidVlaue", myMap);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                }else{
                    Toast.makeText(myActivity,getString(R.string.text_please_select)+" DID",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //ECU查询
        btnEcuQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<AppendOptionVo> list=new ArrayList<>();
                String ecuName = etEcuName.getText().toString();
                //若ECU Name 为空则不查询
                if(!Tools.isNotNull(ecuName)){
                    Toast.makeText(myActivity,getResources().getString(R.string.text_sharemain_hint_ecuname)+" "+getResources().getString(R.string.text_is_null),Toast.LENGTH_SHORT).show();
                    return;
                }
                //请求
                String url = ServiceUrls.getCommonMethodUrl("selectModuleInfo");
                Map<String,Object> map = new HashMap<>();
                map.put("moduleName",ecuName);
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode == 200) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        if(code==200){
                                            String strData = jsonObject.getString("data");
                                            Type type = new TypeToken<List<AppendOptionVo>>() {}.getType();
                                            List<AppendOptionVo> listEcu=gson.fromJson(strData,type);
                                            selectDialogECU.setData(listEcu, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    popupWindow.dismiss();
                                                    etEcuName.setText(appendOptionVo.getName());//回填ECU名称
                                                    etEcuFullName.setText(appendOptionVo.getFullName());//回填ECU全称
                                                }
                                            });
                                            //显示选择列表
                                            selectDialogECU.show();
                                        }else{
                                            etEcuFullName.setText("");//清空ECU全称
                                            Toast.makeText(myActivity,getResources().getString(R.string.text_no_data),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(myActivity,getResources().getString(R.string.text_network_anomaly),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        //ECU诊断控制查询（ECU名称 to CANID ）
        btnDiagnosisNameToCan.setOnClickListener(new View.OnClickListener() {
            final List<AppendOptionVo> list=new ArrayList<>();
            @Override
            public void onClick(View v) {
                String ecuName = etNameToCanIDEcuName.getText().toString();
                if(!Tools.isNotNull(ecuName)){
                    Toast.makeText(myActivity,getResources().getString(R.string.text_sharemain_hint_ecuname)+" "+getResources().getString(R.string.text_is_null),Toast.LENGTH_SHORT).show();
                    return;
                }
                //请求
                String url = ServiceUrls.getCommonMethodUrl("selectModuleInfo");
                Map<String,Object> map = new HashMap<>();
                map.put("moduleName",ecuName);
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode == 200) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        if(code==200){
                                            String strData = jsonObject.getString("data");
                                            Type type = new TypeToken<List<AppendOptionVo>>(){}.getType();
                                            List<AppendOptionVo> listCan = gson.fromJson(strData,type);
                                            selectDialogECUToCan.setData(listCan, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    popupWindow.dismiss();
                                                    etNameToCanIDEcuName.setText(appendOptionVo.getName());//回填ECU名称
                                                    etNameToCanIDCanID.setText(appendOptionVo.getValue());//回填CANID
                                                }
                                            });
                                            //显示选择列表
                                            selectDialogECUToCan.show();
                                        }else{
                                            etEcuFullName.setText("");//清空ECU全称
                                            Toast.makeText(myActivity,getResources().getString(R.string.text_no_data),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }else{
                                    Toast.makeText(myActivity,getResources().getString(R.string.text_network_anomaly),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
        //ECU诊断控制查询（CANID to ECU名称 ）
        btnDiagnosisCanToName.setOnClickListener(new View.OnClickListener() {
            final List<AppendOptionVo> list=new ArrayList<>();
            @Override
            public void onClick(View v) {
                String canid = etCanIDToNameCanID.getText().toString();
                if(!Tools.isNotNull(canid)){
                    Toast.makeText(myActivity,getResources().getString(R.string.text_sharemain_hint_canid)+" "+getResources().getString(R.string.text_is_null),Toast.LENGTH_SHORT).show();
                    return;
                }
                //请求
                String url = ServiceUrls.getCommonMethodUrl("selectModuleInfo");
                Map<String,Object> map = new HashMap<>();
                map.put("canidValue",canid);
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode == 200) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        if(code==200){
                                            String strData = jsonObject.getString("data");
                                            Type type = new TypeToken<List<AppendOptionVo>>(){}.getType();
                                            List<AppendOptionVo> listCan = gson.fromJson(strData,type);
                                            selectDialogCanToECU.setValueData(listCan, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    popupWindow.dismiss();
                                                    etCanIDToNameCanID.setText(appendOptionVo.getValue());//回填CANID
                                                    etCanIDToNameEcuName.setText(appendOptionVo.getName());//回填ECU名称
                                                }
                                            });
                                            //显示选择列表
                                            selectDialogCanToECU.show();
                                        }else{
                                            etEcuFullName.setText("");//清空ECU全称
                                            Toast.makeText(myActivity,getResources().getString(R.string.text_no_data),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else{
                                    Toast.makeText(myActivity,getResources().getString(R.string.text_network_anomaly),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        //供应商查询（供应商编号 to 名称）
        btnSupplierCodeToName.setOnClickListener(new View.OnClickListener() {
            final List<AppendOptionVo> list=new ArrayList<>();
            @Override
            public void onClick(View v) {
                String code = etCodeToNameCode.getText().toString();
                if(!Tools.isNotNull(code)){
                    Toast.makeText(myActivity,getResources().getString(R.string.text_sharemain_title_supplier)+" "+getResources().getString(R.string.text_is_null),Toast.LENGTH_SHORT).show();
                    return;
                }
                //请求
                String url = ServiceUrls.getCommonMethodUrl("selectSupInfo");
                Map<String,Object> map = new HashMap<>();
                map.put("supplierCode",code);
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode == 200) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        if(code==200){
                                            String strData = jsonObject.getString("data");
                                            Type type = new TypeToken<List<AppendOptionVo>>(){}.getType();
                                            List<AppendOptionVo> listSup = gson.fromJson(strData,type);
                                            selectDialogCodeToName.setValueData(listSup, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    popupWindow.dismiss();
                                                    etCodeToNameCode.setText(appendOptionVo.getValue());//回填code
                                                    etCodeToNameName.setText(appendOptionVo.getName());//回填name
                                                }
                                            });
                                            //显示选择列表
                                            selectDialogCodeToName.show();
                                        }else{
                                            etCodeToNameName.setText("");//清空name
                                            Toast.makeText(myActivity,getResources().getString(R.string.text_no_data),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else{
                                    Toast.makeText(myActivity,getResources().getString(R.string.text_network_anomaly),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
        //供应商查询（供应商名称 to 编号）
        btnSupplierNameToCode.setOnClickListener(new View.OnClickListener() {
            final List<AppendOptionVo> list=new ArrayList<>();
            @Override
            public void onClick(View v) {
                String name = etNameToCodeName.getText().toString();
                if(!Tools.isNotNull(name)){
                    Toast.makeText(myActivity,getResources().getString(R.string.text_sharemain_hint_supplier)+" "+getResources().getString(R.string.text_is_null),Toast.LENGTH_SHORT).show();
                    return;
                }
                //请求
                String url = ServiceUrls.getCommonMethodUrl("selectSupInfo");
                Map<String,Object> map = new HashMap<>();
                map.put("supplierName",name);
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode == 200) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        if(code==200){
                                            String strData = jsonObject.getString("data");
                                            Type type = new TypeToken<List<AppendOptionVo>>(){}.getType();
                                            List<AppendOptionVo> listSup = gson.fromJson(strData,type);
                                            selectDialogNameToCode.setData(listSup, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    popupWindow.dismiss();
                                                    etNameToCodeName.setText(appendOptionVo.getName());//回填name
                                                    etNameToCodeCode.setText(appendOptionVo.getValue());//回填code
                                                }
                                            });
                                            //显示选择列表
                                            selectDialogNameToCode.show();
                                        }else{
                                            etCodeToNameName.setText("");//清空name
                                            Toast.makeText(myActivity,getResources().getString(R.string.text_no_data),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else{
                                    Toast.makeText(myActivity,getResources().getString(R.string.text_network_anomaly),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
        //ECU生产线查询
        btnProductionLine.setOnClickListener(new View.OnClickListener() {
            final List<AppendOptionVo> list=new ArrayList<>();
            @Override
            public void onClick(View v) {
                String ecuname = etProductionEcuName.getText().toString();
                if(!Tools.isNotNull(ecuname)){
                    Toast.makeText(myActivity,getResources().getString(R.string.text_sharemain_hint_ecuname)+" "+getResources().getString(R.string.text_is_null),Toast.LENGTH_SHORT).show();
                    return;
                }
                //请求
                String url = ServiceUrls.getCommonMethodUrl("selectEOL");
                Map<String,Object> map = new HashMap<>();
                map.put("input",ecuname);
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccess && responseCode == 200) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        if(code==200){
                                            String strData = jsonObject.getString("data");
                                            Type type = new TypeToken<List<AppendOptionVo>>(){}.getType();
                                            List<AppendOptionVo> listSup = gson.fromJson(strData,type);
                                            selectDialogEOLContent.setData(listSup, new BottomOptionDialog.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                                    popupWindow.dismiss();
                                                    etProductionEcuName.setText(appendOptionVo.getName());//回填name
                                                    String content = appendOptionVo.getFullName().replace("\n"," ");
                                                    etProductEolContent.setText(content);//回填output
                                                }
                                            });
                                            //显示选择列表
                                            selectDialogEOLContent.show();
                                        }else{
                                            etProductEolContent.setText("");//清空name
                                            Toast.makeText(myActivity,getResources().getString(R.string.text_no_data),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else{
                                    Toast.makeText(myActivity,getResources().getString(R.string.text_network_anomaly),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });



        //ECU全称点击事件
        etEcuFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    String ecuFullName = etEcuFullName.getText().toString();
                    if(Tools.isNotNull(ecuFullName)){
                        selectDialogQuery = new ShareQueryDetailDialog(myActivity,getResources().getString(R.string.text_detail_info),ecuFullName);
                        selectDialogQuery.show(getFragmentManager(),"");
                    }
                }
            }
        });
        //诊断专家CANID点击
        etNameToCanIDCanID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    String canID = etNameToCanIDCanID.getText().toString();
                    if(Tools.isNotNull(canID)){
                        selectDialogQuery = new ShareQueryDetailDialog(myActivity,getResources().getString(R.string.text_detail_info),canID);
                        selectDialogQuery.show(getFragmentManager(),"");
                    }
                }
            }
        });
        //诊断专家ECU Name点击
        etCanIDToNameEcuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    String ecuname = etCanIDToNameEcuName.getText().toString();
                    if(Tools.isNotNull(ecuname)){
                        selectDialogQuery = new ShareQueryDetailDialog(myActivity,getResources().getString(R.string.text_detail_info),ecuname);
                        selectDialogQuery.show(getFragmentManager(),"");
                    }
                }
            }
        });
        //供应商查询 供应商名称点击
        etCodeToNameName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    String ecuname = etCodeToNameName.getText().toString();
                    if(Tools.isNotNull(ecuname)){
                        selectDialogQuery = new ShareQueryDetailDialog(myActivity,getResources().getString(R.string.text_detail_info),ecuname);
                        selectDialogQuery.show(getFragmentManager(),"");
                    }
                }
            }
        });
        //供应商查询 供应商名称点击
        etNameToCodeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    String ecuname = etNameToCodeCode.getText().toString();
                    if(Tools.isNotNull(ecuname)){
                        selectDialogQuery = new ShareQueryDetailDialog(myActivity,getResources().getString(R.string.text_detail_info),ecuname);
                        selectDialogQuery.show(getFragmentManager(),"");
                    }
                }
            }
        });
        //EOL 生产线查询 EOL内容点击事件
        etProductEolContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tools.isFastClick()){
                    String ecuname = etProductEolContent.getText().toString();
                    if(Tools.isNotNull(ecuname)){
                        selectDialogQuery = new ShareQueryDetailDialog(myActivity,getResources().getString(R.string.text_detail_info),ecuname);
                        selectDialogQuery.show(getFragmentManager(),"");
                    }
                }
            }
        });
        //输入框点击事件
        etEcuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEcuName.setKeyBoard(myActivity);
            }
        });
        etNameToCanIDEcuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNameToCanIDEcuName.setKeyBoard(myActivity);
            }
        });

        etCanIDToNameCanID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCanIDToNameCanID.setKeyBoard(myActivity);
            }
        });

        etNameToCodeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNameToCodeName.setKeyBoard(myActivity);
            }
        });

        etCodeToNameCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCodeToNameCode.setKeyBoard(myActivity);
            }
        });
        etProductionEcuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etProductionEcuName.setKeyBoard(myActivity);
            }
        });


    }

    //Query页面初始化
    private void initQueryView() {
        ecuDialog=new BottomOptionDialog(myActivity, getResources().getString(R.string.text_security_ecu));
        supDialog=new BottomOptionDialog(myActivity, getResources().getString(R.string.text_security_supplier));
        didDialog=new BottomOptionDialog(myActivity, getResources().getString(R.string.text_detection_ecu_did));
        selectDialogECU = new BottomOptionDialog(myActivity,getString(R.string.text_sharemain_hint_code));
        selectDialogECUToCan = new BottomOptionDialog(myActivity,getString(R.string.text_sharemain_hint_ecuname));
        selectDialogCanToECU = new BottomOptionDialog(myActivity,getString(R.string.text_sharemain_hint_canid));
        selectDialogNameToCode = new BottomOptionDialog(myActivity,getString(R.string.text_sharemain_hint_supplier));
        selectDialogCodeToName = new BottomOptionDialog(myActivity,getString(R.string.text_sharemain_hint_code));
        selectDialogEOLContent = new BottomOptionDialog(myActivity,getString(R.string.text_sharemain_hint_ecuname));
        etEcuName.initKeyBoard(myActivity);//初始化编辑框键盘
        etNameToCanIDEcuName.initKeyBoard(myActivity);//初始化编辑框键盘
        etCanIDToNameCanID.initKeyBoard(myActivity);//初始化编辑框键盘
        etNameToCodeName.initKeyBoard(myActivity);//初始化编辑框键盘
        etCodeToNameCode.initKeyBoard(myActivity);//初始化编辑框键盘
        etProductionEcuName.initKeyBoard(myActivity);//初始化编辑框键盘
    }
    /**
     * 绑定供应商下拉框
     * @param moduleId
     */
    public void selectSupplierByModuleId(int moduleId){

        //获取车型信息
        String url= ServiceUrls.getShareMethodUrl("selectSupplierByModuleId");
        Map<String,Object> map=new HashMap<>();
        map.put("moduleId",moduleId);
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                List<AppendOptionVo> SupList=null;//供应商列表
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    SupList = gson.fromJson(data,type);
                                    supDialog.setData(SupList, new BottomOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            didDialog.removeSaveId();//清除上次保存的小勾勾
                                            tvSelectDID.setText(getString(R.string.text_please_select));//清除did选项框
                                            listDidVlaue.clear();//清空小方块list
                                            llDidBulk.removeAllViews();//移除所有DID小块
                                            tvSelectSupplier.setText(appendOptionVo.getName());//设置模块文本
                                            selectDidContent(saveModuleId,appendOptionVo.getId());//查询供应商
                                            popupWindow.dismiss();//关闭弹窗
                                        }
                                    });
                                    //supDialog.show();//显示
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismissImmediately();//关闭
                    }
                });
            }
        });
    }

    /**
     * 绑定DID下拉框
     * @param moduleId
     * @param supplierId
     */
    public void selectDidContent(Integer moduleId,Integer supplierId){
        //获取did信息
        String url= ServiceUrls.getShareMethodUrl("findDidByModSup");
        Map<String,Object> map=new HashMap<>();
        map.put("moduleId",moduleId);
        map.put("supplierId",supplierId);
        promptDialog.showLoading(getString(R.string.text_loading));//显示加载框
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                List<AppendOptionVo> DidList=null;//did列表
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                String data=jsonObject.getString("data");
                                if (code==200){
                                    Type type=new TypeToken<List<AppendOptionVo>>(){}.getType();
                                    DidList = gson.fromJson(data,type);
                                    didDialog.setData(DidList, new BottomOptionDialog.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo) {
                                            AppendOptionVo didValue = new AppendOptionVo();
                                            didValue.setId(appendOptionVo.getId());
                                            didValue.setName(appendOptionVo.getName());
                                            tvSelectDID.setText(appendOptionVo.getName());//设置DID文本
                                                /*if(!listDidVlaue.containsKey(appendOptionVo.getId())){
                                                    //最多只能选择四条
                                                    if(listDidVlaue.size()<4){
                                                        listDidVlaue.put(appendOptionVo.getId(),appendOptionVo.getName());//添加到小方块list
                                                        addDidBulk(appendOptionVo.getId(),appendOptionVo.getName());//添加小方块
                                                    }else{
                                                        Toast.makeText(myActivity,getString(R.string.text_only_choose_four),Toast.LENGTH_SHORT).show();
                                                    }
                                                }*/
                                            if(listDidVlaue.indexOf(appendOptionVo)==-1){
                                                //最多只能选择四条
                                                AppendOptionVo didVal = new AppendOptionVo();
                                                if(listDidVlaue.size()<4){
                                                    listDidVlaue.add(appendOptionVo);
                                                    addDidBulk(appendOptionVo.getId(),appendOptionVo.getName()+"("+appendOptionVo.getValue()+")");//添加小方块
                                                }else{
                                                    Toast.makeText(myActivity,getString(R.string.text_only_choose_four),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            popupWindow.dismiss();//关闭弹窗
                                        }
                                    });
                                    //supDialog.show();//显示
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        promptDialog.dismissImmediately();//关闭
                    }
                });
            }
        });
    }

    /**
     *
     * @param didID
     * @param didName
     */
    public void addDidBulk(final int didID, final String didName){

        // 1.创建外围LinearLayout控件
        final LinearLayout layout = new LinearLayout(myActivity);
        LinearLayout.LayoutParams lLayoutlayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,95);
        // 设置margin
        lLayoutlayoutParams.setMargins(0, 0, 15, 0);

        layout.setLayoutParams(lLayoutlayoutParams);
        // 设置属性
        layout.setBackground(getResources().getDrawable(R.drawable.btn_border_radius_fill_green));   //
        layout.setPadding(30,0,30,0);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        // 2.创建内部TextView控件
        TextView tvContent = new TextView(myActivity);
        LinearLayout.LayoutParams etParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 100);
        tvContent.setLayoutParams(etParam);
        // 设置属性
        tvContent.setGravity(Gravity.CENTER);
        tvContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        tvContent.setTextSize(14);
        tvContent.setText(didName);
        tvContent.setTextColor(getResources().getColor(R.color.colorWhite));
        // 将EditText放到LinearLayout里
        layout.addView(tvContent);

        //3.创建ImageView控件
        ImageView ivClose = new ImageView(myActivity);
        LinearLayout.LayoutParams ivParam = new LinearLayout.LayoutParams(50,50);
        ivClose.setLayoutParams(ivParam);
        ivClose.setImageResource(R.drawable.ic_close);
        layout.addView(ivClose);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移除list
                for(int i=0;i<listDidVlaue.size();i++){
                    if(listDidVlaue.get(i).getId()==didID){
                        listDidVlaue.remove(i);
                    }
                }
                //移除小方块
                llDidBulk.removeView(layout);
            }
        });

        //4.将最外层layout添加到指定位置
        llDidBulk.addView(layout);
    }

    /**
     * 清空诊断控制模块所有值
     */
    public void resetDiagnosis(){
        etNameToCanIDEcuName.setText("");
        etNameToCanIDCanID.setText("");
        etCanIDToNameCanID.setText("");
        etCanIDToNameEcuName.setText("");
    }

    /**
     * 清空供应商查询模块所有值
     */
    public void resetSupplier(){
        etNameToCodeName.setText("");
        etNameToCodeCode.setText("");
        etCodeToNameCode.setText("");
        etNameToCodeName.setText("");
    }
}