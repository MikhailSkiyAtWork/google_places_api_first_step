package com.example.admin.googleplaces.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mikhail Valuyskiy on 09.07.2015.
 */

// For using abstract class in is necessary that all descendants use the same params, but depend on
// the request the number of params may be different.
// So I decided to create RequestParams.cs class which has all params and different constructors  and properties.
// (Take a look) So I have question is it ok to us properties or constructor?
public class RequestParams {

    private static final String COMMA = ",";

    // Base URL
    private String baseUrl_;

    // Google Places Api Key
    private String apiKey_;

    // Latitude and Longitude of point
    private String point_;

    private String radius_;

    // A textual identifier that uniquely identifies a place, returned from a Place Search request
    private String placeId_;

    // Maximum desired width
    private String maxWidth;

    // Maximum desired height
    private String maxHeight;

    //  A string identifier that uniquely identifies a photo. Are returned from Place Details request
    private String photoReference_;

    // Query for text search
    public String query_;

    public String getPhotoReference() {
        return this.photoReference_;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference_ = photoReference;
    }




    //region Accessors
    public String getApiKey() {
        return this.apiKey_;
    }

    public void setApiKey(String apiKey) {
        this.apiKey_ = apiKey;
    }

    public String getPoint() {
        return this.point_;
    }

    public void setPoint(LatLng point) {
        String latitude = Double.toString(point.latitude);
        String longitude = Double.toString(point.longitude);
        String location = latitude + COMMA + longitude;
        this.point_ = location;
    }

    public String getPlaceId(){
        return this.placeId_;
    }

    public void setPlaceId(String placeId){
        this.placeId_ = placeId;
    }

    public String getRadius() {
        return this.radius_;
    }

    public void setRadius(int radius) {
        this.radius_ = Integer.toString(radius);
    }

    public void setMaxWidth(int maxWidth){
        this.maxWidth = Integer.toString(maxWidth);
    }

    public String getMaxWidth(){
        return this.maxWidth;
    }

    public void setMaxHeight(int maxHeight){
        this.maxHeight = Integer.toString(maxHeight);
    }

    public String getMaxHeight(){
        return this.maxHeight;
    }

    public void setQuery(String query){
        this.query_ = query;
    }

    public String getQuery(){
        return this.query_;
    }

    //endregion

    //region Constructors

    public  RequestParams(){

    }

    // Create params for Place Search Requst
    public RequestParams(LatLng point, int radius, String key) {
        setPoint(point);
        setRadius(radius);
        this.apiKey_ = key;
    }

    // Create params for Place Details Request
    public RequestParams(String placeId, String key) {
        this.placeId_ = placeId;
        this.apiKey_ = key;
    }

    // Create params for Photo Request
    public RequestParams(int maxWidth, int maxHeight, String photoReference, String key){
        setMaxWidth(maxWidth);
        setMaxHeight(maxHeight);
        this.photoReference_ = photoReference;
        this.apiKey_ = key;
    }

    public RequestParams(String query, LatLng point,int radius, String key){
        this.query_ = query;
        setPoint(point);
        setRadius(radius);
        this.apiKey_ = key;
    }

    //endregion
}
