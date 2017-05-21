package com.example.lekham.appchat.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lekham.appchat.R;
import com.example.lekham.appchat.models.Chat;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;
    private List<Chat> chatList;

    public ChatRecyclerAdapter(List<Chat> chats) {
        chatList = chats;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                viewHolder = new MyChatViewHolder(layoutInflater.inflate(R.layout.item_chat_mine, parent, false));
                break;
            case VIEW_TYPE_OTHER:
                viewHolder = new OtherChatViewHolder(layoutInflater.inflate(R.layout.item_chat_other, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        if (TextUtils.equals(chat.getSenderUid(), FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
            configureChatMyViewHolder((MyChatViewHolder) holder, chat);
        } else {
            configureChatOtherViewHolder((OtherChatViewHolder) holder, chat);
        }
    }

    private void configureChatMyViewHolder(MyChatViewHolder myChatViewHolder, Chat chat) {
        if (chat != null) {
            myChatViewHolder.mTextViewMessage.setText(chat.getMessage());
            myChatViewHolder.mTextViewUserAlphabet.setText(chat.getSender().substring(0, 1));
        }
    }


    private void configureChatOtherViewHolder(OtherChatViewHolder otherChatViewHolder, Chat chat) {
        if (chat != null) {
            otherChatViewHolder.mTextViewMessage.setText(chat.getMessage());
            otherChatViewHolder.mTextViewUserAlphabet.setText(chat.getReceiver().substring(0, 1));
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(chatList.get(position).getSenderUid(), FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewMessage, mTextViewUserAlphabet;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            mTextViewMessage = (TextView) itemView.findViewById(R.id.text_view_message);
            mTextViewUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewMessage, mTextViewUserAlphabet;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            mTextViewMessage = (TextView) itemView.findViewById(R.id.text_view_message);
            mTextViewUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }
}
