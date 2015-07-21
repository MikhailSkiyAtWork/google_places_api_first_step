package com.example.admin.googleplaces.requests;

/**
 * Created by Mikhail Valuyskiy on 08.07.2015.
 */


import android.net.Uri;
import android.util.Log;

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
public class FetchPlaceDetailsRequest  {

    private static final int SUCCESS_STATUS = 200;

    //region Keys for Place Details building query
    private static final String BASE_PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String PLACE_ID_KEY = "placeid";
    protected static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    private String response_;
    private ExplicitPlaceDetails details_;
    private List<Photo> photoRefs = new ArrayList<Photo>();
    private RequestParams requestParams_;
    private OkHttpClient client_;

    public FetchPlaceDetailsRequest(RequestParams params){
        this.requestParams_ = params;
        this.client_ = new OkHttpClient();
    }

    public String getResponse() {
        return this.response_;
    }

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();

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

    /**
     * Sends Place Details request by placeDetailsUrl
     *
     * @param url special Url that contained the placeId and Google Places API
     * @return string that contains json response from server
     */
    public String sendRequest(String url) throws IOException {
        String response =  run(url);
        return response;
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        String responseString = null;
        Response response = client_.newCall(request).execute();
        if (response.code() == SUCCESS_STATUS){
            responseString = response.body().string();
        }
        return responseString;
    }

    public static ExplicitPlaceDetails parsePlaceDetailsResponse(String jsonResponse) throws JSONException {
        ExplicitPlaceDetails explicitPlaceDetails = JsonHelper.getPlaceDetailsFromJson(jsonResponse);
        return explicitPlaceDetails;
    }





}





