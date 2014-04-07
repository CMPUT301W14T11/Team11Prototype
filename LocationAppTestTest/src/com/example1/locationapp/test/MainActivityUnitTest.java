package com.example1.locationapp.test;

import java.util.ArrayList;
import java.util.Date;

import org.apache.http.impl.client.DefaultHttpClient;

import Model.Comments;
import Model.SubCommentModel;
import android.app.Activity;
import android.app.Instrumentation;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.MainActivity;

public class MainActivityUnitTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	Instrumentation instrumentation;
	Activity activity;
	//EditText textInput;
	SubCommentModel model;
	ArrayList<Comments> array;
	ArrayList<Comments> array2;
	public MainActivityUnitTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		model = new SubCommentModel(new Comments(0, 0, 0, 0, "hello", "hi", new Date(),99,99,"li"));
		array = new ArrayList<Comments>();
		}
	/**
	 * test activity
	 */
	public void testActivity()
	{
		assertNotNull(model);
	}
	/**
	 * test array for comments
	 */
	public void testMasterModel()
	{
		assertEquals(0,array.size());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
