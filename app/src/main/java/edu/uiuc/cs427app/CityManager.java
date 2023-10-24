package edu.uiuc.cs427app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CityManager {
    // SharedPreferences object to interact with the device's storage
    private SharedPreferences sharedPreferences;
    // Constant for the shared preferences file name
    private static final String PREFS_NAME = "user_data";
    // Set to store the list of city names
    private Set<String> cityList;
    // Key used to store and retrieve the city list from SharedPreferences, unique for each user
    private String cityListKey;

    public CityManager(Context context, String username) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Create a unique key for the city list based on the username
        this.cityListKey = username + "_city_list";
        // Retrieve the city list from SharedPreferences, or initialize with an empty set if it doesn't exist
        this.cityList = new HashSet<>(sharedPreferences.getStringSet(cityListKey, new HashSet<>()));

    }

    public ArrayList<String> getCityList() {
        return new ArrayList<>(cityList);
    }

    public void addCity(String cityName) {
        cityList.add(cityName);
        updateCityListInPreferences();
    }
    public void removeCity(String cityName) {
        cityList.remove(cityName);
        updateCityListInPreferences();
    }

    public int getSize() {
        return cityList.size();
    }


    //update the city list in SharedPreferences
    private void updateCityListInPreferences() {
        // Get the editor to make changes to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Put the updated city list in SharedPreferences
        editor.putStringSet(cityListKey, cityList);
        editor.apply();
    }

}
