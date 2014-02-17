package com.example.homepractice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ConnectThread extends Thread {
	private String email = null;
	private String passwd = null;
	private String name = null;
	private String tel = null;
	public static String Url = "http://220.149.217.111:1717/";
	private String type = null;
	Context context = null;
	JSONArray arr = null;

	String errorCode = null;

	Handler handler = new Handler();
	
	public ConnectThread(String email, String passwd, Context context) {
		this.email = email;
		this.passwd = passwd;
		this.context = context;
		type = "login";
	}
	public ConnectThread(String email, String passwd, String name, String tel, Context cont) {
		this.email = email;
		this.passwd = passwd;
		this.name = name;
		this.tel = tel;
		this.context = cont;
		type = "join";
	}
	public ConnectThread(JSONArray arr) {
		this.arr = arr;
		type = "json";
	}
	
	public void run() {

		try {
			request(Url);
			handler.post(new Runnable() {
				public void run() {
				}
			});
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}
	
	private String request(String urlStr) {
    	StringBuilder output = new StringBuilder();
    	try {
    		HttpClient client = new DefaultHttpClient();
    		HttpPost httppost = new HttpPost(urlStr);
    		StringEntity entity = null;

    		if(type.equals("json")) {
    			entity = new StringEntity(arr.toString(), "UTF-8");
    			httppost.setEntity(entity);
    		}
    		else {
    			List<NameValuePair> fields = new ArrayList<NameValuePair>(1);
    			fields.add(new BasicNameValuePair("type", type));
    			fields.add(new BasicNameValuePair("mem_email", email));
    			fields.add(new BasicNameValuePair("mem_passwd", passwd));
    			if(type.equals("join")) {
    				fields.add(new BasicNameValuePair("mem_name", name));
    				fields.add(new BasicNameValuePair("mem_tel", tel));
    			}
    			httppost.setEntity(new UrlEncodedFormEntity(fields, "UTF-8"));
    		}
    		
			Log.d("SampleHTTPClient", "\nRequest using HttpClient ...");
			HttpResponse response = client.execute(httppost);
			Log.d("SampleHTTPClient", "\nResponse from HttpClient ...");

			//InputStream instream = response.getEntity().getContent();
			//BufferedReader reader = new BufferedReader(new InputStreamReader(instream)) ;
			//String line = null;
			
			InputStream instream = response.getEntity().getContent();
			XmlParser xmlparser = new XmlParser(instream);
			errorCode = xmlparser.xmlParse();
			handler.post(new Runnable() {
				public void run() {
					if(errorCode.equals("401")) {
						Toast toast = Toast.makeText(context, "Email 중복", Toast.LENGTH_LONG);
						toast.show();
					}
					else if(errorCode.equals("301")) {
						Toast toast = Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG);
						toast.show();
					}
					else if(errorCode.equals("402")) {
						Toast toast = Toast.makeText(context, "로그인 실패", Toast.LENGTH_LONG);
						toast.show();
					}
				}
			});
			/*
			while(true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				output.append(line);
			}    			
			reader.close();
			*/
    	} catch(Exception ex) {
    		Log.e("SampleHTTPClient", "Exception in processing response.", ex);
    	}
    	
    	
    	return jsonParser(output.toString());
    }
	
	public String jsonParser(String json) {
		String result = null;
		try{
			JSONObject obj = new JSONObject(json);
			result = obj.getString("result");
		}
		catch (JSONException e){
			e.getStackTrace();
		}
		return result;
	}

}