package Model;

import java.io.Serializable;

import android.graphics.Bitmap;

public class CommentUser implements Serializable{

	    private String name;
	    private String age;
	    private String facebook;
	    private String LinkedIn;
	    private String Phone;
	    private String Email;
	    private Bitmap picture;
	    private String imageEncode;
	    
	    public CommentUser()
	    {
	    	
	    }
	    
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getFacebook() {
			return facebook;
		}
		public void setFacebook(String facebook) {
			this.facebook = facebook;
		}
		public String getLinkedIn() {
			return LinkedIn;
		}
		public void setLinkedIn(String linkedIn) {
			LinkedIn = linkedIn;
		}
		public String getPhone() {
			return Phone;
		}
		public void setPhone(String phone) {
			Phone = phone;
		}
		public String getEmail() {
			return Email;
		}
		public void setEmail(String email) {
			Email = email;
		}
		public Bitmap getPicture() {
			return picture;
		}
		public void setPicture(Bitmap picture) {
			this.picture = picture;
		}
		public String getImageEncode() {
			return imageEncode;
		}
		public void setImageEncode(String imageEncode) {
			this.imageEncode = imageEncode;
		}
	    
	    
	    
}
