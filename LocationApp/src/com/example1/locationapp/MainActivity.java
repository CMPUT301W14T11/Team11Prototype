package com.example1.locationapp;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import Controller.Compara;
import Controller.DateSort;
import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import InternetConnection.ConnectToInternet;
import InternetConnection.ElasticSearchResponse;
import InternetConnection.ElasticSearchSearchResponse;
import Model.CommentUser;
import Model.Comments;
import Model.SubCommentModel;
import Model.UserModel;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * this is Main display page for the app which shows main comments of the
 * application and user may edit comments, change location
 * @author zuo2
 */
public class MainActivity extends Activity implements OnRefreshListener {
	private CommentUser someuser;
	private int flag_location;
	private ListView listview;
	private ArrayList<Comments> comment_array;
	private CutAdapter adapter;
	private Gson gson;
	private HttpClient httpclient;
	private Location current_location;
	private GPSTracker gps;
	private Context content;
	private ProgressDialog dialog1;
	private double radius = 0.1;
	private int index = 0;
	private LocalFileSaver fileSaver = new LocalFileSaver(this);
	private LocalFileLoder fileLoader = new LocalFileLoder(this);
	private UserModel user;
	private ConnectToInternet connects = new ConnectToInternet();
	private PullToRefreshLayout mPullToRefreshLayout;
	
	
	
	/**
	 * onCreate method.
	 * Once the activity is created, first set the content view, and initialize ActionBar and a Spinner for sort options.
	 * Then, load the content of the Comment and adapt to the ListView with the Comment replies and set the click listener for 
	 * sub comments and edit comments choice (location change).
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar bar = getActionBar();
		
		Intent intent = getIntent();
		final ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		/**
		 * checking the location
		 */
		if (wifi.isConnected() || mobile.isConnected()) {

		} else {
			Toast.makeText(this, "No Internet!", Toast.LENGTH_LONG).show();

		}

		content = this;
		dialog1 = new ProgressDialog(content);
		
		
		try {
			// getting location when app starts, so we can search the database
			// for location, will add use location later
			gps = new GPSTracker(this);
			if (gps.canGetLocation) {
				current_location = gps.getLocation();
			} else { 
				// if gps is not turned on then , ask user to turn it on
				gps.showSettingsAlert();
			}
		} catch (NullPointerException e) {
			Toast.makeText(content, "Can't get location please check gps",
					Toast.LENGTH_SHORT).show();
		}


		String name = intent.getStringExtra("name");
		
		if (name.equals(""))
			bar.setTitle("Welcome, Guest");
		else
			bar.setTitle("Welcome, " + name);
		
		fileLoader.Exist();
		if (!fileLoader.exist()) {
			user = new UserModel();
			user.setUser_name(name);
			fileSaver.saveInFile(user);
		} else {
			user = new UserModel();
			user = fileLoader.loadFromFile();
			user.setUser_name(name);
			fileSaver.saveInFile(user);
		}

