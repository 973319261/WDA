package com.gx.wda.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.gx.wda.util.MyWebSocketClient;

import java.net.URI;

/**
 * Service和Activity之间通讯
 */
public class JWebSocketClientService extends Service {
    private URI uri;
    public MyWebSocketClient client;
    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();

    //用于Activity和service通讯
    public class JWebSocketClientBinder extends Binder {
        public JWebSocketClientService getService() {
            return JWebSocketClientService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}