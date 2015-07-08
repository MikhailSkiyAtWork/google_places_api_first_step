package com.example.admin.googleplaces.requests;

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
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
public class FetchPlaceSearchRequest {

    //region Keys for building query
    private static final String BASE_PLACE_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String LOCATION_KEY = "location";
    private static final String RADIUS_KEY = "radius";
    private static final String SENSOR_KEY = "sensor";
    private static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    //region Values for building query
    private static int RADIUS_VALUE = 50;
    private static boolean SENSOR_VALUE = false;
    //endregion

    //region Constants for working with JSON
    // There are the names of the JSON objects that need to be extracted
    final static String STATUS = "status";

    final static String RESULTS_ARRAY_KEY = "results";
    final static String ICON_KEY = "icon";
    final static String ID_KEY = "id";
    final static String NAME_KEY = "name";

    // Items for extracting photo attrs
    final static String PHOTOS_ARRAY_KEY = "photos";
    final static String HEIGHT_KEY = "height";
    final static String WIDTH_KEY = "width";
    final static String HTML_ATTRIBUTIONS_KEY = "html_attributions";
    final static String PHOTO_REFERENCE_KEY = "photo_reference";

    final static String TYPES_KEY = "types";
    final static String PLACE_ID_KEY = "place_id";
    final static String RATING_KEY = "rating";
    //endregion

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();

    /**
     * Creates query for search request by LatLng and Google Places API KEY
     *
     * @param point is LatLng objects, that represents latitude and longitude of chosen place
     * @param key   is a Google Places Api Key, in general it is storing at mainifest.xml file
     * @return the URL of search request
     */
    public static URL getQuery(String point, String key) {
        URL url = null;
        try {
            Uri builtUri = Uri.parse(BASE_PLACE_SEARCH_URL).buildUpon()
                    .appendQueryParameter(LOCATION_KEY, point)
                    .appendQueryParameter(RADIUS_KEY, Integer.toString(RADIUS_VALUE))
                    .appendQueryParameter(SENSOR_KEY, Boolean.toString(SENSOR_VALUE))
                    .appendQueryParameter(GOOGLE_PLACES_API_KEY, key)
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
    public static List<PlaceDetails> parseSearchPlacesResponse(String jsonResponse)
            throws JSONException {

        JSONObject placeDetailsJson = new JSONObject(jsonResponse);
        JSONArray results = placeDetailsJson.getJSONArray(RESULTS_ARRAY_KEY);

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
        String iconLink = placeDetailsJSONObject.getString(ICON_KEY);
        URL iconUrl = convertIconLinkToUrl(iconLink);

        String id = placeDetailsJSONObject.getString(ID_KEY);
        String name = placeDetailsJSONObject.getString(NAME_KEY);

        // Checks that this value exist
        double rating = 0;
        if (placeDetailsJSONObject.has(RATING_KEY)) {
            rating = placeDetailsJSONObject.getDouble(RATING_KEY);
        }

        String placeId = placeDetailsJSONObject.getString(PLACE_ID_KEY);
        // List for keeping types of place
        List<String> types = new ArrayList<String>();

        // Get set of types
        JSONArray typesArray = placeDetailsJSONObject.getJSONArray(TYPES_KEY);
        types = getTypes(typesArray);

        // List which contains all extracted photos if there are exists
        List<Photo> extractedPhotoList = new ArrayList<Photo>();

        // Get photos
        if (placeDetailsJSONObject.has(PHOTOS_ARRAY_KEY)) {
            JSONArray photosArray = placeDetailsJSONObject.getJSONArray(PHOTOS_ARRAY_KEY);
            for (int k = 0; k < photosArray.length(); k++) {
                JSONObject photo = photosArray.getJSONObject(k);
                Photo extractedPhoto = getPlacePhoto(photo);
                extractedPhotoList.add(extractedPhoto);
            }
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
     */
    public static Photo getPlacePhoto(JSONObject photoJsonObject) throws JSONException {
        int height = photoJsonObject.getInt(HEIGHT_KEY);
        int width = photoJsonObject.getInt(WIDTH_KEY);
        String photoReference = photoJsonObject.getString(PHOTO_REFERENCE_KEY);
        List<String> htmlAttrs = new ArrayList<String>();

        JSONArray htmlAttrsArray = photoJsonObject.getJSONArray(HTML_ATTRIBUTIONS_KEY);
        for (int m = 0; m < htmlAttrsArray.length(); m++) {
            String attr = htmlAttrsArray.getString(m);
            htmlAttrs.add(attr);
        }

        Photo extractedPhoto = new Photo(height, width, photoReference, htmlAttrs);
        return extractedPhoto;
    }

    public static List<String> getPhotoRefsFromAllPlaces(List<PlaceDetails> places){
        // add check size of places
        if (places.size()!=0) {
            List<String> allPhotoRefs = new ArrayList<String>();
            List<String> photoRefsFromPlace = new ArrayList<String>();

            for (int i = 0; i < places.size(); i++) {
                photoRefsFromPlace = getPhotoRefsByPlace(places.get(i));
                if (photoRefsFromPlace != null) {
                    allPhotoRefs.addAll(photoRefsFromPlace);
                }
            }
            return allPhotoRefs;
        } else return null;
    }

    public static List<String> getPhotoRefsByPlace(PlaceDetails place) {
        List<String> photoRefs = new ArrayList<String>();
        List<Photo> photos = place.getPhotos();
        if (photos.size()!= 0) {
            for (int i = 0; i < photos.size(); i++) {
                photoRefs.add(photos.get(i).getPhotoReference());
            }
            return photoRefs;
        } else {
            return null;
        }
    }

    /**
     * Converts icon link to the URL object
     */
    public static URL convertIconLinkToUrl(String iconLink) {
        URL iconUrl;
        try {
            iconUrl = new URL(iconLink);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        }
        return iconUrl;
    }
}
