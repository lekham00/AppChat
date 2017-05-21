package com.example.lekham.appchat.core.register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Le Kham on 5/20/2017.
 */

public interface RegisterContract {
    interface View {
        void onRegistrationSuccess(FirebaseUser firebaseUser);

        void onRegistrationFailure(String message);
    }

    interface Presenter {
        void register(Activity activity, String email, String password);
    }

    interface Interator {
        void performFireBaseRegistration(Activity activity, String email, String password);
    }

    interface OnRegistrationListener {
        void onSuccess(FirebaseUser firebaseUser);

        void onFailure(String message);
    }
}
