package fj.foodjunkies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

public class Welcome extends Activity {

    private Button log_out;
    private Button recommend;
    private Button quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        TextView displayID = (TextView)findViewById(R.id.displayID);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String userID = sharedPref.getString("userID", "");
        displayID.setText(userID);

        log_out = (Button) findViewById(R.id.button);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.LoginActivity.class));

            }
        });

        recommend = (Button) findViewById(R.id.button3);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.Recommend.class));

            }
        });

        quiz = (Button) findViewById(R.id.button2);
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.CuisineQuiz.class));

            }
        });
    }


}