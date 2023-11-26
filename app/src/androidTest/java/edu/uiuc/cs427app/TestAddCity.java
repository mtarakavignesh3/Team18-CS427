package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;

import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;


import static org.hamcrest.CoreMatchers.allOf;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestAddCity {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

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

        onView(withId(R.id.buttonMapAdd)).perform(click());

        onView(allOf(withId(R.id.cityName), withText("Santa Monica")))
                .check(matches(isDisplayed()));
    }
}
