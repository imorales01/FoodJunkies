package fj.foodjunkies;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectRestaurant extends AppCompatActivity implements android.location.LocationListener {

    //Parameters and constraints for the Yelp API
    private String apiKey = "oNavXIvI6AgPNIM-fsgq2EBTWIJJKySS0yE-t-ANnOMTaJKiJJI1gT_DssXmcRCgVOgYQZQ8Jx2vPlnQ-jbjSrdaccAUT1-Qkap-wkBwQZ9MSVy_E39a1ekSI_rpWnYx";
    private String longitude = "40.7685"; //Coordinates for location, Hunter College
    private String latitude = "-73.9646";
    private String radius = "3000"; //1 mile, 1610 meters | 5 miles, 8046.72 meters (Change values here for testing distance)

    private String term; //Search for term of food or restraunts
    private String limit = "3"; //Limit search to 3
    private String open_now = "true"; //Show only open stores
    private String id;

    private fj.foodjunkies.DataBaseHelper db;
    private int userID = 1; //Current user
    private android.location.Location location;

    private int REQUEST_LOCATION_ACCESS = 1;
    ArrayList <Restaurant> restaurants;

    private TextView foodText;
    private ListView restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_restaurant);
        ActionBar actionBar = getSupportActionBar(); //Set back button on the title bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Select Restaurant");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //Create a LocationManager to get the location of the user
        db = new fj.foodjunkies.DataBaseHelper(this); //Create a database with DataBaseHelper

        //Check if the app has location permissions, if not then request them
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}, REQUEST_LOCATION_ACCESS);
                return;
            }
        }

        //Get an intent extra from the Recommend activity which will contain the food to search for
        Intent intent = getIntent();
        term = intent.getStringExtra("RECOMMEND");

        restaurantList = (ListView) findViewById(R.id.restaurantList);
        foodText = (TextView) findViewById(R.id.textFood);
        foodText.setText(term);
        foodText.setAllCaps(true);

        //Create a location manager to get the GPS of current location
        double Longitude=0, Latitude=0;
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //Get the last known GPS location

        if (location!=null){ //If there was a last known location then get the coordinates
            Longitude = location.getLongitude();
            Latitude = location.getLatitude();
            Log.d("location@","Not NULL: " + Longitude + " " + Latitude);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this); //Request a GPS update
        }

        //Get the user Constraints from the database to obtain parameters for the YELP API
        int budget, distance, time;
        if (db.userExists(userID)){
            budget = db.getBudget(userID);
            distance = db.getDistance(userID);
            time = db.getTime(userID);
        }


        //Create an API Factory object and authenticate with the API key
        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
        YelpFusionApi yelpFusionApi = null;
        try {
            yelpFusionApi = apiFactory.createAPI(apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Put parameters for the Yelp API GET request
        Map<String, String> params = new HashMap<>();

        params.put("term", term); //The food that is searched for, obtained from the Recommendation process
        params.put("latitude", longitude);
        params.put("longitude", latitude);
        params.put("limit", limit); //Limit is 3 restraunts on display
        params.put("radius", radius);
        params.put("open_now", open_now); //Restaurant is open

        //@@@@ FOR TESTING: if you want to use a hard coded Hunter address then do nothing, other wise comment out the [param longitude & latitude above]and uncomment the one down here
        //Values obtained from user Constraints, and GPS location
//        params.put("price", String.valueOf(budget)); //Budget constraints
//        params.put("radius", String.valueOf(distance)); //Radius is the distance from current position
//        params.put("latitude", String.valueOf(Latitude)); //Longitude and Latitude of current GPS location
//        params.put("longitude", String.valueOf(Longitude));

        restaurants = new ArrayList <Restaurant>(); //Initialize an empty restraunt ArrayList to store restraunts returned from the API result

        //Create a Call object inserting the parameters
        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);
        //Call the API asynchronously so it doesn't block the main UI thread
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
     Log.d("@@@", String.valueOf(response));

                int totalNumberOfResult = searchResponse.getTotal();
                ArrayList<Business> businesses = searchResponse.getBusinesses(); //API returns an ArrayList of the business objects representing businesses found matching the paramters
                //If the Yelp API returned restraunts matching the parameters, then parse the business objects for relevant restaurant information and save to an ArrayList
                if (!businesses.isEmpty()){
                    Log.d("@@@", "Size: " + String.valueOf(businesses.size()));
                    for (int i=0; i<businesses.size(); i++){
                        String businessID = businesses.get(i).getId();
                        String businessName = businesses.get(i).getName();  //Get the business name
                        Double rating = businesses.get(i).getRating();  //Get the rating
                        com.yelp.fusion.client.models.Location businessLocation = businesses.get(i).getLocation(); //Get the address
                        String address = String.valueOf(businessLocation.getDisplayAddress()); //Get the displayable address in String format
                        String phone = businesses.get(i).getPhone(); //Get the phone number
                        String price = businesses.get(i).getPrice(); //Get the price rating $$$$
                        String imageURL = businesses.get(i).getImageUrl(); //Get the image url for display
                        double milesAway = metersToMiles(businesses.get(i).getDistance()); //Get the distance of the restaurant in meters and convert into miles

                        //Create a new Restaurant object to store information on the restaurant
                        Restaurant restaurant = new Restaurant(businessID, businessName, address, phone, price, rating, milesAway, imageURL);
                        restaurants.add(restaurant); //Add to the restaurant ArrayList
     Log.d("@@@", "# of Restraunts found: "+ totalNumberOfResult + " / Business id: " + businessID + " / Name: " + businessName + " / Rating: " + rating + " / Address: " + address + " / Miles Away: " + milesAway);
                    }

//@@@@@@@@@ Creating a basic ListView atm with just the name of the restaurant
                    //Create an ArrayList with the String names of the restaurants because we can't create an ArrayAdapter with ArrayList of object Food
                    final ArrayList<String>  restaurantNames = new ArrayList<String>();
                    for (int i=0; i<restaurants.size(); i++){
                        String displayRestaurant = "\n" + restaurants.get(i).name + " ("
                                +  restaurants.get(i).price + ") "
                                +  "\nAddress: " + restaurants.get(i).address
                                + "\nRating: " + restaurants.get(i).rating
                                +"\nDistance: " + metersToMiles(businesses.get(i).getDistance()) + " miles\n";
                        restaurantNames.add(displayRestaurant);
                    }

                    //Bind the ArrayList to an Array Adapter for display, and create a custom view with white text
                    ArrayAdapter<String> restaurantAdapter = new ArrayAdapter<String>(SelectRestaurant.this, android.R.layout.simple_list_item_1, restaurantNames) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent); // Get the Item from ListView
                            TextView tv = (TextView) view.findViewById(android.R.id.text1); // Initialize a TextView for ListView each Item
                            tv.setTextColor(Color.WHITE);// Set the text color of TextView to white
                            return view;
                        }
                    };
                    restaurantList.setAdapter(restaurantAdapter);

                    //When a restaurant is selected, pass the restaurant to another activity to display additional information
                    restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Create an intent to pass the restaurants information to the next activity for display
                            Intent passIntent = new Intent(SelectRestaurant.this, DisplayRestaurant.class);
                            passIntent.putExtra("id", restaurants.get(position).id);
                            passIntent.putExtra("name", restaurants.get(position).name);
                            passIntent.putExtra("address", restaurants.get(position).address);
                            passIntent.putExtra("phone", restaurants.get(position).phone);
                            passIntent.putExtra("price", restaurants.get(position).price);
                            passIntent.putExtra("distance", String.valueOf(restaurants.get(position).distance));
                            passIntent.putExtra("rating", String.valueOf(restaurants.get(position).rating));
                            passIntent.putExtra("imageURL", String.valueOf(restaurants.get(position).imageURL));
                            passIntent.putExtra("food", term); //Pass the food name
                            startActivity(passIntent); //Start the next activity
                        }
                    });
                }
                else { //There were no restaurants that matched the parameters
                    Toast.makeText(getApplicationContext(), "There were no restraunts nearby serving this dish." , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SelectRestaurant.this, Recommend.class); //Go back to the previous recommendation page if no restraunts are found within the constraints
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // HTTP error happened, do something to handle it.
                Log.d("@@@", "Failed to fetch API");
            }
        };
        call.enqueue(callback);
    }

    //Convert from meters to miles
    private double metersToMiles (double meters) {
        double distanceInMiles;
        distanceInMiles = meters * 0.000621371; //Convert meters to miles
        distanceInMiles = Math.round(distanceInMiles * 100); //Round the miles to nearest 2 decimal places
        distanceInMiles = distanceInMiles/100;
        return distanceInMiles;
    }

    //Go back to the previous activity on back arrow press
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(SelectRestaurant.this, fj.foodjunkies.Recommend.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("location@", location.getLongitude() + " " + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    //Class to hold information on a restaurant
    public class Restaurant {
        Restaurant (String id, String name, String address, String phone, String price,
                    double rating, double distance, String imageURL)
        {
            this.id = id;
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.price = price;
            this.rating = rating;
            this.distance = distance;
            this.imageURL = imageURL;
        }

        public String id;
        public String name;
        public String address;
        public String phone;
        public String price;
        public String imageURL;
        public double distance;
        public double rating;
    }
}
