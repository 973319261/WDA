package com.gx.wda.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gx.wda.R;
import com.gx.wda.bean.InformVo;
import com.gx.wda.ui.main.InformDetailActivity;
import com.gx.wda.ui.share.NoticeDetailActivity;
import com.gx.wda.widget.BadgeHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InformAdapter extends RecyclerView.Adapter<InformAdapter.ViewHolder> {
    private Activity activity;
    private List<InformVo> list;

    public InformAdapter(Activity activity){
        this.activity=activity;
        this.list=new ArrayList<>();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_rv_inform_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InformVo inform=list.get(position);
        if (inform!=null){
            if (inform.getTypeId()==0){//通知
                holder.ivLogo.setBackgroundResource(R.drawable.ic_message);
            }else {
                holder.ivLogo.setBackgroundResource(R.drawable.ic_notice);
            }
            //创建红点提示
            BadgeHelper badgeHelper = new BadgeHelper(activity);
            badgeHelper.setBadgeType(BadgeHelper.Type.TYPE_POINT)
                    .setBadgeOverlap(true);
            badgeHelper.bindToTargetView(holder.tvPoint);
            if (inform.getIsRead()){//公告
                badgeHelper.setBadgeEnable(false);//关闭红点提示
            }else {
                badgeHelper.setBadgeEnable(true);//开启红点提示
            }
            holder.tvTitle.setText(inform.getInformTitle());
            holder.tvContent.setText(inform.getInformContent());
            //时间处理
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            holder.tvDate.setText(format.format(inform.getCreationTime()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(inform.getTypeId()==0){//通知
                        Intent intent = new Intent(activity, InformDetailActivity.class);
                        intent.putExtra("informId",inform.getInformId());
                        activity.startActivity(intent);
                    }else {//公告
                        Intent intent = new Intent(activity, NoticeDetailActivity.class);
                        intent.putExtra("noticeId",inform.getInformId());
                        activity.startActivity(intent);
                    }
                    badgeHelper.bindToTargetView(holder.tvPoint);
                    badgeHelper.setBadgeEnable(false);//关闭红点提示
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     *
     * @param listAdd  要添加的数据
     */
    public void addItem(List<InformVo> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLogo;
        private TextView tvPoint;
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo=itemView.findViewById(R.id.iv_inform_logo);
            tvPoint=itemView.findViewById(R.id.tv_red_point);
            tvTitle=itemView.findViewById(R.id.iv_inform_title);
            tvContent=itemView.findViewById(R.id.iv_inform_content);
            tvDate=itemView.findViewById(R.id.iv_inform_date);
        }
    }


}
