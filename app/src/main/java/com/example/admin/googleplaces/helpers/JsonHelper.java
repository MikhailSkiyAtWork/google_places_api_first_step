package com.example.admin.googleplaces.helpers;

/**
 * Created by Mikhail Valuyskiy on 09.07.2015.
 */

import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Helps with parsing JSON
 */
public class JsonHelper {

    //region Constants for parsing JSON

    // There are the names of the JSON objects that need to be extracted
    //TODO do as static final not final static
    final static String STATUS = "status";

    static final String RESULT_KEY = "result";
    static final String PHOTOS_KEY = "photos";

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

    /**
     * Returns ExplicitPlaceDetails Object from JSONObject
     * @param placeDetailsJsonObject JSONObject that represents details about current place
     */
    public static ExplicitPlaceDetails getPlaceDetailsFromJson(String placeDetailsJsonObject) throws JSONException {
        JSONObject placeDetailsJson = new JSONObject(placeDetailsJsonObject);

        // Get the  JSONObject which represents the Explicit Place Details
        JSONObject result = placeDetailsJson.getJSONObject(RESULT_KEY);
        // Get the JSONArray which contains the array of photos
        JSONArray photosArray = result.getJSONArray(PHOTOS_KEY);

        String name = result.getString(NAME_KEY);
        List<Photo> extractedPhotos = getPhotosFromJson(photosArray);

        //Create Place Details Object
        ExplicitPlaceDetails explicitPlaceDetails = new ExplicitPlaceDetails(name, extractedPhotos);
        return explicitPlaceDetails;
    }

    /**
     * Returns all photos which is contained in JSONArray
     * @param photosArray JSONArray is an array of JSONObjects
     */
    public static List<Photo> getPhotosFromJson(JSONArray photosArray) throws JSONException {
        List<Photo> photos = new ArrayList<Photo>();
        for (int i = 0; i < photosArray.length(); i++) {

            Photo photo = getPlacePhoto(photosArray.getJSONObject(i));
            photos.add(photo);
        }
        return photos;
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

    /**
     * Returns All nearby places and some details about such places
     * @param jsonResponse the string returned from server
     */
    public static List<NearbyPlaceDetails> getAllNearbyPlaces(String jsonResponse)
            throws JSONException {

        JSONObject nearbyPlaceDetailsJson = new JSONObject(jsonResponse);
        JSONArray results = nearbyPlaceDetailsJson.getJSONArray(RESULTS_ARRAY_KEY);

        // Get the number of all places
        int placesCount = results.length();

        List<NearbyPlaceDetails> nearbyPlaceDetailsList = new ArrayList<NearbyPlaceDetails>();

        for (int i = 0; i < placesCount; i++) {
            JSONObject placeDetailsJsonObject = results.getJSONObject(i);
            NearbyPlaceDetails nearbyPlaceDetails = getNearbyPlaceDetails(placeDetailsJsonObject);
            nearbyPlaceDetailsList.add(nearbyPlaceDetails);
        }
        return nearbyPlaceDetailsList;
    }

    /**
     * Extracts the NearbyPlaceDetails object from JSONObject
     * @param nearbyPlaceDetailsJson JSONObject which contains info about nearby place details
     */
    public static NearbyPlaceDetails getNearbyPlaceDetails(JSONObject nearbyPlaceDetailsJson) throws JSONException {
        // Retrieve the fields from JSONObject
        String iconLink = nearbyPlaceDetailsJson.getString(ICON_KEY);
        URL iconUrl = Utily.convertIconLinkToUrl(iconLink);

        String id = nearbyPlaceDetailsJson.getString(ID_KEY);
        String name = nearbyPlaceDetailsJson.getString(NAME_KEY);

        // Checks that this value exist
        double rating = 0;
        if (nearbyPlaceDetailsJson.has(RATING_KEY)) {
            rating = nearbyPlaceDetailsJson.getDouble(RATING_KEY);
        }

        String placeId = nearbyPlaceDetailsJson.getString(PLACE_ID_KEY);
        // List for keeping types of place
        List<String> types = new ArrayList<String>();

        // Get set of types
        JSONArray typesArray = nearbyPlaceDetailsJson.getJSONArray(TYPES_KEY);
        types = getTypes(typesArray);

        // List which contains all extracted photos if there are exists
        List<Photo> extractedPhotoList = new ArrayList<Photo>();

        // Get photos
        if (nearbyPlaceDetailsJson.has(PHOTOS_ARRAY_KEY)) {
            JSONArray photosArray = nearbyPlaceDetailsJson.getJSONArray(PHOTOS_ARRAY_KEY);
            for (int k = 0; k < photosArray.length(); k++) {
                JSONObject photo = photosArray.getJSONObject(k);
                Photo extractedPhoto = getPlacePhoto(photo);
                extractedPhotoList.add(extractedPhoto);
            }
        }

        NearbyPlaceDetails nearbyPlaceDetails = new NearbyPlaceDetails(id, placeId, name, iconUrl, extractedPhotoList, types, rating);

        return nearbyPlaceDetails;
    }

    /**
     * Returns all types of place such as "museum" etc.
     */
    public static List<String> getTypes(JSONArray typesArray) throws JSONException {
        // List for keeping types of place
        List<String> types = new ArrayList<String>();
        for (int j = 0; j < typesArray.length(); j++) {
            String typeStr = typesArray.getString(j);
            types.add(typeStr);
        }
        return types;
    }
}
