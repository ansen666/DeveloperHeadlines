package com.ansen.developerheadlines.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.adapter.FragmentAdapter;

public class MainFragment extends Fragment {
    private int screenWidth, screenHeight;

    private List<Fragment> list = new ArrayList<Fragment>();
    private ViewPager vPager;
    private FragmentAdapter adapter;
    private TextView tvSelected, tvSubscribe, tvFind;
    private View viewIndicator;

    private int currentIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getScreenSize(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_main, null);
        /**
         * 初始化三个Fragment  并且填充到ViewPager
         */
        vPager = rootView.findViewById(R.id.viewpager_home);

        SelectedFragment selectedFragment = new SelectedFragment();
        SubscribeFragment subscribeFragment = new SubscribeFragment();
        FindFragment findFragment = new FindFragment();

        list.add(selectedFragment);
        list.add(subscribeFragment);
        list.add(findFragment);

        adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), list);
        vPager.setAdapter(adapter);
        vPager.setOffscreenPageLimit(2);
        vPager.setCurrentItem(0);
        vPager.setOnPageChangeListener(pageChangeListener);

        tvSelected = (TextView) rootView.findViewById(R.id.tv_selected);
        tvSubscribe = (TextView) rootView.findViewById(R.id.tv_subscribe);
        tvFind = (TextView) rootView.findViewById(R.id.tv_find);

        tvSelected.setOnClickListener(clickListener);
        tvSubscribe.setOnClickListener(clickListener);
        tvFind.setOnClickListener(clickListener);

        tvSelected.setSelected(true);

        viewIndicator = rootView.findViewById(R.id.view_indicator);

        initCursorPosition();

        return rootView;
    }

    private int currentSelectId;
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentSelectId != v.getId()) {//防止重复点击
                switch (v.getId()) {
                    case R.id.tv_selected:
                        vPager.setCurrentItem(0);
                        break;
                    case R.id.tv_subscribe:
                        vPager.setCurrentItem(1);
                        break;
                    case R.id.tv_find:
                        vPager.setCurrentItem(2);
                        break;
                }

                currentSelectId = v.getId();
            }

        }
    };

    private void initCursorPosition() {
        LayoutParams layoutParams = viewIndicator.getLayoutParams();
        layoutParams.width = screenWidth / 3;
        viewIndicator.setLayoutParams(layoutParams);

        TranslateAnimation animation = new TranslateAnimation(-screenWidth / 3, 0, 0, 0);
        animation.setFillAfter(true);
        viewIndicator.startAnimation(animation);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            translateAnimation(index);//移动指示器
            changeTextColor(index);//改变文字颜色
            currentIndex = index;//设置当前选中
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     * 改变标题栏字体颜色
     *
     * @param index
     */
    private void changeTextColor(int index) {
        tvSelected.setSelected(false);
        tvSubscribe.setSelected(false);
        tvFind.setSelected(false);

        switch (index) {
            case 0:
                tvSelected.setSelected(true);
                break;
            case 1:
                tvSubscribe.setSelected(true);
                break;
            case 2:
                tvFind.setSelected(true);
                break;
        }
    }

    /**
     * 移动标题栏点点点...
     *
     * @param index
     */
    private void translateAnimation(int index) {
        TranslateAnimation animation = null;
        switch (index) {
            case 0://订阅->精选
                animation = new TranslateAnimation((screenWidth / 3), 0, 0, 0);
                break;
            case 1://
                if (0 == currentIndex) {//精选->订阅
                    animation = new TranslateAnimation(0, screenWidth / 3, 0, 0);
                } else if (2 == currentIndex) {//发现->订阅
                    animation = new TranslateAnimation((screenWidth / 3) * 2, screenWidth / 3, 0, 0);
                }
                break;
            case 2://订阅－》发现
                animation = new TranslateAnimation(screenWidth / 3, (screenWidth / 3) * 2, 0, 0);
                break;
        }
        animation.setFillAfter(true);
        animation.setDuration(300);
        viewIndicator.startAnimation(animation);
    }

    // 获取屏幕宽高
    private void getScreenSize(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }
}
