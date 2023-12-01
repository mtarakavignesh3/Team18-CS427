package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    private AccountManager accountManager;

    @Before
    public void setup() {
        // Launch the LoginActivity
        ActivityScenario.launch(LoginActivity.class);
        accountManager = AccountManager.get(InstrumentationRegistry.getInstrumentation().getContext());

        // Initialize Espresso Intents
        Intents.init();
    }

    /**
     * Gets account from the account manager by username
     * @param name: username of the account to get
     */
    private Account getAccountByName(String name) {
        Account returnedAccount = null;

        Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");
        for (Account account : accounts) {
            if (account.name.equals(name)) {
                returnedAccount = account;
            }
        }

        return returnedAccount;
    }

    @After
    public void tearDown() {
        // Release Espresso Intents
        Intents.release();
    }

    @Test
    public void testUserLogin() {
        String usernameTest = "test_user99";
        String passwordTest = "pw";

        // Ensure the account exists before login
        Account accountBeforeLogin = this.getAccountByName(usernameTest);
        assertNotNull(accountBeforeLogin);

        // Perform login actions
        onView(withId(R.id.login_username)).perform(typeText(usernameTest), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(passwordTest), closeSoftKeyboard());

        onView(withId(R.id.login_button)).perform(click());

        // Check if the Intent to open MainActivity is sent
        intended(hasComponent(MainActivity.class.getName()));

        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
