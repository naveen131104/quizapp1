package com.example.quizapp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    Button btnGeneral, btnSports, btnScience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btnGeneral = findViewById(R.id.btnGeneral);
        btnSports = findViewById(R.id.btnSports);
        btnScience = findViewById(R.id.btnScience);

        btnGeneral.setOnClickListener(v -> openQuiz("General"));
        btnSports.setOnClickListener(v -> openQuiz("Sports"));
        btnScience.setOnClickListener(v -> openQuiz("Science"));
    }

    private void openQuiz(String category) {
        Intent i = new Intent(CategoryActivity.this, QuizActivity.class);
        i.putExtra("CATEGORY", category);
        startActivity(i);
    }
}
