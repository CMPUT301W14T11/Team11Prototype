package com.example1.locationapp.test;

<<<<<<< HEAD
import android.test.ActivityInstrumentationTestCase2;
=======
import com.example1.locationapp.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
>>>>>>> 2feab0d0e0880e6f4c7e19778661872d989d75f7
import Model.Comments;
import Model.IDModel;
import junit.framework.TestCase;

<<<<<<< HEAD
public class CommentsModelTest extends ActivityInstrumentationTestCase2 <IDModel>{
=======
public class CommentsModelTest extends ActivityInstrumentationTestCase2<MainActivity>{
>>>>>>> 2feab0d0e0880e6f4c7e19778661872d989d75f7

	
	

	public CommentsModelTest(Class<MainActivity> activityClass) {
		super(activityClass);
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
