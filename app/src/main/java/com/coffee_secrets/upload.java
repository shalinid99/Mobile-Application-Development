package com.coffee_secrets;

public class upload {


    private String mName;
    private String mImageUrl;


    public upload(String name, String ImageUrl){

             if (name.trim().equals("")){
                 name = "no name";
             }


        this.mName = name;
        this.mImageUrl = ImageUrl;
    }


    public String getmName(){
        return mName;

    }


    private void setmName(String name){
        mName = name;

    }


    public String getmImageUrl(){
        return mImageUrl;

    }


    private void setmImageUrl(String imageUrl){
        mName = imageUrl;

    }



}
