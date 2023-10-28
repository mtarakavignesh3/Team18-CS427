package edu.uiuc.cs427app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountManager accountManager;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Switch switchView;
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
        switchView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(view.getId() == R.id.register_button){
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Missing Fields: Please type a username and a password", Toast.LENGTH_SHORT).show();
            } else {
                Account newAccount = new Account(username, "edu.uiuc.cs427app");
                boolean accountAdded = accountManager.addAccountExplicitly(newAccount, password, null);
                if (accountAdded) {
                    Toast.makeText(this, "Account Registered", Toast.LENGTH_SHORT).show();
                    addUserToSharedPreferences(username);
                    finish(); // Close the registration activity
                } else {
                    Toast.makeText(this, "Account already exist", Toast.LENGTH_SHORT).show();
                }
            }
        }

        // handles theme
        if(view.getId() == R.id.id_switch){
            if (switchView.isChecked()) {
                Log.i("[DEBUG]", "switch on!");
            } else {
                Log.i("[DEBUG]", "switch off!");
            }
        }

    }


    // Adds an empty user to the username
    public void addUserToSharedPreferences(String username) {
        // Save User data to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.userDataFileName), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        User user = new User(username);
        String json = gson.toJson(user);
        editor.putString(username, json);
        editor.apply();
    }
}