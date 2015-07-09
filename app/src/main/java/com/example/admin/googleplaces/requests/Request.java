package com.example.admin.googleplaces.requests;

import android.net.Uri;

import com.example.admin.googleplaces.data.PlaceDetails;
import com.example.admin.googleplaces.data.RequestParams;

import java.net.URL;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 09.07.2015.
 */
public abstract class Request {

    abstract URL getUrl(RequestParams params);
    abstract String sendRequest(URL url);
}
