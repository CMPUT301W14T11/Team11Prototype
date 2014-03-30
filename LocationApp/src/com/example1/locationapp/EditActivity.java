package com.example1.locationapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Model.Comments;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EditActivity extends Activity {
	EditText title , subject;
	Button location, submit;
	Comments newcomments;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_edit);
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		title = (EditText) findViewById(R.id.editText1);
		subject = (EditText) findViewById(R.id.editText2);
		location = (Button) findViewById(R.id.button1);
		submit = (Button) findViewById(R.id.button2);
		new AsyncTask<Void,Void,Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				
				try {
					// IDModel id_toReturn ;// this is ID object from server
					Gson gson = new Gson();
					HttpClient httpclient = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(
							"http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/"+id);
					httpget.addHeader("Accept", "application/json");

					HttpResponse response = httpclient.execute(httpget);

					String json = getEntityContent(response);

					// We have to tell GSON what type we expect
					Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Comments>>() {
					}.getType();
					// Now we expect to get a Recipe response
					ElasticSearchResponse<Comments> esResponse = gson.fromJson(json,
							elasticSearchResponseType);
					// We get the recipe from it!
					newcomments = esResponse.getSource();
					



					// System.out.println(recipe.toString());
					// httpget.releaseConnection();
				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				} catch (NullPointerException e) {

					
				} catch (RuntimeException e) {

					
				}
				return null;
				
				
			}
			
		}.execute();
	}
    public void changelocation(View v)
    {
    	
    }
    public void submit(View v)
    { 
    	String NewTitle = title.getText().toString();
    	String NewSubject = subject.getText().toString();
    	
    	if(NewTitle.equals(null))
    	{
    		Toast.makeText(EditActivity.this, "title can't be empty",Toast.LENGTH_SHORT).show();
    	}
    	else
    	{
    		newcomments.setThe_comment(NewTitle);
    		newcomments.setSubject_comment(NewSubject);
    		new AsyncTask<Void, Void, Void>()
        	{

    			@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					new AsyncTask<Void,Void,Void>()
					{

						@Override
						protected Void doInBackground(Void... params) {
							
							HttpClient httpclient = new DefaultHttpClient();
							HttpDelete httpDelete = new HttpDelete("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/"+id);
							httpDelete.addHeader("Accept","application/json");
							try {
								HttpResponse responsedelete = httpclient.execute(httpDelete);
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
						}
						
					}.execute();
				}

				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					Toast.makeText(EditActivity.this, "Comment Updated", Toast.LENGTH_SHORT).show();
					finish();
				}

				@Override
    			protected Void doInBackground(Void... params) {
    				// TODO Auto-generated method stub
    				try{
					HttpClient httpclient = new DefaultHttpClient();
					
					// delete
    	    		HttpPut httpPost = new HttpPut("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/"+id);
    	    		StringEntity stringentity = null;
                    Gson gson = new Gson();
    	    		stringentity = new StringEntity(gson.toJson(newcomments));
    	    		httpPost.setHeader("Accept", "application/json");
    	    		httpPost.setEntity(stringentity);
    	    		HttpResponse response = null;
    	    		response = httpclient.execute(httpPost);
    	    		System.out.println("put response:"+response.getStatusLine());
    	    		}
    	    		 catch (ClientProtocolException e) {
    	    			e.printStackTrace();

    	    		} catch (IOException e) {
    	    			e.printStackTrace();

    	    		} catch (NullPointerException e) {

    	    			
    	    		} catch (RuntimeException e) {

    	    			
    	    		} 
    	    	

    				return null;
    			}
        		
        	}.execute();
    		    	
    	}
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_edit, container,
					false);
			return rootView;
		}
	}
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:" + json);
		return json;
	}

}