package com.ansen.developerheadlines.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.adapter.LauncherPagerAdapter;
import com.ansen.developerheadlines.iview.ILauncherView;

/**
 * 第一次启动页面
 * 
 * @author Ansen
 * @create time 2016-04-15
 */
@SuppressLint("ResourceAsColor")
public class LauncherActivity extends FragmentActivity implements ILauncherView {
	private ViewPager viewpagerLauncher;
	private LauncherPagerAdapter adapter;

	private ImageView[] tips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_luancher);
		
		if(!isFirst()){
			gotoMain();
		}
		viewpagerLauncher = (ViewPager) findViewById(R.id.viewpager_launcher);
		adapter = new LauncherPagerAdapter(this, this);
		viewpagerLauncher.setOffscreenPageLimit(2);
		viewpagerLauncher.setCurrentItem(0);
		viewpagerLauncher.setAdapter(adapter);
		viewpagerLauncher.setOnPageChangeListener(changeListener);
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);// 初始化底部显示控件
		tips = new ImageView[4];
		for (int i = 0; i < tips.length; i++){
			ImageView imageView = new ImageView(this);
			if (i == 0) {
				imageView.setBackgroundResource(R.mipmap.page_indicator_focused);
			} else {
				imageView.setBackgroundResource(R.mipmap.page_indicator_unfocused);
			}
			tips[i] = imageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 10;// 设置点点点view的左边距
			layoutParams.rightMargin = 10;// 设置点点点view的右边距
			group.addView(imageView, layoutParams);
		}
	}

	private OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageScrollStateChanged(int arg0) {}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		@Override
		public void onPageSelected(int index) {
			setImageBackground(index);// 改变点点点的切换效果

			TextView tvStartHeadlines = (TextView) adapter.getViews().get(index).findViewById(R.id.tv_start_headlines);
			if (index == tips.length - 1) {// 最后一个
				tvStartHeadlines.setVisibility(View.VISIBLE);
			} else {
				tvStartHeadlines.setVisibility(View.INVISIBLE);
			}
		}
	};

	/**
	 * 改变点点点的切换效果
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.mipmap.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
			}
		}
	}

	@Override
	public void gotoMain() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private boolean isFirst() {
		SharedPreferences setting = getSharedPreferences("headlines", 0);
		Boolean user_first = setting.getBoolean("FIRST", true);
		if (user_first) {// 第一次
			setting.edit().putBoolean("FIRST", false).commit();
			return true;
		} else {
			return false;
		}
	}
}
