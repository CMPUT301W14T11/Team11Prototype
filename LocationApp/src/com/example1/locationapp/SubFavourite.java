package com.example1.locationapp;

import java.util.ArrayList;

import Controller.LocalFileLoder;
import Model.FavouriteComment;
import Model.FavouriteModel;
import Model.UserModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

/**
 * This activity for user's off line's sub-comments.
 * @author zuo2
 */
public class SubFavourite extends Activity
{
	private UserModel user;
	private LocalFileLoder fileLoader = new LocalFileLoder(this);
	private ArrayList<FavouriteComment> matchlist;
	private CustomAdapter adapter;
	private ListView list;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_favourite);
		matchlist = new ArrayList<FavouriteComment>();
		Intent intent = getIntent();
		id = intent.getIntExtra("masterID", 0);
		populateListView();
	}

	
	
	
	
	
	
	
	
	
	/**
	 * load the comment and sub-comment from the local saving file, and the
	 * result into the list of view.
	 */
	private void populateListView() {
		user = new UserModel();
		user = fileLoader.loadFromFile();

		list = (ListView) findViewById(R.id.listViewSubComments);

		FavouriteModel favouriteModel = null;
		for (int i=0; i<user.getFaviourte().size(); i++)
		{
			if (user.getFaviourte().get(i).getID() == id)
			{
				favouriteModel = user.getFaviourte().get(i);
			}
		}
		
		for (int i=0; i<favouriteModel.getSubComment().size(); i++)
		{
			matchlist.add(favouriteModel.getSubComment().get(i));
		}
		
		adapter = new CustomAdapter(this, R.layout.listlayout, matchlist);
		list.setAdapter(adapter);

	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 * @param menu
	 * @return boolean
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.sub_favourite, menu);
		return true;
	}

}
