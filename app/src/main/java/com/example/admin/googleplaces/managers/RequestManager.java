package com.example.admin.googleplaces.managers;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.admin.googleplaces.handlers.MessageHandler;
import com.example.admin.googleplaces.interfaces.Requests;
import com.example.admin.googleplaces.models.ExplicitPlaceDetails;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPhotoRequest;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.example.admin.googleplaces.requests.Request;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public class RequestManager {

    private static final int SEND_SEARCH_REQUEST = 1;
    private static final int SEND_PHOTO_REQUEST = 2;
    private static final int PHOTO_DOWNLOADED = 3;
    private static final int SEND_DETAILS_REQUEST = 4;
    private static final int SEND_PHOTOSSS_REQUEST =5;

    private  MessageHandler handler_ = new MessageHandler(this);
    private Requests clientActivity_;

    private ExplicitPlaceDetails details_;
    private List<Photo> photoRefs = new ArrayList<Photo>();


    public RequestManager(Requests clienActivity){
       this.clientActivity_ = clienActivity;
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
                handler_.sendMessage(handler_.obtainMessage(SEND_PHOTO_REQUEST,response));
            }
        });
        background.start();
    }

    public void sendPhotoRequest(List<NearbyPlaceDetails> places){
        final RequestParams requestParams = new RequestParams(60,60,places.get(0).getPhotos().get(0).getPhotoReference(),"AIzaSyAHi0UQFl62k5kkFgrxWoS2xlnFd8p8_So");

        Thread back = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = FetchPhotoRequest.getQuery(requestParams);
                Bitmap a = FetchPhotoRequest.sendPhotoRequest(url);
                handler_.sendMessage(handler_.obtainMessage(PHOTO_DOWNLOADED,a));
            }
        });
        back.start();
    }


    public void getPhotos( List<Photo> places){

        for (int i=0;i<places.size();i++) {
            final RequestParams requestParams = new RequestParams(60, 60, places.get(i).getPhotoReference(), "AIzaSyAHi0UQFl62k5kkFgrxWoS2xlnFd8p8_So");

            Thread back = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Bitmap> images= new ArrayList<>();
                    URL url = FetchPhotoRequest.getQuery(requestParams);
                    Bitmap a = FetchPhotoRequest.sendPhotoRequest(url);
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
