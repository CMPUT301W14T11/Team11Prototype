package com.example1.locationapp;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.test.suitebuilder.TestSuiteBuilder.FailedToCreateTests;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;

/**
 * ChoseImageActivity has two functionalities taking pictures and choose image
 * from local file;
 * 
 * @author qyu4
 * 
 */
public class ChoseImageActivity extends Activity implements
		ImageChooserListener {

	private ImageView imageViewThumbnail;

	private ImageView imageViewThumbSmall;

	private TextView textViewFile;

	private ImageChooserManager imageChooserManager;

	private ProgressBar pbar;

	private String filepath2;
	private String filePath;

	private int chooserType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chose_image);

		Button buttonTakePicture = (Button) findViewById(R.id.buttonTakePicture);
		buttonTakePicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takePicture();
			}
		});
		Button buttonChooseImage = (Button) findViewById(R.id.buttonChooseImage);
		buttonChooseImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseImage();
			}
		});

		imageViewThumbnail = (ImageView) findViewById(R.id.imageViewThumb);
		imageViewThumbSmall = (ImageView) findViewById(R.id.imageViewThumbSmall);
		textViewFile = (TextView) findViewById(R.id.textViewFile);

		pbar = (ProgressBar) findViewById(R.id.progressBar);
		pbar.setVisibility(View.GONE);

	}
	
	/**
	 * this method is for user choose the image which already saved.
	 */

	private void chooseImage() {
		chooserType = ChooserType.REQUEST_PICK_PICTURE;
		imageChooserManager = new ImageChooserManager(this,
				ChooserType.REQUEST_PICK_PICTURE, "myfolder", true);
		imageChooserManager.setImageChooserListener(this);
		try {
			pbar.setVisibility(View.VISIBLE);
			filePath = imageChooserManager.choose();
			System.out.println("gotiamge" + filePath);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method for user taking the photo.
	 */

	private void takePicture() {
		chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
		imageChooserManager = new ImageChooserManager(this,
				ChooserType.REQUEST_CAPTURE_PICTURE, "myfolder", true);
		imageChooserManager.setImageChooserListener(this);
		try {
			pbar.setVisibility(View.VISIBLE);
			filePath = imageChooserManager.choose();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
			if (imageChooserManager == null) {
				reinitializeImageChooser();
			}
			imageChooserManager.submit(requestCode, data);
		} else {
			pbar.setVisibility(View.GONE);
		}
	}

	/**
	 * get the Image 
	 */
	@Override
	public void onImageChosen(final ChosenImage image) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				pbar.setVisibility(View.GONE);
				if (image != null) {
					textViewFile.setText(image.getFilePathOriginal());
					filePath = image.getFilePathOriginal();
					System.out.println(filePath + "theout");
					filepath2 = filePath;
					imageViewThumbnail.setImageURI(Uri.parse(new File(image
							.getFileThumbnail()).toString()));
					imageViewThumbSmall.setImageURI(Uri.parse(new File(image
							.getFileThumbnailSmall()).toString()));
				}
			}
		});

		/*
		 * new AsyncTask<Void, Void, Void>() {
		 * 
		 * @Override protected Void doInBackground(Void... params) { // TODO
		 * Auto-generated method stub pbar.setVisibility(View.GONE); if (image
		 * != null) { textViewFile.setText(image.getFilePathOriginal());
		 * imageViewThumbnail.setImageURI(Uri.parse(new File(image
		 * .getFileThumbnail()).toString()));
		 * imageViewThumbSmall.setImageURI(Uri.parse(new File(image
		 * .getFileThumbnailSmall()).toString())); }
		 * 
		 * return null; }
		 * 
		 * }.execute();
		 */

	}

	/**
	 * to return the the error information to let user know.
	 */
	@Override
	public void onError(final String reason) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				pbar.setVisibility(View.GONE);
				Toast.makeText(ChoseImageActivity.this, reason,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onBackPressed() {

		Intent intent = new Intent();
		intent.putExtra("image", filePath);
		intent.putExtra("choseimage", filepath2);
		setResult(RESULT_OK, intent);
		finish();
		super.onBackPressed();

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	// Should be called if for some reason the ImageChooserManager is null (Due
	// to destroying of activity for low memory situations)
	private void reinitializeImageChooser() {
		imageChooserManager = new ImageChooserManager(this, chooserType,
				"myfolder", true);
		imageChooserManager.setImageChooserListener(this);
		imageChooserManager.reinitialize(filePath);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("chooser_type", chooserType);
		outState.putString("media_path", filePath);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("chooser_type")) {
				chooserType = savedInstanceState.getInt("chooser_type");
			}

			if (savedInstanceState.containsKey("media_path")) {
				filePath = savedInstanceState.getString("media_path");
			}
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
}
