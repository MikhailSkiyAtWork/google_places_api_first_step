package com.example.admin.googleplaces.models;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */

import java.net.URL;
import java.util.List;

/**
 * Describes details about the nearby place
 */
public class NearbyPlaceDetails extends PlaceDetails {
    private String id_;
    private String placeId_;
    private String name_;
    private double latitude_;
    private double longitude_;

    // URL for icon
    private URL iconUrl_;

    private List<String> types_;
    private double rating_;

    //region Constructors

    public NearbyPlaceDetails(String id, String placeId_, String name_, URL iconUrl_, List<Photo> photos_, List<String> types_, double rating_,double latitude, double longitude) {
        this.id_ = id;
        this.placeId_ = placeId_;
        this.name_ = name_;
        this.iconUrl_ = iconUrl_;
        this.photos_ = photos_;
        this.types_ = types_;
        this.rating_ = rating_;
        this.latitude_ = latitude;
        this.longitude_ = longitude;
    }

    public NearbyPlaceDetails(String id, String placeId_, String name_, URL iconUrl_, List<String> types_) {
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

//endregion


}
