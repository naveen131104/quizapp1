package com.example.quizapp1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quiz_app.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "email TEXT)");

        db.execSQL("CREATE TABLE scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "category TEXT, " +
                "score INTEGER, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(userId) REFERENCES users(id))");

        db.execSQL("CREATE TABLE questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category TEXT, " +
                "question TEXT, " +
                "option1 TEXT, " +
                "option2 TEXT, " +
                "option3 TEXT, " +
                "option4 TEXT, " +
                "answer TEXT)");

        insertSampleQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS scores");
        db.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(db);
    }

    private void insertSampleQuestions(SQLiteDatabase db) {
        addQuestion(db, "General","What is the capital of India?","Delhi","Mumbai","Kolkata","Chennai","Delhi");
        addQuestion(db, "General","Which festival is called the Festival of Lights?","Diwali","Holi","Eid","Christmas","Diwali");
        addQuestion(db, "General","Largest ocean on Earth?","Atlantic","Indian","Pacific","Arctic","Pacific");
        addQuestion(db, "General","Who invented the telephone?","Newton","Edison","Bell","Tesla","Bell");
        addQuestion(db, "General","Smallest planet in the Solar System?","Mars","Mercury","Venus","Pluto","Mercury");
        addQuestion(db, "General","Who wrote the Ramayana?","Valmiki","Vyas","Kalidas","Tulsidas","Valmiki");
        addQuestion(db, "General","National bird of India?","Crow","Peacock","Sparrow","Parrot","Peacock");
        addQuestion(db, "General","Fastest land animal?","Cheetah","Lion","Tiger","Horse","Cheetah");
        addQuestion(db, "General","Who is called the Father of the Nation of India?","Gandhi","Nehru","Tagore","Ambedkar","Gandhi");
        addQuestion(db, "General","What is H2O commonly called?","Oxygen","Water","Hydrogen","Salt","Water");

        addQuestion(db, "Sports","How many players in a football team on the field?","9","10","11","12","11");
        addQuestion(db, "Sports","How many rings in the Olympic logo?","4","5","6","7","5");
        addQuestion(db, "Sports","T20 cricket overs per side?","10","20","30","50","20");
        addQuestion(db, "Sports","Wimbledon is associated with which sport?","Cricket","Football","Tennis","Badminton","Tennis");
        addQuestion(db, "Sports","Who won the 2011 Cricket World Cup?","Australia","India","Sri Lanka","England","India");
        addQuestion(db, "Sports","How many players in a basketball team on court?","4","5","6","7","5");
        addQuestion(db, "Sports","First modern Olympics were held in?","Rome","Athens","Paris","London","Athens");
        addQuestion(db, "Sports","Which country invented Baseball?","USA","Japan","UK","Canada","USA");
        addQuestion(db, "Sports","Which sport uses a shuttlecock?","Tennis","Badminton","Cricket","Squash","Badminton");
        addQuestion(db, "Sports","Who is often called 'God of Cricket'?","Dhoni","Kohli","Tendulkar","Dravid","Tendulkar");

        addQuestion(db, "Science","Which planet is called the Red Planet?","Earth","Mars","Venus","Jupiter","Mars");
        addQuestion(db, "Science","DNA stands for?","Deoxyribonucleic acid","Ribonucleic acid","Deoxyribonucleotide","None","Deoxyribonucleic acid");
        addQuestion(db, "Science","Approx speed of light?","3x10^6 m/s","3x10^8 m/s","3000 m/s","300 m/s","3x10^8 m/s");
        addQuestion(db, "Science","Which gas is produced by plants?","Oxygen","Carbon Dioxide","Methane","Nitrogen","Oxygen");
        addQuestion(db, "Science","SI unit of force?","Pascal","Newton","Watt","Joule","Newton");
        addQuestion(db, "Science","Boiling point of water at sea level?","90°C","100°C","110°C","120°C","100°C");
        addQuestion(db, "Science","Who proposed the theory of relativity?","Newton","Einstein","Galileo","Kepler","Einstein");
        addQuestion(db, "Science","Which particle has negative charge?","Proton","Electron","Neutron","Photon","Electron");
        addQuestion(db, "Science","Chemical symbol for Gold?","Au","Ag","Gd","Go","Au");
        addQuestion(db, "Science","Which instrument measures temperature?","Barometer","Thermometer","Hygrometer","Anemometer","Thermometer");
    }

    private void addQuestion(SQLiteDatabase db, String category, String q, String o1, String o2, String o3, String o4, String ans) {
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("question", q);
        cv.put("option1", o1);
        cv.put("option2", o2);
        cv.put("option3", o3);
        cv.put("option4", o4);
        cv.put("answer", ans);
        db.insert("questions", null, cv);
    }
    public boolean registerUser(String username, String password, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("email", email);
        long id = db.insert("users", null, cv);
        return id != -1;
    }

    public int loginUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM users WHERE username=? AND password=?", new String[]{username, password});
        if (c.moveToFirst()) {
            int id = c.getInt(0);
            c.close();
            return id;
        }
        c.close();
        return -1;
    }

    public int getUserId(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM users WHERE username=?", new String[]{username});
        if (c.moveToFirst()) {
            int id = c.getInt(0);
            c.close();
            return id;
        }
        c.close();
        return -1;
    }

    public void saveScore(int userId, String category, int score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId", userId);
        cv.put("category", category);
        cv.put("score", score);
        db.insert("scores", null, cv);
    }

    public List<String> getLeaderboardList() {
        SQLiteDatabase db = getReadableDatabase();
        List<String> out = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT u.username, s.score FROM scores s JOIN users u ON s.userId = u.id ORDER BY s.score DESC, s.timestamp ASC LIMIT 50", null);
        if (c.moveToFirst()) {
            do {
                String u = c.getString(0);
                int sc = c.getInt(1);
                out.add(u + " - " + sc);
            } while (c.moveToNext());
        }
        c.close();
        return out;
    }

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> out = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, category, question, option1, option2, option3, option4, answer FROM questions WHERE category=?", new String[]{category});
        if (c.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(c.getInt(0));
                q.setCategory(c.getString(1));
                q.setQuestion(c.getString(2));
                q.setOption1(c.getString(3));
                q.setOption2(c.getString(4));
                q.setOption3(c.getString(5));
                q.setOption4(c.getString(6));
                q.setAnswer(c.getString(7));
                out.add(q);
            } while (c.moveToNext());
        }
        c.close();
        Collections.shuffle(out);
        return out.size() > 5 ? out.subList(0,5) : out;
    }
}
