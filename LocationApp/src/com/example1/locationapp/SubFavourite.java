package com.example1.locationapp;

import java.util.ArrayList;

import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import Model.FavouriteComment;
import Model.FavouriteModel;
import Model.UserModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class SubFavourite extends Activity
{
	private UserModel user;
	private LocalFileLoder fl = new LocalFileLoder(this);
	private LocalFileSaver fs = new LocalFileSaver(this);
	private ArrayList<FavouriteModel> favourite;
	private ArrayList<FavouriteComment> matchlist;
	private FavouriteComment fc;
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
		user = fl.loadFromFile();

		list = (ListView) findViewById(R.id.favouritelist);

		FavouriteModel fm = null;
		for (int i=0; i<user.getFaviourte().size(); i++)
		{
			if (user.getFaviourte().get(i).getID() == id)
			{
				fm = user.getFaviourte().get(i);
			}
		}
		
		Log.v("output>>>>>>>>>>>>>>",""+fm.getSubComment().size());
		for (int i=0; i<fm.getSubComment().size(); i++)
		{
			matchlist.add(fm.getSubComment().get(i));
		}
		
		adapter = new CustomAdapter(this, R.layout.listlayout, matchlist);
		list.setAdapter(adapter);

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub_favourite, menu);
		return true;
	}

}
