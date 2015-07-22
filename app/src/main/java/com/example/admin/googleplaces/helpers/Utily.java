package com.example.admin.googleplaces.helpers;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.googleplaces.interfaces.UIactions;
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

    public static String getApiKey(Context context) {
        String placesApiKey = "";
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;
            placesApiKey = bundle.getString("com.google.android.maps.v2.API_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(context.getApplicationInfo().className, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(context.getApplicationInfo().className, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
        return placesApiKey;
    }

}
