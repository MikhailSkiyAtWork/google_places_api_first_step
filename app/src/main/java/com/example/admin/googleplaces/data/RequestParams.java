package com.example.admin.googleplaces.data;

import com.example.admin.googleplaces.requests.Request;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mikhail Valuyskiy on 09.07.2015.
 */
public class RequestParams {

    private static final String COMMA = ",";

    // Base URL
    private String baseUrl_;
    // Google Places Api Key
    private String apiKey_;
    // Latitude and Longitude of point
    private String point_;
    private String radius_;
    private String placeId_;
    private String maxWidth;
    private String maxHeight;
    private String photoReference;


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



    //endregion

    //region Constructors

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
        this.photoReference = photoReference;
        this.apiKey_ = key;
    }


    //endregion
}
