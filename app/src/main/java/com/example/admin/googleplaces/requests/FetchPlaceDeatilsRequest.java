package com.example.admin.googleplaces.requests;

import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.util.Log;

import com.example.admin.googleplaces.data.Photo;
import com.example.admin.googleplaces.data.PlaceDetails;
import com.google.android.gms.maps.model.LatLng;

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

    //region Constants for working with JSON
    // There are the names of the JSON objects that need to be extracted
    final static String STATUS = "status";

    final static String RESULTS_ARRAY = "results";
    final static String ICON = "icon";
    final static String ID = "id";
    final static String NAME = "name";

    // Items for extracting photo attrs
    final static String PHOTOS_ARRAY = "photos";
    final static String HEIGHT = "height";
    final static String WIDTH = "width";
    final static String HTML_ATTRIBUTIONS = "html_attributions";
    final static String PHOTO_REFERENCE = "photo_reference";

    final static String TYPES = "types";
    final static String PLACE_ID = "place_id";
    final static String RATING = "rating";
    //endregion

    private static final String LOG_TAG = FetchPlaceDeatilsRequest.class.getSimpleName();

    /**
     * Creates query for search request by LatLng and API KEY
     *
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
                    .appendQueryParameter(SENSOR, Boolean.toString(SENSOR_VALUE))
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
     *
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


    /**
     * Parse out JSON-response, returned from server
     *
     * @param jsonResponse the string returned from server
     */
    public static List<PlaceDetails> parseResponse(String jsonResponse)
            throws JSONException {

        JSONObject placeDetailsJson = new JSONObject(jsonResponse);
        JSONArray results = placeDetailsJson.getJSONArray(RESULTS_ARRAY);

        // Get the number of all places
        int placesCount = results.length();

        List<PlaceDetails> placeDetailsList = new ArrayList<PlaceDetails>();

        for (int i = 0; i < placesCount; i++) {
            JSONObject placeDetailsJsonObject = results.getJSONObject(i);
            PlaceDetails placeDetails = getPlaceDetails(placeDetailsJsonObject);
            placeDetailsList.add(placeDetails);
        }
        return placeDetailsList;
    }

    /**
     * Extracts the placeDetails object from JSONObject
     *
     * @param placeDetailsJSONObject JSONObject which contains info about place details
     * @return PlaceDetails object
     * @throws JSONException
     */
    public static PlaceDetails getPlaceDetails(JSONObject placeDetailsJSONObject) throws JSONException {
        // Retrieve the fields from JSONObject
        String icon = placeDetailsJSONObject.getString(ICON);
        URL iconUrl;

        // Convert icon link to the URL object
        try {
            iconUrl = new URL(icon.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        }

        String id = placeDetailsJSONObject.getString(ID);
        String name = placeDetailsJSONObject.getString(NAME);
        double rating = placeDetailsJSONObject.getDouble(RATING);
        String placeId = placeDetailsJSONObject.getString(PLACE_ID);
        // List for keeping types of place
        List<String> types = new ArrayList<String>();

        // Get set of types
        JSONArray typesArray = placeDetailsJSONObject.getJSONArray(TYPES);
        types = getTypes(typesArray);


        // List which contains all extracted photos if there are exists
        List<Photo> extractedPhotoList = new ArrayList<Photo>();

        // Get photos
        JSONArray photosArray = placeDetailsJSONObject.getJSONArray(PHOTOS_ARRAY);
        for (int k = 0; k < photosArray.length(); k++) {
            JSONObject photo = photosArray.getJSONObject(k);
            Photo extractedPhoto = getPlacePhoto(photo);
            extractedPhotoList.add(extractedPhoto);
        }
        PlaceDetails placeDetails = new PlaceDetails(id, placeId, name, iconUrl, extractedPhotoList, types, rating);

        return placeDetails;
    }

    public static List<String> getTypes(JSONArray typesArray) throws JSONException {
        // List for keeping types of place
        List<String> types = new ArrayList<String>();
        for (int j = 0; j < typesArray.length(); j++) {
            String typeStr = typesArray.getString(j);
            types.add(typeStr);
        }
        return types;
    }

    /**
     * Extracts Photo details from JSONObject
     *
     * @param photoJsonObject JSONObject
     * @return Object Photo
     * @throws JSONException
     */
    public static Photo getPlacePhoto(JSONObject photoJsonObject) throws JSONException {
        int height = photoJsonObject.getInt(HEIGHT);
        int width = photoJsonObject.getInt(WIDTH);
        String photoReference = photoJsonObject.getString(PHOTO_REFERENCE);
        List<String> htmlAttrs = new ArrayList<String>();

        JSONArray htmlAttrsArray = photoJsonObject.getJSONArray(HTML_ATTRIBUTIONS);
        for (int m = 0; m < htmlAttrsArray.length(); m++) {
            String attr = htmlAttrsArray.getString(m);
            htmlAttrs.add(attr);
        }

        Photo extractedPhoto = new Photo(height, width, photoReference, htmlAttrs);
        return extractedPhoto;
    }
}
