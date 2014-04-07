package com.example1.locationapp;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import InternetConnection.ConnectToInternet;
import InternetConnection.ElasticSearchResponse;
import InternetConnection.ElasticSearchSearchResponse;
import Model.Comments;
import Model.FavouriteComment;
import Model.FavouriteModel;
import Model.SaveFavourite;
import Model.UserModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example1.locationapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Favorite is for user saving their favorite comment when you phone is off line 
 * you can also view the favorite part. 
 * in the favorite comments it also contain the sub-comments.
 * @author zuo2
 *
 */
public class Favourite extends Activity {
	private UserModel user;
	private LocalFileLoder fl = new LocalFileLoder(this);
	private LocalFileSaver fs = new LocalFileSaver(this);
	private ArrayList<FavouriteModel> favourite;
	private ArrayList<FavouriteComment> matchlist;
	private CustomAdapter adapter;
	private ListView list;
	private HttpClient httpclient;
	private int code;
	private Location current_location;
	private ConnectToInternet connects = new ConnectToInternet();
	private SaveFavourite save = new SaveFavourite();
	private Context content = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);
		matchlist = new ArrayList<FavouriteComment>();
		user = new UserModel();
		user = fl.loadFromFile();
		httpclient = new DefaultHttpClient();
		GPSTracker gps = new GPSTracker(Favourite.this);
		if(gps.canGetLocation)
		{
			current_location = gps.getLocation();
		}
		
		Intent intent = getIntent();
		code = intent.getIntExtra("code", 0);		
		ActionBar bar = getActionBar();
		if (code == 0)
			bar.setTitle("Faviourite");
		else
			bar.setTitle("Personal Saving");
		
		ConnectivityManager cm =
		        (ConnectivityManager)content.getSystemService(Context.CONNECTIVITY_SERVICE);		
		
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		if(isConnected)
		{
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {		
					get_comments(user,code);
					return null;
				}

				@Override
				protected void onPostExecute(Void result)
				{
					populateListView();				
					list.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {

							int getID = matchlist.get(arg2).getID();
							Intent intent1 = new Intent();
							intent1.putExtra("masterID", getID);
							intent1.setClass(Favourite.this, SubFavourite.class);
							Favourite.this.startActivity(intent1);
						}
					});
					super.onPostExecute(result);
				}
			}.execute();
		}
		else
		{
			populateListView();		
			list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {

					int getID = matchlist.get(arg2).getID();
					Intent intent1 = new Intent();
					intent1.putExtra("masterID", getID);
					intent1.setClass(Favourite.this, SubFavourite.class);
					Favourite.this.startActivity(intent1);
				}
			});
		}
	}

	
	
	
	
	
	
	
	
	/**
	 * load the comment and sub-comment from the local saving file, and the
	 * result into the list of view.
	 */
	private void populateListView() {
		
		list = (ListView) findViewById(R.id.favouritelist);
		user = new UserModel();
		user = fl.loadFromFile();
		String username = user.getUser_name();
		favourite = user.getFaviourte();
		int len = favourite.size();

		for (int i = 0; i < len; i++) {
			if (username.equals(favourite.get(i).getUsername()) && favourite.get(i).getCode() == code) {
				matchlist.add(favourite.get(i).getComment());
			}
		}
		adapter = new CustomAdapter(this, R.layout.listlayout, matchlist);
		list.setAdapter(adapter);

	}

	
	
	
	
	
	
	
	
	/**
	 * download comment from the server
	 * @param user
	 * @param code
	 */
	public void get_comments(UserModel user, int code) {
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
		Gson gson1 = new Gson();
		
		try {
			
			for (int i=0; i<user.getFaviourte().size(); i++)
			{
				if (user.getUser_name().equals(user.getFaviourte().get(i).getUsername()) && user.getFaviourte().get(i).getCode()== code)
				{
					String query_range2 = "{\"query\":{\"bool\":{\"must\":{\"match\":{\"master_ID\":"
							+ user.getFaviourte().get(i).getID() + "}}} }}";
					StringEntity entity = new StringEntity(query_range2);
					httpPost.setHeader("Accept", "application/json");
					httpPost.setEntity(entity);
					HttpResponse response = httpclient.execute(httpPost);
					String json1 = connects.getEntityContent(response);
					
					Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>() {
					}.getType();
					ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(
							json1, elasticSearchSearchResponseType);
					int num = 0;
					user.getFaviourte().get(i).clean();
					ArrayList<Comments> comment = new ArrayList<Comments>();
					for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
						Comments comms = r.getSource();
						comment.add(comms);
					}				
					//Using for sort the subcomments
					Collections.sort(comment, new CommentComparator());
					
					for (int i1=0; i1<comment.size(); i1++)
					{
						if (num==0)
						{
							user.getFaviourte().get(i).getComment().setDistance(save.getDistance(user.getFaviourte().get(i).getComment().getLatitude(), user.getFaviourte().get(i).getComment().getLongitude(), current_location));
							user.getFaviourte().get(i).getComment().setImage(comment.get(i1).getImage_encode());
							user.getFaviourte().get(i).getComment().setText(comment.get(i1).getSubject_comment());
							user.getFaviourte().get(i).getComment().setTitle(comment.get(i1).getThe_comment());
							num++;
						}									
						FavouriteComment fc = fileC(comment, i1);
						user.getFaviourte().get(i).addSubComment(fc);				
						fs.saveInFile(user);
					}
				}
			}
			
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	
	
	
	
	
	
	
	
	/**
	 * set the information for Favourite Comments.
	 * @param comment
	 * @param i1
	 * @return fc
	 */
	private FavouriteComment fileC(ArrayList<Comments> comment, int i1) {
		FavouriteComment fc = new FavouriteComment();
		fc.setDistance(save.getDistance(comment.get(i1).getLat(),
				comment.get(i1).getLon(), current_location));
		fc.setImage(comment.get(i1).getImage_encode());
		fc.setText(comment.get(i1).getSubject_comment());
		fc.setTitle(comment.get(i1).getThe_comment());
		fc.setUserName(comment.get(i1).getUserName());
		return fc;
	}

	
	
	
	
	
	
	
	
	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 * @param menu
	 * @return true
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.favourite, menu);
		return true;
	}

}
