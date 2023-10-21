package edu.uiuc.cs427app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountManager accountManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType("edu.uiuc.cs427app");

        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        TextView username = findViewById(R.id.register_username);
//        TextView password = findViewById(R.id.register_password);

        Account newAccount = new Account("username", "edu.uiuc.cs427app");
//        accountManager.addAccountExplicitly(newAccount, "password", null);
//        accountManager.setAuthToken(newAccount, "test_accounts", "auth_token");

        Toast.makeText(RegisterActivity.this,
                "Account Registered", Toast.LENGTH_LONG).show();
    }
}
