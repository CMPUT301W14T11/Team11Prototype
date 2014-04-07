package Controller;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import android.content.Context;
import android.location.Location;

import Model.Comments;
/**
 * interface for upload comments to server
 * @author yazhou
 */
public interface CommentController {
          
	public void insertMaster(Comments comment, int number);
	public ArrayList<Comments> get_comments(ArrayList<Comments> comment_array, Context content, HttpClient httpclient,
			Location current_location, double radius);
	
}
