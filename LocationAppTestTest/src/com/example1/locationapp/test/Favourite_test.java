package com.example1.locationapp.test;
import java.util.ArrayList;

import Model.FavouriteComment;
import Model.FavouriteModel;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.Favourite;




public class Favourite_test extends ActivityInstrumentationTestCase2<Favourite>
{
	private String username ="User_test_name";
	private FavouriteComment comment = null;
	private ArrayList<FavouriteComment> subcomment = null;
	
	private int user_id1 = 111;
	private int masterid1 = 112;
	private int sid1 = 114;
	private int subid1 = 113;
	private String title1 = "gggg";
	private String subject1 = "ggggjob";
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
		FavouriteComment test = new FavouriteComment();
		FavouriteComment test1 =  new FavouriteComment();
		test.setID(1);
		test1.setID(2);
		
		
		FavouriteModel fa = new FavouriteModel(username, test, subcomment);
		FavouriteModel fai = new FavouriteModel(username, test1, subcomment);
		assertNotSame("fai should not equal to fa", fa.getComment(),fai.getComment());
		
	}
	
	
	
	public void test_getSubComment_null(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", null,fa.getSubComment());
	}
	
	public void test_subComment_which_is_not_null(){
		FavouriteComment test = new FavouriteComment();
		FavouriteComment test1 = new FavouriteComment();
		ArrayList<FavouriteComment> test_list = new ArrayList<FavouriteComment>();
	    
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
