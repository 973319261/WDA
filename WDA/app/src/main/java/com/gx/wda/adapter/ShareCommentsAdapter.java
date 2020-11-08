package com.gx.wda.adapter;

import android.app.Dialog;
import android.content.res.Resources;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.CommentsVo;
import com.gx.wda.bean.SecurityRecordVo;
import com.gx.wda.dialog.InputDialog;
import com.gx.wda.dialog.MessageDialog;
import com.gx.wda.util.OkHttpTool;
import com.gx.wda.util.SPUtils;
import com.gx.wda.util.SecurityRecordUtil;
import com.gx.wda.util.ServiceUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分享列表PDF在线预览
 */
public class ShareCommentsAdapter extends RecyclerView.Adapter<ShareCommentsAdapter.ViewHolder>{
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private AppCompatActivity myActivity;
    private MyApplication myApplication;//全局Application对象
    private InputDialog inputDialog1;
    private List<CommentsVo> mListData;
    private Integer userid;
    private OnSendClickListener onSendClickListener;
    private OnLongClick onLongClick;

    public ShareCommentsAdapter(List<CommentsVo> mListData, AppCompatActivity activity, MyApplication myApplication,OnSendClickListener onSendClickListener){
        this.myActivity = activity;
        this.mListData=mListData;
        this.myApplication = myApplication;
        this.onSendClickListener=onSendClickListener;
    }

    //设置点击事件方法

    public void setLongClickListener(OnLongClick onLongClick){
        this.onLongClick=onLongClick;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(myActivity).inflate(R.layout.item_rv_share_comment_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        userid = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取本地用户数据
        final CommentsVo dbComm=mListData.get(position);
        if (dbComm!=null){

            SimpleDateFormat SHOT_DATE = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat SHOW_TIME = new SimpleDateFormat("HH:mm:ss");
            String date =SHOT_DATE.format(dbComm.getCreationTime());
            String time =SHOW_TIME.format(dbComm.getCreationTime());
            Calendar dateNow=Calendar.getInstance();
            String[] datas = date.split("-");
            String dateTime = date;
            if(Integer.valueOf(datas[0])<=dateNow.get(Calendar.YEAR)){
                if(Integer.valueOf(datas[1])<=dateNow.get(Calendar.MONTH+1)){
                    if(Integer.valueOf(datas[2])==dateNow.get(Calendar.DATE)){
                        dateTime=time;
                    }
                }
            }
            if(dbComm.getCriticId()!=0){
                holder.tvCritic.setText("@"+dbComm.getCirticName());
                holder.tvCritic.setVisibility(View.VISIBLE);
            }
            holder.tvDate.setText(dateTime);//设置发布时间
            holder.tvName.setText(dbComm.getUserName());//设置评论人
            holder.tvCon.setText(dbComm.getContent());//设置评论内容


            if (onSendClickListener!=null){


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputDialog1 = new InputDialog("@"+holder.tvName.getText().toString(), "", InputType.TYPE_CLASS_TEXT, 140, new InputDialog.OnConfirmClickListener() {
                            @Override
                            public void onConfirmClick(Dialog dialog, String content) {
                                onSendClickListener.onSendClick(dialog,dbComm,content,inputDialog1);

                            }
                        });
                        inputDialog1.show(myActivity.getSupportFragmentManager(),"");
                    }
                });
            }

            if(onLongClick!=null){
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onLongClick.onLongClick(v,dbComm,position);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }


    /**
     * 添加数据
     * @param listAdd
     */
    public void addItem(List<CommentsVo> listAdd,int loadPage) {
        if (loadPage == 1) {
            //如果是加载第一页，需要先清空数据列表
            this.mListData.clear();
            //添加数据
            if (listAdd != null) {
                this.mListData.addAll(listAdd);
            }
            //通知RecyclerView进行改变--整体
            notifyDataSetChanged();
        } else {
            //不是第一页的情况
            //添加数据
            int nowCount = this.mListData.size();
            if (listAdd != null) {
                this.mListData.addAll(listAdd);
            }
            //通知RecyclerView进行改变 -- 局部刷新
            notifyItemRangeChanged(nowCount, mListData.size());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvName;
        private final TextView tvDate;
        private final TextView tvCon;
        private final TextView tvCritic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvName= itemView.findViewById(R.id.tv_it_comment_name);
            tvDate = itemView.findViewById(R.id.tv_it_comment_date);
            tvCon = itemView.findViewById(R.id.tv_it_comment_con);
            tvCritic = itemView.findViewById(R.id.tv_critic_name);

        }
    }
    public interface OnSendClickListener {
        //整条数据的点击事件
        void onSendClick(Dialog dialog, CommentsVo commentsVo, String content, InputDialog inputDialog);

    }

    public interface OnLongClick{
        void onLongClick(View view, CommentsVo data, int position);
    }
}

