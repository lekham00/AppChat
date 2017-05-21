package com.example.lekham.appchat.core.users.add;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Le Kham on 5/20/2017.
 */

public interface AddUsersContract {
    interface View {
        void onAddUserSuccess(String message);

        void onAddUserFailure(String message);
    }

    interface Interator {
        void addUserToDatabase(Context context, FirebaseUser firebaseUser);
    }

    interface Presenter {
        void addUser(Context context, FirebaseUser firebaseUser);
    }

    interface AddUserListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
