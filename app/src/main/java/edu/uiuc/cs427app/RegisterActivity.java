package edu.uiuc.cs427app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.accounts.AccountManager;
import android.accounts.Account;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

//    AccountManager mAccountManager = AccountManager.get(getBaseContext());

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Account[] accounts = mAccountManager.getAccountsByType("test_accounts");

        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView username = findViewById(R.id.register_username);
        TextView password = findViewById(R.id.register_password);

        Account newAccount = new Account("username", "test_accounts");
//        mAccountManager.addAccountExplicitly(newAccount, "password", null);
//        mAccountManager.setAuthToken(newAccount, "test_accounts", "auth_token");
    }
}
