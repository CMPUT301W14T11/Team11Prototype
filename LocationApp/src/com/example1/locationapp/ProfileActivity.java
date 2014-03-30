package com.example1.locationapp;

import Model.CommentUser;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
    CommentUser goduser;
    TextView textview1,textview2,textview3,textview4,textview5,textview6,textview7;
    ImageView imageview;
    Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_profile);

		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		Intent intent = getIntent();
		goduser= (CommentUser) intent.getSerializableExtra("name");
		textview1 = (TextView) findViewById(R.id.textView188);
		textview2 = (TextView) findViewById(R.id.textView388);
		textview3 = (TextView) findViewById(R.id.textView288);
		textview4 = (TextView) findViewById(R.id.textView588);
		textview5 = (TextView) findViewById(R.id.textView488);
		textview6 = (TextView) findViewById(R.id.textView688);
		textview7 = (TextView) findViewById(R.id.textView8);
		textview1.setText(goduser.getName());
		textview2.setText(goduser.getAge());
		textview3.setText(goduser.getFacebook());
		textview4.setText(goduser.getLinkedIn());
		textview5.setText(goduser.getPhone());
		textview6.setText(goduser.getEmail());
		textview7.setText(goduser.getBio());
		imageview = (ImageView) findViewById(R.id.imageimage11);
		if(goduser.getImageEncode()!=null)
		{
		byte[] imageAsBytes = Base64.decode(goduser.getImageEncode().getBytes(),Base64.NO_WRAP);
    	bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    	imageview.setImageBitmap(bitmap);
    	bitmap=null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_profile,
					container, false);
			return rootView;
		}
	}

}
