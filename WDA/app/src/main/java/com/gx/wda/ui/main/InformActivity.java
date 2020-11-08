package com.gx.wda.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.util.StatusBarUtil;
import com.gx.wda.widget.BadgeHelper;
import com.gx.wda.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知页面
 */
public class InformActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private String[] title=null;//标题
    private Integer[] state={0,1};//状态
    private TabLayout tabLayout;//标签
    private ViewPager2 viewPager;//内容
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        myActivity=this;
        setContentView(R.layout.activity_inform);
        myApplication= (MyApplication) myActivity.getApplication();
        tabLayout = findViewById(R.id.tl_inform_title);
        viewPager = findViewById(R.id.vp_inform_container);
        title= new String[]{getResources().getString(R.string.text_inform_inform), getResources().getString(R.string.text_inform_notice)};//标题
        MyFragmentStateAdapter myFragmentStateAdapter=new MyFragmentStateAdapter((FragmentActivity) myActivity);
        viewPager.setAdapter(myFragmentStateAdapter);//设置适配器
        //tabLayout与viewPager的关联
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(title[position]);
            }
        }).attach();
        int currentItem=getIntent().getIntExtra("currentItem",0);//当前页
        viewPager.setCurrentItem(currentItem);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData(getResources().getString(R.string.text_inform_title), R.drawable.ic_back, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });

        initView();//初始化页面
    }
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
    }
    class MyFragmentStateAdapter extends FragmentStateAdapter {
        //存放Fragment
        Fragment[]fragments;

        public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity){
            super(fragmentActivity);
            fragments=new Fragment[title.length];
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (fragments[position]==null){
                Bundle bundle=new Bundle();
                bundle.putInt("state",state[position]);
                InformListFragment fragment=new InformListFragment();//创建Fragment
                fragment.setArguments(bundle);//设置参数
                fragments[position]=fragment;
            }
            return fragments[position];
        }

        @Override
        public int getItemCount() {
            return fragments.length;
        }
    }


}
