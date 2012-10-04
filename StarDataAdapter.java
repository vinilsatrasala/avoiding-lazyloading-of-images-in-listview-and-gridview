package com.rikt.tech.and;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StarDataAdapter extends ArrayAdapter<CommonDetailOfEachStar>
{
	 private final Activity activity;
	 
	    private final List<CommonDetailOfEachStar> stars;
	    //Creating Object for the class
       public LazyLoadingOfImages imageLoader;
	    double longitudeAddress,latitudeAddress;
	  
	    public StarDataAdapter(Activity activity,int resource, List<CommonDetailOfEachStar> objects) {
	        super(activity, R.layout.movie , objects);
	        this.activity = activity;
	        this.stars = objects;
	        imageLoader=new LazyLoadingOfImages(activity.getApplicationContext()); // this class downloads all te images from the services and avoids lazy list loading
	        
	    }

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	        View rowView = convertView;
	        StockQuoteView sqView = null;

	        if(rowView == null)
	        {
	            // Get a new instance of the row layout view
	            LayoutInflater inflater = activity.getLayoutInflater();
	            rowView = inflater.inflate(R.layout.movie, null);
	           
	            // Hold the view objects in an object,
	            // so they don't need to be re-fetched
	            sqView = new StockQuoteView();
	            sqView.name = (TextView) rowView.findViewById(R.id.ticker_symbol);
	            sqView.addr = (TextView) rowView.findViewById(R.id.ticker_price);	            
	            sqView.img = (ImageView) rowView.findViewById(R.id.Image);
	            sqView.arrow = (ImageView) rowView.findViewById(R.id.arrow);
	            sqView.ll = (LinearLayout) rowView.findViewById(R.id.LinearLayout2);
	            sqView.lll = (LinearLayout) rowView.findViewById(R.id.LinearLayout1);
	            sqView.llll = (LinearLayout) rowView.findViewById(R.id.Linearyou01);
	            
	            // Cache the view objects in the tag,
	            // so they can be re-accessed later
	            rowView.setTag(sqView);
	        } else {
	            sqView = (StockQuoteView) rowView.getTag();
	        }
	        final CommonDetailOfEachStar stardata = stars.get(position);
	        sqView.name.setText(stardata.getSname());
	        sqView.name.setTextColor(Color.rgb(248, 223, 144));
	        sqView.addr.setText(stardata.getAdress());
	        //passing the image url and the image view to display image method and setting tha apt image to the image view
	        imageLoader.DisplayImage(stardata.getPicture(), sqView.img); 
	        sqView.img.setPadding(2, 2, 2, 2);
	        sqView.ll.setBackgroundDrawable(rowView.getResources().getDrawable(R.drawable.shape1));
	        sqView.lll.setBackgroundDrawable(rowView.getResources().getDrawable(R.drawable.shape1));
	        sqView.arrow.setFocusable(false);
	        sqView.arrow.setFocusableInTouchMode(false);
	        sqView.arrow.setTag(new Integer(position));
	        sqView.arrow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																										
					int starid= stars.get(position).getId();
					String tag=Integer.toString(position);
					Log.d(tag,"tag");
					Intent myIntent = new Intent(v.getContext(),StarDetailActivity.class);
				    myIntent.putExtra("id",starid);
					v.getContext().startActivity(myIntent);

				}
			});
	        Drawable rightArrow = getContext().getResources().getDrawable(R.drawable.selected_cell);
	        Drawable rightArrow1 = getContext().getResources().getDrawable(R.drawable.celllft);
	        if(stars.get(position).isSelected())
	        {
	        	sqView.llll.setBackgroundDrawable(rightArrow);
	        	
	    	}
	    	else
	    	{
	    		sqView.llll.setBackgroundDrawable(rightArrow1);
	    	}

	        return rowView;
	    }

   protected static class StockQuoteView {
	        protected TextView name;
	        protected TextView addr;
	        protected ImageView img;
	        protected ImageView arrow;
	        protected LinearLayout ll;
	        protected LinearLayout lll; 
	        protected LinearLayout llll;

	    }

   public static Bitmap getBitmapFromURL(String src) {
       try {
           Log.e("src",src);
           URL url = new URL(src);
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setDoInput(true);
           connection.connect();
           InputStream input = connection.getInputStream();
           Bitmap myBitmap = BitmapFactory.decodeStream(input);
           Log.e("Bitmap","returned");
           return myBitmap;
       } catch (IOException e) {
           e.printStackTrace();
           Log.e("Exception",e.getMessage());
           return null;
       }
   }
}