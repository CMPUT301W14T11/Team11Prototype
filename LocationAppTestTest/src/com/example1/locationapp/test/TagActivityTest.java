package com.example1.locationapp.test;

import com.example1.locationapp.R;
import com.example1.locationapp.TagActivity;
import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;


public class TagActivityTest extends ActivityInstrumentationTestCase2<TagActivity> {
	private Instrumentation instrumentation;
	private Activity activity;
	public TagActivityTest() {
		super(TagActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}
	/**
	 * testing activty
	 */
	public void testActivity()
	{
		assertNotNull(activity);
	}
	/**
	 * testing tags button
	 */
	public void testTagDone()
	{
		Button button  = (Button) activity.findViewById(R.id.tagbutton2);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
					        button);
	}
	/**
	 * testing tag enter button
	 */
	public void testTagEnter()
	{
		Button button  = (Button) activity.findViewById(R.id.tagbutton1);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
					        button);
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
