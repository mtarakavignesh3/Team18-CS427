package edu.uiuc.cs427app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountManager accountManager;
    private EditText usernameEditText;
    private EditText passwordEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountManager = AccountManager.get(this);
        usernameEditText = findViewById(R.id.register_username);
        passwordEditText = findViewById(R.id.register_password);

        Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Missing Fields: Please type a username and a password", Toast.LENGTH_SHORT).show();
        } else {
            Account newAccount = new Account(username, "edu.uiuc.cs427app");
            boolean accountAdded = accountManager.addAccountExplicitly(newAccount, password, null);
            if (accountAdded) {
                Toast.makeText(this, "Account Registered", Toast.LENGTH_SHORT).show();
                finish(); // Close the registration activity
            } else {
                Toast.makeText(this, "Failed to register the account", Toast.LENGTH_SHORT).show();
            }
        }
    }
}