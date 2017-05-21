package com.example.lekham.appchat;

import android.app.Application;

/**
 * Created by Le Kham on 5/21/2017.
 */

public class FirebaseChatMainApp extends Application {
    private static boolean isIsChatActivityOpen = false;

    public static boolean isIsChatActivityOpen() {
        return isIsChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean isChatActivityOpen) {
        FirebaseChatMainApp.isIsChatActivityOpen = isChatActivityOpen;
    }
}
