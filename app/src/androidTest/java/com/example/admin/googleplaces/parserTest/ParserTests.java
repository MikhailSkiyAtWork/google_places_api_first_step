package com.example.admin.googleplaces.parserTest;

import android.test.AndroidTestCase;

import com.example.admin.googleplaces.data.Photo;
import com.example.admin.googleplaces.data.NearbyPlaceDetails;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 07.07.2015.
 */
public class ParserTests extends AndroidTestCase {

    private URL iconUrl_;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        iconUrl_ = FetchPlaceSearchRequest.convertIconLinkToUrl(Constants.ICON_LINK);
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
     * Test extraction of NearbyPlaceDetails object
     *
     * @param placeDetailsJsonObject JSONObject which represents the NearbyPlaceDetails object
     * @throws JSONException
     */
    public void testGetPlaceDetails(JSONObject placeDetailsJsonObject) throws JSONException {
        // Get the photos for testing
        JSONArray photos = placeDetailsJsonObject.getJSONArray(Constants.PHOTOS_ARRAY_KEY);
        Photo extractedPhoto = FetchPlaceSearchRequest.getPlacePhoto(photos.getJSONObject(0));

        // Get the types for testing
        JSONArray types = placeDetailsJsonObject.getJSONArray(Constants.TYPES_KEY);
        List<String> typeList = FetchPlaceSearchRequest.getTypes(types);

        // Try get NearbyPlaceDetails object from JSON response
        NearbyPlaceDetails nearbyPlaceDetails = FetchPlaceSearchRequest.getPlaceDetails(placeDetailsJsonObject);

        assertEquals(Constants.ID, nearbyPlaceDetails.getId());
        assertEquals(Constants.PLACE_ID, nearbyPlaceDetails.getPlaceId());
        assertEquals(Constants.NAME, nearbyPlaceDetails.getName());
        assertEquals(iconUrl_, nearbyPlaceDetails.getIconUrl());

        // Check extracted Photo object
        comparePhotosObjects(extractedPhoto, nearbyPlaceDetails.getPhotos().get(0));

        // Check all 3 types
        assertEquals(typeList.get(0), nearbyPlaceDetails.getTypes().get(0));
        assertEquals(typeList.get(1), nearbyPlaceDetails.getTypes().get(1));
        assertEquals(typeList.get(2), nearbyPlaceDetails.getTypes().get(2));

        // Check rating
        assertEquals(Constants.RATING, nearbyPlaceDetails.getRating());
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
        Photo extractedPhoto = FetchPlaceSearchRequest.getPlacePhoto(photoJsonObject);
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
        List<String> typesList = FetchPlaceSearchRequest.getTypes(types);
        assertEquals(typesList.get(0), Constants.MUSEUM_TYPE);
        assertEquals(typesList.get(1), Constants.POINT_OF_INTEREST_TYPE);
        assertEquals(typesList.get(2), Constants.ESTABLISHMENT);
    }
}
