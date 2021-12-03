package com.ansen.developerheadlines.fragment;

import com.ansen.developerheadlines.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ShareFragment extends Fragment {
	private TextView tvContent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View rootView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_share, null);
		tvContent=(TextView) rootView.findViewById(R.id.tv_content);
		return rootView;
	}
}
