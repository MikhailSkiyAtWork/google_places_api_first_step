package com.example.admin.googleplaces.fragments;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.googleplaces.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class PreviewFragment extends Fragment implements GoogleMap.OnMapClickListener{

    private GoogleMap map_; // Might be null if Google Play services APK is not available.
    private String TAG = "PreviewFragment";




    public PreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMapIfNeeded();

        // Handle user's click on the map
        map_.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String point = latLng.toString();

        // Show info about selected point
       // text_.setText(point);
        map_.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        /////////

        // ??? String location = Utily.getStringLocation(latLng);
      //  WebApiManager manager = new WebApiManager();
        String key = getApiKey();
     //   String result = manager.getPlaceInfo(latLng, key);

        // TODO SET PREVIEW HERE!!!
//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.chehov);
//        map_.addMarker(new MarkerOptions().position(new LatLng(47.2092003, 38.9334364))
//                .title("Marker")
//                .icon());

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map_ == null) {
            // Try to obtain the map from the SupportMapFragment.
            map_ = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map_ != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(47.2092003, 38.9334364));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
        map_.moveCamera(center);
        map_.animateCamera(zoom);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

    public String getApiKey() {
        String placesApiKey = "";
        try {
            ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
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
