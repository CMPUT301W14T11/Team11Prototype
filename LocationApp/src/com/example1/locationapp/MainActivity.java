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

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import Controller.CommentController;
import Controller.IDController;
import Model.Comments;
import Model.IDModel;
import Model.UserModel;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends Activity implements OnRefreshListener,CommentController,IDController {
    ListView listview ;
    ArrayList<Comments> comment_array;
    cutadapter adapter ;
    IDModel total_comments;
    Gson gson;
    HttpClient httpclient;
    IDModel theid;
    int number_of_comments;
    Location current_location;
    GPSTracker gps ;
    Context content;
    ProgressDialog dialog1;
    Button load_button;
    double radius= 0.1;
    UserModel user1;
    // request code for startActivityForResult are:
    // "1" for enterCommentActivity, so it will bring you to comment entering activity
    private PullToRefreshLayout mPullToRefreshLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//load_button = (Button ) findViewById(R.id.refresh_button);
		content = this;
		dialog1 = new ProgressDialog(content);
		user1 = new UserModel();
		
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
		System.out.println("lat="+current_location.getLatitude());
		httpclient= new DefaultHttpClient();
		
		comment_array = new ArrayList<Comments>();
		listview = (ListView) findViewById(R.id.ptr_listview1);
		
		total_comments = new IDModel(0);
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
		// set up footer for the listview
		View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footlayout, null, false);
		footerView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("footer clicked");
				new AsyncTask<Void, Void, Void>()
				{
                    
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						dialog1.dismiss();
					}

					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						dialog1.setTitle("Loading cause your internet is too slow!");
						dialog1.show();
					}

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						System.out.println("unrun");
						get_comments("get some comments man!");
						System.out.println("runned");
						return null;
					}
					
				}.execute();
				adapter.notifyDataSetChanged();
			}
		});
		listview.addFooterView(footerView);
		
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
    

    // get result from other activity
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
	@Override
	public void insert(IDModel id ) throws IllegalStateException, IOException{
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
	@Override
	public IDModel get_id()
	{   try{
		IDModel id_toReturn ;// this is ID object from server
		HttpGet httpget = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/lab111/1/?pretty=1");
		httpget.addHeader("Accept","application/json");
		
			HttpResponse response = httpclient.execute(httpget);
			
			String json = getEntityContent(response);
			
			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<IDModel>>(){}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<IDModel> esResponse = gson.fromJson(json, elasticSearchResponseType);
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
	// get_comments(String url),get master comments form server, this is sorting by location
	// you dont;t have to use Sting url, you can define your own url in the get_comments(String url) function
	@Override
	public  void get_comments(String url)
	{
	HttpPost httpPost= new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
	//HttpGet  httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/emouse/_search?pretty=1");
	Gson gson1 = new Gson();
	try {
		double lat_gte = current_location.getLatitude()-radius;
		double lat_lte = current_location.getLatitude()+radius;
		double lon_gte = current_location.getLongitude()-radius;
		double lon_lte = current_location.getLongitude()+radius;
		String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":"+lat_gte+",\"lte\":"+ lat_lte +",\"boost\":0.0} }}}";
		//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":"+lat_gte+",\"lte\":"+ lat_lte +",\"boost\":0.0},\"lon\":{\"gte\":"+lon_gte+",\"lte\":"+ lon_lte +",\"boost\":0.0} }}}";
		//String query1 = "{\"query\":{\"query_string\":{\"default_field\":\"master_ID\",\"query\":15}}}";
		//String query_location ="{\"query\": {\"geo_shape\": {\"location\": {\"shape\": {\"type\": \"envelope\",\"coordinates\": [[13, 53],[14, 52]]}}}}}";
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
				radius= radius+0.01;
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
	@Override
	public void insertMaster(Comments com) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	

}
