package com.example.lekham.appchat.core.users.get;

import android.text.TextUtils;
import android.util.Log;

import com.example.lekham.appchat.models.User;
import com.example.lekham.appchat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class GetUserInterator implements GetUserContract.Interactor {

    private GetUserContract.OnGetAllUsersListener mOnGetAllUsersListener;

    public GetUserInterator(GetUserContract.OnGetAllUsersListener onGetAllUsersListener) {
        mOnGetAllUsersListener = onGetAllUsersListener;
    }

    @Override
    public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                List<User> users = new ArrayList<User>();
                while (dataSnapshotIterator.hasNext()) {
                    DataSnapshot snapshot = dataSnapshotIterator.next();
                    User user = snapshot.getValue(User.class);
                    if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                        if (!TextUtils.equals(user.getUid(), FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            users.add(user);
                        }
                    }
                    Log.d("LK","getAllUsersFromFirebase");
                }
                mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage().toString());
            }
        });
    }

    @Override
    public void getChatUsersFromFirebase() {

    }
}
