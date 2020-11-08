package com.gx.wda.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.NoticeVo;
import com.gx.wda.ui.share.NoticeDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 分享列表PDF在线预览
 */
public class ShareNoticeAdapter extends RecyclerView.Adapter<ShareNoticeAdapter.ViewHolder>{
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Activity myActivity;
    private MyApplication myApplication;//全局Application对象
    private List<NoticeVo> mListData;
    public ShareNoticeAdapter(List<NoticeVo> mListData, Activity activity, MyApplication myApplication){
        this.myActivity = activity;
        this.mListData=mListData;
        this.myApplication = myApplication;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(myActivity).inflate(R.layout.item_rv_share_notice_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final NoticeVo noticeVo=mListData.get(position);
            if (noticeVo!=null){
                int fileType = noticeVo.getFileTypeId();
                int pictureUrl = R.drawable.ic_file;
                switch (fileType){
                    case 1:
                        pictureUrl = R.drawable.ic_video;
                        break;
                    case 2:
                        pictureUrl = R.drawable.ic_pdf;
                        break;
                    case 3:
                        pictureUrl = R.drawable.ic_word;
                        break;
                    case 4:
                        pictureUrl = R.drawable.ic_excel;
                        break;
                    case 5:
                        pictureUrl = R.drawable.ic_ppt;
                        break;
                }
                //使用Glide加载图片
                Glide.with(myActivity)
                        .load(pictureUrl)
                        .error(R.drawable.ic_file)
                        .into(holder.ivCover);
                //时间处理
                SimpleDateFormat SHOT_DATE = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat SHOW_TIME = new SimpleDateFormat("HH:mm:ss");
                String date =SHOT_DATE.format(noticeVo.getReleaseTime());
                String time =SHOW_TIME.format(noticeVo.getReleaseTime());
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
                holder.tvPostDate.setText(dateTime);//设置发布时间

                holder.tvTheme.setText(noticeVo.getNoticeName());//设置主题
                holder.tvAuthor.setText(noticeVo.getUserName());//设置作者


                holder.llContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myActivity, NoticeDetailActivity.class);
                        intent.putExtra("noticeId",noticeVo.getNoticeId());
                        myActivity.startActivity(intent);
                    }
                });
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
    public void addItem(List<NoticeVo> listAdd,int loadPage) {
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
        private LinearLayout llContent;
        private ImageView ivCover;//封面图片
        private LinearLayout llNotice;//信息
        private TextView tvTheme;//主题
        private TextView tvAuthor;//作者
        private TextView tvPostDate;//发布时间
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llContent = itemView.findViewById(R.id.ll_notice_content);
            llNotice=itemView.findViewById(R.id.ll_notice);
            tvTheme=itemView.findViewById(R.id.tv_notice_theme);
            tvAuthor=itemView.findViewById(R.id.tv_notice_author);
            tvPostDate=itemView.findViewById(R.id.tv_notice_postdate);
            ivCover = itemView.findViewById(R.id.iv_notice_logo);
        }
    }
}

