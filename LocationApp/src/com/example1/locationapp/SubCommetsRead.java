package com.example1.locationapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import Model.Comments;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SubCommetsRead extends Activity {
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private ListView listViewSubComment;
	private EditText editText;
	private Button button1;
	private cutadapter ListAdapter;
	private ArrayList<Comments> comment_list; 
	private Bitmap bitmap;
	private int number; 
	private Context content;
	private Location location;
    private GPSTracker gps;
    private double longitude;
    private double latitude;
    private Gson gson = new Gson();
    //private EnterCommentsActivity callEnterComments = new EnterCommentsActivity();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sub_commets_read);
	listViewSubComment = (ListView)findViewById(R.id.listViewSubComments);
	editText = (EditText)findViewById(R.id.editTextSubmitSubComments);
	button1 = (Button)findViewById(R.id.buttonSaveSubComments);
	button1.setText("Submit Sub Comments");
    comment_list = new ArrayList<Comments>();
    // add an example to test the list
    comment_list.add(new Comments(1,0,0, 0, "It works", "Tesing", new Date(), null, 123, 123, null));
	ListAdapter = new cutadapter(this, R.layout.listlayout, comment_list);
	gps = new GPSTracker(this);
	content = this;
	if (gps.canGetLocation){
	location = gps.getLocation();
	System.out.println(location+"wocao");
	}
			
			
    Intent intent = getIntent();
			
    number = intent.getIntExtra("number_of_comments", 0);
			
    System.out.println("lol"+location);
			
    longitude = location.getLongitude();
    latitude =location.getLatitude();
	
	listViewSubComment.setAdapter(ListAdapter);
	button1.setOnClickListener(new MyButton1Listener());
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.sub_commets_read, menu);
	return true;
	}
	class MyButton1Listener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String title = editText.getText().toString();
	        if ("".equals(title))
	        {   
	        	
	        	Toast.makeText(getBaseContext(), "Title is empty! add some words please!", Toast.LENGTH_SHORT).show();
	        	
	        }else{
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
				         final Comments new_comment = new Comments(0,number,0,0,editText.getText().toString(),editText.getText().toString(),new Date(),location,longitude,latitude);
				         insertMaster(new_comment);
				       }
				       else
				       { System.out.println("image posted");
	                         			       
				         String encode_image= convert_image_to_string(bitmap);
				    	 final Comments new_comment = new Comments(0,number,0,0,editText.getText().toString(),editText.getText().toString(),new Date(),location,longitude,latitude,encode_image);
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
		}
	}
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
	public String  convert_image_to_string(Bitmap bitmap)
	 {
		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		 bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		 byte[] byteArray = byteArrayOutputStream .toByteArray();
		 String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
		return encoded;
	 }
}


