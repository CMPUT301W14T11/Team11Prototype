package com.example1.locationapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import Controller.CommentController;
import Controller.IDController;
import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import Controller.compara;
import Controller.datesort;
import Model.Comments;
import Model.IDModel;
import Model.UserModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**this is Main display page for the app*/
public class MainActivity extends Activity implements OnRefreshListener,CommentController,IDController {

	int location_flag;
    ListView listview ;
    ArrayList<Comments> comment_array, date_comment_array;
    cutadapter adapter ;
    //IDModel total_comments;
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

    public int mode = 0;

    LocalFileSaver fileSaver = new LocalFileSaver(this);
    LocalFileLoder fileLoader = new LocalFileLoder(this);
    private UserModel user;

    // "1" for enterCommentActivity, so it will bring you to comment entering activity
    private PullToRefreshLayout mPullToRefreshLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();

		//theid= new IDModel(0);
		// checking where there is internet or not, if no internet then exit app
		final ConnectivityManager connMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        
        /**checking the location 
         * 
         */
        if(wifi.isConnected() || mobile.isConnected())
        {   System.out.println("connected");
        	//Toast.makeText(this, "Connected to Internet", Toast.LENGTH_LONG).show();
        }
        else
        {   System.out.println("outout");
        	Toast.makeText(this, "No Internet!", Toast.LENGTH_LONG).show();
        	android.os.Process.killProcess(android.os.Process.myPid());
        	
        }
        
        
		
		
		//load_button = (Button ) findViewById(R.id.refresh_button);
		content = this;
		dialog1 = new ProgressDialog(content);
		//current_location=(Location)getIntent().getSerializableExtra("location");
		try{
		// getting location when app starts, so we can search the database for location, will add use location later
		gps = new GPSTracker(this);
		if (gps.canGetLocation)
		{
			current_location = gps.getLocation();
			System.out.println("can get location");
			gps.stopUsingGPS();
		}
		else
		{   // if gps is not turned on then , ask user to turn it on 
			gps.showSettingsAlert();
		}
		}
		catch(NullPointerException e)
		{
		   Toast.makeText(content, "Can't get location please check gps", Toast.LENGTH_SHORT).show();	
		}
		//check user, if name is not null and not file then make a new file
		
		String name = intent.getStringExtra("name");
        
        fileLoader.Exist();
		if (!fileLoader.exist())
		{   user = new UserModel();
		    user.setUser_name(name);
		    //user.setUser_location(current_location);
			fileSaver.saveInFile(user);
		}
		else
		{
			user = new UserModel();
			user = fileLoader.loadFromFile();
			 user.setUser_name(name);
			   // user.setUser_location(current_location);
				fileSaver.saveInFile(user);
		}

		
		user = fileLoader.loadFromFile();
		fileSaver.saveInFile(user);
		user = fileLoader.loadFromFile();
		fileSaver.saveInFile(user);
		// start a httpclient for connecting to server
		//System.out.println("lat="+current_location.getLatitude());
		httpclient= new DefaultHttpClient();
		
		comment_array = new ArrayList<Comments>();
		listview = (ListView) findViewById(R.id.ptr_listview1);
		
		
		gson = new Gson();
		
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
		//listview.addFooterView(footerView);
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
						adapter.notifyDataSetChanged();
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
						radius= radius+0.1;
						System.out.println("runned");
						
						
						
