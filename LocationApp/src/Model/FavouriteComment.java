package Model;

import com.google.gson.JsonElement;

import android.graphics.Bitmap;


public class FavouriteComment
{
	private String title;
	private String text;
	private JsonElement comment_image;
	private String userName;
	private double distance;
	private int id;
	private double latitude, longitude;
	
	
	public void setLocation(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
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
	
	public void setImage(JsonElement image)
	{
		this.comment_image = image;
	}
	
	public JsonElement getImage()
	{
		return comment_image;
	}
	
	
}
