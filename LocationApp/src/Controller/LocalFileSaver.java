package Controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import Model.UserModel;
import android.content.Context;

import com.google.gson.Gson;

/**
 * used to save UserModel to the localfile
 * @author bqi
 *
 */
public class LocalFileSaver
{

	private static final String FILENAME = "fav1.sav";
	private Gson gson = new Gson();
	private Context context;
	
	/**
	 * get sender's context
	 * @param context -- sender's context
	 */
	public LocalFileSaver(Context context)
	{
		this.context = context;
	}
	
	

	/**
	 * save the UserModel to the local file
	 * @param user
	 */
	public void saveInFile(UserModel user) {
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
			String json = gson.toJson(user);
			fos.write(json.getBytes());
			fos.write("\n".getBytes());
			out.close();
			fos.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
