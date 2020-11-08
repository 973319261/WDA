package com.gx.wda.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gx.wda.R;
import com.gx.wda.bean.FaultCodeVo;
import com.gx.wda.dialog.FaultCodeInfoDialog;

import java.util.ArrayList;
import java.util.List;

public class DtcQueryAdapter extends RecyclerView.Adapter<DtcQueryAdapter.ViewHolder> {
    private Activity activity;
    private List<FaultCodeVo> list;//数据
    private OnItemClickListener onItemClickListener;
    public DtcQueryAdapter(Activity activity){
        this.activity=activity;
        this.list=new ArrayList<>();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_rv_dtc_query_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final FaultCodeVo faultCodeVo=list.get(position);
        if (faultCodeVo!=null){
            holder.tvVehicle.setText(faultCodeVo.getVehicleName());
            holder.tvModule.setText(faultCodeVo.getModuleName());
            holder.tvSupplier.setText(faultCodeVo.getSupplierName());
            holder.tvSae.setText(faultCodeVo.getDtc());
            holder.tvHex.setText(faultCodeVo.getHexDtc());
            holder.tvChinese.setText(faultCodeVo.getChineseDescription());
            //=======列表每一项点击 item点击事件
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断是否设置点击事件
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClick(faultCodeVo,position);
                        }
                    }
                });
            }
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
    public void addItem(List<FaultCodeVo> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvVehicle;
        TextView tvModule;
        TextView tvSupplier;
        TextView tvSae;
        TextView tvHex;
        TextView tvChinese;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicle=itemView.findViewById(R.id.tv_dtc_vehicle);
            tvModule=itemView.findViewById(R.id.tv_dtc_module);
            tvSupplier=itemView.findViewById(R.id.tv_dtc_supplier);
            tvSae=itemView.findViewById(R.id.tv_dtc_sae);
            tvHex=itemView.findViewById(R.id.tv_dtc_hex);
            tvChinese=itemView.findViewById(R.id.tv_dtc_chinese);
        }

    }
    /**
     * 点击事件
     */
    public interface OnItemClickListener{
        void onItemClick(FaultCodeVo data,Integer position);
    }
}
