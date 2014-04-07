package com.example1.locationapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
/**
 * this class is the base of the mainActivity
 * It has the frame action bar
 * when you pull the action bar the list view will refreshed
 * @author zuo2
 * 
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Fragment sampleFragment = getSampleFragment();
		if (sampleFragment != null) {
			getFragmentManager().beginTransaction()
					.replace(android.R.id.content, sampleFragment).commit();
		}

	}

	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.base_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	
	
	
	
	
	
	
	
	@Override
	/**
	 *  This method is for menu. This menu items will appear in all
	 *  activities extends this class. I have use this menus to navigate
	 *  between activities.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_listview:
			Toast.makeText(this, "Pull to Refresh in ListView",
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
	
	
	
	
	/**
	 *This method will override by child class. Then base class can get the
	 *fragment
	 *@return null
	 */
	protected Fragment getSampleFragment() {
		return null;
	}

}