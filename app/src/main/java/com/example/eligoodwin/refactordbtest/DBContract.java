package com.example.eligoodwin.refactordbtest;

import android.provider.BaseColumns;

/**
 * Created by eligoodwin on 11/11/17.
 */

/*Build a mysql database to store markov'ed user tweets so that they can be displayed
*
* */

public class DBContract {
    private DBContract(){}

    public final class MarkovUser implements BaseColumns{
        public static final String DB_Name = "markoved_users";
        public static final String TABLE_NAME_1 = "markoved_user";
        public static final String TABLE_NAME_2 = "user_tweet";
        public static final String MARKOVED_USER_ID = "markoved_user_ID";
        public static final String TWEET_ID = "tweet_ID";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_TWEET = "tweet";
        public static final String COLUMN_NAME_PROFILE_URL = "profile_pic";

        public static final String SQL_CREATE_TABLE_MARKOVED_USERS = "CREATE TABLE " + TABLE_NAME_1
                + " (" + MARKOVED_USER_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_USER_NAME + " VARCHAR(255)," +
                COLUMN_NAME_PROFILE_URL + " VARCHAR(255));";
        public static final String SQL_DROP_MARKOVED_USERS = "DROP TABLE IF EXISTS " + TABLE_NAME_1;

        public static final String SQL_CREATE_TABLE_TWEETS = "Create TABLE " + TABLE_NAME_2
                + " (" + TWEET_ID+ " INTEGER PRIMARY KEY, " +
                MARKOVED_USER_ID + " INTEGER KEY NOT NULL, " +
                COLUMN_NAME_TWEET + " VARCHAR(255), " +
                "FOREIGN KEY (" + MARKOVED_USER_ID+ ") REFERENCES "+ TABLE_NAME_1+
                    "(" + MARKOVED_USER_ID + ") ON DELETE CASCADE);";

        public static final int DB_VERSION = 22;
        public static final String SQL_DROP_TWEETS = "DROP TABLE IF EXISTS " + TABLE_NAME_2;

    }
}
