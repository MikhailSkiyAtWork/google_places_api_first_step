package com.example.admin.googleplaces.models;

/**
 * Created by Mikhail Valuyskiy on 09.07.2015.
 */

import java.util.List;

/**
 * General class for Place Details information
 */
public class PlaceDetails {
    protected List<Photo> photos_;
    public List<Photo> getPhotos(){
        return this.photos_;
    }
}
