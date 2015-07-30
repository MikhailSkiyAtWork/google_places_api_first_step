package com.example.admin.googleplaces.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.adapters.FullScreenImageAdapter;
import com.example.admin.googleplaces.helpers.Utily;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;

import java.util.ArrayList;

public class FullscreenActivity extends ActionBarActivity implements UIactions {

    private ViewPager viewPager_;
    private ArrayList<Bitmap> photos_;
    private RequestManager manager_ = new RequestManager(this);
    private int position_;
    private FullScreenImageAdapter adapter_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        viewPager_ = (ViewPager) findViewById(R.id.pager);

        Bundle extras = getIntent().getExtras();
        String placeId = extras.getString(getResources().getString(R.string.place_id_key));
        position_ = extras.getInt(this.getResources().getString(R.string.position));
        RequestParams requestParams = new RequestParams(placeId, Utily.getApiKey(this));
        manager_.postDetailsSearchRequest(requestParams);
        photos_ = new ArrayList<>();
    }

    public void showPreview(PreviewData previewData) {
        for (int i = 0; i < previewData.getImages().size(); i++) {
            photos_.add(previewData.getImages().get(i));
        }

        adapter_ = new FullScreenImageAdapter(this, photos_);
        viewPager_.setAdapter(adapter_);
        viewPager_.setCurrentItem(position_);
    }

    // Necessary for supporting UIactions interface
    public void showWarning() {
    }

    public Context getContextForClient() {
        return this.getApplicationContext();
    }
}
