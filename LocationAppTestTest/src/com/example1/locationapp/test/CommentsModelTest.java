package com.example1.locationapp.test;

import com.example1.locationapp.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import Model.Comments;

public class CommentsModelTest extends ActivityInstrumentationTestCase2<MainActivity>{

	
	

	public CommentsModelTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	Comments comments ;
	protected void setUp() throws Exception {
		super.setUp();
		comments = new Comments(0, 0, 0, 0, null, null, null, null, 0, 0);
	}
	
	public void testComments()
	{
		comments.getDistance();
		assertEquals(0, comments.getMaster_ID());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
