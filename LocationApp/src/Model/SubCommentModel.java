package Model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Controller.CommentController;
import InternetConnection.ConnectToInternet;
import InternetConnection.ElasticSearchResponse;
import InternetConnection.ElasticSearchSearchResponse;
/**
 * Subcomments controller for internet upload and download
 * @author yazhou
 *
 */
public class SubCommentModel implements CommentController{
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private Comments comment;
	private Gson gson = new Gson();
	private ConnectToInternet connect = new ConnectToInternet();
	
	public SubCommentModel(Comments comment){
		this.setComment(comment);
	}
	/**
	 * this is for insert comment to the server
	 */
	@Override
	public void insertMaster(Comments comm, int ServerID)
	 {
		 HttpClient httpclient  = new DefaultHttpClient();
		 HttpPost httpPost = new HttpPost(SERVER+MASTERCOMMENT+ServerID);
		 try {
			StringEntity data = new StringEntity(gson.toJson(comm));
			httpPost.setEntity(data);
			httpPost.setHeader("Accept","application/json");
			@SuppressWarnings("unused")
			HttpResponse response = httpclient.execute(httpPost); 
			
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		 
	 }

	
	
	
	
	
	
	
	
	
	
	/**
	 * use this function for download comments from the server
	 * @param comment_list1
	 * @param mID
	 * @param httpclient
	 * @return
	 */
	public ArrayList<Comments> get_comments(ArrayList<Comments> comment_list1, int mID,HttpClient httpclient) {
		HttpPost httpPost = new HttpPost(
			"http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");

		Gson gson1 = new Gson();
		try {
			String query_range2 = "{\"query\":{\"bool\":{\"must\":{\"match\":{\"master_ID\":"
				+ mID + "}}} }}";
			StringEntity entity = new StringEntity(query_range2);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpPost);
			String json1 = connect.getEntityContent(response);
			System.out.println(json1 + "holy");
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>() {
			}.getType();
			ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(
				json1, elasticSearchSearchResponseType);
		
		for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
			Comments comms = r.getSource();

			// check weath the comment if already in the arraylist, if not
			// then add it in there
			int flag = 0;
			for (Comments com : comment_list1) { // turn on the flag if
												// object is already inside
												// the arary
				if (com.getMaster_ID() == comms.getMaster_ID()) {
					flag = 1;
					comment_list1.add(comms);
					break;
				}
			}
			// if flag not turned on then add the object into the arraylsit
			if (flag == 0) {

				comment_list1.add(comms);
			}

		}
		// System.out.println(comment_list.size()+"size"+ServerID);

		} catch (ClientProtocolException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return comment_list1;
	}
	@Override
	public ArrayList<Comments> get_comments(ArrayList<Comments> comment_array,
			Context content, HttpClient httpclient, Location current_location,
			double radius) {

		return null;
	}
	public Comments getComment() {
		return comment;
	}
	public void setComment(Comments comment) {
		this.comment = comment;
	}
	

}
