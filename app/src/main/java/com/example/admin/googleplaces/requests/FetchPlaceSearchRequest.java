package com.example.admin.googleplaces.requests;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

//import com.example.admin.googleplaces.listeners.AsyncTaskListener;
import com.example.admin.googleplaces.helpers.States;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.helpers.JsonHelper;
import com.example.admin.googleplaces.models.RequestParams;
import com.google.android.gms.maps.model.LatLng;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
public class FetchPlaceSearchRequest extends GeneralRequest {

    private static final String SEARCH_REQUEST_TAG = "search_request";

    //region Keys for building query
    private static final String BASE_PLACE_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String LOCATION_KEY = "location";
    private static final String RADIUS_KEY = "radius";
    private static final String SENSOR_KEY = "sensor";
    protected static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    //region Values for building query
    private static int RADIUS_VALUE = 50;
    private static boolean SENSOR_VALUE = false;
    //endregion

    private List<NearbyPlaceDetails> places_ = new ArrayList<>();
    private List<String> photoRefs_ = new ArrayList<>();
    private RequestParams requestParams_;

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();

    public FetchPlaceSearchRequest(RequestParams requestParams) {
        this.requestParams_ = requestParams;
    }

    public int getStatus() {
        return States.NEARBY_PLACES_WAS_FOUND;
    }

    public String getTag() {
        return this.SEARCH_REQUEST_TAG;
    }

    /**
     * Creates query for search request by LatLng and Google Places API KEY
     */
    // TODO Check requestParams.getPoint() and others not null or empty
    public String getUrl() {
        String url = null;
        Uri builtUri = Uri.parse(BASE_PLACE_SEARCH_URL).buildUpon()
                .appendQueryParameter(LOCATION_KEY, this.requestParams_.getPoint())
                .appendQueryParameter(RADIUS_KEY, this.requestParams_.getRadius())
                .appendQueryParameter(GOOGLE_PLACES_API_KEY, this.requestParams_.getApiKey())
                .build();

        url = builtUri.toString();
        return url;
    }

    /**
     * Returns All nearby places and some details about such places
     */
    public static List<NearbyPlaceDetails> getNearbyPlaces(String jsonResponse) throws JSONException {
        List<NearbyPlaceDetails> nearbyPlaceDetailsList = JsonHelper.getAllNearbyPlaces(jsonResponse);
        return nearbyPlaceDetailsList;
    }

    /**
     * Returns all nearby places which have photos
     */
    public static   List<NearbyPlaceDetails> getPlacesWithPhoto(List<NearbyPlaceDetails> places) {
        if (places.size() != 0) {
            List<NearbyPlaceDetails> placesWithPhoto = new ArrayList<NearbyPlaceDetails>();
            List<String> photoRefsFromPlace = new ArrayList<String>();
            for (int i = 0; i < places.size(); i++) {
                photoRefsFromPlace = getPhotoRefsByPlace(places.get(i));
                if (photoRefsFromPlace.size() > 0) {
                    placesWithPhoto.add(places.get(i));
                }
            }
            return placesWithPhoto;
        } else {
            return null;
        }
    }

    public static List<String> getPhotoRefsFromAllPlaces(List<NearbyPlaceDetails> places) {
        // add check size of places
        if (places.size() != 0) {
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

    public static List<String> getPhotoRefsByPlace(NearbyPlaceDetails place) {
        List<String> photoRefs = new ArrayList<String>();
        List<Photo> photos = place.getPhotos();
        if (photos.size() != 0) {
            for (int i = 0; i < photos.size(); i++) {
                photoRefs.add(photos.get(i).getPhotoReference());
            }
            return photoRefs;
        }
        return photoRefs;
    }


}
