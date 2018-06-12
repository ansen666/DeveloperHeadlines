package com.ansen.developerheadlines.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.fragment.GiftFragment;
import com.ansen.developerheadlines.fragment.MainFragment;
import com.ansen.developerheadlines.fragment.ShareFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    private RelativeLayout rlHome, rlGift, rlShare;

    private int currentSelectItem = R.id.rl_home;//默认首页

    private MainFragment mainFragment;
    private ShareFragment shareFragment;
    private GiftFragment giftFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWindowStatus();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

//		findViewById(R.id.iv_menu).setOnClickListener(clickListener);

        initLeftMenu();//初始化左边菜单

        mainFragment = new MainFragment(drawerListener);
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, mainFragment).commit();
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
                currentSelectItem = v.getId();
                noItemSelect();
                changeFragment(v.getId());//设置fragment显示切换
                switch (v.getId()) {
                    case R.id.rl_home:
                        rlHome.setSelected(true);
                        break;
                    case R.id.rl_gift:
                        rlGift.setSelected(true);
                        break;
                    case R.id.rl_share:
                        rlShare.setSelected(true);
                        break;
                }
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        }
    };

    /**
     * 改变fragment的显示
     *
     * @param resId
     */
    private void changeFragment(int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//开启一个Fragment事务

        hideFragments(transaction);//隐藏所有fragment
        if (resId == R.id.rl_home) {//主页
            if (mainFragment == null) {//如果为空先添加进来.不为空直接显示
                mainFragment = new MainFragment(drawerListener);
                transaction.add(R.id.content_frame, mainFragment);
            } else {
                transaction.show(mainFragment);
            }
        } else if (resId == R.id.rl_share) {
            if (shareFragment == null) {
                shareFragment = new ShareFragment();
                transaction.add(R.id.content_frame, shareFragment);
            } else {
                transaction.show(shareFragment);
            }
        } else if (resId == R.id.rl_gift) {
            if (giftFragment == null) {
                giftFragment = new GiftFragment();
                transaction.add(R.id.content_frame, giftFragment);
            } else {
                transaction.show(giftFragment);
            }
        }
        transaction.commitAllowingStateLoss();//一定要记得提交事务
    }

    /**
     * 显示之前隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mainFragment != null)//不为空才隐藏,如果不判断第一次会有空指针异常
            transaction.hide(mainFragment);
        if (shareFragment != null)
            transaction.hide(shareFragment);
        if (giftFragment != null)
            transaction.hide(giftFragment);
    }

    private void noItemSelect() {
        rlHome.setSelected(false);
        rlGift.setSelected(false);
        rlShare.setSelected(false);
    }

//	private OnClickListener clickListener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.iv_menu:// 打开左边抽屉
//				mDrawerLayout.openDrawer(Gravity.LEFT);
//				break;
//			}
//		}
//	};

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

    private MainDrawerListener drawerListener=new MainDrawerListener() {
        @Override
        public void open() {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    };

    public interface MainDrawerListener{
        void open();//打开Drawer
    }
}
