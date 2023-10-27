package edu.uiuc.cs427app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to pull the list of cities for a pre-existing user or create a new one.
 * Allows user to update the list by adding or removing cities.
 * Updates the app display when the city list is updated.
 */
public class CityManager {
    // SharedPreferences object to interact with the device's storage
    private SharedPreferences sharedPreferences;
    // Constant for the shared preferences file name
    private static final String PREFS_NAME = "user_data";
    // Set to store the list of city names
    private Set<String> cityList;
    // Key used to store and retrieve the city list from SharedPreferences, unique for each user
    private String cityListKey;

    /**
     * Constructor for a CityManager object that is used to display cities and keep track
     * of the user's city list.
     * @param context a unique key for the city list based on the username
     * @param username to get the city list from SharedPreferences, or initialize with an empty set if it doesn't exist
     */
    public CityManager(Context context, String username) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Create a unique key for the city list based on the username
        this.cityListKey = username + "_city_list";
        // Retrieve the city list from SharedPreferences, or initialize with an empty set if it doesn't exist
        this.cityList = new HashSet<>(sharedPreferences.getStringSet(cityListKey, new HashSet<>()));
    }

    /**
     * Returns list of cities.
     * @return city list as an array.
     */
    public ArrayList<String> getCityList() {
        return new ArrayList<>(cityList);
    }

    /**
     * Adds a city to the city list.
     * @param cityName city name as a String
     */
    public void addCity(String cityName) {
        cityList.add(cityName);
        updateCityListInPreferences();
    }

    /**
     * Removes a city from the city list.
     * @param cityName city name as a String.
     * @return 1 if city was successfully removed, 0 if unsuccessful.
     */
    public int removeCity(String cityName) {
        // If city found, return 1 for success
        if (cityList.contains(cityName)) {
            cityList.remove(cityName);
            updateCityListInPreferences();
            return 1;
        } else { // If city not found in list, return error
            return 0;
        }
    }

    /**
     * Returns city list count.
     * @return int
     */
    public int getSize() {
        return cityList.size();
    }

    /**
     * Updates the city list in SharedPreferences
     */
    private void updateCityListInPreferences() {
        // Get the editor to make changes to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Put the updated city list in SharedPreferences
        editor.putStringSet(cityListKey, cityList);
        editor.apply();
    }
}
