package edu.uiuc.cs427app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountManager accountManager;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Switch switchView;
    private SharedPreferences sharedPreferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountManager = AccountManager.get(this);
        usernameEditText = findViewById(R.id.register_username);
        passwordEditText = findViewById(R.id.register_password);

        switchView = findViewById(R.id.id_switch);
        if (switchView.isChecked()) {
            Log.i("NumberGenerated", "Theme 1.");
        } else {
            Log.i("NumberGenerated", "Theme 2");
        }
        Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Theme", "Dark theme selected.");
                } else {
                    Log.i("Theme", "Light theme selected.");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean accountAdded = false;
        boolean isThemeDark = switchView.isChecked();

        if (view.getId() == R.id.register_button) {
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Missing Fields: Please type a username and a password", Toast.LENGTH_SHORT).show();
            } else {
                Account newAccount = new Account(username, "edu.uiuc.cs427app");
                accountAdded = accountManager.addAccountExplicitly(newAccount, password, null);
                if (accountAdded) {
                    Toast.makeText(this, "Account Registered", Toast.LENGTH_SHORT).show();
                    saveThemeToSharedPreferences(isThemeDark); // updated this call
                    applyTheme(isThemeDark ? "Dark" : "Light");
                    addUserToSharedPreferences(username, isThemeDark);
                    finish(); // Close the registration activity
                } else {
                    Toast.makeText(this, "Account already exists", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveThemeToSharedPreferences(Boolean isThemeDark) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.userDataFileName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isThemeDark) {
            editor.putString(getString(R.string.themePreferenceKey), "Dark");
        } else {
            editor.putString(getString(R.string.themePreferenceKey), "Light");
        }
        editor.apply();
    }

    private void addUserToSharedPreferences(String username, boolean isThemeDark) {
        User newUser = new User(username, isThemeDark ? "Dark" : "Light");
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.userDataFileName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonUser = gson.toJson(newUser);
        editor.putString(username, jsonUser);
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