package com.example.admin.googleplaces;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MainActivity extends ActionBarActivity {

    private GoogleMap map_; // Might be null if Google Play services APK is not available.
    private static final LatLng SQUARE = new LatLng(47.208712, 38.936523);
    private static final LatLng SQUARE_1 = new LatLng(47.208467, 38.936180);
    private static final LatLng SQUARE_2 = new LatLng(47.209021, 38.934740);
    private static final LatLng SQUARE_3 = new LatLng(47.209686, 38.935155);
    private static final LatLng SQUARE_4 = new LatLng(47.209686, 38.935155);

    private static final LatLng FAINA = new LatLng(47.210497, 38.933804);
    private static final LatLng FAINA_1 = new LatLng(47.211109, 38.932806);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
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
                addLines();
            }
        }
    }

    private void addLines() {

        map_.addPolyline((new PolylineOptions())
                .add(SQUARE, SQUARE_1, SQUARE_2,SQUARE_3,SQUARE_4,FAINA,FAINA_1).width(5).color(Color.BLUE)
                .geodesic(true));
        // move camera to zoom on map
        map_.moveCamera(CameraUpdateFactory.newLatLngZoom(SQUARE,
                13));
    }

    private void setUpMap() {
        map_.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
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
