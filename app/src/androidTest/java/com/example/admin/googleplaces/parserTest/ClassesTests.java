package com.example.admin.googleplaces.parserTest;

import android.test.AndroidTestCase;

import com.example.admin.googleplaces.data.Photo;
import com.example.admin.googleplaces.data.PlaceDetails;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;

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
        URL iconUrl = FetchPlaceSearchRequest.convertIconLinkToUrl(Constants.ICON_LINK);
        Photo testPhoto = new Photo(Constants.HEIGHT, Constants.WIDTH, Constants.PHOTO_REFS, Constants.HTML_ATTRS);
        photos.add(testPhoto);

        types.add(Constants.MUSEUM_TYPE);
        types.add(Constants.POINT_OF_INTEREST_TYPE);
        types.add(Constants.ESTABLISHMENT);

        PlaceDetails placeDetails = new PlaceDetails(Constants.ID, Constants.PLACE_ID, Constants.NAME, iconUrl, photos, types, Constants.RATING);

        assertEquals(Constants.ID, placeDetails.getId());
        assertEquals(Constants.PLACE_ID, placeDetails.getPlaceId());
        assertEquals(Constants.NAME, placeDetails.getName());
        assertEquals(iconUrl, placeDetails.getIconUrl());
        assertEquals(photos.get(0), placeDetails.getPhotos().get(0));
        // Check all 3 types
        assertEquals(types.get(0), placeDetails.getTypes().get(0));
        assertEquals(types.get(1), placeDetails.getTypes().get(1));
        assertEquals(types.get(2), placeDetails.getTypes().get(2));
        // Check rating
        assertEquals(Constants.RATING, placeDetails.getRating());
    }
}
