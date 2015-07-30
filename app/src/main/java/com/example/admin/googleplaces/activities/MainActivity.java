package com.example.admin.googleplaces.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.helpers.Constants;
import com.example.admin.googleplaces.helpers.Utily;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.example.admin.googleplaces.requests.FetchTextSearchRequest;
import com.example.admin.googleplaces.services.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener, UIactions, View.OnKeyListener {


    private final int PREVIEW_IMAGE_INDEX = 0;
    private GoogleMap map_; // Might be null if Google Play services APK is not available.
    private RequestManager manager_ = new RequestManager(this);
    private LatLng selectedPoint_;
    private EditText searchField_;
    private Button searchButton_;
    private String radius_;
    private LatLng currentLocation_;
    private GPSTracker tracker_;
    private ArrayList<Integer> selectedItems_;
    private ArrayList<String> selectedValues_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchField_ = (EditText) this.findViewById(R.id.search_field);
        searchField_.setOnKeyListener(this);
        searchButton_ = (Button) this.findViewById(R.id.search_button);

        tracker_ = new GPSTracker(this);
        selectedValues_ = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.pref_type_values)));

        // Get radius values from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        radius_ = preferences.getString(getResources().getString(R.string.pref_radius_key), Integer.toString(Constants.DEFAULT_RADIUS));

        // Put data about display
        Utily.setDisplayPrefs(this);
        setUpMapIfNeeded();

        // Handle user's click on the map
        map_.setOnMapClickListener(this);

        searchButton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextSearchRequest();
            }
        });
    }


    protected Dialog onCreateDialog(int id){
        selectedItems_ = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle(R.string.pref_type_summary)
                .setMultiChoiceItems(R.array.pref_type_options, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedItems_.add(which);
                        } else if (selectedItems_.contains(which)) {
                            selectedItems_.remove(Integer.valueOf(which));
                        }
                    }
                });

        return builder.create();
    }

    public void launchTask(View v) {
        switch (v.getId()) {
            case (R.id.search_button):
                sendTextSearchRequest();
                break;
            case (R.id.clear_button):
                clearEditText();
                clearMap();
                break;
            case (R.id.types_button):
                showDialog(0);
                break;
            case (R.id.find_me_button):
                if (tracker_.isGpsEnabled()) {
                    moveToCurrentLocation();
                } else {
                    showNoGpsWarning();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            sendTextSearchRequest();
            return true;
        }
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        selectedPoint_ = latLng;
        map_.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        RequestParams requestParams = new RequestParams(latLng, 5, Utily.getApiKey(this));
        manager_.postNearbySearchRequest(requestParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showPreview(PreviewData previewData) {
        map_.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if (previewData.getImages().size() != 0) {
            Bitmap previewImage = previewData.getImages().get(PREVIEW_IMAGE_INDEX);
            image = (Bitmap.createScaledBitmap(previewImage, Constants.HEIGHT_PREVIEW, Constants.WIDTH_PREVIEW, false));
            LatLng foundPlace = new LatLng(previewData.getLatitude(), previewData.getLongitude());
            map_.animateCamera(CameraUpdateFactory.newLatLng(foundPlace));
            map_.addMarker(new MarkerOptions()
                            .position(foundPlace)
                            .title(previewData.getName())
                            .snippet(previewData.getPlaceId())
                            .icon(BitmapDescriptorFactory.fromBitmap(Utily.getRoundedCroppedBitmap(image, Constants.DEFAULT_CROPPING_RADIUS)))
            );
        }
    }

    public void showWarning() {
        Toast.makeText(this, getResources().getString(R.string.no_photo_label), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String id = marker.getSnippet();
        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
        intent.putExtra(getResources().getString(R.string.place_id_key), id);
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

        // Get current location
        if (tracker_.isGpsEnabled()) {
            currentLocation_ = tracker_.getCurrentLocation();
        } else {
            currentLocation_ = map_.getCameraPosition().target;
        }

        RequestParams requestParams = new RequestParams(query, currentLocation_, Integer.parseInt(radius_), Utily.getApiKey(this));

        ArrayList<String> values = new ArrayList<>();
        if (selectedItems_ != null) {
            for (int i = 0; i < selectedItems_.size(); i++) {
                String type = selectedValues_.get(selectedItems_.get(i));
                values.add(type);
                requestParams.setTypes(values);
            }
        }
        manager_.postTextSearchRequest(requestParams);
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

    private void setUpMap() {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Constants.MOSCOW_LAT, Constants.MOSCOW_LONG));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(Constants.DEFAULT_ZOOM);
        map_.moveCamera(center);
        map_.animateCamera(zoom);
    }

    private void clearEditText() {
        searchField_.setText("");
    }

    private void clearMap() {
        map_.clear();
    }

    private void moveToCurrentLocation() {
        LatLng currentLocation = tracker_.getCurrentLocation();
        map_.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
        map_.addMarker(new MarkerOptions()
                .position(currentLocation)
                .title(getResources().getString(R.string.my_location_label))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    private void showNoGpsWarning() {
        Toast.makeText(this, getResources().getString(R.string.no_gps_label), Toast.LENGTH_LONG).show();
    }
}
