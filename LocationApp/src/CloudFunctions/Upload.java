package CloudFunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Model.Comments;
import Model.IDModel;

import com.example1.locationapp.ElasticSearchResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Upload {
	private static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	private static final String MASTERCOMMENT = "emouse/";
	private static final String IDSERVER = "http://cmput301.softwareprocess.es:8080/testing/lab111/1";
	private Gson gson = new Gson();
	public Upload()
	{
		
	}
	/**upload Comment to server, and also increas the counter
	 * of the IDModel object,so there is no need to update the counter again
	 * @param comment
	 */
	public void uploadComment(Comments comment)
	{
		
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			//upload the comment
			comment.setMaster_ID(new Upload().downloadIDModel().getId_for_master());
			HttpPost httpPost = new HttpPost(SERVER + MASTERCOMMENT + comment.getMaster_ID());
			StringEntity data = new StringEntity(gson.toJson(comment));
			httpPost.setEntity(data);
			httpPost.setHeader("Accept", "application/json");
			HttpResponse response = httpclient.execute(httpPost);
			//increase the counter of the IDModel then upload to server
			

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**download IDModel from the server
	 * 
	 * @return
	 */
	public IDModel downloadIDModel()
	{   IDModel model = null;
		try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(IDSERVER);
		httpget.addHeader("Accept", "application/json");
		HttpResponse response = httpclient.execute(httpget);
		String json = getEntityContent(response);
		Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<IDModel>>() {
		}.getType();
		ElasticSearchResponse<IDModel> esResponse = gson.fromJson(json,
				elasticSearchResponseType);
		model = esResponse.getSource();
		
		}
		catch(Exception e)
		{
			
		}
		return model;
	}
	
	public void uploadIDModel()
	{	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpput = new HttpPost(IDSERVER);
		IDModel model = new Upload().downloadIDModel();
		int id = model.getId_for_master()+1;
		model.setId_for_master(id);
		StringEntity stringentity = new StringEntity(gson.toJson(model));
		httpput.setHeader("Accept", "application/json");
		httpput.setEntity(stringentity);
		HttpResponse response = httpclient.execute(httpput);
		}
		catch(Exception e){}
	
	}
	
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:" + json);
		return json;
	}
	
	

}
