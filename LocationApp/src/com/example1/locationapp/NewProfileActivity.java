package com.example1.locationapp;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Model.CommentUser;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class NewProfileActivity extends Activity {
    EditText Ename,Eage,Efacebook,Elinkedin,Ephone,Eemail;
    Button CreateButton;
    HttpClient httpclient;
    
    String theUsername;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_profile);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Intent intent = getIntent();
		theUsername= intent.getStringExtra("username");
		httpclient = new DefaultHttpClient();
		
		
	}
    /**Upload user profile to the server
     * 
     * @param v
     */
	public void upload_profile(View v)
	{   
	Eage = (EditText) findViewById(R.id.EditText010);
	Efacebook = (EditText) findViewById(R.id.EditText030);
	Elinkedin = (EditText) findViewById(R.id.EditText040);
	Ephone = (EditText) findViewById(R.id.EditText050);
	Eemail = (EditText) findViewById(R.id.editText20);
	CreateButton = (Button) findViewById(R.id.new_profile_button);
		final CommentUser NewUser =new CommentUser();
		NewUser.setAge(Eage.getText().toString());
		NewUser.setName(theUsername);
		NewUser.setFacebook(Efacebook.getText().toString());
		NewUser.setLinkedIn(Elinkedin.getText().toString());
		NewUser.setPhone(Ephone.getText().toString());
		NewUser.setEmail(Eemail.getText().toString());
		UUID NewID = UUID.randomUUID();
		final String string_ID = NewID.toString();
		   final Gson gson = new Gson();
			
		   new AsyncTask<Void,Void,Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try{
					   HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/profile/"+string_ID);
						//String query_profile = "{\"query\":{\"match\":{\"name\":\""+UserName+"\"}}}";
						//StringEntity entity;
						//entity = new StringEntity(query_profile);
						//httppost.setHeader("Accept", "application/json");
						//httppost.setEntity(entity);
						//HttpResponse response = httpclient.execute(httppost);
						
						StringEntity data = new StringEntity(gson.toJson(NewUser));
						httpPost.setEntity(data);
						httpPost.setHeader("Accept", "application/json");
						HttpResponse response = httpclient.execute(httpPost);
					    System.out.println(response.getStatusLine().toString() + "testing");
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				return null;
			}}.execute();
	Toast.makeText(NewProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
	finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
