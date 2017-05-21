package com.example.lekham.appchat.utils;

import android.content.Context;
import android.graphics.Color;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class Toaster {

    private static StyleableToast st;

    public static StyleableToast error(Context context, String message, int flags) {
        st = new StyleableToast(context, message, flags);
        st.setBackgroundColor(Color.RED);
        st.setBoldText();
        st.setTextColor(Color.WHITE);
        st.setCornerRadius(7);
        return st;
    }
    public static StyleableToast success(Context context, String message, int flags) {
        st = new StyleableToast(context, message, flags);
        st.setBackgroundColor(Color.GREEN);
        st.setBoldText();
        st.setTextColor(Color.WHITE);
        st.setCornerRadius(7);
        return st;
    }
}
