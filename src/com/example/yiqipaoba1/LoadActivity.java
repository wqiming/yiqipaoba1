package com.example.yiqipaoba1;

import java.util.Calendar;

import com.baidu.mapapi.SDKInitializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoadActivity extends Activity {
    private View loadLayout=null;
    private static final String TAG = "Load_Activity";
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	SDKInitializer.initialize(this.getApplicationContext());
        setContentView(R.layout.activity_load);
        loadLayout= (View)findViewById(R.id.load_layout); 
		
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int minuteOfDay = hour * 60 + minute;
		final int start = 6 * 60;
		final int end = 17 * 60 + 59;
		if (minuteOfDay >= start && minuteOfDay <= end) {
		    Log.d(TAG,"day");
		    loadLayout.setBackgroundResource(R.drawable.daybackground); 
		} else {
		    Log.d(TAG,"night");
		    loadLayout.setBackgroundResource(R.drawable.nightbackground); 
		}
		boolean gps_status=isOPen(this);
		if(gps_status==true){
			Log.d(TAG,"open");
		}else{
			Log.d(TAG,"closed");
			
			final Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("温馨提示");
			builder.setMessage("打开GPS?");
			builder.setPositiveButton("确定"
				, new OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						
						startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
						
					}
					
				  
				});
			// 为对话框设置一个“取消”按钮
			builder.setNegativeButton("取消"
				,  new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						finish();
					}
				});
			//创建、并显示对话框
			builder.create().show();
			
			
		 }
		
		 
    }
    
    
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		 if (requestCode == 2){
		    finish();
		 }
	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isOPen(getApplicationContext())){
			Log.d(TAG, "load main activity");
			   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			   //startActivity(intent);
			   startActivityForResult(intent, 2);
			}		
	}



	public static final boolean isOPen(final Context context) {  
        LocationManager locationManager   
                                 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
         boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
         //boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);  
        //if (gps || network) {  
          //  return true;  
        //}
         if (gps){
        	 return true;
         }
  
        return false;  
    }  
    
	 public static final void openGPS(Context context) {  
	        Intent GPSIntent = new Intent();  
	        GPSIntent.setClassName("com.android.settings",  
	                "com.android.settings.widget.SettingsAppWidgetProvider");  
	        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");  
	        GPSIntent.setData(Uri.parse("custom:3"));  
	        try {  
	            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();  
	        } catch (CanceledException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	
}
