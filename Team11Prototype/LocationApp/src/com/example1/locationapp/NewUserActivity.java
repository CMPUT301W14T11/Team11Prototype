package com.example1.locationapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends Activity
{

	private Button submit;
	private EditText name;
	private String username;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		name = (EditText) findViewById(R.id.editText1);
		submit = (Button) findViewById(R.id.Submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
	        public void onClick(View arg0) {
	            Intent intent = new Intent(NewUserActivity.this,
	                    MainActivity.class);
	            username = name.getText().toString();
	            if (username.equals(""))
	            {
	            	Toast.makeText(NewUserActivity.this,
	                        "Name cannot be null !!!", Toast.LENGTH_SHORT)
	                        .show();
	            }
	            else
	            {
	            	intent.putExtra("name", username);
	 	            startActivity(intent);
	            }
	           
			}
			
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

}
