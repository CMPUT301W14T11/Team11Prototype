package com.example1.locationapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * open source code from http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
 * @author qyu4
 */
public class GPSTracker extends Service implements LocationListener{

    private GPSTrackerContent gPSTrackerContent;

	// flag for GPS status
    boolean isGPSEnabled = false; 
    // flag for network status
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;    
    private Location location; 
    private double latitude; 
    private double longitude; 
    // The minimum distance to change Updates in meters (10 meters)
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; 
    // The minimum time between updates in milliseconds  (1 second)
    private static final long MIN_TIME_BW_UPDATES = 1000;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    
    
    
    //protected LocationManager gps_locationManager;
    public GPSTracker(Context context) {
        this.gPSTrackerContent = new GPSTrackerContent(context);
		getLocation();
    }

    
    
    /**
     * method for user to get the location.
     * @return
     * current location
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) gPSTrackerContent.getMContext()
                    .getSystemService(LOCATION_SERVICE);
      
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // Inflate the menu; this adds items to the action bar if it is present.ork provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        
                        // trying to get the best Accuracy
                        
                       if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }
    
    

    /**
     * Function to get latitude
     * @return 
     * current latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    

    /**
     * Function to get longitude
     * @return 
     * current longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }         
        // return longitude
        return longitude;
    }
    
    

    /**
     * Function to check if best network provider
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
     
    
 
    /**
     * Function to show settings alert dialog
     * */
    public void showSettingsAlert(){
        
  
        // Setting Icon to Dialog  
        gPSTrackerContent.showSettingsAlert();
    }	/*



    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }      
    }
    
    

	@Override
	public void onLocationChanged(Location location) {

		
	}

	
	
	@Override
	public void onProviderDisabled(String provider) {

		
	}

	
	
	@Override
	public void onProviderEnabled(String provider) {

		
	}

	
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		
	}

	
	
	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

}
