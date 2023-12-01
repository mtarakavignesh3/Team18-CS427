package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.accounts.Account;
import android.accounts.AccountManager;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test sign up with the following steps:
 * 1. Checking if the account doesn't exist yet
 * 2. Creating the account
 * 3. Checking if username and password are correct
 * 4. Deleting the account
 */
@RunWith(AndroidJUnit4.class)
public class SignUpTest {

    private AccountManager accountManager;
    @Before
    public void setup() {
        ActivityScenario.launch(RegisterActivity.class);
        accountManager = AccountManager.get(InstrumentationRegistry.getInstrumentation().getContext());
    }

    /**
     * Gets account from the account manager by username
     * @param name: username of the account to get
     */
    private Account getAccountByName(String name){
        Account returnedAccount = null;

        Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");
        for (Account account : accounts) {
            if (account.name.equals(name)){
                returnedAccount = account;
            }
        }

        return returnedAccount;
    }
    @Test
    public void testUserSignup() {
        String usernameTest = "test_user99";
        String passwordTest = "pw";

        // Test account does not exist yet
        Account beforeRegistration = this.getAccountByName(usernameTest);
        assertNull(beforeRegistration);

        onView(withId(R.id.register_username)).perform(typeText(usernameTest), closeSoftKeyboard());
        onView(withId(R.id.register_password)).perform(typeText(passwordTest), closeSoftKeyboard());

        // Introduce delay to observe the registration process
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.register_button)).perform(click());

        // Test account now exists
        Account afterRegistration = this.getAccountByName(usernameTest);
        assertEquals(afterRegistration.name, usernameTest);
        assertEquals(accountManager.getPassword(afterRegistration), passwordTest);

        /*
        // Cleanup values from test
        accountManager.removeAccountExplicitly(afterRegistration);
        */
    }
}