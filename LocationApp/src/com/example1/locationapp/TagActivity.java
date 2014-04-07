package com.example1.locationapp;

import java.util.ArrayList;

import com.example1.locationapp.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This acitvity for user add Tag in the and searching by tag
 */
public class TagActivity extends Activity {
    ArrayList<String> taglist;
    EditText editText;
    Button enterbutton , donebutton;
    TextView textview;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		taglist = new ArrayList<String>();
		editText= (EditText) findViewById(R.id.tagedit);
		enterbutton= (Button) findViewById(R.id.tagbutton1);
		donebutton= (Button) findViewById(R.id.tagbutton2);
		textview = (TextView) findViewById(R.id.tagtextView1);
	}
	
	

	/**
	 * use this function to add tags to the comments
	 * @param v
	 */
	public void enter(View v)
	{
		
		String tag = editText.getText().toString();
		if(tag.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Tags can't be empty!", Toast.LENGTH_SHORT).show();
		}
		else
		{   String text1="";
			taglist.add(tag);
			
			for(String text : taglist)
			{
				text1=text1+text+", ";
			}
			textview.setText("Tags:"+text1);
			editText.setText(null);
		}
	}
	

	
	/**
	 * go back to MainActivity from tags
	 * @param v
	 */
	public void done(View v)
	{
		Intent intent = new Intent();
		intent.putExtra("TagArray", taglist);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 * @param menu
	 * @return boolean
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tag, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_tag, container,
					false);
			return rootView;
		}
	}

}
