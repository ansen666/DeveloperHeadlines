package com.ansen.developerheadlines.fragment;

import com.ansen.developerheadlines.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 送礼物界面
 * @author ansen	
 * @create time 2016-04-19
 */
public class GiftFragment extends Fragment{
	private TextView tvContent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View rootView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_gift, null);
		tvContent=(TextView) rootView.findViewById(R.id.tv_content);
		return rootView;
	}
}
