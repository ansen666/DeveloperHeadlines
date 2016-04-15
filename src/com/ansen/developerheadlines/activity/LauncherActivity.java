package com.ansen.developerheadlines.activity;

import com.ansen.developerheadlines.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * 第一次启动页面
 * @author Ansen
 * @create time 2016-04-15
 */
public class LauncherActivity extends FragmentActivity{
	
	private ViewPager viewpagerLauncher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_luancher);
		
		viewpagerLauncher=(ViewPager) findViewById(R.id.viewpager_launcher);
		
	}
	
}
