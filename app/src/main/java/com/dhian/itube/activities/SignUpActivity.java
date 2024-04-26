package com.dhian.itube.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.dhian.itube.R;
import com.dhian.itube.User;
import com.dhian.itube.UserController;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnCreateAccount;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        userController = new UserController(SignUpActivity.this);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                etFullName.setError(null);
                etUsername.setError(null);
                String title = etFullName.getText().toString(),
                        description = etUsername.getText().toString();
                if ("".equals(title)) {
                    etFullName.setError("Enter Full name");
                    etFullName.requestFocus();
                    return;
                }
                if ("".equals(description)) {
                    etUsername.setError("Enter Username");
                    etUsername.requestFocus();
                    return;
                }
                String password = etPassword.getText().toString();
                if ("".equals(password)) {
                    etPassword.setFocusable(true);
                    etPassword.setError("Enter Password");
                    etPassword.requestFocus();
                    return;
                }

                String confirmPassword = etConfirmPassword.getText().toString();
                if ("".equals(confirmPassword)) {
                    etConfirmPassword.setFocusable(true);
                    etConfirmPassword.setError("Confirm Password");
                    etConfirmPassword.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    etPassword.setFocusable(true);
                    etPassword.setError("Passwords do not match");
                    etPassword.requestFocus();
                    etConfirmPassword.setFocusable(true);
                    etConfirmPassword.setError("Passwords do not match");
                    etConfirmPassword.requestFocus();
                    return;
                }
                User data = new User(title, description, password);
                long id = userController.newUser(data);
                if (id == -1) {
                    Snackbar.make(v, "Error saving. Try again", Snackbar.LENGTH_LONG)
                            .setAnchorView(R.id.btnCreateAccount)
                            .setAction("Action", null).show();
                } else {
                    finish();
                }
            }
        });


    }


}
