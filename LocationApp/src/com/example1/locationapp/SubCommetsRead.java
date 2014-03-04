package com.example1.locationapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SubCommetsRead extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_commets_read);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub_commets_read, menu);
		return true;
	}

}
