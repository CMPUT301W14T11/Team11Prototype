package Model;

import java.util.ArrayList;


/**
 * Used to store each favourite
 * @author bqi
 *
 */
public class FavouriteModel
{
	private String username;
	private FavouriteComment comment;
	private ArrayList<FavouriteComment> subcomment = new ArrayList<FavouriteComment>();
	private int id;
	private int code;
	
	/**
	 * Constructor of FavouriteModel
	 * @param username -- current user's name
	 * @param comment -- the main comment 
	 * @param subcomment -- all subcomments of the main comment
	 */
	public FavouriteModel(String username, FavouriteComment comment, ArrayList<FavouriteComment> subcomment)
	{
		this.username=username;
		this.comment=comment;
		this.subcomment=subcomment;
	}
	
	/**
	 * set code for the saving comment
	 * @param code -- 0 for favourite, 1 for saving
	 */
	public void setCode(int code)
	{
		this.code = code;
	}
	
	/**
	 * return the favourite's code
	 * @return
	 * the code of favourite
	 */
	public int getCode()
	{
		return code;
	}
	
	/**
	 * clean the subcomment arraylist
	 */
	public void clean()
	{
		subcomment.clear();
	}
	
	/**
	 * store the comment's id
	 * @param id -- comment's id
	 */
	public void setID(int id)
	{
		this.id = id;
	}
	
	/**
	 * get the comment Id
	 * @return
	 * the comment's id
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * add a subcomment to the arraylist
	 * @param sub -- the subcomment need to be added
	 */
	public void addSubComment(FavouriteComment sub)
	{
		subcomment.add(sub);
	}
	
	/**
	 * get the arraylist of subcomments
	 * @return
	 * the array of subcomments
	 */
	public ArrayList<FavouriteComment> getSubComment()
	{
		return subcomment;
	}
	
	/**
	 * get the user of the favourite belongs to
	 * @return
	 * the favourite's belonging 
	 */
	public String getUsername()
	{
		
		return username;
	}
	
	/**
	 * set the username for the favourite
	 * @param username -- user who store the favourite
	 */
	public void setUsername(String username)
	{
	
		this.username = username;
	}

	/**
	 * get the main comment of the favourite
	 * @return
	 * the main comment of the favourite
	 */
	public FavouriteComment getComment()
	{
	
		return comment;
	}
	
}
