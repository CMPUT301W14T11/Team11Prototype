package com.example1.locationapp.test;

import com.example1.locationapp.NewUserActivity;
import com.example1.locationapp.R;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.EditText;
/**
 * in this file new user information will be checked.
 * @author yazhou
 *
 */
public class NewUserActivityTest extends ActivityInstrumentationTestCase2<NewUserActivity> {

	@SuppressWarnings("unused")
	private Instrumentation instrumentation;
	private Activity activity;
	public NewUserActivityTest() {
		super(NewUserActivity.class);
		
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
	/**
	 * test view exists
	 */
	public void testView()
	{
		EditText edittext  = (EditText) activity.findViewById(R.id.editText1);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
					        edittext);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
