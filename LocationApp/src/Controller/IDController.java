package Controller;

import java.io.IOException;

import Model.IDModel;

public interface IDController {
	// insert ID object to server, return type is void
	public void insert(IDModel id ) throws IllegalStateException, IOException;
	// get ID object from server, return type is ID class
	public IDModel get_id();

}
