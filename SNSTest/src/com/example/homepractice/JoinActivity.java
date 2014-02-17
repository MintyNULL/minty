package com.example.homepractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JoinActivity extends Activity {
	
	private TextView joinEmail = null;
	private TextView joinPasswd = null;
	private TextView joinName = null;
	private TextView joinTel = null;
	private Button joinBtn = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        init();
        
		joinBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				ConnectThread thread = new ConnectThread(joinEmail.getText().toString(), joinPasswd.getText().toString(), joinName.getText().toString(), joinTel.getText().toString(), getBaseContext());
				thread.start();

				Intent intent = new Intent(getBaseContext(), AddFriendActivity.class);
   				startActivityForResult(intent, 1002);
			}
		});

    }
    
    public void init() {
    	joinEmail = (TextView)findViewById(R.id.joinEmail);
    	joinPasswd = (TextView)findViewById(R.id.joinPasswd);
    	joinName = (TextView)findViewById(R.id.joinName);
    	joinTel = (TextView)findViewById(R.id.joinTel);
		joinBtn = (Button)findViewById(R.id.joinBtn2);
	}
   
/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1002) {
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
