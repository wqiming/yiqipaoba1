package com.example.yiqipaoba1;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;






import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class LoginActivity extends ActionBarActivity implements OnClickListener {
    Button LoginBtn;
    Button RegisterBtn;
    EditText UsernameET;
    EditText PasswordET;
    HttpClient httpClient;
    SharedPreferences preferences;
	SharedPreferences.Editor editor;
    
	@Override
	public void onBackPressed() {
		Intent intent=new Intent();
		setResult(2,intent);
		finish();
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("一起跑吧");
		LoginBtn=(Button)this.findViewById(R.id.LoginBtn);
		RegisterBtn=(Button)this.findViewById(R.id.RegisterBtn);
		LoginBtn.setOnClickListener(this);
		RegisterBtn.setOnClickListener(this);
		UsernameET = (EditText) findViewById(R.id.Username);
		PasswordET = (EditText) findViewById(R.id.Password);
		
		preferences = getSharedPreferences("yiqipaoba1", MODE_PRIVATE);
		editor = preferences.edit();
		
		String Username = preferences.getString("Username", null);
		String Password = preferences.getString("Password", null);
		UsernameET.setText(Username==null?"":Username);
		PasswordET.setText(Password==null?"":Password);
		// 创建DefaultHttpClient对象
		httpClient = new DefaultHttpClient();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.RegisterBtn){
			Toast.makeText(this,
					"Register clicked!", 5000).show();
			
		}
		if(v.getId()==R.id.LoginBtn){
			
			    String name = ((EditText) this
					.findViewById(R.id.Username)).getText()
					.toString();
				String pass = ((EditText) this
					.findViewById(R.id.Password)).getText()
					.toString();
				HttpPost post = new HttpPost(
					"http://xrun.xtep.com/wbservice/UpdateUserInfo.aspx?action=login");
				// 如果传递参数个数比较多的话可以对传递的参数进行封装
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params
					.add(new BasicNameValuePair("Username", name));
				params
					.add(new BasicNameValuePair("Password", pass));
				try
				{
					// 设置请求参数
					post.setEntity(new UrlEncodedFormEntity(
						params, HTTP.UTF_8));
					// 发送POST请求
					HttpResponse response = httpClient
						.execute(post);
					// 如果服务器成功地返回响应
					if (response.getStatusLine()
						.getStatusCode() == 200)
					{
						String msg = EntityUtils
							.toString(response.getEntity());
						// 提示登录成功
						if(msg.equals("0")){
							editor.putString("Username",name);
							editor.putString("Password",pass);
							editor.commit();
							Intent intent = new Intent(getApplicationContext(), MainActivity.class);
							startActivityForResult(intent, 2);
							
							
						}else{
						    Toast.makeText(this, "login fail", 5000).show();	
						
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
		
		}
		
	}

	
}
