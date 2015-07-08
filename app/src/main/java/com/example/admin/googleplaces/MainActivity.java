package com.example.admin.googleplaces;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import com.example.admin.googleplaces.managers.WebApiManager;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener {

    private GoogleMap map_; // Might be null if Google Play services APK is not available.
    private TextView text_;
    public static ImageView imageView;
    private String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_ = (TextView) findViewById(R.id.locinfo);
        imageView = (ImageView) findViewById(R.id.cover_image_view);
        setUpMapIfNeeded();

        // Handle user's click on the map
        map_.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String point = latLng.toString();

        // Show info about selected point
        text_.setText(point);
        map_.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        /////////

        String location = Utily.getStringLocation(latLng);
        WebApiManager manager = new WebApiManager();
        String key = getApiKey();
        String result = manager.getPlaceInfo(location, key);

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

    private void setUpMap() {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.chehov);
        // map_.addMarker(new MarkerOptions().position(new LatLng(47.2092003, 38.9334364)).title("Marker").icon(icon));
        map_.addMarker(new MarkerOptions().position(new LatLng(47.2092003, 38.9334364)).title("Marker"));

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
