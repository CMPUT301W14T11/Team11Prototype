package Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class InternetChecker extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        
        
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
