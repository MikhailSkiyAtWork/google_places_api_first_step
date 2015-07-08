package com.example.admin.googleplaces.parserTest;

import android.test.AndroidTestCase;

import com.example.admin.googleplaces.data.Photo;
import com.example.admin.googleplaces.data.PlaceDetails;
import com.example.admin.googleplaces.requests.FetchPlaceDeatilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 07.07.2015.
 */
public class ParserTests extends AndroidTestCase {

    private URL iconUrl_;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        iconUrl_ = FetchPlaceDeatilsRequest.getIconUrl(Constants.ICON_LINK);
        testJsonParser(Constants.JSON_RESPONSE);
    }

    public void testJsonParser(String JSON_RESPONSE) throws JSONException {

        JSONObject response = new JSONObject(JSON_RESPONSE);
        JSONArray results = response.getJSONArray(Constants.RESULTS_ARRAY_KEY);

        // Current number of objects 1
        int placesCount = results.length();

        // Check the count of places
        assertFalse(placesCount != 1);

        JSONObject placeDetailsJsonObject = results.getJSONObject(0);

        JSONArray photos = placeDetailsJsonObject.getJSONArray(Constants.PHOTOS_ARRAY_KEY);
        // Test getPhotoMethod
        testGetPlacePhotoMethod(photos.getJSONObject(0));

        JSONArray types = placeDetailsJsonObject.getJSONArray(Constants.TYPES_KEY);
        testGetTypesMethod(types);

        testGetPlaceDetails(placeDetailsJsonObject);
    }

    /**
     * Test extraction of PlaceDetails object
     *
     * @param placeDetailsJsonObject JSONObject which represents the PlaceDetails object
     * @throws JSONException
     */
    public void testGetPlaceDetails(JSONObject placeDetailsJsonObject) throws JSONException {
        // Get the photos for testing
        JSONArray photos = placeDetailsJsonObject.getJSONArray(Constants.PHOTOS_ARRAY_KEY);
        Photo extractedPhoto = FetchPlaceDeatilsRequest.getPlacePhoto(photos.getJSONObject(0));

        // Get the types for testing
        JSONArray types = placeDetailsJsonObject.getJSONArray(Constants.TYPES_KEY);
        List<String> typeList = FetchPlaceDeatilsRequest.getTypes(types);

        // Try get placeDetails object from JSON response
        PlaceDetails placeDetails = FetchPlaceDeatilsRequest.getPlaceDetails(placeDetailsJsonObject);

        assertEquals(Constants.ID, placeDetails.getId());
        assertEquals(Constants.PLACE_ID, placeDetails.getPlaceId());
        assertEquals(Constants.NAME, placeDetails.getName());
        assertEquals(iconUrl_, placeDetails.getIconUrl());

        // Check extracted Photo object
        comparePhotosObjects(extractedPhoto, placeDetails.getPhotos().get(0));

        // Check all 3 types
        assertEquals(typeList.get(0), placeDetails.getTypes().get(0));
        assertEquals(typeList.get(1), placeDetails.getTypes().get(1));
        assertEquals(typeList.get(2), placeDetails.getTypes().get(2));

        // Check rating
        assertEquals(Constants.RATING, placeDetails.getRating());
    }

    /**
     * Tests 2 Photos expected and actual
     */
    public void comparePhotosObjects(Photo expected, Photo actual) {
        assertEquals(expected.getHeight(), actual.getHeight());
        assertEquals(expected.getWidth(), expected.getWidth());
        assertEquals(expected.getPhotoReference(), expected.getPhotoReference());
        assertEquals(expected.getHtmlAttrs().get(0), actual.getHtmlAttrs().get(0));
    }

    /**
     * Tests extraction of Photo object of current Place
     *
     * @param photoJsonObject JsonObject which represent photo
     * @throws JSONException
     */
    public void testGetPlacePhotoMethod(JSONObject photoJsonObject) throws JSONException {
        Photo extractedPhoto = FetchPlaceDeatilsRequest.getPlacePhoto(photoJsonObject);
        assertEquals(Constants.HEIGHT, extractedPhoto.getHeight());
        assertEquals(Constants.WIDTH, extractedPhoto.getWidth());
        assertEquals(Constants.PHOTO_REFS, extractedPhoto.getPhotoReference());
        assertEquals(Constants.HTML_ATTRS.get(0), extractedPhoto.getHtmlAttrs().get(0));
    }

    /**
     * Tests extraction of types of current place
     *
     * @param types JSONArray which represents the types of current place
     * @throws JSONException
     */
    public void testGetTypesMethod(JSONArray types) throws JSONException {
        List<String> typesList = FetchPlaceDeatilsRequest.getTypes(types);
        assertEquals(typesList.get(0), Constants.MUSEUM_TYPE);
        assertEquals(typesList.get(1), Constants.POINT_OF_INTEREST_TYPE);
        assertEquals(typesList.get(2), Constants.ESTABLISHMENT);
    }
}
