package Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Controller.CommentController;
import Controller.compara;
import InternetConnection.ConnectToInternet;
import InternetConnection.ElasticSearchResponse;
import InternetConnection.ElasticSearchSearchResponse;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Base64;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CommentsModel implements CommentController{
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private Gson gson= new Gson();
	private ConnectToInternet connect = new ConnectToInternet();
	
	@Override
	public void insertMaster(Comments comm, int number) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(SERVER + MASTERCOMMENT + number);
		try {
			StringEntity data = new StringEntity(gson.toJson(comm));
			httpPost.setEntity(data);
			httpPost.setHeader("Accept", "application/json");
			HttpResponse response = httpclient.execute(httpPost);
			System.out.println(response.getStatusLine().toString() + "testing");
			System.out.println("chenggong " + number);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	
	public String convert_image_to_string(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
		return encoded;
	}
	
	@Override
	public ArrayList<Comments> get_comments(ArrayList<Comments> comment_array,Context content,HttpClient httpclient,Location current_location,double radius) {
		HttpPost httpPost = new HttpPost(
				"http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
		Gson gson1 = new Gson();
		try {
			double lat_gte = current_location.getLatitude() - radius;
			double lat_lte = current_location.getLatitude() + radius;
			double lon_gte = current_location.getLongitude() - radius;
			double lon_lte = current_location.getLongitude() + radius;
			String query_range2 = "{\"query\":{\"bool\" : {\"must\" : {\"range\" : {\"lat\" : { \"gte\" : "
					+ lat_gte
					+ ", \"lte\" : "
					+ lat_lte
					+ ",\"boost\":0.0 }}},\"must\":{\"match\":{\"sub_comments_ID\":0}},\"must\" : {\"range\" : {\"lon\" : { \"gte\" : "
					+ lon_gte
					+ ", \"lte\" : "
					+ lon_lte
					+ ", \"boost\":0.0}}}}}}";

			StringEntity entity = new StringEntity(query_range2);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpPost);
			String json1 = connect.getEntityContent(response);
			System.out.println(response.getStatusLine().toString() + "status");
			System.out.println(json1 + "holy");
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>() {
			}.getType();
			ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(
					json1, elasticSearchSearchResponseType);
            // new version of array sorting
			
			comment_array.clear();
			for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
				Comments comms = r.getSource();
				
				// check weath the comment if already in the arraylist, if not
				// then add it in there
				int flag = 0;
				for (Comments com : comment_array) { // turn on the flag if
														// object is already
														// inside the arary
					if (com.getMaster_ID() == comms.getMaster_ID()) {
						flag = 1;
						break;
					}
				}
				// if flag not turned on then add the object into the arraylsit
				if (flag == 0) {
					float DistanceResult [] = new float[10];
					Location.distanceBetween(current_location.getLatitude(),current_location.getLongitude(),comms.getLat(),comms.getLon(),DistanceResult);
					comms.setDistance(DistanceResult[0]);
					comment_array.add(comms);
				}
				Collections.sort(comment_array, new compara());
				for (Comments com : comment_array) {
					System.out.println("distance:" + com.getDistance());
				}
			}

		} catch (ClientProtocolException e) {

			System.out.println("client exe");
			e.printStackTrace();
		} catch (IOException e) {

			System.out.println("IO exe");
			e.printStackTrace();
		} catch (NullPointerException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {

			//Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		return comment_array;
	}

}
