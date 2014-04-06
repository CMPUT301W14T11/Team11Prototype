package com.example1.locationapp;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Environment;
/**
 * refacter activiy
 * @author yazhou
 *
 */
public class EnterCommentsActivityProduct {
	private String mCurrentPhotoPath;

	public String getMCurrentPhotoPath() {
		return mCurrentPhotoPath;
	}

	public void setMCurrentPhotoPath(String mCurrentPhotoPath) {
		this.mCurrentPhotoPath = mCurrentPhotoPath;
	}

	public File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, ".jpg", storageDir);
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}
}