package com.example.admin.googleplaces.requests;

/**
 * Created by Mikhail Valuyskiy on 08.07.2015.
 */


import android.net.Uri;
import android.util.Log;

import com.example.admin.googleplaces.data.Photo;
import com.example.admin.googleplaces.data.PlaceDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class FetchPlaceDetailsRequest {

    //region Keys for Place Details building query
    private static final String BASE_PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String PLACE_ID_KEY = "placeid";
    private static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    //region Constants for parsing JSON
    static final String RESULT_KEY = "result";
    static final String NAME_KEY = "name";
    static final String PHOTOS_KEY = "photos";
    //endregion

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();

    /**
     * Creates query for place details request by placeId and Google Places API KEY
     *
     * @param placeId a textual identifier that uniquely identifies a place, returned from a Place Search
     * @param key     is a Google Places Api Key, in general it is storing at mainifest.xml file
     * @return URL of Place Details request
     */

    // TODO buildUrl instead of getQuery
    public static URL getQuery(String placeId, String key) {
        URL url = null;
        try {
            Uri placeDetailsUri = Uri.parse(BASE_PLACE_DETAILS_URL).buildUpon()
                    .appendQueryParameter(PLACE_ID_KEY, placeId)
                    .appendQueryParameter(GOOGLE_PLACES_API_KEY, key)
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
    public static String sendPlaceDetailsRequest(URL placeDetailsUrl) {
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

    public static List<PlaceDetails> parsePlaceDetailsResponse(String jsonResponse) throws JSONException {

        JSONObject placeDetails = new JSONObject(jsonResponse);
        JSONObject result = new JSONObject(RESULT_KEY);

        String name = result.getString(NAME_KEY);

        JSONArray photosArray = result.getJSONArray(PHOTOS_KEY);
        List<Photo> photos = new ArrayList<Photo>();

        for (int i =0;i<photosArray.length();i++) {

            Photo photo = FetchPlaceSearchRequest.getPlacePhoto(photosArray.getJSONObject(i));
            photos.add(photo);
        }


    }

}





