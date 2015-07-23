package com.example.admin.googleplaces.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Display;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.googleplaces.handlers.MessageHandler;
import com.example.admin.googleplaces.helpers.Utily;
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

import com.android.volley.Request.Method;
import com.example.admin.googleplaces.requests.GeneralRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class RequestManager {

    private MessageHandler handler_ = new MessageHandler(this);
    private UIactions clientActivity_;

    private ExplicitPlaceDetails details_;
    private List<Photo> photoRefs = new ArrayList<Photo>();
    private static final String LOG_TAG = RequestManager.class.getSimpleName();

    public RequestManager(UIactions clienActivity) {
        this.clientActivity_ = clienActivity;
    }

    /**
     * Parses json response which contains result of searching nearby places
     */
    public void parseSearchResponse(String response) {
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
    public void parseDetailedResponse(String response) {
        ExplicitPlaceDetails details = new ExplicitPlaceDetails();
        try {
            details = FetchPlaceDetailsRequest.parsePlaceDetailsResponse(response);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }
        handler_.sendMessage(handler_.obtainMessage(States.EXPLICIT_DETAILS_WAS_PARSE_OUT, details));
    }


    /**
     * Sends request (Search or Details) depends on the instance of request was sent
     *
     * @param request type of request (can be either PlaceSearchRequest or PlaceDetailsRequst)
     */
    public void sendRequest(GeneralRequest request) {

        String tag = request.getTag();
        // Special url for request
        String url = request.getUrl();
        // Status for sending into MessageHandler
        final int status = request.getStatus();

        // Check if there is saved data in the cache
        // If so, there is no need to send same request, just take it from cache
        // Otherwise, send request
        String data = getDataFromCache(url);
        if (data != null) {
            handler_.sendMessage(handler_.obtainMessage(status, data));
        } else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET, url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String responseString = response.toString();
                            handler_.sendMessage(handler_.obtainMessage(status, responseString));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(LOG_TAG, "Error", error);
                        }
                    });

            AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag);
        }
    }

    private Cache.Entry retrieveDataFromCache(String url) {
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        return entry;
    }

    private String getDataFromCache(String url) {
        Cache.Entry retrievedData = retrieveDataFromCache(url);
        String data = null;
        if (retrievedData != null) {
            try {
                data = new String(retrievedData.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private Bitmap getPhotoFromCache(String url) {
        Cache.Entry retrievedData = retrieveDataFromCache(url);
        Bitmap photoFromCache = null;
        if (retrievedData != null) {
            photoFromCache = BitmapFactory.decodeByteArray(retrievedData.data, 0, retrievedData.data.length);
        }
        return photoFromCache;
    }

    /**
     * Sends photo request and gets list of photos
     */
    public void VsendPhotoRequest(Photo photo) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        int width = Utily.getApropriateWidth(this.clientActivity_.getContextForClient(), photo.getWidth());
        int height = Utily.getApropriateHeight(this.clientActivity_.getContextForClient(), photo.getHeight());
        final RequestParams requestParams = new RequestParams(width, height, photo.getPhotoReference(), Utily.getApiKey(this.clientActivity_.getContextForClient()));

        final FetchPhotoRequest photoRequest = new FetchPhotoRequest(requestParams);
        String url = photoRequest.getUrl();

        final ArrayList<Bitmap> photos = new ArrayList<>();

        Bitmap photoFromCache = getPhotoFromCache(url);
        if (photoFromCache != null) {
            photos.add(photoFromCache);
            handler_.sendMessage(handler_.obtainMessage(States.PHOTO_DOWNLOADED, photos));
        } else {

            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
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

    public void updatePreview(PreviewData previewData) {
        clientActivity_.showPreview(previewData);
    }

}
