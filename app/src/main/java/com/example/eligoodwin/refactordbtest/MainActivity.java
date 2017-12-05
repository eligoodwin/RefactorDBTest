package com.example.eligoodwin.refactordbtest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button submit;
    private Button getButton;
    private EditText enterTweet;
    private EditText username;
    private EditText url;
    private TextView textView;
    private static final String TAG = MainActivity.class.getSimpleName();

    private SQLiteDatabase sqLiteDatabase;
    private MarkovUserDB  markovUserDB;

    private String theUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markovUserDB = new MarkovUserDB(MainActivity.this);
        try{
            sqLiteDatabase = markovUserDB.getReadableDatabase();
            Toast.makeText(MainActivity.this, "database is working? "+ sqLiteDatabase.isOpen(), Toast.LENGTH_SHORT).show();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM markoved_user where 0", null);
            String[] cols = cursor.getColumnNames();
            cursor.close();
            String colnames = "";

            for(String s: cols){
                colnames += (s + " ");
            }
            Log.d(TAG, "user column names are: " + colnames);
            Cursor cursor2 = sqLiteDatabase.rawQuery("SELECT * FROM user_tweet where 0", null);
            String[] cols2 = cursor2.getColumnNames();
            String colnames2 = "";
            for(String s: cols2){
                colnames2 += (s + " ");
            }
            Log.d(TAG, "tweet column names are: " + colnames2);

            cursor2.close();
        }catch(SQLiteException e){
            e.printStackTrace();
        }

        Log.d(TAG, "database stuff " + MarkovUserDB.SQL_CREATE_TABLE_MARKOVED_USERS);


        setContentView(R.layout.activity_main);
        enterTweet = findViewById(R.id.tweetbox);
        username = findViewById(R.id.username);
        url = findViewById(R.id.url);
        textView = findViewById(R.id.diplayDBentry);
        submit = findViewById(R.id.submitButton);
        getButton =findViewById(R.id.getButton);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetData = enterTweet.getText().toString();
                theUsername = username.getText().toString();
                String theUrl = url.getText().toString();

                addEntry(tweetData, theUrl);
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastEntry();
            }
        });
    }



    private void addEntry(String tweetdata, String theUrl){
        ContentValues vals = new ContentValues();
        vals.put("user_name", theUsername);
        vals.put("profile_pic", theUrl);
        long userID = sqLiteDatabase.insert("markoved_user", null, vals);
        vals.clear();
        Log.d(TAG, "Row id " + userID);
//       Cursor usercursor = sqLiteDatabase.query("markoved_user",
//                new String[]{"markoved_user_ID"},
//                "user_name = ?",
//                new String[]{theUsername},
//                null,
//                null,
//                null,
//                null
//                );

        vals.put(MarkovUserDB.MARKOVED_USER_ID, userID);
        vals.put(MarkovUserDB.COLUMN_NAME_TWEET, tweetdata);
        sqLiteDatabase.insert("user_tweet", null, vals);
    }

    private void getLastEntry(){
        Cursor usercursor = sqLiteDatabase.query("markoved_user",
                new String[]{"*"},
                "user_name=?",
                new String[]{theUsername},
                null,
                null,
                null,
                null
                );
        usercursor.moveToFirst();
        int userid = usercursor.getInt(usercursor.getColumnIndex("markoved_user_ID"));
        String thierUsername = usercursor.getString(usercursor.getColumnIndex("user_name"));
        String picurl = usercursor.getString(usercursor.getColumnIndex("profile_pic"));

        usercursor = sqLiteDatabase.rawQuery("SELECT user_tweet.tweet FROM user_tweet WHERE user_tweet.markoved_user_id=?", new String[]{String.valueOf(userid)});

        usercursor.moveToFirst();
        String theTweet = usercursor.getString(usercursor.getColumnIndex("tweet"));

        String finalString = thierUsername + " " + picurl + " " +theTweet;
        textView.setText(finalString);

    }
}
