package com.example.admin.googleplaces.requests;

/**
 * Created by Mikhail Valuyskiy on 08.07.2015.
 */


import android.net.Uri;
import android.util.Log;

import com.example.admin.googleplaces.helpers.States;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;
import com.example.admin.googleplaces.helpers.JsonHelper;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.RequestParams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Contain fields and methods which are necessary for sending Place Details request
 */
public class FetchPlaceDetailsRequest extends GeneralRequest {

    private static final String DETAILS_REQUEST_TAG = "detailed_request";

    //region Keys for Place Details building query
    private static final String BASE_PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String PLACE_ID_KEY = "placeid";
    private static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    private List<Photo> photoRefs = new ArrayList<Photo>();
    private RequestParams requestParams_;

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();

    public FetchPlaceDetailsRequest(RequestParams params){
        this.requestParams_ = params;
    }

    public String getTag(){
        return this.DETAILS_REQUEST_TAG;
    }

    /**
     * Creates query for place details request by placeId and Google Places API KEY
     */
    public String getUrl() {
        String url = null;
        Uri placeDetailsUri = Uri.parse(BASE_PLACE_DETAILS_URL).buildUpon()
                .appendQueryParameter(PLACE_ID_KEY, requestParams_.getPlaceId())
                .appendQueryParameter(GOOGLE_PLACES_API_KEY, requestParams_.getApiKey())
                .build();

        url = placeDetailsUri.toString();
        return url;
    }

    public static ExplicitPlaceDetails parsePlaceDetailsResponse(String jsonResponse) throws JSONException {
        ExplicitPlaceDetails explicitPlaceDetails = JsonHelper.getPlaceDetailsFromJson(jsonResponse);
        return explicitPlaceDetails;
    }

    public int getStatus(){
        return States.EXPLICIT_DETAILS_FETCHED;
    }
}





