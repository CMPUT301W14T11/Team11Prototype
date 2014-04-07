package com.example1.locationapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.example1.locationapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * This class is to change location on the bit map in the application;
 * @author qyu4
 */
public class Playtube extends Activity {
	private GoogleMap map;
	private double lat, lon;
	private LatLng lat_and_long, new_positon;
	private CameraUpdate camUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_tube);
		Intent intent = getIntent();
		lat = intent.getDoubleExtra("lat", 0);
		lon = intent.getDoubleExtra("long", 0);
		// get the map from the map fragment
		lat_and_long = new LatLng(lat, lon);
		camUpdate = CameraUpdateFactory.newLatLng(lat_and_long);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.animateCamera(camUpdate);
		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng arg0) {
				
				map.addMarker(new MarkerOptions()
						.position(arg0)
						.title("new location")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			}
		});
		map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				
				new_positon = arg0.getPosition();
				Intent intent = new Intent();
				intent.putExtra("lat", new_positon.latitude);
				intent.putExtra("lon", new_positon.longitude);
				setResult(RESULT_OK, intent);
				finish();
				return false;
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 * @param menu
	 * @return true
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.play_tube, menu);
		return true;
	}

	
	
	
	
	
	
	
	
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();

	}

}
