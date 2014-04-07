package com.example1.locationapp;

import java.util.Comparator;

import Model.Comments;

/**
 * this is for Sorting to comments by compare it with itself.
 * @author zuo2
 */
public class CommentComparator implements Comparator<Comments>
{
	
	/**
	 * basic method of sorting
	 * @param comment1  Comments model 
	 * @param comment2  Comments model
	 * @return give the result that which ID is bigger.
	 */
	@Override
    public int compare(Comments comment1, Comments comment2) {
       return comment1.getSub_comments_ID()-comment2.getSub_comments_ID();
    }
}
