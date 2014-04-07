package Controller;

import java.util.Comparator;

import Model.Comments;
/**
 * this this to compara comments by date
 * @author yazhou
 *
 */
public class DateSort implements Comparator<Comments>{

	@Override
	public int compare(Comments comment1, Comments comment2) {
		return comment2.getMaster_ID()-comment1.getMaster_ID();

	}

}
