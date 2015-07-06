package com.example.admin.googleplaces.requests;

import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
 public class FetchPlaceDeatilsRequest {

    //region Constanst for building query (Keys)
    private static final String PLACE_DETAILS_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String LOCATION = "location";
    private static final String RADIUS = "radius";
    private static final String SENSOR = "sensor";
    private static final String KEY = "key";
    //endregion

    //region Constants for building query (Values)
    private static int RADIUS_VALUE = 500;
    private static boolean SENSOR_VALUE = false;
    //endregion

    private static final String LOG_TAG = FetchPlaceDeatilsRequest.class.getSimpleName();

    /**
     * Creates query for search request by LatLng and API KEY
     * @param point is LatLng objects, that represents latitude and longitude of chosen place
     * @param key   is a Google Places Api Key, in general it is storing at mainifest.xml file
     * @return the URL of search request
     */
    public static URL getQuery(String point, String key) {
        URL url = null;
        try {
            Uri builtUri = Uri.parse(PLACE_DETAILS_BASE_URL).buildUpon()
                    .appendQueryParameter(LOCATION, point)
                    .appendQueryParameter(RADIUS, Integer.toString(RADIUS_VALUE))
                    .appendQueryParameter(SENSOR,Boolean.toString(SENSOR_VALUE))
                    .appendQueryParameter(KEY, key)
                    .build();

            url = new URL(builtUri.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        }
        return url;
    }

    /**
     * Sends search request by special URL
     * @param url the search query
     * @return string that contains json response from server
     */
    public static String sendSearchRequest(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine())!= null){
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0){
                return null;
            }
            jsonResult = buffer.toString();

        } catch (IOException e){
            Log.e(LOG_TAG,"Error",e);
            return null;
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader!=null){
                try {
                    reader.close();
                } catch (final  IOException e){
                    Log.e(LOG_TAG,"Error closing stream",e);
                }
            }
        }
     return  jsonResult;
    }


    /**
     * Parse out JSON-response, returned from server
     * @param jsonResponse the string returned from server
     */
    public void parseResponse(String jsonResponse) {

    }


}
