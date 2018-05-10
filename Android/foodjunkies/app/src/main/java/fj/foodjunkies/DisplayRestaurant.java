package fj.foodjunkies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class DisplayRestaurant extends AppCompatActivity {

    private String restaurantName;
    private String address;
    private String phoneNumber;
    private String price;
    private String imageURL;
    private double distance;
    private double rating;
    private ImageView restaurantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_restaurant);
        ActionBar actionBar = getSupportActionBar(); //Set back button on the title bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        restaurantImage = (ImageView) findViewById(R.id.restaurantImage);

        //Get restaurant information from the previous SelectRestaurant activity by extracting extras from Intent
        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phoneNumber = intent.getStringExtra("phone");
        price = intent.getStringExtra("price");
        distance = Double.valueOf(intent.getStringExtra("distance")); //Convert the string values to doubles
        rating = Double.valueOf(intent.getStringExtra("rating"));
        imageURL = intent.getStringExtra("imageURL");

        setTitle(restaurantName); //Set title to the restaurant name

        new DownloadImageTask(restaurantImage).execute(imageURL); //Asynchronously download the image from the URL and set

    }

    //@@@@@@@@@ UNUSED FUNCTION SRC: https://stackoverflow.com/questions/6407324/how-to-display-image-from-url-on-android
    // Converts the image from a URL into a drawable
    public static Drawable loadURLImage(String url) {
        try {
            InputStream inputStream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(inputStream, "src name");
            return drawable;
        } catch (Exception e) {
            return null;
        }
    }

    //Go back to the previous activity on back arrow press
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(DisplayRestaurant.this, SelectRestaurant.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    //Class to Asynchronously download an image from the YELP URL and set the imageView
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bitmapImage;

        public DownloadImageTask(ImageView bitmapImage) {
            this.bitmapImage = bitmapImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                //Create an input stream with the given URL, and then convert it into a Bitmap
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bitmapImage.setImageBitmap(result); //Set the ImageView as the image from the URL
        }
    }
}
