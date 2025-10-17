package com.example.quizapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS = "QuizAppPrefs";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USERNAME = "username";

    EditText etUsername, etPassword;
    Button btnLogin, btnToSignup;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToSignup = findViewById(R.id.btnToSignup);

        btnLogin.setOnClickListener(v -> {
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString().trim();
            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = db.loginUser(u, p);
            if (id != -1) {
                SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
                sp.edit().putInt(KEY_USER_ID, id).putString(KEY_USERNAME, u).apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        btnToSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }
}
