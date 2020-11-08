package com.gx.wda.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.ui.LoginActivity;

/**
 * 退出应用弹出框
 */
public class LogOutDialog extends DialogFragment {
    private Activity activity;//上下文
    private MyApplication myApplication;
    private Dialog dialog;//弹框
    private View view;
    private LinearLayout llExit;//退出
    private LinearLayout llClose;//关闭
    private LinearLayout llCancel;//取消

    public LogOutDialog(@NonNull Activity activity) {
        this.activity=activity;
        myApplication= (MyApplication) activity.getApplication();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog=new Dialog(activity,R.style.dialog);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘就会把dialog弹起，有的手机则会遮住dialog布局。
        view = View.inflate(getActivity(), R.layout.dialog_logout,null);
        dialog.setContentView(view);
        llExit=view.findViewById(R.id.ll_exit);
        llClose=view.findViewById(R.id.ll_close);
        llCancel=view.findViewById(R.id.ll_cancel);
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.windowAnimations=R.style.dialog_bottom_top;//设置弹窗动画
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setViewListener();
        return dialog;
    }

    /**
     * 事件监听
     */
    private void setViewListener(){
        //退出
        llExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, LoginActivity.class);
                startActivity(intent);
                myApplication.destroyWebSocketClient();//关闭消息推送
                myApplication.destroy();//释放拉流推流资源
                activity.finish();//关闭主页面
            }
        });
        //关闭
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication.destroyWebSocketClient();//关闭消息推送
                myApplication.destroy();//释放拉流推流资源
                activity.finish();//关闭主页面
            }
        });
        //取消
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
