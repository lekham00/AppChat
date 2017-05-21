package com.example.lekham.appchat.core.chat;

import android.content.Context;

import com.example.lekham.appchat.models.Chat;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class ChatPresenter implements ChatContract.Presenter, ChatContract.OnSendMessageListener, ChatContract.OnGetMessageListener {

    private ChatContract.View mView;
    private ChatInterator mChatInterator;

    public ChatPresenter(ChatContract.View view) {
        mView = view;
        mChatInterator = new ChatInterator(this, this);

    }

    @Override
    public void sendMessage(Context context, Chat chat, String token) {
        mChatInterator.sendMessageToDatabase(context, chat, token);
    }

    @Override
    public void getMessage(String senderUid, String receiverUid) {
        mChatInterator.getMessageFromDatabase(senderUid, receiverUid);
    }

    @Override
    public void onSendSuccess() {
        mView.onSendMessageSuccess();
    }

    @Override
    public void onSendFailure(String message) {
        mView.onSendMessageFailure(message);
    }

    @Override
    public void onGetSuccess(Chat chat) {
        mView.onGetMessageSuccess(chat);
    }

    @Override
    public void onGetFailure(String message) {
        mView.onGetMessageFailure(message);
    }
}
