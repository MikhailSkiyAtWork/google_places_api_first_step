package com.example.admin.googleplaces.requests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.googleplaces.models.RequestParams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */
public class FetchPhotoRequest {

    private static final int SUCCESS_STATUS = 200;

    //region Keys for building query
    private static final String BASE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?";
    private static final String MAX_WIDTH_KEY = "maxwidth";
    private static final String MAX_HEIGHT_KEY = "maxheight";
    private static final String PHOTO_REFERENCE_KEY = "photoreference";
    private static final String GOOGLE_PLACES_API_KEY = "key";
    //endregion

    private static final String LOG_TAG = FetchPlaceSearchRequest.class.getSimpleName();
    private Bitmap photo_;
    private RequestParams requestParams_;
    private OkHttpClient client_;

    public FetchPhotoRequest(RequestParams params){
        this.requestParams_ = params;
        this.client_ = new OkHttpClient();
    }

    /**
     * Creates query for getting photo
     */
    public String getUrl() {
        String url = null;
        Uri builtUri = Uri.parse(BASE_PHOTO_URL).buildUpon()
                .appendQueryParameter(MAX_WIDTH_KEY, requestParams_.getMaxWidth())
                .appendQueryParameter(MAX_HEIGHT_KEY, requestParams_.getMaxHeight())
                .appendQueryParameter(PHOTO_REFERENCE_KEY, requestParams_.getPhotoReference())
                .appendQueryParameter(GOOGLE_PLACES_API_KEY, requestParams_.getApiKey())
                .build();

        url = builtUri.toString();
        return url;
    }

    /**
     * Sends request by special URL to get photo
     */
    public Bitmap sendRequest(String url) throws IOException {
        Bitmap image =  run(url);
        return image;
    }

    Bitmap run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client_.newCall(request).execute();
        Bitmap image = null;
        if (response.code() == SUCCESS_STATUS){
            ResponseBody body = response.body();
            InputStream inputStream = body.byteStream();
            image = BitmapFactory.decodeStream(inputStream);
        }
        return image;
    }


}
