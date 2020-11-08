package com.gx.wda.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gx.wda.R;
import com.gx.wda.bean.FaultCodeVo;

/**
 * 故障码信息弹窗
 */
public class FaultCodeInfoDialog extends DialogFragment {
    private Context context;
    private FaultCodeVo faultCodeVo;//数据
    private Dialog dialog;//弹窗
    private View view;
    private ImageView ivClose;//关闭
    public FaultCodeInfoDialog(@NonNull Context context ,FaultCodeVo faultCodeVo){
        this.context=context;
        this.faultCodeVo=faultCodeVo;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog=new Dialog(context, R.style.dialog);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘就会把dialog弹起，有的手机则会遮住dialog布局。
        view = View.inflate(getActivity(),R.layout.dialog_faultcode_info,null);
        dialog.setContentView(view);
        initView();//初始化
        setViewListener();//事件监听
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
        return dialog;
    }

    /**
     * 初始化页面
     */
    private void initView(){
        TextView tvVehicle=view.findViewById(R.id.tv_dtc_vehicle);
        TextView tvConfigure=view.findViewById(R.id.tv_dtc_configure);
        TextView tvModule=view.findViewById(R.id.tv_dtc_module);
        TextView tvSupplier=view.findViewById(R.id.tv_dtc_supplier);
        TextView tvSae=view.findViewById(R.id.tv_dtc_sae);
        TextView tvHex=view.findViewById(R.id.tv_dtc_hex);
        TextView tvChinese=view.findViewById(R.id.tv_dtc_chinese);
        TextView tvEnglish=view.findViewById(R.id.tv_dtc_english);
        TextView tvOperating=view.findViewById(R.id.tv_dtc_operating);
        TextView tvSetting=view.findViewById(R.id.tv_dtc_setting);
        TextView tvSettingAfter=view.findViewById(R.id.tv_dtc_setting_after);
        TextView tvRestore=view.findViewById(R.id.tv_dtc_restore);
        TextView tvActivate=view.findViewById(R.id.tv_dtc_activate);
        TextView tvMilOff=view.findViewById(R.id.tv_dtc_mil_off);
        TextView tvClear=view.findViewById(R.id.tv_dtc_clear);
        ivClose=view.findViewById(R.id.iv_close);
        String noData=getString(R.string.text_no_data);//暂无数据
        String vehicle=faultCodeVo.getVehicleName()==null?noData:faultCodeVo.getVehicleName().trim();
        String configure=faultCodeVo.getConfigurationLevelName()==null?noData:faultCodeVo.getConfigurationLevelName().trim();
        String module=faultCodeVo.getModuleName()==null?noData:faultCodeVo.getModuleName().trim();
        String supplier=faultCodeVo.getSupplierName()==null?noData:faultCodeVo.getSupplierName().trim();
        String sae=faultCodeVo.getDtc()==null?noData:faultCodeVo.getDtc().trim();
        String hex=faultCodeVo.getHexDtc()==null?noData:faultCodeVo.getHexDtc().trim();
        String chinese=faultCodeVo.getChineseDescription()==null?noData:faultCodeVo.getChineseDescription().trim();
        String english=faultCodeVo.getEnglishDescription()==null?noData:faultCodeVo.getEnglishDescription().trim();
        String operating=faultCodeVo.getOperatingConditions()==null?noData:faultCodeVo.getOperatingConditions().trim();
        String setting=faultCodeVo.getSettingConditions()==null?noData:faultCodeVo.getSettingConditions().trim();
        String settingAfter=faultCodeVo.getSettingAfterConditions()==null?noData:faultCodeVo.getSettingAfterConditions().trim();
        String restore=faultCodeVo.getRestoreConditions()==null?noData:faultCodeVo.getRestoreConditions().trim();
        String activate=faultCodeVo.getActivateMilRegulations()==null?noData:faultCodeVo.getActivateMilRegulations().trim();
        String milOff=faultCodeVo.getMilOffRegulations()==null?noData:faultCodeVo.getMilOffRegulations().trim();
        String clear=faultCodeVo.getClearConditions()==null?noData:faultCodeVo.getClearConditions().trim();
        tvVehicle.setText(getString(R.string.text_security_history_vehicle)+vehicle);
        tvConfigure.setText(getString(R.string.text_history_configuration)+configure);
        tvModule.setText(getString(R.string.text_security_history_ecu)+module);
        tvSupplier.setText(getString(R.string.text_security_history_supplier)+supplier);
        tvSae.setText(getString(R.string.text_dtc_dtc)+sae);
        tvHex.setText(getString(R.string.text_dtc_hex_dtc)+hex);
        tvChinese.setText(getString(R.string.text_dtc_chinese_description)+chinese);
        tvEnglish.setText(getString(R.string.text_dtc_english_description)+english);
        tvOperating.setText(getString(R.string.text_dtc_operating_conditions)+operating);
        tvSetting.setText(getString(R.string.text_dtc_setting_conditions)+setting);
        tvSettingAfter.setText(getString(R.string.text_dtc_setting_after_conditions)+settingAfter);
        tvRestore.setText(getString(R.string.text_dtc_restore_conditions)+restore);
        tvActivate.setText(getString(R.string.text_dtc_activate_mil_regulations)+activate);
        tvMilOff.setText(getString(R.string.text_dtc_mil_off_regulations)+milOff);
        tvClear.setText(getString(R.string.text_dtc_clear_conditions)+clear);
    }

    /**
     * 设置监听事件
     */
    private void setViewListener(){
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
