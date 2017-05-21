package com.example.lekham.appchat.core.chat;

import android.content.Context;

import com.example.lekham.appchat.models.Chat;

/**
 * Created by Le Kham on 5/20/2017.
 */

public interface ChatContract {

    interface View {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);

        void onGetMessageSuccess(Chat chat);

        void onGetMessageFailure(String message);
    }

    interface Interator {
        void sendMessageToDatabase(Context context, Chat chat, String token);

        void getMessageFromDatabase(String senderUid, String receiverUid);
    }

    interface Presenter {
        void sendMessage(Context context, Chat chat, String token);

        void getMessage(String senderUid, String receiverUid);
    }

    interface OnSendMessageListener {
        void onSendSuccess();

        void onSendFailure(String message);
    }

    interface OnGetMessageListener {
        void onGetSuccess(Chat chat);

        void onGetFailure(String message);
    }
}
