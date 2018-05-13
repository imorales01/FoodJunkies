package fj.foodjunkies;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;

public class CuisineQuiz extends AppCompatActivity {

    ArrayList<String> cuisineStack;
    ArrayList<Integer> images;
    boolean [] preference;

    ImageView like;
    ImageView dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_quiz);
        ActionBar actionBar = getSupportActionBar(); //Set back button on the title bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Food Quiz");

        like = (ImageView) findViewById(R.id.heart);
        dislike = (ImageView) findViewById(R.id.ximage);
        like.setVisibility(View.INVISIBLE);
        dislike.setVisibility(View.INVISIBLE);

        //Create a list of cuisines for the user to like/dislike
        cuisineStack = new ArrayList<String>();
        cuisineStack.add("American");
        cuisineStack.add("Chinese");
        cuisineStack.add("Korean");
        cuisineStack.add("European");
        cuisineStack.add("Indian");
        cuisineStack.add("Italian");
        cuisineStack.add("Japanese");
        cuisineStack.add("Mediterranean");
        cuisineStack.add("Middle Eastern");
        cuisineStack.add("Spanish");

        //Create an ArrayList to hold the R.id of the images to be loaded corresponding to the cuisines
        images = new ArrayList<Integer>();
        images.add(R.drawable.american);
        images.add(R.drawable.chinese);
        images.add(R.drawable.korean);
        images.add(R.drawable.european);
        images.add(R.drawable.indian);
        images.add(R.drawable.italian);
        images.add(R.drawable.japanese);
        images.add(R.drawable.mediterranean);
        images.add(R.drawable.middleeastern);
        images.add(R.drawable.spanish);

        //Create an array of booleans that stores if the user liked or disliked the cuisine
        preference = new boolean [10];

        //Initialize a SwipeStack object which is the set of cards
        SwipeStack swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        swipeStack.setAdapter(new SwipeStackAdapter(cuisineStack, images));

        //Create a listener to save if the user liked or disliked a cuisine
        SwipeStack.SwipeStackListener listener = new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                //Make the dislike image appear and disappear
                dislike.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        dislike.setVisibility(View.INVISIBLE);
                    }
                }, 600);
                //On left swipe, set cuisine to false to indicate dislike
                preference[position]=false;
            }

            @Override
            public void onViewSwipedToRight(int position) {
                //Make the like image appear and disappear
                like.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        like.setVisibility(View.INVISIBLE);
                    }
                }, 600);
                //On right swipe, set cuisine to true to indicate like
                preference[position]=true;
            }

            @Override
            public void onStackEmpty() {
                //Save the user's preferences into the MySQL server

                //              Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), fj.foodjunkies.Constraints.class));
            }
        };
        swipeStack.setListener(listener);
    }


    //Swipe Stack Adapter class to set the views for the cards
    public class SwipeStackAdapter extends BaseAdapter {

        private ArrayList<String> mData;
        private ArrayList<Integer> mImage;

        public SwipeStackAdapter(ArrayList<String> data, ArrayList<Integer> images) {
            this.mData = data;
            this.mImage = images;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.card, parent, false);
            TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
            textViewCard.setText(mData.get(position));
            ImageView cuisineBackground = (ImageView) convertView.findViewById(R.id.cuisineBackground);
            cuisineBackground.setImageResource(mImage.get(position));

            return convertView;
        }
    }

    //Go back to the previous activity on back arrow press
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(CuisineQuiz.this, Welcome.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
