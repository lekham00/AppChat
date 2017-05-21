package com.example.lekham.appchat.core.register;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lekham.appchat.fragment.RegisterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class RegisterInterator implements RegisterContract.Interator {

    private static String TAG = RegisterInterator.class.getSimpleName();
    private RegisterContract.OnRegistrationListener onRegistrationListener;

    public RegisterInterator(RegisterContract.OnRegistrationListener onRegistrationListener) {
        this.onRegistrationListener = onRegistrationListener;
    }

    @Override
    public void performFireBaseRegistration(Activity activity, String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "onComplete : " + task.isSuccessful());
                if (task.isSuccessful()) {
                    onRegistrationListener.onSuccess(task.getResult().getUser());
                } else {
                    onRegistrationListener.onFailure(task.getException().getMessage().toString());
                }
            }
        });
    }
}
