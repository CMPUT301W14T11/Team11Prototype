package com.example1.locationapp;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Model.Comments;
import Model.FavouriteComment;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
// custom listview adapter for making a listview adapter
public class cutadapter extends ArrayAdapter<Comments>{
	private ArrayList<Comments> entries;
    private Activity activity;
 
    public cutadapter(Activity a, int textViewResourceId, ArrayList<Comments> entries) {
        super(a, textViewResourceId, entries);
        this.entries = entries;
        this.activity = a;
    }
    

	public static class ViewHolder{
        public TextView item1; //make a Testview
        public TextView item2; //make a Testview
        public TextView item3;
        public TextView item4;
        public ImageView imageview;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi =
                (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listlayout, null);
            holder = new ViewHolder();
            holder.item1 = (TextView) v.findViewById(R.id.bigg);
            holder.item2 = (TextView) v.findViewById(R.id.smalll);
            holder.item3 = (TextView) v.findViewById(R.id.loca);
            holder.item4 = (TextView) v.findViewById(R.id.number);
            holder.imageview = (ImageView) v.findViewById(R.id.imageView88);
            v.setTag(holder);
        }
        else
            holder=(ViewHolder)v.getTag();
 
        final Comments custom = entries.get(position);
        if (custom != null) {
        	DecimalFormat format = new DecimalFormat("###.#");
            holder.item1.setText(custom.getThe_comment());
            holder.item2.setText(custom.getSubject_comment());
            holder.item3.setText(format.format(custom.getDistance())+"m");
            if(custom.getUserName()==null)
         	{
         		holder.item4.setText("Guest");
         	}
            else
            {
            	holder.item4.setText(custom.getUserName());
            }
            
            if(custom.getImage_encode()!=null)
            {   
            //  we need to convert base64 string back to bitmap , and add bitmap to the comment object
            	byte[] imageAsBytes = Base64.decode(custom.getImage_encode().getBytes(),Base64.NO_WRAP);
            	Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            	
            	if(bitmap!=null)
                {
                	custom.setComment_image(bitmap);
                	holder.imageview.setImageBitmap(custom.getComment_image());
                    System.out.println("imageset");            
                }
            }
            
        }
        return v;
    }
	
	


	}
    
    
 
    


