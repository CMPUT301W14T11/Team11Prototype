package com.example1.locationapp.test;

import com.example1.locationapp.SubCommetsRead;


import Model.IDModel;
import android.test.ActivityInstrumentationTestCase2;

/**
 * in this IDModelTest getId_for_master() and setId_for_mater() will be
 * tested.
 * @author qyu4
 *
 */

public class IDModelTest extends ActivityInstrumentationTestCase2<SubCommetsRead>{

	
	



	public IDModelTest() {
		super(SubCommetsRead.class);
	
	}
	private int testModel = 2;
	@SuppressWarnings("unused")
	private IDModel model = new IDModel(testModel);
	
	protected void setUp() throws Exception { 
		super.setUp();	
	}
	/**
	 * Initialize testModel as 2 and initialize a IDModel with 2.
	 * getID will be checked correct if getId_for_master returns 2.
	 */
	public void testGetIDMethod()
	{

		IDModel getTest = new IDModel(testModel);
		assertEquals("2 should equal to 2",2, getTest.getId_for_master());

	}
	/**
	 * Initialize testModel as 2 and initialize a IDModel with 2.
	 * using setId_forMaster method to set an id to be testModel+1. 
	 * if getId_for_master is correct, then getId_for_master will return 3.
	 */
	public void testSetIDMethod()
	{
		IDModel setTest = new IDModel(testModel);
		setTest.setId_for_master(testModel+1);

		assertEquals("3 should equal to 3",3, setTest.getId_for_master());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
