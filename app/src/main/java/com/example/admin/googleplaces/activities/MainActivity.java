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
import com.example.admin.googleplaces.handlers.MessageHandler;
import com.example.admin.googleplaces.interfaces.Requests;
import com.example.admin.googleplaces.managers.RequestManager;
import com.example.admin.googleplaces.models.NearbyPlaceDetails;
import com.example.admin.googleplaces.models.Photo;
import com.example.admin.googleplaces.models.PreviewData;
import com.example.admin.googleplaces.models.RequestParams;
import com.example.admin.googleplaces.requests.FetchPhotoRequest;
import com.example.admin.googleplaces.requests.FetchPlaceSearchRequest;
import com.example.admin.googleplaces.requests.Request;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.net.URL;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener,Requests {




    private GoogleMap map_; // Might be null if Google Play services APK is not available.
    private TextView text_;
    public static ImageView imageView;
    private String TAG = MainActivity.class.getName();
    RequestManager manager = new RequestManager(this);

    private Button getMore_;

    private String placeId_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_ = (TextView) findViewById(R.id.locinfo);
        imageView = (ImageView) findViewById(R.id.cover_image_view);

        getMore_ = (Button)findViewById(R.id.more);
        getMore_.setEnabled(false);
        getMore_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                intent.putExtra("placeId",placeId_);
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

        // Show info about selected point
      //  text_.setText(point);
        map_.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        RequestParams requestParams = new RequestParams(latLng,50,getApiKey());

        manager.sendSearchRequest(requestParams);

        /////////

       // ??? String location = Utily.getStringLocation(latLng);
       // WebApiManager manager = new WebApiManager();
        String key = getApiKey();
        //String result = manager.getPlaceInfo(latLng, key);

//        List<NearbyPlaceDetails> nearbyPlaceDetailses = searchRequest.getPlacesWithPhoto(searchRequest.getPlaceInfo(latLng,key));
//        List<String> photoRefs = searchRequest.getPhotoRefsFromAllPlaces(nearbyPlaceDetailses);
//
//        FetchPhotoRequest photoRequest = new FetchPhotoRequest();
//        Bitmap image = photoRequest.getPhoto(Integer.toString(60),Integer.toString(60),photoRefs.get(0),key);
//
//        ImageView imageView = (ImageView)this.findViewById(R.id.cover_image_view);
//        imageView.setImageBitmap(image);

        // TODO SET PREVIEW HERE!!!
//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.chehov);
//        map_.addMarker(new MarkerOptions().position(new LatLng(47.2092003, 38.9334364))
//                .title("Marker")
//                .icon());

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
        ImageView imageView = (ImageView) this.findViewById(R.id.cover_image_view);
        imageView.setImageBitmap(previewData.getImage_().get(0));
        TextView name = (TextView)this.findViewById(R.id.locinfo);
        name.setText(previewData.getName());
        placeId_ = previewData.getPlaceId();
        getMore_.setEnabled(true);
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
