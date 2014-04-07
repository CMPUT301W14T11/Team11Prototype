package com.example1.locationapp;


import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
/**
 * class for GPSTrackerContent reference from the 
 * open source code from http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
 * @author qyu4
 */
public class GPSTrackerContent {
	private final Context mContext;

	public GPSTrackerContent(Context context) {
		this.mContext = context;
	}

	public Context getMContext() {
		return mContext;
	}

	/**
	 * Function to show settings alert dialog
	 */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		alertDialog.setTitle("GPS is settings");
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}
}