package com.example.homepractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView email = null;
	private TextView passwd = null;
	private Button loginBtn = null;
	private Button findpwBtn = null;
	private Button joinBtn = null;
	

	public static final int REQUEST_CODE_ANOTHER = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();		
	}

	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			if(!checkNet())
				return;
			
			switch (v.getId()) {
			case R.id.loginBtn:
				checkLogin();
				break;
			case R.id.findpwBtn:
				break;

			case R.id.joinBtn:
				Intent intent = new Intent(getBaseContext(), JoinActivity.class);
				startActivityForResult(intent, REQUEST_CODE_ANOTHER);
				break;
			}
		}
	};
	
	public void init() {
		email = (TextView)findViewById(R.id.email);
		passwd = (TextView)findViewById(R.id.passwd);
		loginBtn = (Button)findViewById(R.id.loginBtn);
		findpwBtn = (Button)findViewById(R.id.findpwBtn);
		joinBtn = (Button)findViewById(R.id.joinBtn);
		
		loginBtn.setOnClickListener(mClickListener);
		findpwBtn.setOnClickListener(mClickListener);
		joinBtn.setOnClickListener(mClickListener);
	}
	
	public boolean checkNet() {
		if (!NetworkCheck.IsWifiAvailable(this) && !NetworkCheck.Is3GAvailable(this))
		{
			Toast.makeText(this, "네크워크에 연결할 수 없습니다.", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	public boolean checkLogin() {
		
		if(email.length()==0 || passwd.length()==0) {
			Toast toast = Toast.makeText(getBaseContext(), "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG);
			toast.show();
			
			return false;
		}
		
		ConnectThread thread = new ConnectThread(email.getText().toString(), passwd.getText().toString(), getBaseContext());
		thread.start();
		
		return true;
	}
/*
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_ANOTHER) {
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 메소드가 호출됨. 요청코드 : " + requestCode + ", 결과코드 : " + resultCode, Toast.LENGTH_LONG);
			toast.show();

			if (resultCode == RESULT_OK) {
				String name = data.getExtras().getString("name");
				toast = Toast.makeText(getBaseContext(), "응답으로 전달된 name : " + name, Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}
*/
}
