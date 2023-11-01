package edu.uiuc.cs427app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;

/**
 * The LoginActivity class represents the login screen of the application.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private AccountManager accountManager;
    private EditText usernameEditText;
    private EditText passwordEditText;

    /**
     * This method is called when the LoginActivity is created.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager = AccountManager.get(this);
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);

        // Initialize the login and create account buttons.
        Button loginBtn = findViewById(R.id.login_button);
        Button createAccountBtn = findViewById(R.id.create_an_account_button);

        // Set click listeners.
        loginBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);
    }

    /**
     * Launches the registration activity when the "Create an Account" button is clicked.
     */
    private void registerActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the main activity after successful login.
     * @param username The username of the logged-in user.
     */
    private void gotoMainActivity(String username) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Handles login attempts and provides feedback through Toast messages.
     * @param view The view that triggered the login attempt.
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.create_an_account_button:
                registerActivity();
                break;

            case R.id.login_button:
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Boolean accountFound = false;

                if (username.isEmpty() || password.isEmpty()) {
                    // Display a Toast message if the user doesn't provide a username or password.
                    Toast.makeText(this, "Missing Fields: Please type a username and a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");

                for (Account account : accounts) {
                    String accountName = account.name;

                    if (account.name.equals(username)) {
                        accountFound = true;
                        String accountPassword = accountManager.getPassword(account);

                        if(password.equals(accountPassword)) {
                            // Display a Toast message for a successful login.
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                            // Perform user login and launch the main activity.
                            login(username);

                            gotoMainActivity(username);
                            break;
                            //finish();
                        } else {
                            // Display a Toast message for a failed login attempt.
                            Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    /**
     * Saves user data to SharedPreferences after a successful login.
     * @param username The username of the logged-in user.
     */
    private void login(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.userDataFileName), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonUser = sharedPreferences.getString(username, "");

        if (!jsonUser.isEmpty()) {
            User user = gson.fromJson(jsonUser, User.class);
            // Apply the user's selected theme.
            applyTheme(user.getTheme());
        } else {
            // Apply the default "Light" theme.
            applyTheme("Light");
        }

        // Store the current user's username in SharedPreferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.currentUserVariable), username);
        editor.apply();
    }

    /**
     * Applies the selected theme to the app based on the user's preference.
     * @param theme The theme to apply (e.g., "Dark" or "Light").
     */
    private void applyTheme(String theme) {
        Log.i("[DEBUG]Theme: ", theme);

        if ("Dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if ("Light".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}