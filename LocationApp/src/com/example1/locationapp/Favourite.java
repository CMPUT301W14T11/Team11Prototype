package com.example1.locationapp;



import java.util.ArrayList;

import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import Model.FavouriteComment;
import Model.FavouriteModel;
import Model.UserModel;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Favourite extends Activity
{
    private Context content;
	private UserModel user;
	private LocalFileLoder fl =  new LocalFileLoder(this);
	private LocalFileSaver fs =  new LocalFileSaver(this);
	private ArrayList<FavouriteModel> favourite;
    private ArrayList<FavouriteComment> matchlist;
    private FavouriteComment fc;
	//private ArrayList<String> matchlist = new ArrayList<String>();
	//private ArrayAdapter<String> adapter;
	private CustomAdapter adapter;
	private ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		content=this;
		setContentView(R.layout.activity_favourite);
		matchlist= new ArrayList<FavouriteComment>();
		
		populateListView();
	}
    /** 
     * load the comment and sub-comment from the local saving file, and the result
     * into the list of view.
     */
	private void populateListView()
	{
		user = new UserModel();
		user = fl.loadFromFile();
		
		
		
		list = (ListView) findViewById(R.id.favouritelist);

		
		
		String username = user.getUser_name();
		favourite = user.getFaviourte();
		int len = favourite.size();
		
		for (int i = 0; i<len; i++)
		{
			if (username.equals(favourite.get(i).getUsername()))
			{
				matchlist.add(favourite.get(i).getComment());
				//matchlist.add(favourite.get(i).getUsername());
				
			}
		}

		adapter = new CustomAdapter(this, R.layout.listlayout, matchlist);
		list.setAdapter(adapter);
		
	}
/**
 * is working for when you click the list of view.
 */
	private void registerClickCallback()
	{
		ListView list = (ListView) findViewById(R.id.favouritelist);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> paret, View viewClicked, int position,
					long id)
			{

				// TODO Auto-generated method stub
				TextView textView = (TextView) viewClicked;
				String massage = "You Clicked #" + position + ", which is string:" + textView.getText().toString();
				Toast.makeText(Favourite.this, massage, Toast.LENGTH_LONG).show();
				
			}
		});
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favourite, menu);
		return true;
	}

}
