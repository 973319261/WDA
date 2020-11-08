package com.gx.wda.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gx.wda.R;

/**
 * 网络状态弹窗
 */
public class NetStateChangedDialog extends Dialog {
    public NetStateChangedDialog(@NonNull Context context) {
        super(context, R.style.StyleNetChangedDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_network_state_tip,null);
        initView(contentView);
        setContentView(contentView);
        setCanceledOnTouchOutside(false);
        TextView tvContent=contentView.findViewById(R.id.tv_content);
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWirelessSettings(context);
            }
        });
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.windowAnimations = R.style.StyleNetChangedDialog_Animation;
        getWindow().setAttributes(layoutParams);
        getWindow().setDimAmount(0.3f);/*使用时设置窗口后面的暗淡量*/
    }

    private void initView(View contentView) {

        ImageView close=contentView.findViewById(R.id.iv_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    /**
     * 打开网络设置界面
     * <p>3.0以下打开设置界面</p>
     */
    private static void openWirelessSettings(Context context) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

}