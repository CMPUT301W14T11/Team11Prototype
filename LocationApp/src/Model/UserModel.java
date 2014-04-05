package Model;

import java.util.ArrayList;

/**
 * The model which will store all users information in single phone
 * @author bqi
 *
 */
public class UserModel {
    
    private ArrayList<FavouriteModel> faviourte = new ArrayList<FavouriteModel>();
    private String user_name;
    private ArrayList<Comments> comment = new ArrayList<Comments>();
    /**
     * Constructor of the UserModel, initial with "" to be username
     */
    public UserModel()
    {
    	user_name = "";
    }

    /**
     * add a comment to comment array
     * @param c -- the comment needs to be added
     */
    public void addComment(Comments c)
    {
    	comment.add(c);
    }
    
    /**
     * get the comments array
     * @return
     * the comments array
     */
    public ArrayList<Comments> getComment()
    {
    	return comment;
    }
    
    /**
     * get current login username
     * @return
     * "" if login as guest, non-empty string if login as some name
     */
	public String getUser_name()
	{
	
		return user_name;
	}

	/**
	 * set username when user change
	 * @param user_name -- nuew user name
	 */
	public void setUser_name(String user_name)
	{
	
		this.user_name = user_name;
	}
	
	/**
	 * get the Favourite list
	 * @return
	 * the favourite list
	 */
	public ArrayList<FavouriteModel> getFaviourte()
	{
	
		return faviourte;
	}
	
	/**
	 * add a favourite model to the user
	 * @param f -- favourite model
	 */
	public void addFaviourte(FavouriteModel f)
	{
		faviourte.add(f);
	}

	

	
	
	
	
}
