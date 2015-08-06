package com.example.admin.googleplaces.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.adapters.PhotoAdapter;
import com.example.admin.googleplaces.helpers.Utily;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends ActionBarActivity implements UIactions {

    private RequestManager manager_ = new RequestManager(this);
    private List<Bitmap> photos_;
    private String placeId_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle extras = getIntent().getExtras();
        placeId_ = extras.getString(getResources().getString(R.string.place_id_key));
        RequestParams requestParams = new RequestParams(placeId_, Utily.getApiKey(this));
        manager_.postDetailsSearchRequest(requestParams);
        photos_ = new ArrayList<>();
    }

    public Context getContextForClient() {
        return this.getApplicationContext();
    }


    public void showPreview(PreviewData previewData) {
        for (int i = 0; i < previewData.getImages().size(); i++) {
            photos_.add(previewData.getImages().get(i));
        }
        GridView gridView = (GridView) findViewById(R.id.gridview);
        PhotoAdapter adapter = new PhotoAdapter(this, photos_, placeId_);
        gridView.setAdapter(adapter);
    }

    // Necessary for supportin UIactions interface
    public void showWarning() {
    }

}
