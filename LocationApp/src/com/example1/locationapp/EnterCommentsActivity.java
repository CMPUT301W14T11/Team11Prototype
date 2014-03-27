package com.example1.locationapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Controller.IDController;
import Controller.LocalFileLoder;
import Model.Comments;
import Model.IDModel;
import Model.UserModel;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * EnterCommentActivity class takes user 
 * input as master comment which will be shown
 * in the Main activity
 * @author qyu4
 *
 */
public class EnterCommentsActivity extends Activity implements IDController,
		Serializable {
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	EditText title_edit, subject_edit;
	Button post_button, picture_add_button;
	Location location;
	GPSTracker gps;
	ImageView imageview;
	int number;
	double longitude;
	double latitude;
	Gson gson;
	Context content;
	Bitmap bitmap;
	IDModel id_obj;
	private LocalFileLoder fl = new LocalFileLoder(this);
	private UserModel user;

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

		try {
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			System.out.println("cclocation" + latitude + "   " + longitude);
		} catch (NullPointerException e) {
			Toast.makeText(content, "Can't get location", Toast.LENGTH_LONG)
					.show();
		}
		gson = new Gson();

	}

	// send comment to server
	public void send(View view) {
		String title = title_edit.getText().toString();
		if ("".equals(title)) {

			Toast.makeText(getBaseContext(),
					"Title is empty! add some words please!",
					Toast.LENGTH_SHORT).show();

		}
		String subject = subject_edit.getText().toString();

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

						number = get_id();
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
							location, longitude, latitude, user.getUser_name());
					System.out.println("this is so cool");
					insertMaster(new_comment);
					System.out.println("this is so cool2!" + number);
				} else {
					user = fl.loadFromFile();
					System.out.println("image posted");
					String encode_image = convert_image_to_string(bitmap);
					final Comments new_comment = new Comments(0, number, 0, 0,
							title_edit.getText().toString(), subject_edit
									.getText().toString(), new Date(),
							location, longitude, latitude, encode_image,
							user.getUser_name());
					insertMaster(new_comment);

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
							insert(id_obj);
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

	// insert function for httppost comments
	public void insertMaster(Comments comm) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(SERVER + MASTERCOMMENT + number);
		try {
			StringEntity data = new StringEntity(gson.toJson(comm));
			httpPost.setEntity(data);
			httpPost.setHeader("Accept", "application/json");
			HttpResponse response = httpclient.execute(httpPost);
			System.out.println(response.getStatusLine().toString() + "testing");
			System.out.println("chenggong " + number);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// chose picture request for picture is 5
	public void addPicture(View view) {
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
				System.out.println("haha" + file);
			} else {
				bitmap = BitmapFactory.decodeFile(file2);
				System.out.println("haha2" + file);
			}

			imageview.setImageBitmap(bitmap);

		}

	}

	// convert the bitmap image to base64 string
	public String convert_image_to_string(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
		return encoded;
	}

	// decrease size of the image
	Bitmap ShrinkBitmap(String file, int width, int height) {

		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) height);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) width);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}

		bmpFactoryOptions.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
		return bitmap;
	}

	@Override
	public void insert(IDModel id) throws IllegalStateException, IOException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://cmput301.softwareprocess.es:8080/testing/lab111/1");
		StringEntity stringentity = null;

		try {
			stringentity = new StringEntity(gson.toJson(id));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (NullPointerException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		httpPost.setHeader("Accept", "application/json");

		httpPost.setEntity(stringentity);

		HttpResponse response = null;

		try {
			System.out.println("wocao2");
			response = httpclient.execute(httpPost);
			System.out.println("wocao1");

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (NullPointerException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public int get_id() {

		IDModel id_toReturn;// this is ID object from server
		int id = 0;
		try {
			// IDModel id_toReturn ;// this is ID object from server
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://cmput301.softwareprocess.es:8080/testing/lab111/1");
			httpget.addHeader("Accept", "application/json");

			HttpResponse response = httpclient.execute(httpget);

			String json = getEntityContent(response);

			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<IDModel>>() {
			}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<IDModel> esResponse = gson.fromJson(json,
					elasticSearchResponseType);
			// We get the recipe from it!
			id_toReturn = esResponse.getSource();
			System.out.println();

			id = id_toReturn.getId_for_master();

			// System.out.println(recipe.toString());
			// httpget.releaseConnection();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (NullPointerException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		return id;

	}

	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:" + json);
		return json;
	}

}
