package com.example1.locationapp.test;

import java.util.ArrayList;

import com.example1.locationapp.MainActivity;

import Model.Comments;
import Model.FavouriteModel;
import Model.UserModel;

import android.test.ActivityInstrumentationTestCase2;



public class UserModelTest extends  ActivityInstrumentationTestCase2<MainActivity> {
    private UserModel user;
    private FavouriteModel faviourte;
    private ArrayList<FavouriteModel> faviourteList = new ArrayList<FavouriteModel>();
    private ArrayList<FavouriteModel> faviourteList2;
    private Comments comment;
    private ArrayList<Comments> comm;
    public UserModelTest() {
        super(MainActivity.class);
        // TODO Auto-generated constructor stub
    }


//    protected void setUp() throws Exception {
//        super.setUp();
//        user = new UserModel("test",null);
//       
//       
//    }
//   
    public void testGetName()
    {
        assertEquals("not equal","test",user.getUser_name());
    }
    /*
    public void testAddFav(){
        comment = new Comments(0, 0, 0, 0, null, null, null, null, 0, 0);
        comm.add(comment);
        faviourte=new FavouriteModel("test", comment, comm);
        //user.addFaviourte(faviourte);
        faviourteList.add(faviourte);
        faviourteList2=user.getFaviourte();
        assertNull(faviourteList);
    }*/
   
}