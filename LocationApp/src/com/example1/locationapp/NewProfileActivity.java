package com.example1.locationapp;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Model.CommentUser;
import Model.CommentsModel;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * This activity is using for create the profile of a user 
 * then other user can check each other's information in the profile.
 * @author zuo2
 */
public class NewProfileActivity extends Activity {
    private EditText Eage,Efacebook,Elinkedin,Ephone,Eemail,Ebio;
    private ImageView imageview;
    private HttpClient httpclient;
    private String theUsername;
    private Bitmap bitmap;
    private String user_uuid;
    private CommentUser godusr;
    int flag = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_new_profile);
		Intent intent = getIntent();
		theUsername= intent.getStringExtra("username");
		user_uuid = intent.getStringExtra("uuid");
		godusr = (CommentUser) intent.getSerializableExtra("object");
		if(user_uuid!=null)
		{
			flag=1;
		}
		httpclient = new DefaultHttpClient();
		imageview = (ImageView) findViewById(R.id.imageView1);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//get image , requestcode is 654
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),ChoseImageActivity.class);
				startActivityForResult(intent, 654);
			}
		});
		Eage = (EditText) findViewById(R.id.EditText010);
		Efacebook = (EditText) findViewById(R.id.EditText030);
		Elinkedin = (EditText) findViewById(R.id.EditText040);
		Ephone = (EditText) findViewById(R.id.EditText050);
		Eemail = (EditText) findViewById(R.id.editText20);
		Ebio = (EditText) findViewById(R.id.editText1);
		if(godusr!=null)
		{
			Eage.setText(godusr.getAge());
			Efacebook.setText(godusr.getFacebook());
			Elinkedin.setText(godusr.getLinkedIn());
			Ephone.setText(godusr.getPhone());
			Eemail.setText(godusr.getEmail());
			Ebio.setText(godusr.getBio());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK)
		{
			switch(requestCode)
			{
				case 654:
					String file = data.getStringExtra("image");
					String file2 = data.getStringExtra("choseimage");
					if (file != null) {
						bitmap = BitmapFactory.decodeFile(file);
					} else {
						bitmap = BitmapFactory.decodeFile(file2);
					}

					imageview.setImageBitmap(bitmap);
					
					break;
			}
		}
		
	}

    
    
    
    
    
    
    
    
    
	/**Upload user profile to the server
     * @param v
     */
	public void upload_profile(View v)
	{   

		final CommentUser NewUser =new CommentUser();
		NewUser.setAge(Eage.getText().toString());
		NewUser.setName(theUsername);
		NewUser.setFacebook(Efacebook.getText().toString());
		NewUser.setLinkedIn(Elinkedin.getText().toString());
		NewUser.setPhone(Ephone.getText().toString());
		NewUser.setEmail(Eemail.getText().toString());
		NewUser.setBio(Ebio.getText().toString());
		if(bitmap!=null)
		{
			NewUser.setImageEncode(new CommentsModel().convert_image_to_string(bitmap));
		}
		
		UUID NewID = UUID.randomUUID();
		if(flag==1)
		{
			NewUser.setUudi(user_uuid);
			
		}
		else
		{
			NewUser.setUudi(NewID.toString());
		}
		
		   final Gson gson = new Gson();
			
		   new AsyncTask<Void,Void,Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				try{
					   HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/profile/"+NewUser.getUudi());
					   StringEntity data = new StringEntity(gson.toJson(NewUser));
						httpPost.setEntity(data);
						httpPost.setHeader("Accept", "application/json");
						httpclient.execute(httpPost);
						} catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
				return null;
			}}.execute();
	Toast.makeText(NewProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
	bitmap = null;
	finish();
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Inflate the menu; this adds items to the action bar if it is present
	 * @param menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_profile, menu);
		return true;
	}

	
	
	
	
	
	
	
	
	
	/**
	 * Handle action bar item clicks here. The action bar will
	 * automatically handle clicks on the Home/Up button, so long
	 * as you specify a parent activity in AndroidManifest.xml.
	 * @param item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	
	
	
	
	
	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_profile,
					container, false);
			return rootView;
		}
	}

}
