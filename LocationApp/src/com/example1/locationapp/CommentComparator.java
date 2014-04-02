package com.example1.locationapp;

import java.util.Comparator;

import Model.Comments;

/**
 * this is for Sorting to comments by compare it with itself.
 * @author zuo2
 *
 */
public class CommentComparator implements Comparator<Comments>
{
	@Override
    public int compare(Comments s, Comments t) {
       return s.getSub_comments_ID()-t.getSub_comments_ID();
    }
}
