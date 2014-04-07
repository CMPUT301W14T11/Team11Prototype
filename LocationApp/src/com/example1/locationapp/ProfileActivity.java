package com.example1.locationapp;

import com.example1.locationapp.R;

import Controller.LocalFileLoder;
import Model.CommentUser;
import Model.UserModel;
import android.app.ActionBar;
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
import android.widget.Toast;

/**
 * for user owner or other user to watch the information of the user.
 * @author zuo2
 */

public class ProfileActivity extends Activity {
	private CommentUser goduser;
	private TextView textview1,textview2,textview3,textview4,textview5,textview6,textview7;
	private ImageView imageview;
	private Bitmap bitmap;
	private String uuid="";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_profile);
		ActionBar bar = getActionBar();
		bar.setTitle("My Profile");
		Intent intent = getIntent();
		goduser= (CommentUser) intent.getSerializableExtra("name");
		textview1 = (TextView) findViewById(R.id.textView188);
		textview2 = (TextView) findViewById(R.id.textView388);
		textview3 = (TextView) findViewById(R.id.textView288);
		textview4 = (TextView) findViewById(R.id.textView588);
		textview5 = (TextView) findViewById(R.id.textView488);
		textview6 = (TextView) findViewById(R.id.textView688);
		textview7 = (TextView) findViewById(R.id.textView8);
		uuid = goduser.getUudi();
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
	
	

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 * @param menu
	 * @return true
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	

	/**
	 * Handle action bar item clicks here. The action bar will
	 * automatically handle clicks on the Home/Up button, so long
	 * as you specify a parent activity in AndroidManifest.xml.
	 * @param item
	 * @return boolean
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			LocalFileLoder loader = new LocalFileLoder(getApplicationContext());
			UserModel user = loader.loadFromFile();
			String username = user.getUser_name();
			if(username.equals(textview1.getText()))
			{
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), NewProfileActivity.class);
				intent.putExtra("username",user.getUser_name());
				intent.putExtra("uuid",uuid);
				intent.putExtra("object", goduser);
				startActivity(intent);
				ProfileActivity.this.finish();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Can't edit other people's profile", Toast.LENGTH_SHORT).show();
			}
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
