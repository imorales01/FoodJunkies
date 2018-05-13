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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class DisplayRestaurant extends AppCompatActivity {

    private String id;
    private String restaurantName;
    private String address;
    private String phoneNumber;
    private String price;
    private String imageURL;
    private String food;
    private double distance;
    private double rating;
    private ImageView restaurantImage;

    TextView textViewName;
    TextView textViewAddress;
    TextView textViewPhone;
    TextView textViewPrice;
    TextView textViewDistance;
    TextView textViewRating;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_restaurant);
        ActionBar actionBar = getSupportActionBar(); //Set back button on the title bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        restaurantImage = (ImageView) findViewById(R.id.restaurantImage);

        //Get restaurant information from the previous SelectRestaurant activity by extracting extras from Intent
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        restaurantName = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phoneNumber = intent.getStringExtra("phone");
        price = intent.getStringExtra("price");
        //distance = Double.valueOf(intent.getStringExtra("distance")); //Convert the string values to doubles
        //rating = Double.valueOf(intent.getStringExtra("rating"));
        imageURL = intent.getStringExtra("imageURL");
        food = intent.getStringExtra("food");

        setTitle("Additional details..."); //Set title to the restaurant name

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.setText(restaurantName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewAddress.append(address);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewPhone.append(phoneNumber);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewPrice.append(price);
        textViewDistance = (TextView) findViewById(R.id.textViewDistance);
        textViewDistance.append(intent.getStringExtra("distance") + " miles");
        textViewRating = (TextView) findViewById(R.id.textViewRating);
        textViewRating.append(intent.getStringExtra("rating") + "/5");

        // use the space below to after simon finishes his history page to save a specific restaurant to history using business id

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open up the rating page
                startActivity(new Intent(getApplicationContext(), RatePage.class));
            }
        });


        Glide.with(this).load(imageURL).into(restaurantImage);
    }

    //Go back to the previous activity on back arrow press
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(DisplayRestaurant.this, SelectRestaurant.class);
        myIntent.putExtra("RECOMMEND", food);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
