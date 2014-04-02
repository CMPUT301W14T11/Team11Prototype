package Controller;

import java.util.Comparator;

import Model.Comments;

public class datesort implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		return rhs.getMaster_ID()-lhs.getMaster_ID();
=======
		return lhs.getMaster_ID()-rhs.getMaster_ID();
>>>>>>> 64e16f660b45ad7b1439548e2e4e7fac322c7909
	}

}
