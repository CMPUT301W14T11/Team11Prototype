package com.example1.locationapp.test;
import java.util.ArrayList;
import java.util.Date;

import com.example1.locationapp.Favourite;
import com.example1.locationapp.GPSTracker;



import Model.Comments;
import Model.FavouriteModel;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;




public class Favourite_test extends ActivityInstrumentationTestCase2<Favourite>
{
	private String username ="User_test_name";
	private Comments comment = null;
	ArrayList<Comments> subcomment = null;
	
	private int user_id1 = 111;
	private int masterid1 = 112;
	private int sid1 = 114;
	private int subid1 = 113;
	private String title1 = "gggg";
	private String subject1 = "ggggjob";
	private Location location1 = null;
	
	private Date the_date1 = new Date(System.currentTimeMillis());

	private double lon1 = 12.3322;
	private double lat1 = 12.3327;
	private String encode1 = "sssssss";
	
	public Favourite_test()
	{

		super(Favourite.class);
		// TODO Auto-generated constructor stub
	}
	
	public void test_getusername(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", "User_test_name",fa.getUsername());
	}
	
	public void test_getComment(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", null,fa.getComment());
	}
	
	public void test_diff_comments(){
		Comments test = new Comments(user_id1, masterid1, sid1, subid1, title1, subject1, the_date1, location1, lon1, lat1);
		Comments test1 =  new Comments(user_id1, masterid1, sid1, subid1, title1, subject1, the_date1, location1, lon1, lat1, encode1);
		
		
		FavouriteModel fa = new FavouriteModel(username, test, subcomment);
		FavouriteModel fai = new FavouriteModel(username, test1, subcomment);
		assertNotSame("fai should not equal to fa", fa.getComment(),fai.getComment());
		
	}
	
	
	
	public void test_getSubComment_null(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", null,fa.getSubComment());
	}
	
	public void test_subComment_which_is_not_null(){
		Comments test = new Comments(user_id1, masterid1, sid1, subid1, title1, subject1, the_date1, location1, lon1, lat1);
		Comments test1 =  new Comments(user_id1, masterid1, sid1, subid1, title1, subject1, the_date1, location1, lon1, lat1, encode1);
		ArrayList<Comments> test_list = new ArrayList<Comments>();
	    
	    test_list.add(test);
	    test_list.add(test1);
	    
		
		FavouriteModel fa = new FavouriteModel(username, comment, test_list);
		assertEquals("fai should not equal to fa", test_list,fa.getSubComment());
		
	}

	protected void setUp() throws Exception
	{

		super.setUp();
	}

	protected void tearDown() throws Exception
	{

		super.tearDown();
	}

}
