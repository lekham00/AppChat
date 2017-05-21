package com.example.lekham.appchat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lekham.appchat.FirebaseChatMainApp;
import com.example.lekham.appchat.R;
import com.example.lekham.appchat.fragment.ChatFragment;
import com.example.lekham.appchat.utils.Constants;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String receiver = bundle.getString(Constants.ARG_RECEIVER).toString();
            String receiver_uid = bundle.getString(Constants.ARG_RECEIVER_UID).toString();
            String firebaseToken = bundle.getString(Constants.ARG_FIREBASE_TOKEN).toString();
            setTitle(receiver);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_chat, ChatFragment.newInstance(receiver, receiver_uid, firebaseToken), ChatFragment.class.getSimpleName());
            fragmentTransaction.commit();
        }

    }

    public static void startIntent(Context context, String receiver,
                                   String receiverUid,
                                   String firebaseToken) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        context.startActivity(intent);
    }

    public static void startIntent(Context context, int flags) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseChatMainApp.setChatActivityOpen(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseChatMainApp.setChatActivityOpen(false);
    }
}
