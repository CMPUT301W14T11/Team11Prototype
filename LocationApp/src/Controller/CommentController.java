package Controller;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;

import android.content.Context;
import android.location.Location;

import Model.Comments;
import Model.IDModel;

public interface CommentController {
          
	

	
	public void insertMaster(Comments com, int number);

	public ArrayList<Comments> get_comments(ArrayList<Comments> comment_array, Context content, HttpClient httpclient,
			Location current_location, double radius);
	
}
