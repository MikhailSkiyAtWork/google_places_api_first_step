package com.example.admin.googleplaces;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */

import com.google.android.gms.maps.model.LatLng;

/**
 * Helper for general operations
 */
public class Utily {
    private static final String COMMA = ",";

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
}
