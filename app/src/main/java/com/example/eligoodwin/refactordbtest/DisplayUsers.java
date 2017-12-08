package com.example.eligoodwin.refactordbtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayUsers extends AppCompatActivity {
    private static final String TAG = DisplayUsers.class.getSimpleName();
    private ListView listView;
    private List<UserModel> userModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);
        populateTable();
    }


    //get entries from db into usable form
    private void populateTable(){
        userModels = getModelsFromDb();
        listView =(ListView)findViewById(R.id.dbList);

        UserAdapter adapter = new UserAdapter(
                getApplicationContext(), R.layout.user_model_display,
                userModels
        );

        listView.setAdapter(adapter);
    }


    private List<UserModel> getModelsFromDb(){
        List<UserModel> usersInDB  = new ArrayList<>();
        //get the entries out of the database
        SQLiteDatabase database;
        MarkovUserDB markovUserDB;

        try{
            markovUserDB = new MarkovUserDB(this);
            database = markovUserDB.getReadableDatabase();
            Log.d(TAG, "creating database" );

            //get user id, username, url
            Cursor cursor = database.rawQuery("SELECT markoved_user.user_name, markoved_user.profile_pic, tweet FROM markoved_user " +
                    "INNER JOIN user_tweet ON markoved_user.markoved_user_ID = user_tweet.markoved_user_ID", null);
            cursor.moveToFirst();

            //shove everything into the model
            while(!cursor.isAfterLast()){
                String username = cursor.getString(cursor.getColumnIndex(MarkovUserDB.COLUMN_NAME_USER_NAME));
                String userUrl = cursor.getString(cursor.getColumnIndex(MarkovUserDB.COLUMN_NAME_PROFILE_URL));
                String userTweet = cursor.getString(cursor.getColumnIndex(MarkovUserDB.COLUMN_NAME_TWEET));
                UserModel tempModel = new UserModel(username, userUrl, userTweet);
                usersInDB.add(tempModel);
                cursor.moveToNext();
            }
            cursor.close();

        }catch (SQLiteException e){
            Log.d(TAG, "database could nto be created");
            e.printStackTrace();
        }

        return usersInDB;
    }
}
