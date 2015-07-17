package com.example.admin.googleplaces.requests;

import com.example.admin.googleplaces.models.RequestParams;

import java.net.URL;

/**
 * Created by Mikhail Valuyskiy on 09.07.2015.
 */
public abstract class Request {

    protected static final String GOOGLE_PLACES_API_KEY = "key";

    public abstract URL getUrl(RequestParams params);
    public abstract String sendRequest();
    public abstract String getResponse();
}
