package com.example.quizapp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etEmail;
    Button btnSignup, btnToLogin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = new DBHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSignup = findViewById(R.id.btnSignup);
        btnToLogin = findViewById(R.id.btnToLogin);

        btnSignup.setOnClickListener(v -> {
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString().trim();
            String e = etEmail.getText().toString().trim();
            if (u.isEmpty() || p.isEmpty() || e.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok = db.registerUser(u, p, e);
            if (ok) {
                Toast.makeText(SignupActivity.this, "Registered. Please login.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(SignupActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
            }
        });

        btnToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }
}
