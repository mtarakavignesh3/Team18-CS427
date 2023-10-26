package edu.uiuc.cs427app;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.widget.SearchView;

import java.io.IOException;
import java.util.List;

/**
 * This activity displays a Google Map and provides functionality to search for cities and add a location.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;// Google Map object
    SearchView citySearch;// Search view for entering city names

    Marker marker;// Marker for selected city on the map

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialize the search view
        citySearch = (SearchView) findViewById(R.id.idSearchView);

        // Set a listener to handle city search queries
        citySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Search for the entered city name on the map
                searchCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // Initialize the map fragment to display the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // Set up a button to add the selected location
        Button buttonMapConfirm = findViewById(R.id.buttonMapAdd);
        Button buttonMapReturn = findViewById(R.id.buttonMapCancel);

        buttonMapConfirm.setOnClickListener(this);
        buttonMapReturn.setOnClickListener(this);

        mapFragment.getMapAsync(this);
    }

    /**
     *
     * @param cityName
     */
    private void searchCity(String cityName) {
        //create and initialize a geo coder
        Geocoder geocoder = new Geocoder(this);
        // Get the list of addresses for the given city name
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
                if(marker != null) {
                    marker.remove();
                }
                //add marker to this address
                marker = mMap.addMarker(new MarkerOptions().position(latLng).title(cityName));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10)); // zoom level 10 is typically city level zoom
            } /*else {
                // City not found
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * map is ready to be used and set the default address
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng champaign = new LatLng(40.1164, -88.2434);
        mMap.addMarker(new MarkerOptions()
                .position(champaign)
                .title("Marker in Champaign"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(champaign));
    }

    /**
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.buttonMapAdd) {
            Intent resultIntent = new Intent();// Intent to send the result back to the calling activity
            if(marker != null) {
                // Add the city name from the marker's title to the intent
                resultIntent.putExtra("city_name", marker.getTitle());
                setResult(RESULT_OK, resultIntent);
            }
            finish();// End the activity and return to the calling activity

        }

        // Ends the activity and returns to the calling activity
        if(view.getId() == R.id.buttonMapCancel) {
            finish();
        }
    }
}