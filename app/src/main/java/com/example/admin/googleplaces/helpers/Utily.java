package com.example.admin.googleplaces.helpers;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.activities.MainActivity;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Helper for general operations
 */
public class Utily {

    public static final int WIDTH_CODE = 0;
    public static final int HEIGHT_CODE = 1;

    public static final int NO_PHOTOS = 0;
    public static final int PHOTO_WAS_FOUND = 1;

    public static final String DISPLAY_PREFS = "Display_prefs";
    public static final String DISPLAY_HEIGHT = "height";
    public static final String DISPLAY_WIDTH = "width";

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

    public static void setDisplayPrefs(Context context){
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        int widthScreen = display.getWidth();
        int heightScreen = display.getHeight();
        SharedPreferences.Editor editor = context.getSharedPreferences(DISPLAY_PREFS, Context.MODE_PRIVATE).edit();
        editor.putInt(DISPLAY_WIDTH,widthScreen);
        editor.putInt(DISPLAY_HEIGHT,heightScreen);
        editor.commit();
    }

    public static int getApropriateWidth(Context context, int currentWidth) {
        SharedPreferences prefs = context.getSharedPreferences(DISPLAY_PREFS, Context.MODE_PRIVATE);
        int width = prefs.getInt(DISPLAY_WIDTH, currentWidth);
        double w = currentWidth / width;
        int scale = (int) Math.round(w);
        if (scale == 0) scale = scale + 1;
        int resultWidth = currentWidth/scale;
        return resultWidth;
    }

    public static int getApropriateHeight(Context context, int currentHeight) {
        SharedPreferences prefs = context.getSharedPreferences(DISPLAY_PREFS, Context.MODE_PRIVATE);
        int height = prefs.getInt(DISPLAY_HEIGHT, currentHeight);
        double w = currentHeight / height;
        int scale = (int) Math.round(w);
        if (scale == 0) scale = scale + 1;
        int resultHeight = currentHeight/scale;
        return resultHeight;
    }

    public static int getApropriateSize(Context context, int currentSize,int dimension) {
        SharedPreferences prefs = context.getSharedPreferences(DISPLAY_PREFS, Context.MODE_PRIVATE);
        int height = 0;
        if (dimension == HEIGHT_CODE){
             height = prefs.getInt(DISPLAY_HEIGHT, currentSize);
        } else if (dimension == WIDTH_CODE){
             height = prefs.getInt(DISPLAY_WIDTH, currentSize);
        }
        double w = currentSize / height;
        int apropriateHeight = (int) Math.round(w);
        if (apropriateHeight == 0) apropriateHeight = apropriateHeight + 1;
        int resultHeight = currentSize/apropriateHeight;
        return resultHeight;
    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap,int radius){
        Bitmap finalBitmap;
        if (bitmap.getWidth()!=radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap,radius,radius,false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0,0,finalBitmap.getWidth(),finalBitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
                finalBitmap.getHeight() / 2 + 0.7f,
                finalBitmap.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);

        return output;
    }

    /**
     * Creates short string which contains types in special format (such as type1|type2|type3|type4)
     */
    public static String createRequestFriendlyTypes (ArrayList<String> values) {
        String requestFriendlyString = "";
        if (values.size() == 1) {
            requestFriendlyString = values.get(0);
        } else {
            for (int i = 0; i < values.size(); i++) {
                requestFriendlyString += values.get(i);
                if (values.size()-1 != i){
                    requestFriendlyString +="|";
                }
            }
        }
        Log.v("TYPES",requestFriendlyString);
        return requestFriendlyString;
    }

}
