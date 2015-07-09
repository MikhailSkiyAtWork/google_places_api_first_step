package com.example.admin.googleplaces;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.URL;

/**
 * Helper for general operations
 */
public class Utily {
    private static final String COMMA = ",";

    private static final String LOG_TAG = Utily.class.getSimpleName();

    /**
     * Creates string-format location from LatLng objects for search query
     *
     * @param point LatLng objects, represent the selected point
     * @return Location like "latitude,longitude"
     */
    public static String getStringLocation(LatLng point) {
        String latitude = Double.toString(point.latitude);
        String longitude = Double.toString(point.longitude);
        String location = latitude + COMMA + longitude;
        return location;
    }

    /**
     * Converts icon link to the URL object
     */
    public static URL convertIconLinkToUrl(String iconLink) {
        URL iconUrl;
        try {
            iconUrl = new URL(iconLink);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        }
        return iconUrl;
    }
}
