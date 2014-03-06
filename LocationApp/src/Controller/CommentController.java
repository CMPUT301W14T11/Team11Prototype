package Controller;

import java.io.IOException;

import Model.Comments;
import Model.IDModel;

public interface CommentController {
          
	
	public  void get_comments(String url);
	
	public void insertMaster(Comments com);
	
}
