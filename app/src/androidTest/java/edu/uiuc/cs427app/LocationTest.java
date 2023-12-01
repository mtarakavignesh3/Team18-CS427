package edu.uiuc.cs427app;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;

import android.content.Intent;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class LocationTest {
    @Before
    public void setup() {
        ActivityScenario.launch(MapsActivity.class);
    }
    @Test
    public void testMapForFirstCity() {
        String firstCity = "New York City";
        String latAndLon = "Latitude: 40.7127753\nLongitude: -74.0059728";
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapDetailActivity.class);
        intent.putExtra("city", firstCity);
        ActivityScenario.launch(intent);
        onView(withId(R.id.CityName)).check(matches(withText(containsString(firstCity.toUpperCase()))));
        onView(withId(R.id.LatitudeAndLongitude)).check(matches(withText(containsString(latAndLon))));
    }
    @Test
    public void testMapForSecondCity() {
        String firstCity = "Seattle";
        String latAndLon = "Latitude: 47.6061389\nLongitude: -122.33284809999999";
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapDetailActivity.class);
        intent.putExtra("city", firstCity);
        ActivityScenario.launch(intent);
        onView(withId(R.id.CityName)).check(matches(withText(containsString(firstCity.toUpperCase()))));
        onView(withId(R.id.LatitudeAndLongitude)).check(matches(withText(containsString(latAndLon))));
    }
}
