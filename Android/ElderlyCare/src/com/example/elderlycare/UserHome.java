package com.example.elderlycare;


import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class UserHome extends Activity
{

	Button b1,b2,b3,b4,b5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		
		
		b1=(Button)findViewById(R.id.buttonfammember);
		b2=(Button)findViewById(R.id.buttonvemg);

		b4=(Button)findViewById(R.id.buttonlogout);
		b5=(Button)findViewById(R.id.buttonuseregister);
		
		if(Login.content.equals("admin"))
		{
			b5.setVisibility(View.VISIBLE);
		}
		else if(Login.content.equals("user"))
		{
			b5.setVisibility(View.GONE);
		}
		
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),NewUser.class));
			}
		});
		b1.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),RegisterFamilyMember.class));
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),ViewEmergency.class));
				
			}
		});
		
	
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Login.class));
				
			}
		});
	}

}
