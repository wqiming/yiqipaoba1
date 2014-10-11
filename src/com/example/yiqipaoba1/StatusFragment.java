package com.example.yiqipaoba1;

import java.util.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class StatusFragment extends Fragment implements OnClickListener {
    Button btn1;
    Button btn2;
    
    SQLiteDatabase db;
    long startTime = 0;
    public int paused = 0;
    public long totaltime = 0;
    public long currenttime = 0;
    public double totallen=0;
    TextView timerTextView;
    TextView speedTextView;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
    	 public void run() {
    		 currenttime = System.currentTimeMillis() - startTime+totaltime;
    		 int seconds = (int) (currenttime/ 1000);
             int minutes = seconds / 60;
             seconds = seconds % 60;
             timerTextView.setText(String.format("%d'%02d\"", minutes, seconds));
             speedTextView.setText(String.format("%.2f", (totallen*3600/currenttime)));
             timerHandler.postDelayed(this, 500);
    	 }
    };
    
	public StatusFragment() {
		// Required empty public constructor
	}

	public void AddLength(double len){
		Log.i("StatusFragment","AddLength");
		totallen=totallen+len;
		TextView DistTextView = (TextView)getView().findViewById(R.id.distance);
		DistTextView.setText(String.format("%.3f", totallen/1000));
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_status, container, false);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		btn1=(Button)getView().findViewById(R.id.finish);
		btn1.setOnClickListener(this);
		btn2=(Button)getView().findViewById(R.id.pause);
		btn2.setOnClickListener(this);
		timerTextView=(TextView)getView().findViewById(R.id.timer);
		speedTextView=(TextView)getView().findViewById(R.id.speed);
		startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
        db = SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir()
    			.toString() + "/rundata.db3" , null);	
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (db != null && db.isOpen())
		{
			db.close();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		running_controller con=(running_controller)getActivity();
		// TODO Auto-generated method stub
		if(v.getId()==R.id.finish){
			Log.i("StatusFragment","finish clicked");
			String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
			try 
			{
				insertData(db , date, "empty" , "empty");
				//Cursor cursor = db.rawQuery("select * from rundb", null);
			}
			catch(SQLiteException  se)
			{
				//执行DDL创建数据表
				db.execSQL("create table stats(_id integer primary key autoincrement,"
					+ " date varchar(50),"
					+ " distance varchar(50),"
					+ " duration varchar(50))");
				//执行insert语句插入数据
				insertData(db , date, "empty" , "empty");
				//执行查询
				//Cursor cursor = db.rawQuery("select * from rundb", null);
				
			}
		}
		if(v.getId()==R.id.pause){
			if(paused==0){
			totaltime=currenttime;
			Log.i("StatusFragment","pause clicked");
			btn2.setText("继续");
			timerHandler.removeCallbacks(timerRunnable);
			con.onPauseRunning();
			paused=1;
			}else{
				btn2.setText("暂停");
				startTime = System.currentTimeMillis();
				timerHandler.postDelayed(timerRunnable, 0);
				con.onResumeRunning();
				paused=0;
			}
		}
	}


	private void insertData(SQLiteDatabase db2, String date, String distance,
			String duration) {
		// TODO Auto-generated method stub
		db.execSQL("insert into stats values(null , ? , ?, ?)"
				, new String[]{date , distance, duration});
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//timerHandler.removeCallbacks(timerRunnable);
		//btn2.setText("暂停");
	}

}
