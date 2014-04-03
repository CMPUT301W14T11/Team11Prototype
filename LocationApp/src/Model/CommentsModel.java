package Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import InternetConnection.ConnectToInternet;
import android.graphics.Bitmap;
import android.util.Base64;

import com.google.gson.Gson;

public class CommentsModel {
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private Gson gson= new Gson();
	
	
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

}
