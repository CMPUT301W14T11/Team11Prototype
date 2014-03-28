package com.example1.locationapp;

import java.util.ArrayList;

import Model.Comments;
import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchActivity extends Activity {
    cutadapter adapter2;
    ArrayList<Comments> comment_list;
    ListView listview2;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
        System.out.println("set on content");
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		listview2 =(ListView) findViewById(R.id.search_list);
		comment_list = new ArrayList<Comments>();
		adapter2 = new cutadapter(SearchActivity.this,R.layout.listlayout, comment_list);
		listview2.setAdapter(adapter2);
		//ArrayList<String> list = new ArrayList<String>();
		//ArrayAdapter<String> st_ada = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		
		handleIntent(getIntent());
	}

	public void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		// call detail activity for clicked entry
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			System.out.println("query is:"+query);
			
			
			doSearch(query);
			
		}
		//listview2.setAdapter(adapter);
	}

	private void doSearch(String queryStr) {
		// get a Cursor, prepare the ListAdapter
		// and set it
		listview2 =(ListView) findViewById(R.id.search_list);
		comment_list = new ArrayList<Comments>();
		adapter2 = new cutadapter(SearchActivity.this,R.layout.listlayout, comment_list);
		listview2.setAdapter(adapter2);
		System.out.println("search is on");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_search,
					container, false);
			return rootView;
		}
	}

}
