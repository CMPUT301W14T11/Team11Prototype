package Model;

import android.location.Location;

public class SaveFavourite {
	
	
	
	public double getDistance(double commentLaiude, double commentLongitude, Location current_location)
	{
		//Location current_location = null;
		float DistanceResult [] = new float[10];
		Location.distanceBetween(current_location.getLatitude(),current_location.getLongitude(),commentLaiude,commentLongitude,DistanceResult);
		return DistanceResult[0];
	}
}
