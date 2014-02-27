package com.example1.locationapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

//import ca.ualberta.cs.CMPUT301.chenlei.ElasticSearchResponse;
//import ca.ualberta.cs.CMPUT301.chenlei.ElasticSearchSearchResponse;
//import ca.ualberta.cs.CMPUT301.chenlei.Recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import ca.ualberta.cs.CMPUT301.chenlei.ElasticSearchResponse;
//import ca.ualberta.cs.CMPUT301.chenlei.ElasticSearchSearchResponse;

public class MainActivity extends Activity implements OnRefreshListener {
    ListView listview ;
    ArrayList<Comments> comment_array;
    cutadapter adapter ;
    ID total_comments;
    Gson gson;
    HttpClient httpclient;
    ID theid;
    int number_of_comments;
    Location current_location;
    GPSTracker gps ;
    Context content;
    ProgressDialog dialog1;
    // request code for startActivityForResult are:
    // "1" for enterCommentActivity, so it will bring you to comment entering activity
    private PullToRefreshLayout mPullToRefreshLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		content = this;
		dialog1 = new ProgressDialog(content);
		
		// getting location when app starts, so we can search the database for location, will add use location later
		gps = new GPSTracker(this);
		if (gps.canGetLocation)
		{
			current_location = gps.getLocation();
			System.out.println("can get location");
		}
		else
		{   // if gps is not turned on then , ask user to turn it on 
			gps.showSettingsAlert();
		}
		// start a httpclient for connecting to server
		
		httpclient= new DefaultHttpClient();
		
		comment_array = new ArrayList<Comments>();
		listview = (ListView) findViewById(R.id.ptr_listview1);
		
		total_comments = new ID(0);
		gson = new Gson();
		//using async task to get ID object form server
		new AsyncTask<Void,Void,Void>()
		{
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
				theid = get_id();
				number_of_comments = theid.id_for_master;
				return null;
				
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				dialog1.dismiss();
				super.onPostExecute(result);
				
				
			}

			
			
		}.execute();
		//async task is done
		adapter = new cutadapter(MainActivity.this,R.layout.listlayout,comment_array);
		
		//add pull to refresh
		// Now find the PullToRefreshLayout to setup
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        
		// Now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(this)
		// Mark All Children as pullable
		   .allChildrenArePullable()
		// Set the OnRefreshListener
		   .listener(this)
		// Finally commit the setup to our PullToRefreshLayout
		   .setup(mPullToRefreshLayout);
		// done adding pull to refresh
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		listview.setAdapter(adapter);
	}
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId())
		{
		case R.id.item1:
			System.out.println("new is clicked");
			Intent intent = new Intent ();
			intent.putExtra("number_of_comments",number_of_comments);
			intent.setClass(MainActivity.this, EnterCommentsActivity.class);
			startActivityForResult(intent, 1);
			break;
			
		case R.id.item2:
			Intent intent2 = new Intent(MainActivity.this,PlayTube.class);
			startActivity(intent2);
			break;
			}
		
		
		return super.onOptionsItemSelected(item);
	}
    

    // get result from other actity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode ==1 && resultCode == RESULT_OK)
		{
			number_of_comments++;
			theid.id_for_master=number_of_comments;
			System.out.println("zhuyuanzhang"+theid.id_for_master);
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						insert(theid);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}).start();
            // wait for 0.5 seconds to finish the thread
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		
	  }}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    // insert object into elasticsearch server	
	public void insert(ID id ) throws IllegalStateException, IOException{
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/lab111/1");
		StringEntity stringentity = null;

		try {
			stringentity = new StringEntity(gson.toJson(id));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.setHeader("Accept", "application/json");

		httpPost.setEntity(stringentity);

		HttpResponse response = null;
		
		try {
			System.out.println("wocao2");
			response = httpclient.execute(httpPost);
			System.out.println("wocao1");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	
		
	
     
		
		         
	}
    
	// get id object from the server
	public ID get_id()
	{   try{
		ID id_toReturn ;// this is ID object from server
		HttpGet httpget = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/lab111/1/?pretty=1");
		httpget.addHeader("Accept","application/json");
		
			HttpResponse response = httpclient.execute(httpget);
			
			String json = getEntityContent(response);
			
			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<ID>>(){}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<ID> esResponse = gson.fromJson(json, elasticSearchResponseType);
			// We get the recipe from it!
			id_toReturn = esResponse.getSource();
			
			System.out.println(id_toReturn.id_for_master+"dddddd");
			
			return id_toReturn;
			//System.out.println(recipe.toString());
			//httpget.releaseConnection();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
	
	/**
	 * get the http response and return json string
	 */
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:"+json);
		return json;
	}
	// testing query,use this function
	public  void get_comments(String url)
	{
	HttpPost httpPost= new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
	//HttpGet  httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/emouse/_search?pretty=1");
	Gson gson1 = new Gson();
	try {
		String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":-200,\"lte\":1000,\"boost\":2.0}}}}";
		//String query1 = "{\"query\":{\"query_string\":{\"default_field\":\"master_ID\",\"query\":15}}}";
		StringEntity entity = new StringEntity(query);
		httpPost.setHeader("Accept","application/json");
		httpPost.setEntity(entity);
		HttpResponse response = httpclient.execute(httpPost);
		String json1 = getEntityContent(response);
		System.out.println(json1+"holy");
		Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>(){}.getType();
		ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(json1, elasticSearchSearchResponseType);
		System.out.println();
		for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
			Comments comms = r.getSource();
			
			// check weath the comment if already in the arraylist, if not then add it in there
			int flag=0;
			for (Comments com : comment_array)
			{  // turn on the flag if object is already inside the arary
				if(com.master_ID==comms.master_ID)
				{
					flag =1 ;
					break;
				}
			}
			// if flag not turned on then add the object into the arraylsit
			if (flag==0)
			{
				comment_array.add(comms);
			}
			
		}
				
		} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		System.out.println("client exe");
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("IO exe");
		e.printStackTrace();
	}
	
		
	}



	@Override
	public void onRefreshStarted(View view) {
		// TODO Auto-generated method stub
		new AsyncTask<Void,Void,Void>()
		{   ElasticSearchSearchResponse<Comments> ESresponse;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				System.out.println("okay123");
				get_comments("get from server");
				//comment_array.add(new Comments(0,0,new DateTime(),"Title:How are you","Things around you that called life are build by people no smarter than you,eveyone can achieve great result if they work hard",current_location,current_location.getLongitude(),current_location.getLatitude()));		
				System.out.println("okay1234");
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
				mPullToRefreshLayout.setRefreshComplete();
				super.onPostExecute(result);
				// loop through  the response
				
				
			}

			
			
		}.execute();
		
		
	}
	
	
	
	
	
	

}
