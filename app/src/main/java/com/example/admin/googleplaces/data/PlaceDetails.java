package com.example.admin.googleplaces.data;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */

import java.net.URL;
import java.util.List;

/**
 * Describes details about the place
 */
public class PlaceDetails {
    private String id_;
    private String placeId_;
    private String name_;

    // URL for icon
    private URL iconUrl_;
    private List<Photo> photos_;
    private List<String> types_;
    private double rating_;

    //region Constructors

    public PlaceDetails(String id,String placeId_, String name_, URL iconUrl_, List<Photo> photos_, List<String> types_, double rating_) {
        this.id_ = id;
        this.placeId_ = placeId_;
        this.name_ = name_;
        this.iconUrl_ = iconUrl_;
        this.photos_ = photos_;
        this.types_ = types_;
        this.rating_ = rating_;
    }

    public PlaceDetails(String id, String placeId_, String name_, URL iconUrl_, List<String> types_) {
        this.id_ = id;
        this.placeId_ = placeId_;
        this.name_ = name_;
        this.iconUrl_ = iconUrl_;
        this.types_ = types_;
    }
    //endregion

    //region Accessors

    public String getId() {
        return this.id_;
    }

    public void setId_(String id) {
        this.id_ = id;
    }

    public String getPlaceId() {
        return this.placeId_;
    }

    public void setPlaceId(String placeId) {
        this.placeId_ = placeId;
    }

    public String getName() {
        return this.name_;
    }

    public void setName(String name) {
        this.name_ = name;
    }

    public URL getIconUrl() {
        return this.iconUrl_;
    }

    public void setIconUrl(URL iconUrl) {
        this.iconUrl_ = iconUrl;
    }

    public List<Photo> getPhotos() {
        return this.photos_;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos_ = photos;
    }

    public List<String> getTypes() {
        return this.types_;
    }

    public void setTypes(List<String> types) {
        this.types_ = types_;
    }

    public double getRating() {
        return this.rating_;
    }

    public void setRating(double rating) {
        this.rating_ = rating;
    }
//endregion


}
