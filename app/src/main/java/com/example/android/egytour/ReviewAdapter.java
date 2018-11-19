package com.example.android.egytour;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReviewAdapter extends ArrayAdapter<Review>{
    private Context context;
    private ArrayList<Review> reviews;

    public  ReviewAdapter (Context context , ArrayList<Review> s ){
        super(context,0,s);
        this.context=context;
        this.reviews = s;

    }

    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.review, parent, false);
        }
        Review current = (Review) getItem(position);
        TextView user = (TextView) listItemView.findViewById(R.id.userName);
        TextView rev = (TextView) listItemView.findViewById(R.id.landmarkReview);
        user.setText(current.getUser_name()+":");
        rev.setText(current.getRev());
        return listItemView;



    }

}
