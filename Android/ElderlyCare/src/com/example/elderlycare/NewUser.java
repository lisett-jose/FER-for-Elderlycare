package com.example.elderlycare;


import org.json.JSONObject;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends Activity implements JsonResponse {
	EditText e1,e2,e3,e4,e5,e6;
	Button b1;
	String fname,lname,phone,email,uname,pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		
		e1=(EditText)findViewById(R.id.fname);
		e2=(EditText)findViewById(R.id.lname);
		e3=(EditText)findViewById(R.id.phone);
		e4=(EditText)findViewById(R.id.email);
		e5=(EditText)findViewById(R.id.uname);
		e6=(EditText)findViewById(R.id.pass);
		b1=(Button)findViewById(R.id.button1);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				phone=e3.getText().toString();
				email=e4.getText().toString();
				uname=e5.getText().toString();
				pass=e6.getText().toString();
				
				JsonReq JR= new JsonReq();
				JR.json_response=(JsonResponse)NewUser.this;
				String q="/register?fname="+fname+"&lname="+lname+"&phone="+phone+"&email="+email+"&username=" + uname + "&password=" + pass;
				JR.execute(q);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		
		try {
			String status=jo.getString("status");
			Log.d("pearl",status);
			if(status.equalsIgnoreCase("success"))
			{
				startActivity(new Intent(getApplicationContext(),UserHome.class));	
				Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();	 
			}
			else if(status.equalsIgnoreCase("duplicate"))
			{
				 Toast.makeText(getApplicationContext(), "Username already Exist...", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
		
		
	}

}
