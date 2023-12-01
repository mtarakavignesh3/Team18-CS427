package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;


import static org.hamcrest.CoreMatchers.allOf;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddRemoveCityTest {
    //@Rule
    //public ActivityScenarioRule<MainActivity> activityRule =
    //        new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        // Assuming the user is not logged in initially
        // You might need to adjust this based on your actual app's behavior
        ActivityScenario.launch(LoginActivity.class);
        Intents.init();

        // Perform login actions
        onView(withId(R.id.login_username)).perform(typeText("test_user100"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("pw"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
    }

    @After
    public void tearDown() {
        onView(withId(R.id.buttonLogout)).perform(click());
        // Release Espresso Intents
        Intents.release();
    }

    @Test
    public void loginOpensMainActivity() {
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void addNewCity() throws InterruptedException {
        onView(withId(R.id.buttonAddLocation)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.idSearchView)).perform(click());

        onView(allOf(withId(androidx.appcompat.R.id.search_src_text),
                isDescendantOfA(withId(R.id.idSearchView)),
                isDisplayed()))
                .perform(replaceText("Santa Monica"), closeSoftKeyboard());

        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(pressImeActionButton());

        Thread.sleep(2000);

        onView(withId(R.id.buttonMapAdd)).perform(click());

        onView(allOf(withId(R.id.cityName), withText("Santa Monica")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void removeCity() throws InterruptedException {
        //Add Seattle
        onView(withId(R.id.buttonAddLocation)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.idSearchView)).perform(click());

        onView(allOf(withId(androidx.appcompat.R.id.search_src_text),
                isDescendantOfA(withId(R.id.idSearchView)),
                isDisplayed()))
                .perform(replaceText("Seattle"), closeSoftKeyboard());

        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(pressImeActionButton());

        Thread.sleep(2000);

        onView(withId(R.id.buttonMapAdd)).perform(click());

        onView(allOf(withId(R.id.cityName), withText("Seattle")))
                .check(matches(isDisplayed()));

        //Remove Seattle
        //onView(allOf(withId(R.id.button_delete), withText("Seattle"))).perform(click());
        onView(allOf(isDescendantOfA(withId(R.id.city_ListView)),
                isDescendantOfA(withChild(withText("Seattle"))),
                //hasSibling(withText("Seattle")),
                withId(R.id.button_delete))).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(allOf(isDescendantOfA(withId(R.id.city_ListView)),
                isDescendantOfA(withChild(withText("Seattle")))))
                .check(doesNotExist());
    }
}