						return null;
					}
					
				}.execute();
				
				
			}
		});
		listview.addFooterView(footerView);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int getID = comment_array.get(arg2).getMaster_ID();
				Intent intent1 = new Intent();
				intent1.putExtra("masterID", getID);
				//intent1.putExtra("main", comment_array.get(arg2));
				intent1.setClass(MainActivity.this, SubCommetsRead.class);
				MainActivity.this.startActivity(intent1);
				//Toast.makeText(MainActivity.this,
		                //listview.getTag(arg2).toString()+"", Toast.LENGTH_SHORT)
		                //.show();
				
			}
		});
		listview.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(content);
				String items[]={"Edit Comment","Add Tags"};
				
				builder.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				return false;
			}
		});
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		listview.setAdapter(adapter);
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				get_comments("get some comments");
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				adapter.notifyDataSetChanged();
			}
			
			
		}.execute();
	}
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId())
		{
		case R.id.item1:
			System.out.println("new is clicked");
			Intent intent = new Intent ();
			intent.putExtra("lat",current_location.getLatitude());
			intent.putExtra("lon", current_location.getLongitude());
			intent.setClass(MainActivity.this, EnterCommentsActivity.class);
			startActivityForResult(intent, 1);
			break;
			
		case R.id.item2:
			// this is to start change location activity
			// request code is 7
			Intent intent2 = new Intent(MainActivity.this,Playtube.class);
			intent2.putExtra("lat", current_location.getLatitude());
			intent2.putExtra("lon", current_location.getLongitude());
			startActivityForResult(intent2, 7); 
			break;
		case R.id.item3:
			sortByDate();
			break;
		case R.id.item5:
			Intent intent3 = new Intent(MainActivity.this,Favourite.class);
			startActivityForResult(intent3, 9);
			break;
			
		case R.id.item7:
			Intent intent7 = new Intent(MainActivity.this,MainPage.class);
			user.setUser_name("");
			//user.setUser_location(null);
			fileSaver.saveInFile(user);
			startActivity(intent7);
			break;
			
			
		}
		
		return super.onOptionsItemSelected(item);
	}
    

    // get result from other activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// when use finish choesing location, get it from playtube_activity
		System.out.println("code is "+requestCode);
		switch (requestCode)
		{
		case 1:
			
			break;
		case 7:
			System.out.println("trytry");
			if(resultCode==RESULT_OK)
			{   Toast.makeText(content, "Your Location is changed!", Toast.LENGTH_LONG).show();
				double lat = data.getDoubleExtra("lat", current_location.getLatitude());
				
				double lon = data.getDoubleExtra("lon",current_location.getLongitude());
				
				System.out.println("current:"+current_location.getLatitude()+"wocao"+current_location.getLongitude());
				current_location.setLatitude(lat);
				current_location.setLongitude(lon);
				System.out.println("new current:"+current_location.getLatitude()+"wocao"+current_location.getLongitude());
				location_flag=1;
				comment_array.clear();
				get_comments("get comments using new locaiton");
				adapter.notifyDataSetChanged();
			}
			break;
		}
		
		
		
		}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    // insert object into elasticsearch server
	/*@Override
	public void insert(IDModel id ) throws IllegalStateException, IOException{
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/lab111/1");
		StringEntity stringentity = null;
        
		try {
			stringentity = new StringEntity(gson.toJson(id));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		catch (RuntimeException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
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
		catch (NullPointerException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		catch (RuntimeException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
	}*/
    
	// get id object from the server
	/*@Override
	public int get_id()
	{   IDModel id_toReturn ;// this is ID object from server
		int id = 0;
		try{
		//IDModel id_toReturn ;// this is ID object from server
		HttpGet httpget = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/lab111/1");
		httpget.addHeader("Accept","application/json");
		
			HttpResponse response = httpclient.execute(httpget);
			
			String json = getEntityContent(response);
			
			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<IDModel>>(){}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<IDModel> esResponse = gson.fromJson(json, elasticSearchResponseType);
			// We get the recipe from it!
			id_toReturn = esResponse.getSource();
			System.out.println();
			System.out.println(id_toReturn.id_for_master+"dddddd");
			id = id_toReturn.id_for_master;
			
			//System.out.println(recipe.toString());
			//httpget.releaseConnection();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      catch (NullPointerException e) {
		// TODO: handle exception
		Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
	    }
		  catch (RuntimeException e) {
		// TODO: handle exception
		Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
	}
	    return id; 
	}*/
	
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
		ArrayList<Comments> lat_object = new ArrayList<Comments>();
		ArrayList<Comments> lon_object = new ArrayList<Comments>();
		double lat_gte = current_location.getLatitude()-radius;
		double lat_lte = current_location.getLatitude()+radius;
		double lon_gte = current_location.getLongitude()-radius;
		double lon_lte = current_location.getLongitude()+radius;
		String query_range = "{\"query\":{\"bool\" : {\"must\" : {\"range\" : {\"lat\" : { \"gte\" : "+lat_gte+", \"lte\" : "+lat_lte+",\"boost\":0.0 }}},\"must\" : {\"range\" : {\"lon\" : { \"gte\" : "+lon_gte+", \"lte\" : "+ lon_lte+", \"boost\":0.0}}}}}}";
		String query_range2 = "{\"query\":{\"bool\" : {\"must\" : {\"range\" : {\"lat\" : { \"gte\" : "+lat_gte+", \"lte\" : "+lat_lte+",\"boost\":0.0 }}},\"must\":{\"match\":{\"sub_comments_ID\":0}},\"must\" : {\"range\" : {\"lon\" : { \"gte\" : "+lon_gte+", \"lte\" : "+ lon_lte+", \"boost\":0.0}}}}}}";
		// these are unused query , this is just for testing
		//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":-200,\"lte\":200,\"boost\":0.0} }}}";
		//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":"+lat_gte+",\"lte\":"+ lat_lte +",\"boost\":0.0} }}}";
		///String query2 = "{\"query\":{\"range\":{\"lon\":{\"gte\":"+lon_gte+",\"lte\":"+ lon_lte +",\"boost\":0.0} }}}";
		//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":-200,\"lte\":200,\"boost\":0.0} }}}";
		//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":"+lat_gte+",\"lte\":"+ lat_lte +",\"boost\":0.0},\"lon\":{\"gte\":"+lon_gte+",\"lte\":"+ lon_lte +",\"boost\":0.0} }}}";
		//String query1 = "{\"query\":{\"query_string\":{\"default_field\":\"master_ID\",\"query\":15}}}";
		//String query_location ="{\"query\": {\"geo_shape\": {\"location\": {\"shape\": {\"type\": \"envelope\",\"coordinates\": [[13, 53],[14, 52]]}}}}}";
		StringEntity entity = new StringEntity(query_range2);
		httpPost.setHeader("Accept","application/json");
		httpPost.setEntity(entity);
		HttpResponse response = httpclient.execute(httpPost);
		String json1 = getEntityContent(response);
		System.out.println(response.getStatusLine().toString()+"status");
		System.out.println(json1+"holy");
		Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>(){}.getType();
		ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(json1, elasticSearchSearchResponseType);

		for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
			Comments comms = r.getSource();

			//check weath the comment if already in the arraylist, if not then add it in there
			int flag=0;
			for (Comments com : comment_array)
			{ // turn on the flag if object is already inside the arary
			if(com.getMaster_ID()==comms.getMaster_ID())
			{
			flag =1 ;
			break;
			}
			}
			// if flag not turned on then add the object into the arraylsit
			if (flag==0)
			{
		      comms.setDistance(current_location.distanceTo(comms.getComment_location()));
			  comment_array.add(comms);
			}
			Collections.sort(comment_array, new compara());
			for(Comments com : comment_array)
			{
			  System.out.println("distance:"+com.getDistance());
			}
		    }
		
		
		}
      catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		System.out.println("client exe");
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("IO exe");
		e.printStackTrace();}
	  catch (NullPointerException e) {
		// TODO: handle exception
		Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
	  }
	catch (RuntimeException e) {
		// TODO: handle exception
		Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
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
				radius= radius+0.1;
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
	
	
public void sortByDate(){
		
		new AsyncTask<Void, Void, Void>()
		{
            
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog1.dismiss();
				Collections.sort(comment_array,new datesort());
				Collections.reverse(comment_array);
				adapter.notifyDataSetChanged();
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				comment_array.clear();
				dialog1.setTitle("Loading cause your internet is too slow!");
				dialog1.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				System.out.println("unrun");
				get_comments("get some comments man!");
				
				radius= radius+0.1;
				System.out.println("runned");
				
				
				
				return null;
			}
			
		}.execute();


	}
@Override
public void insert(IDModel id) throws IllegalStateException, IOException {
	// TODO Auto-generated method stub
	
}
@Override
public int get_id() {
	// TODO Auto-generated method stub
	return 0;
}
	




	
	
	
	
	

}
