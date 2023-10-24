package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> cityList;
    private CityManager cityManager;

    // Adapter to bridge data between the city list and the ListView UI component
    private ArrayAdapter<String> adapter;
    private static final int ADD_LOCATION_REQUEST = 1;

    //This method is called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// Set the layout for this activity
        cityList = new ArrayList<>();
        cityManager = new CityManager(this, "yujia");// Initialize the city manager with a user name
        cityList = cityManager.getCityList();// get the list of cities from the city manager

        // Find the ListView by its ID and set up the adapter for it
        ListView listView = findViewById(R.id.city_ListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityList);
        listView.setAdapter(adapter);

        Button buttonNew = findViewById(R.id.buttonAddLocation);

        buttonNew.setOnClickListener(this);
    }

    //update the city list and notify the adapter of the data change
    private void updateCityList() {
        cityList.clear();
        cityList.addAll(cityManager.getCityList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonAddLocation:
                // Start the MapsActivity to add a new location
                intent = new Intent(this, MapsActivity.class);
                startActivityForResult(intent, ADD_LOCATION_REQUEST);
                break;
        }
    }

    // Callback method for receiving results from activities started for result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if the result is from the MapsActivity and if it was successful
        if(requestCode == ADD_LOCATION_REQUEST) {
            if(resultCode == RESULT_OK && data != null) {
                // Retrieve the city name from the result
                String cityName = data.getStringExtra("city_name");
                if(cityName != null && !cityName.isEmpty()) {
                    // Add the city to the user_data, update the city list
                    cityManager.addCity(cityName);
                    updateCityList();
                    Toast.makeText(this, "successfully add city", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}

