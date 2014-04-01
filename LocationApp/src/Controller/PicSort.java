package Controller;

import java.util.Comparator;

import Model.Comments;

public class PicSort implements Comparator<Comments>{

	@Override
	public int compare(Comments lhs, Comments rhs) {
		// TODO Auto-generated method stub
		int result =0;
		if(lhs.getImage_encode()!=null && rhs.getImage_encode()!=null)
		{
			result =lhs.getImage_encode().compareTo(rhs.getImage_encode()); 
		}
		else
		{
			result = -1;
		}
		
		return result;
	}

}
