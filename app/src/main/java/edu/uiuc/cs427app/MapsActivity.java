package edu.uiuc.cs427app;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.widget.SearchView;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SearchView citySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        citySearch = (SearchView) findViewById(R.id.idSearchView);

        citySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Search for the city
                searchCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //place a map in the application
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void searchCity(String cityName) {
        //create and initialize a geo coder
        Geocoder geocoder = new Geocoder(this);
        //the list to store all the addresses
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(cityName, 1);
            // check the addresses is null or not
            if (addresses != null && !addresses.isEmpty()) {
                //get the address
                Address address = addresses.get(0);
                //get the latitude and longitude
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                // clear previous markers
                mMap.clear();
                //add marker to this address
                mMap.addMarker(new MarkerOptions().position(latLng).title(cityName));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10)); // zoom level 10 is typically city level zoom
            } /*else {
                // City not found
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // map is ready to be used and set the default address
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng champaign = new LatLng(40.1164, -88.2434);
        mMap.addMarker(new MarkerOptions()
                .position(champaign)
                .title("Marker in Champaign"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(champaign));
    }
}