		user = fileLoader.loadFromFile();
		fileSaver.saveInFile(user);
		user = fileLoader.loadFromFile();
		fileSaver.saveInFile(user);
		httpclient = new DefaultHttpClient();
		comment_array = new ArrayList<Comments>();
		listview = (ListView) findViewById(R.id.ptr_listview1);
		gson = new Gson();
		adapter = new CutAdapter(MainActivity.this, R.layout.listlayout,
				comment_array);
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);


		ActionBarPullToRefresh.from(this)
	
				.allChildrenArePullable()
		
				.listener(this)
	
				.setup(mPullToRefreshLayout);
		View footerView = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.footlayout, null, false);
		footerView.setOnClickListener(new OnClickListener() {

			
			@Override
			public void onClick(View v) {
				
				new AsyncTask<Void, Void, Void>() {

					
					@Override
					protected void onPostExecute(Void result) {

						super.onPostExecute(result);
						//get_comments("get some comments man!");
						dialog1.dismiss();
						adapter.notifyDataSetChanged();
					}

					
					@Override
					protected void onPreExecute() {

						super.onPreExecute();
						dialog1.setTitle("Loading... Please wait");
						dialog1.show();
					}

					
					@Override
					protected Void doInBackground(Void... params) {
						
						get_comments("get some comments man!");
						radius = radius + 0.1;
						return null;
					}

				}.execute();

			}
		});
		listview.addFooterView(footerView);
		
		
		/**
		 * Sub comments button click listener
		 * After you click the Comments you want to see, will jump to sub comments page with 
		 * corresponding main activity
		*/
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				int getID = comment_array.get(arg2).getMaster_ID();
				Intent intent1 = new Intent();
				intent1.putExtra("masterID", getID);
				intent1.putExtra("longitude", current_location.getLongitude());
				intent1.putExtra("latitude", current_location.getLatitude());
				intent1.setClass(MainActivity.this, SubCommetsRead.class);
				MainActivity.this.startActivity(intent1);
				

			}
		});
		
		
		/**
		 * Long click listener for comments
		 * When user want to change location of the comments or edit comments
		 * User may long click the comments on the list.
		*/
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				AlertDialog.Builder builder = new AlertDialog.Builder(content);
				String items[] = { "Edit Comment", "Add Tags","View profile" };
				builder.setItems(items, new DialogInterface.OnClickListener() {

					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							String CheckName = user.getUser_name();
							final Comments SelectedComment = comment_array.get(arg2);
							if(SelectedComment.getUserName().equals(CheckName))
							{   
								final Dialog dialogui = new Dialog(content);
								dialogui.setContentView(R.layout.dialogui);
								dialogui.setTitle("Edit my comment");
								dialogui.show();
								flag_location=0;
								final TextView locationview = (TextView) dialogui.findViewById(R.id.textView1);
								final TextView locationview2 = (TextView) dialogui.findViewById(R.id.textView2);
								Button Changebutton = (Button) dialogui.findViewById(R.id.button1);
								Button Locationbutton = (Button) dialogui.findViewById(R.id.button2);
								final EditText titleedit = (EditText) dialogui.findViewById(R.id.editText1);
								final EditText subjectedit = (EditText) dialogui.findViewById(R.id.editText2);
								Locationbutton.setOnClickListener(new OnClickListener() {
									
							
									@Override
									public void onClick(View v) {
										locationview.setText("Enter Latitude");
										locationview2.setText("Enter Longitude");
										titleedit.setHint("Lat");
										subjectedit.setHint("Lon");
										flag_location = 1;
									}
								});
								
								Changebutton.setOnClickListener(new OnClickListener() {
									
								
									@Override
									public void onClick(View v) {
										if(flag_location==0)
										{	
										comment_array.get(arg2).setThe_comment(titleedit.getText().toString());
										comment_array.get(arg2).setSubject_comment(subjectedit.getText().toString());
										adapter.notifyDataSetChanged();
										
										}
										if(flag_location==1)
										{
											double lat = Double.parseDouble(titleedit.getText().toString()); 
											double lon = Double.parseDouble(subjectedit.getText().toString());	
											comment_array.get(arg2).setLat(lat);
											comment_array.get(arg2).setLon(lon);
											
										}
										new AsyncTask<Void,Void,Void>()
										{

									
											@Override
											protected Void doInBackground(
													Void... params) {
												HttpClient httpclient = new DefaultHttpClient();
												HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/"+comment_array.get(arg2).getMaster_ID());
												try {
													StringEntity data = new StringEntity(gson.toJson(comment_array.get(arg2)));
													httpPost.setEntity(data);
													httpPost.setHeader("Accept", "application/json");
													httpclient.execute(httpPost);
				
												} catch (UnsupportedEncodingException e) {

													e.printStackTrace();
												} catch (ClientProtocolException e) {

													e.printStackTrace();
												} catch (IOException e) {

													e.printStackTrace();
												}
												return null;
											}
											
										}.execute();
										
										Toast.makeText(content,"Comment has changed",Toast.LENGTH_SHORT).show();
										dialogui.dismiss();
									}
								});
							}
							else
							{
								Toast.makeText(content,"You can only edit your own comment",Toast.LENGTH_SHORT).show();
							}
							break;
						case 1:
							Intent intent = new Intent();
							intent.setClass(MainActivity.this,
									TagActivity.class);
							
							index = arg2;
							startActivityForResult(intent, 1258);
							break;
						case 2:
							final String name = comment_array.get(arg2).getUserName();
							new AsyncTask<Void, Void, Void>()
							{
								
								@Override
								protected void onPostExecute(Void result) {
									super.onPostExecute(result);
									if (flag==0)
									{
										AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
										builder.setTitle("This user does not have profile");
										builder.setMessage("Please check back later");
										builder.setCancelable(true);
										builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
										
											
											@Override
											public void onClick(DialogInterface dialog, int which) {
												dialog.cancel();
											}
										});
										AlertDialog adialog = builder.create();
										adialog.show();
									}
								}
								int flag = 0;
								
								
								@Override
								protected Void doInBackground(Void... params) {
									try{
										Gson gson = new Gson();
										
										HttpPost httppost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/profile/_search?pretty=1");
										String query_profile = "{\"query\":{\"match\":{\"name\":\""+name+"\"}}}";
										StringEntity entity;
										entity = new StringEntity(query_profile);
										httppost.setHeader("Accept", "application/json");
										httppost.setEntity(entity);
										HttpResponse response = httpclient.execute(httppost);
										String json1 = connects.getEntityContent(response);
										Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<CommentUser>>() {
										}.getType();
										ElasticSearchSearchResponse<CommentUser> esResponse = gson.fromJson(
												json1, elasticSearchSearchResponseType);
										for(ElasticSearchResponse<CommentUser> r : esResponse.getHits())
										{   // get some result, then flag is 1
											someuser = r.getSource();
											flag=1;
											break;
										}
										
										
										if (flag==1)
										{
											Intent intent_profile = new Intent();
											intent_profile.setClass(content, ProfileActivity.class);
											intent_profile.putExtra("name",someuser);
											startActivityForResult(intent_profile, 939);
										}
										}
										 catch (ClientProtocolException e) {
											e.printStackTrace();
										} catch (IOException e) {
											e.printStackTrace();
										}
										return null;
									
								}
								
							}.execute();
							break;
						}

					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				return false;
			}
		});
		listview.setAdapter(adapter);
		new AsyncTask<Void, Void, Void>() {

			
			@Override
			protected Void doInBackground(Void... params) {

				get_comments("get some comments");
				return null;
			}

			
			@Override
			protected void onPostExecute(Void result) {

				super.onPostExecute(result);
				adapter.notifyDataSetChanged();
			}

		}.execute();
	}
	
	

	/**
	 * An option window jump out allows user to select
	 * whether edit comments, add tags as well as view profile
	 * @param item -- selected item
	*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
		case R.id.item1:
			user = fileLoader.loadFromFile();
			if (user.getUser_name().equals("")) {
				Toast.makeText(MainActivity.this,
						"You don not have right to post a comment",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent();
				intent.putExtra("latitude", current_location.getLatitude());
				intent.putExtra("longitude", current_location.getLongitude());
				intent.setClass(MainActivity.this, EnterCommentsActivity.class);
				startActivityForResult(intent, 1);
				break;
			}

			break;

		case R.id.item2:
			// this is to start change location activity
			// request code is 7
			user = fileLoader.loadFromFile();
			if (user.getUser_name().equals("")) {
				Toast.makeText(MainActivity.this,
						"You don not have right to change location",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				final Dialog dialogui = new Dialog(content);
				dialogui.setContentView(R.layout.dialogui);
				dialogui.setTitle("Change Location");
				dialogui.show();
				
				final TextView locationview = (TextView) dialogui.findViewById(R.id.textView1);
				final TextView locationview2 = (TextView) dialogui.findViewById(R.id.textView2);
				Button Changebutton = (Button) dialogui.findViewById(R.id.button1);
				Button Locationbutton = (Button) dialogui.findViewById(R.id.button2);
				final EditText titleedit = (EditText) dialogui.findViewById(R.id.editText1);
				final EditText subjectedit = (EditText) dialogui.findViewById(R.id.editText2);
				locationview.setText("Enter Latitude");
				locationview2.setText("Enter Longitude");
				titleedit.setHint("Lat");
				subjectedit.setHint("Lon");
				Changebutton.setVisibility(View.INVISIBLE);
				Locationbutton.setText("Change Location");
				Locationbutton.setOnClickListener(new OnClickListener() {
					/**
					 * Change location of the comments
					*/
					@Override
					public void onClick(View v) {
						current_location.setLatitude(Double.parseDouble(titleedit.getText().toString()));
						current_location.setLongitude(Double.parseDouble(subjectedit.getText().toString()));
						dialogui.dismiss();
						Toast.makeText(getApplicationContext(), "Location changed", Toast.LENGTH_SHORT).show();
					}
				});
			}	
			break;
		case R.id.item3:
			sortByDate();
			break;
		case R.id.item4:
			sortByPicture();
			break;
			
		case R.id.item5:
			user = fileLoader.loadFromFile();
			if (user.getUser_name().equals("")) {
				Toast.makeText(MainActivity.this,
						"You don not have right to use this feature",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent3 = new Intent(MainActivity.this, Favourite.class);
				intent3.putExtra("code", 0);
				intent3.putExtra("latitude", current_location.getLatitude());
				intent3.putExtra("longitude", current_location.getLongitude());
				startActivityForResult(intent3, 9);
			}	
			break;
		
		case R.id.item6:
			user = fileLoader.loadFromFile();
			if (user.getUser_name().equals("")) {
				Toast.makeText(MainActivity.this,
						"You don not have right to use this feature",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent6 = new Intent(MainActivity.this, Favourite.class);
				intent6.putExtra("code", 1);
				intent6.putExtra("latitude", current_location.getLatitude());
				intent6.putExtra("longitude", current_location.getLongitude());
				startActivity(intent6);
			}
			break;
			
		case R.id.item7:
			Intent intent7 = new Intent(MainActivity.this, MainPage.class);
			intent7.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			user.setUser_name("");
			fileSaver.saveInFile(user);
			startActivity(intent7);
			break;
		case R.id.item99:
			final String UserName = user.getUser_name();
			

			if (user.getUser_name().equals("")) {
				Toast.makeText(MainActivity.this,
						"You don not have right to add profile",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				new AsyncTask<Void,Void,Void>()
				{
					
					/**
					 * Get profile out the comments authors
					 * @return null
					*/
					@Override
					protected Void doInBackground(Void... params) {
						try{
						Gson gson = new Gson();
						
						HttpPost httppost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/profile/_search?pretty=1");
						String query_profile = "{\"query\":{\"match\":{\"name\":\""+UserName+"\"}}}";
						StringEntity entity;
						entity = new StringEntity(query_profile);
						httppost.setHeader("Accept", "application/json");
						httppost.setEntity(entity);
						HttpResponse response = httpclient.execute(httppost);
						String json1 = connects.getEntityContent(response);
						Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<CommentUser>>() {
						}.getType();
						ElasticSearchSearchResponse<CommentUser> esResponse = gson.fromJson(
								json1, elasticSearchSearchResponseType);
						int flag = 0;
						
						for(ElasticSearchResponse<CommentUser> r : esResponse.getHits())
						{   // get some result, then flag is 1
							someuser = r.getSource();
							flag=1;
							break;
						}
						
						if (flag==0)
						{
						
							//no result, result code 12345
							Intent intent = new Intent();
							intent.putExtra("username",UserName);
							intent.setClass(content, NewProfileActivity.class);
							startActivityForResult(intent, 12345);
						
						}
						if (flag==1)
						{
							// have result , result code 939
							Intent intent_profile = new Intent();
							intent_profile.setClass(content, ProfileActivity.class);
							intent_profile.putExtra("name",someuser);
							startActivityForResult(intent_profile, 939);
						}
						
						}
						 catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
					
				}.execute();			
			}
			
			break;
		case R.id.menu_item_search:
			
            onSearchRequested();
            return true;

		}

		return super.onOptionsItemSelected(item);
	}



	/**
	 * Get new array after modified the comments
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return null
	*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:

			break;
		case 7:

			if (resultCode == RESULT_OK) {
				Toast.makeText(content, "Your Location is changed!",
						Toast.LENGTH_LONG).show();
				double lat = data.getDoubleExtra("lat",
						current_location.getLatitude());

				double lon = data.getDoubleExtra("lon",
						current_location.getLongitude());

				current_location.setLatitude(lat);
				current_location.setLongitude(lon);		
				comment_array.clear();
				adapter.notifyDataSetChanged();
				get_comments("get comments using new locaiton");
				adapter.notifyDataSetChanged();
			}
			break;
		case 1258:
			if (resultCode == RESULT_OK) {
				comment_array.get(index).setTagsList(
						data.getStringArrayListExtra("TagArray"));
				new AsyncTask<Void, Void, Void>()
				{
					
					@Override
					protected Void doInBackground(Void... params) {
						try
						{
						HttpPut httpPost = new HttpPut("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/"+comment_array.get(index).getMaster_ID()+"/");
						StringEntity stringentity = null;
						stringentity = new StringEntity(gson.toJson(comment_array.get(index)));
						httpPost.setHeader("Accept", "application/json");
						httpPost.setEntity(stringentity);
						httpclient.execute(httpPost);
						}
						catch(Exception e)
						{
							
						}
						return null;
					}
					
				}.execute();
				}
		}

	}
	
	
	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);
		searchView.setSubmitButtonEnabled(true);
		
		return true;
	}


	
	/**
	 * download form server , to get comment object
	 * @param url
	 */
	public void get_comments(String url) {
		HttpPost httpPost = new HttpPost(
				"http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
		Gson gson1 = new Gson();
		try {
			double lat_gte = current_location.getLatitude() - radius;
			double lat_lte = current_location.getLatitude() + radius;
			double lon_gte = current_location.getLongitude() - radius;
			double lon_lte = current_location.getLongitude() + radius;
			String query_range2 = "{\"query\":{\"bool\" : {\"must\" : {\"range\" : {\"lat\" : { \"gte\" : "
					+ lat_gte
					+ ", \"lte\" : "
					+ lat_lte
					+ ",\"boost\":0.0 }}},\"must\":{\"match\":{\"sub_comments_ID\":0}},\"must\" : {\"range\" : {\"lon\" : { \"gte\" : "
					+ lon_gte
					+ ", \"lte\" : "
					+ lon_lte
					+ ", \"boost\":0.0}}}}}}";

			StringEntity entity = new StringEntity(query_range2);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpPost);
			String json1 = connects.getEntityContent(response);
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>() {
			}.getType();
			ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(
					json1, elasticSearchSearchResponseType);
            // new version of array sorting
			
			comment_array.clear();
			for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
					Comments comments = r.getSource();
					float DistanceResult [] = new float[10];
					Location.distanceBetween(current_location.getLatitude(),current_location.getLongitude(),comments.getLat(),comments.getLon(),DistanceResult);
					comments.setDistance(DistanceResult[0]);
					comment_array.add(comments);			
			}
			Collections.sort(comment_array, new Compara());	
			user = fileLoader.loadFromFile();		
			user.getComment().clear();
			for (int i = 0 ; i<comment_array.size(); i++)
			{
				Comments comment1 = null;
				ArrayList<Comments> helper = new ArrayList<Comments>();
				SubCommentModel scm = new SubCommentModel(comment1);
				comment_array.get(i).setSubComment(scm.get_comments(helper, comment_array.get(i).getMaster_ID(), httpclient));				
				user.addComment(comment_array.get(i));
			}
			fileSaver.saveInFile(user);
		

		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (NullPointerException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {


		}
	}
	

	
	/**
	 * to refresh the view
	 * @param view
	 */
	@Override
	public void onRefreshStarted(View v) {

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {

				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {

				get_comments("get from server");
				radius = radius + 0.1;
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {

				adapter.notifyDataSetChanged();
				mPullToRefreshLayout.setRefreshComplete();
				super.onPostExecute(result);
				
			}

		}.execute();

	}
	
	

	/**
	 * sorting this by picture, and show only picture in the comment
	 */
	public void sortByPicture()
	{
		
		ArrayList<Comments> nonPictureComment = new ArrayList<Comments>();
		Iterator<Comments> iter = comment_array.iterator();
		while (iter.hasNext()) {
		    Comments comment = iter.next();
		    if (comment.getImage_encode()==null)
		    {    
		    	nonPictureComment.add(comment);
		    	iter.remove();
		    }
		    
		}
		comment_array.addAll(nonPictureComment);
		adapter.notifyDataSetChanged();
	}
	
	

	/**
	 * sort by date, using master id to sort, then add to comment
	 */
	public void sortByDate() {
		Collections.sort(comment_array,new DateSort());
		adapter.notifyDataSetChanged();
	}
}
