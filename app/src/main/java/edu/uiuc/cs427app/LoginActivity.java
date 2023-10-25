package edu.uiuc.cs427app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountManager accountManager;
    private EditText usernameEditText;
    private EditText passwordEditText;

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
            @Override
            public void onClick(View view) {
                registerActivity();
            }
        });
    }

    private void registerActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void GotoMainActivity(String username) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userId", username);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Missing Fields: Please type a username and a password", Toast.LENGTH_SHORT).show();
        } else {
            // Authenticate the user
            Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");
            for (Account account : accounts) {
                if (account.name.equals(username)) {
                    String accountPassword = accountManager.getPassword(account);
                    if (password.equals(accountPassword)) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        GotoMainActivity(account.name);
                        finish();
                        break;
                    }
                    // Authentication failed
                    Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
