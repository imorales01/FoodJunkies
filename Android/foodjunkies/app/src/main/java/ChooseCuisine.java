package fj.foodjunkies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ChooseCuisine extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String URL = "http://54.208.66.68:80/setdefaultRating.php";
    private StringRequest request;
    private String userID;

    GridView androidGridView;

    String[] gridViewString = {
            "American", "Chinese", "European", "Indian", "Italian", "Japanese", "Korean", "Mediterranean", "Middle Eastern", "Spanish"
    };

    int[] gridViewImage = {
            R.drawable.american, R.drawable.chinese, R.drawable.european, R.drawable.indian, R.drawable.italian, R.drawable.japanese, R.drawable.korean, R.drawable.mediterranean, R.drawable.middleeastern, R.drawable.spanish,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cuisine);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sharedPref.getString("userID", "");

        //set up volley
        requestQueue = Volley.newRequestQueue(this);

        //Run the function to set default ratings
        setDefaultRating();

        fj.foodjunkies.CustomGridViewActivity adapterViewAndroid = new fj.foodjunkies.CustomGridViewActivity(ChooseCuisine.this, gridViewString, gridViewImage);
        androidGridView = (GridView) findViewById(R.id.displayCuisines);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(ChooseCuisine.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setDefaultRating(){

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.names().get(0).equals("success")) {
                        Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_LONG).show();
                    }
                    if (jsonObject.names().get(0).equals("fail")) {
                        Toast.makeText(getApplicationContext(), "Fail " + jsonObject.getString("fail"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error" + jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("User_ID", userID);

                return hashMap;
            }
        };

        requestQueue.add(request);
    }

    public void doneButtonClick(View view) {
        // Start the profile activity
        startActivity(new Intent(getApplicationContext(), fj.foodjunkies.Constraints.class));
    }
}
