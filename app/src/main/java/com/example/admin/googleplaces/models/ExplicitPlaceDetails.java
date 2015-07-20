package com.example.admin.googleplaces.models;

/**
 * Created by Mikhail Valuyskiy on 08.07.2015.
 */

import java.util.List;

/**
 * Describes explicit details about selected place
 */
public class ExplicitPlaceDetails extends PlaceDetails {
    private String name_;


    public  ExplicitPlaceDetails(){}

    //region Accessors
    public String getName() {
        return this.name_;
    }

    public void setName(String name) {
        this.name_ = name;
    }

    public List<Photo> getPhotos() {
        return this.photos_;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos_ = photos;
    }
    //endregion

    //region Constructors

    public ExplicitPlaceDetails(String name, List<Photo> photos) {
        this.name_ = name;
        this.photos_ = photos;
    }
    //endregion

}
