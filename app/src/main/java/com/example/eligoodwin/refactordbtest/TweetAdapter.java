package com.example.eligoodwin.refactordbtest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by eligoodwin on 12/7/17.
 */

public class TweetAdapter extends ArrayAdapter<String>{
    List<String> allTweets;
    Context context;
    int resource;

    public TweetAdapter(Context context, int resource, List<String> allTweets){
        super(context, resource, allTweets);
        this.allTweets = allTweets;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.user_tweets_display, null, true);
        }

        final String tweet = getItem(position);
        TextView theTweet = (TextView)convertView.findViewById(R.id.aUserTweet);
        theTweet.setText(tweet);
        return convertView;
    }



}

