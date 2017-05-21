package com.example.lekham.appchat.core.users.add;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class AddUsersPresenter implements AddUsersContract.Presenter, AddUsersContract.AddUserListener {
    private AddUsersContract.View mView;
    private AddUsersInterator mAddUsersInterator;

    public AddUsersPresenter(AddUsersContract.View view) {
        mView = view;
        mAddUsersInterator = new AddUsersInterator(this);
    }

    @Override
    public void addUser(Context context, FirebaseUser firebaseUser) {
        mAddUsersInterator.addUserToDatabase(context, firebaseUser);
    }

    @Override
    public void onSuccess(String message) {
        mView.onAddUserSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onAddUserFailure(message);
    }
}
