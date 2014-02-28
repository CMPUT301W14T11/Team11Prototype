package Controller;

import java.io.IOException;

import com.example1.locationapp.ID;

public interface IDController {
	// insert ID object to server, return type is void
	public void insert(ID id ) throws IllegalStateException, IOException;
	// get ID object from server, return type is ID class
	public ID get_id();

}
