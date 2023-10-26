package edu.uiuc.cs427app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The activity that displays the city list.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> cityList;
    private CityManager cityManager;

    // Adapter to bridge data between the city list and the ListView UI component
    private ArrayAdapter<String> adapter;
    private static final int ADD_LOCATION_REQUEST = 1;

    private String username;

    /**
     * This method is called when the activity is first created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = "yujia";

        super.onCreate(savedInstanceState);
        this.setTitle("Team18-" + username);
        setContentView(R.layout.activity_main);// Set the layout for this activity

        cityList = new ArrayList<>();
        cityManager = new CityManager(this, username);// Initialize the city manager with a user name
        cityList = cityManager.getCityList();// get the list of cities from the city manager

        // Find the ListView by its ID and set up the adapter for it
        ListView listView = findViewById(R.id.city_ListView);
        listView.setAdapter(new CityCustomAdapter(cityList, this));

        // Sets up the Add A Location button
        Button buttonNew = findViewById(R.id.buttonAddLocation);
        buttonNew.setOnClickListener(this);
    }

    /**
     * update the city list and notify the adapter of the data change
     */
    private void updateCityList() {
        cityList.clear();
        cityList.addAll(cityManager.getCityList());
        adapter.notifyDataSetChanged();
    }

    /**
     *
     * @param view
     */
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

    /**
     * Callback method for receiving results from activities started for result
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                    Toast.makeText(this, "City added!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}

