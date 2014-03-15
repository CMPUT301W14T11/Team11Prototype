package com.example1.locationapp.test;

import java.util.ArrayList;

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

	Comments comment1, comment2 ;
	SubCommetsRead iDModel = new SubCommetsRead();
	SubCommentController subCon= new SubCommentController(comment1);
	int matchCheck;

	
	protected void setUp() throws Exception { 
		super.setUp();
		comment1 = new Comments(0, 0, 0, 0, null, "IDMODEL", null, null, 0, 0,null);
		comment2 = new Comments(0, 0, 1, 0, null, "IDMODEL", null, null, 0, 0,null);

		subCon.insertMaster(comment1, 0);
		matchCheck = iDModel.get_id() + 1;
		subCon.insertMaster(comment2, 1);
		
	}
	/**
	 * for method insertMater(comment, number),comment is the comment insert to server
	 * number is total comment 
	 */
	public void testGetIDMethod()
	{

		assertEquals(matchCheck, iDModel.get_id());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
