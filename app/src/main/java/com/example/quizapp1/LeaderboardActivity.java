package com.example.quizapp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    ListView lv;
    Button btnBackToCategory;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        lv = findViewById(R.id.listLeaderboard);
        btnBackToCategory = findViewById(R.id.btnBackToCategory);
        db = new DBHelper(this);

        load();

        // ✅ Navigate back to Category/Menu activity
        btnBackToCategory.setOnClickListener(v -> {
            Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void load() {
        List<String> data = db.getLeaderboardList();
        if (data == null || data.isEmpty()) data.add("No scores yet");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);
    }
}
