package com.example.admin.googleplaces.managers;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.admin.googleplaces.handlers.MessageHandler;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.PlaceDetails;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPhotoRequest;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class RequestManager {

    private static final int SEND_SEARCH_REQUEST = 1;
    private static final int NEARBY_PLACES_WAS_FOUND = 2;
    private static final int PHOTO_DOWNLOADED = 3;
    private static final int SEND_DETAILS_REQUEST = 4;
    private static final int SEND_PHOTOSSS_REQUEST =5;

    private  MessageHandler handler_ = new MessageHandler(this);
    private UIactions clientActivity_;

    private ExplicitPlaceDetails details_;
    private List<Photo> photoRefs = new ArrayList<Photo>();


    public RequestManager(UIactions clienActivity){
       this.clientActivity_ = clienActivity;
    }

    public void parseDetailedResponse(String response){
        ExplicitPlaceDetails details = new ExplicitPlaceDetails();
        try {
            details = FetchPlaceDetailsRequest.parsePlaceDetailsResponse(response);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }
        handler_.sendMessage(handler_.obtainMessage(EXPLICIT_DETAILS_WAS_PARSE_OUT,details));
    }

    public  void sendDetailedRequest(final RequestParams requestParams){
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                FetchPlaceDetailsRequest searchRequest = new FetchPlaceDetailsRequest(requestParams);
                URL url = searchRequest.getUrl();

                String response = searchRequest.sendRequest(url);

                handler_.sendMessage(handler_.obtainMessage(SEND_PHOTOSSS_REQUEST,response));
            }
        });
        background.start();
    }

    public  void sendSearchRequest(final RequestParams requestParams){
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                FetchPlaceSearchRequest searchRequest = new FetchPlaceSearchRequest(requestParams);
                URL url = searchRequest.getUrl();
                String response = searchRequest.sendRequest(url);
                handler_.sendMessage(handler_.obtainMessage(NEARBY_PLACES_WAS_FOUND,response));
            }
        });
        background.start();
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
                    URL url = photoRequest.getUrl();
                    Bitmap a = photoRequest.sendPhotoRequest(url);
                    photos.add(a);
                    handler_.sendMessage(handler_.obtainMessage(PHOTO_DOWNLOADED, photos));
                }
            });
            back.start();
        }
    }

    public void getPhotos( List<Photo> places){

        for (int i=0;i<places.size();i++) {
            final RequestParams requestParams = new RequestParams(60, 60, places.get(i).getPhotoReference(), "AIzaSyAHi0UQFl62k5kkFgrxWoS2xlnFd8p8_So");

            Thread back = new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Bitmap> images= new ArrayList<Bitmap>();

                    FetchPhotoRequest photoRequest = new FetchPhotoRequest(requestParams);
                    URL url = photoRequest.getUrl();
                    Bitmap a = photoRequest.sendPhotoRequest(url);
                    images.add(a);
                    handler_.sendMessage(handler_.obtainMessage(PHOTO_DOWNLOADED, images));
                }
            });
            back.start();
        }
    }

    public void updatePreview(PreviewData previewData){
        clientActivity_.showPreview(previewData);
    }



}
