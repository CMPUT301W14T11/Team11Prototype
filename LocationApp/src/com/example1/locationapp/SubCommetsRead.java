package com.example1.locationapp;

import java.util.ArrayList;
import java.util.Date;

import Model.Comments;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SubCommetsRead extends Activity {
	private ListView listViewSubComment;
	private EditText editText;
	private Button button1;
	private cutadapter ListAdapter;
	ArrayList<Comments> comment_list; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sub_commets_read);
	listViewSubComment = (ListView)findViewById(R.id.listViewSubComments);
	editText = (EditText)findViewById(R.id.editTextSubmitSubComments);
	button1 = (Button)findViewById(R.id.buttonSaveSubComments);
	button1.setText("Submit Sub Comments");
    comment_list = new ArrayList<Comments>();
    // add an example to test the list
    comment_list.add(new Comments(0, 0,0, "It works", "Tesing", new Date(), null, 123, 123));
    ListAdapter = new cutadapter(this, R.layout.listlayout, comment_list);
	
	listViewSubComment.setAdapter(ListAdapter);
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.sub_commets_read, menu);
	return true;
	}

}

