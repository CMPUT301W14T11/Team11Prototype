package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonElement;
/**
* A Comments object contains the user id,master comments id,sub comments id, sub comments title and body,a picture if it has
* date,location of the comments posted, a userName which published this comment
*/
public class Comments implements Serializable{
	private int master_comment_ID;
	private int sub_comments_ID;
	private int master_ID;
	private int sub_ID;
	private String the_comment,subject_comment;
	//Location comment_location;
	private Date comment_date;
	private boolean master_comment;
	//private Location comment_location ;
	private double lon ;
	private double lat;
	private double distance;
	//private Bitmap comment_image;
	private JsonElement image_encode;
	private int userID;
	private String userName;
	private ArrayList<String> TagsList= new ArrayList<String>();
	public ArrayList<String> getTagsList() {
		return TagsList;
	}

	public void setTagsList(ArrayList<String> tagsList) {
		TagsList = tagsList;
	}


	
	public Comments(int user_id,int masterid , int sid, int subid,String title, String subject,Date the_date,double lon,double lat, String userName)
	{   
		this.master_ID=masterid;
		this.sub_comments_ID=sid;
		this.sub_ID= subid;
		this.comment_date=the_date;
		this.the_comment=title;
		this.subject_comment=subject;
		this.master_comment=true;
		this.lon = lon;
		this.lat = lat;
		this.userID=user_id;
		this.userName = userName; 
		
	}
	
	
	// make a comments with image in them
	public Comments(int user_id,int masterid , int sid,int subid,String title, String subject,Date the_date,double lon,double lat,JsonElement encode, String userName)
	{   
		this.master_ID=masterid;
		this.sub_comments_ID=sid;
		this.sub_ID= subid;
		this.comment_date=the_date;
		this.the_comment=title;
		this.subject_comment=subject;
		this.master_comment=true;
		this.lon = lon;
		this.lat = lat;
		this.image_encode=encode;
		this.userID=user_id;
		this.userName = userName;
	}

	
	public String getUserName()
	{
	
		return userName;
	}

	public int getMaster_comment_ID() {
		return master_comment_ID;
	}

	public void setMaster_comment_ID(int master_comment_ID) {
		this.master_comment_ID = master_comment_ID;
	}

	public int getSub_comments_ID() {
		return sub_comments_ID;
	}

	public void setSub_comments_ID(int sub_comments_ID) {
		this.sub_comments_ID = sub_comments_ID;
	}

	public int getMaster_ID() {
		return master_ID;
	}

	public void setMaster_ID(int master_ID) {
		this.master_ID = master_ID;
	}

	public int getSub_ID() {
		return sub_ID;
	}

	public void setSub_ID(int sub_ID) {
		this.sub_ID = sub_ID;
	}

	public String getThe_comment() {
		return the_comment;
	}

	public void setThe_comment(String the_comment) {
		this.the_comment = the_comment;
	}

	public String getSubject_comment() {
		return subject_comment;
	}

	public void setSubject_comment(String subject_comment) {
		this.subject_comment = subject_comment;
	}

	public Date getComment_date() {
		return comment_date;
	}

	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}

	public boolean isMaster_comment() {
		return master_comment;
	}

	public void setMaster_comment(boolean master_comment) {
		this.master_comment = master_comment;
	}



	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}


	public JsonElement getImage_encode() {
		return image_encode;
	}

	public void setImage_encode(JsonElement image_encode) {
		this.image_encode = image_encode;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	

}
