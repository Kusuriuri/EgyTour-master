package com.example.android.egytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewsList extends AppCompatActivity {
    public ArrayList<String> reviews;
    public ArrayList<String> users;
    public ArrayList<Review> objectReviews;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_list);
        objectReviews= new ArrayList<Review>();
        reviews=getIntent().getStringArrayListExtra("revs");
        users=getIntent().getStringArrayListExtra("user_names");
        for (int k=0;k<reviews.size();k++){
            objectReviews.add(new Review(users.get(k),reviews.get(k)));
        }
        listView = (ListView) findViewById(R.id.list);
        ReviewAdapter adapter = new ReviewAdapter(ReviewsList.this,objectReviews);
        listView.setAdapter(adapter);

    }
}
