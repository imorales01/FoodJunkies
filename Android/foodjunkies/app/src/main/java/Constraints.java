package fj.foodjunkies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;

public class Constraints extends AppCompatActivity {
    //Declaring Seekbars and textviews
    private static SeekBar budget_bar;
    private static TextView budget_text;

    private static SeekBar distance_bar;
    private static TextView distance_text;

    private static SeekBar time_bar;
    private static TextView time_text;

    private static
    //Declare DataBase Helper class
    fj.foodjunkies.DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraints);
        db = new fj.foodjunkies.DataBaseHelper(this); //Create a database with DataBaseHelper

        int ID=1; //Temporary ID for testing, feed in current ID from another activity

        seekBar(ID); //Create the seek bars
    }

    public void doneButtonClicked(View view) {
        startActivity(new Intent(getApplicationContext(), fj.foodjunkies.Welcome.class));
    }


    //Seek Bar saves the information from the user with sliders
    public void seekBar(final int ID){
        //Connecting the seek bars and texts
        budget_bar = (SeekBar)findViewById(R.id.barBudget);
        budget_text = (TextView)findViewById(R.id.budgetNum);
        budget_text.setText("$" + budget_bar.getProgress());

        distance_bar = (SeekBar)findViewById(R.id.barDistance);
        distance_text = (TextView)findViewById(R.id.distanceNum);
        distance_text.setText("" + distance_bar.getProgress() + " miles");

        time_bar = (SeekBar)findViewById(R.id.barTime);
        time_text =(TextView)findViewById(R.id.timeNum);
        time_text.setText("" + time_bar.getProgress() + " mins");

        //If the user already exists in the database get values and update the seek bars
        if (db.userExists(ID)) {
            //Get the constraint values from the database for the user
            int budget = db.getBudget(ID);
            int distance = db.getDistance(ID);
            int time = db.getTime(ID);

            //Set values of the seek bars to the user's from the SQLite database
            budget_bar.setProgress(budget);
            distance_bar.setProgress(distance);
            time_bar.setProgress(time);

            //Set the default text after fetching values
            budget_text.setText("$" + budget );
            distance_text.setText(distance + " miles");
            time_text.setText(time + " min");
        }
        else { //The user doesn't exist so we should add it to the database
            db.addUser(ID); //User added with default values 0 for budget, time, distance constraints
        }

        //Seek bar for budget
        budget_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value; //Store the current amount of the slider
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        budget_text.setText("$" + progress);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        budget_text.setText("$" + progress_value );
                        db.updateBudget(ID,progress_value); //Update the budget constraint for the user in a database
                    }
                }
        );

        //Seek bar for distance
        distance_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        distance_text.setText( progress + " miles");
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        distance_text.setText( progress_value + " miles");
                        db.updateDistance(ID,progress_value); //Update the distance constraints for the user in a database
                    }
                }
        );

        //Seek bar for time
        time_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        time_text.setText(progress + " min");
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        time_text.setText(progress_value + " min");
                        db.updateTime(ID,progress_value); //Update the time constraints for the user in a database
                    }
                }
        );
    }
}
