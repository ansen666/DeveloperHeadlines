package com.ansen.developerheadlines.activity;

import android.content.Context;
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

/**
 * 第一次启动页面
 * @author Ansen
 * @create time 2016-04-15
 */
public class LauncherActivity extends FragmentActivity{
	private ViewPager viewPager;
	private LauncherPagerAdapter adapter;

	private ImageView[] tips;
	private TextView tvStartHeadlines;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_luancher);

		if(!isFirst()){//不是第一次启动 直接进入首页
			gotoMain();
		}

		tvStartHeadlines= (TextView) findViewById(R.id.tv_start_headlines);
		tvStartHeadlines.setOnClickListener(onClickListener);

		viewPager = (ViewPager) findViewById(R.id.viewpager_launcher);
		viewPager.setOffscreenPageLimit(2);//设置缓存页数
		viewPager.setAdapter(adapter = new LauncherPagerAdapter(this));//设置适配器
		viewPager.setOnPageChangeListener(changeListener);//页面监听

		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);//初始化底部显示控件
		tips = new ImageView[4];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			if (i == 0) {
				imageView.setBackgroundResource(R.mipmap.page_indicator_focused);
			} else {
				imageView.setBackgroundResource(R.mipmap.page_indicator_unfocused);
			}
			tips[i] = imageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
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
			setImageBackground(index);//改变点点点的切换效果

			if (index == tips.length - 1) {//最后一个
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

	private View.OnClickListener onClickListener=new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()){
				case R.id.tv_start_headlines:
					gotoMain();//进入首页
					break;
			}
		}
	};

	public void gotoMain() {
		setFirst();//设置成第二次启动

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 是否第一次启动 true:第一次启动 false:第二次启动
	 * @return
	 */
	private boolean isFirst() {
		SharedPreferences setting = getSharedPreferences("headlines",Context.MODE_PRIVATE);
		return setting.getBoolean("FIRST",true);
	}

	/**
	 * 把其一次启动的值设置成false
	 */
	public void setFirst(){
		SharedPreferences setting = getSharedPreferences("headlines", Context.MODE_PRIVATE);
		setting.edit().putBoolean("FIRST",false).commit();
	}
}
