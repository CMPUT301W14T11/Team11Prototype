package com.example1.locationapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

public class EnterCommentsActivity extends Activity {
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	EditText title_edit , subject_edit;
    Button post_button,picture_add_button ;
    Location location;
    GPSTracker gps;
    ImageView imageview;
    int number; 
    double longitude;
    double latitude;
    Gson gson ;
    Context content;
    Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_comments);
		// Show the Up button in the action bar.
		//setupActionBar();
		imageview = (ImageView) findViewById(R.id.imageView1);
		picture_add_button = (Button) findViewById(R.id.button2);
		content = this;
		//make three new tabs
		title_edit = (EditText) findViewById(R.id.editText1);
		subject_edit = (EditText) findViewById(R.id.editText2);
		post_button = (Button) findViewById(R.id.button1);

	    gps = new GPSTracker(this);
		if (gps.canGetLocation){
		location = gps.getLocation();
		System.out.println(location+"wocao");
		}
				
				
	    Intent intent = getIntent();
				
	    number = intent.getIntExtra("number_of_comments", 0);
				
	    System.out.println("lol"+location);
				
	    longitude = location.getLongitude();
	    latitude =location.getLatitude();
	    gson = new Gson();
			
	}
			
		
		
			
	
	// send comment to server
    public void send(View view)
    {   String title = title_edit.getText().toString();
        if ("".equals(title))
        {   
        	Toast.makeText(getBaseContext(), "Title is empty! add some words please!", Toast.LENGTH_SHORT).show();
        }
       String subject = subject_edit.getText().toString();
       
       /*if(bitmap==null)
       {	   
         final Comments new_comment = new Comments(number,0,title,subject,new Date(),location,longitude,latitude);
       }
       else
       {
    	 final Comments new_comment = new Comments(number,0,title,subject,new Date(),location,longitude,latitude,bitmap);
       }*/
    	new AsyncTask<Void,Void,Void>()
    	{   ProgressDialog dialog1= new ProgressDialog(content);
    		@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				dialog1.setTitle("Loading cause your internet is too slow!");
				dialog1.show();
				super.onPreExecute();
			}
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				if(bitmap==null)
			       {	   
			         final Comments new_comment = new Comments(number,0,title_edit.getText().toString(),subject_edit.getText().toString(),new Date(),location,longitude,latitude);
			         insertMaster(new_comment);
			       }
			       else
			       { System.out.println("image posted");
                         			       
			         String encode_image= convert_image_to_string(bitmap);
			    	 final Comments new_comment = new Comments(number,0,title_edit.getText().toString(),subject_edit.getText().toString(),new Date(),location,longitude,latitude,encode_image);
			    	 insertMaster(new_comment);
			       }
				
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				dialog1.dismiss();
				super.onPostExecute(result);
				
				
			}
    		
    	}.execute();
       
    	setResult(RESULT_OK);
    	finish();
    }
    
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}





	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_comments, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// insert function for httppost comments
	 public void insertMaster(Comments comm)
	 {
		 HttpClient httpclient  = new DefaultHttpClient();
		 HttpPost httpPost = new HttpPost(SERVER+MASTERCOMMENT+number);
		 try {
			StringEntity data = new StringEntity(gson.toJson(comm));
			httpPost.setEntity(data);
			httpPost.setHeader("Accept","application/json");
			HttpResponse response = httpclient.execute(httpPost); 
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 //chose picture request for picture is 5 
	 public void addPicture(View view)
	 {
		 Intent intent = new Intent(this,ChoseImageActivity.class);
		 startActivityForResult(intent, 5);
	 }
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     if (resultCode==RESULT_OK)
	     {   String file = data.getStringExtra("image");
	    	 System.out.println("haha"+file);
	    	 bitmap=ShrinkBitmap(file, 50, 50);
	    	 
	    	 imageview.setImageBitmap(bitmap);
	    	 
	     }
	     
	 }
	 // convert the bitmap image to base64 string
	 public String  convert_image_to_string(Bitmap bitmap)
	 {
		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		 bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		 byte[] byteArray = byteArrayOutputStream .toByteArray();
		 String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
		return encoded;
	 }
	 // decrease size of the image
	 Bitmap ShrinkBitmap(String file, int width, int height){

		 BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		 bmpFactoryOptions.inJustDecodeBounds = true;
		 Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

		 int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
		 int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);

		 if (heightRatio > 1 || widthRatio > 1)
		 {
		 if (heightRatio > widthRatio)
		 {
		 bmpFactoryOptions.inSampleSize = heightRatio;
		 } else {
		 bmpFactoryOptions.inSampleSize = widthRatio;
		 }
		 }

		 bmpFactoryOptions.inJustDecodeBounds = false;
		 bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
		 return bitmap;
		 }
	 
	

}
