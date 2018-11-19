package com.example.android.egytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WriteReview extends AppCompatActivity {
    public static final String URL ="http://192.168.1.3/postrev.php";
    public String rev;
    public String user_name;
    public String landmark_name;
    public EditText r;
    public RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        ratingBar = findViewById(R.id.ratingBar);
        user_name=getIntent().getStringExtra("user_name");
        landmark_name=getIntent().getStringExtra("landmark_name");
        r=(EditText) findViewById(R.id.emailText);
    }



    public void postRev(View view){
        rev= r.getText().toString().trim();
        RequestQueue rq= Volley.newRequestQueue(this);
        StringRequest sr= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
               // Log.d("Test1","ko");
                Toast.makeText(WriteReview.this, "Review added successfully!", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WriteReview.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("landmark_name",landmark_name);
                params.put("review",rev);
                params.put("rating",ratingBar.getRating()+"");
                params.put("name",user_name);
                return params;
            }
        };
        rq.add(sr);
    }
}
