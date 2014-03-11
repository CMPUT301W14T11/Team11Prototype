package Controller;

import java.util.Comparator;

import Model.Comments;

public class compara implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		// TODO Auto-generated method stub
		
		return (int) (lhs.getDistance()-rhs.getDistance());
	}

}
