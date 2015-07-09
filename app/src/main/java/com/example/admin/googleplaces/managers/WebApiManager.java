package com.example.admin.googleplaces.managers;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.googleplaces.activities.MainActivity;
import com.example.admin.googleplaces.data.NearbyPlaceDetails;
import com.example.admin.googleplaces.data.RequestParams;
import com.example.admin.googleplaces.requests.FetchPhotoRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
public class WebApiManager {
    private String info_;
    private final String LOG_TAG = WebApiManager.class.getSimpleName();
    private String apiKey;


    public String getPlaceInfo(LatLng point, String key) {
        if ((point != null) && (key != null)) {
            apiKey = key;
            RequestParams requestParams = new RequestParams(point,50,key);
            new WebApiWorker().execute(requestParams);
            return info_;
        } else {
            Log.e(LOG_TAG, "Invalid input params");
            throw new InvalidParameterException("Invalid input params");
        }
    }

    private class WebApiWorker extends AsyncTask<RequestParams, Void, String> {

        private final String LOG_TAG = WebApiWorker.class.getSimpleName();

        protected String doInBackground(RequestParams... params) {

            if (params.length == 0) {
                return null;
            }

            // Create search query

            URL url = FetchPlaceSearchRequest.getUrl(params[0]);
            // Send request
            String data = FetchPlaceSearchRequest.sendRequest(url);
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    List<NearbyPlaceDetails> places = FetchPlaceSearchRequest.parseSearchPlacesResponse(result);
                    List<String> photoRefs = FetchPlaceSearchRequest.getPhotoRefsFromAllPlaces(places);

                    if ((photoRefs != null) && (photoRefs.size() != 0)) {
                        new PhotoExtractor().execute(Integer.toString(400), Integer.toString(400), photoRefs.get(0), apiKey);
                    }
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class PhotoExtractor extends AsyncTask<String, Void, Bitmap> {

        // ATTENTION
        // PARAMS represents 4 args: width,height,photoRefs,key
        protected Bitmap doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            // Create photo request query
            URL url = FetchPhotoRequest.getQuery(params[0], params[1], params[2], params[3]);
            // Send request
            Bitmap photo = FetchPhotoRequest.sendPhotoRequest(url);
            return photo;
        }

        @Override
        protected void onPostExecute(Bitmap photo){

            MainActivity.imageView.setImageBitmap(photo);
        }
    }
}
