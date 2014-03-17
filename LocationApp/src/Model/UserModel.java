package Model;

import java.util.ArrayList;

public class UserModel {
    
   //private Location user_location;
    private ArrayList<FavouriteModel> faviourte = new ArrayList<FavouriteModel>();
    private String user_name;
    
    public UserModel()
    {
    	user_name = "";
    	//user_location=null;
    }

	public String getUser_name()
	{
	
		return user_name;
	}


	public void setUser_name(String user_name)
	{
	
		this.user_name = user_name;
	}

	
//	public Location getUser_location()
//	{
//	
//		return user_location;
//	}
//
//	
//	public void setUser_location(Location user_location)
//	{
//	
//		this.user_location = user_location;
//	}

	
	public ArrayList<FavouriteModel> getFaviourte()
	{
	
		return faviourte;
	}
	
	public void addFaviourte(FavouriteModel f)
	{
		faviourte.add(f);
	}

	

	
	
	
	
}
