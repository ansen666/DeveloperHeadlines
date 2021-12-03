package com.ansen.developerheadlines.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * ViewPager适配器
 * @author ansen
 * @create time 2016-04-18
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
	private final List<Fragment> fragmentList = new ArrayList<>();
	private final List<String> fragmentTitleList = new ArrayList<String>();

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragmentTitleList.get(position);
	}

	public void addFragment(Fragment fragment, String title) {
		fragmentList.add(fragment);
		fragmentTitleList.add(title);
	}
}