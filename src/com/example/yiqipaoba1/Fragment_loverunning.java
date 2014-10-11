package com.example.yiqipaoba1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Fragment_loverunning extends Fragment implements OnClickListener {
	private View myFragmentView;
	
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	   
		
	    super.onCreate(savedInstanceState);
		
		
	}

	public Fragment_loverunning() {
		// Required empty public constructor
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		myFragmentView=inflater.inflate(R.layout.fragment_loverunning,
				container, false);
		Button btn1=(Button)myFragmentView.findViewById(R.id.button1);
		btn1.setOnClickListener(this);
		Button btn2=(Button)myFragmentView.findViewById(R.id.button2);
		btn2.setOnClickListener(this);
		
	    mLocationClient = new LocationClient(getActivity());     //声明LocationClient类
		mLocationClient.registerLocationListener( myListener );    //注册监听函数
			
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		    
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		
		return myFragmentView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.button1){
			Log.i("loverunning","button1 clicked");
		}
		if(v.getId() == R.id.button2){
			Log.i("loverunning","button2 clicked");
			 Intent intent = new Intent(getActivity(),Startrunning.class);
			   startActivity(intent);
			 //startActivityForResult(intent, 2);
		}
		if(v.getId() == R.id.recordbtn){
		
			
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onDestroy();
	}

	public class MyLocationListener implements BDLocationListener {
		private String url = "http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=LEubmtpTpnHSLfNEtXe5kmRc";
		//private String url = "http://www.weather.com.cn/data/cityinfo/101281601.html";
		//DefaultHttpClient httpClient = new DefaultHttpClient();
	    //HttpPost httpPost = new HttpPost(url);
	    
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null)
	            return ;
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\ncity : ");
		sb.append(location.getCity());
		
		Log.i("loverunning",sb.toString());
		
		 try {
		      // defaultHttpClient
		      DefaultHttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost(url);
		      //httpPost.setHeader("Content-type", "application/json");
		      HttpResponse httpResponse = httpClient.execute(httpPost);
		      HttpEntity httpEntity = httpResponse.getEntity();
		      String msg = EntityUtils
						.toString(httpResponse.getEntity());
		      Log.i("loverunning",msg);
		    } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		    } catch (ClientProtocolException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
	}
 }
}


