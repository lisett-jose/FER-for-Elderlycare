package com.example.elderlycare;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class View_notifications extends Activity implements OnItemClickListener,JsonResponse{

	ListView l1;
	SharedPreferences sh;
	String[] iid,datetime,photo,expression;
	public static String iid1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_notifications);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)View_notifications.this;
		String q="/viewnoti";
		jr.execute(q);
	}

	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewnoti"))
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
						iid[i]=ja.getJSONObject(i).getString("notification_id");
						datetime[i]=ja.getJSONObject(i).getString("datetime");
						photo[i]=ja.getJSONObject(i).getString("image_path");
						expression[i]=ja.getJSONObject(i).getString("expression_identified");
						
					}
					l1.setAdapter(new Singleitem(View_notifications.this,iid,datetime,photo,expression));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No notifications", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("updatestatus"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "Updated succesfully..", Toast.LENGTH_LONG).show();		
					startActivity(new Intent(getApplicationContext(),Login.class));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		iid1=iid[arg2];
		final CharSequence[] items = {"Update status as viewed","Cancel"};
		  AlertDialog.Builder builder = new AlertDialog.Builder(View_notifications.this);
		  builder.setTitle("Select option");
		  builder.setItems(items, new DialogInterface.OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int item) {
		    if (items[item].equals("Update status as viewed")) 
		    {   
		    	JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse)View_notifications.this;
				String q="/updatestatus?nid="+View_notifications.iid1;
				jr.execute(q);
		    	
		    }
			if (items[item].equals("Cancel"))
			{
                  dialog.dismiss();
              }
		    }
		  });
		  builder.show();
	}
}
