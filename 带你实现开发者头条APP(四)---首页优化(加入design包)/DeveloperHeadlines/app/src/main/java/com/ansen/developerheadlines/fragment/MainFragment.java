package com.ansen.developerheadlines.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.activity.MainActivity;
import com.ansen.developerheadlines.adapter.FragmentAdapter;

public class MainFragment extends Fragment{
	private ViewPager vPager;

	private MainActivity.MainDrawerListener drawerListener;

	public MainFragment(){}

	public MainFragment(MainActivity.MainDrawerListener drawerListener){
		this.drawerListener=drawerListener;
	}

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, null);

		vPager = (ViewPager) rootView.findViewById(R.id.viewPager);
		vPager.setOffscreenPageLimit(2);//设置缓存页数
		vPager.setCurrentItem(0);

		FragmentAdapter pagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
		SelectedFragment selectedFragment=new SelectedFragment();
		SubscribeFragment subscribeFragment=new SubscribeFragment();
		FindFragment findFragment=new FindFragment();

		pagerAdapter.addFragment(selectedFragment,"精选");
		pagerAdapter.addFragment(subscribeFragment,"订阅");
		pagerAdapter.addFragment(findFragment,"发现");

		vPager.setAdapter(pagerAdapter);

		TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
		tabLayout.setupWithViewPager(vPager);

		Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
		toolbar.inflateMenu(R.menu.ansen_toolbar_menu);
		toolbar.setNavigationIcon(R.mipmap.ic_menu_white);
		toolbar.setTitle("关注公众号[Android开发者666]");
		toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		toolbar.setNavigationOnClickListener(onClickListener);
		return rootView;
    }

	private View.OnClickListener onClickListener=new View.OnClickListener(){
		@Override
		public void onClick(View view) {
			if(drawerListener!=null){
				drawerListener.open();
			}
		}
	};
}
