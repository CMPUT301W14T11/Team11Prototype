package com.example1.locationapp;

import Model.UserModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainPage extends Activity
{

	private UserModel user;
	private Button newUser;
	private Button guest;
	private Location current_location;
    private GPSTracker gps ;
    private Context content;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		
		newUser = (Button) findViewById(R.id.button1);
		guest = (Button) findViewById(R.id.guest);
		
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
		
		
		guest.setOnClickListener(new OnClickListener() {
		@Override
        public void onClick(View arg0) {
            Intent intent = new Intent(MainPage.this,
                    MainActivity.class);
            intent.putExtra("location", current_location);
            startActivity(intent);
		}
		
		});
		
		newUser.setOnClickListener(new OnClickListener() {
			@Override
	        public void onClick(View arg0) {
	            Intent intent = new Intent(MainPage.this,
	                    NewUserActivity.class);
	            startActivity(intent);
			}
			
			});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
