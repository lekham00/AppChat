package com.example.lekham.appchat.core.login;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Le Kham on 5/20/2017.
 */

public interface LoginContract {
    interface View {
        void onLoginSuccess(String message);

        void onLoginFailure(String message);
    }

    interface Interator {
        void loginToDatabase(Context context, String email, String password);
    }

    interface Presenter {
        void login(Context context, String email, String password);
    }

    interface LoginListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
