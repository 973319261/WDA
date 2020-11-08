package com.gx.wda.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.gx.wda.R;
import com.gx.wda.bean.Version;
import com.gx.wda.util.MPermissionUtils;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.widget.ProgressButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * 版本升级弹窗
 */
public class VersionUpdateDialog extends DialogFragment {
    private Dialog dialog;//弹框
    private View view;
    private TextView content;//内容
    private LinearLayout llClose;//关闭
    private ImageView ivClose;//关闭
    private ProgressButton progressButton;//进度条按钮
    private Context context;
    private Activity activity;
    private String download = "/data/data/com.gx.wda/";
    private String url;
    private Version version;
    public VersionUpdateDialog(@NonNull Context context, Version version) {
        activity= (Activity) context;
        this.context=context;
        this.version=version;
        this.url= ServiceUrls.serviceUrl+version.getApkUrl();
    }
    public void setView(View view){
        this.view=view;
    }
    public void setUrl(String url){
        this.url=url;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog=new Dialog(context, R.style.dialog);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘就会把dialog弹起，有的手机则会遮住dialog布局。
        view= View.inflate(getActivity(), R.layout.dialog_version_update,null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        content=view.findViewById(R.id.tv_content);
        progressButton=view.findViewById(R.id.button_progress);
        llClose=view.findViewById(R.id.ll_close);
        ivClose=view.findViewById(R.id.iv_close);
        initView();//初始化
        setViewListener();//事件监听
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.windowAnimations= R.style.dialog_bottom_top;//设置弹窗动画
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog;
    }
    private void initView(){
        //初始化
        progressButton.setMinProgress(0);
        progressButton.setMaxProgress(100);
        content.setText(Html.fromHtml(version.getVersionContent()));
        if (version.getIsForce()){//强制更新
            llClose.setVisibility(View.GONE);
            progressButton.setText(R.string.text_force_update);

            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        activity.finish();
                    }
                    return false;
                }
            });
        }else {
            llClose.setVisibility(View.VISIBLE);
            progressButton.setText(R.string.text_promptly_update);
        }
    }
    private void setViewListener(){
        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions=new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};//写入存储权限
                if (MPermissionUtils.checkPermissions(activity, permissions)) {//检查是否有权限
                    loadNewVersionProgress();
                }else {//没有权限
                    MPermissionUtils.showTipsDialog(activity,getString(R.string.text_permissions_storage));//提示
                }
            }
        });
        //关闭
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    /**
     * 下载新版本程序，需要子线程
     */
    boolean flag=false;
    private void loadNewVersionProgress() {

        //启动子线程下载任务
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(url);
                    sleep(1000);
                    applyInstallCheck(file);
                } catch (Exception e) {
                    //下载apk失败
                    flag=true;
                    e.printStackTrace();
                }
            }}.start();
        if (flag){
            Toast.makeText(context, R.string.text_update_failure, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public  File getFileFromServer(String uri) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(uri);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            int size=conn.getContentLength();
            //获取到文件的大小
          //  pd.setMax(conn.getContentLength());

            InputStream is = conn.getInputStream();
            long time= System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(download, time+"wda.apk");
            File path=new File(download);
            if (!path.exists()) {
                //创建目录;
                path.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                int progress=total*100/size;//进度条
             activity.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     progressButton.setEnabled(false);
                     progressButton.setText(String.format(Locale.getDefault(),"%d%%",progress));
                     if (progress==100){
                         progressButton.setText(R.string.text_download_complete);
                     }
                     progressButton.setProgress( progress);
                 }
             });
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }
    private void applyInstallCheck(File file){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            boolean b=activity.getPackageManager().canRequestPackageInstalls();
            if (!b){
                String[] permissions=new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};//写入存储权限
                if (MPermissionUtils.checkPermissions(activity, permissions)) {//检查是否有权限
                    installAPK(file);
                }else {//没有权限
                    MPermissionUtils.showTipsDialog(activity,"请您开启手机存储权限");//提示
                }
            }else {
                installAPK(file);
            }
        }else {
            installAPK(file);
        }
    }
    /**
     * 打开apk安装包（安装）
     * @param fileSavePath
     */
    /**
     * 安装apk
     */
    private void installAPK(File savedFile) {
        //调用系统的安装方法
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(activity, "com.gx.wda.install", savedFile);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(savedFile);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
        activity.finish();
    }

    /**
     * 显示弹窗
     * @param fragmentManager
     * @param s
     */
    public void show(FragmentManager fragmentManager, String s){
        dialog.show();
    }

}
