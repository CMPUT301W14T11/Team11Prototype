package com.example1.locationapp;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import Controller.BitmapConverter;
import Controller.LocalFileLoder;
import InternetConnection.ConnectToInternet;
import Model.Comments;
import Model.CommentsModel;
import Model.IDModel;
import Model.UserModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonElement;
/**
 * EnterCommentActivity class takes user 
 * input as master comment which will be shown
 * in the Main activity
 * @author qyu4
 *
 */
public class EnterCommentsActivity extends Activity implements 
		Serializable {
	private EnterCommentsActivityProduct enterCommentsActivityProduct = new EnterCommentsActivityProduct();
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private EditText title_edit, subject_edit;
	private Location location;
	private GPSTracker gps;
	private ImageView imageview;
	private int number;
	private Context content;
	private Bitmap bitmap;
	private IDModel id_obj;
	private LocalFileLoder fl = new LocalFileLoder(this);
	private UserModel user;
	private CommentsModel commentsModel = new CommentsModel();
	private ConnectToInternet connect = new ConnectToInternet();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_comments);		
		ActionBar bar = getActionBar();
		bar.setTitle("New Comment");
		// Show the Up button in the action bar.
		id_obj = new IDModel(0);
		imageview = (ImageView) findViewById(R.id.imageView1);
		content = this;
		title_edit = (EditText) findViewById(R.id.editText1);
		subject_edit = (EditText) findViewById(R.id.editText2);
		// get current location of the comments

		gps = new GPSTracker(this);
		if (gps.canGetLocation) {
			location = gps.getLocation();
			gps.stopUsingGPS();
		}
		Intent intent = getIntent();
		location.setLatitude(intent.getDoubleExtra("latitude",0));
		location.setLongitude(intent.getDoubleExtra("longitude",0));
		number = 0;
		LocalFileLoder loader = new LocalFileLoder(this);
		user = loader.loadFromFile();
	}
	
	
	
	
	
	
	
	
	
	/**
	 * click to buton to send the comments to the cloud
	 * @param view
	 */
	// send comment to server
	public void send(View view) {
		String title = title_edit.getText().toString();
		
		if ("".equals(title)) {

			Toast.makeText(getBaseContext(),
					"Title is empty! add some words please!",
					Toast.LENGTH_SHORT).show();

		}
		String subject = subject_edit.getText().toString();
		// do not delete this comment, it might be usefull
		InternetChecker check = new InternetChecker();
		if(!check.connected(getApplicationContext()))
		{
			Toast.makeText(getApplicationContext(), "No internet right now, Comement will be send later",Toast.LENGTH_LONG).show();
			SharedPreferences sharedPref = content.getSharedPreferences("mydata", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("title", title);
			editor.putString("subject", subject);
			editor.putString("name", user.getUser_name());
			
			if(bitmap!=null)
			{	
				BitmapConverter ImageConvert = new BitmapConverter();
				JsonElement encode_image =ImageConvert.serialize(bitmap, null, null);
				editor.putString("image",encode_image.toString());
			}
			
			editor.commit();
			finish();
		}
		
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {

				super.onPreExecute();
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						number = connect .get_id(content);
						number++;
						return null;
					}

				}.execute();
			}

			@Override
			protected Void doInBackground(Void... params) {

				if (bitmap == null) {
					user = fl.loadFromFile();
					final Comments new_comment = new Comments(0, number, 0, 0,
							title_edit.getText().toString(), subject_edit
									.getText().toString(), new Date(),
							location.getLongitude(),location.getLatitude(), user.getUser_name());
					commentsModel.insertMaster(new_comment,number);
				} else {
					user = fl.loadFromFile();
					BitmapConverter ImageConvert = new BitmapConverter();
					JsonElement encode_image =ImageConvert.serialize(bitmap, null, null);
					final Comments new_comment = new Comments(0, number, 0, 0,
							title_edit.getText().toString(), subject_edit
									.getText().toString(), new Date(),
							location.getLongitude(),location.getLatitude(), encode_image,
							user.getUser_name());
					commentsModel.insertMaster(new_comment,number);

				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {

				super.onPostExecute(result);
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						id_obj.setId_for_master(number);
						try {
							connect .insert(id_obj, content);
						} catch (IllegalStateException e) {

							e.printStackTrace();
						} catch (IOException e) {

							e.printStackTrace();
						}
						return null;
					}

				}.execute();

			}

		}.execute();

		setResult(RESULT_OK);
		finish();
	}

	
	
	
	
	
	
	
	
	/**
	 * set result for Activity result
	 */
	@Override
	public void onBackPressed() {

		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}

	
	
	
	
	
	
	
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.enter_comments, menu);
		return true;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * This ID represents the Home or Up button. In the case of this
	 * activity, the Up button is shown. Use NavUtils to allow users
	 * to navigate up one level in the application structure. For
	 * more details, see the Navigation pattern on Android Design:
	 * http://developer.android.com/design/patterns/navigation.html#up-vs-back
	 * @param item
	 * @author zuo2
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	
	
	
	
	
	
	
	/**
	 * click to add picture, go to image chosser activity
	 * @param view
	 */
	// chose picture request for picture is 5
	public void addPicture(View view) {
		//orginnal take picture
		Intent intent = new Intent(this, ChoseImageActivity.class);
		startActivityForResult(intent, 5);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			String file = data.getStringExtra("image");
			String file2 = data.getStringExtra("choseimage");
			if (file != null) {
				bitmap = BitmapFactory.decodeFile(file);
			} else {
				bitmap = BitmapFactory.decodeFile(file2);
			}

			imageview.setImageBitmap(bitmap);

		}

	}
}
