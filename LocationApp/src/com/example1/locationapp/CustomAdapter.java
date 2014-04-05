package com.example1.locationapp;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example1.locationapp.R;

import Controller.BitmapConverter;
import Model.FavouriteComment;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
    
    /**
     * 
     * @param a
     * @param textViewResourceId
     * @param entries
     */
 
    public CustomAdapter(Activity a, int textViewResourceId, ArrayList<FavouriteComment> entries) {
        super(a, textViewResourceId, entries);
        this.fc = entries;
        this.activity = a;
    }
    
    /**
     * 
     * @author zuo2
     *
     */
	public static class ViewHolder{
        public TextView item1; //make a Testview
        public TextView item2; //make a Testview
        public TextView item3;
        public TextView item4;
        public ImageView imageview;
    }

	/**
	 * 
	 */
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
        	DecimalFormat format = new DecimalFormat("###.#");
            holder.item1.setText(custom.getTitle());
            holder.item2.setText(custom.getText());
            holder.item3.setText(format.format(custom.getDistance())+"m");
            
            if(custom.getUserName()==null)
         	{
         		holder.item4.setText("Guest");
         	}
            else
            {
            	holder.item4.setText(custom.getUserName());
            }
            
            if(custom.getImage()!=null)
            {   
            	BitmapConverter ImageConvter = new BitmapConverter();
            	Bitmap bitmap = ImageConvter.deserialize(custom.getImage(), null, null);
            //  we need to convert base64 string back to bitmap , and add bitmap to the comment object
            	//byte[] imageAsBytes = Base64.decode(custom.getImage().getBytes(),Base64.NO_WRAP);
            	//Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            	
            	if(bitmap!=null)
                {
                	holder.imageview.setImageBitmap(bitmap);
                    System.out.println("imageset");            
                }
            }
            else
            {
            	holder.imageview.setImageBitmap(null);
            }
            
        }
        return v;
    }
	
	


	}
    
    
 
    



