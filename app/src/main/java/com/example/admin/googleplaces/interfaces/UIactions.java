package com.example.admin.googleplaces.interfaces;

import android.content.Context;

import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;

import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 17.07.2015.
 */
public interface UIactions {
    void showPreview(PreviewData previewData);
    Context getContextForClient();
}