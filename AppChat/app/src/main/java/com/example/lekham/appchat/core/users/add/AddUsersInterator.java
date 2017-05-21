package com.example.lekham.appchat.core.users.add;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.lekham.appchat.R;
import com.example.lekham.appchat.models.User;
import com.example.lekham.appchat.utils.Constants;
import com.example.lekham.appchat.utils.SharedPrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class AddUsersInterator implements AddUsersContract.Interator {

    private AddUsersContract.AddUserListener mAddUserListener;

    public AddUsersInterator(AddUsersContract.AddUserListener addUserListener) {
        mAddUserListener = addUserListener;
    }

    @Override
    public void addUserToDatabase(final Context context, FirebaseUser firebaseUser) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(), new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN));
        databaseReference.child(Constants.ARG_USERS).child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mAddUserListener.onSuccess(context.getString(R.string.user_successfully_added));
                } else {
                    mAddUserListener.onSuccess(context.getString(R.string.user_unable_to_add));
                }

            }
        });
    }
}
