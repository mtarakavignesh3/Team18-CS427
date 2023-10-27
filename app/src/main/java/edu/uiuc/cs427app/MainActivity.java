package edu.uiuc.cs427app;

import android.accounts.Account;
import android.accounts.AccountManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

/**
 * The activity that displays the city list.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, InterfaceRemoveCity {
    private ArrayList<String> cityList;
    private CityManager cityManager;

    // Adapter to bridge data between the city list and the ListView UI component
    static CityCustomAdapter adapter;
    private static final int ADD_LOCATION_REQUEST = 1;

    private String username;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;

    /**
     * This method is called when the activity is first created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve User data from SharedPreferences
        sharedPreferences = getSharedPreferences(getString(R.string.userDataFileName), Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(getString(R.string.currentUserVariable), null);
        String getJson = sharedPreferences.getString(userId, null);

        Gson gson = new Gson();
        if (getJson == null) {
            goToLoginActivity();
        }

        User currUser = gson.fromJson(getJson, User.class);
        username = userId;
        List<String> locations = currUser.getSavedPlaces();

        //getSupportActionBar().setTitle(getString(R.string.app_name) + "-" + userId);

        // Initializing the UI components
        // The list of locations should be customized per user (change the implementation so that
        // buttons are added to layout programmatically

        this.setTitle("Team18-" + username);
        //setContentView(R.layout.activity_main);// Set the layout for this activity

        cityList = (ArrayList<String>) locations;
        cityManager = new CityManager(this, username);// Initialize the city manager with a user name
        cityList = cityManager.getCityList();// get the list of cities from the city manager

        // Find the ListView by its ID and set up the adapter for it
        ListView listView = findViewById(R.id.city_ListView);
        adapter = new CityCustomAdapter(cityList, this, this);
        listView.setAdapter(adapter);

        // Sets up the Add A Location button
        Button buttonNew = findViewById(R.id.buttonAddLocation);
        buttonNew.setOnClickListener(this);

        // Set up a logout button
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
    }

    /**
     * Update the city list and notify the adapter of the data change
     */
    private void updateCityList() {
        cityList.clear();
        cityList.addAll(cityManager.getCityList());
        adapter.notifyDataSetChanged();
    }

    /**
     * Used to remove a city from CityManager and notify the adapter of the data change
     * @param city
     */
    @Override
    public void removeCity(String city) {
        cityManager.removeCity(city);
        updateCityList();
    }

    /**
     * Start the MapsActivity to add a new location
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonAddLocation:
                intent = new Intent(this, MapsActivity.class);
                startActivityForResult(intent, ADD_LOCATION_REQUEST);
                break;
            case R.id.buttonLogout:
                logOut();
                break;
        }
    }

    public void goToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void logOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.currentUserVariable), "");
        editor.apply();

        goToLoginActivity();
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

