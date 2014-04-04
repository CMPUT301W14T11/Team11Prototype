package com.example1.locationapp.test;

import java.util.ArrayList;

import Model.FavouriteComment;
import Model.FavouriteModel;
import Model.UserModel;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.MainActivity;



public class UserModelTest extends  ActivityInstrumentationTestCase2<MainActivity> {
    private UserModel user;
    private FavouriteModel favourite;
    private ArrayList<FavouriteModel> faviourteList = new ArrayList<FavouriteModel>();
    private ArrayList<FavouriteModel> faviourteList2;
    private FavouriteComment comment;
    private ArrayList<FavouriteComment> comm;
    
    public UserModelTest() {
        super(MainActivity.class);
    }


    protected void setUp() throws Exception { 
		super.setUp();
		user = new UserModel();
	}
    
    /**
     * test for getUser_name method and setUser_name method
     */
    public void testGetName()
    {
    	user.setUser_name("test");
        assertEquals("not equal","test",user.getUser_name());
    }
    
    /**
     * test for addFaviourte method to see if favourite is added successfully
     */
    public void test_getFavourite()
    {
    	favourite = new FavouriteModel("test", comment, comm);
    	user.addFaviourte(favourite);
    	faviourteList.add(favourite);
    	assertEquals("Favourite didn't add", user.getFaviourte(), faviourteList);
    }
    
    
    

   
}