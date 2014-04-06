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
	public int compare(Comments lhs, Comments rhs) {

		return (int) (lhs.getSub_comments_ID()-rhs.getSub_comments_ID());
	}
	
}
