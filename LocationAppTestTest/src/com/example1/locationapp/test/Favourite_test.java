package com.example1.locationapp.test;
import java.util.ArrayList;

import Model.FavouriteComment;
import Model.FavouriteModel;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.Favourite;

/**
 * in this file favourite model will be checked.
 * @author yazhou
 *
 */


public class Favourite_test extends ActivityInstrumentationTestCase2<Favourite>
{
	private String username ="User_test_name";
	private FavouriteComment comment = null;
	private ArrayList<FavouriteComment> subcomment = null;
	
	public Favourite_test()
	{

		super(Favourite.class);
		// TODO Auto-generated constructor stub
	}
	/**
	 * test user name
	 */
	public void test_getusername(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", "User_test_name",fa.getUsername());
	}
	/**
	 * test getting comments
	 */
	public void test_getComment(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", null,fa.getComment());
	}
	/**
	 * test different comments
	 */
	public void test_diff_comments(){
		FavouriteComment test = new FavouriteComment();
		FavouriteComment test1 =  new FavouriteComment();
		test.setID(1);
		test1.setID(2);
		
		
		FavouriteModel fa = new FavouriteModel(username, test, subcomment);
		FavouriteModel fai = new FavouriteModel(username, test1, subcomment);
		assertNotSame("fai should not equal to fa", fa.getComment(),fai.getComment());
		
	}
	
	
	/**
	 * test sub comment 
	 */
	public void test_getSubComment_null(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", null,fa.getSubComment());
	}
	/**
	 * test sub comment is not null
	 */
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
