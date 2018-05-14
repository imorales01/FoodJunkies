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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RatePage extends AppCompatActivity {

    private String userID; //Current user
    private ListView rateList;
    private ArrayList <String> restaurantNames;

    //Volley stuff
    private StringRequest request;
    RequestQueue requestQueue;

    String dishID, Name, Address, cusID;
    String get_Bookmarks = "http://54.208.66.68:80/getBookmarks2.php";
/*
    //Arrays for holding Bookmarked restaurants info, to be used with the listview
    private String[] Names = new String[50];
    private String[] dish_ID = new String[50];
    private String[] cus_ID = new String[50];
    private String[] rest_ID = new String[50];
 */

    //Initialize the ArrayList to store information
    private ArrayList <String> Names = new ArrayList <String> ();
    private ArrayList <String> dish_ID = new ArrayList <String> ();
    private ArrayList <String> cus_ID = new ArrayList <String> ();
    private ArrayList <String> rest_ID = new ArrayList <String> ();
    private ArrayList <String> dishName = new ArrayList <String> ();

    private int bookmarkListLength=0;

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

        requestQueue = Volley.newRequestQueue(this);

        rateList = (ListView) findViewById(R.id.rateList);
/*
        //Initialize the ArrayList to store information
        ArrayList <String> Names = new ArrayList <String> ();
        ArrayList <String> dish_ID = new ArrayList <String> ();
        ArrayList <String> cus_ID = new ArrayList <String> ();
        ArrayList <String> rest_ID = new ArrayList <String> ();
*/
        getBookmarks(); //Populate the arrays with the information of bookmarked restaurants, and set the ListView

//        restaurantNames = new ArrayList <String>();
        //Fill up the ArrayList with the restaurants
        //restaurantNames.add(string); //Add to the ArrayList like a vector, so depending on how your restaurants are returned you can just add the names here
        /*
        restaurantNames = Names;

        for (int i=0; i<bookmarkListLength; i++){
        //    restaurantNames.add(Names[i]);
        }

        for (int i=0; i<restaurantNames.size(); i++){
            System.out.println(restaurantNames.get(i));
        }

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
        */
    }

    public void getBookmarks (){
        request = new StringRequest(Request.Method.POST, get_Bookmarks, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Creating JsonObject from response String
                    JSONObject jsonObject= new JSONObject(response.toString());

                    JSONArray items = jsonObject.getJSONArray("products");
                    bookmarkListLength = items.length();
                    System.out.println(items.length());
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);

                        String namesT = item.getString("Name");
                        String dishIDT = item.getString("Dish_ID");
                        String cusIDT = item.getString("Cus_ID");
                        String restIDT = item.getString("Rest_ID");
                        String foodName = item.getString("DishName");

                        Names.add(namesT);
                        dish_ID.add(dishIDT);
                        cus_ID.add(cusIDT);
                        rest_ID.add(restIDT);
                        dishName.add(foodName);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Bind the ArrayList to an Array Adapter for display after populating the ArrayList, and create a custom view with white text
                ArrayAdapter<String> restaurantAdapter = new ArrayAdapter<String>(RatePage.this, android.R.layout.simple_list_item_1, Names) {
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
                        passIntent.putExtra("restaurantName", Names.get(position)); //Pass the name of the restaurant
                        passIntent.putExtra("dishID", dish_ID.get(position)); //Pass the name of the restaurant
                        passIntent.putExtra("cusID", cus_ID.get(position)); //Pass the name of the restaurant
                        passIntent.putExtra("restID", rest_ID.get(position)); //Pass the name of the restaurant
                        passIntent.putExtra("dishName", dishName.get(position));
                        startActivity(passIntent); //Start the next activity
                    }
                });
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("User_ID",userID);

                return hashMap;
            }
        };
        requestQueue.add(request);



    }

    //Go back to the previous activity on back arrow press
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(RatePage.this, Welcome.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
