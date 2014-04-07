package Model;

import android.location.Location;
/**
 * use this class if you want to get distance for offline comments
 * @author yazhou
 *
 */
public class SaveFavourite {
	
	
	/**
	 * function for compute the distance of the comment
	 * @param commentLaiude
	 * @param commentLongitude
	 * @param current_location
	 * @return
	 */
	public double getDistance(double commentLaiude, double commentLongitude, Location current_location)
	{
		float DistanceResult [] = new float[10];
		Location.distanceBetween(current_location.getLatitude(),current_location.getLongitude(),commentLaiude,commentLongitude,DistanceResult);
		return DistanceResult[0];
	}
}
