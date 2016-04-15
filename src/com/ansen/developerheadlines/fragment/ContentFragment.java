package com.ansen.developerheadlines.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ansen.developerheadlines.R;

/**
 * 显示内容的Fragment
 * @author Ansen
 * @create time 2016-04-15
 */
public class ContentFragment  extends Fragment{
	private TextView textView;
    
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.fragment_content, container, false);  
        textView = (TextView) view.findViewById(R.id.textView);  
          
        String text = getArguments().getString("text");  
        textView.setText(text);  
          
        return view;  
    }  
}
