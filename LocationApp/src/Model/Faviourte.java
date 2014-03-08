package Model;



public class Faviourte
{
	private String username;
	private Comments comment;
	
	public Faviourte(String username, Comments comment)
	{
		this.username=username;
		this.comment=comment;
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
