package wu.chukho.foodjunkies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class ChooseCuisine extends AppCompatActivity {

    GridView androidGridView;

    String[] gridViewString = {
            "American", "Chinese", "European", "Indian", "Italian", "Japanese", "Korean", "Mediterranean", "Middle Eastern", "Spanish",
            //"American", "Chinese", "European", "Indian", "Italian", "Japanese", "Korean", "Mediterranean", "Middle Eastern", "Spanish",
            //"American", "Chinese", "European", "Indian", "Italian", "Japanese", "Korean", "Mediterranean", "Middle Eastern", "Spanish",
    };

    int[] gridViewImage = {
            R.drawable.american, R.drawable.chinese, R.drawable.european, R.drawable.indian, R.drawable.italian, R.drawable.japanese, R.drawable.korean, R.drawable.mediterranean, R.drawable.middleeastern, R.drawable.spanish,
            //R.drawable.american, R.drawable.chinese, R.drawable.european, R.drawable.indian, R.drawable.italian, R.drawable.japanese, R.drawable.korean, R.drawable.mediterranean, R.drawable.middleeastern, R.drawable.spanish,
            //R.drawable.american, R.drawable.chinese, R.drawable.european, R.drawable.indian, R.drawable.italian, R.drawable.japanese, R.drawable.korean, R.drawable.mediterranean, R.drawable.middleeastern, R.drawable.spanish,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cuisine);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(ChooseCuisine.this, gridViewString, gridViewImage);
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

}
