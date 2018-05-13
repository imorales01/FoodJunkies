package fj.foodjunkies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RatePage extends AppCompatActivity {

    private String userID; //Current user
    private ListView rateList;
    private ArrayList <String> restaurantNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_page);
        ActionBar actionBar = getSupportActionBar(); //Set back button on the title bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Review Restaurants");

        //Get the current user ID
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sharedPref.getString("userID", "");

        rateList = (ListView) findViewById(R.id.rateList);

        restaurantNames = new ArrayList <String>();
        restaurantNames.add("pizza");



        //@@@ Over here is where you will fill up the ArrayList with the restaurants
        //restaurantNames.add(string); //Add to the ArrayList like a vector, so depending on how your restaurants are returned you can just add the names here

        //Bind the ArrayList to an Array Adapter for display after populating the ArrayList, and create a custom view with white text
        ArrayAdapter<String> restaurantAdapter = new ArrayAdapter<String>(RatePage.this, android.R.layout.simple_list_item_1, restaurantNames) {
            @Override //This code just makes the text of the ArrayAdapter white
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent); // Get the Item from ListView
                TextView tv = (TextView) view.findViewById(android.R.id.text1); // Initialize a TextView for ListView each Item
                tv.setTextColor(Color.WHITE);// Set the text color of TextView to white
                return view;
            }
        };
        rateList.setAdapter(restaurantAdapter); //Bind the ArrayList to the adapter for display

        //When an item on the ListView is pressed, it will return the position which we use to determine the item pressed
        rateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create an intent to pass the restaurant selected to the next activity
                Intent passIntent = new Intent(RatePage.this, RateQuestions.class);
                passIntent.putExtra("restaurant", restaurantNames.get(position)); //Pass the name of the restaurant
                startActivity(passIntent); //Start the next activity
            }
        });
    }
    //Go back to the previous activity on back arrow press
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(RatePage.this, Welcome.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
