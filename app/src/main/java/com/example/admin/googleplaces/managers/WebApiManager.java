//package com.example.admin.googleplaces.managers;
//
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.example.admin.googleplaces.activities.MainActivity;
//import com.example.admin.googleplaces.models.NearbyPlaceDetails;
//import com.example.admin.googleplaces.models.RequestParams;
//import com.example.admin.googleplaces.requests.FetchPhotoRequest;
//import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
//import com.example.admin.googleplaces.listeners.*;
//import com.example.admin.googleplaces.requests.Request;
//import com.google.android.gms.maps.model.LatLng;
//
//import org.json.JSONException;
//
//import java.net.URL;
//import java.security.InvalidParameterException;
//import java.util.List;
//
///**
// * Created by Mikhail Valuyskiy on 06.07.2015.
// */
//public class WebApiManager implements AsyncTaskListener {
//    private String info_;
//    private final String LOG_TAG = WebApiManager.class.getSimpleName();
//    private String apiKey;
//    private List<NearbyPlaceDetails> places_;
//    private FetchPlaceSearchRequest placeSearchRequest_;
//    List<String> photoRefs_;
//
//
//    public Bitmap getPlacePhoto
//
//    public String getPlaceInfo(LatLng point, String key) {
//        if ((point != null) && (key != null)) {
//            apiKey = key;
//            RequestParams requestParams = new RequestParams(point, 50, key);
//            placeSearchRequest_ = new FetchPlaceSearchRequest(requestParams);
//
//            WebApiWorker task = new WebApiWorker();
//            task.setOnResultListener(this);
//            task.execute(placeSearchRequest_);
//
//
//            return info_;
//        } else {
//            Log.e(LOG_TAG, "Invalid input params");
//            throw new InvalidParameterException("Invalid input params");
//        }
//    }
//
//    public void onTaskCompleted(String result) {
//        try {
//            places_ = placeSearchRequest_.getPlacesWithPhoto(placeSearchRequest_.getNearbyPlaces(result));
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        photoRefs_ = placeSearchRequest_.getPhotoRefsFromAllPlaces(places_);
//
//        PhotoExtractor extractor = new PhotoExtractor();
//
//        // UI as an argument
//        extractor.setPhotoExtractorListener();
//    }
//
//    private class WebApiWorker extends AsyncTask<Request, Void, String> {
//
//        private final String LOG_TAG = WebApiWorker.class.getSimpleName();
//        AsyncTaskListener listener;
//
//        public void setOnResultListener(AsyncTaskListener listener) {
//            this.listener = listener;
//        }
//
//        protected String doInBackground(Request... params) {
//
//            if (params.length == 0) {
//                return null;
//            }
//            String response = params[0].sendRequest();
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
////            try {
//            listener.onTaskCompleted(result);
//        }
////                if (result != null) {
////                    List<NearbyPlaceDetails> places = FetchPlaceSearchRequest.getNearbyPlaces(result);
////                    List<String> photoRefs = FetchPlaceSearchRequest.getPhotoRefsFromAllPlaces(places);
////
////                    if ((photoRefs != null) && (photoRefs.size() != 0)) {
////                        new PhotoExtractor().execute(Integer.toString(400), Integer.toString(400), photoRefs.get(0), apiKey);
////                    }
////                }
////
////            } catch (JSONException e) {
////                throw new RuntimeException(e);
////            }
//    }
//}
//
//private class PhotoExtractor extends AsyncTask<String, Void, Bitmap> {
//
//    PhotoExtractorListener photoExtractorListener;
//
//    public void setPhotoExtractorListener(PhotoExtractorListener listener) {
//        this.photoExtractorListener = listener;
//    }
//
//    // ATTENTION
//    // PARAMS represents 4 args: width,height,photoRefs,key
//    protected Bitmap doInBackground(String... params) {
//        if (params.length == 0) {
//            return null;
//        }
//        // Create photo request query
//        URL url = FetchPhotoRequest.getQuery(params[0], params[1], params[2], params[3]);
//        // Send request
//        Bitmap photo = FetchPhotoRequest.sendPhotoRequest(url);
//        return photo;
//    }
//
//    @Override
//    protected void onPostExecute(Bitmap photo) {
//        photoExtractorListener.photoDownloaded(photo);
//    }
//}
////}
