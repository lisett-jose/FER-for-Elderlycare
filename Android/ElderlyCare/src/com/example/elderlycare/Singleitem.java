package com.example.elderlycare;


import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Singleitem extends ArrayAdapter<String>
{
	String[] iid,datetime,photo,expression;
	Activity activity;
	
	public Singleitem(Activity activity,String[] iid,String[] datetime,String[] photo,String[] exapression) 
	{
		super(activity,R.layout.singleitem,photo);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.iid=iid;
		this.datetime=datetime;
		this.expression=exapression;
		this.photo=photo;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		
		LayoutInflater inflate=activity.getLayoutInflater();
		View vi=inflate.inflate(R.layout.singleitem, null,true);
		
		TextView t1=(TextView) vi.findViewById(R.id.textViewdatetime);
		TextView t2=(TextView) vi.findViewById(R.id.textViewexp);
		
		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getContext());
		t1.setText(datetime[position]);
		t2.setText(expression[position]);
		
		ImageView im1=(ImageView) vi.findViewById(R.id.imageView1);
		String path = "http://"+sh.getString("ip", "")+"/"+photo[position];
	//	Toast.makeText(activity, path, Toast.LENGTH_SHORT).show();
		Picasso.with(activity).load(path).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(im1);
	
	
		return vi;
	}

}
