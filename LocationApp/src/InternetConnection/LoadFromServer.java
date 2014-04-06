package InternetConnection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Model.Comments;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * use this function to download comments from the serer
 * @author yazhou
 *
 */
public class LoadFromServer {
	private Gson gson= new Gson();
	private HttpClient httpclient= new DefaultHttpClient();
	private ConnectToInternet connect = new ConnectToInternet();
	public ArrayList<Comments> search(ArrayList<Comments> comment_list2, String query_range2){
		try{
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
		StringEntity entity = new StringEntity(query_range2);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(entity);
		HttpResponse response = httpclient.execute(httpPost);
		
		String json1 = connect.getEntityContent(response);
		System.out.println(response.getStatusLine().toString() + "status");
		System.out.println("search result is the:"+json1);
		Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>() {
		}.getType();
		ElasticSearchSearchResponse<Comments> esResponse = gson.fromJson(
				json1, elasticSearchSearchResponseType);
		for (ElasticSearchResponse<Comments> r : esResponse.getHits())
		{
			comment_list2.add(r.getSource());
		}
		}catch (Exception e) {
		}
		
		return comment_list2;
	}
}
