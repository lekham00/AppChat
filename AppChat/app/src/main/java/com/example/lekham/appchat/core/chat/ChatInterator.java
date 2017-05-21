package com.example.lekham.appchat.core.chat;

import android.content.Context;
import android.util.Log;

import com.example.lekham.appchat.models.Chat;
import com.example.lekham.appchat.server.FcmNotificationBuilder;
import com.example.lekham.appchat.utils.Constants;
import com.example.lekham.appchat.utils.SharedPrefUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class ChatInterator implements ChatContract.Interator {
    private static final String TAG = "ChatInteractor";
    private ChatContract.OnSendMessageListener mOnSendMessageListener;
    private ChatContract.OnGetMessageListener mOnGetMessageListener;

    public ChatInterator(ChatContract.OnSendMessageListener onSendMessageListener, ChatContract.OnGetMessageListener onGetMessageListener) {
        mOnGetMessageListener = onGetMessageListener;
        mOnSendMessageListener = onSendMessageListener;

    }

    @Override
    public void sendMessageToDatabase(final Context context, final Chat chat, final String token) {
        final String room_type_1 = chat.getSenderUid() + "_" + chat.getReceiverUid();
        final String room_type_2 = chat.getReceiverUid() + "_" + chat.getSenderUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("LK", "sendMessageToDatabase onDataChange :" + dataSnapshot.getValue());
                if (dataSnapshot.hasChild(room_type_1)) {
                    Log.e(TAG, "sendMessageToFirebaseUser: " + room_type_1 + " exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.getTimestamp())).setValue(chat);
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    Log.e(TAG, "sendMessageToFirebaseUser: " + room_type_2 + " exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_2).child(String.valueOf(chat.getTimestamp())).setValue(chat);
                } else {
                    Log.e(TAG, "sendMessageToFirebaseUser: success");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.getTimestamp())).setValue(chat);
                    getMessageFromDatabase(chat.getSenderUid(), chat.getReceiverUid());
                }

                mOnSendMessageListener.onSendSuccess();
                sendPushNotificationToReceiver(chat.getSender(), chat.getMessage(), chat.getSenderUid(), new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN), token);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnSendMessageListener.onSendFailure(databaseError.getMessage().toString());
            }
        });
    }

    @Override
    public void getMessageFromDatabase(String senderUid, String receiverUid) {
        final String room_type_1 = senderUid + "_" + receiverUid;
        final String room_type_2 = receiverUid + "_" + senderUid;
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("LK", "dataSnapshot :" + dataSnapshot.getValue());
                if (dataSnapshot.getValue() != null) {
                    if (dataSnapshot.hasChild(room_type_1)) {
                        getMessageFirebaseDatabase(room_type_1);
                    } else if (dataSnapshot.hasChild(room_type_2)) {
                        getMessageFirebaseDatabase(room_type_2);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetMessageListener.onGetFailure(databaseError.getMessage().toString());
            }
        });
    }

    private void getMessageFirebaseDatabase(String room) {
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_CHAT_ROOMS).child(room).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    mOnGetMessageListener.onGetSuccess(chat);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetMessageListener.onGetFailure(databaseError.getMessage().toString());
            }
        });
    }

    private void sendPushNotificationToReceiver(String username,
                                                String message,
                                                String uid,
                                                String firebaseToken,
                                                String receiverFirebaseToken) {
        FcmNotificationBuilder.initialize()
                .title(username)
                .message(message)
                .username(username)
                .uid(uid)
                .firebaseToken(firebaseToken)
                .receiverFirebaseToken(receiverFirebaseToken)
                .send();
    }
}
