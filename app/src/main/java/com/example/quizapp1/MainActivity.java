package com.example.quizapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout loggedOutMenu, loggedInMenu;
    Button btnLogin, btnSignup;
    Button btnStartQuiz, btnLeaderboard, btnLogout;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggedOutMenu = findViewById(R.id.loggedOutMenu);
        loggedInMenu = findViewById(R.id.loggedInMenu);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);
        btnLeaderboard = findViewById(R.id.btnLeaderboard);
        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);

        SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
        int userId = sp.getInt(LoginActivity.KEY_USER_ID, -1);
        String username = sp.getString(LoginActivity.KEY_USERNAME, null);

        if (userId == -1) {
            loggedOutMenu.setVisibility(View.VISIBLE);
            loggedInMenu.setVisibility(View.GONE);
            tvWelcome.setText("Welcome! Please login or signup.");
        } else {
            loggedOutMenu.setVisibility(View.GONE);
            loggedInMenu.setVisibility(View.VISIBLE);
            tvWelcome.setText("Welcome, " + username + "!");
        }

        btnLogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        btnSignup.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SignupActivity.class)));

        btnStartQuiz.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CategoryActivity.class)));
        btnLeaderboard.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LeaderboardActivity.class)));
        btnLogout.setOnClickListener(v -> {
            sp.edit().remove(LoginActivity.KEY_USER_ID).remove(LoginActivity.KEY_USERNAME).apply();
            // refresh activity to show logged-out menu
            recreate();
        });
    }
}
