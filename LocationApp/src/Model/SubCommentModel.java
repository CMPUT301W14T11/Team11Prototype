package Model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import InternetConnection.ConnectToInternet;
import InternetConnection.ElasticSearchResponse;
import InternetConnection.ElasticSearchSearchResponse;

public class SubCommentModel {
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private Comments comment;
	private Gson gson = new Gson();
	private ConnectToInternet connect = new ConnectToInternet();
	public SubCommentModel(Comments comment){
		this.comment=comment;
	}
	public void insertMaster(Comments comm, int ServerID)
	 {
		 HttpClient httpclient  = new DefaultHttpClient();
		 HttpPost httpPost = new HttpPost(SERVER+MASTERCOMMENT+ServerID);
		 try {
			StringEntity data = new StringEntity(gson.toJson(comm));
			httpPost.setEntity(data);
			httpPost.setHeader("Accept","application/json");
			HttpResponse response = httpclient.execute(httpPost); 
			
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		 
	 }

	
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
		
			System.out.println("client exe");
			e.printStackTrace();
		} catch (IOException e) {
		
			System.out.println("IO exe");
			e.printStackTrace();
		}
		return comment_list1;
	}
	

}
