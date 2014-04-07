package com.example1.locationapp.test;

import Model.CommentUser;
import junit.framework.TestCase;

/**
 * in this file comment user will be checked.
 * @author yazhou
 *
 */
public class CommentUserTest extends TestCase {
	
	public CommentUserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	/**
	 * test user name
	 */
	public void testUserName()
	{
		CommentUser user = new CommentUser();
		user.setAge("19");
		assertEquals("19", user.getAge());
	}
	/**
	 * test user phone
	 */
	public void testUserPone()
	{
		CommentUser user = new CommentUser();
		user.setPhone("780");
		assertEquals("780", user.getPhone());
	}
	/**
	 * test user facebook
	 */
	public void testUserFB()
	{
		CommentUser user = new CommentUser();
		user.setFacebook("cool");
		assertEquals("cool", user.getFacebook());
	}
	/**
	 * test user linkedin
	 */
	public void testUserLINKedin()
	{
		CommentUser user = new CommentUser();
		user.setLinkedIn("asf@hotmail.com");
		assertEquals("asf@hotmail.com", user.getLinkedIn());
	}
	/**
	 * test user email
	 */
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
