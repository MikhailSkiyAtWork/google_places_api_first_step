package com.example.admin.googleplaces.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class PreviewData {
    private String name_;
    private String address_;
    private List<Bitmap> image_;
    private String placeId_;

    public List<Bitmap> getImage_() {
        return image_;
    }

    public void setImage_(List<Bitmap> image_) {
        this.image_ = image_;
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
}
