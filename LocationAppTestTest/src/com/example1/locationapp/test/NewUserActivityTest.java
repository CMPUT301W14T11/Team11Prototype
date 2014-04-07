package com.example1.locationapp.test;

import com.example1.locationapp.NewUserActivity;
import com.example1.locationapp.R;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import junit.framework.TestCase;

public class NewUserActivityTest extends ActivityInstrumentationTestCase2<NewUserActivity> {

	Instrumentation instrumentation;
	Activity activity;
	public NewUserActivityTest() {
		super(NewUserActivity.class);
		
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
		EditText edittext  = (EditText) activity.findViewById(R.id.editText1);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
					        edittext);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
