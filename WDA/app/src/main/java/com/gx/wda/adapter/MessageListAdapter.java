package com.gx.wda.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gx.wda.R;
import com.gx.wda.bean.MessageVo;
import com.gx.wda.bean.UserVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 报文列表适配器
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{
    private Activity activity;
    private List<MessageVo> list;
    private List<MessageVo> listScreen;
    private boolean isTimeType=false;

    public MessageListAdapter(Activity activity){
        this.activity=activity;
        this.list=new ArrayList<>();
        this.listScreen=new ArrayList<>();
    }

    public void isTimeType(){
        isTimeType = !isTimeType;
      /*  if (isTimeType){
            isTimeType=false;
        }else {
            isTimeType=true;
        }*/

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_rv_message_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageVo msgVo=list.get(position);
        if (msgVo!=null){
            holder.tvCount.setText(String.valueOf(list.size()));
            holder.tvCanId.setText(msgVo.getCanId());
            if (isTimeType){
                holder.tvTimer.setVisibility(View.GONE);
                holder.tvLocalTimer.setVisibility(View.VISIBLE);
            }else {
                holder.tvLocalTimer.setVisibility(View.GONE);
                holder.tvTimer.setVisibility(View.VISIBLE);
            }
            holder.tvTimer.setText(msgVo.getTimer());
            holder.tvLocalTimer.setText(msgVo.getLocalTime());
            holder.tvDlc.setText(msgVo.getDlc());
            holder.tvDataField.setText(msgVo.getDataField());
            holder.tvAilse.setText(msgVo.getAisle());
        }
    }

  /*  @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position,@NonNull List<Object> payloads) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
            return;
        }

        MessageVo msgVo=list.get(position);
        if (msgVo!=null){
            holder.tvCount.setText(String.valueOf(list.size()));
            holder.tvCanId.setText(msgVo.getCanId());
            holder.tvTimer.setText(msgVo.getTimer());
            holder.tvDlc.setText(msgVo.getDlc());
            holder.tvDataField.setText(msgVo.getDataField());
            holder.tvAilse.setText(msgVo.getAisle());
        }
    }
*/
    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     *
     * @param listAdd  要添加的数据
     */
    public void addItem(List<MessageVo> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
            this.listScreen.clear();
            this.listScreen.addAll(listAdd);
        }

        notifyDataSetChanged();

        /*activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //通知RecyclerView进行改变--整体
                notifyDataSetChanged();
            }
        });*/
    }

    /**
     * 变更一条
     */
    public void addNewItem(MessageVo listAdd){
        if (listAdd!=null){
            //添加数据
            this.list.add(listAdd);
            this.listScreen.add(listAdd);
        }

        //notifyItemChanged(listScreen.size(),1);
        notifyItemInserted(listScreen.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCount;
        private final TextView tvCanId;
        private final TextView tvTimer;
        private final TextView tvLocalTimer;
        private final TextView tvDlc;
        private final TextView tvDataField;
        private final TextView tvAilse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCount = itemView.findViewById(R.id.tv_message_count);
            tvCanId = itemView.findViewById(R.id.tv_message_canid);
            tvTimer = itemView.findViewById(R.id.tv_message_timer);
            tvLocalTimer = itemView.findViewById(R.id.tv_message_localtimer);
            tvDlc = itemView.findViewById(R.id.tv_message_dlc);
            tvDataField = itemView.findViewById(R.id.tv_message_datafield);
            tvAilse = itemView.findViewById(R.id.tv_message_aisle);
        }
    }


}
