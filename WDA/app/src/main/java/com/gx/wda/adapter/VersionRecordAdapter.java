package com.gx.wda.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gx.wda.R;
import com.gx.wda.bean.Version;
import com.gx.wda.ui.main.IntroductionDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VersionRecordAdapter extends RecyclerView.Adapter<VersionRecordAdapter.ViewHolder> {
    private Activity activity;
    private List<Version> list;
    public VersionRecordAdapter(Activity activity){
        this.activity=activity;
        this.list=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_rv_version_record_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Version version=list.get(position);
        if (version!=null){
            holder.tvTitle.setText(String.format(Locale.getDefault(),activity.getString(R.string.app_name)+" Version "+version.getVersionTitle()));
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            holder.tvDate.setText(sf.format(version.getCreationDate()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(activity, IntroductionDetailActivity.class);
                    intent.putExtra("content",version.getVersionContent());
                    activity.startActivity(intent);
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
    public void addItem(List<Version> listAdd) {
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
        TextView tvTitle;
        TextView tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_version_title);
            tvDate=itemView.findViewById(R.id.tv_version_date);
        }

    }
}
