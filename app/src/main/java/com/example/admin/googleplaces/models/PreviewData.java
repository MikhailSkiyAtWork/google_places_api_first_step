package com.example.admin.googleplaces.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class PreviewData {
    private String name_;
    private String address_;
    private ArrayList<Bitmap> images_;
    private String placeId_;
    private double latitude_;
    private double longitude_;

    public List<Bitmap> getImages() {
        return images_;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images_ = images;
    }


    public String getName() {
        return this.name_;
    }

    public void setName(String name) {
        this.name_ = name;
    }

    public String getAddress() {
        return this.address_;
    }

    public void setAddress(String address) {
        this.address_ = address;
    }

    public String getPlaceId() {
        return this.placeId_;
    }

    public void setPlaceId(String placeId) {
        this.placeId_ = placeId;
    }

    public void setLatitude(double latitude){
        this.latitude_ = latitude;
    }

    public double getLatitude(){
        return this.latitude_;
    }

    public void setLongitude(double longitude){
        this.longitude_ = longitude;
    }

    public double getLongitude(){
        return this.longitude_;
    }
}
