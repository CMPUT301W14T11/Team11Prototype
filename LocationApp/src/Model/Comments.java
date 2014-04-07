package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonElement;
/**
* A Comments object contains the user id,master comments id,sub comments id, sub comments title and body,a picture if it has
* date,location of the comments posted, a userName which published this comment
* @author zuo2
*/
@SuppressWarnings("serial")
public class Comments implements Serializable{
	private int master_comment_ID;
	private int sub_comments_ID;
	private int master_ID;
	private int sub_ID;
	private String the_comment,subject_comment;
	private Date comment_date;
	private boolean master_comment;
	private double lon ;
	private double lat;
	private double distance;
	private JsonElement image_encode;
	private int userID;
	private String userName;
	private ArrayList<String> TagsList= new ArrayList<String>();
	private ArrayList<Comments> subComment = new ArrayList<Comments>();

	
	/**
	 * constructor for comment with no picture
	 * @param user_id    User's ID
	 * @param masterid   comments' master ID
	 * @param sid        SubComments ID
	 * @param subid      SubComments' subcomment ID
	 * @param title      Title of the comments
	 * @param subject    Body of the comments
	 * @param the_date   date posted 
	 * @param lon        location when user post comments
	 * @param lat        location when user post comments
	 * @param userName   User's name
	 */
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
	
	
	/**
	 * use constructor to make comments with picture
	 * @param user_id    User's ID
	 * @param masterid   comments' master ID
	 * @param sid        SubComments ID
	 * @param subid      SubComments' subcomment ID
	 * @param title      Title of the comments
	 * @param subject    Body of the comments
	 * @param the_date   date posted 
	 * @param lon        location when user post comments
	 * @param lat        location when user post comments
	 * @param userName   User's name
	 * @param encode     Picture
	 */
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
	
	

    /**
     * to set the sub comments
     * @param subComments -- updated subComment
     */
	public void setSubComment(ArrayList<Comments> subComments)
	{
		subComment = subComments;
	}
	
	

	/**
	 * to get the subcomment list
	 * @return
	 * the subcomment list
	 */
	public ArrayList<Comments> getSubComment()
	{
		return subComment;
	}
	
	

	/**
	 * to get the tag list
	 * @return
	 * the tag list
	 */
	public ArrayList<String> getTagsList() {
		return TagsList;
	}
	
	

    /**
     * setting the tag list 
     * @param tagsList -- tag list
     */
	public void setTagsList(ArrayList<String> tagsList) {
		TagsList = tagsList;
	}
	
	

	/**
	 * get the string of user name
	 * @return
	 * the string of user name
	 */
	public String getUserName()
	{
	
		return userName;
	}

	

	/**
	 * to get master comment ID
	 * @return
	 * master comment ID
	 */
	public int getMaster_comment_ID() {
		return master_comment_ID;
	}
	
	

    /**
     * seting the master comment id
     * @param master_comment_ID -- the mater comment id
     */
	public void setMaster_comment_ID(int master_comment_ID) {
		this.master_comment_ID = master_comment_ID;
	}
	
	

	/**
	 * get the sub comment id
	 * @return
	 * the sub comment id
	 */
	public int getSub_comments_ID() {
		return sub_comments_ID;
	}
	
	

    /**
     * seting sub comment id
     * @param sub_comments_ID -- the sub comment id
     */
	public void setSub_comments_ID(int sub_comments_ID) {
		this.sub_comments_ID = sub_comments_ID;
	}
	
	

    /**
     * get the master id
     * @return
     * the master id
     */
	public int getMaster_ID() {
		return master_ID;
	}
	
	

    /**
     * set the master id
     * @param master_ID -- master id
     */
	public void setMaster_ID(int master_ID) {
		this.master_ID = master_ID;
	}
	
	

    /**
     * get the sub id
     * @return
     * the sub id
     */
	public int getSub_ID() {
		return sub_ID;
	}
	
	

	/**
	 * to set the sub_id
	 * @param sub_ID -- sub id of comment
	 */
	public void setSub_ID(int sub_ID) {
		this.sub_ID = sub_ID;
	}
	
	
	
    /**
     * to get the comment
     * @return
     * the comment
     */
	public String getThe_comment() {
		return the_comment;
	}
	

	
    /**
     * the set the comment by given string
     * @param the_comment -- the comment need to be set
     */
	public void setThe_comment(String the_comment) {
		this.the_comment = the_comment;
	}
	
	

    /**
     * get the subject comment
     * @return
     * the comment's subject
     */
	public String getSubject_comment() {
		return subject_comment;
	}
	
	

    /**
     * set the subject comment by given string
     * @param subject_comment -- the subject of comment
     */
	public void setSubject_comment(String subject_comment) {
		this.subject_comment = subject_comment;
	}
	
	

    /**
     * get the comment's date
     * @return
     * the comment's date
     */
	public Date getComment_date() {
		return comment_date;
	}
	
	

    /**
     * to set the comment date
     * @param comment_date -- date of comment
     */
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	
	

    /**
     * get the master comments
     * @return
     * the master comments
     */
	public boolean isMaster_comment() {
		return master_comment;
	}
	
	

    /**
     * the set he master commetns by given boolean
     * @param master_comment -- given boolean
     */
	public void setMaster_comment(boolean master_comment) {
		this.master_comment = master_comment;
	}
	
	
	
	/**
	 * get the longitude
	 * @return
	 * the longitude of comment
	 */
	public double getLon() {
		return lon;
	}
	
	

	/**
	 * set the longitude
	 * @param lon -- the longitude of comment
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	

    /**
     * get the latitude
     * @return
     * the latitude of comment
     */
	public double getLat() {
		return lat;
	}
	
	

    /**
     * to set the latitude
     * @param lat -- the latitude of comment
     */
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	

	/**
	 * to the distance
	 * @return
	 * the distance
	 */
	public double getDistance() {
		return distance;
	}
	
	

	/**
	 * to set the distance by give double value
	 * @param distance -- the distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	

	/**
	 * get the image encode
	 * @return
	 * the image encode
	 */
	public JsonElement getImage_encode() {
		return image_encode;
	}
	
	

	/**
	 * set the image encode
	 * @param image_encode -- the image encode
	 */
	public void setImage_encode(JsonElement image_encode) {
		this.image_encode = image_encode;
	}
	
	

    /**
     * get the user ID
     * @return
     * the user ID
     */
	public int getUserID() {
		return userID;
	}
	
	

	/**
	 * set the user ID by given int
	 * @param userID -- the user ID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	

}
