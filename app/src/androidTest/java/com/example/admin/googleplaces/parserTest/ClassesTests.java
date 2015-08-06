package com.example.admin.googleplaces.parserTest;

import android.test.AndroidTestCase;

import com.example.admin.googleplaces.helpers.Utily;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 07.07.2015.
 */
public class ClassesTests extends AndroidTestCase {

    public List<Photo> photos = new ArrayList<Photo>();
    public List<String> types = new ArrayList<String>();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testCreatePhoto();
        testCreatePlaceDetails();
    }

    public void testCreatePhoto() {
        Photo testPhoto = new Photo(Constants.HEIGHT, Constants.WIDTH, Constants.PHOTO_REFS, Constants.HTML_ATTRS);
        assertEquals(Constants.HEIGHT, testPhoto.getHeight());
        assertEquals(Constants.WIDTH, testPhoto.getWidth());
        assertEquals(Constants.PHOTO_REFS, testPhoto.getPhotoReference());
        assertEquals(Constants.HTML_ATTRS.get(0), testPhoto.getHtmlAttrs().get(0));
    }

    public void testCreatePlaceDetails() {
        // Create Url from icon link
        URL iconUrl = Utily.convertIconLinkToUrl(Constants.ICON_LINK);
        Photo testPhoto = new Photo(Constants.HEIGHT, Constants.WIDTH, Constants.PHOTO_REFS, Constants.HTML_ATTRS);
        photos.add(testPhoto);

        types.add(Constants.MUSEUM_TYPE);
        types.add(Constants.POINT_OF_INTEREST_TYPE);
        types.add(Constants.ESTABLISHMENT);

        NearbyPlaceDetails nearbyPlaceDetails = new NearbyPlaceDetails(Constants.ID, Constants.PLACE_ID, Constants.NAME, iconUrl, photos, types, Constants.RATING,Constants.LATITUDE,Constants.LONGITUDE);

        assertEquals(Constants.ID, nearbyPlaceDetails.getId());
        assertEquals(Constants.PLACE_ID, nearbyPlaceDetails.getPlaceId());
        assertEquals(Constants.NAME, nearbyPlaceDetails.getName());
        assertEquals(iconUrl, nearbyPlaceDetails.getIconUrl());
        assertEquals(photos.get(0), nearbyPlaceDetails.getPhotos().get(0));
        // Check all 3 types
        assertEquals(types.get(0), nearbyPlaceDetails.getTypes().get(0));
        assertEquals(types.get(1), nearbyPlaceDetails.getTypes().get(1));
        assertEquals(types.get(2), nearbyPlaceDetails.getTypes().get(2));
        // Check rating
        assertEquals(Constants.RATING, nearbyPlaceDetails.getRating());
        // Check Lat and Long
        assertEquals(Constants.LATITUDE,nearbyPlaceDetails.getLatitude());
        assertEquals(Constants.LONGITUDE,nearbyPlaceDetails.getLongitude());
    }
}
