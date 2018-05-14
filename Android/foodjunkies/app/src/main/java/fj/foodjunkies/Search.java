package fj.foodjunkies;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Search extends AppCompatActivity {

    private String apiKey = "oNavXIvI6AgPNIM-fsgq2EBTWIJJKySS0yE-t-ANnOMTaJKiJJI1gT_DssXmcRCgVOgYQZQ8Jx2vPlnQ-jbjSrdaccAUT1-Qkap-wkBwQZ9MSVy_E39a1ekSI_rpWnYx";
    private String longitude = "40.7685"; //Coordinates for location, Hunter College
    private String latitude = "-73.9646";
    private double Longitude = 0;
    private double Latitude = 0;
    private String radius = "1610"; //1 mile, 1610 meters
    private String open_now = "true";
    private android.location.Location location;
    private int REQUEST_LOCATION_ACCESS = 1;
    ArrayList<Restaurant> restaurants;
    private fj.foodjunkies.DataBaseHelper db;

    Map<String, String> params = new HashMap<>();

    EditText editTextSearchField;
    Button buttonSearch;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearchField = (EditText) findViewById(R.id.editTextSearchField);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
