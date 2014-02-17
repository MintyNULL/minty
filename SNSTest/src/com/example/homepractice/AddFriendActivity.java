package com.example.homepractice;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class AddFriendActivity extends Activity {

	private Button addFriendBtn = null;
	private ListView mListView = null;
	private CheckBox mAllCheckBox = null;
	
	// Data를 관리해주는 Adapter
	private CustomAdapter mCustomAdapter = null;
	// String을 사용한 ArrayList
	private ArrayList<String> mArrayList = new ArrayList<String>();
	

	public static final int REQUEST_CODE_ANOTHER = 1001;
	

	JSONArray array = new JSONArray();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_list);
		
		init();
		
		getList();
		
		mCustomAdapter = new CustomAdapter(this, mArrayList);
		mListView.setAdapter(mCustomAdapter);
		mListView.setOnItemClickListener(mItemClickListener);
		
	}

	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addFriendBtn:
				break;
				
			case R.id.main_all_check_box:
				mCustomAdapter.setAllChecked(mAllCheckBox.isChecked());
				// Adapter에 Data에 변화가 생겼을때 Adapter에 알려준다.
				mCustomAdapter.notifyDataSetChanged();
				break;
			}
		}
	};
	
	// ListView 안에 Item을 클릭시에 호출되는 Listener
	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,	int position, long arg3) {
			Toast.makeText(getBaseContext(), ""+(position+1), Toast.LENGTH_SHORT).show();
			mCustomAdapter.setChecked(position);
			// Data 변경시 호출 Adapter에 Data 변경 사실을 알려줘서 Update 함.
			mCustomAdapter.notifyDataSetChanged();
		}
	};
	
	public void init() {
		mListView = (ListView) findViewById(R.id.list);
		mAllCheckBox = (CheckBox) findViewById(R.id.main_all_check_box);
		mAllCheckBox.setOnClickListener(mClickListener);
		addFriendBtn = (Button) findViewById(R.id.addFriendBtn);
		addFriendBtn.setOnClickListener(mClickListener);
	}
	
	public void getList(){
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[] { 
				ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				};
		
		String[] selectionArgs = null;
		
		//정렬
	    String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	
	    //조회해서 가져온다
	    Cursor contactCursor = managedQuery(uri, projection, null, selectionArgs, sortOrder);
	    
	    if(contactCursor.moveToFirst()){
	    	do{
	    		mArrayList.add(contactCursor.getString(1) + "//" + PhoneNumberUtils.formatNumber(contactCursor.getString(0)).replaceAll("-", ""));
	    		array.put(PhoneNumberUtils.formatNumber(contactCursor.getString(0)).replaceAll("-", ""));
	    	}while(contactCursor.moveToNext());
	    }
	    
	}

}