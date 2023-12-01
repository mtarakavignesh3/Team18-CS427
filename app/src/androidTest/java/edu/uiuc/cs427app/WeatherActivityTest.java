package edu.uiuc.cs427app;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import android.content.Intent;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class WeatherActivityTest {
    @Before
    public void setup() {
        ActivityScenario.launch(WeatherActivity.class);
    }
    @Test
    public void testWeatherActivityInitialization() {
        onView(withId(R.id.welcomeText)).check(matches(isDisplayed()));
        onView(withId(R.id.cityInfo)).check(matches(isDisplayed()));
        onView(withId(R.id.weatherText)).check(matches(isDisplayed()));
    }

    @Test
    public void testCancelButtonClick() {
        onView(withId(R.id.weatherButtonCancel)).check(matches(isDisplayed()));
        onView(withId(R.id.weatherButtonCancel)).perform(click());
        // Adding a sleep of 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWeatherForFirstCity() {
        String firstCity = "New York City";
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), WeatherActivity.class);
        intent.putExtra("city", firstCity);
        ActivityScenario.launch(intent);
        onView(withId(R.id.welcomeText)).check(matches(withText(containsString(firstCity.toUpperCase()))));
        // Adding a sleep of 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testWeatherForSecondCity() {
        String firstCity = "Seattle";
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), WeatherActivity.class);
        intent.putExtra("city", firstCity);
        ActivityScenario.launch(intent);
        onView(withId(R.id.welcomeText)).check(matches(withText(containsString(firstCity.toUpperCase()))));
    }
}
