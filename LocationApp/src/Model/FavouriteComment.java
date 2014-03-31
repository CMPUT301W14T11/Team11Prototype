package Model;

import android.graphics.Bitmap;


public class FavouriteComment
{
	private String title;
	private String text;
	private String comment_image;
	private String userName;
	private double distance;
	private int id;
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	
	public double getDistance()
	{
		return distance;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getTitle()
	{
	
		return title;
	}
	
	public void setTitle(String title)
	{
	
		this.title = title;
	}
	
	public String getText()
	{
	
		return text;
	}
	
	public void setText(String text)
	{
	
		this.text = text;
	}
	
	public void setImage(String image)
	{
		this.comment_image = image;
	}
	
	public String getImage()
	{
		return comment_image;
	}
	
	
}
