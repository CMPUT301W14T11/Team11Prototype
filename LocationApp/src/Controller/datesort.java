package Controller;

import java.util.Comparator;

import Model.Comments;
/**
 * this this to compara comments by date
 * @author yazhou
 *
 */
public class datesort implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		return rhs.getMaster_ID()-lhs.getMaster_ID();

	}

}
