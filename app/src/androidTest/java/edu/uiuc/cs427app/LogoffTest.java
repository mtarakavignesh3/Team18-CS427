package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import androidx.test.espresso.intent.Intents;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class LogoffTest {

    @Before
    public void setup() {
        // Assuming the user is not logged in initially
        // You might need to adjust this based on your actual app's behavior
        ActivityScenario.launch(LoginActivity.class);
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Espresso Intents
        Intents.release();
    }

    @Test
    public void testLogoff() {
        // Perform login actions
        onView(withId(R.id.login_username)).perform(typeText("test_user100"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("pw"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        // Verify if the app navigates to the MainActivity
       // onView(withId(R.id.buttonLogout)).check(matches(isDisplayed()));
        intended(hasComponent(MainActivity.class.getName()));
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on the logout button
        onView(withId(R.id.buttonLogout)).perform(click());

        // Verify if the app navigates back to the LoginActivity
        //onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        intended(hasComponent(LoginActivity.class.getName()));
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
