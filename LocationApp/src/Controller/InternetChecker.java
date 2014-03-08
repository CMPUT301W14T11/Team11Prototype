package Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class InternetChecker extends BroadcastReceiver{
    /**this is trying to received network states,
     * if the app is connected to internet then it will start loading in mainactivity 
     * 
     */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("rrrr");
		final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        
        /**checking the location 
         * 
         */
        if(wifi.isConnected() || mobile.isConnected())
        {
        	Toast.makeText(context, "Connected to Internet", Toast.LENGTH_LONG).show();
        	
        }
        else
        {
        	Toast.makeText(context, "No Internet!", Toast.LENGTH_LONG).show();
        }
        
        
	}

}
