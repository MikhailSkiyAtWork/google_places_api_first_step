package com.example.admin.googleplaces.managers;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.googleplaces.handlers.MessageHandler;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.PlaceDetails;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPhotoRequest;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.example.admin.googleplaces.helpers.States;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import com.android.volley.Request.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class RequestManager {

    private  MessageHandler handler_ = new MessageHandler(this);
    private UIactions clientActivity_;

    private ExplicitPlaceDetails details_;
    private List<Photo> photoRefs = new ArrayList<Photo>();
    private static final String LOG_TAG = RequestManager.class.getSimpleName();

    public RequestManager(UIactions clienActivity){
       this.clientActivity_ = clienActivity;
    }

    /**
     * Parses json response which contains result of searching nearby places
     */
    public void parseSearchResponse(String response){
        List<NearbyPlaceDetails> nearbyPlaces = new ArrayList<>();
        List<NearbyPlaceDetails> placesWithPhoto = new ArrayList<>();
        try {
            nearbyPlaces = FetchPlaceSearchRequest.getNearbyPlaces(response);
            placesWithPhoto = FetchPlaceSearchRequest.getPlacesWithPhoto(nearbyPlaces);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }
        handler_.sendMessage(handler_.obtainMessage(States.SEARCH_RESPONSE_WAS_PARSE_OUT, placesWithPhoto));
    }

    /**
     * Parses json response which contains details about place asynchronously
     */
    public void parseDetailedResponse(String response){
        ExplicitPlaceDetails details = new ExplicitPlaceDetails();
        try {
            details = FetchPlaceDetailsRequest.parsePlaceDetailsResponse(response);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }
        handler_.sendMessage(handler_.obtainMessage(States.EXPLICIT_DETAILS_WAS_PARSE_OUT, details));
    }

    public  void sendDetailedRequest(final RequestParams requestParams){
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                FetchPlaceDetailsRequest searchRequest = new FetchPlaceDetailsRequest(requestParams);
                String response = null;
                String url = searchRequest.getUrl();
                try {
                     response = searchRequest.sendRequest(url);
                } catch (IOException e){
                    Log.e(LOG_TAG,"Error",e);
                }
                handler_.sendMessage(handler_.obtainMessage(States.EXPLICIT_DETAILS_FETCHED,response));
            }
        });
        background.start();
    }

    public void VsendDetailedRequest(RequestParams requestParams){
        String tag_search_request = "detailed_request";
        FetchPlaceDetailsRequest detailsRequest = new FetchPlaceDetailsRequest(requestParams);
        String url = detailsRequest.getUrl();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String responseString = response.toString();
                        handler_.sendMessage(handler_.obtainMessage(States.EXPLICIT_DETAILS_FETCHED, responseString));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "Error", error);
                    }
                });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_search_request);

    }

//    public void sendSearchRequest(final RequestParams requestParams) {
//        Thread background = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FetchPlaceSearchRequest searchRequest = new FetchPlaceSearchRequest(requestParams);
//                String response = null;
//                String url = searchRequest.getUrl();
//                try {
//                    response = searchRequest.sendRequest(url);
//                } catch (IOException e) {
//                    Log.e(LOG_TAG, "Error", e);
//                }
//                handler_.sendMessage(handler_.obtainMessage(States.NEARBY_PLACES_WAS_FOUND, response));
//            }
//        });
//        background.start();
//    }

    public void VsendSearchRequest(final RequestParams requestParams) {
        String tag_search_request = "search_request";

        FetchPlaceSearchRequest searchRequest = new FetchPlaceSearchRequest(requestParams);
        String url = searchRequest.getUrl();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String responseString = response.toString();
                        handler_.sendMessage(handler_.obtainMessage(States.NEARBY_PLACES_WAS_FOUND, responseString));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "Error", error);
                    }
                });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_search_request);
    }


    /**
     * Sends photo request and gets list of photos
     */
    public void sendPhotoRequest(PlaceDetails place) {
        for (int i = 0; i < place.getPhotos().size(); i++) {
            final RequestParams requestParams = new RequestParams(60, 60, place.getPhotos().get(i).getPhotoReference(), "AIzaSyAHi0UQFl62k5kkFgrxWoS2xlnFd8p8_So");

            Thread back = new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Bitmap> photos = new ArrayList<>();
                    FetchPhotoRequest photoRequest = new FetchPhotoRequest(requestParams);
                    String url = photoRequest.getUrl();
                    Bitmap photo = null;
                    try {
                         photo = photoRequest.sendRequest(url);
                    }catch (IOException e){
                        Log.e(LOG_TAG,"Error",e);
                    }
                    if (photo != null) {
                        photos.add(photo);
                        handler_.sendMessage(handler_.obtainMessage(States.PHOTO_DOWNLOADED, photos));
                    }
                }
            });
            back.start();
        }
    }

    public void VsendPhotoRequest(PlaceDetails place){
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        for (int i = 0; i < place.getPhotos().size(); i++) {
            final RequestParams requestParams = new RequestParams(60, 60, place.getPhotos().get(i).getPhotoReference(), "AIzaSyAHi0UQFl62k5kkFgrxWoS2xlnFd8p8_So");

            final FetchPhotoRequest photoRequest = new FetchPhotoRequest(requestParams);
            String url = photoRequest.getUrl();
            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null){
                        ArrayList<Bitmap> photos = new ArrayList<>();
                        photos.add(response.getBitmap());
                        handler_.sendMessage(handler_.obtainMessage(States.PHOTO_DOWNLOADED, photos));
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(LOG_TAG, "Error", error);
                }
            });

        }
    }

    public void updatePreview(PreviewData previewData){
        clientActivity_.showPreview(previewData);
    }

}
