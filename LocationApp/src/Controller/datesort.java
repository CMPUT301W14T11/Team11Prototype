package Controller;

import java.util.Comparator;

import Model.Comments;

public class datesort implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		// TODO Auto-generated method stub
		return lhs.getMaster_ID()-rhs.getMaster_ID();
	}

}
