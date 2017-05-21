package com.example.lekham.appchat.core.users.get;

import com.example.lekham.appchat.models.User;

import java.util.List;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class GetUserPresenter implements GetUserContract.Presenter, GetUserContract.OnGetAllUsersListener {

    private GetUserContract.View mView;
    private GetUserInterator mGetUserInterator;

    public GetUserPresenter(GetUserContract.View view) {
        this.mView = view;
        mGetUserInterator = new GetUserInterator(this);
    }

    @Override
    public void getAllUsers() {
        mGetUserInterator.getAllUsersFromFirebase();
    }

    @Override
    public void getChatUsers() {

    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mView.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mView.onGetAllUsersFailure(message);
    }
}
