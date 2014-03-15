package com.example1.locationapp;



import java.util.ArrayList;

import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import Model.Comments;
import Model.FavouriteModel;
import Model.UserModel;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
	private ArrayList<Comments> matchlist;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		content=this;
		setContentView(R.layout.activity_favourite);
		matchlist= new ArrayList<Comments>();
		populateListView();// put the result into the view list. 
		//registerClickCallback(); // to able click the view list.
	}

	private void populateListView()
	{
		user = fl.loadFromFile();
		
		
		String username = user.getUser_name();
		favourite = user.getFaviourte();
		int len = favourite.size();
			
		for (int i = 0; i<len; i++)
		{
			if (username.equals(favourite.get(i).getUsername()))
			{
				matchlist.add(favourite.get(i).getComment());
			}
		}
		

		ArrayAdapter<Comments> adapter = new ArrayAdapter<Comments>(
				this,
				R.layout.listlayout,
				matchlist);
		
		ListView list = (ListView) findViewById(R.id.favouritelist);

		list.setAdapter(adapter);

		
	}

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
