package com.example1.locationapp.test;

import java.util.ArrayList;

import Controller.SubCommentController;
import Model.Comments;
import android.provider.Settings.System;
import android.test.ActivityInstrumentationTestCase2;

import com.example1.locationapp.SubCommetsRead;

public class SubCommentsTest extends ActivityInstrumentationTestCase2<SubCommetsRead> {
	public SubCommentsTest() {
		super(SubCommetsRead.class);
		// TODO Auto-generated constructor stub
	}


	Comments comments ;
	SubCommetsRead subc = new SubCommetsRead();
	ArrayList<Comments> comm = new ArrayList<Comments>();
	SubCommentController subCon= new SubCommentController(comments);
	protected void setUp() throws Exception {
		super.setUp();
		comments = new Comments(0, 0, 0, 0, "hello", "sub", null, null, 0, 0,null);
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
	* Tests if get_id() function can be used to find out the amount of comments, the number should not be 0 after insert a subcomments
	*/
	public void testGetID()
	{
		assertNotSame(0, subc.get_id());
	}
	/**
	* Tests if we can get ID of the above comments;
	*/
	public void testGetSubComments()
	{
		//comm=subc.get_comments(comm);
		//assertNotSame(0,comm.size());
	}
	public void testGetSub_comments_id(){
		assertEquals(0,comments.getSub_comments_ID());
	}
	public void testGetSub_ID(){
		assertEquals(0, comments.getSub_ID());
	}
	public void testTheComment(){
		assertEquals("hello", comments.getThe_comment());
	}
	public void testSubject_comment(){
		assertEquals("sub", comments.getSubject_comment());
	}
	public void testDate(){
		assertEquals(null,comments.getComment_date());
	}
	public void testLocation(){
		assertEquals(null,comments.getComment_location());
	}
	public void testGetDistance(){
		assertEquals(0,comments.getDistance());
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
