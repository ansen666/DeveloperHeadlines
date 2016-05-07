package com.ansen.developerheadlines.fragment;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.adapter.SelectedRecyclerAdapter;
import com.ansen.developerheadlines.adapter.SelectedPagerAdapter;
import com.ansen.developerheadlines.iview.ICarousePagerSelectView;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

/**
 * 精选
 * @author ansen
 * @create time 2016-04-19
 */
public class SelectedFragment extends Fragment{
	private ViewPager viewPager;
	private SelectedPagerAdapter selectedPagerAdapter;
	
	private ImageView[] tips;//底部。。。
	
	private Timer timer;
	private final int  CAROUSEL_TIME = 3000;//滚动间隔
	
	private int currentIndex=0;//当前选中
	
	private TextView tvContent;
	private String[] carousePageStr=new String[]{"Android开发666","公众号:Ansen_666","Python 的练手项目有哪些值得推荐"};

	private PtrClassicFrameLayout ptrClassicFrameLayout;
	private RecyclerView mRecyclerView;
	private SelectedRecyclerAdapter selectedAdapter;
	private RecyclerAdapterWithHF mAdapter;
	private int page = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_selected, null);

		ptrClassicFrameLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.test_recycler_view_frame);
		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.test_recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		init();
		return rootView;
	}

	private void init() {
		//初始化适配器
		selectedAdapter = new SelectedRecyclerAdapter(getActivity());
		//对适配器进行封装
		mAdapter = new RecyclerAdapterWithHF(selectedAdapter);
		//把滚动Banner加入头部
		mAdapter.addCarouse(initCarouselHead());

		mRecyclerView.setAdapter(mAdapter);

		ptrClassicFrameLayout.setPtrHandler(ptrDefaultHandler);//设置下拉监听
		ptrClassicFrameLayout.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听

		ptrClassicFrameLayout.setLoadMoreEnable(true);//设置可以加载更多
	}

	private PtrDefaultHandler ptrDefaultHandler=new PtrDefaultHandler() {
		@Override
		public void onRefreshBegin(PtrFrameLayout frame) {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					page = 0;
					selectedAdapter.getFirst();
					mAdapter.notifyDataSetChanged();
					ptrClassicFrameLayout.refreshComplete();
				}
			},700);
		}
	};

	private OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
		@Override
		public void loadMore() {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					page++;
					selectedAdapter.loadMore(page);//模拟数据
					mAdapter.notifyDataSetChanged();
					ptrClassicFrameLayout.loadMoreComplete(true);//下拉加载完毕
					Toast.makeText(getActivity(), "load more complete", Toast.LENGTH_SHORT).show();
				}
			},1000);
		}
	};

	private ICarousePagerSelectView carousePagerSelectView=new ICarousePagerSelectView() {
		@Override
		public void carouseSelect(int index) {
			Toast.makeText(getActivity(), carousePageStr[index], Toast.LENGTH_SHORT).show();
		}
	};
	
	TimerTask task = new TimerTask() {
		public void run() {
			handler.sendEmptyMessage(CAROUSEL_TIME);
		}
	};
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case CAROUSEL_TIME:
				if(currentIndex>=tips.length-1){//已经滚动到最后,从第一页开始
					viewPager.setCurrentItem(0);
				}else{//开始下一页
					viewPager.setCurrentItem(currentIndex+1);
				}
				break;
			}
		};
	};

	//初始化
	private View initCarouselHead(){
		View headView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_selected_header,mRecyclerView,false);

		tvContent=(TextView) headView.findViewById(R.id.tv_content);
		tvContent.setText(carousePageStr[0]);

		viewPager = (ViewPager)headView.findViewById(R.id.viewpager);
		selectedPagerAdapter=new SelectedPagerAdapter(getActivity(),carousePagerSelectView);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setCurrentItem(0);
		viewPager.addOnPageChangeListener(onPageChangeListener);
		viewPager.setAdapter(selectedPagerAdapter);

		ViewGroup group = (ViewGroup) headView.findViewById(R.id.viewGroup);// 初始化底部显示控件
		tips = new ImageView[3];
		for (int i = 0; i < tips.length; i++){
			ImageView imageView = new ImageView(getActivity());
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

		timer = new Timer(true);//初始化计时器
		timer.schedule(task, 0, CAROUSEL_TIME);//延时0ms后执行,3000ms执行一次

		return headView;
	}
	
	@Override
	public void onDestroy(){
		task.cancel();
		System.exit(0);
	}
	
	private OnPageChangeListener onPageChangeListener=new OnPageChangeListener() {
		@Override
		public void onPageSelected(int index) {
			tvContent.setText(carousePageStr[index]);
			setImageBackground(index);// 改变点点点的切换效果
			currentIndex=index;
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2){}
		@Override
		public void onPageScrollStateChanged(int arg0) {}
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

	public void setPullRefresh(boolean pullRefresh) {
		ptrClassicFrameLayout.setPullRefresh(pullRefresh);
	}
}
