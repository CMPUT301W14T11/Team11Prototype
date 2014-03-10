package Controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import Model.UserModel;
import android.content.Context;

import com.google.gson.Gson;


public class LocalFileSaver
{

	private static final String FILENAME = "fav1.sav";
	/**
	 * @uml.property  name="counter"
	 * @uml.associationEnd  
	 */

	private Gson gson = new Gson();
	private Context context;
	
	public void saveInFile(UserModel user) {
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
			String json = gson.toJson(user);
			//fos.write(new String(date.toString() + " | " + text)
				//	.getBytes());
			out.write(json+ "\n");
			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
