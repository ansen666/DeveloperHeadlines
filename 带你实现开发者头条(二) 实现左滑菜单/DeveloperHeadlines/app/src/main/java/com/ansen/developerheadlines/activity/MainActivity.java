package com.ansen.developerheadlines.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.fragment.ContentFragment;

public class MainActivity extends FragmentActivity{
	private DrawerLayout mDrawerLayout;
	private RelativeLayout rlHome, rlGift, rlShare;
	private int currentSelectItem = R.id.rl_home;//默认首页
	private ContentFragment contentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		findViewById(R.id.iv_menu).setOnClickListener(clickListener);
		
		initLeftMenu();//初始化左边菜单
		
		contentFragment=new ContentFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.content_frame,contentFragment).commit();  

		setWindowStatus();
	}

	private void initLeftMenu() {
		rlHome = (RelativeLayout) findViewById(R.id.rl_home);
		rlGift = (RelativeLayout) findViewById(R.id.rl_gift);
		rlShare = (RelativeLayout) findViewById(R.id.rl_share);
		
		rlHome.setOnClickListener(onLeftMenuClickListener);
		rlGift.setOnClickListener(onLeftMenuClickListener);
		rlShare.setOnClickListener(onLeftMenuClickListener);

		rlHome.setSelected(true);
	}

	private OnClickListener onLeftMenuClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (currentSelectItem != v.getId()) {//防止重复点击
				currentSelectItem=v.getId();
				noItemSelect();
				
				switch (v.getId()) {
				case R.id.rl_home:
					rlHome.setSelected(true);
					contentFragment.setContent("这是首页");
					break;
				case R.id.rl_gift:
					rlGift.setSelected(true);
					contentFragment.setContent("这是礼物兑换");
					break;
				case R.id.rl_share:
					rlShare.setSelected(true);
					contentFragment.setContent("这是我的分享");
					break;
				}
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
		}
	};
	
	private void noItemSelect(){
		rlHome.setSelected(false);
		rlGift.setSelected(false);
		rlShare.setSelected(false);
	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_menu:// 打开左边抽屉
				mDrawerLayout.openDrawer(Gravity.LEFT);
				break;
			}
		}
	};

	// 设置状态栏
	private void setWindowStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			// 设置状态栏颜色
			getWindow().setBackgroundDrawableResource(R.color.main_color);
		}
	}
}
