package com.example.lekham.appchat.core.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.lekham.appchat.utils.Constants;
import com.example.lekham.appchat.utils.SharedPrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class LoginInterator implements LoginContract.Interator {

    private LoginContract.LoginListener mLoginListener;

    public LoginInterator(LoginContract.LoginListener loginListener) {
        mLoginListener = loginListener;
    }

    @Override
    public void loginToDatabase(final Context context, String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mLoginListener.onSuccess(task.getResult().toString());
                    updateToken(task.getResult().getUser().getUid(), new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN));
                } else {
                    mLoginListener.onFailure(task.getException().getMessage().toString());
                }
            }
        });
    }

    private void updateToken(String ui, String token) {
        FirebaseDatabase.getInstance().getReference()
                .child(Constants.ARG_USERS)
                .child(ui)
                .child(Constants.ARG_FIREBASE_TOKEN)
                .setValue(token);
    }
}
