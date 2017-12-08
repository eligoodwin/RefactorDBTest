package com.example.eligoodwin.refactordbtest;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DisplayTweets extends AppCompatActivity {
    private List<String> userTweets;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tweets);
        //generate the view
        populateTable();
    }

    void populateTable(){
        userTweets = getAllTweetS();
        listView = (ListView)findViewById(R.id.tweetList);
    TweetAdapter adapter = new TweetAdapter(
            getApplicationContext(), R.layout.user_tweets_display,
           userTweets
    );
    listView.setAdapter(adapter);

    }


    List<String> getAllTweetS(){
        List<String> tweets = new ArrayList<>();

        SQLiteDatabase database;
        MarkovUserDB markovUserDB;

        try{
            markovUserDB = new MarkovUserDB(this);
            database = markovUserDB.getReadableDatabase();

            Cursor cursor = database.rawQuery("SELECT tweet FROM user_tweet", null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                String tweet = cursor.getString(cursor.getColumnIndex(MarkovUserDB.COLUMN_NAME_TWEET));
                tweets.add(tweet);
                cursor.moveToNext();
            }
            cursor.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

        return tweets;
    }
}
