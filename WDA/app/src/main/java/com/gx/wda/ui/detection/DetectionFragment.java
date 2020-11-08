package com.gx.wda.ui.detection;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gx.wda.MyApplication;
import com.gx.wda.R;
import com.gx.wda.bean.Jurisdiction;

import java.util.ArrayList;
import java.util.List;

/**
 * 检测页面
 */
public class DetectionFragment extends Fragment {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private TabLayout tlTitle;
    private int[] stateId = {0, 1, 2};
    private List<String> title =new ArrayList<>();//状态标题
    private List<Jurisdiction> jurisdictionList;//权限数据
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
        myApplication= (MyApplication) myActivity.getApplication();
        jurisdictionList = myApplication.getJurisdictionList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detection,container,false);

        //获取控件
        tlTitle = view.findViewById(R.id.tl_detection_title);
        ViewPager2 vpContainer = view.findViewById(R.id.vp_detection_container);
        initView();
        //=========TableLayout+ViewPager2+Fragemnt初始化=============
        //=实例化适配器
        MyFragmentStateAdapter myFragmentStateAdapter = new MyFragmentStateAdapter(getActivity());

        //给ViewPager2设置适配器
        vpContainer.setAdapter(myFragmentStateAdapter);
        //设置ViewPager2的预加载数，传入大于1的值来设置预加载数量,默认不预加载
        vpContainer.setOffscreenPageLimit(1);
        new TabLayoutMediator(tlTitle, vpContainer, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //设置TabLayout的显示
                // tab:当前处于选中状态的Tab对象
                // position:当前Tab所处的位置
                tab.setText(title.get(position));
            }
        }).attach(); // 不要忘记，否则没效果
        return view;
    }
    /**
     * 初始化页面
     */
    private void initView() {
        if (jurisdictionList.get(5).getIsEnable()) {//DID解析
            title.add(getResources().getString(R.string.text_datastream_title_did_analysis));
        }
        if (jurisdictionList.get(7).getIsEnable()) {//ECU版本
            title.add(getResources().getString(R.string.text_detection_ecu_version));
        }
        if (jurisdictionList.get(8).getIsEnable()) {//节点在线
            title.add(getResources().getString(R.string.text_detection_node_online));
        }
        if (title.size()==1){//当为一个页面时不显示标题状态
            tlTitle.setVisibility(View.GONE);
        }
    }

    class MyFragmentStateAdapter extends FragmentStateAdapter {
        //存放Fragment
        Fragment[] fragments;

        public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            fragments = new Fragment[title.size()];
        }

        //创建Fragment
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (fragments[position] == null) {
                //创建传递参数的Bundle
                Bundle bundle = new Bundle();
                bundle.putInt("stateId", stateId[position]);
                //创建Fragment
                DetectionStateFragment orderListFragment = new DetectionStateFragment();
                //设置参数
                orderListFragment.setArguments(bundle);
                fragments[position] = orderListFragment;
            }
            return fragments[position];
        }

        //获取Fragment的数量
        @Override
        public int getItemCount() {
            return fragments.length;
        }
    }
}
