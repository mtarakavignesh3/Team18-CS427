package edu.uiuc.cs427app;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
public class MapDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;// Google Map object
    //city name passed from the previous activity
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting the layout for this activity
        setContentView(R.layout.activity_map_detail);

        // Retrieving the city name passed from the MainActivity
        cityName = getIntent().getStringExtra("city");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //create and initialize a geo coder
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        Address address;
        // checking if the entered location is null or not.
        try {
            addresses = geocoder.getFromLocationName(cityName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                address = addresses.get(0);
                LatLng cityLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(cityLatLng).title(cityName));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLatLng, 10));

                TextView cityNameTextView = findViewById(R.id.CityName);
                cityNameTextView.setText(cityName);
                TextView latAndLongTextView = findViewById(R.id.LatitudeAndLongitude);
                latAndLongTextView.setText("Latitude: " + address.getLatitude() + "\n" + "Longitude: " + address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Enabling zoom controls on the map UI
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
