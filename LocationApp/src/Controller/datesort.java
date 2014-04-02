package Controller;

import java.util.Comparator;

import Model.Comments;

public class datesort implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		return lhs.getMaster_ID()-rhs.getMaster_ID();
	}

}
