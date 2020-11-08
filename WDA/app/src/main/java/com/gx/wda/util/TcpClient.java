package com.gx.wda.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.ui.MainActivity;
import com.gx.wda.ui.dataStream.DataStreamStateFragment;
import com.suke.widget.SwitchButton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpClient implements Runnable {
    ExecutorService exec = Executors.newCachedThreadPool();
    private MyApplication myApplication;
    private String TAG = "TcpClient";
    private String serverIP = "";
    private int serverPort = 8400;
    private InputStream is;
    private OutputStream out;
    private DataInputStream dis;
    private boolean isBuzzing=false;

    private Socket socket = null;

    AppCompatActivity mainActivity = (AppCompatActivity) MainActivity.context;
    private static final String INTENT_BROADCAST = "android.intent.action.MEDICAL_BROADCAST";// 广播跳转意图


    public TcpClient(MyApplication myApplication,String ip , int port){
        this.myApplication = myApplication;
        this.serverIP = ip;
        this.serverPort = port;
    }

    public void closeSelf(){
        try {
            if(out!=null){
                out.close();
            }
            if(is!=null){
                is.close();
            }
            if(dis!=null){
                dis.close();
            }
            if(socket!=null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) throws IOException {
        if(out!=null){
            byte[] b = EncodingUtil.hexToByte(msg);
            out.write(b);
            Log.e("发送报文：",msg);
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        try {

            socket = new Socket(serverIP,serverPort);
            socket.setSoTimeout(10000);


           // myApplication.setTcpClient(this);
            out = socket.getOutputStream();

            //连接成功，蜂鸣通知
            if(!myApplication.isBuzzing()){
                myApplication.setBuzzing(true);


                //蜂鸣提示
                String buzzing = "AA FE 0E 00 03 00 0A 00 60 09 A0 00 80 05 A0 00 01 00".replace(" ","");
                String xor = EncodingUtil.getBCC(EncodingUtil.hexToByte(buzzing));
                buzzing += xor;
                send(buzzing);


                new Thread(new Runnable() {
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
                            send(setVciTime);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


                //提示连接成功
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.timerLink().cancel();////开启定时
                        MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_connected));//显示已连接
                        Toast.makeText(MainActivity.context,"与VCI建立连接成功",Toast.LENGTH_SHORT).show();
                    }
                });




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
                            send(sendContinuous);
                        } catch (InterruptedException | IOException e) {
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
                            send(softRecord);
                        } catch (InterruptedException | IOException e) {
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
                            send(softRecord);
                        } catch (InterruptedException | IOException e) {
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
                            send(softRecord);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }


            is = socket.getInputStream();
            dis = new DataInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            /*Intent intent =new Intent();
            myApplication.setTcpClient(null);
            intent.setAction("tcpMainReceiver");
            intent.putExtra("tcpMainReceiver","false");
            MainActivity.context.sendBroadcast(intent);//将消息发送给主界面*/
            if(!Tools.isNotNull(myApplication.getVciIp())){
                //如果VCI IP不为空 重连
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myApplication.setLinkState(false);
                        MainActivity.timerLink().start();//开启定时
                        MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_ununited));
                    }
                });
                exec.execute(new TcpClient(myApplication,serverIP,serverPort));
            }
        }
//        while (isRun&&socket!=null){
            try {
                //封装输入流（接收客户端的流）
                if(socket!=null){
                    InputStream is = socket.getInputStream();
                    DataInputStream input = new DataInputStream(is);
                    byte[] b = new byte[5120];
                    int len = 0;
                    int count = 0;
                    String ret = "";
                    while (socket!=null) {
                        //变更连接状态
                        if(!myApplication.isLinkState()){
                            myApplication.setLinkState(true);
                        }
                        try{
                            len = input.read(b);
                            if(len>0){
                                byte[] bs = EncodingUtil.subByte(b,0,len);
                                String msg = EncodingUtil.toHexString(bs);
                               // Log.e("报文",msg);
                                String MSG_HEADER = msg.substring(0,4).toLowerCase();

                                if(MSG_HEADER.contains("aafe")||msg.toUpperCase().contains("0462C110")){
                                    //Log.e("查询VCI状态",msg);
                                    Intent intent =new Intent();
                                    intent.setAction("tcpMainReceiver");
                                    intent.putExtra("tcpMainReceiver",msg);
                                    MainActivity.context.sendBroadcast(intent);//将消息发送给主界面
                                }else{
                                    //AA001400 + 时间戳 + 080018070000065003003201F4 + 填充位 + 校验位  映射首次回应的都是这个
                                    //临时映射成功 080018070000057101F00500
                                    //  080018070000047101F005 取消临时映射
                                    //永久映射
                                    // 得到的Seed值 080018070000066701
                                    // 已收到Key的报文  026702
                                    // 完成永久映射  036EC110
                                    //错误帧 080018070000037F + 服务 + 78（等待中，不作处理）/其他（错误帧，需要提示）
                                    if(msg.toUpperCase().contains("080018070000065003003201F4")||msg.toUpperCase().contains("080018070000057101F005")||msg.toUpperCase().contains("080018070000057101F004")
                                            ||msg.toUpperCase().contains("080018070000066701")||msg.toUpperCase().contains("026702")
                                            ||msg.toUpperCase().contains("036EC110")
                                            ||msg.toUpperCase().contains("080018070000047101F005")
                                            ||msg.toUpperCase().contains("080018070000037F")){
                                        //将报文发送给配置页面
                                        //Log.e("映射指令",msg);
                                        Intent intent =new Intent();
                                        intent.setAction("tcpMapReceiver");
                                        intent.putExtra("tcpMapReceiver",msg);
                                        MainActivity.context.sendBroadcast(intent);//将报文发送给配置页面
                                    }
                                    ret = msg + ",";
                                    count++;
                                    if(count==2){
                                        if (DataStreamStateFragment.context != null) {
                                            Intent intent = new Intent();
                                            intent.setAction("tcpClientReceiver");
                                            intent.putExtra("tcpClientReceiver", ret);
                                            DataStreamStateFragment.context.sendBroadcast(intent);//将消息发送给报文监听界面
                                        }
                                        count = 0;
                                        ret = "";
                                    }else{
                                        continue;
                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            if(!Tools.isNotNull(myApplication.getVciIp())){
                                //如果VCI IP不为空 重连
                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        myApplication.setLinkState(false);
                                        MainActivity.timerLink().start();//开启定时
                                        MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_ununited));
                                    }
                                });
                                exec.execute(new TcpClient(myApplication,serverIP,serverPort));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                /*Intent intent =new Intent();
                myApplication.setTcpClient(null);
                intent.setAction("tcpMainReceiver");
                intent.putExtra("tcpMainReceiver",e.toString());
                MainActivity.context.sendBroadcast(intent);//将消息发送给主界面*/
                if(!Tools.isNotNull(myApplication.getVciIp())){
                    //如果VCI IP不为空 重连
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myApplication.setLinkState(false);
                            MainActivity.timerLink().start();//开启定时
                            MainActivity.tvLinkVci.setText(MainActivity.context.getString(R.string.text_ununited));
                        }
                    });
                    exec.execute(new TcpClient(myApplication,serverIP,serverPort));
                }
            }

//        }
        closeSelf();
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }





}
