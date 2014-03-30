package Model;

import java.util.ArrayList;



public class FavouriteModel
{
	private String username;
	private FavouriteComment comment;
	private ArrayList<FavouriteComment> subcomment = new ArrayList<FavouriteComment>();
	private int id;
	
	public FavouriteModel(String username, FavouriteComment comment, ArrayList<FavouriteComment> subcomment)
	{
		this.username=username;
		this.comment=comment;
		this.subcomment=subcomment;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void addSubComment(FavouriteComment sub)
	{
		subcomment.add(sub);
	}
	
	public ArrayList<FavouriteComment> getSubComment()
	{
		return subcomment;
	}
	public String getUsername()
	{
		
		return username;
	}
	
	public void setUsername(String username)
	{
	
		this.username = username;
	}

	
	public FavouriteComment getComment()
	{
	
		return comment;
	}

	
	public void setFaviourte(FavouriteComment faviourte)
	{
	
		this.comment = faviourte;
	}
	

	
}
