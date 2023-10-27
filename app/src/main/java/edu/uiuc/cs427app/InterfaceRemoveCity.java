package edu.uiuc.cs427app;

/**
 * An interface that allows CityCustomAdapter to communicate the city to be removed to MainActivity.
 */
public interface InterfaceRemoveCity {
    /**
     * Method implemented by MainActivity
     * @param city
     */
    public void removeCity(String city);
}
