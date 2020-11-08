package com.gx.wda.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gx.wda.R;
import com.gx.wda.bean.AppendOptionVo;
import com.gx.wda.util.ImageUtil;
import com.gx.wda.util.KeyBoardUtil;

import java.util.List;

/**
 * 顶部选项
 */
public class TopOptionDialog {
    private Activity activity;//上下文
    private LinearLayout linearLayout;//点击容器
    private ImageView imageView;//图标
    private RadioGroup rgOption;
    private ScrollView svList;//列表数据
    private LinearLayout llEmpty;//空列表
    private PopupWindow popupWindow;//弹窗
    private View view;
    public TopOptionDialog(final Activity activity, String title, final LinearLayout linearLayout , final ImageView imageView){
        this.activity=activity;
        this.linearLayout=linearLayout;
        this.imageView=imageView;
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_option_top, null);
        TextView tvTitle=view.findViewById(R.id.tv_title);//标题
        rgOption = view.findViewById(R.id.rg_option);//RadioGroup
        svList=view.findViewById(R.id.sv_list);//列表数据
        llEmpty = view.findViewById(R.id.ll_empty);//空列表
        LinearLayout llClose=view.findViewById(R.id.ll_close);//关闭
        tvTitle.setText(title);//设置标题
        // 创建PopupWindow对象，指定宽度和高度
        popupWindow = new PopupWindow(view);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();//隐藏
            }
        });
    }

    /**
     * 设置数据和外部点击事件
     * @param list
     * @param onItemClickListener
     */
    public void setData(List<AppendOptionVo> list, final OnItemClickListener onItemClickListener){
        int rgOptionId = rgOption.getCheckedRadioButtonId();//获取原来选择的数据Id
        rgOption.removeAllViews();//清空原来的数据
        if (list!=null && list.size()>0){
            svList.setVisibility(View.VISIBLE);//显示
            llEmpty.setVisibility(View.GONE);//隐藏
            for (final AppendOptionVo appendOptionVo:list){
                final RadioButton button=new RadioButton(activity);
                RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                button.setId(appendOptionVo.getId());//设置ID
                button.setText(appendOptionVo.getName());//设置名称
                button.setButtonDrawable(null);
                button.setBackgroundResource(R.drawable.selector_background_language);//设置选中样式
                button.setTextSize(16);//设置字体大小
                button.setPadding(50,50,50,30);//内边距
                if (appendOptionVo.getId() == rgOptionId) {//判断是否是原来的数据
                    button.setChecked(true);//勾选原来的数据
                }
                rgOption.addView(button,lp);//添加到rg视图
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClick(popupWindow,appendOptionVo);
                        }
                    }
                });
            }
        }else {
            llEmpty.setVisibility(View.VISIBLE);//显示
            svList.setVisibility(View.GONE);//隐藏
        }

    }

    /**
     * 清空选项
     */
    public void cleanOption(){
        rgOption.clearCheck();
    }
    /**
     * 显示
     * @return
     */
    public void show(){
        KeyBoardUtil.hideKeyboard(view);//关闭虚拟键盘
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //popupWindow.setAnimationStyle(R.style.dialog_top_bottom);
        // 设置可以获取焦点
        popupWindow.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(false);
        //关闭时调用事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                linearLayout.setEnabled(true);//重新开启按钮
                if (imageView!=null){
                    imageView.setImageBitmap(ImageUtil.rotateBitmap(activity,R.drawable.ic_down,0));//设置180度的旋转图片
                }

            }
        });
        // 显示方式，
        popupWindow.showAsDropDown(linearLayout);
    }

    /**
     * 隐藏
     */
    public void hide(){
        linearLayout.setEnabled(true);//重新开启按钮
        if (imageView!=null){
            imageView.setImageBitmap(ImageUtil.rotateBitmap(activity,R.drawable.ic_down,0));//设置180度的旋转图片
        }
        popupWindow.dismiss();
    }
    //外部接口
    public interface OnItemClickListener{
        void onItemClick(PopupWindow popupWindow, AppendOptionVo appendOptionVo);
    }
}
