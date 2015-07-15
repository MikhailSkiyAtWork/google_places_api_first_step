package com.example.admin.googleplaces.requests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.googleplaces.activities.MainActivity;
import com.example.admin.googleplaces.listeners.AsyncTaskListener;
import com.example.admin.googleplaces.listeners.PhotoExtractorListener;
import com.example.admin.googleplaces.managers.PhotoExtractor;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.RequestParams;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
public class FetchPhotoRequest {

    //region Keys for building query
    private static final String BASE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?";
    private static final String MAX_WIDTH_KEY = "maxwidth";
    private static final String MAX_HEIGHT_KEY = "maxheight";
    private static final String PHOTO_REFERENCE_KEY = "photoreference";
    private static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();
    private Bitmap photo_;

    public Bitmap getPhoto(String maxWidth, String maxHeight, String photoReference, String key) {
        if ((key != null)) {

            RequestParams requestParams = new RequestParams(Integer.parseInt(maxWidth),Integer.parseInt(maxHeight),photoReference,key);

            PhotoExtractor task = new PhotoExtractor(new PhotoExtractorListener() {
                @Override
                public void photoDownloaded(Bitmap photo) {

                    if (photo != null) {
                        photo_ = photo;
                    }
                }
            });

            task.execute(requestParams);
            return photo_;
        } else {
            Log.e(LOG_TAG, "Invalid input params");
            throw new InvalidParameterException("Invalid input params");
        }
    }

    private class PhotoExtractor extends AsyncTask<RequestParams, Void, Bitmap> {

       PhotoExtractorListener listener;

        public PhotoExtractor(PhotoExtractorListener listener){
            this.listener = listener;
        }

        // ATTENTION
        // PARAMS represents 4 args: width,height,photoRefs,key
        protected Bitmap doInBackground(RequestParams... params) {
            if (params.length == 0) {
                return null;
            }
            // Create photo request query
            URL url = getQuery(params[0]);
            // Send request
            Bitmap photo = sendPhotoRequest(url);
            return photo;
        }

        @Override
        protected void onPostExecute(Bitmap photo){
            listener.photoDownloaded(photo);
        }
    }

    /**
     * Creates query for getting photo
     * maxWidth       maximum desired width
     * maxHeight      maximum desired height
     * photoReference a string identifier that uniquely identifies a photo. Are returned from Place Details request
     * key            is a Google Places Api Key, in general it is storing at mainifest.xml file
     */
    public static URL getQuery(RequestParams requestParams) {
        URL url = null;
        try {
            Uri builtUri = Uri.parse(BASE_PHOTO_URL).buildUpon()
                    .appendQueryParameter(MAX_WIDTH_KEY, requestParams.getMaxWidth())
                    .appendQueryParameter(MAX_HEIGHT_KEY, requestParams.getMaxHeight())
                    .appendQueryParameter(PHOTO_REFERENCE_KEY, requestParams.getPhotoReference())
                    .appendQueryParameter(GOOGLE_PLACES_API_KEY, requestParams.getApiKey())
                    .build();

            url = new URL(builtUri.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        }
        return url;
    }

    /**
     * Sends request by special URL to get photo
     *
     * @param url the photo request query
     * @return string that contains json response from server
     */
    public static Bitmap sendPhotoRequest(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = null;
        Bitmap photo = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream
            InputStream inputStream = urlConnection.getInputStream();

            if (inputStream == null) {
                return null;
            }

            photo = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return photo;
    }
}
