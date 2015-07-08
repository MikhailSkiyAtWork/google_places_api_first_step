package com.example.admin.googleplaces.requests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    /**
     * Creates query for getting photo
     *
     * @param maxWidth       maximum desired width
     * @param maxHeight      maximum desired height
     * @param photoReference a string identifier that uniquely identifies a photo. Are returned from Place Details request
     * @param key            is a Google Places Api Key, in general it is storing at mainifest.xml file
     * @return
     */
    public static URL getQuery(String maxWidth, String maxHeight, String photoReference, String key) {
        URL url = null;
        try {
            Uri builtUri = Uri.parse(BASE_PHOTO_URL).buildUpon()
                    .appendQueryParameter(MAX_WIDTH_KEY, maxWidth)
                    .appendQueryParameter(MAX_HEIGHT_KEY, maxHeight)
                    .appendQueryParameter(PHOTO_REFERENCE_KEY, photoReference)
                    .appendQueryParameter(GOOGLE_PLACES_API_KEY, key)
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



//            reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line + "\n");
//            }
//
//            if (buffer.length() == 0) {
//                return null;
//            }
//            jsonResult = buffer.toString();

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
