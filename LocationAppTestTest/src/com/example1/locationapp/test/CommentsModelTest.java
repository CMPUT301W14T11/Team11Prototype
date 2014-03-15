package com.example1.locationapp.test;

import android.test.ActivityInstrumentationTestCase2;
import Model.Comments;
import Model.IDModel;
import junit.framework.TestCase;

public class CommentsModelTest extends ActivityInstrumentationTestCase2 <IDModel>{

	
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
