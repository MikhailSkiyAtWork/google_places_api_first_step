package com.example.admin.googleplaces.requests;

import android.net.Uri;
import android.util.Log;

import com.example.admin.googleplaces.helpers.States;
import com.example.admin.googleplaces.helpers.Utily;
import com.example.admin.googleplaces.models.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Mikhail Valuyskiy on 27.07.2015.
 */
public class FetchTextSearchRequest extends GeneralRequest {

    private static final String TEXT_SEARCH_REQUEST_TAG = "text_search";

    //region Keys for building query
    private static final String BASE_PLACE_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
    private static final String QUERY_KEY = "query";
    private static final String LOCATION_KEY = "location";
    private static final String TYPES_KEY = "types";
    private static final String RADIUS_KEY = "radius";
    protected static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    private RequestParams requestParams_;

    public FetchTextSearchRequest(RequestParams requestParams){
        this.requestParams_ = requestParams;
    }

    /**
     * Creates url for text search request by current location, radius, and typed text provided by user
     */
    public String getUrl() {
        String url = null;
        Uri builtUri = null;
        // If user has checked type, so create request with types
        if (requestParams_.getTypes() != null) {
            if (requestParams_.getTypes().size() > 0) {
                try {
                    builtUri = Uri.parse(BASE_PLACE_SEARCH_URL).buildUpon()
                            .appendQueryParameter(QUERY_KEY, this.requestParams_.getQuery())
                            .appendQueryParameter(LOCATION_KEY, this.requestParams_.getPoint())
                            .appendQueryParameter(TYPES_KEY, (Utily.createRequestFriendlyTypes(requestParams_.getTypes())))
                            .appendQueryParameter(RADIUS_KEY, this.requestParams_.getRadius())
                            .appendQueryParameter(GOOGLE_PLACES_API_KEY, this.requestParams_.getApiKey())
                            .build();
                } catch (Exception e) {
                    Log.e("UnsupportedEncoding", e.getMessage());
                }
            }
        } else {
            builtUri = Uri.parse(BASE_PLACE_SEARCH_URL).buildUpon()
                    .appendQueryParameter(QUERY_KEY, this.requestParams_.getQuery())
                    .appendQueryParameter(LOCATION_KEY, this.requestParams_.getPoint())
                    .appendQueryParameter(RADIUS_KEY, this.requestParams_.getRadius())
                    .appendQueryParameter(GOOGLE_PLACES_API_KEY, this.requestParams_.getApiKey())
                    .build();
        }
        url = builtUri.toString();
        return url;
    }

    public String getTag() {
        return this.TEXT_SEARCH_REQUEST_TAG;
    }

    public int getStatus() {
        return States.NEARBY_PLACES_WAS_FOUND;
    }
}
