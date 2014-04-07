package com.example1.locationapp.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;

import com.example1.locationapp.MainPage;
import com.example1.locationapp.R;

public class MainPageActivityTest extends ActivityInstrumentationTestCase2<MainPage> {
	Instrumentation instrumentation;
	Activity activity;
	public MainPageActivityTest() {
		super(MainPage.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}
	
	public void testActivity()
	{
		assertNotNull(activity);
	}
	
	public void testView()
	{
		Button button  = (Button) activity.findViewById(R.id.button1);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
					        button);
	}
	public void testButton()
	{
		Button button  = (Button) activity.findViewById(R.id.guest);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
					        button);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
