package Model;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;
// this is a master comment class
public class Comments implements Serializable{
	public int master_comment_ID;
	public int sub_comments_ID;
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
	//consturctor for creating a master comment
	public Comments(int masterid , int sid, int subid,String title, String subject,Date the_date,Location location,double lon,double lat)
	{   
		this.master_ID=masterid;
		this.sub_comments_ID=sid;
		this.sub_ID= subid;
		//this.comment_location= the_location;
		this.comment_date=the_date;
		this.the_comment=title;
		this.subject_comment=subject;
		this.master_comment=true;
		this.comment_location=location;
		this.lon = lon;
		this.lat = lat;
		
	}
	// constrcutor for creating a sub comment;
	/*public Comments(int master_commentid,int masterid , int subid, DateTime the_date,String comment,boolean master)
	{   this.master_comment_ID=master_commentid;
		this.master_ID=masterid;
		this.sub_ID= subid;
		//this.comment_location= the_location;
		this.comment_date=the_date;
		this.the_comment=comment;
		this.master_comment=false;
	}*/
	
	// make a comments with image in them
	public Comments(int masterid , int sid,int subid,String title, String subject,Date the_date,Location location,double lon,double lat,String encode)
	{   
		this.master_ID=masterid;
		this.sub_comments_ID=sid;
		this.sub_ID= subid;
		//this.comment_location= the_location;
		this.comment_date=the_date;
		this.the_comment=title;
		this.subject_comment=subject;
		this.master_comment=true;
		this.comment_location=location;
		this.lon = lon;
		this.lat = lat;
		this.image_encode=encode;
	}
	

}
