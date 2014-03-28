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
	
	public ArrayList<FavouriteModel> getFaviourte()
	{
	
		return faviourte;
	}
	
	public void addFaviourte(FavouriteModel f)
	{
		faviourte.add(f);
	}

	

	
	
	
	
}
