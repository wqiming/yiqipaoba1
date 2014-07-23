package com.example.yiqipaoba1;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MapFragment extends Fragment implements OnClickListener {
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	Button btn;
	Startrunning mlistener;
	MapView mMapView = null;  
	private BaiduMap mBaiduMap;
	public EditText lat_ET = null;
	public EditText log_ET = null;
	
	public double old_lat=0;
	public double old_log=0;
	// UI相关
		OnCheckedChangeListener radioButtonListener;
		Button requestLocButton;
		boolean isFirstLoc = true;// 是否首次定位
	public MapFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View myFragmentView=inflater.inflate(R.layout.fragment_map, container, false);
		btn=(Button) myFragmentView.findViewById(R.id.changeview);
		btn.setOnClickListener(this);
		mMapView = (MapView)myFragmentView.findViewById(R.id.bmapView); 
		
		mBaiduMap = mMapView.getMap();  
	        //普通地图  
	        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);  
	         //卫星地图  
	        // mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
	        
	        //mCurrentMode = LocationMode.NORMAL;
	        mCurrentMode = LocationMode.FOLLOWING;
			mBaiduMap
					.setMyLocationConfigeration(new MyLocationConfigeration(
							mCurrentMode, true, mCurrentMarker));
			
			// 开启定位图层
					mBaiduMap.setMyLocationEnabled(true);
					// 定位初始化
					mLocClient = new LocationClient(getActivity());
					mLocClient.registerLocationListener(myListener);
					LocationClientOption option = new LocationClientOption();
					option.setOpenGps(true);// 打开gps
					option.setCoorType("bd09ll"); // 设置坐标类型
					option.setScanSpan(1000);
				    
					mLocClient.setLocOption(option);
					mLocClient.start();
					Log.d("onCreateView","start");
					
		
		return myFragmentView;
	}

	
	 /**
		 * 定位SDK监听函数
		 */
		public class MyLocationListenner implements BDLocationListener {
			
			public List<LatLng> points = new ArrayList<LatLng>();
			
			@Override
			public void onReceiveLocation(BDLocation location) {
				Log.d("myListener","receive location");
				
				// map view 销毁后不在处理新接收的位置
				if (location == null || mMapView == null)
					return;
				MyLocationData locData = new MyLocationData.Builder()
						.accuracy(location.getRadius())
						// 此处设置开发者获取到的方向信息，顺时针0-360
						.direction(100).latitude(location.getLatitude())
						.longitude(location.getLongitude()).build();
				
				
			    
				if ((locData.latitude<0.000001) || (locData.longitude < 0.000001) ) 
						return;
				
				
				
				//lat_ET.setText(String.format("%.06f", locData.latitude));
			    //log_ET.setText(String.format("%.06f",locData.longitude));
			    
				mBaiduMap.setMyLocationData(locData);

							
				
				//LatLng p = new LatLng(locData.latitude, locData.longitude);
				//points.add(p);
				
				
				if (isFirstLoc) {
					isFirstLoc = false;
					LatLng ll = new LatLng(location.getLatitude(),
							location.getLongitude());
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
					mBaiduMap.animateMapStatus(u);
				}else {
					
					if ((old_lat == 0) || (old_log == 0) ) 
						return;
					points.clear();
					
					  LatLng p1 = new LatLng(old_lat, old_log);
					  LatLng p2 = new LatLng(locData.latitude, locData.longitude);
					  points.add(p1);
					  points.add(p2);
					  //mMapView.getMap().clear();
					  OverlayOptions ooPolyline = new PolylineOptions().width(5)
							.color(0xAAFF0000).points(points);
					  mBaiduMap.addOverlay(ooPolyline);	
					
				}
				
			    
			    old_lat=locData.latitude;
			    old_log=locData.longitude;
			
				
			}

			public void onReceivePoi(BDLocation poiLocation) {
			}
		}


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		  mMapView.onDestroy();  
		  mLocClient.stop();
		  Log.i("MapFragment","onDestroy");
	}

	

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
		Log.i("MapFragment","onPause");
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
		Log.i("MapFragment","onResume");
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mlistener=(Startrunning) activity;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.changeview){
			Log.i("MapFragment","changeview clicked");
			mlistener.changeview();
		}
	}
		 
}
