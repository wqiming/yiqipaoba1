package com.example.yiqipaoba1;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.SupportMapFragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.os.Build;

public class Startrunning extends ActionBarActivity {
	MapFragment mapFragment;
	StatusFragment statusFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_startrunning);
    	setTitle("¿ªÊ¼ÅÜ²½");
    	
    	FragmentManager manager = getSupportFragmentManager();
        mapFragment=new MapFragment();
    	statusFragment=new StatusFragment();  
    	android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
    	//transaction.add(R.id.startrunning, mapFragment, "map_fragment");
    	transaction.add(R.id.maplayout, mapFragment, "map_fragment");
    	transaction.add(R.id.statuslayout, statusFragment, "status_fragment");
    	transaction.commit();

	}

    @SuppressLint("Recycle") public void changeview(){
    	Log.i("startrunning","changeview");
    	FragmentManager manager = getSupportFragmentManager();
    	android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
    	transaction.remove(mapFragment);
    	transaction.remove(statusFragment);
    	transaction.commit();
    	manager.executePendingTransactions();
    	transaction=manager.beginTransaction();
    	
    	transaction.add(R.id.statuslayout, mapFragment, "map_fragment");
    	transaction.commit();

    }
}
