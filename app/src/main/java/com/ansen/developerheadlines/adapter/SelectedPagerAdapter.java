package com.ansen.developerheadlines.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.iview.ICarousePagerSelectView;

/**
 * 轮播动画 ViewPager适配器
 * @author ansen
 * @create time 2016-04-19
 */
public class SelectedPagerAdapter extends PagerAdapter implements OnClickListener{
	private List<View> views;
	//每页显示的图片
	private int[] images=new int[]{R.mipmap.icon_selected_carousel_one,R.mipmap.icon_selected_carousel_two,R.mipmap.icon_selected_carousel_three};
	private ICarousePagerSelectView carousePagerSelectView;
	
	public SelectedPagerAdapter(Context context,ICarousePagerSelectView carousePagerSelectView){
		this.carousePagerSelectView=carousePagerSelectView;
		
		views=new ArrayList<View>();
		
		for(int i=0;i<images.length;i++){//初始化每页显示的View
			View item=LayoutInflater.from(context).inflate(R.layout.fragment_selected_pager_item, null);
			ImageView imageView=(ImageView) item.findViewById(R.id.imageview);
			imageView.setImageResource(images[i]);
			imageView.setTag(i);
			imageView.setOnClickListener(this);
			views.add(imageView);
		}
	}
	
	public List<View> getViews() {
		return views;
	}
	
	@Override
	public int getCount() {
		return views == null ? 0 : views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object){
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		return views.get(position);
	}

	@Override
	public void onClick(View v){
		int index=(Integer) v.getTag();
		carousePagerSelectView.carouseSelect(index);
	}
}
