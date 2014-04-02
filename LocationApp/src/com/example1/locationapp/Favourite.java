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

import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import Model.Comments;
import Model.FavouriteComment;
import Model.FavouriteModel;
import Model.UserModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	private Context content;
	private UserModel user;
	private LocalFileLoder fl = new LocalFileLoder(this);
	private LocalFileSaver fs = new LocalFileSaver(this);
	private ArrayList<FavouriteModel> favourite;
	private ArrayList<FavouriteComment> matchlist;
	private FavouriteComment fc;
	private CustomAdapter adapter;
	private ListView list;
	private HttpClient httpclient;
	private int code;
	private double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		content = this;
		setContentView(R.layout.activity_favourite);
		matchlist = new ArrayList<FavouriteComment>();
		user = new UserModel();
		user = fl.loadFromFile();
		httpclient = new DefaultHttpClient();
		Intent intent = getIntent();
		code = intent.getIntExtra("code", 0);
		latitude = intent.getDoubleExtra("latitude", 0);
		longitude = intent.getDoubleExtra("longitude", 0);
		
		ActionBar bar = getActionBar();
		if (code == 0)
			bar.setTitle("Faviourite");
		else
			bar.setTitle("Personal Saving");
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				get_comments("get some comments man!");
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
	 * is working for when you click the list of view.
	 */
	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.favouritelist);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paret, View viewClicked,
					int position, long id) {

				TextView textView = (TextView) viewClicked;
				String massage = "You Clicked #" + position
						+ ", which is string:" + textView.getText().toString();
				Toast.makeText(Favourite.this, massage, Toast.LENGTH_LONG)
						.show();

			}
		});

	}
	
	public void get_comments(String url) {
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
					String json1 = getEntityContent(response);
					
					Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>() {
					}.getType();
					ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(
							json1, elasticSearchSearchResponseType);
					// <<<<<<< HEAD\
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
							user.getFaviourte().get(i).getComment().setDistance(getDistance(user.getFaviourte().get(i).getComment().getLatitude(), user.getFaviourte().get(i).getComment().getLongitude()));
							user.getFaviourte().get(i).getComment().setImage(comment.get(i1).getImage_encode());
							user.getFaviourte().get(i).getComment().setText(comment.get(i1).getSubject_comment());
							user.getFaviourte().get(i).getComment().setTitle(comment.get(i1).getThe_comment());
							num++;
						}
										
						FavouriteComment fc = new FavouriteComment();
						fc.setDistance(getDistance(user.getFaviourte().get(i).getSubComment().get(i1).getLatitude(), user.getFaviourte().get(i).getSubComment().get(i1).getLongitude()));
						fc.setImage(comment.get(i1).getImage_encode());
						fc.setText(comment.get(i1).getSubject_comment());
						fc.setTitle(comment.get(i1).getThe_comment());
						fc.setUserName(comment.get(i1).getUserName());
						user.getFaviourte().get(i).addSubComment(fc);
						
						fs.saveInFile(user);
					}
				}
			}
			
		} catch (ClientProtocolException e) {
			
			System.out.println("client exe");
			e.printStackTrace();
		} catch (IOException e) {
			
			System.out.println("IO exe");
			e.printStackTrace();
		}
	}
	
	public double getDistance(double commentLaiude, double commentLongitude)
	{
		Location current_location = null;
		float DistanceResult [] = new float[10];
		current_location.distanceBetween(latitude,longitude,commentLaiude,commentLongitude,DistanceResult);
		return DistanceResult[0];
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favourite, menu);
		return true;
	}

}
