package com.example.admin.googleplaces.handlers;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class MessageHandler extends Handler {

    private static final int FIRST_ITEM = 0;
    private static final int SEND_SEARCH_REQUEST = 1;
    private static final int NEARBY_PLACES_WAS_FOUND = 2;
    private static final int PHOTO_DOWNLOADED = 3;
    private static final int SEND_DETAILS_REQUEST = 4;
    private static final int EXPLICIT_DETAILS_FETCHED = 5;
    private UIactions activity_;
    private RequestManager manager;
    private PreviewData previewData_ = new PreviewData();

    public MessageHandler(RequestManager manager) {
        this.manager = manager;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SEND_SEARCH_REQUEST:
                break;
            case NEARBY_PLACES_WAS_FOUND:
                String response = (String) msg.obj;
                List<NearbyPlaceDetails> nearbyPlaces = new ArrayList<>();
                List<NearbyPlaceDetails> placesWithPhoto = new ArrayList<>();
                try {
                    nearbyPlaces = FetchPlaceSearchRequest.getNearbyPlaces(response);
                    placesWithPhoto = FetchPlaceSearchRequest.getPlacesWithPhoto(nearbyPlaces);
                } catch (JSONException e) {
                    Log.e("ERROR", e.getMessage());
                }

                if (placesWithPhoto.size() != 0) {
                    previewData_.setName(placesWithPhoto.get(FIRST_ITEM).getName());
                    previewData_.setPlaceId(placesWithPhoto.get(0).getPlaceId());

                    manager.sendPhotoRequest(placesWithPhoto.get(FIRST_ITEM));
                }
                break;
            case PHOTO_DOWNLOADED:
                ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                images = (ArrayList<Bitmap>) msg.obj;
                previewData_.setImages(images);
                manager.updatePreview(previewData_);
                break;

            case EXPLICIT_DETAILS_FETCHED:
                String placeDetailsResponse = (String) msg.obj;
                ExplicitPlaceDetails details_ = new ExplicitPlaceDetails();
                try {
                    details_ = FetchPlaceDetailsRequest.parsePlaceDetailsResponse(placeDetailsResponse);
                } catch (JSONException e) {
                    Log.e("ERROR", e.getMessage());
                }
               // photoRefs = details_.getPhotos();

                manager.sendPhotoRequest(details_);

        }

    }
}
