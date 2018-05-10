package fj.foodjunkies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Recommend extends AppCompatActivity {

    Button getCuisine, recommendDish, getName, showRec;
    RequestQueue requestQueue;
    //URLS to php scripts
    String getRatings = "http://54.208.66.68:80/getRatings.php";
    String getDishes = "http://54.208.66.68:80/getDishes.php";
    String getDishName = "http://54.208.66.68:80/getDishName.php";

    TextView showDish;
    TextView showName;
    private StringRequest request;
    private String userID;
    //Stores dishes of picked cuisine
    private Integer[] dishID = new Integer[10];
    //Stores users ratings
    private Integer[] cusRatings = new Integer[10];
    private Random generator = new Random();

    //Specific Dish recommendation, Dish name
    String recName;

    //Following are stored as strings due to how volley sends parameters:

    //Specific Dish recommendation, Dish_ID
    String recommendationID;

    //Specific Cuisine recommendation, Cus_ID
    String cusID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        getName = (Button) findViewById(R.id.GetName);
        showRec = (Button) findViewById(R.id.ShowRecommendation);
        showDish = (TextView) findViewById(R.id.textView4);
        showName = (TextView) findViewById(R.id.Namehere);
        getCuisine = (Button) findViewById(R.id.button5);
        recommendDish = (Button) findViewById(R.id.button6);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sharedPref.getString("userID", "");

        requestQueue = Volley.newRequestQueue(this);

        //Get ratings from database, store in cusRatings[]
        getRatings();


        System.out.println("Check cusRatings: " +cusRatings[1]);

        //Button, on click picks a cuisine and gets its dishes, puts dish IDs in dishID[]
        getCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //System.out.println("Check cusRatings: " +cusRatings[1]);

                String test;
                Integer temp;
                temp = pickCuisine(cusRatings);
                test = String.valueOf(temp);
                String toSet = "cusID: " + test;
                getCuisine.setText(toSet);
                cusID = test;

                getDishes();

            }
        });


        //Button, on click picks a dish and gets its ID, puts dish IDs in recommendationID
        recommendDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Check dishID: " +dishID[1]);

                Integer randomIndex = generator.nextInt(dishID.length);
                System.out.println("Check randomIndex: " +randomIndex);
                Integer temp = dishID[randomIndex];
                String toString = String.valueOf(temp);
                showDish.setText(toString);
                recommendationID = toString;
            }
        });


        //Button, on click gets the name of dish recommendation and stores it in recName
        getName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Check recommendationID: " +recommendationID);
                getName();
            }
        });

        //Button, displays recommendation name on screen
        showRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Check recName: " + recName);

                showName.setText(recName);

                Intent intent = new Intent(getApplicationContext(), SelectRestaurant.class);
                intent.putExtra("RECOMMEND", recName);
                startActivity(intent);
            }
        });

    }

    //Algorithm for choosing a cuisine
    //Takes an array of user cuisine ratings
    //Returns cuisine ID
    public Integer pickCuisine(Integer[] x){
        Integer[] a = x;
        int sum = 0;
        for(int i: a)
            sum += i;
        Random r = new Random();
        int s = r.nextInt(sum);  //Get selection position (not array index)

        //Find position in the array:
        int prev_value = 0;
        int current_max_value = 0;
        int found_index = -1;
        for(int i=0; i< a.length; i++){ //walk through the array
            current_max_value = prev_value + a[i];
            //is between beginning and end of this array index?
            boolean found = (s >= prev_value && s < current_max_value)? true : false;
            if( found ){
                found_index = i+1;
                break;
            }
            prev_value = current_max_value;
        }

        return found_index;

    }

    //Gets cuisine ratings of current user
    //Results are stored in cusRatings array
    public void getRatings (){
        request = new StringRequest(Request.Method.POST, getRatings, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Creating JsonObject from response String
                    JSONObject jsonObject= new JSONObject(response.toString());

                    JSONArray items = jsonObject.getJSONArray("products");


                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);

                        String rating = item.getString("Rating");

                        //Change to int
                        Integer b = Integer.valueOf(rating);
                        //Store in cusRatings
                        cusRatings[i] = b;

                        //System.out.println("Check cusRatings:" +cusRatings[i]);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    //Gets dishes from a specified cuisine (cuisine ID is stored in class variable "cusID")
    //Results are stored in dishID array
    public void getDishes(){

        request = new StringRequest(Request.Method.POST, getDishes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Creating JsonObject from response String
                    JSONObject jsonObject= new JSONObject(response.toString());

                    JSONArray items = jsonObject.getJSONArray("products");

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);

                        String dishIDHere = item.getString("Dish_ID");

                        //Change to int
                        Integer d = Integer.valueOf(dishIDHere);
                        //Store in dishID
                        dishID[i] = d;

                        //System.out.println("Check dish:" + dishID[i]);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("Cus_ID",cusID);

                return hashMap;
            }
        };
        requestQueue.add(request);


    }


    public void getName(){
        request = new StringRequest(Request.Method.POST, getDishName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Creating JsonObject from response String
                    JSONObject jsonObject= new JSONObject(response.toString());

                    JSONArray names = jsonObject.getJSONArray("products");

                    for (int i = 0; i < names.length(); i++) {
                        JSONObject name = names.getJSONObject(i);

                        String recommend = name.getString("Name");

                        //Change to int

                        //Store in dishID
                        recName = recommend;

                        //System.out.println("Check recName:" + recName);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("Dish_ID",recommendationID);

                return hashMap;
            }
        };
        requestQueue.add(request);


    }


}



