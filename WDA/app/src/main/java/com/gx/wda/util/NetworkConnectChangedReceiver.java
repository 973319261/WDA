package com.gx.wda.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.gx.wda.dialog.NetStateChangedDialog;


/**
 * Created by dingmouren on 2018/8/1.
 * 监听网络状态变更的广播接收器
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private NetStateChangedDialog mNetStateChangedDialog;/*网络状态变化的提示对话框*/
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);;
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null&&networkInfo.isAvailable())
        {
            if (null != mNetStateChangedDialog)mNetStateChangedDialog.dismiss();
        }else
        {
            mNetStateChangedDialog=new NetStateChangedDialog(context);
            if (null != mNetStateChangedDialog)mNetStateChangedDialog.show();
        }
    }


}