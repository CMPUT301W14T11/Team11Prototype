package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.example1.locationapp.ElasticSearchResponse;
import com.example1.locationapp.ElasticSearchSearchResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.Comments;
/**
 * the class CommentSort is used to sort comments by date and picture individually.
 */
public class CommentSort {
	Location current_location;
	double radius= 0.01;
	HttpClient httpclient;
	ArrayList<Comments> comment_array;
	Context content;
	
	public Comments sortByDate(Comments comment){
		HttpPost httpPost= new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
		Gson gson1 = new Gson();
		
		try {

			double lat_gte = current_location.getLatitude()-radius;
			double lat_lte = current_location.getLatitude()+radius;
			double lon_gte = current_location.getLongitude()-radius;
			double lon_lte = current_location.getLongitude()+radius;
			String query_range = "{\"query\":" +
									"{\"bool\" : " +
										"{\"must\" : " +
											"{\"range\" : " +
												"{\"lat\" : { \"gte\" : "+lat_gte+", \"lte\" : "+lat_lte+" }}}," +
										 "\"must\" : " +
											"{\"range\" : " +
												"{\"lon\" : { \"gte\" : "+lon_gte+", \"lte\" : "+ lon_lte+" }}}}}}";
			
			
			
			

			StringEntity entity = new StringEntity(query_range);
			httpPost.setHeader("Accept","application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpclient.execute(httpPost);
			String json1 = getEntityContent(response);
			System.out.println(json1+"------> print range search comments");
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>(){}.getType();
			ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(json1, elasticSearchSearchResponseType);

			for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
				Comments comms = r.getSource();

				//check weath the comment if already in the arraylist, if not then add it in there
				int flag=0;
				for (Comments com : comment_array)
				{ // turn on the flag if object is already inside the arary
					if(com.getMaster_ID()==comms.getMaster_ID())
					{
						flag =1;
						break;
					}
				}
				// if flag not turned on then add the object into the arraylsit
					if (flag==0)
					{
						comment_array.add(comms);
					}

				}
			
			}
	      catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println("client exe");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO exe");
			e.printStackTrace();}
		  catch (NullPointerException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		  }
		catch (RuntimeException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		
		
		return comment;
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
