package com.example.admin.googleplaces.managers;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.googleplaces.MainActivity;
import com.example.admin.googleplaces.data.PlaceDetails;
import com.example.admin.googleplaces.requests.FetchPhotoRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;

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


    public String getPlaceInfo(String point, String key) {
        if ((point != null) && (key != null)) {
            apiKey = key;
            new WebApiWorker().execute(point, key);
            return info_;
        } else {
            Log.e(LOG_TAG, "Invalid input params");
            throw new InvalidParameterException("Invalid input params");
        }
    }

    private class WebApiWorker extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = WebApiWorker.class.getSimpleName();

        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            // Create search query
            URL url = FetchPlaceSearchRequest.getQuery(params[0], params[1]);
            // Send request
            String data = FetchPlaceSearchRequest.sendSearchRequest(url);
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    List<PlaceDetails> places = FetchPlaceSearchRequest.parseSearchPlacesResponse(result);
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
