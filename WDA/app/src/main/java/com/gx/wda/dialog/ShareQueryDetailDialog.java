package com.gx.wda.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gx.wda.R;

public class ShareQueryDetailDialog extends DialogFragment {
    private Context context;//上下文
    private Dialog dialog;//弹框
    private View view;
    private TextView tvTitle;//标题
    private TextView tvDatail;//选择
    private LinearLayout llCancel;//取消
    private String title;//标题
    private String detail;//内容
    public ShareQueryDetailDialog(@NonNull Context context , String title , String detail) {
        this.context=context;
        this.title=title;
        this.detail=detail;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog=new Dialog(context, R.style.dialog);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘就会把dialog弹起，有的手机则会遮住dialog布局。
        view= View.inflate(getActivity(), R.layout.dialog_share_query_showdata,null);
        dialog.setContentView(view);
        tvTitle=view.findViewById(R.id.tv_title);
        tvDatail=view.findViewById(R.id.tv_detail_data);
        llCancel=view.findViewById(R.id.ll_cancel);
        initView();//初始化
        setViewListener();//事件监听
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.windowAnimations= R.style.dialog_bottom_top;//设置弹窗动画
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog;
    }

    /**
     * 初始化
     */
    private void initView() {
        tvTitle.setText(title);
        tvDatail.setText(detail);
    }

    /**
     * 事件监听
     */
    private void setViewListener() {
        //取消
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
