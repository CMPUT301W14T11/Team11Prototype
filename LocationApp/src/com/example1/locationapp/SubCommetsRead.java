package com.example1.locationapp;

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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Controller.LocalFileLoder;
import Controller.LocalFileSaver;
import Controller.SubCommentController;
import Model.Comments;
import Model.FavouriteModel;
import Model.IDModel;
import Model.UserModel;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SubCommetsRead extends Activity {
	public static final String SERVER = "http://cmput301.softwareprocess.es:8080/cmput301w14t11/";
	public static final String MASTERCOMMENT = "emouse/";
	private ListView listViewSubComment;
	private EditText editText;
	private Button button1;
	private cutadapter ListAdapter;
	private ArrayList<Comments> comment_list; 
	private Bitmap bitmap;
	private int number; 
	private Context content;
	private Location location;
    private GPSTracker gps;
    private HttpClient httpclient;
    private double longitude;
    private double latitude;
    private int subCoId=1;
    private Gson gson = new Gson();
    double radius= 0.01;
    private IDModel id_obj;
    private int ServerID;
    private LocalFileLoder fileLoder = new LocalFileLoder(this);
    private LocalFileSaver fileSaver = new LocalFileSaver(this);
    private UserModel user;
    private Comments comment1;
    private int ggggtest = 1;
    private SubCommentController subController = new SubCommentController(comment1);
    //private Comments mainComment;
    //private EnterCommentsActivity callEnterComments = new EnterCommentsActivity();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sub_commets_read);
	listViewSubComment = (ListView)findViewById(R.id.listViewSubComments);
	editText = (EditText)findViewById(R.id.editTextSubmitSubComments);
	button1 = (Button)findViewById(R.id.buttonSaveSubComments);
	button1.setText("Submit Sub Comments");
    comment_list = new ArrayList<Comments>();
    httpclient= new DefaultHttpClient();
	Intent intent = getIntent();
	number=intent.getIntExtra("masterID", 0);
	//mainComment=intent.getExtra("main");
	Toast.makeText(getBaseContext(), number+"", Toast.LENGTH_SHORT).show();
	id_obj = new IDModel(0);
    // add an example to test the list
    //comment_list.add(new Comments(1,0,0, 0, "It works", "Tesing", new Date(), null, 123, 123, null));
	gps = new GPSTracker(this);
	ListAdapter = new cutadapter(SubCommetsRead.this, R.layout.listlayout, comment_list);
	new AsyncTask<Void, Void, Void>()
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			get_comments("get some comments man!");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ListAdapter.notifyDataSetChanged();
		}
		
	}.execute();
	
	content = this;
	if (gps.canGetLocation){
		location = gps.getLocation();
		System.out.println(location+"wocao");
		gps.stopUsingGPS();
	}
	else
	{
		gps.showSettingsAlert();
	}
			
			
    System.out.println("lol"+location);
			
    longitude = location.getLongitude();
    latitude =location.getLatitude();
    View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footlayout, null, false);
    
    listViewSubComment.addFooterView(footerView);
    
	listViewSubComment.setAdapter(ListAdapter);
	footerView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new AsyncTask<Void, Void, Void>()
			{

				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					get_comments("get some comments man!");
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					ListAdapter.notifyDataSetChanged();
				}
				
			}.execute();
		}
	});
	
	
	button1.setOnClickListener(new MyButton1Listener());
	}
	/**
	 * there are two sub-munu list which is using for saving favourite and another is 
	 * using for save, it will help us save the comments and sub-comments in the file.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		ArrayList<Comments> sub = new ArrayList<Comments>();
		//user = fileLoder.loadFromFile();
		switch (item.getItemId())
		{
		case R.id.fav:
			user = new UserModel();
			user = fileLoder.loadFromFile();
			//Comments maincom = comment_list.get(0); 
			//for (int i =1;i<comment_list.size();i++)
				//sub.add(comment_list.get(i));
			
			//FavouriteModel favi =  new FavouriteModel(user.getUser_name(), maincom, sub);
			//user.addFaviourte(favi);
			fileSaver.saveInFile(user);
			break;
			
		case R.id.save:
			user = new UserModel();
			user = fileLoder.loadFromFile();
			fileSaver.saveInFile(user);
			// this is to start change location activity
			// request code is 7

			break;
			
		}
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.sub_commets_read, menu);
	return true;
	}
	class MyButton1Listener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String title = editText.getText().toString();
			radius=radius+0.01;
	        if ("".equals(title))
	        {   
	        	
	        	Toast.makeText(getBaseContext(), "Title is empty! add some words please!", Toast.LENGTH_SHORT).show();
	        	
	        }else{
			new AsyncTask<Void,Void,Void>()
	    	{   
				ProgressDialog dialog1= new ProgressDialog(content);
	    		
	    		@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					dialog1.setTitle("Loading cause your internet is too slow!");
					dialog1.show();
					super.onPreExecute();
					new AsyncTask<Void	,Void	, Void>()
					{

						@Override
						protected Void doInBackground(Void... params) {
							// TODO Auto-generated method stub
							ServerID = get_id();
							ServerID++;
							System.out.println(comment_list.size()+"size"+ServerID);
							return null;
						}
						
					}.execute();
					
				}
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					if(bitmap==null)
				       { 	   
				         final Comments new_comment = new Comments(0,number,subCoId,0,editText.getText().toString(),editText.getText().toString(),new Date(),location,longitude,latitude);
				         subController.insertMaster(new_comment, ServerID);
				         subCoId++;
				       }
				       else
				       { System.out.println("image posted");
	                         			       
				         String encode_image= convert_image_to_string(bitmap);
				    	 final
				    	 Comments new_comment = new Comments(0,number,subCoId,0,editText.getText().toString(),editText.getText().toString(),new Date(),location,longitude,latitude,encode_image);
				    	 subController.insertMaster(new_comment,ServerID);
				    	 subCoId++;
				       }
					
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					new AsyncTask<Void, Void, Void>()
					{

						@Override
						protected Void doInBackground(Void... params) {
							// TODO Auto-generated method stub
							id_obj.setId_for_master(ServerID);
							try {
								insert(id_obj);
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
						}
						
					}.execute();
					
					
				}
	    		
	    	}.execute();
	       
	    	setResult(RESULT_OK);
	    	finish();
		}
		}
	}
	public void insert(IDModel id) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub)
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/lab111/1");
		StringEntity stringentity = null;
        
		try {
			stringentity = new StringEntity(gson.toJson(id));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		catch (RuntimeException e) {
			// TODO: handle exception
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
			
		}
		catch (NullPointerException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		catch (RuntimeException e) {
			// TODO: handle exception
			Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
		}
		
	}
	public int get_id() {
		// TODO Auto-generated method stub
		IDModel id_toReturn ;// this is ID object from server
		int id = 0;
		try{
		//IDModel id_toReturn ;// this is ID object from server
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/lab111/1");
		httpget.addHeader("Accept","application/json");
		
			HttpResponse response = httpclient.execute(httpget);
			
			String json = getEntityContent(response);
			
			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<IDModel>>(){}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<IDModel> esResponse = gson.fromJson(json, elasticSearchResponseType);
			// We get the recipe from it!
			id_toReturn = esResponse.getSource();
			System.out.println();
			
			id = id_toReturn.getId_for_master();
			
			//System.out.println(recipe.toString());
			//httpget.releaseConnection();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      catch (NullPointerException e) {
		// TODO: handle exception
		Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
	    }
		  catch (RuntimeException e) {
		// TODO: handle exception
		Toast.makeText(content, "no internet", Toast.LENGTH_SHORT).show();
	}
	    return id; 
		
		
	}
	
	
	public String  convert_image_to_string(Bitmap bitmap)
	 {
		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		 bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		 byte[] byteArray = byteArrayOutputStream .toByteArray();
		 String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
		return encoded;
	 }
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
	
	public  void get_comments(String url)
	{
	HttpPost httpPost= new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t11/emouse/_search?pretty=1");
	//HttpGet  httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/emouse/_search?pretty=1");
	Gson gson1 = new Gson();
	try {
		ArrayList<Comments> lat_object = new ArrayList<Comments>();
		ArrayList<Comments> lon_object = new ArrayList<Comments>();
		String query_range2 = "{\"query\":{\"bool\":{\"must\":{\"match\":{\"master_ID\":"+number+"}}} }}";
		StringEntity entity = new StringEntity(query_range2);
		httpPost.setHeader("Accept","application/json");
		httpPost.setEntity(entity);
		HttpResponse response = httpclient.execute(httpPost);
		String json1 = getEntityContent(response);
		System.out.println(json1+"holy");
		Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Comments>>(){}.getType();
		ElasticSearchSearchResponse<Comments> esResponse = gson1.fromJson(json1, elasticSearchSearchResponseType);
//<<<<<<< HEAD
		for (ElasticSearchResponse<Comments> r : esResponse.getHits()) {
			Comments comms = r.getSource();

			//check weath the comment if already in the arraylist, if not then add it in there
			int flag=0;
			for (Comments com : comment_list)
			{ // turn on the flag if object is already inside the arary
			if(com.getMaster_ID()==comms.getMaster_ID())
			{
			flag =1 ;
			comment_list.add(comms);
			break;
			}
			}
			// if flag not turned on then add the object into the arraylsit
			if (flag==0)
			{
			comment_list.add(comms);
			}

		    }
		//System.out.println(comment_list.size()+"size"+ServerID);
		

		    
		}
      catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		System.out.println("client exe");
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("IO exe");
		e.printStackTrace();}
	}
	
	
	
}


