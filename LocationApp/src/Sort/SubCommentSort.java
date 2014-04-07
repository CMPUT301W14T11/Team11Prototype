package Sort;

import java.util.Comparator;

import Model.Comments;
/**
 * This is for soring the sub comment by master id
 * @author yazhou
 *
 */
public class SubCommentSort implements Comparator<Comments>{

	@Override
	public int compare(Comments comment1, Comments comment2) {

		return (int) (comment1.getSub_comments_ID()-comment2.getSub_comments_ID());
	}
	
}
