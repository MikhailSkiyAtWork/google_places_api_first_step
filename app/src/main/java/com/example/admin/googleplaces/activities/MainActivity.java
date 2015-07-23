package com.example.admin.googleplaces.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.googleplaces.R;
import com.example.admin.googleplaces.helpers.Utily;
import com.example.admin.googleplaces.interfaces.UIactions;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.example.admin.googleplaces.ui.MarkerView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener, UIactions {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Put data about display
        Utily.setDisplayPrefs(this);

        text_ = (TextView) findViewById(R.id.place_name);
        imageView = (ImageView) findViewById(R.id.preview_image);


        showMoreButton = (Button) findViewById(R.id.more);
        showMoreButton.setEnabled(false);
        showMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
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
        selectedPoint_ = latLng;
        map_.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        RequestParams requestParams = new RequestParams(latLng, 50, Utily.getApiKey(this));
        FetchPlaceSearchRequest searchRequest = new FetchPlaceSearchRequest(requestParams);
        manager_.sendRequest(searchRequest);
        // manager_.VsendSearchRequest(requestParams);
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
        Bitmap image = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        if (previewData.getImages().size() != 0) {
            ImageView imageView = (ImageView) this.findViewById(R.id.preview_image);
            Bitmap previewImage = previewData.getImages().get(PREVIEW_IMAGE_INDEX);
            image =(Bitmap.createScaledBitmap(previewImage,60,60,false));
            imageView.setImageBitmap(image);
        }
        TextView name = (TextView) this.findViewById(R.id.place_name);
        name.setText(previewData.getName());
        placeId_ = previewData.getPlaceId();
        showMoreButton.setEnabled(true);


        imageViewRound_ = new MarkerView(this);
        imageViewRound_.setImageBitmap(image);

        map_.addMarker(new MarkerOptions()
        .position(selectedPoint_)
        .title("lala")
        .icon(BitmapDescriptorFactory.fromBitmap(MarkerView.getRoundedCroppedBitmap(image,40))));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
        return true;
    }

    public Context getContextForClient(){
        return getApplicationContext();
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

    private void setUpMap() {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(47.2092003, 38.9334364));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
        map_.moveCamera(center);
        map_.animateCamera(zoom);
    }

}
