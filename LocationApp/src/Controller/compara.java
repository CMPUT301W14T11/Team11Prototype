package Controller;

import java.util.Comparator;

import Model.Comments;

public class compara implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		
		return (int) (lhs.getDistance()-rhs.getDistance());
	}

}
