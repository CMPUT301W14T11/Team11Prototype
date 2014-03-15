package Model;

import java.util.ArrayList;



public class FavouriteModel
{
	private String username;
	private Comments comment;
	private ArrayList<Comments> subcomment = new ArrayList<Comments>();
	
	public FavouriteModel(String username, Comments comment, ArrayList<Comments> subcomment)
	{
		this.username=username;
		this.comment=comment;
		this.subcomment=subcomment;
	}
	
	/*public void addSubComment(Comments sub)
	{
		subcomment.add(sub);
	}
	*/
	public ArrayList<Comments> getSubComment()
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

	
	public Comments getComment()
	{
	
		return comment;
	}

	
	public void setFaviourte(Comments faviourte)
	{
	
		this.comment = faviourte;
	}
	

	
}
