package Model;

import java.io.Serializable;

import android.graphics.Bitmap;
/**
 * this class is making profile for the user
 * @author yazhou
 */
@SuppressWarnings("serial")
public class CommentUser implements Serializable{

	    private String name;
	    private String age;
	    private String facebook;
	    private String LinkedIn;
	    private String Phone;
	    private String Email;
	    private Bitmap picture;
	    private String imageEncode;
	    private String bio;
	    private String ProfileEncode;
	    private String uudi;
	    /**
	     * constructor for make a user profile
	     */
	    public CommentUser()
	    {
	    	
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    /**
	     * get uuid
	     * @return
	     */
	    public String getUudi() {
			return uudi;
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    /**
	     * set uuid
	     * @param uudi
	     */
		public void setUudi(String uudi) {
			this.uudi = uudi;
		}

		
		
		
		
		
		
		
		
		
		/**
	     * get the profile encode
	     * @return
	     */
	    public String getProfileEncode() {
			return ProfileEncode;
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    /**
	     * to set the profile encode by given string
	     * @param profileEncode
	     */
		public void setProfileEncode(String profileEncode) {
			ProfileEncode = profileEncode;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to get the bio
		 * @return
		 */
		public String getBio() {
			return bio;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to set bio by given stirng
		 * @param bio
		 */
		public void setBio(String bio) {
			this.bio = bio;
		}
	    
		
		
		
		
		
		
		
		
		
	    /**
	     * to get the name
	     * @return
	     */
		public String getName() {
			return name;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to set the name by given string
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * get the age
		 * @return
		 */
		public String getAge() {
			return age;
		}
		
		
		
		
		
		
		
		
		
		
		/**
		 * to set the age 
		 * @param age
		 */
		public void setAge(String age) {
			this.age = age;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to get the facebook
		 * @return
		 */
		public String getFacebook() {
			return facebook;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to set the facebook
		 * @param facebook
		 */
		public void setFacebook(String facebook) {
			this.facebook = facebook;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * the get the link
		 * @return
		 */
		public String getLinkedIn() {
			return LinkedIn;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to se the linked in
		 * @param linkedIn
		 */
		public void setLinkedIn(String linkedIn) {
			LinkedIn = linkedIn;
		}
		
		
		
		
		
		
		
		
		
		
		/**
		 * to get the phone number
		 * @return
		 */
		public String getPhone() {
			return Phone;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to set the phone number
		 * @param phone
		 */
		public void setPhone(String phone) {
			Phone = phone;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to get the e-mail address
		 * @return
		 */
		public String getEmail() {
			return Email;
		}
		
		
		
		
		
		
		
		
		
		/**
		 * to set the e-mail address
		 * @param email
		 */
		public void setEmail(String email) {
			Email = email;
		}
		
		
		
		
		
		
		
		
		
		
		/**
		 * get the picture
		 * @return
		 */
		public Bitmap getPicture() {
			return picture;
		}
		
		
		
		
		
		
		
		
		
		
		/**
		 * to set the picture
		 * @param picture
		 */
		public void setPicture(Bitmap picture) {
			this.picture = picture;
		}
		
		
		
		
		
		
		
		
		
		
		/**
		 * get he image encode
		 * @return
		 */
		public String getImageEncode() {
			return imageEncode;
		}
		
		
		
		
		
		
		
		
		
		
		/**
		 * to set image encode
		 * @param imageEncode
		 */
		public void setImageEncode(String imageEncode) {
			this.imageEncode = imageEncode;
		}
}
