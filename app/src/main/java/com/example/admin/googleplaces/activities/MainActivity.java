package com.example.admin.googleplaces.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPlaceDetailsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener,UIactions {


    public static ImageView imageView;
    private final int PREVIEW_IMAGE_INDEX = 0;
    private GoogleMap map_; // Might be null if Google Play services APK is not available.
    private TextView text_;
    private String TAG = MainActivity.class.getName();
    private RequestManager manager_ = new RequestManager(this);
    private Button getMoreButton_;
    private String placeId_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_ = (TextView) findViewById(R.id.place_name);
        imageView = (ImageView) findViewById(R.id.preview_image);

        getMoreButton_ = (Button)findViewById(R.id.more);
        getMoreButton_.setEnabled(false);
        getMoreButton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                intent.putExtra(getResources().getString(R.string.place_id_key), placeId_);
                startActivity(intent);
            }
        });

        setUpMapIfNeeded();

        // Handle user's click on the map
        map_.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String point = latLng.toString();

        map_.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        RequestParams requestParams = new RequestParams(latLng,50,getApiKey());
        manager_.VsendSearchRequest(requestParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map_ == null) {
            // Try to obtain the map from the SupportMapFragment.
            map_ = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map_ != null) {
                setUpMap();
            }
        }
    }

//    public getDetails(){
//        SomeManager.sendSearchRequest(RequestParams requestParams);
//    }




// TODO Убрать get(0)
    public void showPreview(PreviewData previewData){

        if (previewData.getImages().size() != 0) {
            ImageView imageView = (ImageView) this.findViewById(R.id.preview_image);
            Bitmap previewImage = previewData.getImages().get(PREVIEW_IMAGE_INDEX);
            imageView.setImageBitmap(previewImage);
        }
        TextView name = (TextView)this.findViewById(R.id.place_name);
        name.setText(previewData.getName());

        placeId_ = previewData.getPlaceId();
        getMoreButton_.setEnabled(true);
    }

    private void setUpMap() {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(47.2092003, 38.9334364));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
        map_.moveCamera(center);
        map_.animateCamera(zoom);
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

    public String getApiKey() {
        String placesApiKey = "";
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;
            placesApiKey = bundle.getString("com.google.android.maps.v2.API_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
        return placesApiKey;
    }
}
