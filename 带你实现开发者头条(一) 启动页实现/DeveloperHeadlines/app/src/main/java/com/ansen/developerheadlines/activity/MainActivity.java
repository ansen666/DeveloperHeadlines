package com.ansen.developerheadlines.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ansen.developerheadlines.R;

public class MainActivity  extends AppCompatActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;  
	private ArrayList<String> menuLists;  
	private ArrayAdapter<String> adapter;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mDrawerList = findViewById(R.id.left_drawer);
        menuLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) 
            menuLists.add("菜单0" + i);  
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,menuLists);
        mDrawerList.setAdapter(adapter);

//        mDrawerList.setOnItemClickListener(this);
	}
}
