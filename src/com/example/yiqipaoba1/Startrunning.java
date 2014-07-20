package com.example.yiqipaoba1;



import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.SupportMapFragment;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class Startrunning extends ActionBarActivity {
	SupportMapFragment map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_startrunning);
    	setTitle("¿ªÊ¼ÅÜ²½");
		/*
    	MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(15).build();
		BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms)
				.compassEnabled(false).zoomControlsEnabled(false);
		map = SupportMapFragment.newInstance(bo);
		FragmentManager manager = getSupportFragmentManager();
		//MapController controller = map.getMapView().getController();
		manager.beginTransaction().add(R.id.map, map, "map_fragment").commit();
		*/
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.startrunning, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
