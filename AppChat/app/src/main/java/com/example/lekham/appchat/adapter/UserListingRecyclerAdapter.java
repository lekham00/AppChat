package com.example.lekham.appchat.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lekham.appchat.R;
import com.example.lekham.appchat.models.User;

import java.util.List;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class UserListingRecyclerAdapter extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder> {
    private List<User> mUsers;


    public UserListingRecyclerAdapter(List<User> users) {
        mUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.mTextViewUsername.setText(user.getEmail());
        holder.mTextViewUserAlphabet.setText(user.getEmail().substring(0, 1));
    }


    @Override
    public int getItemCount() {
        if (mUsers != null && mUsers.size() > 0)
            return mUsers.size();
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewUserAlphabet, mTextViewUsername;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
            mTextViewUsername = (TextView) itemView.findViewById(R.id.text_view_username);
        }

    }
}