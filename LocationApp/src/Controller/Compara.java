package Controller;

import java.util.Comparator;

import Model.Comments;
/**
 * To use this to compare comments by distance 
 * @author yazhou
 */
public class Compara implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		 if (lhs.getDistance() > rhs.getDistance())
			 return 1;
		 else
			 return -1;
	}

}
