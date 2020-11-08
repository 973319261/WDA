package com.gx.wda.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.wda.R;
import com.gx.wda.bean.SecurityRecordVo;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.ui.security.SeedKeyActivity;
import com.gx.wda.ui.security.SeedPinKeyActivity;
import com.gx.wda.ui.security.VinEskActivity;
import com.gx.wda.ui.security.VinPinActivity;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.SecurityRecordUtil;
import com.gx.wda.util.ServiceUrls;
import com.gx.wda.util.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 安全算法历史列表适配器
 */
public class SecurityRecordAdapter extends RecyclerView.Adapter<SecurityRecordAdapter.ViewHolder>{
    private Activity myActivity;
    private List<SecurityRecordVo> list;
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    public SecurityRecordAdapter(Activity activity){
        this.myActivity = activity;
        this.list=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(myActivity).inflate(R.layout.item_rv_security_record_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            final SecurityRecordVo securityRecordVo=list.get(position);
            if (securityRecordVo.getTypeId() > 0){//不是空数据时
                String strPicture = securityRecordVo.getVehiclePicture();
                holder.ivRecord.setImageResource(R.drawable.ic_security_record);//设置图片
                if (Tools.isNotNull(strPicture)) {
                    String pictureUrl = ServiceUrls.serviceUrl+"fileDir/vehicleImage/"+strPicture.trim();
                    //使用Glide加载图片
                    Glide.with(myActivity)
                            .load(pictureUrl)
                            .error(R.drawable.ic_security_record)
                            .into(holder.ivRecord)
                    .getRequest();
                }
                holder.tvTypeName.setText(securityRecordVo.getTypeName());//设置类型名称
                holder.tvVehicle.setText(securityRecordVo.getVehicleName());//设置车型
                holder.tvEcu.setText(securityRecordVo.getModuleName());//设置模块
                holder.tvSupplier.setText(securityRecordVo.getSupplierName());//设置供应商
                if (securityRecordVo.getTypeId()==1){//Seed-->Key类型
                    holder.llLevel.setVisibility(View.VISIBLE);//显示等级
                    holder.tvLevel.setText(securityRecordVo.getArithmeticLevelName());//设置等级名称
                }else {
                    holder.llLevel.setVisibility(View.GONE);//隐藏等级
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tools.isFastClick()){
                            if (securityRecordVo.getTypeId()==1){//Seed-->Key
                                Intent intent=new Intent(myActivity, SeedKeyActivity.class);
                                intent.putExtra("securityRecordVo",gson.toJson(securityRecordVo));
                                intent.putExtra("isHistory",true);
                                myActivity.startActivity(intent);
                            }else if (securityRecordVo.getTypeId()==2){//Vin-->Pin
                                Intent intent=new Intent(myActivity, VinPinActivity.class);
                                intent.putExtra("securityRecordVo",gson.toJson(securityRecordVo));
                                intent.putExtra("isHistory",true);
                                myActivity.startActivity(intent);
                            }else if (securityRecordVo.getTypeId()==3){//Vin-->Esk
                                Intent intent=new Intent(myActivity, VinEskActivity.class);
                                intent.putExtra("securityRecordVo",gson.toJson(securityRecordVo));
                                intent.putExtra("isHistory",true);
                                myActivity.startActivity(intent);
                            }else if (securityRecordVo.getTypeId()==4){//S&P-->Key
                                Intent intent=new Intent(myActivity, SeedPinKeyActivity.class);
                                intent.putExtra("securityRecordVo",gson.toJson(securityRecordVo));
                                intent.putExtra("isHistory",true);
                                myActivity.startActivity(intent);
                            }
                        }
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, myActivity.getString(R.string.text_sure_delect), new MessageDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm==true){//确定按钮
                                    list.remove(position);
                                    holder.ivRecord.setImageResource(R.drawable.ic_security_record);//设置图片
                                    SecurityRecordVo securityRecordVo1=new SecurityRecordVo();
                                    securityRecordVo1.setTypeId(0);
                                    list.add(securityRecordVo1);//添加一条空数据
                                    notifyDataSetChanged();//刷新
                                    SecurityRecordUtil.deleteSecurityRecord(myActivity,position);//通过索引删除安全算法记录
                                }
                                dialog.dismiss();//关闭弹出框
                            }
                        });
                        messageDialog.setTitle(myActivity.getString(R.string.text_deletion_record));
                        messageDialog.show();//显示弹出框
                        return true;
                    }
                });
            }else {
                holder.tvTypeName.setText("");//设置类型名称
                holder.tvVehicle.setText("");//设置车型
                holder.tvEcu.setText("");//设置ECU
                holder.tvSupplier.setText("");//设置供应商
                holder.tvLevel.setText("");//设置供应商
                holder.ivRecord.setImageResource(R.drawable.ic_security_record);//设置图片
                holder.llLevel.setVisibility(View.GONE);//隐藏
                holder.itemView.setOnLongClickListener(null);//去掉长按事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(myActivity,R.string.text_no_data,Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     * 添加数据
     * @param listAdd
     */
    public void addItem(List<SecurityRecordVo> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            Collections.reverse(listAdd);//倒序 获取最新数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivRecord;//车辆图片
        private LinearLayout llSecurity;//信息
        private TextView tvTypeName;//类型
        private TextView tvVehicle;//车型
        private TextView tvEcu;//ECU
        private TextView tvSupplier;//供应商
        private TextView tvLevel;//等级
        private LinearLayout llLevel;//等级
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRecord=itemView.findViewById(R.id.iv_security_record);
            llSecurity=itemView.findViewById(R.id.ll_security);
            tvTypeName=itemView.findViewById(R.id.tv_security_type);
            tvVehicle=itemView.findViewById(R.id.tv_security_vehicle);
            tvEcu=itemView.findViewById(R.id.tv_security_ecu);
            tvSupplier=itemView.findViewById(R.id.tv_security_supplier);
            tvLevel=itemView.findViewById(R.id.tv_security_level);
            llLevel=itemView.findViewById(R.id.ll_security_level);
        }
    }
}
