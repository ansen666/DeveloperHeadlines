package com.ansen.developerheadlines.fragment;

import com.ansen.developerheadlines.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ContentFragment extends Fragment {
	private TextView tvContent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View rootView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_content, null);
		tvContent=rootView.findViewById(R.id.tv_content);
		return rootView;
	}
	
	public void setContent(String content){
		tvContent.setText(content);
	}
}
