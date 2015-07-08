package com.example.admin.googleplaces.data;

/**
 * Created by Mikhail Valuyskiy on 08.07.2015.
 */

import java.util.List;

/**
 * Describes explicit details about selected place
 */
public class PlaceDetails {
    private String name_;
    private List<Photo> photos_;

    public String getName() {
        return this.name_;
    }

    public List<Photo> getPhotos() {
        return this.photos_;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos_ = photos;
    }

    public void setName(String name) {
        this.name_ = name;
    }
}
