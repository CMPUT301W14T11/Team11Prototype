package com.example1.locationapp;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example1.locationapp.R;

import Controller.BitmapConverter;
import Model.Comments;
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
public class CutAdapter extends ArrayAdapter<Comments>{
	private ArrayList<Comments> entries;
    private Activity activity;
    /**
     * Make a new adapter for listview
     * @param a pass in activity
     * @param textViewResourceId
     * @param entries
     */
    public CutAdapter(Activity a, int textViewResourceId, ArrayList<Comments> entries) {
        super(a, textViewResourceId, entries);
        this.entries = entries;
        this.activity = a;
    }
    
    
    
    
    
    
    
    
    
    /**
     * Make the view for listview
     */
	public static class ViewHolder{
        public TextView item1; 
        public TextView item2; 
        public TextView item3;	
        public TextView item4;
        public ImageView imageview;
    }
	
	
	
	
	
	
	
	
	
	/**
	 * Put the data into the  view
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
 
        final Comments custom = entries.get(position);
        if (custom != null) {
        	DecimalFormat format = new DecimalFormat("###.#");
            holder.item1.setText(custom.getThe_comment());
            holder.item2.setText(custom.getSubject_comment());
            holder.item3.setText(format.format(custom.getDistance())+"m");
            holder.item4.setText(custom.getUserName());
            if(custom.getImage_encode()!=null)
            {   
            	BitmapConverter ImageConvter = new BitmapConverter();
            	Bitmap bitmap = ImageConvter.deserialize(custom.getImage_encode(), null, null);
            	
            	if(bitmap!=null)
                {
                	holder.imageview.setImageBitmap(bitmap);
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
    
    
 
    

