package com.ansen.developerheadlines.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ansen.developerheadlines.R;

public class MainActivity  extends Activity{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;  
	private ArrayList<String> menuLists;  
	private ArrayAdapter<String> adapter;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);  
        mDrawerList = (ListView) findViewById(R.id.left_drawer);  
        menuLists = new ArrayList<String>();  
        for (int i = 0; i < 5; i++) 
            menuLists.add("菜单0" + i);  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuLists);  
        mDrawerList.setAdapter(adapter);  
//        mDrawerList.setOnItemClickListener(this);
	}
}
