package com.example1.locationapp.test;

import java.util.ArrayList;
import java.util.Date;

import Model.Comments;
import Model.SubCommentModel;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.SubCommetsRead;

/**
 * in this file subComments model will be checked.
 * @author yazhou
 *
 */
public class SubCommentsTest extends ActivityInstrumentationTestCase2<SubCommetsRead> {
	public SubCommentsTest() {
		super(SubCommetsRead.class);
	}


	private Comments comments ;
	@SuppressWarnings("unused")
	private SubCommetsRead subc = new SubCommetsRead();
	@SuppressWarnings("unused")
	private ArrayList<Comments> comm = new ArrayList<Comments>();
	private SubCommentModel subCon= new SubCommentModel(comments);
	private Date date = new Date();
	protected void setUp() throws Exception {
		super.setUp();
		comments = new Comments(0, 0, 0, 0, "hello", "sub", date, 0, 0,null);
		subCon.insertMaster(comments, 0);
	}
	/**
	* Tests if we can get ID of the above comments;
	*/
	public void testSubComments()
	{
		assertEquals(0, comments.getMaster_ID());
	}

	/**
	* Tests if we can get ID of the above comments;
	*/
	public void testGetSubComments()
	{
		//comm=subc.get_comments(comm);
		//assertNotSame(0,comm.size());
	}
	/*
	* Tests if we can get Sub comments ID of the above comments;
	*/
	public void testGetSub_comments_id(){
		assertEquals(0,comments.getSub_comments_ID());
	}
	/*
	* Tests if we can get SubID of the above comments;
	*/
	public void testGetSub_ID(){
		assertEquals(0, comments.getSub_ID());
	}
	/*
	* Tests if we can the title of the above comments;
	*/
	public void testTheComment(){
		assertEquals("hello", comments.getThe_comment());
	}
	/*
	* Tests if we can get the subject of the above comments;
	*/
	public void testSubject_comment(){
		assertEquals("sub", comments.getSubject_comment());
	}
	/*
	* Tests if we can get Date of the above comments;
	*/
	public void testDate(){
		assertEquals(date,comments.getComment_date());
	}
	
	/*
	* Tests if we can get Distance of the above comments;
	*/
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
