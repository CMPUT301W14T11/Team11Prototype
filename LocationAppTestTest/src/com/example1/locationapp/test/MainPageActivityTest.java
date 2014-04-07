package com.example1.locationapp.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import com.example1.locationapp.MainPage;
/**
 * in this file main page will be checked.
 * @author yazhou
 *
 */
public class MainPageActivityTest extends ActivityInstrumentationTestCase2<MainPage> {
	Instrumentation instrumentation;
	private Activity activity;
	public MainPageActivityTest() {
		super(MainPage.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}
	/**
	 * test activity not null 
	 */
	public void testActivity()
	{
		assertNotNull(activity);
	}
	
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
