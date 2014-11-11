package com.example.yiqipaoba1;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MyCustomAdapter extends CursorAdapter implements OnClickListener  {
	private Context context;
	private int layout;
    public ArrayList<String> list = new ArrayList<String>();
	
	public MyCustomAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, c);
		this.context = context;
	    this.layout = layout;
	   
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void bindView(View v, Context context, Cursor c) {
		// TODO Auto-generated method stub
		
		 Log.i("newview","bindview");
		 int nameCol = c.getColumnIndex("date");
		 
	     String date = c.getString(nameCol);
	 
	        /**
	         * Next set the name of the entry.
	         */    
	        TextView dateText = (TextView) v.findViewById(R.id.date);
	        if (dateText != null) {
	            dateText.setText(date);
	        }
	      
	        nameCol = c.getColumnIndex("distance");
		     String distance = c.getString(nameCol);
		     TextView distanceText = (TextView) v.findViewById(R.id.distance);
		     distanceText.setText(distance); 
	        
		     nameCol = c.getColumnIndex("duration");
		     String duration = c.getString(nameCol);
		     TextView durationText = (TextView) v.findViewById(R.id.duration);
		     durationText.setText(duration);
		     
	        CheckBox cb = (CheckBox)v.findViewById(R.id.checkBox);
		     cb.setTag(c.getPosition());
		     cb.setOnClickListener(this);
		     if (cb.isChecked()) {
		         cb.setChecked(false);
		     }
	}
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		Cursor c = getCursor();
		 Log.i("newview","newview");
		 
	     final LayoutInflater inflater = LayoutInflater.from(context);
	     View v = inflater.inflate(layout, parent, false);
	     
	     int nameCol = c.getColumnIndex("date");
	     String date = c.getString(nameCol);
	     TextView dateText = (TextView) v.findViewById(R.id.date);
	     if (dateText != null) {
	            dateText.setText(date);
	        }
	     
	     nameCol = c.getColumnIndex("distance");
	     String distance = c.getString(nameCol);
	     TextView distanceText = (TextView) v.findViewById(R.id.distance);
	     distanceText.setText(distance);
	     
	     nameCol = c.getColumnIndex("duration");
	     String duration = c.getString(nameCol);
	     TextView durationText = (TextView) v.findViewById(R.id.duration);
	     durationText.setText(duration);
	     
	     CheckBox cb = (CheckBox)v.findViewById(R.id.checkBox);
	     cb.setTag(c.getPosition());
	     cb.setOnClickListener(this);
	     if (cb.isChecked()) {
	         cb.setChecked(false);
	     }
	 
	        
	     return v;
	}


	@Override
	public void onClick(View v) {
		/*must set to int instead of Integer, otherwise the list.remove function will be confused*/
		int i;
		Cursor c = getCursor();
		// TODO Auto-generated method stub
		if(v.getId()==R.id.checkBox){
			CheckBox cb = (CheckBox)v.findViewById(R.id.checkBox);
			int pos = (Integer) v.getTag();
			c.moveToPosition(pos);
		    String date=c.getString(c.getColumnIndex("date"));
			if(cb.isChecked()){
			 
			  Log.i("add", date);
			  list.add(date);
			  //Log.i("add", Integer.toString(pos));
			  //Log.i("size of list=", Integer.toString(list.size()));
			}else{
				
				
			     i=list.indexOf(date);
			     Log.i("remove checkbox index=", Integer.toString(i));
				 if(i!=-1){
			      list.remove(i);
			      Log.i("size of list=", Integer.toString(list.size()));
				}
				
			}
			Log.i("checkbox",c.getString(c.getColumnIndex("date")));
		}
	}


	
}
