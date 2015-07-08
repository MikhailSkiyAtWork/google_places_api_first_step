package com.example.admin.googleplaces.managers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.googleplaces.requests.FetchPlaceDeatilsRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
public class WebApiManager {
    private String info_;
    private final String LOG_TAG = WebApiManager.class.getSimpleName();


    public String getPlaceInfo(String point, String key) {
        if ((point != null) && (key != null)) {
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
            URL url = FetchPlaceDeatilsRequest.getQuery(params[0], params[1]);
            // Send request
            String data = FetchPlaceDeatilsRequest.sendSearchRequest(url);
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                FetchPlaceDeatilsRequest.parseResponse(result);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
