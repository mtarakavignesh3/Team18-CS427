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
 * Class for the login activity.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountManager accountManager;
    private EditText usernameEditText;
    private EditText passwordEditText;

    /**
     * Launches this activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager = AccountManager.get(this);
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);

        Button loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(this);

        Button createAccountBtn = findViewById(R.id.create_an_account_button);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Launches registration activity.
             * @param view
             */
            @Override
            public void onClick(View view) {
                registerActivity();
            }
        });
    }

    /**
     * Launches the registration activity.
     */
    private void registerActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the main activity.
     * @param username
     */
    private void gotoMainActivity(String username) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Login attempts are checked here. Generates Toast messages depending on outcome.
     * @param view
     */
    @Override
    public void onClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Boolean accountFound = false;
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Missing Fields: Please type a username and a password", Toast.LENGTH_SHORT).show();
            return;
        }
        Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");
        for (Account account : accounts) {
            if (account.name.equals(username)) {
                accountFound = true;
                String accountPassword = accountManager.getPassword(account);
                if(password.equals(accountPassword)) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    login(username);
                    gotoMainActivity(account.name);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        if (!accountFound) {
            Toast.makeText(LoginActivity.this, "Account not found. Please register.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Save User data to SharedPreferences
     * @param username
     */
    private void login(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.userDataFileName), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonUser = sharedPreferences.getString(username, "");
        if (!jsonUser.isEmpty()) {
            User user = gson.fromJson(jsonUser, User.class);
            applyTheme(user.getTheme());
        } else {
            applyTheme("Light");
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.currentUserVariable), username);
        editor.apply();
    }

    private void applyTheme(String theme) {
        Log.i("[DEBUG]Theme: ", theme);
        if ("Dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if ("Light".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}
