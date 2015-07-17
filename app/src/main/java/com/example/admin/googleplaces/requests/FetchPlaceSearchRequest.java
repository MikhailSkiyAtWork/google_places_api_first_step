package com.example.admin.googleplaces.requests;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.googleplaces.listeners.AsyncTaskListener;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.helpers.JsonHelper;
import com.example.admin.googleplaces.models.RequestParams;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
public class FetchPlaceSearchRequest  {

    //region Keys for building query
    private static final String BASE_PLACE_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String LOCATION_KEY = "location";
    private static final String RADIUS_KEY = "radius";
    private static final String SENSOR_KEY = "sensor";
    protected static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    //region Values for building query
    private static int RADIUS_VALUE = 50;
    private static boolean SENSOR_VALUE = false;
    //endregion

    private String response_;
    private List<NearbyPlaceDetails> places_ = new ArrayList<>();
    private List<String> photoRefs_ = new ArrayList<>();
    private RequestParams requestParams_;




    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();

//    public FetchPlaceSearchRequest(RequestParams params) {
//        URL url = getUrl(params);
//        response_ = sendRequest(url);
//
//        // WARNING реквест посылается асинхронно, мы не знаем когда будет ответ, как действовать дальше?
//        try {
//            places_ = FetchPlaceSearchRequest.getNearbyPlaces(response_);
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, e.getMessage());
//        }
//
//        photoRefs_ = FetchPlaceSearchRequest.getPhotoRefsFromAllPlaces(places_);
//    }

    public FetchPlaceSearchRequest(RequestParams requestParams) {
        this.requestParams_ = requestParams;
    }

    public List<NearbyPlaceDetails> getPlaceInfo(LatLng point, String key) {
        if ((point != null) && (key != null)) {

            RequestParams requestParams = new RequestParams(point,50,key);

            WebApiWorker task = new WebApiWorker(new AsyncTaskListener() {
                @Override
                public void onTaskCompleted(String response) {

                    if (response != null) {
                        try {
                           places_ = getNearbyPlaces(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            });
            task.execute(requestParams);



            return places_;
        } else {
            Log.e(LOG_TAG, "Invalid input params");
            throw new InvalidParameterException("Invalid input params");
        }
    }



    private class WebApiWorker extends AsyncTask<RequestParams, Void, String> {

        private final String LOG_TAG = WebApiWorker.class.getSimpleName();
        AsyncTaskListener listener;

        public WebApiWorker(AsyncTaskListener listener){
            this.listener = listener;
        }

        protected String doInBackground(RequestParams... params) {

            if (params.length == 0) {
                return null;
            }
            // Create the appropriate URL
//            URL url = getUrl(params[0]);
            URL url = getUrl();
            // Send search request
            String response = sendRequest(url);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            listener.onTaskCompleted(result);
        }


}

    /**
     * Returns the response from the server as a string
     */
    public String getResponse(){
        return this.response_;
    }

    /**
     * Creates query for search request by LatLng and Google Places API KEY
     *
     * @param point is LatLng objects, that represents latitude and longitude of chosen place
     * @param key   is a Google Places Api Key, in general it is storing at mainifest.xml file
     * @return the URL of search request
     */
    // TODO add radius as arg (such as it will posiible change radius from config)
    // TODO Check requestParams.getPoint() and others not null or empty
    public URL getUrl() {
        URL url = null;
        try {
            Uri builtUri = Uri.parse(BASE_PLACE_SEARCH_URL).buildUpon()
                    .appendQueryParameter(LOCATION_KEY, this.requestParams_.getPoint())
                    .appendQueryParameter(RADIUS_KEY, this.requestParams_.getRadius())
                    .appendQueryParameter(GOOGLE_PLACES_API_KEY, this.requestParams_.getApiKey())
                    .build();

            url = new URL(builtUri.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        }
        return url;
    }

    /**
     * Sends search request by special URL
     *
     * @param url the search query
     * @return string that contains json response from server
     */
    public String sendRequest(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();

//            switch (responseCode){
//                case (200):
//                {
//                    throw new IOException();
//                }
//
//                case (201):
//                {
//                    throw new IOException();
//                }
//
//                case (204):
//                {
//                    throw new IOException();
//                }
//
//                case (401):
//                {
//                    throw new IOException();
//                }
//
//                case (403):
//                {
//                    throw new IOException();
//                }
//
//                case (404):
//                {
//                    throw new IOException();
//                }
//
//                case (405):
//                {
//                    throw new IOException();
//                }
//
//                case (422):
//                {
//                    throw new IOException();
//                }
//
//                case (500):
//                {
//                    throw new IOException();
//                }
//                default:break;
//
//            }

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonResult = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return jsonResult;
    }

    /**
     * Returns All nearby places and some details about such places
     */
    public static List<NearbyPlaceDetails> getNearbyPlaces(String jsonResponse) throws JSONException {
        List<NearbyPlaceDetails> nearbyPlaceDetailsList = JsonHelper.getAllNearbyPlaces(jsonResponse);
        return nearbyPlaceDetailsList;
    }

    /**
     * Returns all nearby places which have photos
     */
    public static   List<NearbyPlaceDetails> getPlacesWithPhoto(List<NearbyPlaceDetails> places) {
        if (places.size() != 0) {
            List<NearbyPlaceDetails> placesWithPhoto = new ArrayList<NearbyPlaceDetails>();
            List<String> photoRefsFromPlace = new ArrayList<String>();
            for (int i = 0; i < places.size(); i++) {
                photoRefsFromPlace = getPhotoRefsByPlace(places.get(i));
                if (photoRefsFromPlace.size() > 0) {
                    placesWithPhoto.add(places.get(i));
                }
            }
            return placesWithPhoto;
        } else {
            return null;
        }
    }

    public static List<String> getPhotoRefsFromAllPlaces(List<NearbyPlaceDetails> places) {
        // add check size of places
        if (places.size() != 0) {
            List<String> allPhotoRefs = new ArrayList<String>();
            List<String> photoRefsFromPlace = new ArrayList<String>();

            for (int i = 0; i < places.size(); i++) {
                photoRefsFromPlace = getPhotoRefsByPlace(places.get(i));
                if (photoRefsFromPlace != null) {
                    allPhotoRefs.addAll(photoRefsFromPlace);
                }
            }
            return allPhotoRefs;
        } else return null;
    }

    public static List<String> getPhotoRefsByPlace(NearbyPlaceDetails place) {
        List<String> photoRefs = new ArrayList<String>();
        List<Photo> photos = place.getPhotos();
        if (photos.size() != 0) {
            for (int i = 0; i < photos.size(); i++) {
                photoRefs.add(photos.get(i).getPhotoReference());
            }
            return photoRefs;
        }
        return photoRefs;
    }


}
