package com.example1.locationapp;

import java.io.File;

import com.example1.locationapp.R;

import Controller.LocalFileLoder;
import Model.UserModel;
import android.app.ActionBar;
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
/**
 * main page is giving two options for user to use to application.
 * One is with user name; one is without;
 * @author qyu4
 *
 */
public class MainPage extends Activity {

	private UserModel user;
	private Button newUser;
	private Button guest;
	private Location current_location;
	private GPSTracker gps;
	private Context content;
	private String name;
	private LocalFileLoder fl = new LocalFileLoder(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//hide the action bar
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		// Determin if the file exist
		fl.Exist();
		if (fl.exist()) {

			user = fl.loadFromFile();
			if (!user.getUser_name().equals("")) {
				Intent intent = new Intent(MainPage.this, MainActivity.class);
				intent.putExtra("name", user.getUser_name());

				startActivity(intent);
			}
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		newUser = (Button) findViewById(R.id.button1);
		guest = (Button) findViewById(R.id.guest);

		guest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				name = "";
				Intent intent = new Intent(MainPage.this, MainActivity.class);
				intent.putExtra("name", name);
				startActivity(intent);
			}

		});

		newUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainPage.this, NewUserActivity.class);
				startActivity(intent);
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
