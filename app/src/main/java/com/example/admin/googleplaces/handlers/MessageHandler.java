package com.example.admin.googleplaces.handlers;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.helpers.States;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class MessageHandler extends Handler {

    private static final int FIRST_ITEM = 0;

    private UIactions activity_;
    private RequestManager manager;
    private PreviewData previewData_ = new PreviewData();
    private List<NearbyPlaceDetails> fetchedPlaces_;
    private List<PreviewData> manyPreviews_ = new ArrayList<>();

    public MessageHandler(RequestManager manager) {
        this.manager = manager;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case States.SEND_SEARCH_REQUEST:
                break;
            case States.NEARBY_PLACES_WAS_FOUND:
                Log.v("Status:", "NEARBY_PLACES_WAS_FOUND");
                if (msg.obj != null) {
                    String response = (String) msg.obj;
                    manager.parseSearchResponse(response);
                }
                break;

            // Search response was parse out so send one photo request for preview
            // If there is no places with photo notify user about it
            case States.SEARCH_RESPONSE_WAS_PARSE_OUT:
                Log.v("Status:", "SEARCH_RESPONSE_WAS_PARSE_OUT");
                if (msg.obj != null) {
                    fetchedPlaces_ = (List<NearbyPlaceDetails>) msg.obj;
                    if (fetchedPlaces_.size() != 0) {
                        Log.v("SIZE", Integer.toString(fetchedPlaces_.size()));
                        manager.getCurrentPlacePreview(fetchedPlaces_, FIRST_ITEM);
                    } else {
                        manager.showWarning();
                    }
                }
                break;

            case States.PHOTO_DOWNLOADED:
                Log.v("Status:", "PHOTO_DOWNLOADED");

                if (msg.obj != null) {
                    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                    images = (ArrayList<Bitmap>) msg.obj;
                    previewData_.setImages(images);
                    manager.updatePreview(previewData_);
                }
                break;

            case States.PHOTO_PREVIEW_DOWNLOADED:
                Log.v("Status:", "PHOTO_PREVIEW_DOWNLOADED");

                if (msg.obj != null) {
                    PreviewData downloadedPreview = (PreviewData) msg.obj;

                    Log.v("Name", downloadedPreview.getName());
                    Log.v("PlaceId", downloadedPreview.getPlaceId());
                    Log.v("Latitude", Double.toString(downloadedPreview.getLatitude()));
                    Log.v("Longitude", Double.toString(downloadedPreview.getLongitude()));

                    // show preview
                    manager.updatePreview(downloadedPreview);

                    if (downloadedPreview.getIndex() < fetchedPlaces_.size() - 1) {
                        Log.v("Count of places", Integer.toString(fetchedPlaces_.size()));
                        Log.v("Index", Integer.toString(downloadedPreview.getIndex()));
                        manager.getCurrentPlacePreview(fetchedPlaces_, downloadedPreview.getIndex() + 1);
                    }
                }
                break;

            case States.EXPLICIT_DETAILS_FETCHED:
                if (msg.obj != null) {
                    String placeDetailsResponse = (String) msg.obj;
                    manager.parseDetailedResponse(placeDetailsResponse);
                }
                break;

            case States.EXPLICIT_DETAILS_WAS_PARSE_OUT:
                if (msg.obj != null) {
                    ExplicitPlaceDetails details = (ExplicitPlaceDetails) msg.obj;
                    for (int i = 0; i < details.getPhotos().size(); i++) {
                        manager.postPhotoRequest(details.getPhotos().get(i));
                    }
                }
                break;

            default:
                break;
        }
    }
}
