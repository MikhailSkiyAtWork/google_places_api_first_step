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
public class FetchPlaceDetailsRequest extends Request {

    //region Keys for Place Details building query
    private static final String BASE_PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String PLACE_ID_KEY = "placeid";
    //endregion

    private String response_;
    private ExplicitPlaceDetails details_;
    private List<Photo> photoRefs = new ArrayList<Photo>();


    public FetchPlaceDetailsRequest(RequestParams params){
        URL url = getUrl(params);
        response_ = sendRequest(url);
        try {
            details_ = parsePlaceDetailsResponse(response_);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        photoRefs = details_.getPhotos();
    }

    public String getResponse() {
        return this.response_;
    }

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();

    /**
     * Creates query for place details request by placeId and Google Places API KEY
     *
     * @param placeId a textual identifier that uniquely identifies a place, returned from a Place Search
     * @param key     is a Google Places Api Key, in general it is storing at mainifest.xml file
     * @return URL of Place Details request
     */

    public URL getUrl(RequestParams requestParams) {
        URL url = null;
        try {
            Uri placeDetailsUri = Uri.parse(BASE_PLACE_DETAILS_URL).buildUpon()
                    .appendQueryParameter(PLACE_ID_KEY, requestParams.getPlaceId())
                    .appendQueryParameter(GOOGLE_PLACES_API_KEY, requestParams.getApiKey())
                    .build();

            url = new URL(placeDetailsUri.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        }
        return url;
    }

    /**
     * Sends Place Details request by placeDetailsUrl
     *
     * @param placeDetailsUrl special Url that contained the placeId and Google Places API
     * @return string that contains json response from server
     */
    public  String sendRequest(URL placeDetailsUrl) {
        // TODO sendRequest is a similar like sendSearchRequest
        // TODO So is good idea to create such method as a separate method???
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = null;

        try {
            urlConnection = (HttpURLConnection) placeDetailsUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonResult = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return jsonResult;
    }

    public static ExplicitPlaceDetails parsePlaceDetailsResponse(String jsonResponse) throws JSONException {
        ExplicitPlaceDetails explicitPlaceDetails = JsonHelper.getPlaceDetailsFromJson(jsonResponse);
        return explicitPlaceDetails;
    }





}





