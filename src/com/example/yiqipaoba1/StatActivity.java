package com.example.yiqipaoba1;

import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class StatActivity extends ActionBarActivity {
	SQLiteDatabase db;
	ListView listView;
	private  MyCustomAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir()
				.toString() + "/rundata.db3" , null);		
		listView = (ListView)findViewById(R.id.recordlist);
		setTitle("我的跑步纪录");
		String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		
		try 
		{
			Cursor cursor = db.rawQuery("select * from stats", null);
			inflateList(cursor);
		}
		catch(SQLiteException  se)
		{
			Log.i("StatActivity","SQL access exception");
			
		}
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i("StatActivity",Integer.toString(position));
				
			}});
	}
	
		
	@Override
	public void onBackPressed() {
		
		// TODO Auto-generated method stub
	    if (db != null && db.isOpen())
				{
					db.close();
				}
		super.onBackPressed();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stat_activity_action, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		  switch (item.getItemId()) {
          case R.id.discard:
        	  
        	  int count=adapter.list.size();
        	  Log.i("discard",Integer.toString(count));
        	  int i;
        	  for(i=0;i<count;i++){
        		  Log.i("element",(adapter.list.get(i)));  
        		  db.delete("stats", "date like ?", new String[]{adapter.list.get(i)});
        		  adapter.notifyDataSetChanged();
        		  Cursor cursor = db.rawQuery("select * from stats", null);
        		  adapter.notifyDataSetChanged();
        		  adapter.changeCursor(cursor);
        	  }
        	  
              return true;
          default:
              return super.onOptionsItemSelected(item);
      }
		
	}


	private void inflateList(Cursor cursor) {
		// TODO Auto-generated method stub
		
		 adapter = new MyCustomAdapter(
				this, R.layout.line, cursor 
				, new String[]{"date" , "distance", "duration"}
				, new int[]{R.id.date , R.id.distance, R.id.duration});
			//显示数据
			listView.setAdapter(adapter);
			listView.setItemsCanFocus(true);
	    
	}


	
}
