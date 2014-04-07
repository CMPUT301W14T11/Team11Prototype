package com.example1.locationapp;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * This class manage the functionality in the mainActivity, which shows the
 * listView all comments.
 * @author qyu4
 */
public class ListViewActivity extends BaseActivity {

	@Override
	protected Fragment getSampleFragment() {
		return new SimpleListFragment();
	}

	
	
	
	
	
	
	
	
	
	/**
	 * the SimpleListFragment class is used to control start the listView, pull
	 * to refresh and shows on the activity;
	 */
	public static class SimpleListFragment extends ListFragment implements
			OnRefreshListener {

		int i = 0;

		private PullToRefreshLayout mPullToRefreshLayout;

		ArrayAdapter<String> adapter;

		List<String> list;

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {

			super.onViewCreated(view, savedInstanceState);

			list = new ArrayList<String>();
			int no = 1;
			for (int i = 0; i < 5; i++) {
				list.add("Item No :" + no++);
			}

			super.onViewCreated(view, savedInstanceState);
			ViewGroup viewGroup = (ViewGroup) view;

			// As we're using a ListFragment we create a PullToRefreshLayout manually
			mPullToRefreshLayout = new PullToRefreshLayout(
					viewGroup.getContext());

			// We can now setup the PullToRefreshLayout
			ActionBarPullToRefresh
					.from(getActivity())
					// We need to insert the PullToRefreshLayout into the Fragment's ViewGroup
					.insertLayoutInto(viewGroup)
					// Here we mark just the ListView and it's Empty View as pullable
					.theseChildrenArePullable(android.R.id.list,
							android.R.id.empty).listener(this)
					.setup(mPullToRefreshLayout);

		}

		
		
		
		
		
		
		
		
		
		/**
		 * Set the List Adapter to display the sample items
		 * @param savedInstanceState
		 */
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {

			super.onActivityCreated(savedInstanceState);

			adapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_list_item_1, list);
			setListAdapter(adapter);
			setListShownNoAnimation(true);
		}
		
		
		
		
		
		
		
		
		
		
		/**
		 * to refresh the list view
		 * round progress bar
		 * @param view
		 * @return null
		 */
		@Override
		public void onRefreshStarted(View view) {

			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						Thread.sleep(5000); // 5 seconds
						int itemNo = list.size();
						itemNo++;
						list.add("New Item No :" + itemNo);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);

					adapter.notifyDataSetChanged();
					mPullToRefreshLayout.setRefreshComplete();

				}
			}.execute();

		}

	}

}
