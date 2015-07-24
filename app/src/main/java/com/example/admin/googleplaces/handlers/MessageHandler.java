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
import com.example.admin.googleplaces.helpers.States;

import org.json.JSONException;

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

    public MessageHandler(RequestManager manager) {
        this.manager = manager;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case States.SEND_SEARCH_REQUEST:
                break;
            case States.NEARBY_PLACES_WAS_FOUND:
                if (msg.obj != null) {
                    String response = (String) msg.obj;
                    manager.parseSearchResponse(response);
                }
                break;

            // Search response was parse out so send one photo request for preview
            // If there is no places with photo notify user about it
            case States.SEARCH_RESPONSE_WAS_PARSE_OUT:
                Log.v("SEARCH_RESPONSE PARSED", msg.obj.toString());
                if (msg.obj != null) {
                    List<NearbyPlaceDetails> fetchedPlaces = (List<NearbyPlaceDetails>) msg.obj;
                    if (fetchedPlaces.size() != 0) {
                        previewData_.setName(fetchedPlaces.get(FIRST_ITEM).getName());
                        previewData_.setPlaceId(fetchedPlaces.get(FIRST_ITEM).getPlaceId());
                        manager.VsendPhotoRequest(fetchedPlaces.get(FIRST_ITEM).getPhotos().get(FIRST_ITEM));
                    } else {
                        manager.showWarning();
                    }
                }
                break;

            case States.PHOTO_DOWNLOADED:
                if (msg.obj != null) {
                    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                    images = (ArrayList<Bitmap>) msg.obj;
                    previewData_.setImages(images);
                    manager.updatePreview(previewData_);
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
                        manager.VsendPhotoRequest(details.getPhotos().get(i));
                    }
                }
                break;

            default:
                break;
        }
    }
}
