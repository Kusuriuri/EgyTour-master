package com.example.android.egytour;

public class Review {
    private String user_name;
    private String rev;

    public Review(String user_name, String rev){
        this.user_name=user_name;
        this.rev=rev;

    }

    public String getUser_name() {
        return user_name;
    }

    public String getRev() {
        return rev;
    }

}
