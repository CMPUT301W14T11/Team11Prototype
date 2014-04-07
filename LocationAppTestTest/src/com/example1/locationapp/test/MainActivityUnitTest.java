package com.example1.locationapp.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.MainActivity;

public class MainActivityUnitTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	Instrumentation instrumentation;
	Activity activity;
	//EditText textInput;
	
	public MainActivityUnitTest(String name) {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
