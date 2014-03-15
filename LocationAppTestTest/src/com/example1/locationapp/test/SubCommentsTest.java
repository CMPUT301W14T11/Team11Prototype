package com.example1.locationapp.test;

import java.util.ArrayList;

import Controller.SubCommentController;
import Model.Comments;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.SubCommetsRead;

public class SubCommentsTest extends ActivityInstrumentationTestCase2<SubCommetsRead> {
	private Comments cc;
	private SubCommetsRead subComments; //= new SubCommetsRead();
	//private SubCommentController subCont = new SubCommentController(cc);
	private ArrayList<Comments>comTest;
	//Comments comm ;
	public SubCommentsTest() {
		super(SubCommetsRead.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		comTest = new ArrayList<Comments>();
		//comm = new Comments(0, 0, 0, 0, null, null, null, null, 0, 0);
	}
	public void subGetInsertCommentsTest(){
		//subCont.insertMaster(comm, 0);
		comTest=subComments.get_comments(comTest);
		System.out.println("bag"+comTest);
		assertNotNull(comTest);
		
	}
}
