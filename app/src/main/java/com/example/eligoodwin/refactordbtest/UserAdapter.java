package com.example.eligoodwin.refactordbtest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(int position, View convertView, ViewGroup parnet){
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.user_model_display, null, true);
        }

        final UserModel user = getItem(position);
        TextView userName = convertView.findViewById(R.id.username);
        TextView userTweet = convertView.findViewById(R.id.userTweet);
        TextView userUrl = convertView.findViewById(R.id.url);
        userName.setText(user.getUsername());
        userUrl.setText(user.getUrl());
        userTweet.setText(user.getTweet());

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //delete the user for now show the user name when tapped
                Toast.makeText(context,"You tapped " + user.getUsername(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}
