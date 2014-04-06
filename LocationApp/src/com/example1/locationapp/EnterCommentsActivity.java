package com.example1.locationapp;

import java.io.File;
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
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
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
	private File photoFile;
	private EditText title_edit, subject_edit;
	private Button post_button, picture_add_button;
	private Location location;
	private GPSTracker gps;
	private ImageView imageview;
	private int number;
	private double longitude;
	private double latitude;
	private Gson gson;
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
		// Show the Up button in the action bar.
		// setupActionBar();
		id_obj = new IDModel(0);
		imageview = (ImageView) findViewById(R.id.imageView1);
		picture_add_button = (Button) findViewById(R.id.button2);
		content = this;

		title_edit = (EditText) findViewById(R.id.editText1);
		subject_edit = (EditText) findViewById(R.id.editText2);
		post_button = (Button) findViewById(R.id.button1);
		// get current location of the comments

		gps = new GPSTracker(this);
		if (gps.canGetLocation) {
			location = gps.getLocation();

			gps.stopUsingGPS();
		}

		Intent intent = getIntent();
		latitude = intent.getDoubleExtra("lat", 0);
		longitude = intent.getDoubleExtra("lon", 0);
		number = 0;
		LocalFileLoder loader = new LocalFileLoder(this);
		user = loader.loadFromFile();
		
		/*try {
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			System.out.println("cclocation" + latitude + "   " + longitude);
		} catch (NullPointerException e) {
			Toast.makeText(content, "Can't get location", Toast.LENGTH_LONG)
					.show();
		}*/
		gson = new Gson();

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
			//SharedPreferences sharedPref = EnterCommentsActivity.this.getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("title", title);
			editor.putString("subject", subject);
			editor.putString("name", user.getUser_name());
			
			//Set<String> the_set = new HashSet<String>();
			//the_set.add(title);
			//the_set.add(subject);
			if(bitmap!=null)
			{	
				BitmapConverter ImageConvert = new BitmapConverter();
				JsonElement encode_image =ImageConvert.serialize(bitmap, null, null);
				editor.putString("image",encode_image.toString());
			}
			
			editor.commit();
			finish();
		}
		
		/*final ConnectivityManager connMgr = (ConnectivityManager) EnterCommentsActivity.this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(!wifi.isConnected() || !mobile.isConnected())
		{
			Toast.makeText(getApplicationContext(), "No internet right now, Comement will be send later",Toast.LENGTH_LONG).show();
			SharedPreferences sharedPref = EnterCommentsActivity.this.getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			Set<String> the_set = new HashSet<String>();
			the_set.add(title);
			the_set.add(subject);
			if(bitmap!=null)
			{
				String encode = convert_image_to_string(bitmap);
				the_set.add(encode);
			}
			editor.putStringSet("comments",the_set);
			editor.commit();
			finish();
		}*/
		
		new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog1 = new ProgressDialog(content);

			@Override
			protected void onPreExecute() {

				// dialog1.setTitle("Loading cause your internet is too slow!");
				// dialog1.show();
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
					System.out.println("this is so cool");
					commentsModel.insertMaster(new_comment,number);
					System.out.println("this is so cool2!" + number);
				} else {
					user = fl.loadFromFile();
					System.out.println("image posted");
					BitmapConverter ImageConvert = new BitmapConverter();
					JsonElement encode_image =ImageConvert.serialize(bitmap, null, null);
					//String encode_image = convert_image_to_string(bitmap);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_comments, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
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
		//originnal take picture
		/*int REQUEST_IMAGE_CAPTURE = 200;
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	            System.out.println("file saving error");
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            
	            startActivityForResult(takePictureIntent,200);
	        }
	    }*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*if (requestCode==200 && resultCode==RESULT_OK)
		{   System.out.println("absulute path is:"+mCurrentPhotoPath);
			bitmap = BitmapFactory.decodeFile(photoFile.getAbsoluteFile().getAbsolutePath());
			
			//Bundle extras = data.getExtras();
			//Bitmap imageBitmap = (Bitmap) extras.get("data");
			
			imageview.setImageBitmap(bitmap);
			
		}*/
		if (resultCode == RESULT_OK) {
			String file = data.getStringExtra("image");
			String file2 = data.getStringExtra("choseimage");
			if (file != null) {
				bitmap = BitmapFactory.decodeFile(file);
				System.out.println("haha" + file);
			} else {
				bitmap = BitmapFactory.decodeFile(file2);
				System.out.println("haha2" + file);
			}

			imageview.setImageBitmap(bitmap);

		}

	}

	// convert the bitmap image to base64 string
	

	// decrease size of the image
	


	

	

}
