package com.example.elderlycare;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class notification extends Service implements JsonResponse
{
	
	View v;
	String[] iid,datetime,photo,expression;
	public static BluetoothSocket btSocket = null;
	public static OutputStream outStream = null;
	public InputStream instream = null;
	boolean stopWorker = false;
	int readBufferPosition = 0;
	byte[] readBuffer;
	Thread workerThread;
	SharedPreferences sh;
	
	Handler handler = new Handler();

	StringBuilder builder = new StringBuilder();
	
	Timer timer;
	TimerTask timerTask;
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void onCreate()  
	{
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) 
	{
		
		//Toast.makeText(getApplicationContext(), "hi notif", Toast.LENGTH_LONG).show();
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		startTimer() ;
		
		
		
	}


	void startTimer() {
		timer = new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 0, 60000);
	}
	
	void initializeTimerTask() {
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						viewnoti();
					}
				});
			}
		};
	}
	
	@SuppressLint("NewApi")
	public void createNotification(View  v, String[] cropname,int len)
	{
		 
	       // Prepare intent which is triggered if the
	        // notification is selected
		 int le=len+1;
	   Intent intent = new Intent(this,View_notifications.class);
	    
	   PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

	    // Build notification
	        // Actions are just fake
	   Notification noti = new Notification.Builder(this)

	  .setContentTitle("Emergency situation...")

	// .setContentText("place:"+place+"\n area :"+area+"\n description:"+description).setSmallIcon(R.drawable.location)
	  
	   .setContentText("You have "+le+" notification..").setSmallIcon(R.drawable.elderlyicon)
	   
	 .setContentIntent(pIntent)
           
	    .addAction(R.drawable.ic_launcher, "More", pIntent)
	           
	   .build();
	 
	       NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	      
	  // hide the notification after its selected
	      
	       noti.flags |= Notification.FLAG_AUTO_CANCEL;
	  		notificationManager.notify(0, noti);
	    }

	public void viewnoti()
	{ 
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)notification.this;
		String q="/viewnoti";
		jr.execute(q);
		
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try
		{
			String status=jo.getString("status");
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
					createNotification(v,datetime,i);  
				}
			}
			else
			{
				//Toast.makeText(getApplicationContext(), "No Students", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

}
