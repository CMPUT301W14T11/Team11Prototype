package Model;

import java.util.ArrayList;

import android.location.Location;

public class UserModel {
    private String user_name;
    private Location user_location;
    private ArrayList<Faviourte> faviourte = new ArrayList<Faviourte>();
    
    public UserModel(String user_name, Location user_location)
    {
    	this.user_location = user_location;
    	this.user_name= user_name;

    }
    
    public UserModel()
    {
    	
    }

	
	public String getUser_name()
	{
	
		return user_name;
	}

	
	public void setUser_name(String user_name)
	{
	
		this.user_name = user_name;
	}

	
	public Location getUser_location()
	{
	
		return user_location;
	}

	
	public void setUser_location(Location user_location)
	{
	
		this.user_location = user_location;
	}

	
	public ArrayList<Faviourte> getFaviourte()
	{
	
		return faviourte;
	}
	
	public void addFaviourte(Faviourte f)
	{
		faviourte.add(f);
	}

	

	
	
	
	
}
