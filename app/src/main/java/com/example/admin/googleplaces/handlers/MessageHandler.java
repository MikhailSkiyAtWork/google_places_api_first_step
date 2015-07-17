package com.example.admin.googleplaces.handlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.activities.MainActivity;
import com.example.admin.googleplaces.interfaces.Requests;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.example.admin.googleplaces.requests.Request;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class MessageHandler extends Handler {

    private static final int SEND_SEARCH_REQUEST = 1;
    private static final int SEND_PHOTO_REQUEST = 2;
    private static final int SEND_DETAILS_REQUEST = 4;
    private static final int SEND_PHOTOSSS_REQUEST =5;
    private Requests activity_;
    private RequestManager manager;
    private PreviewData previewData_ = new PreviewData();

    public MessageHandler(RequestManager manager){
        this.manager = manager;
    }

    public void handleMessage(Message msg){
        switch (msg.what){
            case SEND_SEARCH_REQUEST:
                break;
            case SEND_PHOTO_REQUEST:
                String response = (String)msg.obj;
                List<NearbyPlaceDetails> nearbyPlaceDetailses = new ArrayList<>();
                List<NearbyPlaceDetails> placesWithPhoto = new ArrayList<>();
                try {

                    nearbyPlaceDetailses = FetchPlaceSearchRequest.getNearbyPlaces(response);
                    placesWithPhoto  = FetchPlaceSearchRequest.getPlacesWithPhoto( nearbyPlaceDetailses);

                } catch (JSONException e) {
                    Log.e("ERROR",e.getMessage());
                }
                previewData_.setName(placesWithPhoto.get(0).getName());
                previewData_.setPlaceId(placesWithPhoto.get(0).getPlaceId());

                manager.sendPhotoRequest(placesWithPhoto);
                break;
            case 3:
                List<Bitmap> images = new ArrayList<>();
                images = (List<Bitmap>) msg.obj;
             
               previewData_.setImage_(images);
                manager.updatePreview(previewData_);

                break;
            case SEND_PHOTOSSS_REQUEST:
                String placeDetailsResponse = (String)msg.obj;
                 ExplicitPlaceDetails details_ = new ExplicitPlaceDetails();
                List<Photo> photoRefs = new ArrayList<Photo>();
                try {
                    details_ = FetchPlaceDetailsRequest.parsePlaceDetailsResponse(placeDetailsResponse);
                } catch (JSONException e) {
                    Log.e("ERROR", e.getMessage());
                }
                photoRefs = details_.getPhotos();

                manager.getPhotos(photoRefs);

        }
    }
}
