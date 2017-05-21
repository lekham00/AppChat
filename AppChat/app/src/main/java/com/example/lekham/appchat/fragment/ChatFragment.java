package com.example.lekham.appchat.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lekham.appchat.R;
import com.example.lekham.appchat.adapter.ChatRecyclerAdapter;
import com.example.lekham.appchat.core.chat.ChatContract;
import com.example.lekham.appchat.core.chat.ChatPresenter;
import com.example.lekham.appchat.models.Chat;
import com.example.lekham.appchat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment implements TextView.OnEditorActionListener, ChatContract.View {

    EditText mEditTextChat;
    private View mView;
    private ChatPresenter mChatPresenter;
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    private RecyclerView mRecyclerViewChat;
    private List<Chat> chatList = new ArrayList<>();



    public static ChatFragment newInstance(String receiver,
                                           String receiverUid,
                                           String firebaseToken) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_RECEIVER, receiver);
        args.putString(Constants.ARG_RECEIVER_UID, receiverUid);
        args.putString(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_chat, container, false);
        init();
        return mView;
    }

    private void init() {
        mEditTextChat = (EditText) mView.findViewById(R.id.editTextChat);
        mEditTextChat.setOnEditorActionListener(this);
        mChatPresenter = new ChatPresenter(this);
        mRecyclerViewChat = (RecyclerView) mView.findViewById(R.id.recyclerViewChat);
        getMessages();
    }

    private void getMessages() {
        if (getArguments() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (mChatPresenter != null) {
                mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(), getArguments().getString(Constants.ARG_RECEIVER_UID).toString());
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
        }
        return false;
    }

    private void sendMessage() {
        Log.d("LK", "sendMessage : ");
        if (getArguments() != null) {
            String receiver = getArguments().getString(Constants.ARG_RECEIVER).toString();
            String receiverUid = getArguments().getString(Constants.ARG_RECEIVER_UID).toString();
            String sender = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
            String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
            String firebaseToken = getArguments().getString(Constants.ARG_FIREBASE_TOKEN).toString();
            String message = mEditTextChat.getText().toString();
            long timestamp = System.currentTimeMillis();
            Chat chat = new Chat(sender, receiver, senderUid, receiverUid, message, timestamp);
            mChatPresenter.sendMessage(getActivity(), chat, firebaseToken);
        }
    }

    @Override
    public void onSendMessageSuccess() {

    }

    @Override
    public void onSendMessageFailure(String message) {

    }

    @Override
    public void onGetMessageSuccess(Chat chat) {
        Log.d("LK", "chat : " +chat.getMessage());
        if (chat != null) {
            if (mChatRecyclerAdapter == null) {
                mChatRecyclerAdapter = new ChatRecyclerAdapter(chatList);
                mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
            }
            chatList.add(chat);
            mChatRecyclerAdapter.notifyDataSetChanged();
            mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void onGetMessageFailure(String message) {

    }
}
