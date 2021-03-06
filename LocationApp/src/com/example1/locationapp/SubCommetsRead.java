package com.example1.locationapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Controller.BitmapConverter;
import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import InternetConnection.ConnectToInternet;
import InternetConnection.ElasticSearchResponse;
import InternetConnection.ElasticSearchSearchResponse;
import Model.CommentUser;
import Model.Comments;
import Model.FavouriteComment;
import Model.FavouriteModel;
import Model.IDModel;
import Model.SubCommentModel;
import Model.UserModel;
import Sort.SubCommentSort;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * this class is to control the subFavouriteComment-comments part
 * It will find subFavouriteComment-comment of the master comment
 * @author qyu4
 */
public class SubCommetsRead extends Activity {
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private ListView listViewSubComment;
	private EditText editText;
	private Button button1;
	private CutAdapter ListAdapter;
	private ArrayList<Comments> comment_list;
	private Bitmap bitmap;
	private int number;
	private Context content;
	private Location location;
	private GPSTracker gps;
	private HttpClient httpclient;
	private double longitude;
	private double latitude;
	private int subCoId = 1;
	private Gson gson = new Gson();
	private CommentUser someuser;
	private double radius = 0.01;
	private IDModel id_obj;
	private int ServerID;
	private LocalFileLoder fileLoader = new LocalFileLoder(this);
	private LocalFileSaver fileSaver = new LocalFileSaver(this);
	private UserModel user;
	private Comments comment1;
	private String subCommentsTitle;
	private int replyFloor=0;
	private ConnectToInternet connect = new ConnectToInternet();
	private SubCommentModel subModel = new SubCommentModel(
			comment1);
	private int flag_location=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_commets_read);
		listViewSubComment = (ListView) findViewById(R.id.listViewSubComments);
		editText = (EditText) findViewById(R.id.editTextSubmitSubComments);
		button1 = (Button) findViewById(R.id.buttonSaveSubComments);
		button1.setText("Send");
		comment_list = new ArrayList<Comments>();
		
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(false);
		httpclient = new DefaultHttpClient();
		Intent intent = getIntent();
		number = intent.getIntExtra("masterID", 0);
		id_obj = new IDModel(0);
		gps = new GPSTracker(this);
		ListAdapter = new CutAdapter(SubCommetsRead.this, R.layout.listlayout,comment_list);
		content = this;
		ConnectivityManager cm =
		        (ConnectivityManager)content.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		user = fileLoader.loadFromFile();
		
		if (user.getUser_name().equals(""))
			bar.setTitle("Welcome, Guest");
		else
			bar.setTitle("Welcome, " + user.getUser_name());
		if (isConnected)
		{
			
		new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... params) {
				
				comment_list =subModel.get_comments(comment_list, number,httpclient);
				
				if (comment_list.size() == 0)
				{
					user = fileLoader.loadFromFile();
					for (int i = 0; i < user.getComment().size(); i++)
					{
						if (user.getComment().get(i).getMaster_ID() == number)
						{
							comment_list = user.getComment().get(i).getSubComment();
						}
					}
				}
				subCoId=comment_list.size()+1;
				return null;
			}

			
			@Override
			protected void onPostExecute(Void result) {
				
				super.onPostExecute(result);
				for (int i = 0; i<comment_list.size(); i++)
				{
					float DistanceResult [] = new float[10];
					Location.distanceBetween(location.getLatitude(),location.getLongitude(),comment_list.get(i).getLat(),comment_list.get(i).getLon(),DistanceResult);
					comment_list.get(i).setDistance(DistanceResult[0]);	
				}
				Collections.sort(comment_list, new SubCommentSort());
				ListAdapter.notifyDataSetChanged();
			}

		}.execute();

		}
		else
		{
			setDisconnectComment();
		}
	
		if (gps.canGetLocation) {
			location = gps.getLocation();
			gps.stopUsingGPS();
		} else {
			gps.showSettingsAlert();
		}

		location.setLatitude(intent.getDoubleExtra("latitude",0));
		location.setLongitude(intent.getDoubleExtra("longitude",0));		
		longitude = location.getLongitude();
		latitude = location.getLatitude();
		View footerView = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.footlayout, null, false);

		listViewSubComment.addFooterView(footerView);
		editText.setHint("reply to 1");
		listViewSubComment.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				replyFloor = arg2;
				editText.setHint("reply to "+(replyFloor+1));
			}
		});
		listViewSubComment.setOnItemLongClickListener(new OnItemLongClickListener() {

		
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(content);
				String items[] = { "Edit Comment","View profile" };
				builder.setItems(items, new DialogInterface.OnClickListener() {

				
					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							String CheckName = user.getUser_name();
							final Comments SelectedComment = comment_list.get(position);
							if(SelectedComment.getUserName().equals(CheckName))
							{  
							
								final Dialog dialogui = new Dialog(content);
								dialogui.setContentView(R.layout.dialogui);
								dialogui.setTitle("Edit my comment");
								dialogui.show();
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
										if(flag_location==1)
										{
											double lat = Double.parseDouble(titleedit.getText().toString()); 
											double lon = Double.parseDouble(subjectedit.getText().toString());	
											comment_list.get(position).setLat(lat);
											comment_list.get(position).setLon(lon);
											flag_location = 0;
											
										}
										comment_list.get(position).setThe_comment(titleedit.getText().toString());
										comment_list.get(position).setSubject_comment(subjectedit.getText().toString());
										ListAdapter.notifyDataSetChanged();
										dialogui.dismiss();
										
										new AsyncTask<Void,Void,Void>()
										{

											@Override
											protected Void doInBackground(
													Void... params) {
												HttpClient httpclient = new DefaultHttpClient();
												HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/"+comment_list.get(position).getSub_ID());
												try {
													StringEntity data = new StringEntity(gson.toJson(comment_list.get(position)));
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
									}
								});
							}
							else
							{
								Toast.makeText(content,"You can only edit your own comment",Toast.LENGTH_SHORT).show();
							}
							break;
						case 1:
							final String name = comment_list.get(position).getUserName();
							new AsyncTask<Void, Void, Void>()
							{
								
								@Override
								protected void onPostExecute(Void result) {
									super.onPostExecute(result);
									if (flag==0)
									{
										AlertDialog.Builder builder = new AlertDialog.Builder(SubCommetsRead.this);
										builder.setTitle("User has did not create profile");
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
										String json1 = connect.getEntityContent(response);
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
							break;
						}
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				return false;
			}
		});
	
		listViewSubComment.setAdapter(ListAdapter);
		footerView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				editText.setHint("reply to 1");
				ConnectivityManager cm =
				        (ConnectivityManager)content.getSystemService(Context.CONNECTIVITY_SERVICE);
				 
				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				boolean isConnected = activeNetwork != null &&
				                      activeNetwork.isConnectedOrConnecting();
				if (isConnected)
				{
					new AsyncTask<Void, Void, Void>() {
						
					
						@Override
						protected Void doInBackground(Void... params) {
							comment_list.clear();					
							comment_list=subModel.get_comments(comment_list, number,httpclient);
							return null;
						}

					
						@Override
						protected void onPostExecute(Void result) {
							
							super.onPostExecute(result);
							for (int i = 0; i<comment_list.size(); i++)
							{
								float DistanceResult [] = new float[10];
								Location.distanceBetween(location.getLatitude(),location.getLongitude(),comment_list.get(i).getLat(),comment_list.get(i).getLon(),DistanceResult);
								comment_list.get(i).setDistance(DistanceResult[0]);	
							}
							Collections.sort(comment_list, new SubCommentSort());
							ListAdapter.notifyDataSetChanged();
						}

					}.execute();
				}
				else
				{
					setDisconnectComment();
				}
			}
		});
		button1.setOnClickListener(new MyButton1Listener());
	}

	
	
	/**
	 * there are two subFavouriteComment-munu list which is using for saving favourite and
	 * another is using for save, it will help us save the comments and
	 * subFavouriteComment-comments in the file.
	 * @param item -- selected item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.fav:
			faviSaving(0);
			break;
		case R.id.save:
			faviSaving(1);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	

	/**
	 * Save the Faviourite comments into the local file
	 * @param code -- used to determine between faviourite and saving
	 */
	public void faviSaving(int code)
	{
		user = new UserModel();
		user = fileLoader.loadFromFile();
		boolean saved=false;
		if (user.getUser_name().equals(""))
		{
			Toast.makeText(SubCommetsRead.this,
					"Guest cannot add this comment",
					Toast.LENGTH_SHORT).show();
		}
		else
		{
			saved = saved(code, saved);
			if (saved == true)
			{
				Toast.makeText(SubCommetsRead.this,
						"You already saved this comment",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				FavouriteComment favouriteComment = new FavouriteComment();
				ArrayList<FavouriteComment> subcomment = new ArrayList<FavouriteComment>();
				favouriteComment.setText(comment_list.get(0).getSubject_comment());
				favouriteComment.setTitle(comment_list.get(0).getThe_comment());
				favouriteComment.setImage(comment_list.get(0).getImage_encode());
				favouriteComment.setDistance(comment_list.get(0).getDistance());
				favouriteComment.setUserName(comment_list.get(0).getUserName());
				favouriteComment.setLocation(location.getLatitude(), location.getLongitude());
				favouriteComment.setID(number);
				subcomment.add(favouriteComment);
				for (int i =1;i<comment_list.size();i++)
				{
					FavouriteComment subFavouriteComment = new FavouriteComment();
					subFavouriteComment.setText(comment_list.get(i).getSubject_comment());
					subFavouriteComment.setTitle(comment_list.get(i).getThe_comment());
					subFavouriteComment.setImage(comment_list.get(i).getImage_encode());
					subFavouriteComment.setDistance(comment_list.get(i).getDistance());
					subFavouriteComment.setUserName(comment_list.get(i).getUserName());
					subFavouriteComment.setLocation(location.getLatitude(), location.getLongitude());
					subcomment.add(subFavouriteComment);
				}

				FavouriteModel favouriteModel = new FavouriteModel(user.getUser_name(), favouriteComment,
						subcomment);
				favouriteModel.setID(number);
				favouriteModel.setCode(code);
				user.addFaviourte(favouriteModel);
				fileSaver.saveInFile(user);
			}
			
		}
	}
	
	

	/**
	 * this is code to save comments to local
	 * @param code -- 0 for favourite 1 for personal saving
	 * @param saved -- true -- it has been saved, false else
	 * @return
	 * true -- it has been saved, false else
	 */
	private boolean saved(int code, boolean saved) {
		for (int i = 0; i < user.getFaviourte().size(); i++) {
			if (user.getFaviourte().get(i).getID() == number
					&& user.getFaviourte().get(i).getUsername()
							.equals(user.getUser_name())
					&& user.getFaviourte().get(i).getCode() == code) {
				saved = true;
			}
		}
		return saved;
	}
	
	

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sub_commets_read, menu);
		return true;
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK)
		{
			switch(requestCode)
			{
				case 2828:
					String file = data.getStringExtra("image");
					String file2 = data.getStringExtra("choseimage");
					if (file != null) {
						bitmap = BitmapFactory.decodeFile(file);
					} else {
						bitmap = BitmapFactory.decodeFile(file2);
					}
					if(bitmap!=null)
					{
						Toast.makeText(SubCommetsRead.this, "Picture added", Toast.LENGTH_SHORT).show();
					}
					break;
				}			
		}
	}
	
	

	/**
	 * clikc this button to add picture
	 * @param view
	 */
	public void picture(View view)
	{
		Intent intent = new Intent(this, ChoseImageActivity.class);
		startActivityForResult(intent,2828);
	}
	class MyButton1Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			user = fileLoader.loadFromFile();
			if (user.getUser_name().equals(""))
			{
				Toast.makeText(getBaseContext(),
						"Guest cannot add comments!!!",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				String title = editText.getText().toString();
				radius = radius + 0.01;
				if ("".equals(title)) {

					Toast.makeText(getBaseContext(),
							"Title is empty! add some words please!",
							Toast.LENGTH_SHORT).show();

				} else {
					new AsyncTask<Void, Void, Void>() {
						ProgressDialog dialog1 = new ProgressDialog(content);

						
						@Override
						protected void onPreExecute() {
							
							dialog1.setTitle("Loading... Please wait");
							dialog1.show();
							super.onPreExecute();
							new AsyncTask<Void, Void, Void>() {

								
								@Override
								protected Void doInBackground(Void... params) {
									
									ServerID = connect.get_id(content);
									ServerID++;
									return null;
								}

							}.execute();

						}
						
						
						@Override
						protected Void doInBackground(Void... params) {
							
							if (bitmap == null) {
								subCommentsTitle=subCoId+". Relpy to ";
								user = fileLoader.loadFromFile();
								final Comments new_comment = new Comments(0,
										number, subCoId,ServerID, (subCommentsTitle+" "+(replyFloor+1)).toString(), editText.getText()
												.toString(), new Date(),
										longitude, latitude, user.getUser_name());
								subModel.insertMaster(new_comment, ServerID);
								subCoId++;
								replyFloor =0;
							} else {
								subCommentsTitle=subCoId+". Relpy to ";
								JsonElement encode_image = new BitmapConverter().serialize(bitmap, null, null);
								final Comments new_comment = new Comments(0,
										number, subCoId, ServerID, (subCommentsTitle+" "+(replyFloor+1)), editText.getText()
												.toString(), new Date(),
										longitude, latitude, encode_image,
										user.getUser_name());
								subModel.insertMaster(new_comment, ServerID);
								subCoId++;
								replyFloor =0;
								
							}	
							return null;
						}
						
						
						@Override
						protected void onPostExecute(Void result) {
							
							super.onPostExecute(result);
							new AsyncTask<Void, Void, Void>() {

								@Override
								protected Void doInBackground(Void... params) {
									
									id_obj.setId_for_master(ServerID);
									try {
										connect.insert(id_obj,content);
									} catch (IllegalStateException e) {
										
										e.printStackTrace();
									} catch (IOException e) {
										
										e.printStackTrace();
									}
									return null;
								}
							}.execute();
                        dialog1.dismiss();
                        bitmap=null;
                        editText.setText("");
						}
					}.execute();
					setResult(RESULT_OK);
				}
			}
			
		}
	}
	
	

	/**
	 * use this function to load from file, and show comments in listview
	 */
	public void setDisconnectComment()
	{

			user = fileLoader.loadFromFile();
			for (int i = 0; i < user.getComment().size(); i++)
			{
				if (user.getComment().get(i).getMaster_ID() == number)
				{
					comment_list = user.getComment().get(i).getSubComment();
					
				}
			}
			
			Collections.sort(comment_list, new SubCommentSort());
			subCoId=comment_list.size()+1;
			ListAdapter = new CutAdapter(SubCommetsRead.this, R.layout.listlayout,comment_list);
			listViewSubComment.setAdapter(ListAdapter);
			ListAdapter.notifyDataSetChanged();
	}

	

	/**
	 * go back to MainActivty from SUBcomment
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent();
		intent.setClass(SubCommetsRead.this, MainActivity.class);	
	}
}
