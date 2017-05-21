package com.example.lekham.appchat.server;

import com.example.lekham.appchat.FirebaseChatMainApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getData().get(FcmNotificationBuilder.KEY_TITLE);
            String message = remoteMessage.getData().get(FcmNotificationBuilder.KEY_TEXT);
            String username = remoteMessage.getData().get(FcmNotificationBuilder.KEY_USERNAME);
            String uid = remoteMessage.getData().get(FcmNotificationBuilder.KEY_UID);
            String fcmToken = remoteMessage.getData().get(FcmNotificationBuilder.KEY_FCM_TOKEN);
            if (FirebaseChatMainApp.isIsChatActivityOpen()) {

            } else {

            }
        }

    }
}
