package fj.foodjunkies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RateQuestions extends AppCompatActivity {

    private TextView restaurantText;
    private TextView dishText;
    private Button restaurantYes;
    private Button restaurantNo;
    private Button dishYes;
    private Button dishNo;
    private Button doneButton;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_questions);

        ActionBar actionBar = getSupportActionBar(); //Set back button on the title bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Rate");

        restaurantText = (TextView) findViewById(R.id.textRestaurant);
        dishText = (TextView) findViewById(R.id.textCuisine);
        restaurantYes = (Button) findViewById(R.id.yesButton1);
        restaurantNo = (Button) findViewById(R.id.noButton1);
        dishYes = (Button) findViewById(R.id.yesButton2);
        dishNo = (Button) findViewById(R.id.noButton2);
        doneButton = (Button) findViewById(R.id.doneButton);

        //Get the current user ID
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sharedPref.getString("userID", "");

        //Set the text for the restaurant
        restaurantText.setText("Kah");

        //Set the text for the dish
        dishText.setText("Kah");

        //Yes button if the user liked the restaurant
        restaurantYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        //No button if the user disliked the restaurant
        restaurantNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        //Yes button if the user liked the dish
        dishYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        //No button if the user liked the dish
        dishNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        //After pressing the done button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.Welcome.class)); //Go back to homepage
            }
        });
    }
    //Go back to the previous activity on back arrow press
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(RateQuestions.this, RatePage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
