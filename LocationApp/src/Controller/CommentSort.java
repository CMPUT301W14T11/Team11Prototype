package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.example1.locationapp.GPSTracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import InternetConnection.ElasticSearchResponse;
import InternetConnection.ElasticSearchSearchResponse;
import Model.Comments;
/**
 * the class CommentSort is used to sort comments by date and picture individually.
 */
public class CommentSort {
	Location current_location;
	double radius= 0.01;
	HttpClient httpclient;
	ArrayList<Comments> date_comment_array;
	Context content;
    GPSTracker gps ;
	
	public ArrayList<Comments> sortByDate(){
		
		date_comment_array = new ArrayList<Comments>();
		current_location = gps.getLocation();
		
		HttpPost httpPost= new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
		//HttpGet  httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/emouse/_search?pretty=1");
		Gson gson1 = new Gson();
		try {
			ArrayList<Comments> lat_object = new ArrayList<Comments>();
			ArrayList<Comments> lon_object = new ArrayList<Comments>();
			double lat_gte = current_location.getLatitude()-radius;
			double lat_lte = current_location.getLatitude()+radius;
			double lon_gte = current_location.getLongitude()-radius;
			double lon_lte = current_location.getLongitude()+radius;
			String query_range = "{\"query\":{\"bool\" : {\"must\" : {\"range\" : {\"lat\" : { \"gte\" : "+lat_gte+", \"lte\" : "+lat_lte+",\"boost\":0.0 }}},\"must\" : {\"range\" : {\"lon\" : { \"gte\" : "+lon_gte+", \"lte\" : "+ lon_lte+", \"boost\":0.0}}}}}}";
			String query_range2 = "{\"query\":{\"bool\" : {\"must\" : {\"range\" : {\"lat\" : { \"gte\" : "+lat_gte+", \"lte\" : "+lat_lte+",\"boost\":0.0 }}},\"must\":{\"match\":{\"sub_comments_ID\":0}},\"must\" : {\"range\" : {\"lon\" : { \"gte\" : "+lon_gte+", \"lte\" : "+ lon_lte+", \"boost\":0.0}}}}}}";
			// these are unused query , this is just for testing
			//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":-200,\"lte\":200,\"boost\":0.0} }}}";
			//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":"+lat_gte+",\"lte\":"+ lat_lte +",\"boost\":0.0} }}}";
			///String query2 = "{\"query\":{\"range\":{\"lon\":{\"gte\":"+lon_gte+",\"lte\":"+ lon_lte +",\"boost\":0.0} }}}";
			//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":-200,\"lte\":200,\"boost\":0.0} }}}";
			//String query = "{\"query\":{\"range\":{\"lat\":{\"gte\":"+lat_gte+",\"lte\":"+ lat_lte +",\"boost\":0.0},\"lon\":{\"gte\":"+lon_gte+",\"lte\":"+ lon_lte +",\"boost\":0.0} }}}";
			//String query1 = "{\"query\":{\"query_string\":{\"default_field\":\"master_ID\",\"query\":15}}}";
			//String query_location ="{\"query\": {\"geo_shape\": {\"location\": {\"shape\": {\"type\": \"envelope\",\"coordinates\": [[13, 53],[14, 52]]}}}}}";
			StringEntity entity = new StringEntity(query_range2);
			httpPost.setHeader("Accept","application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpPost);
			String json1 = getEntityContent(response);
			System.out.println(response.getStatusLine().toString()+"status");
			System.out.println(json1+"holy");
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>(){}.getType();
			ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(json1, elasticSearchSearchResponseType);

			for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
				Comments comms = r.getSource();

				//check weath the comment if already in the arraylist, if not then add it in there
				int flag=0;
				for (Comments com : date_comment_array)
				{ // turn on the flag if object is already inside the arary
				if(com.getMaster_ID()==comms.getMaster_ID())
				{
				flag =1 ;
				break;
				}
				}
				// if flag not turned on then add the object into the arraylsit
				if (flag==0)
				{	
				  float DistanceResult [] = new float[10]; 
				  current_location.distanceBetween(current_location.getLatitude(),current_location.getLongitude(),comms.getLat(),comms.getLon(),DistanceResult);
				  comms.setDistance(DistanceResult[0]);
				  date_comment_array.add(comms);
				}
				Collections.sort(date_comment_array, new compara());
				for(Comments com : date_comment_array)
				{
				  System.out.println("distance:"+com.getDistance());
				}
			    }
			
			
			}
	      catch (ClientProtocolException e) {

			System.out.println("client exe");
			e.printStackTrace();
		} catch (IOException e) {

			System.out.println("IO exe");
			e.printStackTrace();}
		  catch (NullPointerException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		  }
		catch (RuntimeException e) {

			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		
		return date_comment_array;
	}
	


	/**
	 * get the http response and return json string
	 */
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:"+json);
		return json;
	}

	
}
