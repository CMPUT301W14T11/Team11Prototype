package com.example1.locationapp;

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
/**
 *  custom listview adapter for making a listview adapter
 * @author qyu4
 *
 */
public class CustomAdapter extends ArrayAdapter<FavouriteComment>{
	private ArrayList<FavouriteComment> fc;
    private Activity activity;
 
    public CustomAdapter(Activity a, int textViewResourceId, ArrayList<FavouriteComment> entries) {
        super(a, textViewResourceId, entries);
        this.fc = entries;
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
 
        final FavouriteComment custom = fc.get(position);
        if (custom != null) {
            holder.item1.setText(custom.getTitle());
            holder.item2.setText(custom.getText());
           // holder.item3.setText("Location:"+custom.getDistance()+"");
           // holder.item4.setText(custom.getComment_date().toString());
//            if(custom.getTitle()!=null)
//            {   
//            //  we need to convert base64 string back to bitmap , and add bitmap to the comment object
//            	byte[] imageAsBytes = Base64.decode(custom.getImage_encode().getBytes(),Base64.NO_WRAP);
//            	Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//            	
//            	if(bitmap!=null)
//                {
//                	custom.setComment_image(bitmap);
//                	holder.imageview.setImageBitmap(custom.getComment_image());
//                    System.out.println("imageset");            
//                }
//            }
            
        }
        return v;
    }
	
	


	}
    
    
 
    



