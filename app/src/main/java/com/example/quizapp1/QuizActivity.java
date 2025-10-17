package com.example.quizapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView txtCategory, txtQuestion;
    RadioGroup rg;
    RadioButton r1,r2,r3,r4;
    Button btnNext;

    List<Question> questions;
    int current = 0;
    int score = 0;
    String category;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtCategory = findViewById(R.id.txtCategory);
        txtQuestion = findViewById(R.id.txtQuestion);
        rg = findViewById(R.id.optionsGroup);
        r1 = findViewById(R.id.opt1);
        r2 = findViewById(R.id.opt2);
        r3 = findViewById(R.id.opt3);
        r4 = findViewById(R.id.opt4);
        btnNext = findViewById(R.id.btnNext);

        db = new DBHelper(this);
        category = getIntent().getStringExtra("CATEGORY");
        txtCategory.setText("Category: " + category);

        questions = db.getQuestionsByCategory(category); // shuffled up to 5

        if (questions == null || questions.isEmpty()) {
            Toast.makeText(this, "No questions found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        showQuestion();

        btnNext.setOnClickListener(v -> {
            int sel = rg.getCheckedRadioButtonId();
            if (sel == -1) {
                Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton chosen = findViewById(sel);
            if (chosen.getText().toString().equals(questions.get(current).getAnswer())) score++;
            current++;
            if (current < questions.size()) {
                showQuestion();
            } else {
                // save score if user logged in
                SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
                int userId = sp.getInt(LoginActivity.KEY_USER_ID, -1);
                if (userId != -1) db.saveScore(userId, category, score);

                // Show toast and return to MainActivity
                Toast.makeText(QuizActivity.this, "Your Score: " + score + " / " + questions.size(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(QuizActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    private void showQuestion() {
        rg.clearCheck();
        Question q = questions.get(current);
        txtQuestion.setText(q.getQuestion());
        r1.setText(q.getOption1());
        r2.setText(q.getOption2());
        r3.setText(q.getOption3());
        r4.setText(q.getOption4());
    }
}
