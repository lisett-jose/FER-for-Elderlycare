package com.example.elderlycare;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONObject;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class RegisterFamilyMember extends Activity implements JsonResponse
{
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15;
	Button b,b1,b2,b3;
	RadioButton rmale,rfemale;
	String fname,lname,gen,photo1,photo2,photo3;
	SharedPreferences sh;
	 private DatePickerDialog fromDatePickerDialog;

	 private SimpleDateFormat dateFormatter;

	 String data;
	    String q;
	    String fpth = "";
	    byte[] byteArray1 = null;
	    byte[] byteArray2 = null;
	    byte[] byteArray3= null;
	    String y = "";
	    Uri imageUri = null;
	    public static Bitmap image = null;
	    File f = null;
	    public static String encodedImage = "", path = "";

	    private static final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
	    private Uri mImageCaptureUri;
	    private File outPutFile = null;
	    int flag=0;
	    ProgressBar pb_loading;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registerfamilymember);
		

		try 
		{	
			if(android.os.Build.VERSION.SDK_INT>9)
			{
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		e1=(EditText)findViewById(R.id.editTextfname);
		e2=(EditText)findViewById(R.id.editTextlname);
	
		e13=(EditText)findViewById(R.id.editTextimg1);
		e14=(EditText)findViewById(R.id.editTextimg2);
		e15=(EditText)findViewById(R.id.editTextimg3);
		rmale=(RadioButton)findViewById(R.id.radio0);
		rfemale=(RadioButton)findViewById(R.id.radio1);
		
		pb_loading = (ProgressBar) findViewById(R.id.progressBar1);
		pb_loading.setVisibility(View.GONE);
		
		e13.setEnabled(false);
		e14.setEnabled(false);
		e15.setEnabled(false);

		b1=(Button)findViewById(R.id.buttonimg1);
		b1.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				flag=1;
				selectImageOption();
			}
		});
		b2=(Button)findViewById(R.id.buttonimg2);
		b2.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				flag=2;
				selectImageOption();
			}
		});
		b3=(Button)findViewById(R.id.buttonimg3);
		b3.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				flag=3;
				selectImageOption();
			}
		});
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				
				photo1=e13.getText().toString();
				photo2=e14.getText().toString();
				photo3=e15.getText().toString();
				
				if(rmale.isChecked())
				{
					gen="Male";
				}
				else
				{
					gen="Female";
				}
				
				if(fname.equalsIgnoreCase(""))
				{
					e1.setError("please fill this field");
					e1.setFocusable(true);
				}
				else if(lname.equalsIgnoreCase(""))
				{
					e2.setError("please fill this field");
					e2.setFocusable(true);
				}
				else if(photo1.equalsIgnoreCase(""))
				{
					
					e13.setError("please upload image1");
					e13.setFocusable(true);
				}
				else if(photo2.equalsIgnoreCase(""))
				{
					
					e14.setError("please upload image2");
					e14.setFocusable(true);
				}
				else if(photo3.equalsIgnoreCase(""))
				{
					
					e15.setError("please upload image3");
					e15.setFocusable(true);
				}
				else
				{
					pb_loading.setVisibility(View.VISIBLE);
					
					uploadData();
				}
			}
		});
	}


	private void uploadData() {

        try {
        	q="http://"+Ipsettings.ip+"/api/familyregistration";
            FileUpload client = new FileUpload(q);
            client.connectForMultipart();

            client.addFormPart("fname", fname);
            client.addFormPart("lname", lname);
            client.addFormPart("gen", gen);
            client.addFilePart("upload_image", "abc.jpg", byteArray1);
            client.addFilePart("image1", "abc.jpg", byteArray1);
            client.addFilePart("image2", "abc.jpg", byteArray2);
            client.addFilePart("image3", "abc.jpg", byteArray3);
            
       //   client.addFilePart("image", "abc.jpg", bitmapdata);
            client.finishMultipart();
            data = client.getResponse();
            Log.d("lllllllll", data);
            JSONObject ob = new JSONObject(data);
//            JSONArray ar=new JSONArray(data); 
            if (ob.getString("status").equals("success")) {
                Toast.makeText(getApplicationContext(), "Registered.!", Toast.LENGTH_LONG).show();
//                pb_loading.setVisibility(View.GONE);
        		
                startActivity(new Intent(getApplicationContext(), UserHome.class));
            }

            Log.d("response=======", data);
        } catch (Exception e) 
        {
            Toast.makeText(getApplicationContext(), "Exception 123 : " + e, Toast.LENGTH_LONG).show();
            Log.d("jjj", e.toString());
        }
    }
	
	 private void selectImageOption() {
	        final CharSequence[] items = { "Choose from Gallery", "Cancel"};

	        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterFamilyMember.this);
	        builder.setTitle("Add Photo!");
	        builder.setItems(items, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {

	                if (items[item].equals("Capture Photo")) {
	                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

	                } else if (items[item].equals("Choose from Gallery")) {
	                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                    startActivityForResult(i, GALLERY_CODE);

	                } else if (items[item].equals("Cancel")) {
	                    dialog.dismiss();
	                }
	            }
	        });
	        builder.show();
	    }

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	        super.onActivityResult(requestCode, resultCode, data);

	        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

	            mImageCaptureUri = data.getData();
	            System.out.println("Gallery Image URI : " + mImageCaptureUri);
	            //   CropingIMG();

	            Uri uri = data.getData();
	            Log.d("File Uri", "File Uri: " + uri.toString());
	            // Get the path
	            //String path = null;
	            try {
	                path = FileUtils.getPath(this, uri);
	                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();
				if(flag==1)
				{
	                e13.setText(path.substring(path.lastIndexOf("/") + 1));
	                File fl = new File(path);
	                int ln = (int) fl.length();
	                byteArray1 = null;
	                InputStream inputStream = new FileInputStream(fl);
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                byte[] b = new byte[ln];
	                int bytesRead = 0;
	                while ((bytesRead = inputStream.read(b)) != -1) {
	                    bos.write(b, 0, bytesRead);
	                }
	                inputStream.close();
	                byteArray1 = bos.toByteArray();
				}
				if(flag==2)
				{
	                e14.setText(path.substring(path.lastIndexOf("/") + 1));
	                File fl = new File(path);
	                int ln = (int) fl.length();
	                byteArray2 = null;
	                InputStream inputStream = new FileInputStream(fl);
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                byte[] b = new byte[ln];
	                int bytesRead = 0;
	                while ((bytesRead = inputStream.read(b)) != -1) {
	                    bos.write(b, 0, bytesRead);
	                }
	                inputStream.close();
	                byteArray2 = bos.toByteArray();
				}
				if(flag==3)
				{
	                e15.setText(path.substring(path.lastIndexOf("/") + 1));
	                File fl = new File(path);
	                int ln = (int) fl.length();
	                byteArray3 = null;
	                InputStream inputStream = new FileInputStream(fl);
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                byte[] b = new byte[ln];
	                int bytesRead = 0;
	                while ((bytesRead = inputStream.read(b)) != -1) {
	                    bos.write(b, 0, bytesRead);
	                }
	                inputStream.close();
	                byteArray3 = bos.toByteArray();
				}
//	                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	               // im1.setImageBitmap(bit);

//	                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
//	                encodedImage = str;
	            } catch (Exception e) {
	                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	            }
	        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

	            try {
	                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	               // img_r.setImageBitmap(thumbnail);
	                String imageurl = getRealPathFromURI(imageUri);

	                if(flag==1)
	                {
		                 e13.setText(imageurl.substring(imageurl.lastIndexOf("/") + 1));
		                Toast.makeText(getApplicationContext(), imageurl, Toast.LENGTH_SHORT).show();
		                File file = new File(imageurl);
		                int ln = (int) file.length();
		                byteArray1 = null;
		                try {
		                    InputStream inputStream = new FileInputStream(file);
		                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		                    byte[] b = new byte[ln];
		                    int bytesRead = 0;
	
		                    while ((bytesRead = inputStream.read(b)) != -1) {
		                        bos.write(b, 0, bytesRead);
		                    }
		                    inputStream.close();
		                    byteArray1 = bos.toByteArray();
		                } catch (IOException e) 
		                {
		                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
		                }
	                }
	                if(flag==2)
	                {
		                 e14.setText(imageurl.substring(imageurl.lastIndexOf("/") + 1));
		                Toast.makeText(getApplicationContext(), imageurl, Toast.LENGTH_SHORT).show();
		                File file = new File(imageurl);
		                int ln = (int) file.length();
		                byteArray2 = null;
		                try {
		                    InputStream inputStream = new FileInputStream(file);
		                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		                    byte[] b = new byte[ln];
		                    int bytesRead = 0;
	
		                    while ((bytesRead = inputStream.read(b)) != -1) {
		                        bos.write(b, 0, bytesRead);
		                    }
		                    inputStream.close();
		                    byteArray2 = bos.toByteArray();
		                } catch (IOException e) 
		                {
		                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
		                }
	                }
	                if(flag==3)
	                {
		                 e13.setText(imageurl.substring(imageurl.lastIndexOf("/") + 1));
		                Toast.makeText(getApplicationContext(), imageurl, Toast.LENGTH_SHORT).show();
		                File file = new File(imageurl);
		                int ln = (int) file.length();
		                byteArray3 = null;
		                try {
		                    InputStream inputStream = new FileInputStream(file);
		                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		                    byte[] b = new byte[ln];
		                    int bytesRead = 0;
	
		                    while ((bytesRead = inputStream.read(b)) != -1) {
		                        bos.write(b, 0, bytesRead);
		                    }
		                    inputStream.close();
		                    byteArray3 = bos.toByteArray();
		                } catch (IOException e) 
		                {
		                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
		                }
	                }
//	                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
//	                encodedImage = str;

	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	        }
	    }

	    String getRealPathFromURI(Uri contentUri) {
	        String[] proj = {MediaStore.Images.Media.DATA};
	        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
	        int column_index = cursor
	                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
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
				Toast.makeText(getApplicationContext(), "Added Family Member", Toast.LENGTH_LONG).show();	 
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
