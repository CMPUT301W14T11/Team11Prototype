package com.example1.locationapp.test;

import java.util.Date;

import com.example1.locationapp.EnterCommentsActivity;
import com.example1.locationapp.MainActivity;

import Model.Comments;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

/**
 * in this file comments model will be checked.
 * @author qyu4
 *
 */

public class CommentsModelTest extends ActivityInstrumentationTestCase2<EnterCommentsActivity>{

	
	
	Comments comments;
	public CommentsModelTest() {
		super(EnterCommentsActivity.class);
		// TODO Auto-generated constructor stub
	}


	protected void setUp() throws Exception { 
		super.setUp();
		comments = new Comments(0, 0, 0, 0, null, null, null, null, 0, 0);
	}
	
	public void testGetMasterCommentID()
	{
		int masterCommentID = comments.getMaster_comment_ID();
		assertEquals("0should equal to 0",0, masterCommentID);
	}
	public void testSetMasterCommentID()
	{
		comments.setMaster_comment_ID(12);
		int setMasterID = comments.getMaster_comment_ID();
		assertEquals("12 should equals 12", 12, setMasterID);
		
	}
	public void testGetSubID()
	{
		int getSubId = comments.getSub_comments_ID();
		assertEquals("0 should equal to 0", 0, getSubId);
		
	}
	public void testSetSubID()
	{
		comments.setSub_comments_ID(11);
		int setSubCommentID = comments.getSub_comments_ID();
		assertEquals("11 should equal to 11", 11, setSubCommentID);
	}
	public void testGetComment()
	{
		String getComment = comments.getThe_comment();
		assertEquals("null should be null", null, getComment);		
	}
	public void testSetComment()
	{
		String textToBeSet = "hello world";
		comments.setThe_comment(textToBeSet);
		String textToBeTested = comments.getThe_comment();
		assertEquals("Hello world should be tested", textToBeSet, textToBeTested);
	}
	public void testGetCommentdae()
	{
		Date date = comments.getComment_date();
		assertEquals("date should be null", null, date);
	}
	public void testSetCommentDate()
	{
		Date dateToSet = new Date(System.currentTimeMillis());
		comments.setComment_date(dateToSet);
		Date dateToGet = comments.getComment_date();
		assertEquals("date should be current date", dateToSet, dateToGet);
	}
	public void  testIsMasterComment()
	{
		boolean isMaster = comments.isMaster_comment();
		assertEquals("default comment should be master comment", true, isMaster);
	}
	public void testSetMasterComment()
	{
		boolean setMaster = false;
		comments.setMaster_comment(setMaster);
		boolean checkMaster = comments.isMaster_comment();
		assertEquals("set comment not to be master", false, checkMaster);
		
	}
	public void testGetCommentLocaion()
	{
		Location  location = comments.getComment_location();
		assertEquals("default location should be null", null, location);
	}
	public void testSetCommentLocaion()
	{
		Location loc = new Location("dummyprovider");
		loc.setLatitude(20.3);
		loc.setLongitude(52.6);
		comments.setComment_location(loc);
		Location getLocationTocheck = comments.getComment_location();
		assertEquals("two location should equal", loc, getLocationTocheck);
	}
	public void testGetLon()
	{
		double checkLon =  comments.getLon();
		assertEquals("Defualt lon should be 0", 0.0, checkLon);
	}
	public void testSetLon()
	{
		double setLon = 1.3;
		comments.setLon(setLon);
		double getLonToCheck = comments.getLon();
		assertEquals("1.3 should be the new Longtitude", setLon,getLonToCheck);
	}
	public void testGetLat()
	{
		double checkLat =  comments.getLat();
		assertEquals("Defualt lon should be 0", 0.0, checkLat);
	}
	public void testSetLat()
	{
		double setLat = 1.4;
		comments.setLat(setLat);
		double getLatToCheck = comments.getLat();
		assertEquals("1.3 should be the new Latitude", setLat,getLatToCheck);	
	}
	public void testGetDistance()
	{
		double checkDist =  comments.getDistance();
		assertEquals("Defualt Distance should be 0", 0.0, checkDist);
	}
	public void testSetDistance()
	{
		double setDistance = 3.3;
		comments.setDistance(setDistance);
		double getDistToCheck = comments.getDistance();
		assertEquals("new distance should be 3.3", setDistance, getDistToCheck);
	}
	public void testGetCommentImage()
	{

	}
	public void testSetCommentImage()
	{
		
	}
	public void testGetImageEncode()
	{
		String getImgEncode = comments.getImage_encode();
		assertEquals("Default encode should be null", null, getImgEncode);
	}
	public void testSetImageEncode()
	{
		String setImageString = "123nf12";
		comments.setImage_encode(setImageString);
		String getImageString = comments.getImage_encode();
		assertEquals("String that set should equal", setImageString, getImageString);
	}
	public void testGetUserID()
	{
		int userID = comments.getUserID();
		assertEquals("Defualt user ID should be 0", 0, userID);
	}
	public void testSetUserId()
	{
		int setUserID = 111;
		comments.setUserID(setUserID);
		int getUserID = comments.getUserID();
		assertEquals("user ID should be 111", setUserID, getUserID);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
