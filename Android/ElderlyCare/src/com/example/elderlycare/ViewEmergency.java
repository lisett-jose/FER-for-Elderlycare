package com.example.elderlycare;


import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class ViewEmergency extends Activity implements JsonResponse
{
	ListView l1;
	String[] iid,datetime,photo,expression;
	public static String iid1;
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_emergency);
		
		try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {}
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)ViewEmergency.this;
		String q="/viewemergency";
		jr.execute(q);
			
	}
	@Override
	
	public void response(JSONObject jo)
	{
		
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewemergency"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					
					iid=new String[ja.length()];
					datetime=new String[ja.length()];
					photo=new String[ja.length()];
					expression= new String[ja.length()];
					
					for(int i=0;i<ja.length();i++)
					{
						iid[i]=ja.getJSONObject(i).getString("input_id");
						datetime[i]=ja.getJSONObject(i).getString("datetime");
						photo[i]=ja.getJSONObject(i).getString("image_path");
						expression[i]=ja.getJSONObject(i).getString("expression_identified");
						
					}
					l1.setAdapter(new Singleitem(ViewEmergency.this,iid,datetime,photo,expression));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No posts", Toast.LENGTH_LONG).show();
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),UserHome.class);			
		startActivity(b);
	}
	
}
