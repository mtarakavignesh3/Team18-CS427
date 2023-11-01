package edu.uiuc.cs427app;

/**
 * An interface that allows CityCustomAdapter to communicate the city to be removed to MainActivity.
 */
public interface CityInterface {
    /**
     * Method implemented by MainActivity
     * @param city
     */
    public void removeCity(String city);
    public void getCityMap(String city);
    public void getCityWeather(String city);
}
