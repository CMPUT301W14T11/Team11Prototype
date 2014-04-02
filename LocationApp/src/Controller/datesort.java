package Controller;

import java.util.Comparator;

import Model.Comments;

public class datesort implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		// TODO Auto-generated method stub
		return rhs.getMaster_ID()-lhs.getMaster_ID();
	}

}
