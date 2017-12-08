package com.example.eligoodwin.refactordbtest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by eligoodwin on 12/7/17.
 */

public class UserAdapter extends ArrayAdapter<UserModel> {
    List<UserModel> usersInDatabase;
    Context context;
    int resource;

    public UserAdapter(Context context, int resource, List<UserModel> theUsers){
        super(context, resource, theUsers);
        this.usersInDatabase = theUsers;
        this.resource = resource;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parnet){
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.user_model_display, null, true);
        }

        final UserModel user = getItem(position);
        TextView userName = (TextView)convertView.findViewById(R.id.userName);
        TextView userTweet = (TextView)convertView.findViewById(R.id.userTweet);
        TextView userUrl = (TextView)convertView.findViewById(R.id.userUrl);
        System.out.print("user name is " + user.getUsername());

        userName.setText(user.getUsername());
        userUrl.setText(user.getUrl());
        userTweet.setText(user.getTweet());

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(position);
                Intent viewTweets = new Intent(context, DisplayTweets.class);
                context.startActivity(viewTweets);
            }
        });

        return convertView;
    }

    void deleteUser(int position){
        SQLiteDatabase database;
        MarkovUserDB markovUserDB;

        //test delete of tweet
        markovUserDB = new MarkovUserDB(context);
        database = markovUserDB.getReadableDatabase();
        database.delete(MarkovUserDB.TABLE_NAME_1, MarkovUserDB.COLUMN_NAME_USER_NAME + "=?",
                new String[]{usersInDatabase.get(position).getUsername()});
    }

}
