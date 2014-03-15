package com.example1.locationapp.test;
import java.util.ArrayList;

import com.example1.locationapp.Favourite;


import Model.Comments;
import Model.FavouriteModel;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;




public class Favourite_test extends ActivityInstrumentationTestCase2<Favourite>
{
	private String username ="User_test_name";
	Comments comment = null;
	ArrayList<Comments> subcomment = null;

	public Favourite_test()
	{

		super(Favourite.class);
		// TODO Auto-generated constructor stub
	}
	
	public void test_getusername(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", "User_test_name",fa.getUsername());
	}
	
	public void test_getuserComment(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", null,fa.getComment());
	}
	
	public void test_getuserSubComment(){
		FavouriteModel fa = new FavouriteModel(username, comment, subcomment);
	    assertEquals("the output should equal to User_test_name", null,fa.getSubComment());
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
