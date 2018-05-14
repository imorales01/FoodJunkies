package fj.foodjunkies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends Activity {

    private ImageView imageViewWelcomeBackground;
    private TextView textViewUserID;
    private Button buttonQuiz;
    private Button buttonSearch;
    private Button buttonRecommend;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userID = sharedPref.getString("userID", "");

        setTitle("Welcome to the homepage!");

        //imageViewWelcomeBackground = (ImageView) findViewById(R.id.imageViewWelcomeBackground);

        textViewUserID = (TextView) findViewById(R.id.textViewUserID);

        textViewUserID.append(userID);

        buttonQuiz = (Button) findViewById(R.id.buttonQuiz);
        buttonQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.ChooseCuisine.class));

            }
        });

        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.Search.class));

            }
        });

        buttonRecommend = (Button) findViewById(R.id.buttonRecommend);
        buttonRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.Recommend.class));

            }
        });

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.LoginActivity.class));

            }
        });

    }
}