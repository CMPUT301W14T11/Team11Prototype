package InternetConnection;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.IDModel;
import android.content.Context;
import android.widget.Toast;
/**
 * The intetnet controller to upload and download from server
 * @author yazhou
 *
 */
public class ConnectToInternet {

	
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private Gson gson = new Gson();
	
	
	/**
	 * this is to send IDModel to the server
	 * @param id -- model id
	 * @param content -- context of activity
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public void insert(IDModel id, Context content) throws IllegalStateException, IOException {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://cmput301.softwareprocess.es:8080/testing/lab111/1");
		StringEntity stringentity = null;

		try {
			stringentity = new StringEntity(gson.toJson(id));
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (NullPointerException e) {
			
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {
			
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		httpPost.setHeader("Accept", "application/json");

		httpPost.setEntity(stringentity);


		HttpResponse response = null;

		try {
			response = httpclient.execute(httpPost);

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (NullPointerException e) {
			
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {
			
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}

	}
	

	
	/**
	 * from elastic search to get json from the response
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(response.getEntity().getContent())));
		String output;
		String json = "";
		while ((output = br.readLine()) != null) {
			json += output;
		}
		return json;
	}	
	
	

	/**
	 * download IDModel from the server
	 * @param content -- context of activity
	 * @return
	 * id
	 */
	public int get_id(Context content) {
		
		IDModel id_toReturn;// this is ID object from server
		int id = 0;
		try {
			// IDModel id_toReturn ;// this is ID object from server
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://cmput301.softwareprocess.es:8080/testing/lab111/1");
			httpget.addHeader("Accept", "application/json");

			HttpResponse response = httpclient.execute(httpget);

			String json = getEntityContent(response);

			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<IDModel>>() {
			}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<IDModel> esResponse = gson.fromJson(json,
					elasticSearchResponseType);
			// We get the recipe from it!
			id_toReturn = esResponse.getSource();

			id = id_toReturn.getId_for_master();

		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (NullPointerException e) {
			
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {
			
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		return id;

	}
}
