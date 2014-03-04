package Model;

import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;

public class SubComments extends Comments{
	public int master_comment_ID;
	public int sub_commen_ID;
	public int master_ID;
	public int sub_ID;
	public String the_comment,subject_comment;
	//Location comment_location;
	public Date comment_date;
	public boolean master_comment;
	public Location comment_location ;
	public double lon ;
	public double lat;
	public double distance;
	public Bitmap comment_image;
	public String image_encode;
	public SubComments(int masterid,int sub_commen_ID, int subid, String title, String subject,
			Date the_date, Location location, double lon, double lat) {
		super(masterid, subid, title, subject, the_date, location, lon, lat);
		this.sub_commen_ID=sub_commen_ID;
		// TODO Auto-generated constructor stub
		
	}
	

}
