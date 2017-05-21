package com.example.lekham.appchat.core.register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegistrationListener {
    RegisterContract.View view;
    RegisterInterator registerInterator;
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        registerInterator = new RegisterInterator(this);
    }

    @Override
    public void register(Activity activity, String email, String password) {
        registerInterator.performFireBaseRegistration(activity, email, password);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        view.onRegistrationSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String message) {
        view.onRegistrationFailure(message);
    }
}
