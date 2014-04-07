package Model;

import com.google.gson.JsonElement;

/**
 * Used to store each single comment for favourite and saving function
 * @author bqi
 *
 */
public class FavouriteComment
{
	private String title;
	private String text;
	private JsonElement comment_image;
	private String userName;
	private double distance;
	private int id;
	private double latitude, longitude;
	
	/**
	 * set the coordinates for the comments
	 * @param latitude -- latitude of the comments
	 * @param longitude -- longitude of the comments
	 */
	public void setLocation(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get the latitude of the comment
	 * @return
	 * latitude of the comment
	 */
	public double getLatitude()
	{
		return latitude;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get the longitude of the comment 
	 * @return
	 * longitude of the comment
	 */
	public double getLongitude()
	{
		return longitude;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * set the comment's id
	 * @param id -- the comment's master id
	 */
	public void setID(int id)
	{
		this.id = id;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get comment's master id
	 * @return
	 * comment's master id
	 */
	public int getID()
	{
		return id;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * set the distance from the comment's sending location
	 * @param distance -- the distance from the comment's sending location
	 */
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get the distance from the comment's sending location
	 * @return
	 * the distance from the comment's sending location
	 */
	public double getDistance()
	{
		return distance;
	}

	
	
	
	
	
	
	
	
	
	/**
	 * set the username who store this comment
	 * @param userName -- username  of who store this comment
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get the username who store this comment
	 * @return
	 * the username who store this comment
	 */
	public String getUserName()
	{
		return userName;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get the title of this comment
	 * @return
	 * the title of this comment
	 */
	public String getTitle()
	{
	
		return title;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * set the title of this comment
	 * @param title -- the title of this comment
	 */
	public void setTitle(String title)
	{
	
		this.title = title;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get the content of the comment
	 * @return
	 * the content of the comment
	 */
	public String getText()
	{
	
		return text;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * set the content of the comment
	 * @param text -- the content of the comment
	 */
	public void setText(String text)
	{
	
		this.text = text;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * set the image of the comment
	 * @param image -- the string of image of the comment
	 */
	public void setImage(JsonElement image)
	{
		this.comment_image = image;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get the image string of the comment
	 * @return
	 * the image of the comment
	 */
	public JsonElement getImage()
	{
		return comment_image;
	}
	
	
}
