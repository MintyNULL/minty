package com.example.homepractice;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

public class CustomAdapter extends BaseAdapter {

	private ViewHolder viewHolder = null;
	// 뷰를 새로 만들기 위한 Inflater
	private LayoutInflater inflater = null;
	private ArrayList<String> sArrayList = new ArrayList<String>();
	private boolean[] isCheckedConfrim;

	public CustomAdapter (Context context , ArrayList<String> mList){
		inflater = LayoutInflater.from(context);
		sArrayList = mList;
		// ArrayList Size 만큼의 boolean 배열을 만든다.
		// CheckBox의 true/false를 구별 하기 위해
		isCheckedConfrim = new boolean[sArrayList.size()];
	}

	// CheckBox를 모두 선택하는 메서드
	public void setAllChecked(boolean ischeked) {
		int tempSize = isCheckedConfrim.length;
		for(int a=0 ; a<tempSize ; a++){
			isCheckedConfrim[a] = ischeked;
		}
	}

	public void setChecked(int position) {
		isCheckedConfrim[position] = !isCheckedConfrim[position];
	}

	public ArrayList<Integer> getChecked(){
		int tempSize = isCheckedConfrim.length;
		ArrayList<Integer> mArrayList = new ArrayList<Integer>();
		for(int b=0 ; b<tempSize ; b++){
			if(isCheckedConfrim[b]){
				mArrayList.add(b);
			}
		}
		return mArrayList;
	}

	@Override
	public int getCount() { 
		return sArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ConvertView가 null 일 경우
		View v = convertView;

		if( v == null ){
			viewHolder = new ViewHolder();
			// View를 inflater 시켜준다.
			v = inflater.inflate(R.layout.row, null);
			viewHolder.cBox = (CheckBox) v.findViewById(R.id.checkBox);
			v.setTag(viewHolder);
		}

		else {
			viewHolder = (ViewHolder)v.getTag();
		}

		// CheckBox는 기본적으로 이벤트를 가지고 있기 때문에 ListView의 아이템
		// 클릭리즈너를 사용하기 위해서는 CheckBox의 이벤트를 없애 주어야 한다.
		viewHolder.cBox.setClickable(false);
		viewHolder.cBox.setFocusable(false);

		viewHolder.cBox.setText(sArrayList.get(position));
		// isCheckedConfrim 배열은 초기화시 모두 false로 초기화 되기때문에
		// 기본적으로 false로 초기화 시킬 수 있다.
		viewHolder.cBox.setChecked(isCheckedConfrim[position]);

		return v;
	}
	class ViewHolder {
		// 새로운 Row에 들어갈 CheckBox
		private CheckBox cBox = null;
	}
}