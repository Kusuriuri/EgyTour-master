package com.example.android.egytour;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NearbyLocationDetail extends AppCompatActivity {
    public static final String URL ="http://192.168.1.3/test.php";
    public String landmark_name;
    public String phone_number;
    public String user_name;
    public String rating;
    public Double latitude;
    public Double longitude;
    public TextView title;
    public ImageView disimage;
    public RatingBar ratingview;
    public TextView number;
    public ArrayList<String> reviews=new ArrayList<String>();
    public ArrayList<String> users=new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_location_detail);
        title= (TextView) findViewById(R.id.landmark);
        disimage=(ImageView) findViewById(R.id.landmark_image);
        ratingview=(RatingBar) findViewById(R.id.ratingBar2);
        number=(TextView) findViewById(R.id.number);
        if(getIntent().hasExtra("name")) {
            latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
            longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
            user_name=getIntent().getStringExtra("user_name");
            landmark_name = getIntent().getStringExtra("name");
            title.setText(landmark_name);
            phone_number = getIntent().getStringExtra("phone");
            number.setText("Phone Number: "+phone_number);
            rating = getIntent().getStringExtra("rating");
            ratingview.setRating(Float.parseFloat(rating));
            byte[] byteArray = getIntent().getByteArrayExtra("icon");
            Bitmap icon = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            disimage.setImageBitmap(icon);
        }
        RequestQueue rq= Volley.newRequestQueue(this);
        StringRequest sr= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String o=jsonObject.getString("length");
                    int length=Integer.parseInt(o);
                    for(int j=1;j<=length;j++){
                        String rev=jsonObject.getString("review"+j);
                        reviews.add(rev);
                        String user = jsonObject.getString("user"+j);
                        users.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NearbyLocationDetail.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("landmark_name",landmark_name);
                return params;
            }
        };
        rq.add(sr);
    }
    public void call(View view){
        phone_number.replaceAll("\\s+","");
        Log.d("CallTest",phone_number);
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:"+phone_number));
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        if (ActivityCompat.checkSelfPermission(NearbyLocationDetail.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("CallTest","Not good");

            return;
        }

        startActivity(phoneIntent);
    }
    public void map(View view){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void gotoReviews(View view) {
        Intent x= new Intent(NearbyLocationDetail.this,ReviewsList.class);
        x.putExtra("revs",reviews);
        x.putExtra("user_names",users);
        startActivity(x);
    }
    public void write(View view){
        Intent x =new Intent(NearbyLocationDetail.this,WriteReview.class);
        x.putExtra("user_name",user_name);
        x.putExtra("landmark_name",landmark_name);
        startActivity(x);

    }
}
