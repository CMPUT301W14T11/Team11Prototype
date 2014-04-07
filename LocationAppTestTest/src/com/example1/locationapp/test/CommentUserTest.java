package com.example1.locationapp.test;

import Model.CommentUser;
import junit.framework.TestCase;

public class CommentUserTest extends TestCase {

	public CommentUserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testUserName()
	{
		CommentUser user = new CommentUser();
		user.setAge("19");
		assertEquals("19", user.getAge());
	}
	
	public void testUserPone()
	{
		CommentUser user = new CommentUser();
		user.setPhone("780");
		assertEquals("780", user.getPhone());
	}
	
	public void testUserFB()
	{
		CommentUser user = new CommentUser();
		user.setFacebook("cool");
		assertEquals("cool", user.getFacebook());
	}
	
	public void testUserLINKedin()
	{
		CommentUser user = new CommentUser();
		user.setLinkedIn("asf@hotmail.com");
		assertEquals("asf@hotmail.com", user.getLinkedIn());
	}
	
	public void testUserEmail()
	{
		CommentUser user = new CommentUser();
		user.setEmail("heh@gmail.com");
		assertEquals("heh@gmail.com", user.getEmail());
	}

	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
