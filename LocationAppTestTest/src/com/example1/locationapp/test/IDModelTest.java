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
	private int testModel = 2;
	private IDModel model = new IDModel(testModel);
	
	protected void setUp() throws Exception { 
		super.setUp();
		

		
	}
	/**
	 * for method insertMater(comment, number),comment is the comment insert to server
	 * number is total comment 
	 */
	public void testGetIDMethod()
	{

		IDModel getTest = new IDModel(testModel);
		assertEquals("2 should equal to 2",2, getTest.getId_for_master());

	}
	/**
	 * for method insertMater(comment, number),comment is the comment insert to server
	 * number is total comment 
	 */
	public void testSetIDMethod()
	{
		IDModel setTest = new IDModel(testModel);
		setTest.setId_for_master(testModel+1);

		assertEquals("3 should equal to 3",3, setTest.getId_for_master());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
