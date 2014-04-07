package Controller;

import java.util.Comparator;

import Model.Comments;
/**
 * To use this to compare comments by distance 
 * @author yazhou
 */
public class Compara implements Comparator<Comments>{

	@Override
	public int compare(Comments comment1, Comments comment2) {
		 if (comment1.getDistance() > comment2.getDistance())
			 return 1;
		 else
			 return -1;
	}

}
