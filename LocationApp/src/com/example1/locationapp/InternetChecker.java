package com.example1.locationapp;

import com.example1.locationapp.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * This class will check the if the Internet service in on or not.
 * 
 * @author qyu4
 * 
 */
public class InternetChecker extends BroadcastReceiver {
	/**
	 * this is trying to received network states, if the app is connected to
	 * internet then it will start loading in mainactivity
	 * 
	 */
	
	@Override
	public void onReceive(Context context, Intent intent) {

		System.out.println("rrrr");
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		/**
		 * checking the internet connection
		 * 
		 */
		if (wifi.isConnected() || mobile.isConnected()) {
			Toast.makeText(context, "Connected to Internet", Toast.LENGTH_LONG)
					.show();
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					context).setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle("New Comments")
					.setContentText("Hey, you got new comments!");
		
			
			//upload saved comment to internet;
			Intent resultIntent = new Intent(context, MainActivity.class);

			// The stack builder object will contain an artificial back stack
			// for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out
			// of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(MainPage.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
					0, PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(1, mBuilder.build());
		} else {
			Toast.makeText(context, "No Internet!", Toast.LENGTH_LONG).show();
		}

	}

}
