package com.example1.locationapp.test;

import java.util.ArrayList;
import java.util.Date;

import com.example1.locationapp.SubCommetsRead;

import Model.Comments;
import Model.SubCommentModel;
import android.provider.Settings.System;
import android.test.ActivityInstrumentationTestCase2;


public class SubCommentsTest extends ActivityInstrumentationTestCase2<SubCommetsRead> {
	public SubCommentsTest() {
		super(SubCommetsRead.class);
		// TODO Auto-generated constructor stub
	}


	Comments comments ;
	SubCommetsRead subc = new SubCommetsRead();
	ArrayList<Comments> comm = new ArrayList<Comments>();
	SubCommentModel subCon= new SubCommentModel(comments);
	Date date = new Date();
	protected void setUp() throws Exception {
		super.setUp();
		comments = new Comments(0, 0, 0, 0, "hello", "sub", date, null, 0, 0,null);
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
	* Tests if we can get location of the above comments;
	*/
	public void testLocation(){
		assertEquals(null,comments.getComment_location());
	}
	/*
	* Tests if we can get Distance of the above comments;
	*/
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
