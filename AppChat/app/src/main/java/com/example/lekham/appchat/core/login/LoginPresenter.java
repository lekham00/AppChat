package com.example.lekham.appchat.core.login;

import android.content.Context;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginContract.LoginListener {

    private LoginContract.View mView;
    private LoginInterator mLoginInterator;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mLoginInterator = new LoginInterator(this);

    }

    @Override
    public void login(Context context, String email, String password) {
        mLoginInterator.loginToDatabase(context, email, password);
    }

    @Override
    public void onSuccess(String message) {
        mView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onLoginFailure(message);
    }
}
