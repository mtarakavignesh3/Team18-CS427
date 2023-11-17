package edu.uiuc.cs427app;
import androidx.test.core.app.ActivityScenario;
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

@RunWith(AndroidJUnit4.class)
@MediumTest
public class WeatherActivityTest {
    @Before
    public void setup() {
        ActivityScenario.launch(WeatherActivity.class);
    }
    @Test
    public void testActivityInitialization() {
        onView(withId(R.id.welcomeText)).check(matches(isDisplayed()));
    }
    @Test
    public void testAddButtonClick() {
        onView(withId(R.id.weatherButtonAdd)).perform(click());
    }    @Test
    public void testCancelButtonClick() {
        onView(withId(R.id.weatherButtonCancel)).perform(click());
    }
}
