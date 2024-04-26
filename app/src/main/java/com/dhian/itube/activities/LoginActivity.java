package com.dhian.itube.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.dhian.itube.R;
import com.dhian.itube.User;
import com.dhian.itube.UserController;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignUp;

    private EditText etUsername, etPassword;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsernameLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        userController = new UserController(LoginActivity.this);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etUsername.setError(null);
                etPassword.setError(null);
                String title = etUsername.getText().toString(),
                        description = etPassword.getText().toString();
                if ("".equals(title)) {
                    etUsername.setError("Enter username");
                    etUsername.requestFocus();
                    return;
                }
                if ("".equals(description)) {
                    etPassword.setError("Enter password");
                    etPassword.requestFocus();
                    return;
                }

                User newTask = new User(title, description);
                long id = userController.login(newTask);
                if (id == -1) {
                    Snackbar.make(v, "Error login. Try again", Snackbar.LENGTH_LONG)
                            .setAnchorView(R.id.btnLogin)
                            .setAction("Action", null).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("userId", String.valueOf(id));
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }


}
