package com.example1.locationapp;

import java.util.ArrayList;

import InternetConnection.LoadFromServer;
import Model.Comments;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example1.locationapp.R;

/**
 * This activity for user search the comment by time.
 * @author zuo2
 */
public class SearchActivity extends Activity {
    private CutAdapter adapter2;
    private ArrayList<Comments> comment_list;
    private ListView listview2;
    private String query;
    private LoadFromServer load = new LoadFromServer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_search);
		ActionBar bar = getActionBar();
		bar.setTitle("Search Result");
		handleIntent(getIntent());
	}
	
	

	/**
	 * this function is used to call handIntent
	 * @param intent
	 */
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}
	
	

	/**
	 * get intent form MainActiviy
	 * @param intent
	 */
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
			doSearch(query);
		}
	}
	
	

	/**
	 * do seach from the server and put the result into listviw
	 * @param queryStr
	 */
	private void doSearch(String queryStr) {
		listview2 =(ListView) findViewById(R.id.search_list);
		listview2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int getID = comment_list.get(arg2).getMaster_ID();
				Intent intent1 = new Intent();
				intent1.putExtra("masterID", getID);
				intent1.setClass(SearchActivity.this, SubCommetsRead.class);
				SearchActivity.this.startActivity(intent1);
			}
		});
		comment_list = new ArrayList<Comments>();
		adapter2 = new CutAdapter(SearchActivity.this,R.layout.listlayout, comment_list);
		listview2.setAdapter(adapter2);
		comment_list.clear();
		new AsyncTask<Void,Void,Void>() {
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				adapter2.notifyDataSetChanged();
			}
			@Override
			protected Void doInBackground(Void... params) {
				String query_range2 = "{\"query\":{\"bool\":{\"must\":{\"terms\":{\"TagsList\":["+"\""+query+"\""+"],minimum_match:1}}}}}";
				comment_list=load.search(comment_list,query_range2);
				return null;
			}
			}.execute();
	}
	
	

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 * @param menu
	 * @return true
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	

	/**
	 * Handle action bar item clicks here. The action bar will
	 * automatically handle clicks on the Home/Up button, so long
	 * as you specify a parent activity in AndroidManifest.xml.
	 * @param item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
