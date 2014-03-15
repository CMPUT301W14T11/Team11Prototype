package com.example1.locationapp.test;

import com.example1.locationapp.EnterCommentsActivity;
import com.example1.locationapp.MainActivity;
import com.example1.locationapp.SubCommetsRead;

import Controller.SubCommentController;
import Model.Comments;
import Model.IDModel;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;



public class IDModelTest extends ActivityInstrumentationTestCase2<SubCommetsRead>{

	
	



	public IDModelTest() {
		super(SubCommetsRead.class);
		// TODO Auto-generated constructor stub
	}

	Comments comment1, comment2;
	IDModel idmodel;
	SubCommentController eca =  new SubCommentController(comment1);
	SubCommetsRead scr =  new SubCommetsRead();

	
	protected void setUp() throws Exception { 
		super.setUp();
		comment1 = new Comments(0, 0, 0, 0, null, null, null, null, 0, 0);

		eca.insertMaster(comment1, 0);
	}
	
	public void testGetId_for_master()
	{

		assertEquals(0, scr.get_id());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
