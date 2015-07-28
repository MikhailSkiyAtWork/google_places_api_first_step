package com.example.admin.googleplaces.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.helpers.Utily;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.example.admin.googleplaces.requests.FetchTextSearchRequest;
import com.example.admin.googleplaces.services.GPSTracker;
import com.example.admin.googleplaces.ui.MarkerView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, UIactions {


    public static ImageView imageView;
    private final int PREVIEW_IMAGE_INDEX = 0;
    private GoogleMap map_; // Might be null if Google Play services APK is not available.
    private TextView text_;
    private String TAG = MainActivity.class.getName();
    private RequestManager manager_ = new RequestManager(this);
    private Button showMoreButton;
    private String placeId_;
    private ImageView imageViewRound_;
    private LatLng selectedPoint_;
    private LinearLayout mainLayout_;
    private String selectedMode_;
    private EditText searchField_;
    private Button searchButton_;
    private LinearLayout layout_;
    private GPSTracker tracker_;
    private double latitude_;
    private double longitude_;
    private String radius_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchField_ = (EditText)this.findViewById(R.id.search_field);
        searchButton_ = (Button)this.findViewById(R.id.search_button);

        // Get radius values from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        radius_ = preferences.getString(getResources().getString(R.string.pref_radius_key), "50");

        // Get current location
        tracker_ = new GPSTracker(this);
        if (tracker_.canGetLocation()) {
            latitude_ = tracker_.getLatitude();
            longitude_ = tracker_.getLongitude();
        }

        searchButton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextSearchRequest();
            }
        });


        mainLayout_ = (LinearLayout) this.findViewById(R.id.main_layout);

        // Put data about display
        Utily.setDisplayPrefs(this);
        setUpMapIfNeeded();

        // Handle user's click on the map
        map_.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        selectedPoint_ = latLng;
        map_.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        RequestParams requestParams = new RequestParams(latLng,Integer.parseInt(radius_), Utily.getApiKey(this));
        FetchPlaceSearchRequest searchRequest = new FetchPlaceSearchRequest(requestParams);
        manager_.sendRequest(searchRequest);
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

    public void showPreview(PreviewData previewData) {

        map_.clear();
        map_.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        if (previewData.getImages().size() != 0) {
            Log.v("SHOW_PREVIEW", Integer.toString(previewData.getImages().size()));
            placeId_ = previewData.getPlaceId();
            Bitmap previewImage = previewData.getImages().get(PREVIEW_IMAGE_INDEX);
            image = (Bitmap.createScaledBitmap(previewImage, 60, 60, false));

            LatLng foundPlace = new LatLng(previewData.getLatitude(),previewData.getLongitude());
            map_.animateCamera(CameraUpdateFactory.newLatLng(foundPlace));

            map_.addMarker(new MarkerOptions()
                            .position(foundPlace)
                            .title(previewData.getName())
                            .icon(BitmapDescriptorFactory.fromBitmap(MarkerView.getRoundedCroppedBitmap(image, 50)))
            );
        }

    }

    public void showWarning() {
        Toast.makeText(this, "There is no photos,please choose another place", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
        intent.putExtra(getResources().getString(R.string.place_id_key), placeId_);
        startActivity(intent);
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
        return true;
    }

    public Context getContextForClient() {
        return getApplicationContext();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_settings):
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendTextSearchRequest() {
        String query = searchField_.getText().toString();
        LatLng currentLocation = new LatLng(latitude_, longitude_);
        RequestParams requestParams = new RequestParams(query, currentLocation, Integer.parseInt(radius_), Utily.getApiKey(this));

        FetchTextSearchRequest searchRequest = new FetchTextSearchRequest(requestParams);
        manager_.sendRequest(searchRequest);

    }

    private void setUpMap() {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(47.2092003, 38.9334364));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
        map_.moveCamera(center);
        map_.animateCamera(zoom);
    }

    /**
     * Creates UI depend on the selcted mode
     */
    private void createUI() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedMode_ = preferences.getString(getResources().getString(R.string.pref_modes_key), "");

        if (selectedMode_.equals(getResources().getString(R.string.pref_mode_specific_search))) {

            if (layout_ == null || !layout_.isFocusable()) {

                // Create UI
                layout_ = new LinearLayout(this);

                layout_.setId(R.id.search_field);
                layout_.setOrientation(LinearLayout.HORIZONTAL);
                layout_.setWeightSum(4.0f);

                searchField_ = new EditText(this);
                searchField_.setHint("What are you looking for?");

                searchButton_ = new Button(this);
                searchButton_.setText("Search");
                searchButton_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendTextSearchRequest();
                    }
                });


                layout_.addView(searchField_);
                layout_.addView(searchButton_);

                mainLayout_.addView(layout_);
            }
        } else {
            if (mainLayout_ != null) {
                mainLayout_.removeView(layout_);
            }
        }
    }

}
