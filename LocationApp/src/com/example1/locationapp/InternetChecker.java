package com.example1.locationapp;

import java.util.Date;
import java.util.HashSet;

import InternetConnection.ConnectToInternet;
import Model.Comments;
import Model.SubCommentModel;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
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
	private int MasterId;
	private boolean connect = false;
	double lat;
	double lon;
	public boolean connected(Context context)
	{
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifi.isConnected() || mobile.isConnected()) 
		{
			return true;
		}
		else
			return connect;
	}
	@Override
	public void onReceive(final Context context, Intent intent) {
		final GPSTracker gps = new GPSTracker(context);
		
		connect = true;
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		/**
		 * check for local comments, if there is local saved comments then push to server
		 * 
		 */
		
		SharedPreferences sharedPref = context.getSharedPreferences("mydata",Context.MODE_PRIVATE);
		final String title = sharedPref.getString("title","");
		final String subject = sharedPref.getString("subject", "");
		String picture = sharedPref.getString("image", "");
		final String name = sharedPref.getString("name", "");
		if(!title.equals(""))
		{
			// make new comments
			if(!picture.equals(""))
			{
				new AsyncTask<Void,Void,Void>() {
					
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						// get master ID
						new AsyncTask<Void, Void, Void>()
						{

							@Override
							protected Void doInBackground(Void... params) {
								// TODO Auto-generated method stub
								ConnectToInternet con = new ConnectToInternet();
								MasterId = con.get_id(context);
								return null;
							}
							
						}.execute();
					}


					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						// make new comments
						Comments NewComment = new Comments(0, MasterId, 0, 0, title, subject, new Date(),gps.getLongitude(),gps.getLatitude(),name);
						SubCommentModel modle = new SubCommentModel(NewComment);
						
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						new AsyncTask<Void,Void	,Void>()
						{

							@Override
							protected Void doInBackground(Void... params) {
								// TODO Auto-generated method stub
								return null;
							}
							
						}.execute();
					}
				}.execute();
			}
			// no picture
			else
			{

				new AsyncTask<Void,Void,Void>() {
					
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						// get master ID
						new AsyncTask<Void, Void, Void>()
						{

							@Override
							protected Void doInBackground(Void... params) {
								// TODO Auto-generated method stub
								return null;
							}
							
						}.execute();
					}


					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						// make new comments
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						new AsyncTask<Void,Void	,Void>()
						{

							@Override
							protected Void doInBackground(Void... params) {
								// TODO Auto-generated method stub
								return null;
							}
							
						}.execute();
					}
				}.execute();
			}
		}
		System.out.println("title is:"+title);
		
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
