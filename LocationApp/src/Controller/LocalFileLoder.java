package Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import Model.UserModel;
import android.content.Context;

import com.google.gson.Gson;

/**
 * Used to load file from local
 * @author bqi
 */
public class LocalFileLoder
{

	private static final String FILENAME = "fav1.sav";
	private Gson gson = new Gson();
	private Context context;
	private UserModel um;
	private boolean fileExist = false;
	
	/**
	 * get the senders context
	 * @param context -- sender's context
	 */
	public LocalFileLoder(Context context)
	{
		this.context = context;
	}
	
	/**
	 * used to determine if local file existed
	 */
	public void Exist()
	{
		 try {
             context.openFileInput(FILENAME);
             fileExist=true;
             
		 } catch (FileNotFoundException e) {

		 }
	}
	
	/**
	 * return if local file existed
	 * @return
	 * true -- local file existed. false -- local file doesnt exist
	 */
	public boolean exist()
	{
		return fileExist;
	}
	
	/**
	 * load UserModel from the local file
	 * @return
	 * UserModel from the local file
	 */
	public UserModel loadFromFile() {      
		um = new UserModel();
        try {
                FileInputStream fis = context.openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                String line = in.readLine();
                while (line != null) {
                	um = gson.fromJson(line, UserModel.class);                       
                    line = in.readLine();
                }

        } catch (FileNotFoundException e) {

                e.printStackTrace();
        } catch (IOException e) {

                e.printStackTrace();
        }
        return um;
	}	
}
