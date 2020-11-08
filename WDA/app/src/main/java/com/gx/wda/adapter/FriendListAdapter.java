package com.gx.wda.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gx.wda.R;
import com.gx.wda.bean.UserVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 好友列表适配器
 */
public class FriendListAdapter  extends RecyclerView.Adapter<FriendListAdapter.ViewHolder>{
    private Activity activity;
    private List<UserVo> list;
    private TextView tvNum;//好友在线数量
    private OnItemClickListener onItemClickListener;
    public FriendListAdapter(Activity activity,TextView tvNum){
        this.activity=activity;
        this.tvNum=tvNum;
        this.list=new ArrayList<>();

    }

    /**
     * 刷新好友在线数量
     */
    public void refreshFriendNum(){
        tvNum.setText(String.format(Locale.getDefault(),"%d/%d",findOnLine(),list.size()));
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_rv_friend_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserVo userVo=list.get(position);
        if (userVo!=null){
            holder.tvName.setText(userVo.getUserName());
            holder.tvRoleName.setText(String.format(Locale.getDefault()," (%s)",userVo.getRoleName()));
            if (userVo.getOnline()){
                holder.tvState.setText("在线");
                holder.ivState.setBackgroundResource(R.drawable.ic_online);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                holder.tvRoleName.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                holder.tvState.setTextColor(activity.getResources().getColor(R.color.colorBlack));
            }else {
                holder.tvState.setText("离线");
                holder.ivState.setBackgroundResource(R.drawable.ic_off_line);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                holder.tvRoleName.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                holder.tvState.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
            }
            //=======列表每一项点击 item点击事件
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断是否设置点击事件
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClick(userVo,position);
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
     *
     * @param listAdd  要添加的数据
     */
    public void addItem(List<UserVo> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
        refreshFriendNum();//刷新好友在线数量
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvRoleName;
        private ImageView ivState;
        private TextView tvState;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_friend_name);
            tvRoleName=itemView.findViewById(R.id.tv_role_name);
            ivState=itemView.findViewById(R.id.iv_friend_state);
            tvState=itemView.findViewById(R.id.tv_friend_state);
        }
    }
    /**
     * 查询在线好友数
     * @return
     */
    private int findOnLine(){
        int count=0;
        for (UserVo userVo:list){
            if (userVo.getOnline()){
                count++;
            }
        }
        return count;
    }
    /**
     * 点击事件
     */
    public interface OnItemClickListener{
        void onItemClick(UserVo data, Integer position);
    }
}
