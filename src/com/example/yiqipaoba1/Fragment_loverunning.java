package com.example.yiqipaoba1;

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

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Fragment_loverunning extends Fragment implements OnClickListener {
	private View myFragmentView;
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
		return myFragmentView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.button1){
			Log.i("loverunning","button1 clicked");
		}else if(v.getId() == R.id.button2){
			Log.i("loverunning","button2 clicked");
			 Intent intent = new Intent(getActivity(),Startrunning.class);
			   startActivity(intent);
			 //startActivityForResult(intent, 2);
		}
	}

}
