package Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import Model.UserModel;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;


public class LocalFileLoder
{

	private static final String FILENAME = "fav1.sav";


	private Gson gson = new Gson();
	private Context context;
	private UserModel um;
	private boolean fileExist = false;
	
	public LocalFileLoder(Context context)
	{
		this.context = context;
	}
	
	public void Exist()
	{
		 try {
             FileInputStream fis = context.openFileInput(FILENAME);
             fileExist=true;
             
		 } catch (FileNotFoundException e) {
             // TODO Auto-generated catch block
		 }
	}
	
	public boolean exist()
	{
		return fileExist;
	}
	public UserModel loadFromFile() {      
		um = new UserModel();
        try {
                FileInputStream fis = context.openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                String line = in.readLine();
                Log.v("output>>>>>>>>>>>>>>",line);
                while (line != null) {
                	um = gson.fromJson(line, UserModel.class);                       
                    line = in.readLine();
                }

        } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        return um;
	}	
}
