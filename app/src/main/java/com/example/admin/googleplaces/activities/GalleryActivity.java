package com.example.admin.googleplaces.activities;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.adapters.PhotoAdapter;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends ActionBarActivity implements UIactions {

    RequestManager manager = new RequestManager(this);
    List<Bitmap> photos_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle extras = getIntent().getExtras();
        String placeId = extras.getString(getResources().getString(R.string.place_id_key));
        RequestParams requestParams = new RequestParams(placeId,"AIzaSyAHi0UQFl62k5kkFgrxWoS2xlnFd8p8_So");
        manager.VsendDetailedRequest(requestParams);
        photos_ = new ArrayList<>();

    }

    public void showPreview(PreviewData previewData) {
        for (int i = 0; i < previewData.getImages().size(); i++) {
            photos_.add(previewData.getImages().get(i));
        }
        GridView gridView = (GridView) findViewById(R.id.gridview);
        PhotoAdapter adapter = new PhotoAdapter(this, photos_);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
